package net.jeeeyul.eclipse.themes.test.e4app;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class ResetPerspectiveHandler {
	@Execute
	public void execute(EModelService modelService, MApplication application,
			MWindow window) {
		MPerspectiveStack stack = (MPerspectiveStack) modelService.find(
				"net.jeeeyul.eclipse.themes.test.e4app.perspectivestack",
				application);
		MPerspective backup = (MPerspective) modelService.find(
				"net.jeeeyul.eclipse.themes.test.e4app.perspective.backup",
				application);
		MPerspective live = (MPerspective) modelService.find(
				"net.jeeeyul.eclipse.themes.test.e4app.perspective.live",
				application);

		modelService.removePerspectiveModel(live, window);

		MPerspective newLive = (MPerspective) modelService.cloneElement(backup,
				null);
		stack.getChildren().add(newLive);
		stack.setSelectedElement(newLive);
	}
}