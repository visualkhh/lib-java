package khh.hui.input;

import khh.hui.Hui;
import khh.hui.interfaces.HuiInterface;

/*
 * 입력을 대신한다
 */
public abstract class HuiInput<T> implements HuiInterface<T>{
	private Hui hui = null;
	public Hui getHui(){
		return hui;
	}

	public void setHui(Hui hui){
		this.hui = hui;
	}

}
