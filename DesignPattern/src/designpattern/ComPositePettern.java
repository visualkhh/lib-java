package designpattern;

import java.util.ArrayList;

interface ComObject_I{
	public void run(String state);
}

class MailCom implements ComObject_I{
	@Override
	public void run(String state) {
		System.out.println("MAIL ComPosite");
	}
	
}
class SMSCom implements ComObject_I{
	@Override
	public void run(String state) {
		System.out.println("SMS ComPosite");
	}
	
}



class ComGroup implements ComObject_I{
	ArrayList<ComObject_I> list = new ArrayList<ComObject_I>();
	
	public void add(ComObject_I o){
		list.add(o);
	}
	
	public void remove(ComObject_I o){
		list.remove(o);
	}
	
	
	@Override
	public void run(String state) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).run(state);
		}		
	}
}

public class ComPositePettern {
	public static void main(String[] args) {
		ComObject_I mail = new MailCom();
		ComObject_I sms = new SMSCom();
		
		ComGroup ob = new ComGroup();
		ob.add(mail);
		ob.add(sms);
		ob.run("gg");
	}
}
