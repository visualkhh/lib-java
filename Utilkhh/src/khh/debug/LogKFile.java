package khh.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class LogKFile extends File {

	private OutputStream out;
	public LogKFile(String pathname) throws FileNotFoundException {
		super(pathname);
		init();
	}

	public LogKFile(URI uri) throws FileNotFoundException {
		super(uri);
		init();
	}

	public LogKFile(String parent, String child) throws FileNotFoundException {
		super(parent, child);
		init();
	}

	public LogKFile(File parent, String child) throws FileNotFoundException {
		super(parent, child);
		init();
	}

	private void init() throws FileNotFoundException {
		out = new FileOutputStream(this, true);
	}
	
	public void log(String msg) throws IOException{
		log(msg.getBytes());
	}
	public void log(byte[] data) throws IOException{
		out.write(data);
		out.flush();
	}

	protected void finalize() throws Throwable {
		out.close();
		super.finalize();
	}
	
	
	public void flush() throws IOException{
		out.flush();
	}
	
	public void close() throws IOException{
		out.close();
	}
	
}
