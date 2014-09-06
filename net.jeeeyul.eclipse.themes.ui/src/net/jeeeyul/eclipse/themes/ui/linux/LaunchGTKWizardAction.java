package net.jeeeyul.eclipse.themes.ui.linux;

import net.jeeeyul.eclipse.themes.SharedImages;

import org.eclipse.jface.action.Action;

/**
 * An action that opens {@link DownloadGTKRC2Wizard}.
 * 
 * @author Jeeeyul
 */
public class LaunchGTKWizardAction extends Action {

	/**
	 * Creates {@link LaunchGTKWizardAction}
	 */
	public LaunchGTKWizardAction() {
		setText("Launch GTK2.0 RC Wizard");
		setImageDescriptor(SharedImages.getImageDescriptor(SharedImages.DOWNLOAD));
	}

	@Override
	public void run() {
		LaunchGTKWizardJob.INSTANCE.schedule(500);
	}
}
