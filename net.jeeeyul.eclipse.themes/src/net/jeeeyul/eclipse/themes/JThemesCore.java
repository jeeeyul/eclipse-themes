package net.jeeeyul.eclipse.themes;

import net.jeeeyul.eclipse.themes.css.dynamicresource.JTDynamicResourceLocator;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager;
import net.jeeeyul.eclipse.themes.preference.preset.internal.JTPresetManager;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.css.swt.theme.IThemeManager;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Jeeeyul Lee
 * @since 2.0
 */
@SuppressWarnings("restriction")
public class JThemesCore extends AbstractUIPlugin {

	/**
	 * The plug-in id
	 */
	public static final String PLUGIN_ID = "net.jeeeyul.eclipse.themes"; //$NON-NLS-1$

	/**
	 * The custom theme id.
	 */
	public static final String CUSTOM_THEME_ID = "net.jeeeyul.eclipse.themes.custom"; //$NON-NLS-1$

	// The shared instance
	private static JThemesCore plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JThemesCore getDefault() {
		return plugin;
	}

	private JThemePreferenceStore preferenceStore;
	private IJTPresetManager presetManager;

	/**
	 * The constructor
	 */
	public JThemesCore() {
	}

	@Override
	public JThemePreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			preferenceStore = new JThemePreferenceStore((IPersistentPreferenceStore) super.getPreferenceStore());
		}
		return preferenceStore;
	}

	/**
	 * 
	 * @return A shared {@link JTPresetManager} instance.
	 * 
	 * @since 2.0
	 */
	public IJTPresetManager getPresetManager() {
		if (presetManager == null) {
			presetManager = new JTPresetManager();
		}
		return presetManager;
	}

	/**
	 * Logs a error.
	 * 
	 * @param e
	 *            An exception to log.
	 */
	public void log(Exception e) {
		getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(), e.getMessage(), e));
	}

	/**
	 * Logs a warning.
	 * 
	 * @param message
	 *            A message to log.
	 */
	public void logWarn(String message) {
		getLog().log(new Status(IStatus.WARNING, getBundle().getSymbolicName(), message));
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		ServiceReference<IThemeManager> serviceReference = context.getServiceReference(IThemeManager.class);
		IThemeManager manager = context.getService(serviceReference);

		IThemeEngine engine = manager.getEngineForDisplay(Display.getDefault());
		engine.registerResourceLocator(new JTDynamicResourceLocator());
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
