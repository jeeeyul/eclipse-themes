package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Scale
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import org.eclipse.jface.dialogs.IDialogConstants
import net.jeeeyul.eclipse.themes.ui.HSB

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class ETCPage extends ChromePage {
	extension SWTExtensions = new SWTExtensions
	
	Button thinSashButton
	Button standardButton
	Button manualButton
	
	Button partShadowButton
	Scale sashWidthScale
	
	ColorWell windowBackgroundColorWell
	ColorWell partShadowColorWell
	
	ColorWell emptyPartColorWell
	ColorWell emptyPartOutlineColorWell
	
	new(){
		super("etc", SharedImages::LAYOUT)
	}

	override create(Composite parent) {
		parent=>[
			layout = GridLayout
			
			Group[
				text = "Window"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[numColumns = 3]
				
				Label[
					text = "Background Color:"
				]
				
				windowBackgroundColorWell = ColorWell[
					onSelection = [
						getCompanionPage(typeof(ToolbarPage)).updateAutoColors()
					]
				]
				
				PushButton[
					text = "Change"
					onClick = [
						windowBackgroundColorWell.showColorPicker()
					]
				]
			]
			
			Group[
				text = "Sash Width"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout
				
				thinSashButton = RadioButton[
					text = "Thin Sash (Classic, No Shadows)"
					onSelection = [
						updateEnablement()
						updatePreview()
					]
				]
				
				standardButton = RadioButton[
					text = "Standard (Shadows)"
					onSelection = [
						updateEnablement()
						updatePreview()
					]
				]
				
				manualButton = RadioButton[
					text = "Manual (Advanced)"
					onSelection = [
						updateEnablement()
						updatePreview()
					]
				]
			]
			
			Group[
				text = "Advanced"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					numColumns = 3
				]
				
				partShadowButton = Checkbox[
					text = "Casts shadows for Parts"
					layoutData = FILL_HORIZONTAL[horizontalSpan = 3]
					onSelection = [
						updatePreview()
					]
				]
				
				Label[text = "Sash Width:"]
				sashWidthScale = Scale[
					layoutData = FILL_HORIZONTAL[horizontalSpan = 2]
					minimum = 1
					maximum = 15
					pageIncrement = 1
					
					onSelection = [
						updatePreview()
					]
				]
				
				Label[
					text = "Part Shadow Color:"
				]
				
				partShadowColorWell = ColorWell[
					
				]
				
				PushButton[
					text = "Change"
					onClick = [
						partShadowColorWell.showColorPicker()
					]
				]
			]
			
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
			
			Label[
				text = "Using shadow makes more space to draw shadows."
			]
		]
		
	}
	
	def private void updatePreview() {
		var renderer = getTabFolder.renderer as ChromeTabRendering
		var useShadow = standardButton.selection || (partShadowButton.selection && manualButton.selection)
		renderer.shadowVisible = useShadow
	}

	
	override load(IPreferenceStore store) {
		thinSashButton.selection = false
		standardButton.selection = false;
		manualButton.selection = false
		
		var activeButton = switch(store.getString(CHROME_SASH_PRESET)){
			case CHROME_SASH_PRESET_ADVANCED:
				manualButton
			
			case CHROME_SASH_PRESET_STANDARD:
				standardButton
			
			case CHROME_SASH_PRESET_THIN:
				thinSashButton

		}
		activeButton.selection = true
		
		partShadowButton.selection = store.getBoolean(CHROME_PART_SHADOW)
		sashWidthScale.selection = store.getInt(CHROME_PART_CONTAINER_SASH_WIDTH)
		
		
		windowBackgroundColorWell.selection = new HSB(
			store.getFloat(CHROME_WINDOW_BACKGROUND_HUE),
			store.getFloat(CHROME_WINDOW_BACKGROUND_SATURATION), 
			store.getFloat(CHROME_WINDOW_BACKGROUND_BRIGHTNESS)
		)
		
		partShadowColorWell.selection = new HSB(
			store.getFloat(CHROME_PART_SHADOW_HUE),
			store.getFloat(CHROME_PART_SHADOW_SATURATION), 
			store.getFloat(CHROME_PART_SHADOW_BRIGHTNESS)
		)
		
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
		
		updateEnablement();
		
	}
	
	def private updateEnablement() { 
		partShadowButton.enabled = manualButton.selection
		sashWidthScale.enabled = manualButton.selection
		partShadowColorWell.enabled = manualButton.selection || standardButton.selection
	}

	
	override save(IPreferenceStore store) {
		var activeSashPreset = if(thinSashButton.selection){
			CHROME_SASH_PRESET_THIN
		}else if(standardButton.selection){
			CHROME_SASH_PRESET_STANDARD
		}else{
			CHROME_SASH_PRESET_ADVANCED
		}
		store.setValue(CHROME_SASH_PRESET, activeSashPreset)
		store.setValue(CHROME_PART_SHADOW, partShadowButton.selection)
		store.setValue(CHROME_PART_CONTAINER_SASH_WIDTH, sashWidthScale.selection)
		
		store.setValue(CHROME_WINDOW_BACKGROUND_HUE, windowBackgroundColorWell.selection.hue);
		store.setValue(CHROME_WINDOW_BACKGROUND_SATURATION, windowBackgroundColorWell.selection.saturation);
		store.setValue(CHROME_WINDOW_BACKGROUND_BRIGHTNESS, windowBackgroundColorWell.selection.brightness);
		
		store.setValue(CHROME_PART_SHADOW_HUE, partShadowColorWell.selection.hue);
		store.setValue(CHROME_PART_SHADOW_SATURATION, partShadowColorWell.selection.saturation);
		store.setValue(CHROME_PART_SHADOW_BRIGHTNESS, partShadowColorWell.selection.brightness);
		
		store.setValue(CHROME_EMPTY_PART_HUE, emptyPartColorWell.selection.hue)
		store.setValue(CHROME_EMPTY_PART_SATURATION, emptyPartColorWell.selection.saturation)
		store.setValue(CHROME_EMPTY_PART_BRIGHTNESS, emptyPartColorWell.selection.brightness)
		
		store.setValue(CHROME_EMPTY_PART_OUTLINE_HUE, emptyPartOutlineColorWell.selection.hue)
		store.setValue(CHROME_EMPTY_PART_OUTLINE_SATURATION, emptyPartOutlineColorWell.selection.saturation)
		store.setValue(CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS, emptyPartOutlineColorWell.selection.brightness)
	}
	
	override setToDefault(IPreferenceStore store) {
		thinSashButton.selection = false
		standardButton.selection = false;
		manualButton.selection = false
		
		var activeButton = switch(store.getDefaultString(CHROME_SASH_PRESET)){
			case CHROME_SASH_PRESET_ADVANCED:
				manualButton
			
			case CHROME_SASH_PRESET_STANDARD:
				standardButton
			
			case CHROME_SASH_PRESET_THIN:
				thinSashButton

		}
		activeButton.selection = true
		
		partShadowButton.selection = store.getDefaultBoolean(CHROME_PART_SHADOW)
		sashWidthScale.selection = store.getDefaultInt(CHROME_PART_CONTAINER_SASH_WIDTH)
			
		windowBackgroundColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_WINDOW_BACKGROUND_HUE),
			store.getDefaultFloat(CHROME_WINDOW_BACKGROUND_SATURATION), 
			store.getDefaultFloat(CHROME_WINDOW_BACKGROUND_BRIGHTNESS)
		)
		
		partShadowColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_PART_SHADOW_HUE),
			store.getDefaultFloat(CHROME_PART_SHADOW_SATURATION), 
			store.getDefaultFloat(CHROME_PART_SHADOW_BRIGHTNESS)
		)
		
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
		
		updateEnablement()
		updatePreview()
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
	
	def getWindowBackgroundColorWell(){
		return windowBackgroundColorWell
	}
	
}