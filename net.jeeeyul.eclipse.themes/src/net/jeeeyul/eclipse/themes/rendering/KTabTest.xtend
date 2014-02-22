package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.SWT

class KTabTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			newCTabFolder[
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
				
				topRight = newComposite[
					layout = new RowLayout
					
					newToolBar[
						newToolItem[
							image = SharedImages.getImage(SharedImages.GITHUB)
						]
						newToolItem[
							image = SharedImages.getImage(SharedImages.GITHUB)
						]
					]
				]
				
			]
			
			
			newCTabFolder[
				renderer = new KTabRenderer(it)
				tabPosition = SWT.BOTTOM
				
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
					layout = new RowLayout
					
					newToolBar[
						newToolItem[
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