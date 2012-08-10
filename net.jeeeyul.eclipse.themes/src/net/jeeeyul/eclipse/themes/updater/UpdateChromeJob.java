package net.jeeeyul.eclipse.themes.updater;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.ui.progress.UIJob;

public class UpdateChromeJob extends UIJob {

	private UpdateOperation operation;

	public UpdateChromeJob(UpdateOperation operation) {
		super("Update Jeeeyul's Theme");
		this.operation = operation;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		ProvisioningUI.getDefaultUI().openUpdateWizard(false, operation, null);
		return Status.OK_STATUS;
	}

}
