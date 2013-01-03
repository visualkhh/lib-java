package khh.hui.memory;

import java.util.Hashtable;

import khh.hui.Hui;
import khh.hui.interfaces.HuiInterface;
/*
 * 기억을 대신한다
 */
public abstract class HuiMemory<K,V,T> extends Hashtable<K, V> implements HuiInterface<T>{
	private Hui hui = null;
	public Hui getHui(){
		return hui;
	}

	public void setHui(Hui hui){
		this.hui = hui;
	}

}
