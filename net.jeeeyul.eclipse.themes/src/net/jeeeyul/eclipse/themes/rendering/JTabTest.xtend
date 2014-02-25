package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.graphics.Point

class JTabTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			layout = new FillLayout()=>[
				marginWidth = 10
				marginHeight = 10
			]
			background = COLOR_WHITE
			
			newCTabFolder(SWT.BOTTOM)[
				tabHeight = 22
				renderer = new JeeeyulsTabRenderer(it) => [
					settings.margins = newInsets(10)
					settings.paddings = newInsets(50)
					settings.borderRadius = 15
					settings.shadowRadius = 5
					settings.shadowPosition = new Point(2, 2)
					settings.shadowColor = COLOR_GRAY.toHSB
					settings.selectedBorderColor = COLOR_BLACK.toHSB
					settings.unselectedBorderColor = COLOR_RED.toHSB
					settings.borderColor = COLOR_BLACK.toHSB
					settings.borderWidth = 1
					settings.closeButtonLineWidth = 2
					settings.tabSpacing = -1
					
					settings.hoverBackgroundColors = #[COLOR_RED.toHSB, COLOR_GREEN.toHSB]
					settings.hoverBackgroundPercents = #[100]
					settings.hoverBorderColor = COLOR_MARGENTA.toHSB
				]
				setSelectionBackground(#[COLOR_YELLOW, COLOR_WHITE, COLOR_RED], #[50, 95])
				setBackground(#[COLOR_GRAY, COLOR_WHITE], #[100], true)
				newCTabItem[
					text = "First"
					showClose = true
					image = SharedImages.getImage(SharedImages.ADD)
					
					newComposite[
						newCanvas[
//							showTestGrid()
						]
					]
				]
				
				newCTabItem[
					text = "First"
					image = SharedImages.getImage(SharedImages.ADD)
					
					newComposite[
						newCanvas[
							showTestGrid()
						]
					]
				]
				
				topRight = newComposite[
					layout = new RowLayout=>[
						marginTop = 0
						marginBottom = 0
						marginLeft = 0
						marginRight = 0
						marginWidth = 0
						marginHeight = 0
						spacing = 0
					]
//					showTestGrid()
					
					newToolBar[
						newToolItem(SWT.CHECK)[
							selection = true
							image = SharedImages.getImage(SharedImages.GITHUB)
						]
						newToolItem[
							image = SharedImages.getImage(SharedImages.GITHUB)
						]
					]
				]
			]
		].openAndRunLoop()
	}
	
}