package net.jeeeyul.eclipse.themes.ui.hotswap;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
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

public class ColorAndFontCSSGenerator {
	private org.eclipse.ui.themes.ITheme theme;
	// private org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme;
	private IThemeRegistry registry;

	public void run(StringBuilder builder) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		theme = workbench.getThemeManager().getCurrentTheme();
		registry = WorkbenchPlugin.getDefault().getThemeRegistry();
		// cssTheme =
		// ((IThemeEngine)workbench.getService(IThemeEngine.class)).getActiveTheme();

		// The Eclipse ColorsAndFontsPreferencePage uses
		// PrefUtil.getInternalPreferenceStore(), but
		// that just redirects to…
		generateWorkbenchSettings(builder, WorkbenchPlugin.getDefault().getPreferenceStore());

		// Generate the org.eclipse.ui.editors settings
		generateEditorSettings(builder, EditorsPlugin.getDefault().getMarkerAnnotationPreferences(), EditorsPlugin.getDefault().getPreferenceStore());
	}

	protected void generateWorkbenchSettings(StringBuilder sb, IPreferenceStore preferences) {
		sb.append("IEclipsePreferences#org-eclipse-ui-workbench {\n  preferences:");

		FontRegistry fontRegistry = theme.getFontRegistry();
		for (ColorDefinition definition : registry.getColorsFor(theme.getId())) {
			if (definition.isModifiedByUser()) {
				// ColorsAndFontsPreferencePage#createPreferenceKey() doesn't
				// use cssTheme
				// to generate prefkey for modified-by-user
				String prefKey = ThemeElementHelper.createPreferenceKey(theme, definition.getId());
				String fdString = StringConverter.asString(definition.getValue());
				String value = preferences.getString(prefKey);
				if (value.length() > 0 && !value.equals(fdString)) {
					sb.append("\n    '").append(prefKey).append("=").append(fdString).append("'");
				}
			}
		}

		ColorRegistry colorRegistry = theme.getColorRegistry();
		for (FontDefinition definition : registry.getFontsFor(theme.getId())) {
			if(definition.isModifiedByUser()) {
				// ColorsAndFontsPreferencePage#createPreferenceKey() doesn't
				// use cssTheme
				// to generate prefkey for modified-by-user
				String prefKey = ThemeElementHelper.createPreferenceKey(theme, definition.getId());
				String fdString = PreferenceConverter.getStoredRepresentation(definition.getValue());
				String value = preferences.getString(prefKey);
				if (value.length() > 0 && !value.equals(fdString)) {
					sb.append("\n    '").append(prefKey).append("=").append(fdString).append("'");
				}
			}
		}
		sb.append("\n}\n\n");
	}

	protected void generateEditorSettings(StringBuilder sb, MarkerAnnotationPreferences markerAnnotationPreferences, IPreferenceStore preferences) {
		sb.append("IEclipsePreferences#org-eclipse-ui-editors {\n  preferences:");
		for (AnnotationPreference p : (List<AnnotationPreference>) markerAnnotationPreferences.getAnnotationPreferences()) {
			String preferenceKey = p.getColorPreferenceKey();
			if (preferenceKey == null) {
				continue;
			}
			String value = preferences.getString(preferenceKey);
			RGB defaultValue = p.getColorPreferenceValue();
			if (value.length() > 0 && (defaultValue == null || !value.equals(StringConverter.asString(defaultValue)))) {
				sb.append("\n    '").append(preferenceKey).append("=").append(value).append("'");
			}
		}
		sb.append("\n}\n\n");
	}

}
