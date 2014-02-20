package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle

class KTabRenderer extends CTabFolderRenderer {
	extension KTabRendererHelper = new KTabRendererHelper
	extension SWTExtensions = SWTExtensions.INSTANCE

	var tabHeight = 40

	CTabFolder parent

	new(CTabFolder parent) {
		super(parent)
		this.parent = parent
	}

	override protected computeSize(int part, int state, GC gc, int wHint, int hHint) {
		switch (part) {
			case 99:
				new Point(10, 10)
			case PART_BORDER:
				new Point(5, 5)
			default:
				super.computeSize(part, state, gc, wHint, hHint)
		}
	}

	override protected computeTrim(int part, int state, int x, int y, int width, int height) {
		var result = switch (part) {
			case PART_BODY: {
				super.computeTrim(part, state, x, y, width, height)
				new Rectangle(-10, -tabHeight, 20, tabHeight * 2)
			}
			case PART_HEADER :{
				// x를 음수로 주면 들여 써진다.
				// h로 보정 델타 값.
				new Rectangle(0, 0, 0, 0)
			}
			default: {
				super.computeTrim(part, state, x, y, width, height)
			}
		}
		if(part < 0)
			println('''«part» -> «result»''')
		return result

	}

	override protected draw(int part, int state, Rectangle bounds, GC gc) {
		switch (part) {
			case PART_CHEVRON_BUTTON: {
				var invisibleItemCount = parent.itemCount - parent.items.filter[it.showing].size;
				if(invisibleItemCount > 99) {
					invisibleItemCount = 99
				}
				val numExp = invisibleItemCount.toString()
				val font = newFont("Arial", 10, SWT::BOLD).autoRelease
				val textBox = newRectangle(new Point(0, 0), numExp.computeTextExtent(font))
				textBox.relocateCenterWith(bounds)

				var path = newPath[
					autoRelease()
					addRoundRectangle(bounds, 8)
					addString(numExp, textBox.topLeft, font)
				]

				gc.background = COLOR_DARK_GRAY
				gc.fillPath(path)
			}
			
			case PART_MIN_BUTTON: {
				var bar = bounds.copy
				bar.height = 4
				bar.relocateBottomWith(bounds)
				bar.shrink(4, 0)
				gc.background = COLOR_DARK_GRAY
				gc.fillRectangle(bar)
			}
			
			case PART_MAX_BUTTON:{
				gc.setAntialias(SWT::OFF)
				gc.foreground = COLOR_DARK_GRAY
				gc.lineWidth = 2
				gc.drawRoundRectangle(bounds.shrink(4, 3), 8)
			}
			default:
				super.draw(part, state, bounds, gc)
		}
	}

}
