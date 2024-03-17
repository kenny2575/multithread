import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CacheInvocationHandler implements InvocationHandler {

	private final Object object;
	private final TimestampedCache<Method, Object> cache;
	private final Clockable clock;

	public CacheInvocationHandler(Object object, Clockable clock) {

		this.object = object;

		this.cache = new TimestampedCache<>(clock);

		this.clock = clock;

	}

	public Object invokeAndCache(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

		Object result = method.invoke(this.object, args);

		this.cache.cache(method, result);

		return result;

	}

	public Object getCachedValue(Method method) {
		return this.cache.getCachedValue(
				method,
				method.getAnnotation(Cached.class).ttl()
		);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		if (method.isAnnotationPresent(Mutator.class)) {

			this.cache.clear();

			return method.invoke(this.object, args);

		}

		if (method.isAnnotationPresent(Cached.class)) {

			Object value = this.getCachedValue(method);

			if (value == null)
				return this.invokeAndCache(method, args);
			else
				return value;

		} else

			return method.invoke(this.object, args);

	}

}