package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ResourceInterface extends Remote {
	public int testResource(String testValue) throws RemoteException;
}
