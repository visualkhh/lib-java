package khh.communication.tcp.nio.relay.format;

import java.nio.ByteBuffer;
import java.util.Date;

import khh.format.Formater;
import khh.std.Standard;
import khh.util.ByteUtil;

public class FromToRelayFormater extends Formater<ByteBuffer> {
    public static final byte STX=(byte) 0x04;
    public static final byte ETX=(byte) 0x25;
    private String from = null;
    private String to = null;
	public void format(ByteBuffer data) {
		if(data==null)
			return;
		//from! stx str etx
		if(data!=null && data.get()!=STX){
			data.clear();
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
		data.get(tobyte);
		this.to = new String(tobyte);
		data.get(); //ext빼기
		set(data.slice());
		data.clear();
		//System.arraycopy(data, int srcPos, Object dest, int destPos, int length)
	}

	
	
    @Override
    public ByteBuffer unformat() {
        int length =0;
        
        
        String from = getFrom();
        String to = getTo();
        ByteBuffer data = get();
        if(from!=null){
            length += from.length()+1+1;
        }
        if(to!=null){
            length += to.length()+1+1;
        }
        if(data!=null){
            length += data.capacity();
        }
        
        ByteBuffer undata = ByteBuffer.allocate(length);
        
        if(from!=null){
            undata.put(STX);
            undata.put(from.getBytes());
            undata.put(ETX);
        }
        if(to!=null){
            undata.put(STX);
            undata.put(to.getBytes());
            undata.put(ETX);
        }
        if(data!=null){
            undata.put(data);
        }
        undata.clear();
        
        return undata;
    }
    
    
	
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public void setFrom(String from) {
        this.from = from;
    }

	public void setTo(String to) {
        this.to = to;
    }

	@Override
	public String toString() {
	    return "from:"+getFrom()+", to:"+getTo()+", get:"+get();
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
    
    @Override
    public Date getDate() throws ClassCastException{
        throw new ClassCastException("Date Disabled Cast");
    }




    
}
