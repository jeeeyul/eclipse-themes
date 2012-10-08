package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.ChromePage
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.swt.widgets.Button
import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class OtherPage extends ChromePage {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	Button engravedButton
	Button embossedButton
	
	Button useTrimStackBorderButton
	
	new() {
		super("Others", null)
	}

	override create(Composite control) {
		control=>[
			layout = newGridLayout
			layoutData = FILL_HORIZONTAL
			
			newGroup[
				text = "Drag Handle && Stack Border"
				layout = newGridLayout[
					numColumns = 3
				]
				layoutData = FILL_HORIZONTAL
				
				newLabel[
					text = "Handle Type:"
				]				
				
				engravedButton = newRadioButton[
					text = "Engraved"
				]
				
				embossedButton = newRadioButton[
					text = "Embossed"
				]
				
				useTrimStackBorderButton = newCheckbox[
					text = "Use image border for trim stack."
					layoutData = newGridData[
						horizontalSpan = 3
					]
				]
			]
			
			newLabel[
				text = "New workbench window needs to be open to take effect."
			]
		]
	}
	
	override load(IPreferenceStore preferenceStore) {
		embossedButton.selection = preferenceStore.getBoolean(CHROME_USE_EMBOSSED_DRAG_HANDLE)
		engravedButton.selection = !preferenceStore.getBoolean(CHROME_USE_EMBOSSED_DRAG_HANDLE)
		
		useTrimStackBorderButton.selection = preferenceStore.getBoolean(CHROME_USE_TRIMSTACK_IMAGE_BORDER)
	}
	
	override save(IPreferenceStore preferenceStore) {
		preferenceStore.setValue(CHROME_USE_EMBOSSED_DRAG_HANDLE, embossedButton.selection)
		preferenceStore.setValue(CHROME_USE_TRIMSTACK_IMAGE_BORDER, useTrimStackBorderButton.selection)
	}
	
	override setToDefault(IPreferenceStore preferenceStore) {
			embossedButton.selection = preferenceStore.getDefaultBoolean(CHROME_USE_EMBOSSED_DRAG_HANDLE)
		engravedButton.selection = !preferenceStore.getDefaultBoolean(CHROME_USE_EMBOSSED_DRAG_HANDLE)
		
		useTrimStackBorderButton.selection = preferenceStore.getDefaultBoolean(CHROME_USE_TRIMSTACK_IMAGE_BORDER)
	}
	
}