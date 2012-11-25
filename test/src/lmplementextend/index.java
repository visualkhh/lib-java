package lmplementextend;

class hhk
{
	public hhk()
	{
		System.out.println("HHK");
	}

	public item getitem()
	{
		return new A();
	}
}

public class index
{
	public static void main(String[] args)
	{
		hhk h=new hhk();
		
		B b =new B();
		
		item t =  h.getitem();
		t.set();

	}
}
