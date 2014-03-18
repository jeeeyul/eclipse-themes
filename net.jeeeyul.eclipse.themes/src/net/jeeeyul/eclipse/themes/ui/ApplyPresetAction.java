package net.jeeeyul.eclipse.themes.ui;

import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.css.RewriteCustomTheme;
import net.jeeeyul.eclipse.themes.internal.OSHelper;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

@SuppressWarnings("restriction")
public class ApplyPresetAction extends Action {
	private IJTPreset preset;

	public ApplyPresetAction(IJTPreset preset) {
		this.preset = preset;
		this.setText(preset.getName());
		this.setImageDescriptor(preset.getImageDescriptor());
	}

	@Override
	public void run() {
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		Properties properties = preset.getProperties();

		for (Object keyObj : properties.keySet()) {
			String key = (String) keyObj;
			String value = properties.getProperty(key);
			if (key.equals(JTPConstants.Layout.TAB_HEIGHT)) {
				int intValue = Integer.parseInt(value);
				store.setValue(key, Math.max(intValue, OSHelper.INSTANCE.getMinimumTabHeight()));
			} else {
				store.setValue(key, value);
			}
		}
		new RewriteCustomTheme().rewrite();

		MApplication application = (MApplication) PlatformUI.getWorkbench().getService(MApplication.class);
		IEclipseContext context = application.getContext();
		IThemeEngine engine = context.get(IThemeEngine.class);
		if (engine.getActiveTheme() == null || !engine.getActiveTheme().getId().equals(JThemesCore.CUSTOM_THEME_ID)) {
			engine.setTheme(JThemesCore.CUSTOM_THEME_ID, true);
			MessageDialog.openWarning(Display.getDefault().getActiveShell(), "Jeeeyul's Themes",
					"A restart or opening new window is required for the theme change to full effect.");
		}
	}
}
