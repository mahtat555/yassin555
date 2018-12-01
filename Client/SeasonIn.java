import java.rmi.* ;

public interface SeasonIn extends Remote
{
	public String show() throws RemoteException ;
	public String reads(String email) throws RemoteException ;
	public void writes(String email, String msg) throws RemoteException ;
	public Boolean isPassword(String password) throws RemoteException ;
	public void setEmail(String email) throws RemoteException ;
	public void setName(String email) throws RemoteException ;
	public void setPassword(String password) throws RemoteException ;
	public String getEmail() throws RemoteException ;
	public String getName() throws RemoteException ;
}