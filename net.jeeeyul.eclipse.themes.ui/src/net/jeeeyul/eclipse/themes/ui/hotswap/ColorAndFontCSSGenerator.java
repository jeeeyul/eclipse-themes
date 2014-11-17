package net.jeeeyul.eclipse.themes.ui.hotswap;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.internal.themes.ColorDefinition;
import org.eclipse.ui.internal.themes.FontDefinition;
import org.eclipse.ui.internal.themes.IThemeRegistry;
import org.eclipse.ui.internal.themes.ThemeElementHelper;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.MarkerAnnotationPreferences;

/**
 * https://github.com/jeeeyul/eclipse-themes/pull/201#issuecomment-59468039
 * 
 * Add workbench and color changes to the generated CSS files
 * 
 * @author briandealwis
 */
@SuppressWarnings("restriction")
public class ColorAndFontCSSGenerator {
	private org.eclipse.ui.themes.ITheme theme;
	// private org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme;
	private IThemeRegistry registry;

	/**
	 * Add workbench and color changes to the generated CSS files
	 * 
	 * @param builder
	 */
	public void run(StringBuilder builder) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		theme = workbench.getThemeManager().getCurrentTheme();
		registry = WorkbenchPlugin.getDefault().getThemeRegistry();

		// The Eclipse ColorsAndFontsPreferencePage uses
		// PrefUtil.getInternalPreferenceStore(), but
		// that just redirects to…
		generateWorkbenchSettings(builder, WorkbenchPlugin.getDefault().getPreferenceStore());

		// Generate the org.eclipse.ui.editors settings
		generateEditorSettings(builder, EditorsPlugin.getDefault().getMarkerAnnotationPreferences(), EditorsPlugin.getDefault().getPreferenceStore());
	}

	protected void generateWorkbenchSettings(StringBuilder sb, IPreferenceStore preferences) {
		List<String> keys = new LinkedList<String>();
		List<String> values = new LinkedList<String>();
		for (ColorDefinition definition : registry.getColorsFor(theme.getId())) {
			if (definition.isModifiedByUser()) {
				// ColorsAndFontsPreferencePage#createPreferenceKey() doesn't
				// use cssTheme to generate prefkey for modified-by-user.
				// Try to check that the user didn't change the definition
				// to the default value.
				String prefKey = ThemeElementHelper.createPreferenceKey(theme, definition.getId());
				String fdString = null;
				if (definition.getValue() != null) {
					fdString = StringConverter.asString(definition.getValue());
				}
				String value = preferences.getString(prefKey);
				if (value.length() > 0 && (fdString == null || !value.equals(fdString))) {
					keys.add(prefKey);
					values.add(value);
				}
			}
		}

		for (FontDefinition definition : registry.getFontsFor(theme.getId())) {
			if (definition.isModifiedByUser()) {
				// ColorsAndFontsPreferencePage#createPreferenceKey() doesn't
				// use cssTheme to generate prefkey for modified-by-user.
				// Try to check that the user didn't change the definition
				// to the default value.
				String prefKey = ThemeElementHelper.createPreferenceKey(theme, definition.getId());
				String fdString = null;
				if (definition.getValue() != null) {
					fdString = PreferenceConverter.getStoredRepresentation(definition.getValue());
				}
				String value = preferences.getString(prefKey);
				if (value.length() > 0 && (fdString == null || !value.equals(fdString))) {
					keys.add(prefKey);
					values.add(value);
				}
			}
		}
		if (keys.size() > 0) {
			sb.append("IEclipsePreferences#org-eclipse-ui-workbench {\n  preferences:");
			for (int i = 0; i < keys.size(); i++) {
				sb.append("\n    '").append(keys.get(i)).append("=").append(values.get(i)).append("'");
			}
			sb.append(";\n}\n\n");
		}
	}

	@SuppressWarnings("unchecked")
	protected void generateEditorSettings(StringBuilder sb, MarkerAnnotationPreferences markerAnnotationPreferences, IPreferenceStore preferences) {
		List<String> keys = new LinkedList<String>();
		List<String> values = new LinkedList<String>();
		for (AnnotationPreference p : (List<AnnotationPreference>) markerAnnotationPreferences.getAnnotationPreferences()) {
			String preferenceKey = p.getColorPreferenceKey();
			if (preferenceKey == null) {
				continue;
			}
			String value = preferences.getString(preferenceKey);
			RGB defaultValue = p.getColorPreferenceValue();
			if (value.length() > 0 && (defaultValue == null || !value.equals(StringConverter.asString(defaultValue)))) {
				keys.add(preferenceKey);
				values.add(value);
			}
		}
		if (keys.size() > 0) {
			sb.append("IEclipsePreferences#org-eclipse-ui-editors {\n  preferences:");
			for (int i = 0; i < keys.size(); i++) {
				sb.append("\n    '").append(keys.get(i)).append("=").append(values.get(i)).append("'");
			}
			sb.append(";\n}\n\n");
		}
	}

}
