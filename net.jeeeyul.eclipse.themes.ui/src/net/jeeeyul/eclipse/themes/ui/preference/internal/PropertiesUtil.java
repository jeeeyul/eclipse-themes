package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.util.Properties;

/**
 * 
 * @author Jeeeyul
 */
public class PropertiesUtil {
	/**
	 * @param propertiesList
	 *            properties to merge
	 * @return merged {@link Properties}
	 */
	public static Properties merge(Properties... propertiesList) {
		Properties result = new Properties();
		for (Properties eachProps : propertiesList) {
			for (Object eachKey : eachProps.keySet()) {
				result.put(eachKey, eachProps.get(eachKey));
			}
		}
		return result;
	}
}
