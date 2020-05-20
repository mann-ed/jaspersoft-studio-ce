/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.protocol.restv2;

import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.eclipse.core.runtime.IProgressMonitor;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.spi.Connector;

import net.sf.jasperreports.eclipse.util.HttpUtils;

public class JSSApacheConnectorFactory extends ApacheConnectorProvider {
	private Connector conn;

	@Override
	public Connector getConnector(Client client, Configuration runtimeConfig) {
		conn = super.getConnector(client, runtimeConfig);
		return conn;
	}

	private Response doWait(Future<Response> rf, IProgressMonitor monitor) throws Exception {
		try {
			if (monitor != null)
				while (!rf.isDone() && !rf.isCancelled()) {
					if (monitor.isCanceled())
						rf.cancel(true);
					Thread.sleep(5);
				}
			return rf.get();
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		}
	}

	public synchronized Response get(Builder builder, IProgressMonitor monitor) throws Exception {
		builder.header("Accept-Timezone", TimeZone.getDefault().getID());
		builder.header(HttpHeaders.USER_AGENT, HttpUtils.USER_AGENT_JASPERSOFT_STUDIO);
		builder.header(HttpHeaders.CACHE_CONTROL, "no-cache");
		return doWait(builder.async().get(), monitor);
	}

	public Response delete(Builder builder, IProgressMonitor monitor) throws Exception {
		builder.header("Accept-Timezone", TimeZone.getDefault().getID());
		// builder.header("User-Agent", HttpUtils.USER_AGENT_JASPERSOFT_STUDIO);
		return doWait(builder.async().delete(), monitor);
	}

	public Response post(Builder builder, Entity<?> entity, IProgressMonitor monitor) throws Exception {
		return post(builder, entity, monitor, TimeZone.getDefault());
	}

	public Response post(Builder builder, Entity<?> entity, IProgressMonitor monitor, TimeZone tz) throws Exception {
		return this.post(builder, entity, monitor, tz != null ? tz.getID() : null);
	}

	public Response post(Builder builder, Entity<?> entity, IProgressMonitor monitor, String tz) throws Exception {
		if (tz != null)
			builder.header("Accept-Timezone", tz);
		// builder.header("User-Agent", HttpUtils.USER_AGENT_JASPERSOFT_STUDIO);
		return doWait(builder.async().post(entity), monitor);
	}

	public Response put(Builder builder, Entity<?> entity, IProgressMonitor monitor) throws Exception {
		// builder.header("Content-Lenght", 0);
		builder.header("Accept-Timezone", TimeZone.getDefault().getID());
		builder.header("X-HTTP-Method-Override", "PUT");
		// builder.header("User-Agent", HttpUtils.USER_AGENT_JASPERSOFT_STUDIO);
		return doWait(builder.async().post(entity), monitor);
	}
}
