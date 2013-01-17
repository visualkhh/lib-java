//package khh.hui.interfaces;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import khh.hui.action.HuiAction;
//
//public abstract class HuiBase<T> extends Thread implements HuiInterface<T>{
//	private BlockingQueue<HuiAction<T>> huiActionQueue 	= new LinkedBlockingQueue<HuiAction<T>>();
//	@Override
//	final public void run(){
//		try{
//			while(!Thread.currentThread().isInterrupted()){
//				sleep(10);
//				HuiAction<T> action = getHuiActionQueue().take();
//				//...
//				
//			}
//		}catch(InterruptedException e){
//			return;
//		}catch (Exception e) {
//		}
//	}
//	
//	
//	public BlockingQueue<HuiAction<T>> getHuiActionQueue(){
//		return huiActionQueue;
//	}
//
//
//	@Override
//	final public void huiAction(HuiAction<T> huiaction){
//		try{
//			getHuiActionQueue().put(huiaction);
//		}catch(InterruptedException e){
//			e.printStackTrace();
//		}
//		
//	}
//}
