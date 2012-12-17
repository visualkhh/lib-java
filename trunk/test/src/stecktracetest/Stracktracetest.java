package stecktracetest;

public class Stracktracetest {
	
	
	public void a(){
		try {
			b();
		} catch (Exception e) {
				java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
				java.io.PrintWriter writer = new java.io.PrintWriter(bos);
				e.printStackTrace(writer);
				writer.flush();
				System.out.println(bos.toString());
				System.out.println("-----------");
				e.printStackTrace();
		}
	}

	
	public void b(){
		
		c();
	}
	
	public void c(){
		int a = 1/0;
	}
	
	
	public static void main(String[] args) {
		Stracktracetest s = new Stracktracetest()
		;
		
		s.a();
	}
}
