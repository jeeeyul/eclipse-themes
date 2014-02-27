package net.jeeeyul.eclipse.themes;

import net.jeeeyul.eclipse.themes.css.dynamicresource.ChromeDynamicResourceLocator;
import net.jeeeyul.eclipse.themes.internal.Nature;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.css.swt.theme.IThemeManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class JThemesCore extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.jeeeyul.eclipse.themes"; //$NON-NLS-1$
	
	public static final String CUSTOM_THEME_ID = "net.jeeeyul.eclipse.themes.custom";

	// The shared instance
	private static JThemesCore plugin;

	private JThemePreferenceStore preferenceStore;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JThemesCore getDefault() {
		return plugin;
	}

	/**
	 * The constructor
	 */
	public JThemesCore() {
	}

	public void log(Exception e) {
		getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(), e.getMessage(), e));
	}

	public void logWarn(String message) {
		getLog().log(new Status(IStatus.WARNING, getBundle().getSymbolicName(), message));
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		Version version = Nature.INSTANCE.getVersion();
		if (Nature.INSTANCE.isAfter(version, Nature.INSTANCE.JUNO_SR1_RANGE)) {
			ServiceReference<IThemeManager> serviceReference = context.getServiceReference(IThemeManager.class);
			IThemeManager manager = context.getService(serviceReference);

			IThemeEngine engine = manager.getEngineForDisplay(Display.getDefault());
			engine.registerResourceLocator(new ChromeDynamicResourceLocator());
		}
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	@Override
	public JThemePreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			preferenceStore = new JThemePreferenceStore(super.getPreferenceStore());
		}
		return preferenceStore;
	}

}
