import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.script.ScriptException;

import khh.collection.StandardArrayList;
import khh.date.LunarCalendar;
import khh.reflection.ReflectionUtil;
import khh.std.Standard;

public class testmain {
    public testmain() {
        // TODO Auto-generated constructor stub
    }

    public void s() throws InterruptedException, IOException {
    }

    public static void main(String[] args) throws InterruptedException, IOException, ScriptException, SecurityException,
            IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        LunarCalendar cal = new LunarCalendar();
     //   System.out.println(cal.toLunar("19860425"));

        String g=null;
         Class[] classs={ReflectionUtil.getClass("java.lang.String")};
         Object[] objects= {new String("19860425")};
        System.out.println( ReflectionUtil.executeMathod(cal, "toLunar",classs,objects) );
        
        StandardArrayList<Class, Object> standard = new StandardArrayList<Class, Object>();
        standard.add( new Standard(ReflectionUtil.getClass("java.lang.String"),"19860426"));
        System.out.println( ReflectionUtil.executeMathod(cal, "toLunar",standard) );
        
    }
}
