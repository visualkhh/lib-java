package nioActionTest.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import khh.communication.tcp.nio.worker.NioActionWorker;
import khh.communication.tcp.nio.worker.msg.NioActionMsg;
import khh.debug.LogK;
import khh.file.util.FileUtil;
import khh.image.ImageUtil;

public class ActionClient extends NioActionWorker {
	LogK log = LogK.getInstance();
	
	public ActionClient() {
		super(MODE_FIREST_W);
	}
	@Override
	public NioActionMsg onReceiveAction(NioActionMsg msg,SelectionKey selectionKey) throws Exception {
		
//		LinkedHashMap<String, byte[]> param = msg.getParam();
//		Iterator<String> iter = param.keySet().iterator();
//		while(iter.hasNext()){
//			String key = iter.next();
//			log.debug("key: "+key,param.get(key));
//			msg.putParam(key,new String(param.get(key))+key+new String(param.get(key))+new String(param.get(key))+new String(param.get(key))+new String(param.get(key))+new String(param.get(key))+new String(param.get(key)));
//		}
//		return msg;
		return null;
		
	}

	@Override
	public NioActionMsg onSendAction(NioActionMsg msg, SelectionKey selectionKey)throws Exception {
		//Thread.sleep(5000);
		
		if(msg==null){
			msg = new NioActionMsg(1);	
//			msg.putParam("a", "b");
//			msg.putParam("b", "c");
//			setFirestMode(MODE_FIREST_R);
		}
		
		BufferedImage buf = ImageUtil.getBuffreadImage(new File("c:\\23950902.jpg"));
//		FileUtil.writeFile(new File("c:\\23950902hh.jpg"), ImageUtil.toByteArray(buf, "jpg"));
		
		msg.putParam("img",ImageUtil.toByteArray(buf, "jpg"));
		
		
		msg.setSuccess(true);
		
		return msg;
	}

	@Override
	public void receiveTelegram(HashMap<String, Object> telegram,
			SelectionKey selectionKey) throws Exception {
		// TODO Auto-generated method stub

	}

}
