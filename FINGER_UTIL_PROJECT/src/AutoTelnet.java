import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import khh.communication.telnet.TelnetKClient;
import khh.communication.telnet.TelnetKReadListener;
import khh.conversion.util.ConversionUtil;
import khh.property.util.PropertyUtil;
import khh.string.util.StringUtil;
import khh.xml.XMLparser;

import org.xml.sax.SAXException;

public class AutoTelnet{
	ArrayList<TelnetInfo> telnetList = new ArrayList<TelnetInfo>();
	String tpath = "//autotelnet/telnet[@enable='true']";
	public AutoTelnet() {
	}
	private void setting(String path) throws SAXException, IOException, XPathExpressionException, NoClassDefFoundError, InterruptedException{
		path = path==null?"./script.xml":path;
		XMLparser xml = new XMLparser(path);
		Integer cnt = xml.getInt("count("+tpath+")");
		if(cnt==null || cnt<=0){
			System.out.println("Config XML Telnet Element NotFound Sorry");
			return;
		}
		
		
		for(int i = 1; i <= cnt; i++){
			TelnetInfo t = new TelnetInfo(); 
			t.setIp( xml.getString(tpath+"["+i+"]/@ip") );  
			t.setPort( xml.getInt(tpath+"["+i+"]/@port") );  
			t.setId( xml.getString(tpath+"["+i+"]/@id") );  
			t.setPwd( xml.getString(tpath+"["+i+"]/@pwd") );  
			t.setEnable( xml.getBoolean(tpath+"["+i+"]/@enable") );  
			t.setDelay( xml.getInt(tpath+"["+i+"]/@delay") );  
			t.setScript( xml.getString(tpath+"["+i+"]") );  
			//System.out.println(xml.getString(tpath+"["+i+"]"));
			telnetList.add(t);
		}
		for(int i = 0; i < telnetList.size(); i++){
			run(telnetList.get(i));
			//return;
		}
	}

	
	 TelnetKReadListener listener  =new TelnetKReadListener() {
	        @Override
	        public void read(String msg) {
	            try{
	            	System.out.print(msg);
	            }catch (Exception e) {
	            }
	        }
	    };
	private void run(TelnetInfo info) throws SocketException, IOException, InterruptedException{
		TelnetKClient k = null;
        String addr = info.getIp();// "172.19.190.140";
        int port=info.getPort();//23;
        k = new TelnetKClient();
        k.connect(info.getIp(),info.getPort());
        k.addReadListener(listener);

        String[] script = info.getScript()==null?null:info.getScript().trim().split("\n");
//		 BufferedWriter out  =new BufferedWriter(new StringWriter());
		 k.setWriteinputStream(ConversionUtil.toInputStream(info.getId()+PropertyUtil.getSeparator()));
		 Thread.sleep(info.getDelay());
		 k.setWriteinputStream(ConversionUtil.toInputStream(info.getPwd()+PropertyUtil.getSeparator()));
		 Thread.sleep(info.getDelay());
		 
		 for(int i = 0;script!=null && i < script.length; i++){
			 if(i==0){
				 Thread.sleep(6000);
			 }
			 k.setWriteinputStream(ConversionUtil.toInputStream(script[i].trim()+PropertyUtil.getSeparator()));
			 Thread.sleep(info.getDelay());
		}
//		 out.write("SGO1004016");
//		 out.newLine();
//		 out.write("a123456!!");
//		 out.newLine();
		 
//        while(true){
//        	
//        }
	}
	public static void main(String[] args) throws SAXException, IOException, XPathExpressionException, NoClassDefFoundError, InterruptedException{
		AutoTelnet a = new AutoTelnet();
		a.setting(args.length>0?args[0]:null);
	}
}
