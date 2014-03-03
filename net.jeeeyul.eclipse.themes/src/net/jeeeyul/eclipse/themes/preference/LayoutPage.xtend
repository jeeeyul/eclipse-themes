package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.Scale

class LayoutPage extends AbstractJTPreferencePage {
	Button castShadowButton
	ColorWell shadowColorWell
	Scale borderRadiusScale
	Scale paddingsScale
	Scale tabItemPaddingsScale
	Scale tabSpacingScale
	Scale tabItemSpacingScale
	Scale tabHeightScale

	new() {
		super("Layout")
		this.image = SharedImages.getImage(SharedImages.LAYOUT)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[
				numColumns = 3
			]
			newLabel[text = "Border Radius"]
			borderRadiusScale = newScale[
				minimum = 0
				maximum = 10
				selection = 3
				onSelection = [requestUpdatePreview()]
				layoutData = FILL_HORIZONTAL
			]
			borderRadiusScale.appendMonitor("px", 0)
			newLabel[text = "Tab Item Paddings"]
			tabItemPaddingsScale = newScale[
				minimum = 0
				maximum = 10
				selection = 2
				onSelection = [requestUpdatePreview()]
				layoutData = FILL_HORIZONTAL
			]
			tabItemPaddingsScale.appendMonitor("px", 0)
			newLabel[text = "Content Paddings"]
			paddingsScale = newScale[
				minimum = 0
				maximum = 10
				selection = 2
				onSelection = [requestUpdatePreview()]
				layoutData = FILL_HORIZONTAL
			]
			paddingsScale.appendMonitor("px", 0)
			newLabel[text = "Tab Spacing"]
			tabSpacingScale = newScale[
				minimum = 0
				maximum = 40
				selection = 3
				onSelection = [requestUpdatePreview()]
				layoutData = FILL_HORIZONTAL
			]
			tabSpacingScale.appendMonitor("px", -1)
			newLabel[text = "Tab Item Spacing"]
			tabItemSpacingScale = newScale[
				minimum = 0
				maximum = 10
				selection = 2
				onSelection = [requestUpdatePreview()]
				layoutData = FILL_HORIZONTAL
			]
			tabItemSpacingScale.appendMonitor("px", 0)
			newLabel[text = "Tab Height"]
			tabHeightScale = newScale[
				minimum = 22
				maximum = 40
				selection = 22
				onSelection = [requestUpdatePreview()]
				layoutData = FILL_HORIZONTAL
			]
			tabHeightScale.appendMonitor("px", 0)
			castShadowButton = newCheckbox[
				text = "Cast Shadow"
				onSelection = [requestUpdatePreview()]
			]
			shadowColorWell = newColorWell[
				onModified = [
					requestUpdatePreview()
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
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

		renderSettings.borderRadius = borderRadiusScale.selection
		renderSettings.paddings = newInsets(paddingsScale.selection)
		renderSettings.tabItemPaddings = newInsets(tabItemPaddingsScale.selection)
		renderSettings.tabSpacing = tabSpacingScale.selection - 1
		renderSettings.tabItemHorizontalSpacing = this.tabItemSpacingScale.selection

		folder.tabHeight = tabHeightScale.selection
		folder.notifyListeners(SWT.Resize, new Event())
		folder.layout(true, true)
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		this.borderRadiusScale.selection = store.getInt(JTPConstants.Layout.BORDER_RADIUS)
		this.paddingsScale.selection = store.getInt(JTPConstants.Layout.CONTENT_PADDING)
		this.tabHeightScale.selection = store.getInt(JTPConstants.Layout.TAB_HEIGHT)
		this.castShadowButton.selection = store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)

		val shadowColor = store.getHSB(JTPConstants.Layout.SHADOW_COLOR)
		if(shadowColor != null)
			this.shadowColorWell.selection = shadowColor

		this.tabSpacingScale.selection = store.getInt(JTPConstants.Layout.TAB_SPACING) + 1

		this.tabItemPaddingsScale.selection = store.getInt(JTPConstants.Layout.TAB_ITEM_PADDING)
		this.tabItemSpacingScale.selection = store.getInt(JTPConstants.Layout.TAB_ITEM_SPACING)
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.Layout.BORDER_RADIUS, this.borderRadiusScale.selection)
		store.setValue(JTPConstants.Layout.CONTENT_PADDING, this.paddingsScale.selection)
		store.setValue(JTPConstants.Layout.TAB_HEIGHT, this.tabHeightScale.selection)
		store.setValue(JTPConstants.Layout.SHOW_SHADOW, this.castShadowButton.selection)
		store.setValue(JTPConstants.Layout.SHADOW_COLOR, this.shadowColorWell.selection)
		store.setValue(JTPConstants.Layout.TAB_SPACING, this.tabSpacingScale.selection - 1)
		store.setValue(JTPConstants.Layout.TAB_ITEM_PADDING, this.tabItemPaddingsScale.selection)
		store.setValue(JTPConstants.Layout.TAB_ITEM_SPACING, this.tabItemSpacingScale.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
