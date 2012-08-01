package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.swt.graphics.RGB
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.widgets.Button

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*
import net.jeeeyul.eclipse.themes.ui.ColorExtensions

class GradientPage extends AbstractChromePage {
	extension SWTExtensions = new SWTExtensions
	extension ColorExtensions = new ColorExtensions

	ColorWell activeStartColorWell
	ColorWell activeEndColorWell	
	ColorWell activeOutlineColorWell
	
	Button autoActiveEndColorButton
	Button syncActiveEndColorHueButton
	Button autoActiveOutlineColorButton
	Button syncActiveOutlineColorHueButton
	
	ColorWell inactiveStartColorWell
	ColorWell inactiveEndColorWell	
	ColorWell inactiveOutlineColorWell
	
	Button autoInactiveEndColorButton
	Button syncInactiveEndColorHueButton
	Button autoInactiveOutlineColorButton
	Button syncInactiveOutlineColorHueButton
	
	new(){
		super("Part Gradient", SharedImages::PALETTE)
	}

	override create(Composite parent) {
		parent=>[
			layout = GridLayout
			Group[
				layoutData = FILL_HORIZONTAL
				text = "Active Part"
				layout = GridLayout[
					numColumns = 5
					makeColumnsEqualWidth = false
				]
				
				Label[
					text="Gradient Start:"
				]
				
				activeStartColorWell = ColorWell[
					selection = new RGB(255, 0, 0)
					onSelection = [
						syncHueAndComputeAutoColors()
					]
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(activeStartColorWell)
					]
				]
				
				Label[
					layoutData = GridData[
						horizontalSpan = 2
					]
				]
				
				
				Label[
					text="Gradient End:"
				]
				
				activeEndColorWell = ColorWell[
					selection = new RGB(255, 0, 0)
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(activeEndColorWell)
					]
				]
				
				autoActiveEndColorButton = Checkbox[
					text = "Auto"
					onSelection = [
						updateEnablement()
					]
				]
				
				syncActiveEndColorHueButton = Checkbox[
					text = "Sync Hue"
					onSelection = [
						activeEndColorWell.setData("lock-hue", it.selection)
						if(it.selection){
							activeEndColorWell.selection = activeEndColorWell.selection.rewriteHue(activeStartColorWell.selection.hue)
						}
					]
				]
				
				Label[
					text="Outline:"
				]
				
				activeOutlineColorWell = ColorWell[
					selection = new RGB(255, 0, 0)
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(activeOutlineColorWell)
					]
				]
				
				autoActiveOutlineColorButton = Checkbox[
					text = "Auto"
					onSelection = [
						updateEnablement()
					]
				]
				
