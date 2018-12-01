import java.util.*; 
import java.rmi.* ;
import java.rmi.server.* ;
import java.security.MessageDigest;

public class MSGServer extends UnicastRemoteObject implements MSGIn
{
	private HashMap<String, Season> seasons = new HashMap<String, Season>();

	public MSGServer() throws RemoteException {}

	// creer un compte
	public Season createCompte(String name, String email, String password) throws RemoteException {
		if(!this.existUser(email))
		{
			Season season = new Season(name, email, hash512(password));
			seasons.put(email, season);
			System.out.println("New user of email <<" + email + ">> and name <<" + name + ">> is create !! ") ;
			return season;
		} else {
			return null ;
		}
	}
	// connecter a un compte
	public Season connect(String email, String password) throws RemoteException {
		if( this.existUser(email) ) {
			Season season =  this.seasons.get(email) ;
			if(season.isPassword(hash512(password))) {
				System.out.println("The user of email <<" + email + ">> is conected !! ") ;
				return season;
			} else {
				return null ;
			}
		} else {
			return null ;
		}
	}
	public void writes(String email1, String email2, String msg) throws RemoteException {
		if( this.existUser(email1) )
			this.seasons.get(email1).writes(email2, msg) ;
	}
	// ls
	public ArrayList listUsers() throws RemoteException {
		ArrayList<String> al = new ArrayList<String>() ;
		for( Map.Entry season : this.seasons.entrySet() ) {  
   			Season s = (Season) season.getValue() ;
   			al.add(s.show()); 
  		} 
  		return al ;
	}

	// information sur un utilisateur
	public String infoUser(String email) throws RemoteException {
		if( this.seasons.containsKey(email) ) {
			return this.seasons.get(email).show() ;
		}
		return "" ;
	}

	// texte sur l'existance d'un compte
	public boolean existUser(String email) throws RemoteException {
		return this.seasons.containsKey(email);
	}

	// fonction de hash
	private static String hash512(String base) {
    	try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-512");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
        	}
        	return hexString.toString();
    	} catch(Exception ex){
       		throw new RuntimeException(ex);
    	}
	}
}