package ScriptEngineManagerTest;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineManagerTest{
public static void main(String[] args) throws ScriptException{
	ScriptEngineManager manager = new ScriptEngineManager(); 
	ScriptEngine engine = manager.getEngineByName("js"); 
	Object result = engine.eval("4*5"); 
	System.out.println("..result..."+String.valueOf(result));
}
}