				syncActiveOutlineColorHueButton = Checkbox[
					text = "Sync Hue"
					onSelection = [
						activeOutlineColorWell.setData("lock-hue", it.selection)
						if(it.selection){
							activeOutlineColorWell.selection = activeOutlineColorWell.selection.rewriteHue(activeStartColorWell.selection.hue)
						}
					]
				]
			]
			
			Group[
				layoutData = FILL_HORIZONTAL
				text = "Inactive Part"
				layout = GridLayout[
					numColumns = 5
					makeColumnsEqualWidth = false
				]
				
				Label[
					text="Gradient Start:"
				]
				
				inactiveStartColorWell = ColorWell[
					selection = new RGB(255, 0, 0)
					onSelection = [
						syncHueAndComputeAutoColors()
					]
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(inactiveStartColorWell)
					]
				]
				
				Label[
					layoutData = GridData[
						horizontalSpan = 2
					]
				]
				
				
				Label[
					text="Gradient End:"
				]
				
				inactiveEndColorWell = ColorWell[
					selection = new RGB(255, 0, 0)
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(inactiveEndColorWell)
					]
				]
				
				autoInactiveEndColorButton = Checkbox[
					text = "Auto"
					onSelection = [
						updateEnablement()
					]
				]
				
				syncInactiveEndColorHueButton = Checkbox[
					text = "Sync Hue"
					onSelection = [
						inactiveEndColorWell.setData("lock-hue", it.selection)
						if(it.selection){
							inactiveEndColorWell.selection = inactiveEndColorWell.selection.rewriteHue(inactiveStartColorWell.selection.hue)
						}
					]
				]
				
				Label[
					text="Outline:"
				]
				
				inactiveOutlineColorWell = ColorWell[
					selection = new RGB(255, 0, 0)
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(inactiveOutlineColorWell)
					]
				]
				
				autoInactiveOutlineColorButton = Checkbox[
					text = "Auto"
					onSelection = [
						updateEnablement()
					]
				]
				
				syncInactiveOutlineColorHueButton = Checkbox[
					text = "Sync Hue"
					onSelection = [
						inactiveOutlineColorWell.setData("lock-hue", it.selection)
						if(it.selection){
							inactiveOutlineColorWell.selection = inactiveOutlineColorWell.selection.rewriteHue(inactiveStartColorWell.selection.hue)
						}
					]
				]
			]
		]
	}
	
	def private void updateEnablement() {
		activeEndColorWell.next.enabled = !autoActiveEndColorButton.selection
		syncActiveEndColorHueButton.enabled = !autoActiveEndColorButton.selection
		activeOutlineColorWell.next.enabled = !autoActiveOutlineColorButton.selection
		syncActiveOutlineColorHueButton.enabled = !autoActiveOutlineColorButton.selection
		
		inactiveEndColorWell.next.enabled = !autoInactiveEndColorButton.selection
		syncInactiveEndColorHueButton.enabled = !autoInactiveEndColorButton.selection
		inactiveOutlineColorWell.next.enabled = !autoInactiveOutlineColorButton.selection
		syncInactiveOutlineColorHueButton.enabled = !autoInactiveOutlineColorButton.selection
	}
	
	def private void syncHueAndComputeAutoColors() {
		if(autoActiveEndColorButton.selection){
			var startRGB = activeStartColorWell.selection
			activeEndColorWell.selection = startRGB.ampSaturation(1.15f).ampBrightness(0.95f)
		}
		
		else if(syncActiveEndColorHueButton.selection){
			activeEndColorWell.selection = activeEndColorWell.selection.rewriteHue(activeStartColorWell.selection.hue)
		}
		
		if(autoActiveOutlineColorButton.selection){
			var startRGB = activeStartColorWell.selection
			activeOutlineColorWell.selection = startRGB.ampSaturation(3f).ampBrightness(0.7f)
		}
		
		else if(syncActiveOutlineColorHueButton.selection){
			activeOutlineColorWell.selection = activeOutlineColorWell.selection.rewriteHue(activeStartColorWell.selection.hue)
		}
		
		if(autoInactiveEndColorButton.selection){
			var startRGB = inactiveStartColorWell.selection
			inactiveEndColorWell.selection = startRGB.ampSaturation(1.15f).ampBrightness(0.95f)
		}
		
		else if(syncInactiveEndColorHueButton.selection){
			inactiveEndColorWell.selection = inactiveEndColorWell.selection.rewriteHue(inactiveStartColorWell.selection.hue)
		}
		
		if(autoInactiveOutlineColorButton.selection){
			var startRGB = inactiveStartColorWell.selection
			inactiveOutlineColorWell.selection = startRGB.ampSaturation(3f).ampBrightness(0.7f)
		}
		
		else if(syncInactiveOutlineColorHueButton.selection){
			inactiveOutlineColorWell.selection = inactiveOutlineColorWell.selection.rewriteHue(inactiveStartColorWell.selection.hue)
		}
	}
	
	

	override load(IPreferenceStore store) {
		activeStartColorWell.selection = new RGB(
			store.getFloat(CHROME_ACTIVE_START_HUE),
			store.getFloat(CHROME_ACTIVE_START_SATURATION), 
			store.getFloat(CHROME_ACTIVE_START_BRIGHTNESS)
		)
		
		activeEndColorWell.selection = new RGB(
			store.getFloat(CHROME_ACTIVE_END_HUE),
			store.getFloat(CHROME_ACTIVE_END_SATURATION), 
			store.getFloat(CHROME_ACTIVE_END_BRIGHTNESS)
		)
		
		activeOutlineColorWell.selection = new RGB(
			store.getFloat(CHROME_ACTIVE_OUTLINE_HUE),
			store.getFloat(CHROME_ACTIVE_OUTLINE_SATURATION), 
			store.getFloat(CHROME_ACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		autoActiveEndColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoActiveOutlineColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		
		inactiveStartColorWell.selection = new RGB(
			store.getFloat(CHROME_INACTIVE_START_HUE),
			store.getFloat(CHROME_INACTIVE_START_SATURATION), 
			store.getFloat(CHROME_INACTIVE_START_BRIGHTNESS)
		)
		
		inactiveEndColorWell.selection = new RGB(
			store.getFloat(CHROME_INACTIVE_END_HUE),
			store.getFloat(CHROME_INACTIVE_END_SATURATION), 
			store.getFloat(CHROME_INACTIVE_END_BRIGHTNESS)
		)
		
		inactiveOutlineColorWell.selection = new RGB(
			store.getFloat(CHROME_INACTIVE_OUTLINE_HUE),
			store.getFloat(CHROME_INACTIVE_OUTLINE_SATURATION), 
			store.getFloat(CHROME_INACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		autoInactiveEndColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoInactiveOutlineColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		
		updateEnablement()
	}
	
	override save(IPreferenceStore store) {
		store.setValue(CHROME_ACTIVE_START_HUE, activeStartColorWell.selection.hue)
		store.setValue(CHROME_ACTIVE_START_SATURATION, activeStartColorWell.selection.saturation)
		store.setValue(CHROME_ACTIVE_START_BRIGHTNESS, activeStartColorWell.selection.brightness)
		
		store.setValue(CHROME_ACTIVE_END_HUE, activeEndColorWell.selection.hue)
		store.setValue(CHROME_ACTIVE_END_SATURATION, activeEndColorWell.selection.saturation)
		store.setValue(CHROME_ACTIVE_END_BRIGHTNESS, activeEndColorWell.selection.brightness)
		
		store.setValue(CHROME_ACTIVE_OUTLINE_HUE, activeOutlineColorWell.selection.hue)
		store.setValue(CHROME_ACTIVE_OUTLINE_SATURATION, activeOutlineColorWell.selection.saturation)
		store.setValue(CHROME_ACTIVE_OUTLINE_BRIGHTNESS, activeOutlineColorWell.selection.brightness)
		
		store.setValue(CHROME_AUTO_ACTIVE_END_COLOR, autoActiveEndColorButton.selection)
		store.setValue(CHROME_AUTO_ACTIVE_OUTLINE_COLOR, autoActiveOutlineColorButton.selection)
		
		store.setValue(CHROME_INACTIVE_START_HUE, inactiveStartColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_START_SATURATION, inactiveStartColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_START_BRIGHTNESS, inactiveStartColorWell.selection.brightness)
		
		store.setValue(CHROME_INACTIVE_END_HUE, inactiveEndColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_END_SATURATION, inactiveEndColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_END_BRIGHTNESS, inactiveEndColorWell.selection.brightness)
		
		store.setValue(CHROME_INACTIVE_OUTLINE_HUE, inactiveOutlineColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_OUTLINE_SATURATION, inactiveOutlineColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_OUTLINE_BRIGHTNESS, inactiveOutlineColorWell.selection.brightness)
		
		store.setValue(CHROME_AUTO_INACTIVE_END_COLOR, autoInactiveEndColorButton.selection)
		store.setValue(CHROME_AUTO_INACTIVE_OUTLINE_COLOR, autoInactiveOutlineColorButton.selection)
	}

	override setToDefault(IPreferenceStore store) {
		activeStartColorWell.selection = new RGB(
			store.getDefaultFloat(CHROME_ACTIVE_START_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_START_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_START_BRIGHTNESS)
		)
		
		activeEndColorWell.selection = new RGB(
			store.getDefaultFloat(CHROME_ACTIVE_END_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_END_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_END_BRIGHTNESS)
		)
		
		activeOutlineColorWell.selection = new RGB(
			store.getDefaultFloat(CHROME_ACTIVE_OUTLINE_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_OUTLINE_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		autoActiveEndColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoActiveOutlineColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		
		inactiveStartColorWell.selection = new RGB(
			store.getDefaultFloat(CHROME_INACTIVE_START_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_START_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_START_BRIGHTNESS)
		)
		
		inactiveEndColorWell.selection = new RGB(
			store.getDefaultFloat(CHROME_INACTIVE_END_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_END_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_END_BRIGHTNESS)
		)
		
		inactiveOutlineColorWell.selection = new RGB(
			store.getDefaultFloat(CHROME_INACTIVE_OUTLINE_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_OUTLINE_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		autoInactiveEndColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoInactiveOutlineColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		
		updateEnablement()
	}
	
	def private void showColorPickerFor(ColorWell well) { 
		var picker = new ColorPicker()
		picker.selection = well.selection
		if(well.getData("lock-hue") == true){
			picker.lockHue = true
		}

		if(picker.open() == IDialogConstants::OK_ID){
			well.selection = picker.selection
		}
	}
}