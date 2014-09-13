package designpattern;

import java.util.ArrayList;

interface ObserVerObject_I{
	public void run(String state);
}

class MailObserver implements ObserVerObject_I{
	@Override
	public void run(String state) {
		System.out.println("MAIL Observer");
	}
	
}
class SMSObserver implements ObserVerObject_I{
	@Override
	public void run(String state) {
		System.out.println("SMS Observer");
	}
	
}



class Observer{
	ArrayList<ObserVerObject_I> list = new ArrayList<ObserVerObject_I>();
	
	public void add(ObserVerObject_I o){
		list.add(o);
	}
	
	public void remove(ObserVerObject_I o){
		list.remove(o);
	}
	
	
	public void changeState(String state){
		for (int i = 0; i < list.size(); i++) {
			list.get(i).run(state);
		}
	}
}

public class ObserverPettern {
	public static void main(String[] args) {
		ObserVerObject_I mail = new MailObserver();
		ObserVerObject_I sms = new SMSObserver();
		
		Observer ob = new Observer();
		ob.add(mail);
		ob.add(sms);
		ob.changeState("gg");
	}
}
