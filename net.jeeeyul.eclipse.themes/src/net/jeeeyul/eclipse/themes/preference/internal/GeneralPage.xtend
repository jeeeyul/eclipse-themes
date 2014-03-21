package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Spinner
import org.eclipse.swt.program.Program

/**
 * @since 2.0
 */
class GeneralPage extends AbstractJTPreferencePage {
	GradientEdit toolbarEdit
	GradientEdit statusEdit
	GradientEdit perspectiveSwitcherEdit
	ColorWell perspectiveSwitcherKeyLineColorWell
	ColorWell backgroundEdit

	Button castShadowEdit
	ColorWell shadowColorWell
	Spinner partStackSpacingEdit
	Label partStackSpacingRangeLabel
	
	Spinner windowMarginsEdit
	Label windowMarginsRangeLabel

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
			newGroup[
				text = "Window Spacing"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 3
				]
				castShadowEdit = newCheckbox[
					text = "Cast Shadow"
					onSelection = [
						updateRangeLabels()
						requestUpdatePreview()
					]
				]
				shadowColorWell = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
					onModified = [requestFastUpdatePreview()]
				]
				newLabel[text = "Part Stack Spacing"]
				partStackSpacingEdit = newSpinner[
					minimum = 0
					maximum = 10
					selection = 2
					layoutData = newGridData[
						widthHint = 40
					]
				]
				partStackSpacingRangeLabel = newLabel[
					text = "2px ~ 10px"
					foreground = COLOR_DARK_GRAY
				]
				
				newLabel[text = "Margins"]
				windowMarginsEdit = newSpinner[
					minimum = 0
					maximum = 10
					selection = 4
					layoutData = newGridData[
						widthHint = 40
					]
				]
				
				windowMarginsRangeLabel = newLabel[
					text = "2px ~ 10px"
					foreground = COLOR_DARK_GRAY
				]
			]
			newLink[
				text = '''
					Margins of window requires <a href="https://github.com/jeeeyul/eclipse-themes/wiki/Settings-that-require-opening-a-new-window">opening new window</a> to take full effect.
				'''
				onSelection = [
					Program.launch(it.text)
				]
			]
		]
	}

	private def void updateRangeLabels() {
		if(castShadowEdit.selection) {
			partStackSpacingEdit => [
				minimum = 6
				selection = Math.max(selection, 4)
			]
			windowMarginsEdit => [
				minimum = 4
				selection = Math.max(selection, 4)
			]
		} else {
			partStackSpacingEdit => [
				minimum = 2
			]
			windowMarginsEdit => [
				minimum = 0
			]
		}

		partStackSpacingRangeLabel.text = '''«partStackSpacingEdit.minimum» ~ «partStackSpacingEdit.maximum»px'''
		windowMarginsRangeLabel.text = '''«windowMarginsEdit.minimum» ~ «windowMarginsEdit.maximum»px'''
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		if(castShadowEdit.selection) {
			renderSettings.margins = new Rectangle(1, 0, 4, 4)
			renderSettings.shadowColor = shadowColorWell.selection
			renderSettings.shadowPosition = new Point(1, 1)
			renderSettings.shadowRadius = 3
		} else {
			renderSettings.margins = new Rectangle(0, 0, 0, 0)
			renderSettings.shadowColor = null
			renderSettings.shadowRadius = 0
		}
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
		
		this.castShadowEdit.selection = store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)
		val shadowColor = store.getHSB(JTPConstants.Layout.SHADOW_COLOR)
		if(shadowColor != null)
			this.shadowColorWell.selection = shadowColor

		partStackSpacingEdit.selection = store.getInt(JTPConstants.Window.SASH_WIDTH)
		updateRangeLabels()
		
		windowMarginsEdit.selection = store.getRectangle(JTPConstants.Window.MARGINS).x

	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.Window.TOOLBAR_FILL_COLOR, this.toolbarEdit.selection)
		store.setValue(JTPConstants.Window.STATUS_BAR_FILL_COLOR, this.statusEdit.selection)
		store.setValue(JTPConstants.Window.BACKGROUND_COLOR, this.backgroundEdit.selection)
		store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR, this.perspectiveSwitcherEdit.selection)
		store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR, this.perspectiveSwitcherKeyLineColorWell.selection)

		store.setValue(JTPConstants.Window.SASH_WIDTH, partStackSpacingEdit.selection)
		store.setValue(JTPConstants.Layout.SHOW_SHADOW, castShadowEdit.selection)
		store.setValue(JTPConstants.Layout.SHADOW_COLOR, shadowColorWell.selection)
		
		var windowMargin = windowMarginsEdit.selection
		store.setValue(JTPConstants.Window.MARGINS, new Rectangle(windowMargin, windowMargin, windowMargin, windowMargin))
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
