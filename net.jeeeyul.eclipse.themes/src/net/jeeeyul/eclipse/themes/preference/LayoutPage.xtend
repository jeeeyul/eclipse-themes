package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Scale

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class LayoutPage extends AbstractChromePage {
	extension SWTExtensions = new SWTExtensions
	
	Button thinSashButton
	Button standardButton
	Button manualButton
	
	Button partShadowButton
	Scale sashWidthScale
	
	new(){
		super("Layout", SharedImages::LAYOUT)
	}

	override create(Composite parent) {
		parent=>[
			layout = GridLayout
			
			Group[
				text = "Sash Width"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout
				
				thinSashButton = RadioButton[
					text = "Thin Sash (Classic, No Shadows)"
				]
				
				standardButton = RadioButton[
					text = "Standard (Shadows)"
				]
				
				manualButton = RadioButton[
					text = "Manual (Advanced)"
					onClick = [updateEnablement]
				]
			]
			
			Group[
				text = "Advanced"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					numColumns = 2
				]
				
				partShadowButton = Checkbox[
					text = "Casts shadows for Parts"
					layoutData = FILL_HORIZONTAL[horizontalSpan = 2]
				]
				
				Label[text = "Sash Width:"]
				sashWidthScale = Scale[
					minimum = 1
					maximum = 15
					pageIncrement = 1
				]
			]
			
			Label[
				text = "Using shadow makes more space to draw shadows."
			]
		]
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
		
		updateEnablement();
		
	}
	
	def private updateEnablement() { 
		partShadowButton.enabled = manualButton.selection
		sashWidthScale.enabled = manualButton.selection
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
		
		updateEnablement();
	}
	
}