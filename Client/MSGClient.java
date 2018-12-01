import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class MSGClient
{
	public static void main(String[] args) 
	{
	  try 
	  {
	  	Scanner in = new Scanner(System.in) ;
	  	String cmd1, cmd2 ;

	    MSGIn msg = (MSGIn) Naming.lookup("rmi://127.0.0.1:8080/MSG");
	    System.out.println("Welcome to MSGChat! type help for help. ") ;

	    do {
	    	System.out.print("MSG> ") ;
	    	cmd1 = split(in.nextLine()).get(0) ;
	    	if( cmd1.equals("ls") ) {
	    		for( Object user : msg.listUsers() ) 
	    			System.out.println(user) ;
	    	}else if( cmd1.equals("create-compte") ) {
	    		System.out.print("Name     : ") ;
	    		String name = in.nextLine() ;
	    		System.out.print("Email    : ") ;
	    		String email = in.nextLine() ;
	    		System.out.print("Password : ") ;
	    		String password = in.nextLine() ;
	    		SeasonIn season = msg.createCompte(name, email, password) ;
	    		if(season != null) {
		    		do {
		    			System.out.print(email + "# ") ;
		    			cmd2 = split(in.nextLine()).get(0) ;
		    			if( cmd2.equals("ls") ) {
		    				System.out.println("") ;
		    				for( Object user : msg.listUsers() ) 
		    					System.out.println(user) ;
		    				System.out.println("") ;
		    			} else if ( cmd2.equals("chat") ) {
		    				System.out.print("Email : ") ;
	    					String _email = in.nextLine() ;
	    					if( msg.existUser(_email) ) {
			    				threading prog = new threading(season, _email) ;
			    				prog.start();
			    				String txt ;
			    				for(;;) {
			    					txt = in.nextLine() ;
			    					if ( txt.equals(":q") ) {
			    						prog.exit() ;
			    						break ;
			    					}
			    					msg.writes(_email, email, txt) ;
			    				}
				    		} else {
				    			System.out.println("Compte not existe !!") ;
				    		}
		    			} else if ( cmd2.equals("help") ) {
		    				help2() ;
		    			} else if ( cmd2.equals("clear") ) {
		    				System.out.print("\033\143") ;
		    			} else if(cmd2.equals("exit")) {
		    			} else {
		    				System.out.println("command not found") ;
		    			}
		    		} while (!cmd2.equals("exit")) ;
		    	} else
					System.out.println("compte existe !!");
	    	} else if ( cmd1.equals("connect") ) {
	    		System.out.print("Email    : ") ;
	    		String email = in.nextLine() ;
	    		System.out.print("Password : ") ;
	    		String password = in.nextLine() ;
	    		SeasonIn season = msg.connect(email, password) ;
	    		if(season != null) {
		    		do {
		    			System.out.print(email + "# ") ;
		    			cmd2 = split(in.nextLine()).get(0) ;
		    			if( cmd2.equals("ls") ) {
		    				for( Object user : msg.listUsers() ) 
		    					System.out.println(user) ;
		    			} else if ( cmd2.equals("chat") ) {
		    				System.out.print("Email : ") ;
	    					String _email = in.nextLine() ;
	    					if( msg.existUser(_email) ) {
			    				threading prog = new threading(season, _email) ;
			    				prog.start();
			    				String txt ;
			    				for(;;) {
			    					txt = in.nextLine() ;
			    					if ( txt.equals(":q") ) {
			    						prog.exit() ;
			    						break ;
			    					}
			    					msg.writes(_email, email, txt) ;
			    				}
				    		} else {
				    			System.out.println("Compte not existe !!") ;
				    		}
		    			} else if ( cmd2.equals("help") ) {
		    				help2() ;
		    			} else if ( cmd2.equals("clear") ) {
		    				System.out.print("\033\143") ;
		    			} else if(cmd2.equals("exit")) {
		    			} else {
		    				System.out.println("command not found") ;
		    			}
		    		} while (!cmd2.equals("exit")) ;
		    	} else
					System.out.println("compte not existe !!");
	    	} else if ( cmd1.equals("help") ) {
	    		help1() ;
	    	} else if ( cmd1.equals("clear") ) {
	    		System.out.print("\033\143") ;
	    	} else if (cmd1.equals("exit")) {
	    	} else if (cmd1.equals("")) {
			} else {
	    		System.out.println("Command not found.") ;
	    	}
	    } while (!cmd1.equals("exit")) ;

	  } 
	  catch (Exception ex) 
	  {
	    System.out.println("Error");
	  }
	  
	}

	public static void help1() {
		System.out.println("\nls - list compte in Messenger.") ;
		System.out.println("exit - cause normal process termination.") ;
		System.out.println("clear - clear the terminal screen.") ;
		System.out.println("create-compte - create the new compte.") ;
		System.out.println("connect - connected to a compte.\n") ;
	}
	public static void help2() {
		System.out.println("\nls - list compte in Messenger.") ;
		System.out.println("exit - cause normal process termination.") ;
		System.out.println("clear - clear the terminal screen.") ;
		System.out.println("chat- exchange the messages between the users.\n") ;
	}

	public static Vector<String> split(String str){
		String[] tmp = str.split(" ");
	  	Vector<String> vec = new Vector<String>();
	  	for (String s : tmp) {
	  		if (!s.equals(""))
	  			vec.add(s) ;
	  	}
	  	if (vec.size() == 0 )
	  		vec.add("");
	  	return vec ;
	}
}


class threading extends Thread {
	private SeasonIn season ;
	String email ;
	boolean loop = true ;

	public threading (SeasonIn season, String email) {
		this.season = season ;
		this.email = email ;
	}
	public void exit() {
		this.loop = false ;
	}

	public void run() {
		while(loop) {
			String txt = null ;
			for (;;){
				try {
					txt = this.season.reads(this.email) ;
				} catch (RemoteException e) {}
				if (txt == null)
					break ;
				System.out.println("\t\t\t\t\t\t" + txt);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}