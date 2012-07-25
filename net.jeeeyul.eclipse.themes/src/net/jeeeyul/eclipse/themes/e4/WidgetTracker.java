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
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.renderers.swt.SashLayout;
import org.eclipse.e4.ui.workbench.renderers.swt.TrimmedPartLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * It tracks widget assignment to UI Model.
 * 
 * When {@link MPartSashContainer} and {@link MWindow} get's it widget, this
 * tracker applys Chrome Theme Settings.
 * 
 * 
 * @author Jeeeyul
 * @since 1.2
 */
@SuppressWarnings("restriction")
public class WidgetTracker {
	private static WidgetTracker INSTANCE;

	public static WidgetTracker getInstance() {
		return INSTANCE;
	}

	EventHandler sashWeightHandler = new EventHandler() {
		public void handleEvent(Event event) {
			handleWidgetAssignment(event);
		}
	};

	private Set<Composite> sashContainers = new HashSet<Composite>();
	private Set<Shell> topWindows = new HashSet<Shell>();
	private ILock lock = Job.getJobManager().newLock();

	@Inject
	IEventBroker eventBroker;

	private Field sashWidthField;

	/**
	 * Updates all
	 * 
	 * @since 1.2
	 */
	public void applyChromeThemeToAllTopWindows() {
		lock.acquire();
		Composite[] array = sashContainers.toArray(new Composite[sashContainers.size()]);
		Shell[] shells = topWindows.toArray(new Shell[topWindows.size()]);
		lock.release();

		for (Composite each : array) {
			try {
				updateSash(each, ChromeThemeConfig.getInstance().getSashWidth());
			} catch (Exception e) {
				ChromeThemeCore.getDefault().log(e);
				e.printStackTrace();
			}
		}

		/*
		 * 13: Vertical padding
		 * https://github.com/jeeeyul/eclipse-themes/issues/issue/13
		 */
		for (Shell shell : shells) {
			try {
				updateMargin(shell);
			} catch (Exception e) {
				ChromeThemeCore.getDefault().log(e);
				e.printStackTrace();
			}
		}
	}

	@PreDestroy
	public void dispose() {
		sashContainers.clear();
		eventBroker.unsubscribe(sashWeightHandler);
	}

	private Field getSashWidthField() throws SecurityException, NoSuchFieldException {
		if (sashWidthField == null) {
			sashWidthField = SashLayout.class.getDeclaredField("sashWidth");
			sashWidthField.setAccessible(true);
		}
		return sashWidthField;
	}

	private void handleWidgetAssignment(Event event) {
		Object widget = event.getProperty(UIEvents.EventTags.NEW_VALUE);
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MPartSashContainer
				&& event.getProperty(UIEvents.EventTags.TYPE) == UIEvents.EventTypes.SET) {
			Object value = widget;

			if (value instanceof Composite) {
				Composite composite = (Composite) value;
				if (composite.getLayout() instanceof SashLayout) {
					trackSashContainer(composite);
					if (isChromeThemeActive()) {
						updateSash(composite, ChromeThemeConfig.getInstance().getSashWidth());
					}
				}
			}
		}

		/*
		 * 13: Vertical padding
		 * https://github.com/jeeeyul/eclipse-themes/issues/issue/13
		 */
		else if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MTrimmedWindow) {
			if (widget instanceof Shell) {
				trackTopWindowShell((Shell) widget);
				if (isChromeThemeActive()) {
					updateMargin((Shell) widget);
				}
			}
		}
	}

	@PostConstruct
	public void init() {
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, sashWeightHandler);
		INSTANCE = this;
	}

	private boolean isChromeThemeActive() {
		return ActiveThemeTracker.getInstance().isChromeThemeActive();
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

	private void trackTopWindowShell(final Shell shell) {
		if (shell == null || shell.isDisposed()) {
			return;
		}

		lock.acquire();
		topWindows.add(shell);
		lock.release();

		shell.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				untrack(shell);
			}
		});
	}

	private void untrack(Composite composite) {
		lock.acquire();
		sashContainers.remove(composite);
		lock.release();
	}

	private void untrack(Shell shell) {
		lock.acquire();
		topWindows.remove(shell);
		lock.release();
	}

	private void updateMargin(Shell widget) {
		TrimmedPartLayout layout = (TrimmedPartLayout) ((Shell) widget).getLayout();
		int marginTop = ChromeThemeConfig.getInstance().getSashWidth();
		if (ChromeThemeConfig.getInstance().usePartShadow()) {
			marginTop += 3;
		}
		layout.gutterTop = marginTop;
		((Shell) widget).layout();
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
}
