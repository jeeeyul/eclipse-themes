package net.jeeeyul.eclipse.themes.ui.preference.internal

import java.util.Locale
import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.program.Program
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Scale

class PartStackETCPage extends AbstractJTPreferencePage {
	GradientEdit emptyFillEdit
	GradientEdit emptyBorderEdit
	Button emptyBorderHideEdit

	GradientEdit editorsFillEdit
	GradientEdit editorsBorderEdit
	Button editorsBorderHideEdit

	ColorWell dragFeedbackColorWell
	Scale dragFeedbackOpacityScale
	Label dragFeedbackOpacityLabel

	Button mruVisibilityButton

	new() {
		super("Others")
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
			newGroup[
				text = "Other Colors"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 3
				]
				newLabel[
					text = "Drag Feedback"
				]
				dragFeedbackColorWell = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
				newLabel[
					text = "Opacity"
				]
				dragFeedbackOpacityScale = newScale[
					minimum = 0
					maximum = 255
					layoutData = FILL_HORIZONTAL
					onSelection = [
						requestFastUpdatePreview
					]
				]
				dragFeedbackOpacityLabel = newLabel[
					text = "100%"
				]
			]
			newComposite[
				layout = newGridLayout[
					numColumns = 1
					marginHeight = 0
					marginWidth = 0
				]
				layoutData = newGridData[]
				mruVisibilityButton = newCheckbox[
					text = "MRU Visibility"
				]
				
				val mruLink = "http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fswt%2Fcustom%2FCTabFolder.html&anchor=setMRUVisible(boolean)"
				newLink[
					layoutData = newGridData[
						horizontalIndent = 18
					]
					text = '''Get details from <a href="«mruLink»">JavaDoc of CTabFolder</a>'''
					onSelection = [
						Program.launch(it.text)
					]
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		var percentText = String.format(Locale.ENGLISH, "%3.0f %%", (dragFeedbackOpacityScale.selection / 255.0) * 100)
		dragFeedbackOpacityLabel.text = percentText
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		var emptyFill = store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR)
		if(emptyFill !== null) {
			emptyFillEdit.selection = emptyFill
		}

		var emptyBorder = store.getGradient(JTPConstants.EmptyPartStack.BORDER_COLOR)
		if(emptyBorder !== null) {
			emptyBorderEdit.selection = emptyBorder
		}

		emptyBorderHideEdit.selection = !store.getBoolean(JTPConstants.EmptyPartStack.BORDER_SHOW)

		var editorsFill = store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR)
		if(editorsFill !== null) {
			editorsFillEdit.selection = editorsFill
		}

		var editrosBorder = store.getGradient(JTPConstants.EditorsPartStack.BORDER_COLOR)
		if(editrosBorder !== null) {
			editorsBorderEdit.selection = editrosBorder
		}

		editorsBorderHideEdit.selection = !store.getBoolean(JTPConstants.EditorsPartStack.BORDER_SHOW)

		dragFeedbackColorWell.selection = store.getHSB(JTPConstants.Others.DRAG_FEEDBACK_COLOR)
		dragFeedbackOpacityScale.selection = store.getInt(JTPConstants.Others.DRAG_FEEDBACK_ALPHA)

		mruVisibilityButton.selection = store.getBoolean(JTPConstants.Others.MRU_VISIBLE)
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.EmptyPartStack.FILL_COLOR, emptyFillEdit.selection)
		store.setValue(JTPConstants.EmptyPartStack.BORDER_COLOR, emptyBorderEdit.selection)
		store.setValue(JTPConstants.EmptyPartStack.BORDER_SHOW, !emptyBorderHideEdit.selection)

		store.setValue(JTPConstants.EditorsPartStack.FILL_COLOR, editorsFillEdit.selection)
		store.setValue(JTPConstants.EditorsPartStack.BORDER_COLOR, editorsBorderEdit.selection)
		store.setValue(JTPConstants.EditorsPartStack.BORDER_SHOW, !editorsBorderHideEdit.selection)

		store.setValue(JTPConstants.Others.DRAG_FEEDBACK_COLOR, dragFeedbackColorWell.selection)
		store.setValue(JTPConstants.Others.DRAG_FEEDBACK_ALPHA, dragFeedbackOpacityScale.selection)

		store.setValue(JTPConstants.Others.MRU_VISIBLE, mruVisibilityButton.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
