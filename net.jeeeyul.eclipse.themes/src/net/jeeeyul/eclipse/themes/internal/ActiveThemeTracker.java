package net.jeeeyul.eclipse.themes.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.css.RewriteCustomTheme;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class ActiveThemeTracker {
	public static ActiveThemeTracker getInstance() {
		return INSTANCE;
	}

	EventHandler themeChangeHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			handleThemeChange(event);
		}
	};

	private static ActiveThemeTracker INSTANCE;

	private ITheme activeTheme;

	@Inject
	IEventBroker eventBroker;

	@PreDestroy
	public void dispose() {
		eventBroker.unsubscribe(themeChangeHandler);
	}

	public ITheme getActiveTheme() {
		if (activeTheme == null) {
			IThemeEngine engine = getThemeEngine();
			activeTheme = engine.getActiveTheme();
		}
		return activeTheme;
	}

	public IThemeEngine getThemeEngine() {
		IThemeEngine engine = (IThemeEngine) Display.getDefault().getData("org.eclipse.e4.ui.css.swt.theme");
		return engine;
	}

	protected void handleThemeChange(Event event) {
		ITheme theme = (ITheme) event.getProperty(IThemeEngine.Events.THEME);
		if (theme == null) {
			return;
		}

		activeTheme = theme;

		boolean isChromeThemeActivated = JThemesCore.CUSTOM_THEME_ID.equals(theme.getId());
		if (isChromeThemeActivated) {
			onCustomThemeActivativation();
		} else {
			onCustomThemeDeactivation();
		}
	}

	private void onCustomThemeDeactivation() {
	}

	private void onCustomThemeActivativation() {
		new RewriteCustomTheme().rewrite();
	}

	@PostConstruct
	public void init() {
		INSTANCE = this;
		eventBroker.subscribe(IThemeEngine.Events.THEME_CHANGED, themeChangeHandler);
	}

	public boolean isCustomThemeActive() {
		ITheme activeTheme = getActiveTheme();
		if (activeTheme == null) {
			return false;
		} else {
			return JThemesCore.CUSTOM_THEME_ID.equals(activeTheme.getId());
		}
	}
}