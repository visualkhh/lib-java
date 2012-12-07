package khh.communication.tcp.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.sun.mail.iap.ConnectionException;

import khh.communication.Communication_I;
import khh.communication.Connection_Interface;
import khh.communication.tcp.nio.worker.NioWorker;
import khh.debug.LogK;
import khh.std.adapter.Adapter_Base;
import khh.util.Util;

public class NioClient extends Thread implements Communication_I{
	private SocketChannel socketChannel	= null;
	private String serverAddr			= "127.0.0.1";
	private int	serverPort				= 9090;
	private int connectionTimeout	= 0;
	private Selector clientSelector		= null;
	private LogK log 					= LogK.getInstance();
	private NioWorker nioWorker         = null;
	public NioClient() throws IOException{
		init();
	}
	public NioClient(String serverAddr, int serverPort,int serverConnectionTimeout,NioWorker nioWorker) throws IOException{
		setServerAddr(serverAddr);
		setServerPort(serverPort);
		setConnectionTimeout(serverConnectionTimeout);
		setNioWorker(nioWorker);
		init();
	}
	public NioClient(String ServerAddr, int ServerPort,NioWorker nioWorker) throws IOException{
		setServerAddr(ServerAddr);
		setServerPort(ServerPort);
		setConnectionTimeout(0);
		setNioWorker(nioWorker);
		init();
	}
	public void init() throws IOException{
		clientSelector = Selector.open();		
	}
	public SocketChannel connection() throws IOException{
		InetSocketAddress	addr = new InetSocketAddress(getServerAddr(), getServerPort());
		SocketChannel socketChennel=null;
		if(getConnectionTimeout()>0){
			socketChennel = SocketChannel.open();
			socketChennel.socket().connect(addr,getConnectionTimeout());
		}else{
			socketChennel = SocketChannel.open(addr);
		}
		socketChennel.configureBlocking(false);
		log.debug(String.format("New Join Connection  %s   Port:%d   ConnectiomTimeOut:%d",getServerAddr(), getServerPort(),getConnectionTimeout()));
		setSocketChennel(socketChennel);
		socketChennel.register(clientSelector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		return socketChennel;
	}

	public NioWorker getNioWorker() {
        return nioWorker;
    }
    public void setNioWorker(NioWorker nioWorker) {
        this.nioWorker = nioWorker;
    }
    public boolean isConnected(){
		if ( socketChannel == null )
			return false;
		return socketChannel.isConnected() && socketChannel.socket().isConnected()  ;//&& socket.isOpen()&&socket.finishConnect();
	}
	@Override
	public void run(){
		try{
			if(isConnected()==false){
				throw new ConnectionException("notConnection");
			}
		}catch (ConnectionException e) {
            e.printStackTrace();
            return;
        }
		log.debug(String.format("NioClient(id:%d) Running...Thread Run", getId() ));
		while(true){
				try {
					if(clientSelector.select(3) > 0){
						Iterator<SelectionKey> it = clientSelector.selectedKeys().iterator();
						while (it.hasNext()){
							SelectionKey key=null;
							SocketChannel channel 	= null;
							try{
								Thread.sleep(1);
								key = it.next();
//								log.debug("NioClientWorker Type : "+getNioWorker().getFirestMode()+" Readble:"+key.isReadable()+" Writable:"+key.isWritable());
								if((getNioWorker().getFirestMode()==NioWorker.MODE_FIREST_R) && (key.isReadable()==false)){
									continue;//읽기부터인데 읽기가 활성화안되면 넘겨
								}else if((getNioWorker().getFirestMode()==NioWorker.MODE_FIREST_W) && (key.isWritable()==false)){
									continue;//쓰기인데 쓰기가 활성화안되어있으면 넘겨
								}else if((getNioWorker().getFirestMode()==NioWorker.MODE_FIREST_RW) &&(key.isReadable()==false || key.isWritable()==false)){
									continue;//일기쓰기 인데 둘다 안되어있으면 넘겨
								}
								
								if(key.isReadable() || key.isWritable()){	//ReadState SelectionKey  공유queue 에 input
									key.interestOps(0);
									channel = (SocketChannel) key.channel();
									if (channel == null){
										finish(key);
										continue;
									}
									getNioWorker().setSocketChannel(channel);
									getNioWorker().execute(key);
									finish(key);
								}
							}catch (Exception e){
								close();
								log.error(String.format("Worker!!!!  execute WorkerBusiness End [%03d]", getId(), getId()),e);
							}finally{
								if(key != null)
									finish(key);
							}
							it.remove();		//selectorkeys에 남은거지우기위해
						}
					}else{
					    Thread.sleep(2000);
					}
				} catch (Exception e) {
					//e.printStackTrace();
				}
		}
	}
	private void finish(SelectionKey key){
		if (key.isValid()) {
			key.interestOps( SelectionKey.OP_READ | SelectionKey.OP_WRITE );
		}
	}
	public void close(){
		try{
			if(socketChannel!=null)
				socketChannel.close();
		}catch (IOException e1){
		}
	}
	private void close(SocketChannel socket){
		try{
			if(socket!=null)
			socket.close();
		}catch (IOException e1){
		}
	}
	public SocketChannel getSocketChennel() throws IOException{
		if(isConnected()==false){
			socketChannel = connection();
		}
		return socketChannel;
	}
	public void setSocketChennel(SocketChannel socketChennel){
		this.socketChannel= socketChennel;
	}
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout){
		this.connectionTimeout = connectionTimeout;
	}
	public String getServerAddr() {
		return serverAddr;
	}
	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public void setFServerPort(int fServerPort) {
		serverPort = fServerPort;
	}
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}


	
}
