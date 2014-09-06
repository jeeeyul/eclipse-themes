package net.jeeeyul.eclipse.themes.ui.linux;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.progress.UIJob;

/**
 * A job that asks user to restart workbench.
 * 
 * @author Jeeeyul
 */
public class AskingRestart extends UIJob {

	/**
	 * Creates an {@link AskingRestart}.
	 */
	public AskingRestart() {
		super("Restart Eclipse");
		setSystem(true);
		setUser(false);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		if (MessageDialog.openQuestion(Display.getDefault().getActiveShell(), "Jeeeyul's Eclipse Themes", "Do you want to restart eclipse now?")) {
			IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);
			try {
				hs.executeCommand("org.eclipse.ui.file.restartWorkbench", null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Status.OK_STATUS;
	}
}
