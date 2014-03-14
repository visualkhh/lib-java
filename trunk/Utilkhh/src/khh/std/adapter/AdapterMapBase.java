package khh.std.adapter;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import khh.cast.CastMap_I;
import khh.std.Standard;


public abstract class AdapterMapBase<K,T>  implements CastMap_I<K>,Serializable {
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
	final public void add(HashMap<K,T> map) throws Exception{
		Set<K> keySet = map.keySet();
		Iterator<K> it = keySet.iterator();
		while(it.hasNext()){
			K key = it.next();
			T value = map.get(key);
			add(key,value);
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
	public Integer getInt(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (Integer)value;
	}
	public Integer getInt(int index) throws ClassCastException{
		return getInt(getKey(index));
	}
	
	
	public Double getDouble(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (Double)value;
	}
	
	public Double getDouble(int index) throws ClassCastException{
		return getDouble(getKey(index));
	}
	
	public Float getFloat(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (Float)value;
	}
	public Float getFloat(int index) throws ClassCastException{
		return getFloat(getKey(index));
	}
	
	public Short getShort(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(value==null)
			return null;
		return (Short)value;
	}
	public Short getShort(int index) throws ClassCastException{
		return getShort(getKey(index));
	}
	
	public String getString(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (String)value;
	}
	public String getString(int index) throws ClassCastException{
		return getString(getKey(index));
	}
	public Boolean getBoolean(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (Boolean)value;
	}
	public Boolean getBoolean(int index) throws ClassCastException{
		return getBoolean(getKey(index));
	}
	
	public Byte getByte(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (Byte)value;
	}
	public Byte getByte(int index) throws ClassCastException{
		return getByte(getKey(index));
	}
	
	public ByteBuffer getByteBuffer(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(value==null)
           return null;
		return (ByteBuffer)value;
	}
	public ByteBuffer getByteBuffer(int index) throws ClassCastException{
		return getByteBuffer(getKey(index));
	}
	
	public byte[] getByteArray(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(value==null)
			return null;
		return (byte[])value;
	}
	public byte[] getByteArray(int index) throws ClassCastException{
		return getByteArray(getKey(index));
	}
	public Standard getStandard(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(value==null)
			return null;
		return (Standard)value;
	}
	public Standard getStandard(int index) throws ClassCastException{
		return getStandard(getKey(index));
	}
	
	
	public Object getObject(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(value==null)
			return null;
		return (Object)value;
	}
	public Object getObject(int index) throws ClassCastException{
		return getObject(getKey(index));
	}
	
	
	public Date getDate(K key) throws ClassCastException{
		T value = null;
		try {
			value = get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(value==null)
			return null;
		return (Date)value;
	}
	public Date getDate(int index) throws ClassCastException{
		return getDate(getKey(index));
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
