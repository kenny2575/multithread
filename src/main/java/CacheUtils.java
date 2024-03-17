import java.lang.reflect.Proxy;

public class CacheUtils {
	public static <T> T cache(T object, Clockable clock) {
		return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(),
				object.getClass().getInterfaces(),
				new CacheInvocationHandler(object, clock)
		);
	}
}
