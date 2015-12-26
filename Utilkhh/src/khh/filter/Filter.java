package khh.filter;

@FunctionalInterface
public interface Filter<T>{
	public boolean test(T arg0);
}
