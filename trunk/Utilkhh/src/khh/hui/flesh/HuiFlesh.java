package khh.hui.flesh;

import java.awt.GraphicsConfiguration;

import khh.gui.component.frame.FrameFrame;
import khh.hui.Hui;
import khh.hui.interfaces.HuiInterface;
/*
 * 육체를. 대신한다.
 */
public abstract class HuiFlesh<T> extends FrameFrame implements HuiInterface<T>{
	private Hui hui = null;
	public HuiFlesh(){
		super();
	}

	public HuiFlesh(GraphicsConfiguration gc){
		super(gc);
	}

	public HuiFlesh(String title){
		super(title);
	}

	public Hui getHui(){
		return hui;
	}

	public void setHui(Hui hui){
		this.hui = hui;
	}



}
