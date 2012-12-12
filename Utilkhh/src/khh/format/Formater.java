package khh.format;

import java.nio.ByteBuffer;

import khh.cast.CastBase;

abstract public class Formater<T> extends CastBase<T>{

	public abstract void format(T data);

	public abstract T unformat();
}
