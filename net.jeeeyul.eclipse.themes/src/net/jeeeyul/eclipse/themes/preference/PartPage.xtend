package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.graphics.Rectangle

class PartPage extends AbstractJTPreferencePage {
	Color[] backgroundColors = #[]
	GradientEdit backgroundEdit
	GradientEdit borderEdit
	Button hideBorderButton
	Button castShadowButton
	ColorWell shadowColorWell

	new() {
		super("Part")
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout
			newGroup[
				text = "Header && Border"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 4
				]
				newLabel[text = "Background"]
				backgroundEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
					onModified = [
						requestUpdatePreview(false)
					]
				]
				backgroundEdit.appendOrderLockButton [
					layoutData = newGridData[horizontalSpan = 2]
				]
				newLabel[text = "Border"]
				borderEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
					onModified = [
						requestUpdatePreview(false)
					]
				]
				borderEdit.appendOrderLockButton[]
				hideBorderButton = newCheckbox[
					text = "Hide"
					onSelection = [requestUpdatePreview(false)]
				]
			]
			newGroup[
				text = "Shadow"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 2
				]
				castShadowButton = newCheckbox[
					text = "Cast Shadow"
					onSelection = [requestUpdatePreview(false)]
				]
				shadowColorWell = newColorWell[
					onModified = [
						requestUpdatePreview(false)
					]
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		backgroundColors.safeDispose()
		backgroundColors = backgroundEdit.selection.asSWTSafeHSBArray.createColors
		folder.setBackground(backgroundColors, backgroundEdit.selection.asSWTSafePercentArray, true)
		folder.tabHeight = 22

		if(hideBorderButton.selection == false) {
			renderSettings.borderColors = borderEdit.selection.asSWTSafeHSBArray
			renderSettings.borderPercents = borderEdit.selection.asSWTSafePercentArray
		} else {
			renderSettings.borderColors = null
			renderSettings.borderPercents = null
		}

		if(castShadowButton.selection) {
			renderSettings.margins = new Rectangle(1, 0, 4, 4)
			renderSettings.shadowColor = shadowColorWell.selection
			renderSettings.shadowPosition = new Point(1, 1)
			renderSettings.shadowRadius = 3
		} else {
			renderSettings.margins = new Rectangle(0, 0, 0, 0)
			renderSettings.shadowColor = null
			renderSettings.shadowRadius = 0
		}

		folder.notifyListeners(SWT.Resize, new Event())
		folder.layout(true, true)
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
