package com.pucrs.es.pd;

import com.pucrs.es.pd.temp.Fatorial;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class p2pServer extends Thread {

	protected int port;
	protected String host;

	public p2pServer(String[] args) throws IOException {
		port = Integer.parseInt(args[2]);
		host = args[0];
	}

	public void run() {
		// Inicializando Servidor RMI
		try {
			System.setProperty("java.rmi.server.hostname", host);
			LocateRegistry.createRegistry(port);
			System.out.println("RMI registry ready.");
		} catch (RemoteException e) {
			System.out.println("RMI registry already running.");
		}

		// Registrando Rota
		try {
			String server = "rmi://" + host + ":" + port + "/Resource";
			Naming.rebind(server, new Resource());
			System.out.println("p2p Server is ready.");
		} catch (Exception e) {
			System.out.println("p2p Server failed: " + e);
		}
	}
}
