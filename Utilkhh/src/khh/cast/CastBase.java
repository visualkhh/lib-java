package khh.cast;


abstract public class CastBase<T> implements Cast_I<T>{
	private T data = null;

	public void set(T data){
		this.data = data;
	}

	public T get(){
		return this.data;
	}

}
