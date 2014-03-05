package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite

class EmptyPartStackPage extends AbstractJTPreferencePage {
	GradientEdit fillEdit
	GradientEdit borderEdit
	Button hideBorderButton

	new() {
		super("Empty Stack")
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[
				numColumns = 4
			]
			newLabel[
				text = "Fill"
			]
			fillEdit = newGradientEdit[
				layoutData = FILL_HORIZONTAL
			]
			fillEdit.appendOrderLockButton [
				layoutData = newGridData[
					horizontalSpan = 2
				]
			]
			newLabel[
				text = "Border"
			]
			borderEdit = newGradientEdit[
				layoutData = FILL_HORIZONTAL
			]
			borderEdit.appendOrderLockButton[]
			hideBorderButton = newCheckbox[
				text = "Hide"
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		var fill = store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR)
		if(fill != null) {
			fillEdit.selection = fill
		}

		var border = store.getGradient(JTPConstants.EmptyPartStack.BORDER_COLOR)
		if(border != null) {
			borderEdit.selection = border
		}

		hideBorderButton.selection = !store.getBoolean(JTPConstants.EmptyPartStack.BORDER_SHOW)
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.EmptyPartStack.FILL_COLOR, fillEdit.selection)
		store.setValue(JTPConstants.EmptyPartStack.BORDER_COLOR, borderEdit.selection)
		store.setValue(JTPConstants.EmptyPartStack.BORDER_SHOW, !hideBorderButton.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
