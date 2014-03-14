package khh.cast;

import java.nio.ByteBuffer;
import java.util.Date;

import khh.std.Standard;

public interface CastMap_I<K>{
	
//	public void set(T data);
//	public T get();

	public Integer getInt(K key) throws ClassCastException;

	public Double getDouble(K key) throws ClassCastException;

	public Float getFloat(K key) throws ClassCastException;

	public Short getShort(K key) throws ClassCastException;
	
	public String getString(K key) throws ClassCastException;

	public Boolean getBoolean(K key) throws ClassCastException;

	public Byte getByte(K key) throws ClassCastException;

	public ByteBuffer getByteBuffer(K key) throws ClassCastException;

	public byte[] getByteArray(K key) throws ClassCastException;

	public Standard getStandard(K key) throws ClassCastException;

	public Object getObject(K key) throws ClassCastException;
	public Date getDate(K key) throws ClassCastException;


}
