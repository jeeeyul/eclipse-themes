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

class PartTitlePage extends AbstractChromePage {
	extension SWTExtensions = new SWTExtensions
	
	ComboViewer fontSelector
	
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
		]
	}
	
	override load(IPreferenceStore store) {
		fontSelector.selection = new StructuredSelection(store.getString(CHROME_PART_FONT_NAME))
	}
	
	override save(IPreferenceStore store) {
	}
	
	override setToDefault(IPreferenceStore store) {
	}
	
}