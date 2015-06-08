
package net.jeeeyul.eclipse.themes.test.e4app.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.workbench.Selector;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class TestHandler {
	@Execute
	public void execute(EModelService modelService, MApplication app) {
		List<MHandledMenuItem> items = modelService.findElements(app, MHandledMenuItem.class, EModelService.ANYWHERE | EModelService.IN_PART,
				new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return true;
					}
				});

		for (MHandledItem each : items) {
			System.out.println(each.getCommand().getCommandName());
		}
	}
	

}