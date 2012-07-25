package net.jeeeyul.eclipse.themes.e4;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig;

import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.renderers.swt.SashLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class SashTracker {
	private static SashTracker INSTANCE;

	public static SashTracker getInstance() {
		return INSTANCE;
	}

	EventHandler sashWeightHandler = new EventHandler() {
		public void handleEvent(Event event) {
			handleSashWeight(event);
		}
	};

	private Set<Composite> sashContainers = new HashSet<Composite>();
	private ILock lock = Job.getJobManager().newLock();

	@Inject
	IEventBroker eventBroker;

	private Field sashWidthField;

	@PreDestroy
	public void dispose() {
		sashContainers.clear();
		eventBroker.unsubscribe(sashWeightHandler);
	}

	private void handleSashWeight(Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MPartSashContainer
				&& event.getProperty(UIEvents.EventTags.TYPE) == UIEvents.EventTypes.SET) {
			Object value = event.getProperty(UIEvents.EventTags.NEW_VALUE);

			if (value instanceof Composite) {
				Composite composite = (Composite) value;
				if (composite.getLayout() instanceof SashLayout) {
					trackSashContainer(composite);
					if (ThemeTracker.getInstance().isChromeThemeActive()) {
						updateSash(composite, ChromeThemeConfig.getInstance().getSashWidth());
					}
				}
			}
		}
	}

	@PostConstruct
	public void init() {
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, sashWeightHandler);
		INSTANCE = this;
	}

	private void trackSashContainer(final Composite composite) {
		if (composite.isDisposed() || !(composite.getLayout() instanceof SashLayout)) {
			return;
		}
		lock.acquire();
		sashContainers.add(composite);
		lock.release();

		composite.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				untrack(composite);
			}
		});
	}

	private void untrack(Composite composite) {
		lock.acquire();
		sashContainers.remove(composite);
		lock.release();
	}

	/**
	 * Updates all
	 * 
	 * @since 1.2
	 */
	public void applyChromeThemeToAllSashContainers() {
		lock.acquire();
		Composite[] array = sashContainers.toArray(new Composite[sashContainers.size()]);
		lock.release();

		for (Composite each : array) {
			try {
				updateSash(each, ChromeThemeConfig.getInstance().getSashWidth());
			} catch (Exception e) {
				ChromeThemeCore.getDefault().log(e);
				e.printStackTrace();
			}
		}
	}

	private void updateSash(Composite each, int width) {
		if (each == null || each.isDisposed()) {
			return;
		}

		if (!(each.getLayout() instanceof SashLayout)) {
			return;
		}

		SashLayout layout = (SashLayout) each.getLayout();
		try {
			getSashWidthField().setInt(layout, width);
		} catch (Exception e) {
			ChromeThemeCore.getDefault().log(e);
			e.printStackTrace();
		}
		each.layout(true, true);
	}

	private Field getSashWidthField() throws SecurityException, NoSuchFieldException {
		if (sashWidthField == null) {
			sashWidthField = SashLayout.class.getDeclaredField("sashWidth");
			sashWidthField.setAccessible(true);
		}
		return sashWidthField;
	}

	/**
	 * 
	 * 
	 * @since 1.2
	 */
	public void restoreAllSashContainers() {
		lock.acquire();
		Composite[] array = sashContainers.toArray(new Composite[sashContainers.size()]);
		lock.release();

		for (Composite each : array) {
			try {
				updateSash(each, 4);
			} catch (Exception e) {
				e.printStackTrace();
				ChromeThemeCore.getDefault().log(e);
			}
		}
	}
}
