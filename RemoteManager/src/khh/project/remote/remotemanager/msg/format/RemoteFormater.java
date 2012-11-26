package khh.project.remote.remotemanager.msg.format;

import java.nio.ByteBuffer;

public abstract class RemoteFormater {
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
