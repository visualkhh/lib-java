package khh.hui;

import khh.hui.flesh.HuiFlesh;
import khh.hui.input.HuiInput;
import khh.hui.memory.HuiMemory;
import khh.hui.output.HuiOutput;
import khh.hui.think.HuiThink;

public class Hui{
	HuiFlesh huiflesh = null;
	HuiInput huiinput = null;
	HuiMemory huimemory = null;
	HuiOutput huioutput = null;
	HuiThink huithink = null;
	
	public Hui(){
	}
	public Hui(HuiFlesh huiflesh,HuiInput huiinput,HuiMemory huimemory,HuiOutput huioutput,HuiThink huithink){
		this.huiflesh=huiflesh;
		this.huiinput=huiinput;
		this.huimemory=huimemory;
		this.huioutput=huioutput;
		this.huithink=huithink;
	}
	public HuiFlesh getHuiflesh(){
		return huiflesh;
	}
	public void setHuiflesh(HuiFlesh huiflesh){
		this.huiflesh = huiflesh;
	}
	public HuiInput getHuiinput(){
		return huiinput;
	}
	public void setHuiinput(HuiInput huiinput){
		this.huiinput = huiinput;
	}
	public HuiMemory getHuimemory(){
		return huimemory;
	}
	public void setHuimemory(HuiMemory huimemory){
		this.huimemory = huimemory;
	}
	public HuiOutput getHuioutput(){
		return huioutput;
	}
	public void setHuioutput(HuiOutput huioutput){
		this.huioutput = huioutput;
	}
	public HuiThink getHuithink(){
		return huithink;
	}
	public void setHuithink(HuiThink huithink){
		this.huithink = huithink;
	}
	
	
	
	
	
}
