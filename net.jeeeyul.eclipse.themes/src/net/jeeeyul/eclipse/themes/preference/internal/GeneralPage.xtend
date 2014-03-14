package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite

/**
 * @since 2.0
 */
class GeneralPage extends AbstractJTPreferencePage {
	GradientEdit toolbarEdit
	GradientEdit statusEdit
	GradientEdit perspectiveSwitcherEdit
	ColorWell perspectiveSwitcherKeyLineColorWell
	ColorWell backgroundEdit

	new() {
		super("General")
		image = SharedImages.getImage(SharedImages.LAYOUT)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[]
			newGroup[
				text = "Window"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 3
				]
				newLabel[
					text = "Background"
				]
				backgroundEdit = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
				newLabel[
					text = "Tool Bar"
				]
				toolbarEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				toolbarEdit.appendOrderLockButton[]
				newLabel[
					text = "Status Bar"
				]
				statusEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				statusEdit.appendOrderLockButton[]
			]
			newGroup[
				text = "Perspective Switcher"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 3
				]
				newLabel[text = "Fill"]
				perspectiveSwitcherEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				perspectiveSwitcherEdit.appendOrderLockButton[]
				newLabel[
					text = "Key Line"
				]
				perspectiveSwitcherKeyLineColorWell = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		val toolbarFill = store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR)
		if(toolbarFill != null) {
			this.toolbarEdit.selection = toolbarFill
		}

		val statusBarFill = store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR)
		if(statusBarFill != null) {
			this.statusEdit.selection = statusBarFill
		}

		val background = store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
		if(background != null) {
			backgroundEdit.selection = background
		}

		var psFill = store.getGradient(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR)
		if(psFill != null) {
			perspectiveSwitcherEdit.selection = psFill
		}

		var psKeyline = store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR)
		if(psKeyline != null) {
			this.perspectiveSwitcherKeyLineColorWell.selection = psKeyline
		}
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.Window.TOOLBAR_FILL_COLOR, this.toolbarEdit.selection)
		store.setValue(JTPConstants.Window.STATUS_BAR_FILL_COLOR, this.statusEdit.selection)
		store.setValue(JTPConstants.Window.BACKGROUND_COLOR, this.backgroundEdit.selection)
		store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR, this.perspectiveSwitcherEdit.selection)
		store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR, this.perspectiveSwitcherKeyLineColorWell.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
