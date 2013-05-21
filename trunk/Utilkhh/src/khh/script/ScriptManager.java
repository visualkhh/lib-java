package khh.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptManager extends ScriptEngineManager{
//	ScriptEngineManager manager = new ScriptEngineManager(); 
//	ScriptEngine engine = manager.getEngineByName("js"); 
//	Object result = engine.eval("4*5"); 
	public ScriptEngine getJavaScriptEngin(){
		return getEngineByName("js"); 
	}
}
