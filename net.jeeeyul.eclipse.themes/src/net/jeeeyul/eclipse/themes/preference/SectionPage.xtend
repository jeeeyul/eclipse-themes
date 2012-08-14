package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.ChromePage
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import org.eclipse.jface.dialogs.IDialogConstants

class SectionPage extends ChromePage {
	extension SWTExtensions = new SWTExtensions()
	
	ColorWell sectionGradientColorWell
	ColorWell sectionBorderColorWell
	ColorWell sectionTitleColorWell
	ColorWell sectionTitleHighlightColorWell
	
	new() {
		super("Section", SharedImages::EDITOR_AREA);
	}

	override create(Composite control) {
		control =>[
			layout = GridLayout
			
			Group[
				text = "Section"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					numColumns = 3
				]
				
				Label[
					text = "Section Gradient"
				]
				
				sectionGradientColorWell = ColorWell[
					
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPicker(sectionGradientColorWell)
					]
				]
				
				Label[
					text = "Section Border"
				]
				
				sectionBorderColorWell = ColorWell[
					
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPicker(sectionBorderColorWell)
					]
				]
				
				Label[
					text = "Section Title"
				]
				
				sectionTitleColorWell = ColorWell[
					
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPicker(sectionTitleColorWell)
					]
				]
				
				Label[
					text = "Section Title Highlight"
				]
				
				sectionTitleHighlightColorWell = ColorWell[
					
				]
				
				PushButton[
					text = "Change"
					onClick = [
						showColorPicker(sectionTitleHighlightColorWell)
					]
				]
			]
		]
	}

	override load(IPreferenceStore preferenceStore) {
	}

	override save(IPreferenceStore preferenceStore) {
	}

	override setToDefault(IPreferenceStore preferenceStore) {
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