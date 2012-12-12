package khh.cast;

import java.nio.ByteBuffer;
import java.util.Date;

import khh.std.Standard;


public class CastBase<T> implements Cast_I{
	private T data = null;

	final public void set(T data){
		this.data = data;
	}

	final public T get(){
		return this.data;
	}

    @Override
    public Boolean getBoolean() throws ClassCastException {
       return (Boolean) get();
    }

    @Override
    public Byte getByte() throws ClassCastException {
        return (Byte)get();
    }

    @Override
    public byte[] getByteArray() throws ClassCastException {
        return (byte[])get();
    }

    @Override
    public ByteBuffer getByteBuffer() throws ClassCastException {
        return (ByteBuffer)get();
    }

    @Override
    public Double getDouble() throws ClassCastException {
        return (Double)get();
    }

    @Override
    public Float getFloat() throws ClassCastException {
        return (Float)get();
    }

    @Override
    public Integer getInt() throws ClassCastException {
        return (Integer)get();
    }

    @Override
    public Object getObject() throws ClassCastException {
        return (Object)get();
    }

    @Override
    public Standard getStandard() throws ClassCastException {
        return (Standard)get();
    }

    @Override
    public String getString() throws ClassCastException {
        return (String)get();
    }
    
    @Override
    public Date getDate() throws ClassCastException {
        return (Date)get();
    }

}
