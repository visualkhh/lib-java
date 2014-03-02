import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import khh.file.util.FileUtil;

import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.Main;

public class Test {
	   public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException, IOException 
       {
		   ContextFactory factory = ContextFactory.getGlobal();
    	   Context cx = factory.enterContext();
    	   cx.setOptimizationLevel(-1);// without 64kb limit
    	   
    	   
//    	   cx.setLanguageVersion(Context.VERSION_1_5);
//    	   Global global = Main.getGlobal();
//    	   global.init(cx);
//    	   global.readFile(cx, shared, args, funObj)
//    	   Main.processSource(cx, "./lib/env.rhino.js");
    	   
    	   Scriptable shared = cx.initStandardObjects();
    	   Scriptable scope = cx.newObject(shared);
    	   cx.evaluateReader(scope, new java.io.FileReader("./lib/env.rhino.js"), "", 1, null);
    	   
    	   BufferedReader in = new BufferedReader(new FileReader(new File("./onloadTest.html")));
    	   cx.evaluateReader(scope, in, "onloadTest.html", 1, null);
//    	   cx.set
    	   
//    	   String source = "document.body.innerHTML = "+FileUtil.readeFileToString(new File("W:\\code.google\\jsputil-visualkhh\\test\\WebContent\\onloadTest.html"));
//    	   System.out.println(source);
    	   
    	   
    	   
    	   
    	   
    	   
    	   
//    	   
    	   String source ="";
    	   source = "document.body.innerHTML";
//    	   source = "Envjs.scriptTypes['text/javascript'] = true;";
//    	   eval(source,scope,cx);
//    	   
//    	   source = "window.location='onloadTest.html";
    	   eval(source,scope,cx);
//    	   
//    	   source = "window.location";
//    	   eval(source,scope,cx);
    	   
//    	   source += "window.lotaion = W:\\code.google\\jsputil-visualkhh\\test\\WebContent\\onloadTest.html"));
//    	   Object result = cx.evaluateString(scope, source, "", 1, null);
    	   
    	   
    	   
    	   
//    	   
//    	   String source ="";
//    	   source += "document.write = "+FileUtil.readeFileToString(new File("W:\\code.google\\jsputil-visualkhh\\test\\WebContent\\onloadTest.html"));
////    	   source += "var div = document.createElement(\"div\");";
////    	   source += "div.innerHTML = \"korea\";";
////    	   source += "document.body.appendChild(div);";
////    	   source += "document.body.innerHTML;";
//    	   
//    	   Object result = cx.evaluateString(scope, source, "", 1, null);
//    	   System.out.println(result);//"<div>korea</div>"
//    	   
//    	   
//    	   source="";
//    	   source += "document.body.innerHTML;";
////    	   source += "var div = document.createElement(\"div\");";
////    	   source += "div.innerHTML = \"korea\";";
////    	   source += "document.getElementById(\"gg\").appendChild(div);";
////    	   System.out.println(source);
//    	   result = cx.evaluateString(scope, source, "", 1, null);
//    	   System.out.println(result);//"<div>korea</div>"
    	   
	   }
       
	   public static Object eval(String source,Scriptable scope, Context cx){
		   Object result = cx.evaluateString(scope, source, "", 1, null);
    	   System.out.println(result);
    	   
    	   
    	   return result;
	   }
       
}
