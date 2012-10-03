package khh.std;

import java.io.Serializable;

public class Standard<K,T> implements Serializable {
	K key;
	T value;
	public Standard() {
	}
	
	public Standard(K key, T value) {
		this.key = key;
		this.value = value;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public void set(K key, T value){
		this.key = key;
		this.value = value;
	}
}
