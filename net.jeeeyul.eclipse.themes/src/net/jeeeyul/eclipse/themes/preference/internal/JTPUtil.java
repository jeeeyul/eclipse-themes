package net.jeeeyul.eclipse.themes.preference.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.annotations.Ignore;
import net.jeeeyul.eclipse.themes.preference.annotations.PresetCategory;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager;
import net.jeeeyul.eclipse.themes.preference.preset.internal.UserPreset;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * Utilities for {@link JThemePreferenceStore} and {@link JTPConstants}.
 * 
 * @author Jeeeyul
 */
public class JTPUtil {
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * A filter that matches only categories for theme preset.
	 */
	public static final IPreferenceFilter FILTER_PRESET = new IPreferenceFilter() {
		@Override
		public boolean acceptCategory(Class<?> category) {
			return category.getAnnotation(PresetCategory.class) != null;
		}

		@Override
		public boolean acceptKey(Field field) {
			return true;
		}
	};

	/**
	 * A filter that matches every category and field.
	 */
	public static final IPreferenceFilter FILTER_ALL = new IPreferenceFilter() {
		@Override
		public boolean acceptCategory(Class<?> category) {
			return true;
		}

		@Override
		public boolean acceptKey(Field field) {
			return true;
		}
	};

	private static void collectClasses(Class<?> root, List<Class<?>> result) {
		if (root == null || result == null) {
			throw new IllegalArgumentException();
		}
		result.add(root);
		for (Class<?> each : root.getDeclaredClasses()) {
			collectClasses(each, result);
		}
	}

	/**
	 * 
	 * @return User preset name validator.
	 * @see IInputValidator
	 */
	public static IInputValidator getPresetNameValidator() {
		IInputValidator presetNameValidator = new IInputValidator() {
			@Override
			public String isValid(String newText) {
				if (newText.trim().length() == 0) {
					return "Name must be specified.";
				}

				IJTPresetManager presetManager = JThemesCore.getDefault().getPresetManager();
				for (UserPreset each : presetManager.getUserPresets()) {
					if (each.getName().equalsIgnoreCase(newText)) {
						return "Already exist name.";
					}
				}

				if (!UserPreset.isSafeName(newText)) {
					return "Unsafe characters are contained.";
				}
				return null;
			}
		};

		return presetNameValidator;
	}

	/**
	 * List preference keys that matches given filter.
	 * 
	 * @param filter
	 *            filter to match.
	 * @return key list.
	 */
	public static final List<String> listPreferenceKeys(IPreferenceFilter filter) {
		ArrayList<String> result = new ArrayList<String>();

		List<Class<?>> allCategories = new ArrayList<Class<?>>();
		collectClasses(JTPConstants.class, allCategories);

		for (Class<?> eachCategory : allCategories) {
			if (eachCategory.getAnnotation(Ignore.class) != null) {
				continue;
			}

			if (filter.acceptCategory(eachCategory)) {
				for (Field eachField : eachCategory.getDeclaredFields()) {
					if (eachField.getAnnotation(Ignore.class) != null) {
						continue;
					}

					if (filter.acceptKey(eachField)) {
						try {
							result.add((String) eachField.get(eachCategory));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Converts string to .properties value string.
	 * 
	 * @param theString
	 *            string to convert.
	 * @param escapeSpace
	 *            escaping empty space.
	 * @param escapeUnicode
	 *            escaping unicode.
	 * @return converted string.
	 */
	public static String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}
}
