package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.lang.reflect.Field;

import net.jeeeyul.eclipse.themes.ui.preference.annotations.TypeHint;

/**
 * 
 * @author Jeeeyul
 */
public class TypeFilter implements IPreferenceFilter {
	private Class<?> typeClass;

	/**
	 * @param typeClass
	 */
	public TypeFilter(Class<?> typeClass) {
		super();
		this.typeClass = typeClass;
	}

	@Override
	public boolean acceptCategory(Class<?> category) {
		return true;
	}

	@Override
	public boolean acceptKey(Field field) {
		TypeHint annotation = field.getAnnotation(TypeHint.class);
		if (annotation == null) {
			return false;
		}
		return annotation.value() == typeClass;
	}

	@Override
	public IPreferenceFilter chain(IPreferenceFilter other) {
		return new CompoundPreferenceFilter(this, other);
	}

}
