package net.jeeeyul.eclipse.themes.rendering.internal;

import java.lang.reflect.Field;

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

	public HackedField(Class<T> type, String name) {
		this.type = type;
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public FT get(Object obj) {
		try {
			return (FT) getField().get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public FT set(Object obj, FT value) {
		try {
			getField().set(obj, value);
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
