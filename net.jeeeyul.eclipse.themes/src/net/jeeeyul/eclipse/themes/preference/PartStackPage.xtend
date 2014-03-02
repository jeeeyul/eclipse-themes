package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite

class PartStackPage extends AbstractJTPreferencePage {
	Color[] backgroundColors = #[]
	GradientEdit backgroundEdit
	GradientEdit borderEdit
	Button hideBorderButton

	GradientEdit unselectedBackgroundEdit
	GradientEdit unselectedBorderEdit
	Button hideUnselectedBorderButton

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
			
			newRadioButton[
				text = "A"
			]
			newGroup[
				text = "Unselected"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 4
				]
				newLabel[text = "Background"]
				unselectedBackgroundEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
					onModified = [
						requestUpdatePreview(false)
					]
				]
				unselectedBackgroundEdit.appendOrderLockButton [
					layoutData = newGridData[horizontalSpan = 2]
				]
				newLabel[text = "Border"]
				unselectedBorderEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
					onModified = [
						requestUpdatePreview(false)
					]
				]
				unselectedBorderEdit.appendOrderLockButton[]
				hideUnselectedBorderButton = newCheckbox[
					text = "Hide"
					onSelection = [requestUpdatePreview(false)]
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		backgroundColors.safeDispose()
		backgroundColors = backgroundEdit.selection.asSWTSafeHSBArray.createColors
		folder.setBackground(backgroundColors, backgroundEdit.selection.asSWTSafePercentArray, true)

		if(hideBorderButton.selection == false) {
			renderSettings.borderColors = borderEdit.selection.asSWTSafeHSBArray
			renderSettings.borderPercents = borderEdit.selection.asSWTSafePercentArray
		} else {
			renderSettings.borderColors = null
			renderSettings.borderPercents = null
		}

		renderSettings.unselectedBackgroundColors = this.unselectedBackgroundEdit.selection.asSWTSafeHSBArray
		renderSettings.unselectedBackgroundPercents = this.unselectedBackgroundEdit.selection.asSWTSafePercentArray
		renderSettings.tabItemHorizontalSpacing = 0

		if(this.hideUnselectedBorderButton.selection == false) {
			renderSettings.unselectedBorderColors = this.unselectedBorderEdit.selection.asSWTSafeHSBArray
			renderSettings.unselectedBorderPercents = this.unselectedBorderEdit.selection.asSWTSafePercentArray
		} else {
			renderSettings.unselectedBorderColors = null
			renderSettings.unselectedBorderPercents = null
		}

	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
