package khh.property.util;

import java.util.Enumeration;
import java.util.Properties;

public class PropertyUtil
{

	//java(TM) 2 Runtime Environmet. Standard Edition
	public static String getRuntimeName(){
		return getProperty("java.runtime.name");
	}
	//c:\program file\kava\jdk1.4._16\jre\bin
	public static String getJavaPath(){
		return getProperty("sun.boot.library.path");
	}
	
	//1.5.0._17-b04
	public static String getVM_Version(){
		return getProperty("java.vm.version");
	}
	
	//Sun MicroSystem inc
	public static String getVendor(){
		return getProperty("java.vm.vendor");
	}
	//http://java.sun.com
	public static String getVendorURI(){
		return getProperty("java.vm.uri");
	}
	//window ;   unix,linux : 
	public static String getPathSeparator(){
		return getProperty("path.separator");
	}
	//window \   unix/linux / 
	public static String getFileSeparator(){
	    return getProperty("file.separator");
	}
	//java hotspoop(TM) client VM
	public static String getName(){
		return getProperty("java.vm.name");
	}
	//sun.io
	public static String getEncodingPKG(){
		return getProperty("file.encoding.pkg");
	}
	//KR
	public static String getCountry(){
		return getProperty("user.country");
	}
	//SUN_STANDARD
	public static String getLauncher(){
		return getProperty("sun.java.launcher");
	}
	//OS PATH LEVEL
	public static String getOS_PathLevel(){
		return getProperty("sun.os.patch.level");
	}
	//java virtual machine specification
	public static String getSpecification(){
		return getProperty("java.vm.specification.name");
	}
	//d:\.........\classes
	public static String getUserDir(){
	return getProperty("user.dir");
	}
	//1.50.17-bd
	public static String getRuntimeVersion(){
		return getProperty("java.runtime.version");
	}
	//sun.awt.Win32GraphicsEnvironment
	public static String getGraphicsenv(){
		return getProperty("java.awt.graphicsenv");
	}
	//c:\./..jre\lib\endorsedDir
	public static String getEndorsedDir(){
		return getProperty("java.endorsed.dirs");
	}
	//x86
	public static String getArch(){
		return getProperty("os.arch");
	}
	//c:\...local\Temp\
	public static String getTempDir(){
		return getProperty("java.io.tmpdir");
	}
	//separator     줄바꿈..
	public static String getSeparator(){
		return getProperty("line.separator");
	}
	public static String getOSName(){
		return getProperty("os.name");
	}
	public static String getJunEncoding(){
	    return getProperty("sun.jun.encoding");
	}
	public static String getLibraryPath(){
	    return getProperty("java.library.path");
	}
	public static String getClassVersion(){
	    return getProperty("java.class.version");
	}
	public static String getOSVersion(){
	    return getProperty("os.version");
	}
	public static String getUserHome(){
	    return getProperty("user.home");
	}
	public static String getUserTimeZone(){
	    return getProperty("user.timezone");
	}
    public static String getFileEncoding(){
        return getProperty("file.encoding");
    }
    public static String getUserName(){
        return getProperty("user.name");
    }
    public static String getClassPath(){
        return getProperty("java.class.path");
    }
    public static String getJavaHome(){
        return getProperty("java.home");
    }
    public static String getUserLanguage(){
        return getProperty("user.language");
    }
    public static String getBootClassPath(){
        return getProperty("sun.boot.class.path");
    }
    public static String getCPUEndian(){
        return getProperty("sun.cpu.endian");
    }
    public static String getUnicodeEncoding(){
        return getProperty("sun.io.unicode.encoding");
    }
    public static String getDeskTop(){
        return getProperty("sun.desktop");
    }
    public static String getCPUisalist(){
        return getProperty("sun.cpu.isalist");
    }
	//,,,,
	
	
    //자바실행할때 -Da=b  하면 a값은 b다 
    public static Enumeration<String> getPropertiesFull(){
    	Properties props = System.getProperties();
    	return (Enumeration<String>) props.propertyNames();
//        Properties props = System.getProperties();
//        for(Enumeration en = props.propertyNames(); en.hasMoreElements();) {
//        	String key = (String)en.nextElement();
//        	out.println(key);
//            out.println("=");
//            out.println(System.getProperty(key));
//            out.println("<br>");
//        }
    }
	public static Properties getProperties(){
		return System.getProperties();
	}
	public static String getProperty(String param){
		return System.getProperty(param);
	}
}
