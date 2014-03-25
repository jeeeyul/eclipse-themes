package net.jeeeyul.eclipse.themes.preference.internal;

import java.lang.reflect.Field;

public interface IPreferenceFilter {
	public boolean acceptCategory(Class<?> category);

	public boolean acceptKey(Field field);
}
