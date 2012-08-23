package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.ChromePage
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import net.jeeeyul.eclipse.themes.preference.ChromePreferenceExtensions
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import net.jeeeyul.eclipse.themes.ui.ColorWell
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.widgets.Button
import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class EditorPage extends ChromePage {
	extension SWTExtensions = new SWTExtensions
	extension ChromePreferenceExtensions = new ChromePreferenceExtensions

	Button lineVisibleButton
	Button lineDashButton
	ColorWell lineColorWell
	
	new() {
		super("Editor", SharedImages::EDITOR_AREA)
	}

	override create(Composite control) {
		control=>[
			layout = GridLayout
			
			Label[
				text = "Configurations for Editors"
			]
			
			Group[
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					numColumns = 3
				]
				
				lineVisibleButton = Checkbox[
					text = "Line visible"
					layoutData = FILL_HORIZONTAL[
						horizontalSpan = 3
					]
				]
				
				lineDashButton = Checkbox[
					text = "Dashed Line"
					layoutData = FILL_HORIZONTAL[
						horizontalSpan = 3
					]
				]
				
				Label[
					text = "Line Color:"
				]
				
				lineColorWell = ColorWell[
					
				]
				
				PushButton[
					text = "Change"
					onClick = [
						lineColorWell.showColorPicker()
					]
				]
			]
		]
	}
	
	override load(IPreferenceStore preferenceStore) {
		lineVisibleButton.selection = preferenceStore.getBoolean(CHROME_EDITOR_LINE_VISIBLE)
		lineDashButton.selection = preferenceStore.getBoolean(CHROME_EDITOR_LINE_DASH)
		lineColorWell.selection = preferenceStore.getHSB(CHROME_EDITOR_LINE_COLOR)
	}
	
	override save(IPreferenceStore preferenceStore) {
		preferenceStore.setValue(CHROME_EDITOR_LINE_VISIBLE, lineVisibleButton.selection)
		preferenceStore.setValue(CHROME_EDITOR_LINE_DASH, lineDashButton.selection)
		preferenceStore.setValue(CHROME_EDITOR_LINE_COLOR, lineColorWell.selection)
	}
	
	override setToDefault(IPreferenceStore preferenceStore) {
		lineVisibleButton.selection = preferenceStore.getDefaultBoolean(CHROME_EDITOR_LINE_VISIBLE)
		lineDashButton.selection = preferenceStore.getDefaultBoolean(CHROME_EDITOR_LINE_DASH)
		lineColorWell.selection = preferenceStore.getDefaultHSB(CHROME_EDITOR_LINE_COLOR)
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