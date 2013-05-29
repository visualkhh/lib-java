package khh.communication.tcp.nio.websocket.msg;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.conversion.util.ConversionUtil;
import khh.debug.LogK;
import khh.encryption.base64.Base64;
import khh.encryption.sha1.SHA1;
import khh.string.token.StringTokenizer;


public class NioWebSocketMsg extends NioActionMsg {
	LogK log = LogK.getInstance();
	public static final byte[] STX 		= { (byte)'G', (byte)'E', (byte)'T', (byte)' ' , (byte)'/' };//GET /
	public static final byte[] ETX 		= { (byte)'\r', (byte)'\n', (byte)'\r', (byte)'\n' };
	
//    public static final byte[] PARAM_STX= {  };
//    public static final byte[] PARAM_CTX= { (byte)':' }; 
//    public static final byte[] PARAM_ETX= { (byte)'\r', (byte)'\n' };
  
    public String head_protocol="HTTP/1.1";
	@Override
	public void format(ByteBuffer data) {
		if(data==null){
			return;
		}
		String msg = ConversionUtil.toString(data) ;
		if(msg==null && msg.length()<=0){
			return;
		}
	    log.debug("msg\r\n"+msg);
		
		
		
		
		StringTokenizer hhkTok = new StringTokenizer(msg, "\r\n");
		setHead_protocol(hhkTok.nextToken("\r\n"));
		while (hhkTok.hasMoreElements()) {
			String m = hhkTok.nextToken();
			String[] param = m.split(": ");
			String key = param[0];
			String value = param[1];
			this.putParam(key.toUpperCase(), value);
			log.debug("head_param key:"+key+"    value:"+value);
		}
		
		
	}
	@Override
	public ByteBuffer unformat() {
		
		String key=getParamString("Sec-WebSocket-Key");
		String append="258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		String aceeptkey=null;
		try {
			aceeptkey =new String( Base64.encode( SHA1.encode((key+append).getBytes())) );
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		
//		String head_protocol = getHead_protocol()+" 101 Switching Protocols";
		String head_protocol = "HTTP/1.1 101 WebSocket Protocol Handshake";
//		String upgrade = "Upgrade: " + getParamString("Upgrade".toUpperCase());
		String upgrade = "Upgrade: WebSocket";
//		String connection = "Connection: " + getParamString("Connection".toUpperCase());
		String connection = "Connection: Upgrade";
		String sec_websocket_location = "Sec-WebSocket-Location: "+ getParamString("Sec-WebSocket-Location".toUpperCase());;
//		String sec_websocket_origin = "Sec-WebSocket-Origin:" + getParamString("Origin");
		String sec_websocket_accept="Sec-WebSocket-Accept: "+aceeptkey;
		String sec_websocket_protocol="Sec-WebSocket-Protocol: "+  getParamString("Sec-WebSocket-Protocol".toUpperCase());
		
		
		String msg=
				head_protocol+"\r\n"+
				upgrade+"\r\n"+
				connection+"\r\n"+
				//sec_websocket_origin+"\r\n"+
				sec_websocket_location+"\r\n"+
				sec_websocket_accept+"\r\n"+
				sec_websocket_protocol+
				new String (ETX);
				
		log.debug("unformat:\r\n"+msg);
		return ConversionUtil.toByteBuffer(msg);
	}

	public String getHead_protocol() {
		return head_protocol;
	}

	public void setHead_protocol(String head_protocol) {
		this.head_protocol = head_protocol;
	}
	
}
