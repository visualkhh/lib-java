package khh.std;

public class Action<T>{
	private T source;
	private int id;
	private String actionCommand;
	private long when;

	public Action(T source, int id, String actionCommand){
		this(source, id, actionCommand, 0);
	}

	public Action(T source, int id, String actionCommand, long when){
		this.source=source;
		this.id=id;
		this.actionCommand=actionCommand;
		this.when=when;
	}

	public T getSource(){
		return source;
	}

	public void setSource(T source){
		this.source = source;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}


	public String getActionCommand(){
		return actionCommand;
	}

	public void setActionCommand(String actionCommand){
		this.actionCommand = actionCommand;
	}

	public long getWhen(){
		return when;
	}

	public void setWhen(long when){
		this.when = when;
	}

	
	
}