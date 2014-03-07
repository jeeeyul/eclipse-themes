package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.AbstractJTPreferencePage
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import org.eclipse.swt.custom.CTabFolder
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.eclipse.themes.preference.internal.LineStyleEditor
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.eclipse.themes.SharedImages

class OthersPage extends AbstractJTPreferencePage {

	new() {
		super("Others")
		this.image = SharedImages.getImage(SharedImages.EDITOR_AREA)
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
