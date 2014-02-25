package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions

class GradPathTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			onPaint = [
				var path = newPath[
					autoRelease()
					addRectangle(10, 10, 100, 100)
				]
				gc.drawGradientPath(path, #[COLOR_RED, COLOR_GREEN, COLOR_WHITE], #[50, 100], false)
			]
		].openAndRunLoop()
		
	}
	
}