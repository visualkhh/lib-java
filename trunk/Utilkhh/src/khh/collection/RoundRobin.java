package khh.collection;

import java.util.ArrayList;

 public  class RoundRobin<T> extends ArrayList<T>
{
	private int index=-1; //초기값 inde 0 이기때문에 
	private int jumpsize=1;
	
	public RoundRobin()
	{
	}
	
	public synchronized T getNext(){
		synchronized (this)
		{
				return get(getNextIndex());
		}
	}
	public synchronized  T getPrevious(){
		synchronized (this)
		{
			return get(getPreviousIndex());
		}
	}
	
	
	public synchronized  int getNextIndex(){
		synchronized (this)
		{
			index+=getJumpsize();
			if(index>=size()){
				index=0;
			}
			return index;
		}
	}
	public synchronized  int getPreviousIndex(){
		synchronized (this)
		{
			index-=getJumpsize();
			if(index<0){
				index=size()-1;
			}
			return index;
		}
	}
	
	public synchronized int getIndex(){
		synchronized (this)
		{
			return index;
		}
	}

	public synchronized int getJumpsize()
	{
		synchronized (this)
		{
			return jumpsize;
		}
	}

	public  synchronized void setJumpsize(int jumpsize)
	{
		synchronized (this)
		{
			this.jumpsize = jumpsize;
		}
	}
	
}
