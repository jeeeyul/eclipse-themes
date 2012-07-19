package net.jeeeyul.eclipse.themes

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.ui.progress.UIJob

/**
 * Add "empty" class(CSS) into {@link CTabFolder} when there is no item. 
 */
class UpdateCTabFolderJob extends UIJob{
	extension CSSExtension = new CSSExtension()
	
	CTabFolder folder
	
	new(CTabFolder folder) {
		super("Update CTabFolder CSS")
		this.folder = folder
		system = true
	}

	override runInUIThread(IProgressMonitor monitor) {
		if(folder == null || folder.disposed){
			return Status::OK_STATUS
		}
		
		var classes = folder.styleClasses
		var haveToSetEmpty = folder.itemCount == 0
		
		if(haveToSetEmpty){
			classes += "empty"
		}else{
			classes.remove("empty")
		}
		
		folder.styleClasses = classes
		folder.reskin(SWT::ALL)
		
		return Status::OK_STATUS
	}
	
	override shouldSchedule() {
		folder != null && !folder.disposed
	}
	
	override shouldRun() {
		shouldSchedule()
	}
}