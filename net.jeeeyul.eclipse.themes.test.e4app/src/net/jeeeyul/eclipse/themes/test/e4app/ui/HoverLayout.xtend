package net.jeeeyul.eclipse.themes.test.e4app.ui

import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.SWT

class HoverLayout extends Layout {

	override protected computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		val size = new Point(0, 0)
		composite.children.forEach [
			var eachSize = it.computeSize(wHint, hHint)
			size.x = Math.max(eachSize.x, size.x)
			size.y = Math.max(eachSize.y, size.y)
		]

		return size
	}

	override protected layout(Composite composite, boolean flushCache) {
		composite.children.forEach [
			var data = it.layoutData as HoverData
			if(data.hover){
				var prefer = it.computeSize(SWT.DEFAULT, SWT.DEFAULT)
				it.location = new Point(0, 0)
				it.size = new Point(composite.clientArea.width, prefer.y)
				it.moveAbove(null)
			}else{
				it.bounds = composite.clientArea
			}
		]
	}
}