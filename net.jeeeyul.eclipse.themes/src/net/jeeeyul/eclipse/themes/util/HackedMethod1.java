package net.jeeeyul.eclipse.themes.util;

import java.lang.reflect.Method;

/**
 * Abstracts method that has 1 argument.
 * 
 * @param <T>
 *            The type declared method.
 * @param <A1>
 *            The type of first argument.
 * @param <R>
 *            Return type of the method.
 * @author Jeeeyul
 */
@SuppressWarnings("javadoc")
public class HackedMethod1<T, A1, R> {
	private Class<T> type;
	private String name;
	private Method method;
	private Class<A1> argumentType1;

	public HackedMethod1(Class<T> type, String name, Class<A1> argumentType1) {
		this.type = type;
		this.name = name;
		this.argumentType1 = argumentType1;
	}

	public Method getMethod() {
		if (method == null) {
			try {
				method = type.getDeclaredMethod(name, argumentType1);
			} catch (Exception e) {
				throw new RuntimeException(e);

			}
		}
		return method;
	}

	@SuppressWarnings("unchecked")
	public R invoke(T object, A1 argument) {
		try {
			return (R) getMethod().invoke(object, argument);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
