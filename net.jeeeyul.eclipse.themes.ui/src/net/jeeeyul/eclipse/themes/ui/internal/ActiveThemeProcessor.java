package net.jeeeyul.eclipse.themes.ui.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.hotswap.RewriteCustomTheme;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * It tracks active theme changing, and it handles
 * {@link #onCustomThemeActivativation()} and
 * {@link #onCustomThemeDeactivation()}
 * 
 * @author Jeeeyul
 * @since 2.0.0
 */
@SuppressWarnings("restriction")
public class ActiveThemeProcessor {
	EventHandler themeChangeHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			handleThemeChange(event);
		}
	};

	@Inject
	IEventBroker eventBroker;

	/**
	 * Stops tracking theme chaninging.
	 */
	@PreDestroy
	public void dispose() {
		eventBroker.unsubscribe(themeChangeHandler);
		Debug.println("Active Theme Processor is disposed");
	}

	private void handleThemeChange(Event event) {
		ITheme theme = (ITheme) event.getProperty(IThemeEngine.Events.THEME);
		if (theme == null) {
			return;
		}

		boolean isCustomThemeActivated = JThemesCore.CUSTOM_THEME_ID.equals(theme.getId());
		if (isCustomThemeActivated) {
			onCustomThemeActivativation();
		}

		else {
			// FIXME should have to check previous theme.
			onCustomThemeDeactivation();
		}
	}

	/**
	 * 
	 * @return {@link IThemeEngine} from eclipse.
	 */
	public IThemeEngine getThemeEngine() {
		IThemeEngine engine = (IThemeEngine) Display.getDefault().getData("org.eclipse.e4.ui.css.swt.theme");
		return engine;
	}

	/**
	 * Start tracking change of theme.
	 */
	@PostConstruct
	public void init() {
		eventBroker.subscribe(IThemeEngine.Events.THEME_CHANGED, themeChangeHandler);
		Debug.println("Active Theme Tracker has been started");
	}

	private void onCustomThemeActivativation() {
		Debug.println("Dynamic Custom Theme is Activated!");
		new RewriteCustomTheme().rewrite();
	}

	private void onCustomThemeDeactivation() {
		Debug.println("Dynamic Custom Theme is Deactivated!");
	}
}