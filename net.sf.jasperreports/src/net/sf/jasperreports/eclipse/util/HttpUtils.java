/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.eclipse.core.net.proxy.IProxyChangeEvent;
import org.eclipse.core.net.proxy.IProxyChangeListener;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class HttpUtils {

	public static String getProxyProtocol(IProxyData d) {
		String protocol = d.getType().toLowerCase();
		if (protocol.equals("https"))
			protocol = "http";
		return protocol;
	}

	public static HttpClientBuilder setupProxy(HttpClientBuilder clientBuilder) {
		final CredentialsProvider cp = new BasicCredentialsProvider();
		clientBuilder.setDefaultCredentialsProvider(cp);

		HttpRoutePlanner routePlanner = new HttpRoutePlanner() {
			@Override
			public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
					throws HttpException {

				InetAddress local = RequestConfig.DEFAULT.getLocalAddress();
				boolean secure = "https".equalsIgnoreCase(target.getSchemeName());
				try {

					for (IProxyData d : proxyService.select(new URI(target.toURI()))) {

						Credentials c = getCredentials(d);
						if (c != null)
							cp.setCredentials(new AuthScope(d.getHost(), d.getPort()), c);

						return new HttpRoute(target, local, new HttpHost(d.getHost(), d.getPort(), getProxyProtocol(d)),
								secure);
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				return new HttpRoute(target, local, secure);
			}
		};
		clientBuilder.setRoutePlanner(routePlanner);
		return clientBuilder;
	}

	public static Executor setupProxy(Executor exec, URI uri) {
		if (uri.getHost() == null)
			return exec;
		for (IProxyData d : proxyService.select(uri)) {
			Credentials c = getCredentials(d);
			if (c != null)
				exec.auth(new HttpHost(d.getHost(), d.getPort()), c);
			exec.authPreemptiveProxy(new HttpHost(d.getHost(), d.getPort(), getProxyProtocol(d)));
			break;
		}
		executors.put(exec, uri);
		return exec;
	}

	public static void setupProxy(Executor exec, URI uri, Request req) {
		HttpHost proxy = HttpUtils.getUnauthProxy(exec, uri);
		if (proxy != null)
			req.viaProxy(proxy);
	}

	public static HttpHost getUnauthProxy(Executor exec, URI uri) {
		for (IProxyData d : proxyService.select(uri)) {
			Credentials c = getCredentials(d);
			if (c != null)
				continue;
			return new HttpHost(d.getHost(), d.getPort(), getProxyProtocol(d));
		}
		return null;
	}

	private static Map<Executor, URI> executors = new HashMap<Executor, URI>();

	public static IProxyService getProxyService() {
		BundleContext bc = JasperReportsPlugin.getDefault().getBundle().getBundleContext();
		ServiceReference serviceReference = bc.getServiceReference(IProxyService.class.getName());
		IProxyService service = (IProxyService) bc.getService(serviceReference);
		service.addProxyChangeListener(new IProxyChangeListener() {

			@Override
			public void proxyInfoChanged(IProxyChangeEvent event) {
				for (Executor exe : executors.keySet())
					setupProxy(exe, executors.get(exe));
			}
		});
		return service;
	}

	public static IProxyService proxyService = getProxyService();

	public static Credentials getCredentials(IProxyData data) {
		String userId = data.getUserId();
		if (userId != null) {
			Credentials proxyCred = new UsernamePasswordCredentials(userId, data.getPassword());
			// if the username is in the form "user\domain"
			// then use NTCredentials instead.
			int domainIndex = userId.indexOf("\\");
			if (domainIndex > 0) {
				String domain = userId.substring(0, domainIndex);
				if (userId.length() > domainIndex + 1) {
					String user = userId.substring(domainIndex + 1);
					proxyCred = new NTCredentials(user, data.getPassword(), data.getHost(), domain);
				}
			}
			return proxyCred;
		}
		return null;
	}

	public static String USER_AGENT_JASPERSOFT_STUDIO = "JaspersoftStudio";
	public static final String USER_AGENT = "com.jaspersoft.studio.server.user-agent"; //$NON-NLS-1$
}
