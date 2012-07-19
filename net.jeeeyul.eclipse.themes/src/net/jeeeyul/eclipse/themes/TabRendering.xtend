package net.jeeeyul.eclipse.themes

import org.eclipse.e4.ui.workbench.renderers.swt.CTabRendering
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Rectangle

class TabRendering extends CTabRendering {
	extension CSSExtension = new CSSExtension()
	
	CTabFolder tabFolder
	UpdateCTabFolderJob updateClassesJob 
	
	new(CTabFolder tabFolder) {
		super(tabFolder)
		this.tabFolder = tabFolder
		this.updateClassesJob = new UpdateCTabFolderJob(tabFolder)
	}
	
	override protected draw(int part, int state, Rectangle bounds, GC gc) {
		var oldClasses = tabFolder.styleClasses
		var hasEmptyClass = oldClasses.contains("empty")
		var haveToSetEmpty = tabFolder.itemCount == 0
		
		if(hasEmptyClass != haveToSetEmpty){
			updateClassesJob.schedule()
		}
		
		super.draw(part, state, bounds, gc)
	}
}