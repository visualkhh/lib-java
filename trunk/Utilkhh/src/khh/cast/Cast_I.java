package khh.cast;

import java.nio.ByteBuffer;
import java.util.Date;

import khh.std.Standard;

public interface Cast_I{
	
//	public void set(T data);
//	public T get();

	public Integer getInt() throws ClassCastException;

	public Double getDouble() throws ClassCastException;

	public Float getFloat() throws ClassCastException;

	public String getString() throws ClassCastException;

	public Boolean getBoolean() throws ClassCastException;

	public Byte getByte() throws ClassCastException;

	public ByteBuffer getByteBuffer() throws ClassCastException;

	public byte[] getByteArray() throws ClassCastException;

	public Standard getStandard() throws ClassCastException;

	public Object getObject() throws ClassCastException;
	public Date getDate() throws ClassCastException;

}
