package khh.hui.interfaces;

import khh.hui.action.HuiAction;

public interface HuiInterface<T>{
	public void huiAction(HuiAction<T> action);
//	public void huiAction(int action, Action<T> data);
}
