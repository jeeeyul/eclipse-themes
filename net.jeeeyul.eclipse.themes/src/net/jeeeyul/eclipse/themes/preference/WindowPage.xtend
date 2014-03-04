package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.swtend.ui.ColorWell
import org.eclipse.swt.SWT

class WindowPage extends AbstractJTPreferencePage {
	GradientEdit toolbarEdit
	GradientEdit statusEdit
	GradientEdit perspectiveSwitcherEdit
	ColorWell perspectiveSwitcherKeyLineColorWell
	ColorWell backgroundEdit

	new() {
		super("Window")
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[
				numColumns = 3
			]
			newLabel[
				text = "Background"
				layoutData = newGridData[
					horizontalSpan = 2
				]
			]
			backgroundEdit = newColorWell[
				layoutData = newGridData[
					horizontalAlignment = SWT.RIGHT
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
				text = "Perspective Switcher"
			]
			perspectiveSwitcherEdit = newGradientEdit[
				layoutData = FILL_HORIZONTAL
			]
			perspectiveSwitcherEdit.appendOrderLockButton[]
			newLabel[
				text = "Perspective Switcher Key Line"
				layoutData = newGridData[
					horizontalSpan = 2
				]
			]
			perspectiveSwitcherKeyLineColorWell = newColorWell[
				layoutData = newGridData[
					horizontalAlignment = SWT.RIGHT
				]
			]
			newLabel[
				text = "Status Bar"
			]
			statusEdit = newGradientEdit[
				layoutData = FILL_HORIZONTAL
			]
			statusEdit.appendOrderLockButton[]
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
