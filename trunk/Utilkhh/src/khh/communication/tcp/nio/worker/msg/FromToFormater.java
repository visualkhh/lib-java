package khh.communication.tcp.nio.worker.msg;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class FromToFormater extends NioActionMsg {
    public static final byte STX=(byte) 0x04;
    public static final byte CTX=(byte) 0x25;
    public static final byte ETX=(byte) 0x12;
    
	public static enum PARAM {
		FROM("FROM"),
        TO("TO"),
        FILENAME("FILENAME"),//서버시간 yyyy/MM/dd HH:mm:ss/SSS
        FILESIZE("FILESIZE"),//서버정보요청 열려있는 서버이름s!   
        PART("PART"),//서버에 붙어있는 클라이언트이름s!   
        
        
        OTHER("OTHER"), //여기위에서부터는 다 FROMTO임..
        //LOGIN_LOGOUT(50002),
        //ADMIN_CLIENT_JOIN(50004)
        ;
        String value;
        PARAM(String id) {
            this.value = id;
        }
        public String getValue() {
            return this.value;
        }
    };
    
    LinkedHashMap<String, byte[]> param = new LinkedHashMap<String, byte[]>();
    
	public void format(ByteBuffer data) {
		if(data==null)
			return;
		
		
		while(true)  {
			//name 
			if(data.get()!=STX){
				data.clear();
				set(data);
				break;
			}
			int paramNamelength=0;
			int paramNamePosition = data.position();
			for(int i = paramNamePosition; i < data.limit(); i++){
				byte get = data.get();
				if(get == CTX){
					break;
				}else{
					paramNamePosition++;
				}
			}
			
			byte[] paramNamebyte = new byte[paramNamelength];
			data.position(paramNamePosition);
			data.get(paramNamebyte);
			
			
			
			if(data.get()!=CTX){ //ext빼기
				break;
			}
			
			
			
			// value
			if(data.get()!=STX){
				data.position(data.position()-1);
				set(data.slice());
				break;
			}
			int paramValuelength=0;
			int paramValuePosition = data.position();
			for(int i = paramValuePosition; i < data.limit(); i++){
				byte get = data.get();
				if(get == ETX){
					break;
				}else{
					paramValuelength++;
				}
			}
			byte[] paramValuebyte = new byte[paramValuelength];
			data.position(paramValuePosition);
			data.get(paramValuebyte);
			
			if(data.get()!=ETX){ //ext빼기
				break;
			}
			
			param.put(new String(paramNamebyte), paramValuebyte);
			
		}
		
		set(data.slice());
//		data.clear();
//		setSuccess(true);
		//System.arraycopy(data, int srcPos, Object dest, int destPos, int length)
	}

	
	
    @Override
    public ByteBuffer unformat() {
    	
    	
    	//size
    	int length =0;
    	Iterator<String> iter = param.keySet().iterator();
    	while(iter.hasNext()){
    		String key = iter.next();
    		byte[] value = param.get(key);
    		length+=(key!=null?key.getBytes().length:0)+(value!=null?value.length:0)+3; //stx + paramname + ctx + paramvalue + etx
    	}
		 ByteBuffer data = get();
		 if(data!=null){
		     length += data.capacity();
		 }
	 
	 
		 //dataSet
    	ByteBuffer unformatdata = ByteBuffer.allocateDirect(length);
		iter = param.keySet().iterator();
    	while(iter.hasNext()){
    		String key = iter.next();
    		byte[] value = param.get(key);
    		unformatdata.put(STX);
			if(key != null&&key.length()>0){
				unformatdata.put(key.getBytes());
			}
			unformatdata.put(CTX);
			if(value != null&&value.length>0){
				unformatdata.put(value);
			}
    		unformatdata.put(ETX);
    	}
    	
        if(data!=null){
        	unformatdata.put(data);
        }
    	
        unformatdata.clear();
        return unformatdata;
    }
    
    

	@Override
	public String toString() {
	    return "PARAM:"+getParam()+",get:"+get()+",success:"+isSuccess()+",DataLength:"+getLength();
	}



	private LinkedHashMap<String, byte[]> getParam(){
		return param;
	}

	private void setParam(LinkedHashMap<String, byte[]> param){
		this.param = param;
	}

    
}
