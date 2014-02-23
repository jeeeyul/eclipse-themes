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
				renderer = new KTabRenderer(it)
				setSelectionBackground(#[COLOR_BLUE, COLOR_BLUE, COLOR_WHITE, COLOR_WHITE], #[20,20, 100])
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
			
			
			newCTabFolder(SWT.BOTTOM)[
				setSelectionBackground(#[COLOR_BLUE, COLOR_BLUE, COLOR_WHITE, COLOR_GREEN], #[20,20, 100])
				renderer = new KTabRenderer(it)
				
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