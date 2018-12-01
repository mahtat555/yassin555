import java.util.*; 

public class Message
{
	private int pi = 0, ps = 0 ;
	private ArrayList<String> sms = new ArrayList<String>() ;

	public Message() {}

	// nouveau message
	public void addMsg(String msg) {
		this.sms.add(msg) ;
		this.pi++ ;
	}
	// 
	public String msgs() {
		if( this.ps < this.pi ) 
			return this.sms.get(this.ps++) ;
		else 
			return null ;
	}
	// 
	public String getMsg(int i) {
		return this.sms.get(i) ;
	}
}