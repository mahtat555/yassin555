import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;

public class Server {

  public static void main(String[] args)  throws RemoteException 
  {
    try 
    {
      LocateRegistry.createRegistry(8080);
      MSGServer msg = new MSGServer();
      Naming.rebind("rmi://127.0.0.1:8080/MSG", msg);

      System.out.println("Server launches");
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    } 
  }
}
