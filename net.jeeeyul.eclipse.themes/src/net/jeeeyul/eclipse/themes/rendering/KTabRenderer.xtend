package net.jeeeyul.eclipse.themes.rendering

import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Rectangle

class KTabRenderer extends CTabFolderRenderer {
	CTabFolder parent

	new(CTabFolder parent) {
		super(parent)
		this.parent = parent
	}

	override protected draw(int part, int state, Rectangle bounds, GC gc) {
		super.draw(part, state, bounds, gc)
	}

	override protected computeSize(int part, int state, GC gc, int wHint, int hHint) {
		super.computeSize(part, state, gc, wHint, hHint)
	}

	override protected computeTrim(int part, int state, int x, int y, int width, int height) {
		super.computeTrim(part, state, x, y, width, height)
	}
}