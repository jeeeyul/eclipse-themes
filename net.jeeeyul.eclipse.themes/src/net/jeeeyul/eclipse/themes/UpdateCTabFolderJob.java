package net.jeeeyul.eclipse.themes;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.progress.UIJob;

/**
 * Add "empty" class(CSS) into {@link CTabFolder} when there is no item. 
 */
public class UpdateCTabFolderJob extends UIJob {
	
	private CTabFolder folder;
	
	public UpdateCTabFolderJob(CTabFolder folder) {
		super("Update CTabFolder CSS");
		this.folder = folder;
		this.setSystem(true);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor arg0) {
		
		if(folder == null || folder.isDisposed()){
			return Status.OK_STATUS;
		}
		
		CSSClasses classes = CSSClasses.getStyleClasses(folder);
		boolean haveToSetEmpty = folder.getItemCount() == 0;

		if(haveToSetEmpty)
			classes.add("empty");
		else
			classes.remove("empty");

		CSSClasses.setStyleClasses(folder, classes);
		folder.reskin(SWT.ALL);

		return Status.OK_STATUS;
	}
	
	@Override
	public boolean shouldSchedule() {
		return folder != null && !folder.isDisposed();
	}
	
	@Override
	public boolean shouldRun() {
		return shouldSchedule();
	}
}
