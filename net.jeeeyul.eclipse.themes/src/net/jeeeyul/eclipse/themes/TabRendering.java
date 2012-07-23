package net.jeeeyul.eclipse.themes;

import org.eclipse.e4.ui.workbench.renderers.swt.CTabRendering;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

@SuppressWarnings("restriction")
public class TabRendering extends CTabRendering {

	private CTabFolder tabFolder;
	private UpdateCTabFolderJob updateClassesJob;

	public TabRendering(CTabFolder tabFolder) {
		super(tabFolder);
		this.tabFolder = tabFolder;
		this.updateClassesJob = new UpdateCTabFolderJob(tabFolder);
	}

	@Override
	protected void draw(int part, int state, Rectangle bounds, GC gc) {
		CSSClasses oldClasses = CSSClasses.getStyleClasses(tabFolder);
		boolean hasEmptyClass = oldClasses.contains("empty");
		boolean haveToSetEmpty = tabFolder.getItemCount() == 0;

		if (hasEmptyClass != haveToSetEmpty) {
			updateClassesJob.schedule();
		}

		super.draw(part, state, bounds, gc);
	}
}
