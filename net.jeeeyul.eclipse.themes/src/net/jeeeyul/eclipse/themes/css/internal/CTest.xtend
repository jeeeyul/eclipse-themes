package net.jeeeyul.eclipse.themes.css.internal

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT

class CTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			newCTabFolder(SWT.SINGLE)[
				newCTabItem[
					text = "a"
					newComposite[
						newPushButton[
							text = "a"
						]
					]
				]
			]
		].openAndRunLoop
	}
	
}