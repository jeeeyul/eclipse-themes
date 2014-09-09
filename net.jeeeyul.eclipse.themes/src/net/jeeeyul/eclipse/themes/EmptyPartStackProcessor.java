package net.jeeeyul.eclipse.themes;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.css.internal.CSSClasses;
import net.jeeeyul.eclipse.themes.internal.Debug;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.ElementContainer;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Widget;
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
			Object parent = event.getProperty("ChangedElement");
			if (parent instanceof MPartStack) {
				MPartStack stack = (MPartStack) parent;
				applyTagsAndClasses(stack);
			}
		}
	};

	@PreDestroy
	public void dispose() {
		broker.unsubscribe(widgetAssignListener);
		broker.unsubscribe(modelChildrenListener);

		Debug.println("Empty Part Stack Processor is stopped.");
	}

	private void applyEmptyTag(MPartStack ps) {
		if (ps.getChildren().size() == 0) {
			if (!ps.getTags().contains("empty")) {
				ps.getTags().add("empty");
			}
		}
	}

	private void applyTagsAndClasses(MPartStack stack) {
		Widget widget = (Widget) stack.getWidget();
		CSSClasses styleClasses = CSSClasses.getStyleClasses(widget);

		if (stack.getChildren().size() == 0) {
			stack.getTags().add("empty");
			styleClasses.add("empty");
			engine.setClassname(widget, styleClasses.toString());
		}

		else {
			stack.getTags().remove("empty");
			styleClasses.remove("empty");
			engine.setClassname(widget, styleClasses.toString());
		}
	}

	@Execute
	public void execute(MApplication app) {
		Debug.println("Empty Part Stack Processor is started");

		broker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, widgetAssignListener);
		broker.subscribe(ElementContainer.TOPIC_CHILDREN, modelChildrenListener);
	}
}
