package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite

class OthersPage extends AbstractJTPreferencePage {

	new() {
		super("Others")
		this.image = SharedImages.getImage(SharedImages.FILE)
	}

	LineStyleEditor underLineStyleEdit
	ColorWell underLineColorEdit

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout
			newGroup[
				text = "Text Editor"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 2
					makeColumnsEqualWidth = false
				]
				newLabel[text = "Underline Style"]
				underLineStyleEdit = new LineStyleEditor(it) => [
					control.layoutData = FILL_HORIZONTAL
				]
				newLabel[text = "Underline Color"]
				underLineColorEdit = newColorWell[]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		underLineStyleEdit.selection = store.getInt(JTPConstants.TextEditor.UNDER_LINE_STYLE)
		var underLineColor = store.getHSB(JTPConstants.TextEditor.UNDER_LINE_COLOR)
		if(underLineColor != null) {
			underLineColorEdit.selection = underLineColor
		}
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.TextEditor.UNDER_LINE_STYLE, underLineStyleEdit.selection)
		store.setValue(JTPConstants.TextEditor.UNDER_LINE_COLOR, underLineColorEdit.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
