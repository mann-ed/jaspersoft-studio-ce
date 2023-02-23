/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.ireport.jasperserver.ws;

public interface ManagementService extends java.rmi.Remote {
    public java.lang.String runReport(java.lang.String requestXmlString) throws java.rmi.RemoteException;
    public java.lang.String put(java.lang.String requestXmlString) throws java.rmi.RemoteException;
    public java.lang.String get(java.lang.String requestXmlString) throws java.rmi.RemoteException;
    public java.lang.String list(java.lang.String requestXmlString) throws java.rmi.RemoteException;
    public java.lang.String delete(java.lang.String requestXmlString) throws java.rmi.RemoteException;
    public java.lang.String copy(java.lang.String requestXmlString) throws java.rmi.RemoteException;
    public java.lang.String move(java.lang.String requestXmlString) throws java.rmi.RemoteException;
}
