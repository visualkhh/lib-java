package khh.std.realworld;

import java.util.Date;

public class Info<T>{
	private String command;
	private T source;
	private Date date;
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
	
}
