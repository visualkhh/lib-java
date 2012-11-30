package khh.format;

import khh.cast.CastBase;


abstract public class  Formater<T> extends CastBase<T> {
	
	public abstract void format(T data);

}
