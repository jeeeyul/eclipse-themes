package net.jeeeyul.eclipse.themes;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.css.internal.CSSClasses;
import net.jeeeyul.eclipse.themes.internal.Debug;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.ElementContainer;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.xtext.xbase.lib.Functions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class EmptyPartStackProcessor {
	@Inject
	IEventBroker broker;

	@Inject
	IStylingEngine engine;

	@Inject
	EModelService modelService;

	private EventHandler widgetAssignListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (!UIEvents.isSET(event)) {
				return;
			}
			Object e = event.getProperty("ChangedElement");
			if (e instanceof MPartStack) {
				MPartStack ps = (MPartStack) e;
				applyEmptyTag(ps);
			}
		}
	};

	private EventHandler modelChildrenListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (UIEvents.isADD(event) || UIEvents.isREMOVE(event)) {
				Object parent = event.getProperty("ChangedElement");
				if (parent instanceof MPartStack) {
					MPartStack stack = (MPartStack) parent;
					applyTagsAndClasses(stack);
				}
			}
		}
	};

	private EventHandler toggleViewVisibilityListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object sender = event.getProperty("ChangedElement");

			if (sender instanceof MPlaceholder) {
				MPlaceholder placeholder = (MPlaceholder) sender;
				Object parent = placeholder.getParent();
				if (parent instanceof MPartStack) {
					applyTagsAndClasses((MPartStack) parent);
				}
			}

		}
	};

	private void applyEmptyTag(MPartStack stack) {
		boolean hasEmptyTag = stack.getTags().contains("empty");
		boolean hasChild = hasVisibleChild(stack);

		if (hasChild == false && hasEmptyTag == false) {
			stack.getTags().add("empty");
		}

		else if (hasEmptyTag && hasChild) {
			stack.getTags().remove("empty");
		}
	}

	private void applyTagsAndClasses(MPartStack stack) {
		Widget widget = (Widget) stack.getWidget();
		if (widget == null || widget.isDisposed()) {
			return;
		}

		CSSClasses styleClasses = CSSClasses.getStyleClasses(widget);

		boolean hasEmptyTag = stack.getTags().contains("empty");
		boolean hasChild = hasVisibleChild(stack);

		if (hasChild == false && hasEmptyTag == false) {
			stack.getTags().add("empty");
			styleClasses.add("empty");
			engine.setClassname(widget, styleClasses.toString());
			Debug.println("New Empty Part Stack Found!");
		}

		else if (hasEmptyTag && hasChild) {
			stack.getTags().remove("empty");
			styleClasses.remove("empty");
			engine.setClassname(widget, styleClasses.toString());
			Debug.println("Empty Part Stack about to have a new child");
		}
	}

	@PreDestroy
	public void dispose() {
		broker.unsubscribe(widgetAssignListener);
		broker.unsubscribe(modelChildrenListener);

		Debug.println("Empty Part Stack Processor is stopped.");
	}

	@Execute
	public void execute(MApplication app) {
		Debug.println("Empty Part Stack Processor is started");

		// Initial PartStack Rendering. (for e4 App)
		broker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, widgetAssignListener);

		// Catches editor opening and closing
		broker.subscribe(ElementContainer.TOPIC_CHILDREN, modelChildrenListener);

		// Catches view closing.
		broker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, toggleViewVisibilityListener);
	}

	private boolean hasVisibleChild(MPartStack stack) {
		boolean hasChild = IterableExtensions.exists(stack.getChildren(), new Functions.Function1<MStackElement, Boolean>() {
			@Override
			public Boolean apply(MStackElement e) {
				return e.isToBeRendered();
			}
		});
		return hasChild;
	}
}
