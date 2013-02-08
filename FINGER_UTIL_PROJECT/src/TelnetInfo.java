
public class TelnetInfo{
	String ip="127.0.0.1";
	int port=23;
	String id;
	String pwd;
	String script;
	boolean enable=true;
	int delay=3000;
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public int getPort(){
		return port;
	}
	public void setPort(int port){
		this.port = port;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getPwd(){
		return pwd;
	}
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	public String getScript(){
		return script;
	}
	public void setScript(String script){
		this.script = script;
	}
	public boolean isEnable(){
		return enable;
	}
	public void setEnable(boolean enable){
		this.enable = enable;
	}
	public int getDelay(){
		return delay;
	}
	public void setDelay(int delay){
		this.delay = delay;
	}
	
	
}
