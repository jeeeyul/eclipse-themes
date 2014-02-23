package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.SWT

class KTabTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			newCTabFolder(SWT.CLOSE)[
				tabHeight = 25
				renderer = new KTabRenderer(it) => [
					settings.margins = newInsets(5)
					settings.borderRadius = 8
					settings.paddings = newInsets(2)
					settings.borderColor = COLOR_BLACK.toHSB
				]
				setSelectionBackground(#[COLOR_YELLOW, COLOR_WHITE], #[100])
				setBackground(#[COLOR_GRAY, COLOR_WHITE], #[100], true)
				newCTabItem[
					text = "First"
					image = SharedImages.getImage(SharedImages.ADD)
					
					newComposite[
						newCanvas[
							showTestGrid()
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
					showTestGrid()
					
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
			
			
			newCTabFolder[
				foreground = COLOR_WHITE
				selectionForeground = COLOR_WHITE
				setSelectionBackground(#[COLOR_GRAY, COLOR_GRAY, COLOR_BLACK, COLOR_BLACK], #[ 17, 22, 100])
				setBackground(#[COLOR_DARK_GRAY, COLOR_BLACK], #[100])
				renderer = new KTabRenderer(it)=>[
					settings.borderColor = COLOR_BLACK.toHSB
					settings.innerBorderColor = COLOR_BLACK.toHSB
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
				
				newCTabItem[
					text = "First"
					image = SharedImages.getImage(SharedImages.ADD)
					
					newComposite[
						newCanvas[
							showTestGrid()
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
				newCTabItem[
					text = "First"
					image = SharedImages.getImage(SharedImages.ADD)
					
					newComposite[
						newCanvas[
							showTestGrid()
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
				newCTabItem[
					text = "First"
					image = SharedImages.getImage(SharedImages.ADD)
					
					newComposite[
						newCanvas[
							showTestGrid()
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
				
			]
		].openAndRunLoop()
	}
	
}