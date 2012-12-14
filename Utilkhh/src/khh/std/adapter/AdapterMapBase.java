package khh.std.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import khh.std.Standard;


public abstract class AdapterMapBase<K,T>  implements Serializable {
	private ArrayList<Standard<K,T>> container = null;
	
	public AdapterMapBase() {
		container = new ArrayList<Standard<K,T>>();
	}
	
	
	public void isAddValidate(K key , T value) throws Exception {
	}
	public void isGetValidate(T returnval)throws Exception {
	}
	
	final public void add(K key,T value)  throws Exception {
	    synchronized (container) {
        		isAddValidate(key,value);
        		int i = getIndex(key);
        		if(i<0){		
        			container.add(new Standard(key,value));
        		}else{
        //			container.add(new Standard(key,value));
        			set(i,key,value);
        		}
	    }
	}
	final public void add(int index, K key,T value) throws Exception{
	    synchronized (container) {
    		isAddValidate(key,value);
    		int i = getIndex(key);
    		if(i<0){		
    			container.add(index,new Standard(key,value));
    		}else{
    			throw new Exception("duplicate add Object index("+i+")");
    		}
	    }
	}
	
	final public void set(int index, K key,T value)throws Exception{
	    synchronized (container) {
    		isAddValidate(key,value);
    		int i = getIndex(key);
    		if(i<0){		
    			container.set(index,new Standard(key,value));
    		}else{
    			throw new Exception("duplicate set Object index->("+i+")");
    		}
	    }
	}
	final public void set(K key,T value)throws Exception{
	    synchronized (container) {
    		isAddValidate(key,value);
    		int i = getIndex(key);
    		if(i<0){		
    			add(key,value);
    		}else{
    			container.get(i).setValue(value);
    		}
	    }
	}

	
	

	
	
	
	
	final public void clear(){
	    synchronized (container) {
	        container.clear();
	    }
	}
	
	
	
	
	final public int getIndex(K key)
	{
	    synchronized (container) {
    		for(int  i = 0 ; i < size();i++){
    			if(getKey(i)==key || getKey(i).equals(key)){
    				return i;
    			}
    		}
    		return -1;//(T)get(key).g;
	    }
	}
	final public K getKey(int index){
	    synchronized (container) {
	        return container.get(index).getKey();	
	    }
	}
	final public int size(){
	    synchronized (container) {
	        return container.size();
	    }
	}
	
	
	//////////
	
	final public T get(K key) throws Exception{
	    synchronized (container) {
    		T returnval=null;
    		int index = getIndex(key);
    		if(index<0){
    			returnval=null;
    //			throw new NullPointerException("null Point  key="+key.toString());
    		}else{
    			returnval = get(index);
    		}
    		isGetValidate(returnval);
    		return returnval;
	    }
		
	}
	final  public T get(int index) throws Exception{
	    synchronized (container) {
    		T returnval=null;
    		returnval = container.get(index).getValue();	
    		isGetValidate(returnval);
    		return returnval;
	    }
	}
	////
	
	
	
	
	
	
	final public void remove(K key)  throws Exception {
	    synchronized (container) {
    		int i = getIndex(key);
    		if(i<0){		
    		}else{
    			container.remove(i);
    		}
	    }
	}
	final public void remove(int index) throws Exception{
	    synchronized (container) {
	        container.remove(index);
	    }
	}
	
	
	
	
	
	
	
	///////conversion// int - >integer duble , ...등등 리턴값  kdn에서 바꿈
	final public Integer getInt(K key) throws Exception{
		return conversionInt(get(key));
	}
	final public Integer getInt(int index) throws Exception{
		return conversionInt(get(index));
	}
	public Integer conversionInt(T value) throws Exception{
	    if(value==null)
            return null;
		return (Integer)value;
	}
	
	final public Double getDouble(K key) throws Exception{
		return conversionDouble(get(key));
	}
	final public Double getDouble(int index) throws Exception{
		return conversionDouble(get(index));
	}
	public Double conversionDouble(T value) throws Exception{
	    if(value==null)
            return null;
		return (Double)value;
	}
	
	
	final public Float getFloat(K key) throws Exception{
		return conversionFloat(get(key));
	}
	final public Float getFloat(int index) throws Exception{
		return conversionFloat(get(index));
	}
	public Float conversionFloat(T value) throws Exception{
	    if(value==null)
            return null;
		return (Float)value;
	}
	
	
	final public String getString(K key) throws Exception{
		return conversionString(get(key));
	}
	final public String getString(int index) throws Exception{
		return conversionString(get(index));
	}
	public String conversionString(T value) throws Exception{
	    if(value==null)
            return null;
		return (String)value;
	}
	
	final public Boolean getBoolean(K key) throws Exception{
		return conversionBoolean(get(key));
	}
	final public Boolean getBoolean(int index) throws Exception{
		return conversionBoolean(get(index));
	}
	public Boolean conversionBoolean(T value) throws Exception{
	    if(value==null)
	        return null;
		return (Boolean)value;
	}
	
	//////////
	public  ArrayList<Standard<K,T>> getList(){
		return container;
	}
	
//	@Override
//	public String toString() {
//	    
//	    return size()+"   Key ";
//	}
//	//////////
//	abstract  public T getMin();
//	abstract  public T getMax();
//	abstract  public T getAvg();
//	abstract  public T getSum();
//	abstract  public T getAlgorithm();
}
