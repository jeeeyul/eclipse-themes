package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.ChromePage
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.swt.widgets.Button
import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.widgets.Control

class OtherPage extends ChromePage {
	extension SWTExtensions = SWTExtensions::INSTANCE
	extension ChromePreferenceExtensions = new ChromePreferenceExtensions
	
	Button useWBColorAsStatusBarColorButton;
	ColorWell statusBarBackgroundColorWell;
	
	Button useStatusBarOutlineButton;
	ColorWell statusBarOutlineColorWell;
	
	new() {
		super(Messages::OTHERS, null)
	}

	override create(Composite control) {
		control=>[
			layout = newGridLayout
			layoutData = FILL_HORIZONTAL
			
			newGroup[
				text = Messages::STATUS_BAR
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 4
				]
				
				
				newLabel[
					text = Messages::BACKGROUND + ":"
				]
				
				statusBarBackgroundColorWell = newColorWell[
					onSelection = [
						updatePreview()
					]
				]
				
				newPushButton[
					text = Messages::CHANGE
					onClick = [
						statusBarBackgroundColorWell.showColorPicker()
					]
				]
				
				useWBColorAsStatusBarColorButton = newCheckbox[
					text = Messages::USE_WINDOW_BACKGROUND
					onSelection = [
						updateEnablement()
					]
				]
				
				newLabel[
					text = Messages::OUTLINE + ":"
				]
				
				statusBarOutlineColorWell = newColorWell[
					updatePreview()
				]
				
				newPushButton[
					text = Messages::CHANGE
					onClick = [
						statusBarOutlineColorWell.showColorPicker()
					]
				]
				
				useStatusBarOutlineButton = newCheckbox[
					text = Messages::SHOW_OUTLINE
					onSelection = [
						updateEnablement()
					]
				]
				
			] // End of Group
		]
	}
	
	override load(IPreferenceStore store) {
		useWBColorAsStatusBarColorButton.selection = store.getBoolean(CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND)
		statusBarBackgroundColorWell.selection = store.getHSB(CHROME_STATUS_BAR_BACKGROUND_COLOR)
		useStatusBarOutlineButton.selection = store.getBoolean(CHROME_USE_STATUS_BAR_OUTLINE)
		statusBarOutlineColorWell.selection = store.getHSB(CHROME_STATUS_BAR_OUTLINE_COLOR)
		
		updateEnablement()
		updatePreview()
	}
	
	override save(IPreferenceStore store) {
		store.setValue(CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND, useWBColorAsStatusBarColorButton.selection)
		store.setValue(CHROME_STATUS_BAR_BACKGROUND_COLOR, statusBarBackgroundColorWell.selection)
		store.setValue(CHROME_USE_STATUS_BAR_OUTLINE, useStatusBarOutlineButton.selection)
		store.setValue(CHROME_STATUS_BAR_OUTLINE_COLOR, statusBarOutlineColorWell.selection)
	}
	
	override setToDefault(IPreferenceStore store) {
		useWBColorAsStatusBarColorButton.selection = store.getDefaultBoolean(CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND)
		statusBarBackgroundColorWell.selection = store.getDefaultHSB(CHROME_STATUS_BAR_BACKGROUND_COLOR)
		useStatusBarOutlineButton.selection = store.getDefaultBoolean(CHROME_USE_STATUS_BAR_OUTLINE)
		statusBarOutlineColorWell.selection = store.getDefaultHSB(CHROME_STATUS_BAR_OUTLINE_COLOR)
		
		updateEnablement()
		updatePreview()
	}
	
	def Control next(Control control){
		var index = control.parent.children.indexOf(control)
		return control.parent.children.get(index + 1)
	}
	
	def void updateEnablement() {
		statusBarBackgroundColorWell.next.enabled = !useWBColorAsStatusBarColorButton.selection
	}
	
	def updatePreview(){
		
	}
	
	def private void showColorPicker(ColorWell well) {
		var picker = new ColorPicker()
		var original = well.selection
		picker.selection = well.selection
		picker.continuosSelectionHandler = [
			well.selection = it
		]
		
		if(well.getData("lock-hue") == true) {
			picker.lockHue = true
		}
		if(picker.open() == IDialogConstants::OK_ID) {
			well.selection = picker.selection
		} else {
			well.selection = original
		}
	}
	
}