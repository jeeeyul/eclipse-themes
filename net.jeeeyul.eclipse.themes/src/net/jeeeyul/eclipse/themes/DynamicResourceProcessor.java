package net.jeeeyul.eclipse.themes;

import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.css.internal.dynamicresource.JTDynamicResourceLocator;
import net.jeeeyul.eclipse.themes.internal.Debug;
import net.jeeeyul.eclipse.themes.internal.ENVHelper;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.widgets.Display;

/**
 * 
 */
@SuppressWarnings("restriction")
public class DynamicResourceProcessor {
	@Inject
	IEclipseContext ctx;

	@Execute
	public void trackThemeInstance() {
		Debug.println("DynamicResourceProcessor Started.");
		ctx.runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(IEclipseContext context) {
				boolean tryNext = tryToInstall(context) == false;
				return tryNext;
			}
		});
	}

	private boolean tryToInstall(IEclipseContext context) {
		context.get(IContributionFactory.class.getName());
		IStylingEngine stylingEngine = context.get(IStylingEngine.class);
		IThemeEngine themeEngine = context.get(IThemeEngine.class);

		if (themeEngine != null) {
			themeEngine.registerResourceLocator(new JTDynamicResourceLocator());
			Debug.println("Dynamic Resource Locator is installed on Theme Engine");

			if (!ENVHelper.INSTANCE.isLinux()) {
				CSSEngine cssEngine = WidgetElement.getEngine(Display.getDefault());
				if (cssEngine != null) {
					cssEngine.getResourcesLocatorManager().registerResourceLocator(new JTDynamicResourceLocator());
					Debug.println("Dynamic Resource Locator is installed on CSS Engine");
					return true;
				}
			}
			return true;
		}

		if (stylingEngine != null) {
			CSSEngine cssEngine = WidgetElement.getEngine(Display.getDefault());
			if (cssEngine != null) {
				cssEngine.getResourcesLocatorManager().registerResourceLocator(new JTDynamicResourceLocator());
				Debug.println("Dynamic Resource Locator is installed on CSS Engine");
				return true;
			}
		}

		return false;
	}
}