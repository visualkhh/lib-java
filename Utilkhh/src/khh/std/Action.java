package khh.std;

import java.util.Date;

import khh.std.realworld.Info;

public class Action<T> extends Info<T>{
	public static enum ACTIONSTATE{
		START,RETURN
	}
	private int id;
	public Action(){
		
	}
	public Action(String command){
		super(command);
	}
	public Action(String command,T source){
		super(command,source);
	}
	public Action(String command,T source,Date date){
		super(command,source,date);
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	
}