package khh.communication.tcp.nio.worker.msg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import khh.debug.LogK;
import khh.format.Formater;
import khh.std.Standard;
import khh.util.ByteUtil;

public class NioActionMsg extends Formater<ByteBuffer>{
	LogK log = LogK.getInstance();
	public static final byte[] STX 		= { (byte)0x01, (byte)0x09, (byte)0x08, (byte)0x06 };
	public static final byte[] ETX 		= { (byte)0x00, (byte)0x04, (byte)0x02, (byte)0x05 };
    public static final byte[] PARAM_STX= { (byte)0x60, (byte)0x12, (byte)0x27, (byte)0x25 };
    public static final byte[] PARAM_CTX= { (byte)0x65, (byte)0x92, (byte)0x08, (byte)0x40 };
    public static final byte[] PARAM_ETX= { (byte)0x62, (byte)0x42, (byte)0x58, (byte)0x60 };
    
	private int action = 0;
	private boolean success = false;
	
	   //int        부호 있는 정수    32 bits      -2147483648 ~ 2147483647
    //기본액션은             1000001 이상
    //서버액션은             2000001 이상
    //EXCEPTION액션은  3000001 이상
    //사용자정의액션은      50000001 이상
    //FromTo액션은         100000001 이상
    public static enum ACTION {
        POLL(1000001),
        ECHO(1000002),
        
        GET_SERVERTIME(2000001),//서버시간 yyyy/MM/dd HH:mm:ss/SSS
        GET_SERVERS(2000003),//멀티모니터에 등록된 서버정보요청 열려있는 서버이름s!   
        GET_CLIENTS(2000004),//서버에 붙어있는 클라이언트이름s!   
        
        NOSUCH_EXCEPTION(3000001),
        NOSUCH_FROMTARGET_EXCEPTION(3000002),
        NOSUCH_TOTARGET_EXCEPTION(3000003),
        EXCEPTION(3000005),
        
        FROMTO(100000001), //여기위에서부터는 다 FROMTO임..
        //LOGIN_LOGOUT(50002),
        //ADMIN_CLIENT_JOIN(50004)
        ;
        int value;
        ACTION(int id) {
            this.value = id;
        }
        public int getValue() {
            return this.value;
        }
    };
    
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
    
    
    public NioActionMsg() {
    }

    public NioActionMsg(int action) {
        setAction(action);
    }

    public NioActionMsg(int action, ByteBuffer data) {
        setAction(action);
        set(data);
    }
	
	

    public int getLength() {
        if(get()==null){
            return 0;
        }else{
            return get().limit()-get().position();
        }
    }
    

    public int getAction() {
        return action;
    }
    public void setAction(int action) {
        this.action = action;
    }
    
    
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    

    @Override
    public void format(ByteBuffer data) {
        
        if(data==null)
            return;
        
        
        while(true)  {
        	
        	
        	if(data.position()>= data.limit()){
            	break;
            }
        	
        	
            int startposition = data.position();
            //name 
            
            if( data.limit()>=4 && data.get(startposition+0)==PARAM_STX[0] && data.get(startposition+1)==PARAM_STX[1] && data.get(startposition+2)==PARAM_STX[2] && data.get(startposition+3)==PARAM_STX[3] ){
            	data.position(startposition+4); //param stx 빼고.
            }else{
            	data.position(startposition);
            	set(data.slice());
            	break;
            }

        	
            
            int paramNamelength=0;
            int paramNamePosition = data.position();
            for(int i = paramNamePosition; i < data.limit()-4; i++){ //param ctx는빼고..
                //byte get = data.get();
                if(data.get(i)==PARAM_CTX[0] && data.get(i+1)==PARAM_CTX[1] && data.get(i+2)==PARAM_CTX[2] && data.get(i+3)==PARAM_CTX[3]){
                    break;
                }else{
                    paramNamelength++;
                    data.position(i);//포지션 정하고 뒤쪽에 CXT남았고.
                }
            }
            //파라미터 이름넣고
            byte[] paramNamebyte=null;
            if(paramNamelength>0){
                paramNamebyte = new byte[paramNamelength];
                data.position(paramNamePosition);
                data.get(paramNamebyte);
            }
            //ctx check 다시한번 체크하고
            byte pctx0 = data.get();
            byte pctx1 = data.get();
            byte pctx2 = data.get();
            byte pctx3 = data.get();
            if(pctx0==PARAM_CTX[0] && pctx1==PARAM_CTX[1] && pctx2==PARAM_CTX[2] && pctx3==PARAM_CTX[3]){ //ext빼기
            }else{
            	data.position(startposition);
            	break;
            }
            
            
            
            // value
            int paramValuelength=0;
            int paramValuePosition = data.position();
            for(int i = paramValuePosition; i < data.limit()-4; i++){ //pram etx 빼고
                //byte get = data.get();
            	if(data.get(i)==PARAM_ETX[0] && data.get(i+1)==PARAM_ETX[1] && data.get(i+2)==PARAM_ETX[2] && data.get(i+3)==PARAM_ETX[3]){
            		break;
                }else{
                    paramValuelength++;
                    data.position(i);//포지션 정하고 뒤쪽에 EXT남았고.
                }
            }
            //값담고
            byte[] paramValuebyte=null;
            if(paramValuelength>0){
                paramValuebyte = new byte[paramValuelength];
                data.position(paramValuePosition);
                data.get(paramValuebyte);
            }
            //etx check 다시한번체크하고
            byte petx0 = data.get();
            byte petx1 = data.get();
            byte petx2 = data.get();
            byte petx3 = data.get();
            
            if(petx0==PARAM_ETX[0] && petx1==PARAM_ETX[1] && petx2==PARAM_ETX[2] && petx3==PARAM_ETX[3]){ //ext빼기
            }else{
            	data.position(startposition);
            	break;
            }
            
            
            if(paramNamebyte!=null && paramValuebyte!=null){
                param.put(new String(paramNamebyte), paramValuebyte);
            }else{
                data.position(startposition);
                //set(data.slice());
                break;
            }
            
            
            
            
            
        }
        
        
        
        set(data.slice());
        
        data.clear();
        
        
       /* 
        Iterator<String> iter = param.keySet().iterator();
        while(iter.hasNext()){
        	String key = iter.next();
        	log.debug("key : "+key+" , value :",(byte[])param.get(key));
        }
        log.debug("data",get());
        */
        
        
    }

    
    
    
    
    
    
