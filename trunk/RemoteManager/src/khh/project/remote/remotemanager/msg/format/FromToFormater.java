package khh.project.remote.remotemanager.msg.format;

import java.nio.ByteBuffer;

import khh.debug.LogK;
import khh.format.Formater;
import khh.std.Standard;
import khh.util.*;

public class FromToFormater extends Formater<ByteBuffer> {
    public static final byte STX=(byte) 0x04;
    public static final byte ETX=(byte) 0x25;
    
    private String from = null;
    private String to = null;
    private LogK log = LogK.getInstance();
    
	@Override
	public void format(ByteBuffer data) {
		
		//from! stx str etx
		if(data!=null && data.get()!=STX){
			data.reset();
			set(data);
			return;
		}
		int fromlength=0;
		int fromstartposition = data.position();
		for (int i = fromstartposition; i < data.limit(); i++) {
			byte get = data.get();
			if(get==ETX){
				break;
			}else{
				fromlength++;
			}
		}
		byte[] frombyte = new byte[fromlength];
		data.position(fromstartposition);
		data.get(frombyte);
		this.from = new String(frombyte);
		data.get(); //ext빼기
		
		
		
		//to! stx str etx
		if(data!=null && data.get()!=STX){
			set(data.slice());
			return;
		}
		int tolength=0;
		int tostartposition = data.position();
		for (int i = tostartposition; i < data.limit(); i++) {
			byte get = data.get();
			if(get==ETX){
				break;
			}else{
				tolength++;
			}
		}
		byte[] tobyte = new byte[tolength];
		data.position(tostartposition); //stx 빼기
		data.get(tolength);
		this.from = new String(tobyte);
		data.get(); //ext빼기
		set(data.slice());
		//System.arraycopy(data, int srcPos, Object dest, int destPos, int length)
	}

	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}

	




	@Override
	public byte[] getByteArray() throws ClassCastException{
		return ByteUtil.toByteArray(get());
	}

	@Override
	public ByteBuffer getByteBuffer() throws ClassCastException{
		return get();
	}
	@Override
	public String getString() throws ClassCastException{
		return new String(getByteArray());
	}
	
	
	@Override
	public Double getDouble() throws ClassCastException{
		throw new ClassCastException("Double Disabled Cast");
	}

	@Override
	public Float getFloat() throws ClassCastException{
		throw new ClassCastException("Double Float Cast");
	}

	@Override
	public Integer getInt() throws ClassCastException{
		throw new ClassCastException("Integer Float Cast");
	}

	@Override
	public Object getObject() throws ClassCastException{
		throw new ClassCastException("Object Float Cast");
	}

	@Override
	public Standard getStandard() throws ClassCastException{
		throw new ClassCastException("Standard Float Cast");
	}
	@Override
	public Boolean getBoolean() throws ClassCastException{
		throw new ClassCastException("Boolean Disabled Cast");
	}

	@Override
	public Byte getByte() throws ClassCastException{
		throw new ClassCastException("Byte Disabled Cast");
	}
    
}
