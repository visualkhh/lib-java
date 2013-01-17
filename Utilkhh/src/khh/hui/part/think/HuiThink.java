package khh.hui.part.think;

import java.util.HashMap;
import java.util.LinkedHashMap;

import khh.hui.Hui;
import khh.hui.action.HuiAction;
import khh.hui.interfaces.HuiInterface;

/*
 * 생각을 대신한다
 */
public abstract class HuiThink<T> extends HuiMemory<T> implements HuiInterface<T>{
	private Hui hui = null;
	
	public HuiThink(){
	}
	public Hui getHui(){
		return hui;
	} 
	public void setHui(Hui hui){
		this.hui = hui;
	}

}
