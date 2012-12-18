package khh.collection;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Queue<T>{
	private java.util.Queue<T> queue = null;

	public Queue(){
		synchronized(this){
			// queue =new LinkedList<T>();
			queue = new PriorityQueue<T>();
		}

	}

	synchronized public boolean push(T item){
		synchronized(queue){
			return queue.offer(item);
		}
	}

	synchronized public T pop() throws NoSuchElementException{
		synchronized(queue){
			T item = queue.element();
			queue.poll();
			return item;
		}

	}

	synchronized public int size(){
		return queue.size();
	}

	@Override
	public String toString(){
		return queue.toString();
	}

}
