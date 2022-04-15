package com.pucrs.es.pd;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class Resource extends UnicastRemoteObject implements ResourceInterface {
	public Resource() throws RemoteException {
	}

	public int testResource(String testValue) throws RemoteException {
		try {
			System.out.println(RemoteServer.getClientHost() + "Chamou o backend!");
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		return testValue.hashCode();
	}

}

