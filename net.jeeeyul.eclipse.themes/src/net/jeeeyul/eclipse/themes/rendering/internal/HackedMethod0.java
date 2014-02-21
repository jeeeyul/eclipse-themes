package net.jeeeyul.eclipse.themes.rendering.internal;

import java.lang.reflect.Method;

public class HackedMethod0<T, R> {
	private Class<T> type;
	private String name;
	private Method method;

	public HackedMethod0(Class<T> type, String name) {
		this.type = type;
		this.name = name;
	}

	public Method getMethod() {
		if (method == null) {
			try {
				method = type.getDeclaredMethod(name);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);

			}
		}
		return method;
	}

	@SuppressWarnings("unchecked")
	public R invoke(T object) {
		try {
			return (R) getMethod().invoke(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
