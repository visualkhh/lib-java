package khh.filter;

@FunctionalInterface
public interface FilterCheckPair<T,K> {
	public boolean test(T arg1, K arg2);
}
