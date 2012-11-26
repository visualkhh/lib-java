package khh.finger.remote.finmanager.msg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;

import khh.conversion.util.ConversionUtil;
import khh.util.ByteUtil;

public class FingMsg {
    public static final byte STX=(byte) 0x19;
    public static final byte ETX=(byte) 0x86;
    
	private int action;
	private ByteBuffer data = null;
	private boolean success = false;
	private SelectionKey selectionKey=null;
	
	public FingMsg() {
	}
	
	public FingMsg(int action){
		setAction(action);
	}
	public FingMsg(int action,ByteBuffer data){
		setAction(action);
		setData(data);
	}
	
	
	public ByteBuffer getData() {
		return data;
	}
	public byte[] getDataArray() {
		return ByteUtil.toByteArray(data);
	}
	public String getDataToStr() {
		return ConversionUtil.toString(data);
	}
	public void setData(ByteBuffer data) {
		this.data = data;
	}
	public int getLength() {
		if(getData()==null){
			return 0;
		}else{
			return getData().limit()-getData().position();
		}
	}

	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	
	
	public void setSelectionKey(SelectionKey selectionKey){
		this.selectionKey = selectionKey;
	}
	public SelectionKey getSelectionKey() {
		return selectionKey;
	}
	public ByteBuffer makeByteMsg(){
		ByteBuffer buff = ByteBuffer.allocate(getLength()+1+4+4+1); //1 stx,  4 action, 4 length, 1 etx
		ByteBuffer data = getData();
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
