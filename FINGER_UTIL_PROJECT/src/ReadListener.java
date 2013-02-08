import khh.communication.telnet.TelnetKClient;
import khh.communication.telnet.TelnetKReadListener;

public class ReadListener extends Thread implements TelnetKReadListener{
	private String script;
	private int delay;
	private TelnetKClient telnet;
	private int over=0;

	public ReadListener(String script, int delay, TelnetKClient telnet){
		this.script = script;
		this.delay = delay;
		this.telnet = telnet;
	}

	@Override
	public void read(String msg){
		System.out.print(msg);
	}

	@Override
	public void run(){
		while(true){
			try{
				sleep(10);

			}catch(InterruptedException e){
				break;
			}
		}
	}

}
