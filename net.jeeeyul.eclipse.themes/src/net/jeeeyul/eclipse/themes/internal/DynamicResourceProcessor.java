package net.jeeeyul.eclipse.themes.internal;

import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.css.internal.dynamicresource.JTDynamicResourceLocator;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

/**
 * 
 */
@SuppressWarnings("restriction")
public class DynamicResourceProcessor {
	@Inject
	IEclipseContext ctx;

	@Execute
	public void trackThemeInstance() {
		ctx.runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(IEclipseContext context) {
				IThemeEngine engine = (IThemeEngine) context
						.get(IThemeEngine.class.getCanonicalName());

				if (engine != null) {
					engine.registerResourceLocator(new JTDynamicResourceLocator());
					Debug.println("Dynamic Resource Locator is installed");
				}
				return engine == null;
			}
		});

	}
}