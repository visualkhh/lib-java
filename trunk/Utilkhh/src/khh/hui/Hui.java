package khh.hui;

import khh.hui.part.flesh.HuiFlesh;
import khh.hui.part.input.HuiInput;
import khh.hui.part.output.HuiOutput;
import khh.hui.part.think.HuiThink;

public class Hui<T>{
	HuiFlesh	<T>  huiflesh 	= null;
	HuiInput	<T>  huiinput 	= null;
	HuiOutput	<T>  huioutput 	= null;
	HuiThink 	<T>  huithink 	= null;
	
	public Hui(){
	}
	public Hui(HuiFlesh<T> huiflesh,HuiInput<T> huiinput,HuiOutput<T> huioutput,HuiThink<T> huithink){
		this.huiflesh		=	huiflesh;
		this.huiinput		=	huiinput;
		this.huioutput		=	huioutput;
		this.huithink		=	huithink;
		
		this.huiflesh	.setHui(this);		
		this.huiinput	.setHui(this);	
		this.huioutput	.setHui(this);	
		this.huithink	.setHui(this);	
		
	}
	public HuiFlesh<T> getHuiflesh(){
		return huiflesh;
	}
	public void setHuiflesh(HuiFlesh<T> huiflesh){
		this.huiflesh 	= huiflesh;
		this.huiflesh	.setHui(this);	
	}
	public HuiInput<T> getHuiinput(){
		return huiinput;
	}
	public void setHuiinput(HuiInput<T> huiinput){
		this.huiinput 	= huiinput;
		this.huiinput	.setHui(this);	
	}
	public HuiOutput<T> getHuioutput(){
		return huioutput;
	}
	public void setHuioutput(HuiOutput<T> huioutput){
		this.huioutput 	= huioutput;
		this.huioutput	.setHui(this);	
	}
	public HuiThink<T> getHuithink(){
		return huithink;
	}
	public void setHuithink(HuiThink<T> huithink){
		this.huithink 	= huithink;
		this.huithink	.setHui(this);	
	}

	
	
	
	
	
}
