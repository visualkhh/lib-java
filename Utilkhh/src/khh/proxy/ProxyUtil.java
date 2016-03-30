package khh.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyUtil {

	public static <T> T setProxy(Class inter, InvocationHandler handler, Class<T> rc){
		T t = (T) Proxy.newProxyInstance(
				inter.getClassLoader(),
                new Class[] { inter},
                handler);
		return t;
	}
}
