/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.eclipse.core.internal.net.AbstractProxyProvider;
import org.eclipse.core.internal.net.ProxyManager;
import org.eclipse.core.net.proxy.IProxyChangeEvent;
import org.eclipse.core.net.proxy.IProxyChangeListener;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.JerseyInvocation;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.jaspersoft.studio.server.Activator;
import com.jaspersoft.studio.server.model.server.ServerProfile;
import com.jaspersoft.studio.server.protocol.restv2.RestV2Connection;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.utils.compatibility.StringMatcher;

public class HttpUtils {
	public static URI toSafeUri(final URL url) throws URISyntaxException {
		String u = "";
		if (!Misc.isNullOrEmpty(url.getProtocol()))
			u += url.getProtocol() + "://";
		if (!Misc.isNullOrEmpty(url.getUserInfo()))
			u += "@" + url.getUserInfo();
		u += IDN.toASCII(url.getHost());
		if (url.getPort() > 0)
			u += ":" + url.getPort();
		if (!Misc.isNullOrEmpty(url.getPath()))
			u += url.getPath();
		if (!Misc.isNullOrEmpty(url.getQuery()))
			u += url.getQuery();
		if (!Misc.isNullOrEmpty(url.getRef()))
			u += url.getRef();
		URI uri = new URI(u);
		setupUriHost(uri, IDN.toASCII(url.getHost()));
		return uri;
	}

