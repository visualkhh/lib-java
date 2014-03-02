package khh.script;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import khh.file.util.FileUtil;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

public class ScriptManager extends ScriptEngineManager{
//	ScriptEngineManager manager = new ScriptEngineManager(); 
//	ScriptEngine engine = manager.getEngineByName("js"); 
//	Object result = engine.eval("4*5"); 
	public ScriptEngine getJavaScriptEngin(){
		return getEngineByName("js"); 
	}
	
	/*
  ContextFactory factory = ContextFactory.getGlobal();
    	   Context cx = factory.enterContext();
    	   cx.setOptimizationLevel(-1);// without 64kb limit
    	   Scriptable shared = cx.initStandardObjects();
    	   Scriptable scope = cx.newObject(shared);
    	   cx.evaluateReader(scope, new java.io.FileReader("./lib/env.rhino.js"), "", 1, null);
    	   String source =FileUtil.readeFileToString(new File("W:\\code.google\\jsputil-visualkhh\\test\\WebContent\\onloadTest.html"));
    	   System.out.println(source);
//    	   String source ="";
//    	   source += "var div = document.createElement(\"div\");";
//    	   source += "div.innerHTML = \"korea\";";
//    	   source += "document.body.appendChild(div);";
//    	   source += "document.body.innerHTML;";
    	   Object result = cx.evaluateString(scope, source, "", 1, null);
    	   System.out.println(result);//"<div>korea</div>"       
    	   
    	   
    	   source="";
    	   source += "var div = document.createElement(\"div\");";
    	   source += "div.innerHTML = \"korea\";";
    	   source += "document.getElementById(\"gg\").appendChild(div);";
    	   System.out.println(source);
    	   result = cx.evaluateString(scope, source, "", 1, null);
    	   System.out.println(result);//"<div>korea</div>"       
	 */
}
