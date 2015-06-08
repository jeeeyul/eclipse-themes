package net.jeeeyul.eclipse.themes.test.e4app.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

public class AboutHandler {
	@Execute
	public void execute(ESelectionService selectionService) {
		System.out.println("about!");
	}
}