package net.jeeeyul.eclipse.themes.internal.linux;

import org.eclipse.jface.action.Action;

public class LaunchGTKWizardAction extends Action {

	public LaunchGTKWizardAction() {
		setText("Launch GTK2.0 RC Wizard");
	}

	@Override
	public void run() {
		LaunchGTKWizardJob.INSTANCE.schedule(500);
	}
}