    @Override
    public ByteBuffer unformat() {
        int length =0;
        int param_length=0;
        
        Iterator<String> iter = param.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            byte[] value = param.get(key);
          //4pstx + paramname + 4pctx + paramvalue + 4petx
            param_length += 4 + (key != null ? key.getBytes().length : 0) + 4 + (value != null ? value.length : 0) + 4; 
        }
        
        length += 4 + 4 + 4 + 4; //4 stx,  4 action, 4 length, 4 etx
        length+=param_length+getLength();   //paramlength + datalength
        
        
        ByteBuffer unformatdata = ByteBuffer.allocateDirect(length); //1 stx,  4 action, 4 length, 1 etx
        unformatdata.order(ByteOrder.BIG_ENDIAN);
        
        unformatdata.put(STX);
        unformatdata.putInt(getAction());
        unformatdata.putInt(param_length+getLength());
        
        
        
        iter = param.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            byte[] value = param.get(key);
            unformatdata.put(PARAM_STX);
            if(key != null&&key.length()>0){
                unformatdata.put(key.getBytes());
            }
            unformatdata.put(PARAM_CTX);
            if(value != null&&value.length>0){
                unformatdata.put(value);
            }
            unformatdata.put(PARAM_ETX);
        }
        
        
        ByteBuffer data = get();
        if(data!=null){
            unformatdata.put(data);
        }
        unformatdata.put(ETX);
        unformatdata.clear();
        return unformatdata;
    }
	
    
    public void clearParam(){
        param.clear();
    }
    public void clearData(){
        super.set(null);
    }
    
    public void clear(){
        clearParam();
        clearData();
        setAction(0);
        setSuccess(false);
    }
    public void set(String str){
    	set(ByteUtil.toByteBuffer(str));
    }
    public void set(byte[] bytearray){
    	set(ByteUtil.toByteBuffer(bytearray));
    }
    public void set(int i){
    	set(ByteUtil.toByteBuffer(i));
    }
    public void set(long i){
    	set(ByteUtil.toByteBuffer(i));
    }
    public void set(float i){
    	set(ByteUtil.toByteBuffer(i));
    }
    public void set(double i){
    	set(ByteUtil.toByteBuffer(i));
    }
    public void set(NioActionMsg format){
    	set(format.unformat());
    }
    
    
    
    @Override
    public byte[] getByteArray() throws ClassCastException {
    	if(get()!=null){
    		return ByteUtil.toByteArray(get());
    	}else{
    		return null;
    	}
    }

    @Override
    public ByteBuffer getByteBuffer() throws ClassCastException {
        return get();
    }

    @Override
    public String getString() throws ClassCastException {
    	if(getByteArray()!=null){
    		return new String(getByteArray());
    	}else{
    		return null;
    	}
    }

    @Override
    public Double getDouble() throws ClassCastException {
    	return get().getDouble();
    }

    @Override
    public Float getFloat() throws ClassCastException {
    	return get().getFloat();
    }

    @Override
    public Integer getInt() throws ClassCastException {
    	return get().getInt();
    }

    @Override
    public Object getObject() throws ClassCastException {
    	return get();
    }

    @Override
    public Standard getStandard() throws ClassCastException {
        throw new ClassCastException("Standard Float Cast");
    }

    @Override
    public Boolean getBoolean() throws ClassCastException {
        throw new ClassCastException("Boolean Disabled Cast");
    }

    @Override
    public Byte getByte() throws ClassCastException {
        throw new ClassCastException("Byte Disabled Cast");
    }

    @Override
    public Date getDate() throws ClassCastException {
        throw new ClassCastException("Date Disabled Cast");
    }

    public LinkedHashMap<String, byte[]> getParam() {
        return param;
    }

    public void setParam(LinkedHashMap<String, byte[]> param) {
        this.param = param;
    }

    public void putParam(String name, byte[] value) {
        this.param.put(name, value);
    }

    public void putParam(String name, String value) {
        this.param.put(name, value.getBytes());
    }

    public byte[] getParam(String name) {
        return this.param.get(name);
    }

    public String getParamString(String name) {
        byte[] data = this.param.get(name);
        if ( data != null ) {
            return new String(data);
        } else {
            return null;
        }
    }
    
    @Override
    public String toString(){
        return "action:"+getAction()+",PARAM:"+getParam()+",data:"+get()+",success:"+isSuccess()+",length:"+getLength();
    }
	
	
}
