package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event

class SizeBox extends Canvas {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	new(Composite parent, int style) {
		super(parent, style)
		this.onPaint = [paint(it)]
	}

	def paint(extension Event e){
		gc.background = COLOR_MARGENTA
		gc.foreground = COLOR_WHITE
		gc.fillRectangle(clientArea)
		
		var String text = '''«clientArea.width» x «clientArea.height»'''
		var textBox = newRectangle(new Point(0, 0), gc.textExtent(text))
		textBox.relocateCenterWith(clientArea)
		gc.drawString(text, textBox.topLeft)
	}	
}