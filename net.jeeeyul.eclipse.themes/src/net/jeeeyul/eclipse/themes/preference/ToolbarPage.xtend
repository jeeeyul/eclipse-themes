package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.AbstractChromePage
import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite

class ToolbarPage extends AbstractChromePage {
	new(){
		super("Toolbar", SharedImages::TOOLBAR)
	}

	override create(Composite parent) {
	}

	override load(IPreferenceStore store) {
	}

	override save(IPreferenceStore store) {
	}

	override setToDefault(IPreferenceStore store) {
	}
}