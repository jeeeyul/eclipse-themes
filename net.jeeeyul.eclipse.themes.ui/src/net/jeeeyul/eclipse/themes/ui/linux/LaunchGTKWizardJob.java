package net.jeeeyul.eclipse.themes.ui.linux;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * A job that opens {@link DownloadGTKRC2Wizard}.
 * 
 * @author Jeeeyul
 */
public class LaunchGTKWizardJob extends WorkbenchJob {
	/**
	 * singleton
	 */
	public static final LaunchGTKWizardJob INSTANCE = new LaunchGTKWizardJob();

	private LaunchGTKWizardJob() {
		super("Download GTKRC");
		setSystem(true);
		setUser(false);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		new WizardDialog(Display.getDefault().getActiveShell(), new DownloadGTKRC2Wizard()).open();
		return Status.OK_STATUS;
	}

}
