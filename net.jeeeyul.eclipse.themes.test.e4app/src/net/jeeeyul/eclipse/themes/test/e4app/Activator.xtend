package net.jeeeyul.eclipse.themes.test.e4app

import net.jeeeyul.eclipse.themes.test.e4app.model.AppState
import org.eclipse.e4.core.di.InjectorFactory
import org.osgi.framework.BundleActivator
import org.osgi.framework.BundleContext

class Activator implements BundleActivator {
	override void start(BundleContext context) throws Exception {
		InjectorFactory.^default.addBinding(AppState).implementedBy(AppState)
	}

	override void stop(BundleContext context) throws Exception {
	}

}
