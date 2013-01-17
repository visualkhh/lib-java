package khh.hui.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import khh.std.Action;
import khh.std.realworld.Info;

public class HuiAction<T> extends Action<Vector<Info<T>>>{
//arrayList말고 백터쓰면 동기화됨
	Info<T> in = null;
	Info<T> out = null;
	
	public HuiAction(String command){
		super(command);
		init();
	}
	public HuiAction(String command,Vector<Info<T>> source){
		super(command,source);
		init();
	}
	public HuiAction(String command,Vector<Info<T>> source,Date date){
		super(command,source,date);
		init();
	}
	private void init(){
		setSource(new Vector<Info<T>>());
	}
	
	
	public void getInfo(Info<T> info){
		getSource().add(info);
	}
	
	public ArrayList<Info<T>> getInfo(String command){
		ArrayList<Info<T>> list  = null;
		Vector<Info<T>> container = getSource();
		for(int i = 0; i < container.size(); i++){
			Info<T> info = container.get(i);
			if(command.equals(info.getCommand()) || command == info.getCommand()){
				if(list==null){
					list = new ArrayList<Info<T>>();
				}
				list.add(container.get(i));
			}
		}
		return list;
	}
	
	public Info<T> getFirstInfo(){
		Vector<Info<T>> container = getSource();
		Info<T> info=null;
		if(container.size()>0){
			info = container.get(0);
		}
		return info;
	}
	
	public Info<T> getLastInfo(){
		Vector<Info<T>> container = getSource();
		Info<T> info=null;
		if(container.size()>0){
			info = container.get(container.size()-1);
		}
		return info;
	}

}
