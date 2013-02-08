package telnetTest;

import khh.communication.telnet.TelnetKClient;
import khh.communication.telnet.TelnetKReadListener;
import khh.string.util.StringUtil;

public class telnetktest {
    TelnetKReadListener listener  =new TelnetKReadListener() {
        @Override
        public void read(String msg) {
            try{
//            System.out.print(StringUtil.stringCharSetConversion(msg,StringUtil.SET_EUC_KR,StringUtil.SET_UTF_8));
//            System.out.println(StringUtil.stringCharSetConversion(msg,StringUtil.SET_UTF_8,StringUtil.SET_MS949));
//           System.out.println(StringUtil.stringCharSetConversion(msg,StringUtil.SET_MS949,StringUtil.SET_UTF_8));
//            System.out.println(StringUtil.stringCharSetConversion(msg,StringUtil.SET_UTF_8,StringUtil.SET_8859_1));
//            System.out.println(StringUtil.stringCharSetConversion(msg,StringUtil.SET_8859_1,StringUtil.SET_UTF_8));
            	System.out.print(msg);
            }catch (Exception e) {
            }
        }
    };
    
    
    TelnetKClient k = null;
    public void start() throws Exception{
//        String addr = "168.78.203.138";
        String addr = "172.19.190.140";
        int port=23;
        k = new TelnetKClient();
        k.connect(addr,port);
        k.addReadListener(listener);
    }
    public static void main(String[] args) throws Exception {
        new telnetktest().start(); 
    }
}
