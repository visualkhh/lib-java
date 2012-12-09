package khh.communication.tcp.nio.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

import khh.format.Formater;
import khh.std.Standard;
import khh.util.ByteUtil;

public class NioMsg extends Formater<ByteBuffer>{
	public static final byte STX = (byte)0x19;
	public static final byte ETX = (byte)0x86;
	private int action = 0;
	private boolean success = false;
	
    public NioMsg() {
    }

    public NioMsg(int action) {
        setAction(action);
    }

    public NioMsg(int action, ByteBuffer data) {
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
    public String toString(){
        return "action:"+getAction()+", data:"+get()+", success:"+isSuccess();
    }

    @Override
    public void format(ByteBuffer data) {
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
	
    @Override
    public byte[] getByteArray() throws ClassCastException {
        return ByteUtil.toByteArray(get());
    }

    @Override
    public ByteBuffer getByteBuffer() throws ClassCastException {
        return get();
    }

    @Override
    public String getString() throws ClassCastException {
        return new String(getByteArray());
    }

    @Override
    public Double getDouble() throws ClassCastException {
        throw new ClassCastException("Double Disabled Cast");
    }

    @Override
    public Float getFloat() throws ClassCastException {
        throw new ClassCastException("Double Float Cast");
    }

    @Override
    public Integer getInt() throws ClassCastException {
        throw new ClassCastException("Integer Float Cast");
    }

    @Override
    public Object getObject() throws ClassCastException {
        throw new ClassCastException("Object Float Cast");
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

	    
	    
	
	
}
