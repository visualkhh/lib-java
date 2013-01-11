package khh.hui.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import khh.std.Action;
import khh.std.realworld.Info;

public class HuiAction<T> extends Action<T>{
//arrayList말고 백터쓰면 동기화됨
	private Info startPoint; 
	private Info endPoint; 
	private HashMap<String,ArrayList<Info>> nodeBase=null;

	public HuiAction(String command){
		super(command);
	}
	public HuiAction(String command,T source){
		super(command,source);
	}
	public HuiAction(String command,T source,Date date){
		super(command,source,date);
	}
	
	
	
	public void addNodeHistory(String baseName,Info point){
		if(nodeBase==null){
			nodeBase = new HashMap<String, ArrayList<Info>>();
		}
		ArrayList<Info> nodeHistory = nodeBase.get(baseName);
		if(nodeHistory==null){
			nodeHistory = new ArrayList<Info>();
		}
		nodeHistory.add(point);
		nodeBase.put(baseName, nodeHistory);
	}

	public Info beforeTPoint(String baseName){
		if(nodeBase==null){
			return null;
		}
		ArrayList<Info> nodeHistory = nodeBase.get(baseName);
		if(nodeHistory==null || nodeHistory.size()<=0){
			return null;
		}
		return nodeHistory.get(nodeHistory.size()-1);
	}


}
