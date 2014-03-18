package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite

class SpecialPartStackPage extends AbstractJTPreferencePage {
	GradientEdit emptyFillEdit
	GradientEdit emptyBorderEdit
	Button emptyBorderHideEdit

	GradientEdit editorsFillEdit
	GradientEdit editorsBorderEdit
	Button editorsBorderHideEdit

	new() {
		super("Special")
		this.image = SharedImages.getImage(SharedImages.MAXMIZE)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout
			newGroup[
				text = "Empty Part Stack"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 4
				]
				newLabel[
					text = "Fill"
				]
				emptyFillEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				emptyFillEdit.appendOrderLockButton [
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
				newLabel[
					text = "Border"
				]
				emptyBorderEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				emptyBorderEdit.appendOrderLockButton[]
				emptyBorderHideEdit = newCheckbox[
					text = "Hide"
				]
			]
			newGroup[
				text = "Editors Part Stack"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 4
				]
				newLabel[
					text = "Fill"
				]
				editorsFillEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				editorsFillEdit.appendOrderLockButton [
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
				newLabel[
					text = "Border"
				]
				editorsBorderEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				editorsBorderEdit.appendOrderLockButton[]
				editorsBorderHideEdit = newCheckbox[
					text = "Hide"
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		var emptyFill = store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR)
		if(emptyFill != null) {
			emptyFillEdit.selection = emptyFill
		}

		var emptyBorder = store.getGradient(JTPConstants.EmptyPartStack.BORDER_COLOR)
		if(emptyBorder != null) {
			emptyBorderEdit.selection = emptyBorder
		}

		emptyBorderHideEdit.selection = !store.getBoolean(JTPConstants.EmptyPartStack.BORDER_SHOW)

		var editorsFill = store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR)
		if(editorsFill != null) {
			editorsFillEdit.selection = editorsFill
		}

		var editrosBorder = store.getGradient(JTPConstants.EditorsPartStack.BORDER_COLOR)
		if(editrosBorder != null) {
			editorsBorderEdit.selection = editrosBorder
		}

		editorsBorderHideEdit.selection = !store.getBoolean(JTPConstants.EditorsPartStack.BORDER_SHOW)
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.EmptyPartStack.FILL_COLOR, emptyFillEdit.selection)
		store.setValue(JTPConstants.EmptyPartStack.BORDER_COLOR, emptyBorderEdit.selection)
		store.setValue(JTPConstants.EmptyPartStack.BORDER_SHOW, !emptyBorderHideEdit.selection)

		store.setValue(JTPConstants.EditorsPartStack.FILL_COLOR, editorsFillEdit.selection)
		store.setValue(JTPConstants.EditorsPartStack.BORDER_COLOR, editorsBorderEdit.selection)
		store.setValue(JTPConstants.EditorsPartStack.BORDER_SHOW, !editorsBorderHideEdit.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
