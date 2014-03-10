package net.jeeeyul.eclipse.themes.preference.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.annotations.Ignore;
import net.jeeeyul.eclipse.themes.preference.annotations.TypeHint;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.dialogs.IInputValidator;

public class JTPUtil {
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

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

	public static final List<Class<?>> listCategories() {
		ArrayList<Class<?>> result = new ArrayList<Class<?>>();

		for (Class<?> category : JTPConstants.class.getDeclaredClasses()) {
			if (category.getAnnotation(Ignore.class) != null) {
				continue;
			}

			result.add(category);
		}

		return result;
	}

	public static final List<String> listPreferenceKeys() {
		return listPreferenceKeys(null, null);
	}

	public static final List<String> listPreferenceKeys(Class<?> category) {
		return listPreferenceKeys(category, null);
	}

	public static final List<String> listPreferenceKeys(Class<?> category, Class<?> valueType) {
		ArrayList<String> result = new ArrayList<String>();
		if (category == null) {
			for (Class<?> eachCategory : listCategories()) {
				result.addAll(listPreferenceKeys(eachCategory, valueType));
			}
			return result;
		}

		else {
			for (Field f : category.getDeclaredFields()) {
				if (f.getAnnotation(Ignore.class) != null) {
					continue;
				}
				try {
					if (valueType != null) {
						TypeHint thAnno = f.getAnnotation(TypeHint.class);
						if (thAnno.value() == valueType) {
							result.add((String) f.get(category));
						}
					} else {
						result.add((String) f.get(category));
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		for (String e : JTPUtil.listPreferenceKeys(JTPConstants.ActivePartStack.class, HSB.class)) {
			System.out.println(e);
		}
	}

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
