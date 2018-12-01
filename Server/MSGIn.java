import java.rmi.* ;
import java.util.*; 

public interface MSGIn extends Remote
{
	public SeasonIn connect(String emial, String password) throws RemoteException ;
	public SeasonIn createCompte(String name, String email, String password) throws RemoteException ;
	public void writes(String email1, String email2, String msg) throws RemoteException ;
	public ArrayList listUsers() throws RemoteException ;
	public String infoUser(String email) throws RemoteException ;
	public boolean existUser(String email) throws RemoteException ;
}