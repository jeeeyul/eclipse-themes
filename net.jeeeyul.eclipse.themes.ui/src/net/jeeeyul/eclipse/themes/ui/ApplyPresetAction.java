package net.jeeeyul.eclipse.themes.ui;

import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.hotswap.RewriteCustomTheme;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PropertiesUtil;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

/**
 * Applies {@link IJTPreset} to current preference and generate CSS and apply
 * it.
 * 
 * @since 2.0
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class ApplyPresetAction extends Action {
	private IJTPreset preset;

	/**
	 * Creates an {@link ApplyPresetAction}.
	 * 
	 * @param preset
	 */
	public ApplyPresetAction(IJTPreset preset) {
		super(preset.getName(), SWT.CHECK);
		this.preset = preset;
		this.setImageDescriptor(preset.getImageDescriptor());

		/*
		 * https://github.com/jeeeyul/eclipse-themes/issues/140
		 */
		if (getThemeEngine().getActiveTheme().getId().equals(JThemesCore.CUSTOM_THEME_ID)) {
			String lastChoosedPresetId = getStore().getString(JTPConstants.Memento.LAST_CHOOSED_PRESET);
			if (preset.getId().equals(lastChoosedPresetId)) {
				setChecked(true);
			}
		}

	}

	@Override
	public void run() {
		JThemePreferenceStore store = getStore();
		IJTPresetManager presetManager = JThemesCore.getDefault().getPresetManager();
		Properties properties = PropertiesUtil.merge(presetManager.getDefaultPreset().getProperties(), preset.getProperties());

		for (Object keyObj : properties.keySet()) {
			String key = (String) keyObj;
			String value = properties.getProperty(key);
			if (key.equals(JTPConstants.Layout.TAB_HEIGHT)) {
				int intValue = Integer.parseInt(value);
				store.setValue(key, Math.max(intValue, SWTExtensions.INSTANCE.getMinimumToolBarHeight()));
			} else {
				store.setValue(key, value);
			}
		}
		new RewriteCustomTheme(true).rewrite();

		IThemeEngine engine = getThemeEngine();
		if (engine.getActiveTheme() == null || !engine.getActiveTheme().getId().equals(JThemesCore.CUSTOM_THEME_ID)) {
			engine.setTheme(JThemesCore.CUSTOM_THEME_ID, true);
		}

		/*
		 * https://github.com/jeeeyul/eclipse-themes/issues/140
		 */
		store.setValue(JTPConstants.Memento.LAST_CHOOSED_PRESET, preset.getId());
	}

	private IThemeEngine getThemeEngine() {
		MApplication application = (MApplication) PlatformUI.getWorkbench().getService(MApplication.class);
		IEclipseContext context = application.getContext();
		IThemeEngine engine = context.get(IThemeEngine.class);
		return engine;
	}

	private JThemePreferenceStore getStore() {
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		return store;
	}
}
