package khh.std.realworld;

import java.nio.ByteBuffer;
import java.util.Date;

import khh.cast.Cast_I;
import khh.std.Standard;

public class Info<T> implements Cast_I{
	private String command;
	private T source;
	private Date date = new Date();
	public Info(){
	}
	public Info(String command){
		this.command=command;
	}
	public Info(String command,T source){
		this.command=command;
		this.source=source;
	}
	public Info(String command,T source,Date date){
		this.command=command;
		this.source=source;
		this.date=date;
	}
	
	
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public String getCommand(){
		return command;
	}
	public void setCommand(String command){
		this.command = command;
	}
	public T getSource(){
		return source;
	}
	public void setSource(T source){
		this.source = source;
	}
	@Override
	public Boolean getBoolean() throws ClassCastException{
		return getSource()!=null?(Boolean)getSource():null;
	}
	@Override
	public Byte getByte() throws ClassCastException{
		return getSource()!=null?(Byte)getSource():null;
	}
	@Override
	public byte[] getByteArray() throws ClassCastException{
		return getSource()!=null?(byte[])getSource():null;
	}
	@Override
	public ByteBuffer getByteBuffer() throws ClassCastException{
		return getSource()!=null?(ByteBuffer)getSource():null;
	}
	@Override
	public Double getDouble() throws ClassCastException{
		return getSource()!=null?(Double)getSource():null;
	}
	@Override
	public Float getFloat() throws ClassCastException{
		return getSource()!=null?(Float)getSource():null;
	}
	@Override
	public Integer getInt() throws ClassCastException{
		return getSource()!=null?(Integer)getSource():null;
	}
	@Override
	public Object getObject() throws ClassCastException{
		return getSource()!=null?(Object)getSource():null;
	}
	@Override
	public Short getShort() throws ClassCastException{
		return getSource()!=null?(Short)getSource():null;
	}
	@Override
	public Standard getStandard() throws ClassCastException{
		return getSource()!=null?(Standard)getSource():null;
	}
	@Override
	public String getString() throws ClassCastException{
		return getSource()!=null?(String)getSource():null;
	}
	
}
