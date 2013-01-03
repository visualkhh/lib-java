package khh.hui.interfaces;

import khh.std.Action;

public interface HuiInterface<T>{
	public void huiAction(int action, Action<T> data);
}
