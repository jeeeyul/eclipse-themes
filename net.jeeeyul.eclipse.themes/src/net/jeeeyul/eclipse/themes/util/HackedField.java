package net.jeeeyul.eclipse.themes.util;

import java.lang.reflect.Field;

import net.jeeeyul.eclipse.themes.rendering.internal.JTabRendererHelper;

/**
 * It provides abstraction for accessing field with Java Reflection. Used by
 * {@link JTabRendererHelper}.
 * 
 * @param <T>
 *            Type
 * @param <FT>
 *            Field Type
 *
 * @author Jeeeyul
 * @since 2.0.0
 */
public class HackedField<T, FT> {
	private Class<T> type;
	private String name;
	private Field field;

	private Field getField() {
		if (field == null) {
			try {
				field = type.getDeclaredField(name);
				field.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return field;
	}

	/**
	 * 
	 * @param type
	 *            Type.
	 * @param name
	 *            Field name.
	 */
	public HackedField(Class<T> type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * 
	 * @param obj
	 *            field owner.
	 * @return field value.
	 * @since 2.0.0
	 */
	@SuppressWarnings("unchecked")
	public FT get(Object obj) {
		try {
			return (FT) getField().get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * sets a new field value.
	 * 
	 * @param obj
	 *            owner to set field.
	 * @param value
	 *            new value for field.
	 * @return new field value.
	 */
	public FT set(Object obj, FT value) {
		try {
			getField().set(obj, value);
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
