package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.SWT

class SizeBox extends Canvas {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	new(Composite parent, int style) {
		super(parent, style)
		this.onPaint = [paint(it)]
	}

	def paint(extension Event e){
		clientArea
		gc.background = COLOR_MARGENTA
		gc.fill(e.bounds)
		gc.drawTestGrid(clientArea, 10, COLOR_WHITE(), 100);
		gc.drawTestSize(clientArea, SWT::CENTER, SWT::CENTER, COLOR_WHITE, 255)
	}	
}