package net.jeeeyul.eclipse.themes.preference.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.annotations.Ignore;

public class JTPreferenceKeyCollector {

	public JTPreferenceKeyCollector() {
	}

	public List<String> collect() {
		ArrayList<String> result = new ArrayList<String>();

		for (Class<?> category : JTPConstants.class.getDeclaredClasses()) {
			if (category.getAnnotation(Ignore.class) != null) {
				continue;
			}

			for (Field f : category.getDeclaredFields()) {
				if (f.getAnnotation(Ignore.class) != null) {
					continue;
				}
				try {
					result.add((String) f.get(category));
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
		for (String e : new JTPreferenceKeyCollector().collect()) {
			System.out.println(e);
		}
	}

}
