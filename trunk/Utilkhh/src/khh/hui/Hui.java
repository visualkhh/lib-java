package khh.hui;

import khh.hui.action.HuiAction;
import khh.hui.part.flesh.HuiFlesh;
import khh.hui.part.input.HuiInput;
import khh.hui.part.memory.HuiMemory;
import khh.hui.part.output.HuiOutput;
import khh.hui.part.think.HuiThink;
import khh.std.realworld.Info;

public class Hui<T>{
	HuiFlesh	<HuiAction<T>>  huiflesh = null;
	HuiInput	<HuiAction<T>>  huiinput = null;
	HuiMemory	<String,Info,HuiAction<T>>  huimemory = null;
	HuiOutput	<HuiAction<T>>   huioutput = null;
	HuiThink 	<HuiAction<T>>  huithink = null;
	
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