	private static void setupUriHost(URI uri, String host) {
		if (uri.getHost() == null && uri.getAuthority() != null) {
			try {
				String[] s = uri.getAuthority().split(":");
				if (s.length > 0) {
					Field f = URI.class.getDeclaredField("host");
					f.setAccessible(true);
					f.set(uri, s[0]);
					if (s.length > 1) {
						f = URI.class.getDeclaredField("port");
						f.setAccessible(true);
						f.set(uri, Integer.valueOf(s[1]));
					}
				}
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static Builder getRequest(WebTarget target, MediaType arg) {
		return getRequest(target, target.request(arg));
	}

	public static Builder getRequest(WebTarget target, String arg) {
		return getRequest(target, target.request(arg));
	}

	public static Builder getRequest(WebTarget target) {
		return getRequest(target, target.request());
	}

	public static Builder getRequest(WebTarget target, Builder req) {
		try {
			if (target.getUri().getHost() != null && !target.getUri().getHost().contains("_"))
				return req;
			Field f = JerseyInvocation.Builder.class.getDeclaredField("requestContext");
			f.setAccessible(true);
			ClientRequest cr = (ClientRequest) f.get(req);
			if (cr != null) {
				f = ClientRequest.class.getDeclaredField("requestUri");
				f.setAccessible(true);
				URI uri = (URI) f.get(cr);
				if (uri.getHost() == null)
					setupUriHost(uri, target.getUri().getHost());
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return req;
	}

	/**
	 * Patching URI class in order to support the underscore char in hostname for a URL.
	 * This was originated by a very old customer bug.
	 */
	public static void patchURIClass() {
		try {
			long lowMaskValue = lowMask("-_");
			long highMaskValue = highMask("-_");
			
			patchUriField(lowMaskValue, "L_DASH");
		    patchUriField(highMaskValue, "H_DASH");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * An initial workaround was put in place for fixing the problem when lowMask and highMask methods
	 * were removed when moving from Java 8 -> 11.
	 * The current solution introduced for Java 17 uses sun.misc.Unsafe class in order to make it work again.
	 * 
	 * Additional references: 
	 * 	> java.net.URI get host with underscores (https://stackoverflow.com/questions/28568188/java-net-uri-get-host-with-underscores)
	 * 	> Change static final field in java 12+ (https://stackoverflow.com/a/61150853)
	 */
	private static void patchUriField(long maskValue, String fieldName)
	        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		final Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
		unsafeField.setAccessible(true);
		final sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);
		Field field = URI.class.getDeclaredField(fieldName);
		final Object staticFieldBase = unsafe.staticFieldBase(field);
        final long staticFieldOffset = unsafe.staticFieldOffset(field);
        unsafe.putObject(staticFieldBase, staticFieldOffset, maskValue);
	}
	
    // Compute the low-order mask for the characters in the given string (JDK 8)
    private static long lowMask(String chars) {
        int n = chars.length();
        long m = 0;
        for (int i = 0; i < n; i++) {
            char c = chars.charAt(i);
            if (c < 64)
                m |= (1L << c);
        }
        return m;
    }

    // Compute the high-order mask for the characters in the given string (JDK 8)
    private static long highMask(String chars) {
        int n = chars.length();
        long m = 0;
        for (int i = 0; i < n; i++) {
            char c = chars.charAt(i);
            if ((c >= 64) && (c < 128))
                m |= (1L << (c - 64));
        }
        return m;
    }	
	
	
	public static void setupProxy(ClientConfig clientConfig, URI uri) {
		CredentialsProvider cp = (CredentialsProvider) clientConfig
				.getProperty(ApacheClientProperties.CREDENTIALS_PROVIDER);
		setupUriHost(uri, null);

		IProxyService proxyService = net.sf.jasperreports.eclipse.util.HttpUtils.proxyService;
		if (proxyService.isProxiesEnabled()) {
			if (uri.getHost().contains("_")) {
				if (proxyService.hasSystemProxies() && proxyService.isSystemProxiesEnabled()) {
					AbstractProxyProvider nativeProxyProvider = null;
					if (proxyService instanceof ProxyManager) {
						try {
							Field f = ProxyManager.class.getDeclaredField("nativeProxyProvider");
							f.setAccessible(true);
							nativeProxyProvider = (AbstractProxyProvider) f.get(proxyService);
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					if (nativeProxyProvider == null) {
						try {
							nativeProxyProvider = (AbstractProxyProvider) Class
									.forName("org.eclipse.core.net.ProxyProvider").newInstance(); //$NON-NLS-1$
						} catch (ClassNotFoundException e) {
							// no class found
						} catch (Exception e) {
							UIUtils.showError(e);
						}
					}
					if (nativeProxyProvider != null) {
						IProxyData[] proxyDatas = nativeProxyProvider.select(uri);
						if (proxyDatas.length > 0)
							setupClientConfig(cp, clientConfig, proxyDatas[0]);
					}
				} else if (!isHostFiltered(proxyService, uri))
					for (IProxyData d : proxyService.getProxyData()) {
						if (d.getHost() == null)
							continue;
						if (!d.getHost().equals(uri.getHost()))
							continue;
						setupClientConfig(cp, clientConfig, d);
						break;
					}
			} else
				for (IProxyData d : proxyService.select(uri)) {
					setupClientConfig(cp, clientConfig, d);
					break;
				}
		}
		clientConfigs.put(clientConfig, uri);
	}

	private static void setupClientConfig(CredentialsProvider cp, ClientConfig clientConfig, IProxyData d) {
		Credentials c = net.sf.jasperreports.eclipse.util.HttpUtils.getCredentials(d);
		if (c != null && cp != null)
			cp.setCredentials(new AuthScope(new HttpHost(d.getHost(), d.getPort())), c);
		clientConfig.property(ClientProperties.PROXY_URI,
				net.sf.jasperreports.eclipse.util.HttpUtils.getProxyProtocol(d) + "://" + d.getHost() + ":"
						+ d.getPort());
	}

	private static boolean isHostFiltered(IProxyService ps, URI uri) {
		String[] filters = ps.getNonProxiedHosts();
		for (int i = 0; i < filters.length; i++) {
			String filter = filters[i];
			if (matchesFilter(uri.getHost(), filter))
				return true;
		}
		return false;
	}

	private static boolean matchesFilter(String host, String filter) {
		StringMatcher matcher = new StringMatcher(filter, true, false);
		return matcher.match(host);
	}

	public static Request setRequest(Request req, ServerProfile sp) {
		req.connectTimeout(sp.getTimeout());
		req.socketTimeout(sp.getTimeout());
		if (sp.isChunked())
			req.setHeader("Transfer-Encoding", "chunked");
		else
			req.removeHeaders("Transfer-Encoding");
		req.setHeader("Accept", "application/" + RestV2Connection.FORMAT);
		return req;
	}

	public static Request get(String url, ServerProfile sp) throws HttpException, IOException {
		System.out.println(url);
		return HttpUtils.setRequest(Request.Get(url), sp);
	}

	public static Request put(String url, ServerProfile sp) throws HttpException, IOException {
		System.out.println(url);
		return HttpUtils.setRequest(Request.Put(url), sp);
	}

	public static Request post(String url, ServerProfile sp) throws HttpException, IOException {
		System.out.println(url);
		return HttpUtils.setRequest(Request.Post(url), sp);
	}

	public static Request post(String url, Form form, ServerProfile sp) throws HttpException, IOException {
		System.out.println(url);
		return HttpUtils.setRequest(Request.Post(url).bodyForm(form.build()), sp);
	}

	public static Request delete(String url, ServerProfile sp) throws HttpException, IOException {
		System.out.println(url);
		return HttpUtils.setRequest(Request.Delete(url), sp);
	}

	private static Map<Executor, URI> executors = new HashMap<Executor, URI>();
	private static Map<ClientConfig, URI> clientConfigs = new HashMap<ClientConfig, URI>();

	public static IProxyService getProxyService() {
		BundleContext bc = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference serviceReference = bc.getServiceReference(IProxyService.class.getName());
		IProxyService service = (IProxyService) bc.getService(serviceReference);
		service.addProxyChangeListener(new IProxyChangeListener() {

			@Override
			public void proxyInfoChanged(IProxyChangeEvent event) {
				for (Executor exe : executors.keySet())
					net.sf.jasperreports.eclipse.util.HttpUtils.setupProxy(exe, executors.get(exe));
				for (ClientConfig exe : clientConfigs.keySet())
					setupProxy(exe, clientConfigs.get(exe));
			}
		});
		return service;
	}

}
