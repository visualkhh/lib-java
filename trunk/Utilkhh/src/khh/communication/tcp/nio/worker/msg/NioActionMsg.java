package khh.communication.tcp.nio.worker.msg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

import khh.format.Formater;
import khh.std.Standard;
import khh.util.ByteUtil;

public class NioActionMsg extends Formater<ByteBuffer>{
	public static final byte STX = (byte)0x19;
	public static final byte ETX = (byte)0x86;
	private int action = 0;
	private boolean success = false;
	
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
    	//read해서 그냥바로넣기때문에 여긴안쓴다.  아래검증안됨 쓰지마!
		if(data==null)
			return;
		//from! stx str etx
		if(data!=null && data.get()!=STX){
			data.clear();
			set(data);
			return;
		}
		setAction(data.getInt());
		int length = data.getInt();
		ByteBuffer datas =  ByteBuffer.allocateDirect(length);
		datas.put(datas);
		set(datas);
		if(data.get()!=ETX){
			setSuccess(false);
		}else{
			setSuccess(true);
		}
    }

    @Override
    public ByteBuffer unformat() {
        ByteBuffer buff = ByteBuffer.allocate(getLength() + 1 + 4 + 4 + 1); //1 stx,  4 action, 4 length, 1 etx
        ByteBuffer data = get();
        buff.order(ByteOrder.BIG_ENDIAN);
        
        buff.put(STX);
        buff.putInt(getAction());
        buff.putInt(getLength());
        if(data!=null){
            buff.put(data);
        }
        buff.put(ETX);
        
        buff.clear();
        return buff;
    }
	
    
    
    
    public void clear(){
    	super.set(null);
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

	    
    @Override
    public String toString(){
        return "action:"+getAction()+", data:"+get()+", success:"+isSuccess()+"  length:"+getLength();
    }
	
	
}
