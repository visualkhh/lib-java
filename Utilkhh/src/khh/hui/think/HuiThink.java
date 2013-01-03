package khh.hui.think;

import khh.hui.Hui;
import khh.hui.interfaces.HuiInterface;

/*
 * 생각을 대신한다
 */
public abstract class HuiThink<T> implements HuiInterface<T>{
	private Hui hui = null;
	public Hui getHui(){
		return hui;
	}

	public void setHui(Hui hui){
		this.hui = hui;
	}

}
