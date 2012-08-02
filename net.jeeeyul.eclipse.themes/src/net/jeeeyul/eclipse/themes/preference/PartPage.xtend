package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.HSB
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.jface.viewers.ComboViewer
import net.jeeeyul.eclipse.themes.preference.internal.FontNameProvider
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Text
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.viewers.IStructuredSelection

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class PartPage extends AbstractChromePage {
	extension SWTExtensions = new SWTExtensions

	ColorWell activeStartColorWell
	ColorWell activeEndColorWell	
	ColorWell activeOutlineColorWell
	ColorWell activeSelectedTitleColorWell
	ColorWell activeUnselectedTitleColorWell
	
	Button autoActiveEndColorButton
	Button syncActiveEndColorHueButton
	Button autoActiveOutlineColorButton
	Button syncActiveOutlineColorHueButton
	
	ColorWell inactiveStartColorWell
	ColorWell inactiveEndColorWell	
	ColorWell inactiveOutlineColorWell
	ColorWell inactiveSelectedTitleColorWell
	ColorWell inactiveUnselectedTitleColorWell
	
	Button autoInactiveEndColorButton
	Button syncInactiveEndColorHueButton
	Button autoInactiveOutlineColorButton
	Button syncInactiveOutlineColorHueButton
	
	Button previewActiveButton
	Button previewInactiveButton
	
	Button activePartShinyShadowButton
	Button inactivePartShinyShadowButton
	
	PartPreview preview
	ComboViewer fontSelector
	Text fontSizeField
	
	new(){
		super("Part Gradient", SharedImages::PALETTE)
	}

	override create(Composite parent) {
		preview = new PartPreview(tabFolder)
		
		parent=>[
			layout = GridLayout
			Group[
				layoutData = FILL_HORIZONTAL
				text = "Preview Mode"
				layout = GridLayout[
					numColumns = 2
					makeColumnsEqualWidth = false
				]
				
				previewActiveButton = RadioButton[
					text = "Active Part Stack"
					selection = true
					onSelection = [updatePreview]
				]
				
				previewInactiveButton = RadioButton[
					text = "Inactive Part Stack"
					onSelection = [updatePreview]
				]
			]
			
			Group[
				layoutData = FILL_HORIZONTAL
				text = "Font"
				layout = GridLayout[
					numColumns = 4
					makeColumnsEqualWidth = false
				]
				
				Label[text = "Font:"]
				var combo = new Combo(it, SWT::READ_ONLY)=>[
					layoutData = FILL_HORIZONTAL
				]
				fontSelector = new ComboViewer(combo)
				fontSelector.contentProvider = new FontNameProvider()
				fontSelector.input = new Object()
				fontSelector.addSelectionChangedListener[
					updatePreview()
				]
				
				Label[text = "Size:"]
				fontSizeField = TextField[
					layoutData = GridData[
						widthHint = 100
					]
					onModified = [
						updatePreview()
					]
				]
			]
			
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
					onSelection = [
						syncHueAndComputeAutoColors()
						updatePreview()
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
					onSelection = [
						updatePreview()
					]
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
						updateSync()
					]
				]
				
				Label[
					text="Outline:"
				]
				
				activeOutlineColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
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
						updateSync()
					]
				]
				
				Label[
					text = "Selected Tab Title:"
				]
				
				activeSelectedTitleColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(activeSelectedTitleColorWell)
					]
				]
				
				Label[
					layoutData = GridData[
						horizontalSpan = 2
					]
				]
				
				Label[
					text = "Unselected Tab Title:"
				]
				
				activeUnselectedTitleColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(activeUnselectedTitleColorWell)
					]
				]
				
				activePartShinyShadowButton = Checkbox[
					text = "Shiny Shadow"
					layoutData = FILL_HORIZONTAL[horizontalSpan = 2]
					
					onSelection = [
						updatePreview()
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
					onSelection = [
						updatePreview()
					]
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
						updateSync()
					]
				]
				
				Label[
					text="Outline:"
				]
				
				inactiveOutlineColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
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
						updateSync()
					]
				]
				
				Label[
					text = "Selected Tab Title:"
				]
				
				inactiveSelectedTitleColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(inactiveSelectedTitleColorWell)
					]
				]
				
				Label[
					layoutData = GridData[
						horizontalSpan = 2
					]
				]
				
				Label[
					text = "Unselected Tab Title:"
				]
				
				inactiveUnselectedTitleColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				PushButton[
					text = "Change"
					onClick = [
						showColorPickerFor(inactiveUnselectedTitleColorWell)
					]
				]
				
				inactivePartShinyShadowButton = Checkbox[
					text = "Shiny Shadow"
					layoutData = FILL_HORIZONTAL[horizontalSpan = 2]
				]
			]
		]
	}
	def private void updateSync() { 
		activeEndColorWell.setData("lock-hue", syncActiveEndColorHueButton.selection)
		if(syncActiveEndColorHueButton.selection && !autoActiveEndColorButton.selection){
			activeEndColorWell.selection = activeEndColorWell.selection.rewriteHue(activeStartColorWell.selection.hue)
		}
		
		activeOutlineColorWell.setData("lock-hue", syncActiveOutlineColorHueButton.selection)
		if(syncActiveOutlineColorHueButton.selection && !autoActiveOutlineColorButton.selection){
			activeOutlineColorWell.selection = activeOutlineColorWell.selection.rewriteHue(activeStartColorWell.selection.hue)
		}
		
		inactiveEndColorWell.setData("lock-hue", syncInactiveEndColorHueButton.selection)
		if(syncInactiveEndColorHueButton.selection && !autoInactiveEndColorButton.selection){
			inactiveEndColorWell.selection = inactiveEndColorWell.selection.rewriteHue(inactiveStartColorWell.selection.hue)
		}
		
		inactiveOutlineColorWell.setData("lock-hue", syncInactiveOutlineColorHueButton.selection)
		if(syncInactiveOutlineColorHueButton.selection && !autoInactiveOutlineColorButton.selection){
			inactiveOutlineColorWell.selection = inactiveOutlineColorWell.selection.rewriteHue(inactiveStartColorWell.selection.hue)
		}
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
	
	override dispose() {
	}

	override load(IPreferenceStore store) {
		activeStartColorWell.selection = new HSB(
			store.getFloat(CHROME_ACTIVE_START_HUE),
			store.getFloat(CHROME_ACTIVE_START_SATURATION), 
			store.getFloat(CHROME_ACTIVE_START_BRIGHTNESS)
		)
		
		activeEndColorWell.selection = new HSB(
			store.getFloat(CHROME_ACTIVE_END_HUE),
			store.getFloat(CHROME_ACTIVE_END_SATURATION), 
			store.getFloat(CHROME_ACTIVE_END_BRIGHTNESS)
		)
		
		activeOutlineColorWell.selection = new HSB(
			store.getFloat(CHROME_ACTIVE_OUTLINE_HUE),
			store.getFloat(CHROME_ACTIVE_OUTLINE_SATURATION), 
			store.getFloat(CHROME_ACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		activeSelectedTitleColorWell.selection = new HSB(
			store.getFloat(CHROME_ACTIVE_SELECTED_TITLE_HUE),
			store.getFloat(CHROME_ACTIVE_SELECTED_TITLE_SATURATION), 
			store.getFloat(CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS)
		)
		
		activeUnselectedTitleColorWell.selection = new HSB(
			store.getFloat(CHROME_ACTIVE_UNSELECTED_TITLE_HUE),
			store.getFloat(CHROME_ACTIVE_UNSELECTED_TITLE_SATURATION), 
			store.getFloat(CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS)
		)
		
		autoActiveEndColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoActiveOutlineColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		syncActiveEndColorHueButton.selection = store.getBoolean(CHROME_LOCK_ACTIVE_END_HUE)
		syncActiveOutlineColorHueButton.selection = store.getBoolean(CHROME_LOCK_ACTIVE_OUTLINE_HUE)
		
		inactiveStartColorWell.selection = new HSB(
			store.getFloat(CHROME_INACTIVE_START_HUE),
			store.getFloat(CHROME_INACTIVE_START_SATURATION), 
			store.getFloat(CHROME_INACTIVE_START_BRIGHTNESS)
		)
		
		inactiveEndColorWell.selection = new HSB(
			store.getFloat(CHROME_INACTIVE_END_HUE),
			store.getFloat(CHROME_INACTIVE_END_SATURATION), 
			store.getFloat(CHROME_INACTIVE_END_BRIGHTNESS)
		)
		
		inactiveOutlineColorWell.selection = new HSB(
			store.getFloat(CHROME_INACTIVE_OUTLINE_HUE),
			store.getFloat(CHROME_INACTIVE_OUTLINE_SATURATION), 
			store.getFloat(CHROME_INACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		inactiveSelectedTitleColorWell.selection = new HSB(
			store.getFloat(CHROME_INACTIVE_SELECTED_TITLE_HUE),
			store.getFloat(CHROME_INACTIVE_SELECTED_TITLE_SATURATION), 
			store.getFloat(CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS)
		)
		
		inactiveUnselectedTitleColorWell.selection = new HSB(
			store.getFloat(CHROME_INACTIVE_UNSELECTED_TITLE_HUE),
			store.getFloat(CHROME_INACTIVE_UNSELECTED_TITLE_SATURATION), 
			store.getFloat(CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS)
		)
		
		autoInactiveEndColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoInactiveOutlineColorButton.selection = store.getBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		syncInactiveEndColorHueButton.selection = store.getBoolean(CHROME_LOCK_INACTIVE_END_HUE)
		syncInactiveOutlineColorHueButton.selection = store.getBoolean(CHROME_LOCK_INACTIVE_OUTLINE_HUE)
		
		activePartShinyShadowButton.selection = store.getBoolean(CHROME_ACTIVE_UNSELECTED_TITLE_SHINY_SHADOW)
		inactivePartShinyShadowButton.selection = store.getBoolean(CHROME_INACTIVE_UNSELECTED_TITLE_SHINY_SHADOW)
		
		fontSelector.selection = new StructuredSelection(store.getString(CHROME_PART_FONT_NAME))
		fontSizeField.text = Float::toString(store.getFloat(CHROME_PART_FONT_SIZE))
		
		updateEnablement()
		updateSync()
		updatePreview()
	}
	
	def private void updatePreview(){
		if(previewActiveButton.selection){
			preview.gradientStart = activeStartColorWell.selection.toRGB
			preview.gradientEnd = activeEndColorWell.selection.toRGB
			preview.outline = activeOutlineColorWell.selection.toRGB
			preview.selectedTitle = activeSelectedTitleColorWell.selection.toRGB
			preview.unselectedTitle = activeUnselectedTitleColorWell.selection.toRGB
		}else{
			preview.gradientStart = inactiveStartColorWell.selection.toRGB
			preview.gradientEnd = inactiveEndColorWell.selection.toRGB
			preview.outline = inactiveOutlineColorWell.selection.toRGB
			preview.selectedTitle = inactiveSelectedTitleColorWell.selection.toRGB
			preview.unselectedTitle = inactiveUnselectedTitleColorWell.selection.toRGB
		}
		var selectedFontName = (fontSelector.selection as IStructuredSelection).firstElement as String
		
		var fontSize = 9f 
		try{
			fontSize = Float::parseFloat(fontSizeField.text.trim)
		}catch(Exception e){
			fontSize = 9f
		}
		if(fontSize > 20f){
			fontSize = 20f;
		}else if(fontSize < 5f){
			fontSize = 5f;
		}
		
		preview.fontName = selectedFontName
		preview.fontSize = fontSize
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
		
		store.setValue(CHROME_ACTIVE_SELECTED_TITLE_HUE, activeSelectedTitleColorWell.selection.hue)
		store.setValue(CHROME_ACTIVE_SELECTED_TITLE_SATURATION, activeSelectedTitleColorWell.selection.saturation)
		store.setValue(CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS, activeSelectedTitleColorWell.selection.brightness)
		
		store.setValue(CHROME_ACTIVE_UNSELECTED_TITLE_HUE, activeUnselectedTitleColorWell.selection.hue)
		store.setValue(CHROME_ACTIVE_UNSELECTED_TITLE_SATURATION, activeUnselectedTitleColorWell.selection.saturation)
		store.setValue(CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS, activeUnselectedTitleColorWell.selection.brightness)
		
		store.setValue(CHROME_AUTO_ACTIVE_END_COLOR, autoActiveEndColorButton.selection)
		store.setValue(CHROME_AUTO_ACTIVE_OUTLINE_COLOR, autoActiveOutlineColorButton.selection)
		
		store.setValue(CHROME_INACTIVE_START_HUE, inactiveStartColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_START_SATURATION, inactiveStartColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_START_BRIGHTNESS, inactiveStartColorWell.selection.brightness)
		
		store.setValue(CHROME_INACTIVE_END_HUE, inactiveEndColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_END_SATURATION, inactiveEndColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_END_BRIGHTNESS, inactiveEndColorWell.selection.brightness)
		
		store.setValue(CHROME_INACTIVE_SELECTED_TITLE_HUE, inactiveSelectedTitleColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_SELECTED_TITLE_SATURATION, inactiveSelectedTitleColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS, inactiveSelectedTitleColorWell.selection.brightness)
		
		store.setValue(CHROME_INACTIVE_UNSELECTED_TITLE_HUE, inactiveUnselectedTitleColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_UNSELECTED_TITLE_SATURATION, inactiveUnselectedTitleColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS, inactiveUnselectedTitleColorWell.selection.brightness)
		
		store.setValue(CHROME_INACTIVE_OUTLINE_HUE, inactiveOutlineColorWell.selection.hue)
		store.setValue(CHROME_INACTIVE_OUTLINE_SATURATION, inactiveOutlineColorWell.selection.saturation)
		store.setValue(CHROME_INACTIVE_OUTLINE_BRIGHTNESS, inactiveOutlineColorWell.selection.brightness)
		
		store.setValue(CHROME_AUTO_INACTIVE_END_COLOR, autoInactiveEndColorButton.selection)
		store.setValue(CHROME_AUTO_INACTIVE_OUTLINE_COLOR, autoInactiveOutlineColorButton.selection)
		
		
		store.setValue(CHROME_ACTIVE_UNSELECTED_TITLE_SHINY_SHADOW, activePartShinyShadowButton.selection)
		store.setValue(CHROME_INACTIVE_UNSELECTED_TITLE_SHINY_SHADOW, inactivePartShinyShadowButton.selection)
		
		
		var selectedFontName = (fontSelector.selection as IStructuredSelection).firstElement as String
		store.setValue(CHROME_PART_FONT_NAME, selectedFontName)
		
		var fontSize = 9f 
		try{
			fontSize = Float::parseFloat(fontSizeField.text.trim)
		}catch(Exception e){
			fontSize = 9f
		}
		if(fontSize > 20f){
			fontSize = 20f;
		}else if(fontSize < 5f){
			fontSize = 5f;
		}
		
		store.setValue(CHROME_PART_FONT_SIZE, fontSize)
		
	}

	override setToDefault(IPreferenceStore store) {
		activeStartColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_ACTIVE_START_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_START_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_START_BRIGHTNESS)
		)
		
		activeEndColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_ACTIVE_END_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_END_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_END_BRIGHTNESS)
		)
		
		activeOutlineColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_ACTIVE_OUTLINE_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_OUTLINE_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		activeSelectedTitleColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_ACTIVE_SELECTED_TITLE_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_SELECTED_TITLE_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS)
		)
		
		activeUnselectedTitleColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_ACTIVE_UNSELECTED_TITLE_HUE),
			store.getDefaultFloat(CHROME_ACTIVE_UNSELECTED_TITLE_SATURATION), 
			store.getDefaultFloat(CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS)
		)
		
		autoActiveEndColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoActiveOutlineColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		syncActiveEndColorHueButton.selection = store.getDefaultBoolean(CHROME_LOCK_ACTIVE_END_HUE)
		syncActiveOutlineColorHueButton.selection = store.getDefaultBoolean(CHROME_LOCK_ACTIVE_OUTLINE_HUE)
		
		inactiveStartColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_INACTIVE_START_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_START_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_START_BRIGHTNESS)
		)
		
		inactiveEndColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_INACTIVE_END_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_END_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_END_BRIGHTNESS)
		)
		
		inactiveOutlineColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_INACTIVE_OUTLINE_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_OUTLINE_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_OUTLINE_BRIGHTNESS)
		)
		
		inactiveSelectedTitleColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_INACTIVE_SELECTED_TITLE_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_SELECTED_TITLE_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS)
		)
		
		inactiveUnselectedTitleColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_INACTIVE_UNSELECTED_TITLE_HUE),
			store.getDefaultFloat(CHROME_INACTIVE_UNSELECTED_TITLE_SATURATION), 
			store.getDefaultFloat(CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS)
		)
		
		autoInactiveEndColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_END_COLOR)
		autoInactiveOutlineColorButton.selection = store.getDefaultBoolean(CHROME_AUTO_ACTIVE_OUTLINE_COLOR)
		syncInactiveEndColorHueButton.selection = store.getDefaultBoolean(CHROME_LOCK_INACTIVE_END_HUE)
		syncInactiveOutlineColorHueButton.selection = store.getDefaultBoolean(CHROME_LOCK_INACTIVE_OUTLINE_HUE)
		
		activePartShinyShadowButton.selection = store.getDefaultBoolean(CHROME_ACTIVE_UNSELECTED_TITLE_SHINY_SHADOW)
		inactivePartShinyShadowButton.selection = store.getDefaultBoolean(CHROME_INACTIVE_UNSELECTED_TITLE_SHINY_SHADOW)
		
		fontSelector.selection = new StructuredSelection(store.getDefaultString(CHROME_PART_FONT_NAME))
		fontSizeField.text = Float::toString(store.getDefaultFloat(CHROME_PART_FONT_SIZE))
		
		updateEnablement()
		updateSync()
		updatePreview()
	}
	
	def private void showColorPickerFor(ColorWell well) { 
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