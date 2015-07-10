package khh.communication.tcp.nio.bypass;

public class NioByPassTest {

	public NioByPassTest() {
	}

	
	public static void main(String[] args) throws Exception {
		NioByPass server = new NioByPass(443,"192.168.60.22",443);
		server.start();
	}
}
