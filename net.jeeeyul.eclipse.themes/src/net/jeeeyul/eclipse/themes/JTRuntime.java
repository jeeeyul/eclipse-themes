package net.jeeeyul.eclipse.themes;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import net.jeeeyul.eclipse.themes.css.internal.EditBoxTracker;
import net.jeeeyul.eclipse.themes.css.internal.NamedColorHack;
import net.jeeeyul.eclipse.themes.css.internal.dynamicresource.JeeeyulProtocol;

public class JTRuntime extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "net.jeeeyul.eclipse.themes";
	public static final String CUSTOM_THEME_ID = "net.jeeeyul.eclipse.themes.custom"; //$NON-NLS-1$

	private static JTRuntime INSTANCE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
		new JeeeyulProtocol().setup(context);
	}

	public static JTRuntime getInstance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		EditBoxTracker.INSTANCE.stopTracking();
		NamedColorHack.INSTANCE.stop();

		super.stop(bundleContext);
	}

}
