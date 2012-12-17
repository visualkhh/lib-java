package queueTest;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {
	public static void main(String[] args) {
//		Queue<String> a =  new LinkedList<String>();
//		a.add("a1");
//		a.add("a2");
//		a.add("a3");
//		a.add("a4");
//		a.add("a5");
//		a.add("a6");
//		a.add("a7");
//		
//		 System.out.println( a.size());
//		
		 
		 khh.collection.Queue<String> k =  new khh.collection.Queue<String>();;
		 k.push("a1");
		 k.push("a2");
		 k.push("a3");
		 k.push("a4");
		 k.push("a5");
		
		 
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 k.push("a1");
		 k.push("a2");
		 k.push("a3");
		 k.push("a4");
		 k.push("a5");
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 System.out.println(k.pop());
		 
		 
		//Queue<String> ab = new  Vector<String>();
		
	}
}
