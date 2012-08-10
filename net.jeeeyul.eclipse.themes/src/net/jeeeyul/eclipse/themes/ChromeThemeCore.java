package net.jeeeyul.eclipse.themes;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ChromeThemeCore extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.jeeeyul.eclipse.themes"; //$NON-NLS-1$

	// The shared instance
	private static ChromeThemeCore plugin;

	/**
	 * The constructor
	 */
	public ChromeThemeCore() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ChromeThemeCore getDefault() {
		return plugin;
	}

	public void log(Exception e) {
		getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(), e.getMessage(), e));
	}

	public void logWarn(String message) {
		getLog().log(new Status(IStatus.WARNING, getBundle().getSymbolicName(), message));
	}

}
