package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.graphics.Point
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.graphics.Rectangle

class JTabTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			layout = new FillLayout()=>[
				marginWidth = 10
				marginHeight = 10
			]
			background = COLOR_WHITE
			
			newCTabFolder(SWT.CLOSE)[
				tabHeight = 22
				unselectedCloseVisible = false
				setMinimizeVisible(true)
				setMaximizeVisible(true)
				renderer = new JeeeyulsTabRenderer(it) => [
					settings.margins = newInsets(10)
					settings.paddings = newInsets(2)
					settings.borderRadius = 5
					settings.shadowRadius = 5
					settings.shadowPosition = new Point(2, 2)
					settings.shadowColor = COLOR_GRAY.toHSB
					settings.borderWidth = 1
					settings.closeButtonLineWidth = 2
					settings.tabSpacing = 2
					settings.tabItemHorizontalSpacing = 0
					settings.tabItemPaddings = new Rectangle(0, 0, 0, 0)
					
					settings.hoverBackgroundColors = null
					settings.hoverBackgroundPercents = null
					settings.hoverBorderColors = #[HSB.RED, HSB.BLUE]
					settings.hoverBorderPercents = #[100]
					
					settings.unselectedTextShadowColor = COLOR_WHITE.toHSB
					
					settings.borderColors = #[HSB.RED, HSB.BLUE]
					settings.borderPercents = #[100]
				]
				setSelectionBackground(#[COLOR_YELLOW, COLOR_WHITE, COLOR_RED], #[50, 95])
				setBackground(#[COLOR_GRAY, COLOR_WHITE], #[100], true)
				newCTabItem[
					text = "First"
					showClose = true
					image = SharedImages.getImage(SharedImages.BLOCK)
					
					newComposite[
						newCanvas[
//							showTestGrid()
						]
					]
				]
				
				newCTabItem[
					text = "Sec"
					image = SharedImages.getImage(SharedImages.BLOCK)
					
					newComposite[
						newCanvas[
							showTestGrid()
						]
					]
				]
				
				newCTabItem[
					text = "Third"
					image = SharedImages.getImage(SharedImages.BLOCK)
					
					newComposite[
						newCanvas[
							showTestGrid()
						]
					]
				]
				
				newCTabItem[
					text = "Four"
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