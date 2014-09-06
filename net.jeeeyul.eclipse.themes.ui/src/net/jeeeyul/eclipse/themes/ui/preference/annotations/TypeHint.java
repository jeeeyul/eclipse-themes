package net.jeeeyul.eclipse.themes.ui.preference.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;

/**
 * This annotation is used to specify type of value for field that contains key.
 * 
 * @see JTPConstants
 * 
 * @author Jeeeyul
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeHint {
	/**
	 * 
	 * @return the type for value of preference key in
	 *         {@link JThemePreferenceStore}.
	 */
	Class<?> value();
}
