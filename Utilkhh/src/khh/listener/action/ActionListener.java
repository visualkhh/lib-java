package khh.listener.action;

import khh.std.Action;

public abstract class ActionListener<T>{
	long eventid = 0;
	String eventname;

	public ActionListener(){

	}

	public ActionListener(long id){
		this(id,null);
	}
	public ActionListener(String name){
		this(0,null);
	}
	public ActionListener(long id, String name){
		this.eventid = id;
		this.eventname = name;
	}


	public long getEventid(){
		return eventid;
	}

	public void setEventid(long eventid){
		this.eventid = eventid;
	}

	public String getEventname(){
		return eventname;
	}

	public void setEventname(String eventname){
		this.eventname = eventname;
	}

	abstract public void actionPerformed(Action<T> event);
}
