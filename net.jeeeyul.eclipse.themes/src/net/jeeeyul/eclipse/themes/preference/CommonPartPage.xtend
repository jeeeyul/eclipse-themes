package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.internal.FontNameProvider
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.swt.SWT
import org.eclipse.swt.program.Program
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Scale
import org.eclipse.swt.widgets.Text

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class CommonPartPage extends ChromePage {
	extension SWTExtensions = new SWTExtensions
	ComboViewer fontSelector
	Text fontSizeField
	Scale paddingScale
	Scale cornerRadiusScale
	Label paddingLabel
	Label cornerRadiusLabel
	Button useMruButton
	FontPreview fontPreview

	new() {
		super("Part", SharedImages::PART)
	}

	override create(Composite parent) {
		fontPreview = new FontPreview(tabFolder)
		parent=>[
			layout = GridLayout
			Label[
				text = "Configurations for Part Stack."
			]
			Group[
				layoutData = FILL_HORIZONTAL
				text = "Part Title"
				layout = GridLayout[
					numColumns = 4
					makeColumnsEqualWidth = false
				]
				Label[ text = "Font:" ]
				var combo = new Combo(it, SWT::READ_ONLY)=>[]
				fontSelector = new ComboViewer(combo)
				fontSelector.contentProvider = new FontNameProvider()
				fontSelector.input = new Object()
				fontSelector.addSelectionChangedListener[
					updatePreview()
				]
				Label[ text = "Size:" ]
				fontSizeField = TextField[
					layoutData = GridData[
						widthHint = 50
					]
					onModified = [
						updatePreview()
					]
				]
			]
			// End Group
			
			Group[
				text = "Part Stack Shape"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					numColumns = 3
				]
				
				Label[
					text = "Corner Radius:"
				]
				cornerRadiusLabel = Label[
					text = "10px"
					layoutData = GridData[
						widthHint = 35
					]
				]
				cornerRadiusScale = Scale[
					minimum = 6
					maximum = 25
					selection = 10
					pageIncrement = 1
					onSelection = [
						updatePreview()
					]
				]
				
				Label[
					text = "Part Padding:"
				]
				paddingLabel = Label[
					text = "10px"
				]
				paddingScale = Scale[
					minimum = 0
					maximum = 10
					selection = 0
					pageIncrement = 1
					onSelection = [
						updatePreview()
					]
				]
				
				
			] // GROUP
			
			/*
			 * 35: Expose the mru-visible css property
			 * https://github.com/jeeeyul/eclipse-themes/issues/issue/35
			 */
			useMruButton = Checkbox[
				text = "Make MRU Visible"
				layoutData = GridData[ horizontalSpan = 3 ]
			]
			Link[
				text = "When the MRU visibility turned on,\r\n" 
				+ "the tabs that are visible will be the tabs most recently selected.\r\n"
				+ "<a href=\"http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fswt%2Fcustom%2FCTabFolder.html&anchor=setMRUVisible(boolean)\">Get more information</a>"
				layoutData = GridData[
					horizontalSpan = 3
					horizontalIndent = 20
				]
				addListener(SWT::Selection)[
					Program::launch(it.text)
				]
			]
			
			Label[
				text = "To preview some layout settings, use Apply Button"				
			]
		]
	}

	def void updatePreview() {
		paddingLabel.text = paddingScale.selection + "px"
		cornerRadiusLabel.text = cornerRadiusScale.selection + "px"
		
		var selectedFontName = (fontSelector.selection as IStructuredSelection).firstElement as String
		var fontSize = 9f
		try{
			fontSize = Float::parseFloat(fontSizeField.text.trim)
		}catch(Exception e){
			fontSize = 9f
		}
		if(fontSize > 20f) {
			fontSize = 20f;
		} else if(fontSize < 5f) {
			fontSize = 5f;
		}
		
		fontPreview.fontHeight = fontSize
		fontPreview.fontName = selectedFontName
		fontPreview.run()
	}

	override load(IPreferenceStore store) {
		fontSelector.selection = new StructuredSelection(store.getString(CHROME_PART_FONT_NAME))
		fontSizeField.text = Float::toString(store.getFloat(CHROME_PART_FONT_SIZE))
		cornerRadiusScale.selection = store.getInt(CHROME_PART_STACK_CORNER_RADIUS)
		paddingScale.selection = store.getInt(CHROME_PART_STACK_PADDING)
		useMruButton.selection = store.getBoolean(CHROME_PART_STACK_USE_MRU)
		
		previewLayout()
		updatePreview()
	}

	override save(IPreferenceStore store) {
		var selectedFontName = (fontSelector.selection as IStructuredSelection).firstElement as String
		if(selectedFontName == null || selectedFontName.trim.empty) {
			selectedFontName = display.systemFont.fontData.get(0).name
		}
		store.setValue(CHROME_PART_FONT_NAME, selectedFontName)
		var fontSize = 9f
		try{
			fontSize = Float::parseFloat(fontSizeField.text.trim)
		}catch(Exception e){
			fontSize = 9f
		}
		if(fontSize > 20f) {
			fontSize = 20f;
		} else
			if(fontSize < 5f) {
				fontSize = 5f;
			}
		store.setValue(CHROME_PART_FONT_SIZE, fontSize)
		store.setValue(CHROME_PART_STACK_PADDING, paddingScale.selection)
		store.setValue(CHROME_PART_STACK_CORNER_RADIUS, cornerRadiusScale.selection)
		store.setValue(CHROME_PART_STACK_USE_MRU, useMruButton.selection)
		
		previewLayout()		
	}

	def private void previewLayout(){
		var renderer = tabFolder.renderer as ChromeTabRendering
		renderer.cornerRadius = cornerRadiusScale.selection
		
		var pad = paddingScale.selection
		renderer.setPadding(pad + 5, pad + 5, pad, pad + 7)
		tabFolder.getParent().layout(true, true);
	}

	override setToDefault(IPreferenceStore store) {
		fontSelector.selection = new StructuredSelection(store.getDefaultString(CHROME_PART_FONT_NAME))
		fontSizeField.text = Float::toString(store.getDefaultFloat(CHROME_PART_FONT_SIZE))
		cornerRadiusScale.selection = store.getDefaultInt(CHROME_PART_STACK_CORNER_RADIUS)
		paddingScale.selection = store.getDefaultInt(CHROME_PART_STACK_PADDING)
		useMruButton.selection = store.getDefaultBoolean(CHROME_PART_STACK_USE_MRU)
		updatePreview()
		previewLayout()		
	}

	override dispose() {
		fontPreview.dispose()
	}
}