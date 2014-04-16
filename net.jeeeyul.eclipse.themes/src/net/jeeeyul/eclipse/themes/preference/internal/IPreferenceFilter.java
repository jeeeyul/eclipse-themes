package net.jeeeyul.eclipse.themes.preference.internal;

import java.lang.reflect.Field;

import net.jeeeyul.eclipse.themes.preference.JTPConstants;

/**
 * Filter that accepts key files in {@link JTPConstants}.
 * 
 * @see JTPUtil#listPreferenceKeys(IPreferenceFilter)
 * 
 * @author Jeeeyul
 */
public interface IPreferenceFilter {
	/**
	 * @param category
	 *            category interface.
	 * @return <code>true</code> if accept category interface.
	 */
	public boolean acceptCategory(Class<?> category);

	/**
	 * 
	 * @param field
	 *            filed that contains key to test.
	 * @return <code>true</code> if accept given field.
	 */
	public boolean acceptKey(Field field);
}
