package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.internal.FontNameProvider
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Composite
import org.eclipse.jface.viewers.StructuredSelection
import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.FontData

class PartTitlePage extends AbstractChromePage {
	extension SWTExtensions = new SWTExtensions
	
	ComboViewer fontSelector
	Font previewFont
	
	new(){
		super("Part Title", SharedImages::FONT)
	}

	override create(Composite parent) {
		parent => [
			layout = GridLayout[
				numColumns = 2
			]
			Label[
				text = "Font Name:"
			]
			var combo = new Combo(it, SWT::BORDER)=>[
				layoutData = FILL_HORIZONTAL
			]
			fontSelector = new ComboViewer(combo)
			fontSelector.contentProvider = new FontNameProvider()
			fontSelector.input = new Object()
			fontSelector.addSelectionChangedListener[updatePreview()]
		]
	}
	
	def void updatePreview() {
		var String selection = (fontSelector.selection as IStructuredSelection).firstElement as String
		previewFont.safeDispose()
		var fontData = new FontData()
		fontData.name = selection
		fontData.height = 9
		previewFont = new Font(tabFolder.display, fontData)
		tabFolder.setFont(previewFont)
	}

	
	override load(IPreferenceStore store) {
		fontSelector.selection = new StructuredSelection(store.getString(CHROME_PART_FONT_NAME))
	}
	
	override save(IPreferenceStore store) {
	}
	
	override setToDefault(IPreferenceStore store) {
	}
	
	override dispose() {
		previewFont.safeDispose()
	}
	
}