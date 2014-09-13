package designpattern;
//데코레이터패턴


interface FileOut_I{
	public void write(byte[] data);
}

class FileOut implements FileOut_I{
	@Override
	public void write(byte[] data) {
		System.out.println("Last : "+new String(data));
	}
}





abstract class Decorator implements FileOut_I{
	private FileOut_I delegate;
	public Decorator(FileOut_I delegate){
		this.delegate = delegate;
	}
	
	protected void doDelegate(byte[] data){
		delegate.write(data);
	}
}


class Encryption extends Decorator{
	public Encryption(FileOut_I delegate) {
		super(delegate);
	}

	@Override
	public void write(byte[] data) {
		String s = new String(data)+"_Encryption";
		doDelegate(s.getBytes());
	}
}


class ZipOut extends Decorator{
	public ZipOut(FileOut_I delegate) {
		super(delegate);
	}

	@Override
	public void write(byte[] data) {
		String s = new String(data)+"_Zip";
		doDelegate(s.getBytes());
	}
}

class Mp3Out extends Decorator{
	public Mp3Out(FileOut_I delegate) {
		super(delegate);
	}

	@Override
	public void write(byte[] data) {
		String s = new String(data)+"_Mp3";
		doDelegate(s.getBytes());
	}
}


public class DecoratorPattern {
	public static void main(String[] args) {
		FileOut_I out = new Encryption(new ZipOut(new Mp3Out(new FileOut())));
		 out.write("Show".getBytes());
		 
		 out = new Mp3Out(new Encryption(new ZipOut(new FileOut())));
		 out.write("Money".getBytes());
	}
}
