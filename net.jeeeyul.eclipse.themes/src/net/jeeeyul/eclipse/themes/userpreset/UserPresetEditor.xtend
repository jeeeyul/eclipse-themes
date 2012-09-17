package net.jeeeyul.eclipse.themes.userpreset

import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.swt.widgets.Composite

class UserPresetEditor {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	def private void create(Composite container){
		container.Composite[
			layout = GridLayout
		]
	}
}