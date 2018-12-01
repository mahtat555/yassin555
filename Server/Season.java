import java.util.*; 
import java.rmi.* ;
import java.rmi.server.* ;
import java.security.MessageDigest;

public class Season extends UnicastRemoteObject implements SeasonIn
{
	private String name, email, password;
	private HashMap<String, Message> messages = new HashMap<String, Message>();

	public Season(String name, String email, String password) throws RemoteException {
		this.email = email ;
		this.password = password;
		this.name = name;
	}

	// afficher les infos
	public String show() throws RemoteException {
		return this.email + " : " + this.name ;
	}
	// afficher le messages
	public String reads(String email) throws RemoteException {
		if( this.messages.containsKey(email) )
			return this.messages.get(email).msgs();
		return null ;
	}
	// ajouter un messge
	public void writes(String email, String msg) throws RemoteException {
		if( this.messages.containsKey(email) )
			this.messages.get(email).addMsg(msg) ;
		else {
			Message lmgs = new Message() ;
			lmgs.addMsg(msg) ;
			this.messages.put(email, lmgs) ;
		}
	}
	// teste de mot de pass
	public Boolean isPassword(String password) throws RemoteException {
		return this.password.equals(password) ;
	}

	// setters
	public void setEmail(String email) throws RemoteException {
		this.email = email;
	}
	public void setName(String email) throws RemoteException {
		this.name = name;
	}
	public void setPassword(String password) throws RemoteException {
		this.password = password;
	}

	// getters
	public String getEmail() throws RemoteException {
		return this.email;
	}
	public String getName() throws RemoteException {
		return this.name;
	}
}