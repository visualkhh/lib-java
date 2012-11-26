package khh.finger.remote.finmanager.msg.format;

import java.nio.ByteBuffer;

public abstract class FingFormater {
//	ByteBuffer data =null;
//	public ByteBuffer getData() {
//		return data;
//	}
//	public void setData(ByteBuffer data) {
//		this.data = data;
//		format(this.data);
//	}
	abstract public void format(ByteBuffer data);
}
