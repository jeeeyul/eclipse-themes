package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.HSB
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class EmptyPartPage extends ChromePage {
	extension SWTExtensions = new SWTExtensions
	
	ColorWell emptyPartColorWell
	ColorWell emptyPartOutlineColorWell
	
	new(){
		super("Editor Area", SharedImages::EDITOR_AREA)
	}

	override create(Composite control) {
		control => [
			layout = GridLayout
			Group[
				text = "Empty Editor Area"
				layout = GridLayout[numColumns = 3]
				layoutData = FILL_HORIZONTAL
				
				Label[ text = "Background Color:"]
				emptyPartColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				
				PushButton[
					text = "Change"
					onClick = [
						emptyPartColorWell.showColorPicker()
					]
				]
				
				Label[ text = "Outline Color:"]
				emptyPartOutlineColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				
				PushButton[
					text = "Change"
					onClick = [
						emptyPartOutlineColorWell.showColorPicker()
					]
				]
			]
		]
	}
	def void updatePreview() { 
		
	}

	
	override load(IPreferenceStore store) {
		emptyPartColorWell.selection = new HSB(
			store.getFloat(CHROME_EMPTY_PART_HUE),
			store.getFloat(CHROME_EMPTY_PART_SATURATION), 
			store.getFloat(CHROME_EMPTY_PART_BRIGHTNESS)
		)
		
		emptyPartOutlineColorWell.selection = new HSB(
			store.getFloat(CHROME_EMPTY_PART_OUTLINE_HUE),
			store.getFloat(CHROME_EMPTY_PART_OUTLINE_SATURATION), 
			store.getFloat(CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS)
		)
	}
	
	override save(IPreferenceStore store) {
		store.setValue(CHROME_EMPTY_PART_HUE, emptyPartColorWell.selection.hue)
		store.setValue(CHROME_EMPTY_PART_SATURATION, emptyPartColorWell.selection.saturation)
		store.setValue(CHROME_EMPTY_PART_BRIGHTNESS, emptyPartColorWell.selection.brightness)
		
		store.setValue(CHROME_EMPTY_PART_OUTLINE_HUE, emptyPartOutlineColorWell.selection.hue)
		store.setValue(CHROME_EMPTY_PART_OUTLINE_SATURATION, emptyPartOutlineColorWell.selection.saturation)
		store.setValue(CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS, emptyPartOutlineColorWell.selection.brightness)
	}
	
	override setToDefault(IPreferenceStore store) {
		emptyPartColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_EMPTY_PART_HUE),
			store.getDefaultFloat(CHROME_EMPTY_PART_SATURATION), 
			store.getDefaultFloat(CHROME_EMPTY_PART_BRIGHTNESS)
		)
		
		emptyPartOutlineColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_EMPTY_PART_OUTLINE_HUE),
			store.getDefaultFloat(CHROME_EMPTY_PART_OUTLINE_SATURATION), 
			store.getDefaultFloat(CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS)
		)
	}
	
	def private void showColorPicker(ColorWell well) { 
		var picker = new ColorPicker()
		var original = well.selection
		
		picker.selection = well.selection
		picker.continuosSelectionHandler = [
			well.selection = it
		]
		if(well.getData("lock-hue") == true){
			picker.lockHue = true
		}

		if(picker.open() == IDialogConstants::OK_ID){
			well.selection = picker.selection
		}else{
			well.selection = original
		}
	}
	
}