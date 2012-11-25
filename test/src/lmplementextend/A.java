package lmplementextend;

public class A implements item
{

	@Override
	public void close()
	{
		System.err.println("A Close");
		
	}

	@Override
	public void get()
	{
		System.err.println("A Get");
		
	}

	@Override
	public void set()
	{
		System.err.println("A Set");
		
	}

}
