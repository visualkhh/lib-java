package khh.finger.remote.finmanager.msg.format;

import java.nio.ByteBuffer;

import khh.debug.LogK;

public class FingTitleFormater extends FingFormater {
    public static final byte TITLE_STX=(byte) 0x04;
    public static final byte TITLE_ETX=(byte) 0x25;
    
    private String title;
    private ByteBuffer data;
    private LogK log = LogK.getInstance();
    
	@Override
	public void format(ByteBuffer data) {
		if(data!=null && data.get()!=TITLE_STX){
			return;
		}
		
		int titlelength=0;
		for (int i = data.position(); i < data.limit(); i++) {
			byte get = data.get();
			if(get==TITLE_ETX){
				break;
			}else{
				titlelength++;
			}
		}
		
		byte[] titlebyte = new byte[titlelength];
		data.position(1); //stx 빼기
		data.get(titlebyte);
		this.title = new String(titlebyte);
		data.get(); //ext빼기
		if(this.title!=null&& this.title.length()>0){
			setData(data.slice());
		}
		//System.arraycopy(data, int srcPos, Object dest, int destPos, int length)
	}

	public String getTitle() {
		return title;
	}
	public ByteBuffer getData() {
		return data;
	}
	public void setData(ByteBuffer data) {
		this.data = data;
		log.debug("format Data Set",this.data);
	}
	
    
}
