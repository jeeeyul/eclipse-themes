package net.jeeeyul.eclipse.themes.ui.preference.internal

import java.util.List
import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.program.Program
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.Spinner

class LayoutPage extends AbstractJTPreferencePage {
	Spinner borderRadiusScale
	Spinner paddingsScale
	Spinner tabItemPaddingsScale
	Spinner tabSpacingScale
	Spinner tabItemSpacingScale
	Spinner tabHeightScale

	Button truncateTabItemsButton
	Button truncateEditorTabItemsButton
	Button useEllipsesButton
	Spinner minimumCharactersSpinner
	Spinner minimumCharactersForEditorSpinner
	
	List<Button> closeButtonAlignButtons

	new() {
		super("Layout")
		this.image = SharedImages.getImage(SharedImages.LAYOUT)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[]
			newComposite[
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 3
				]
				newLabel[text = "Border Radius"]
				borderRadiusScale = newSpinner[
					minimum = 0
					maximum = 10
					selection = 3
					onSelection = [requestFastUpdatePreview()]
					layoutData = newGridData[
						widthHint = 40
					]
				]
				newLabel[
					text = "0 ~ 10px"
					foreground = COLOR_DARK_GRAY
				]
				newLabel[text = "Tab Item Paddings"]
				tabItemPaddingsScale = newSpinner[
					minimum = 0
					maximum = 10
					selection = 2
					onSelection = [requestFastUpdatePreview()]
					layoutData = newGridData[
						widthHint = 40
					]
				]
				newLabel[
					text = "0 ~ 10px"
					foreground = COLOR_DARK_GRAY
				]
				newLabel[text = "Content Paddings"]
				paddingsScale = newSpinner[
					minimum = 0
					maximum = 10
					selection = 2
					onSelection = [requestFastUpdatePreview()]
					layoutData = newGridData[
						widthHint = 40
					]
				]
				newLabel[
					text = "0 ~ 10px"
					foreground = COLOR_DARK_GRAY
				]
				newLabel[text = "Tab Spacing"]
				tabSpacingScale = newSpinner[
					minimum = -1
					maximum = 20
					selection = 2
					onSelection = [requestFastUpdatePreview()]
					layoutData = newGridData[
						widthHint = 40
					]
				]
				newLabel[
					text = "-1(Overlap) ~ 20px"
					foreground = COLOR_DARK_GRAY
				]
				newLabel[text = "Part Icon, Title and Close Spacing"]
				tabItemSpacingScale = newSpinner[
					minimum = 0
					maximum = 10
					selection = 2
					onSelection = [requestFastUpdatePreview()]
					layoutData = newGridData[
						widthHint = 40
					]
				]
				newLabel[
					text = "0 ~ 10px"
					foreground = COLOR_DARK_GRAY
				]
				newLabel[text = "Tab Height"]
				tabHeightScale = newSpinner[
					minimum = minimumToolBarHeight
					maximum = 40
					selection = minimumToolBarHeight
					onSelection = [requestUpdatePreview()]
					layoutData = newGridData[
						widthHint = 40
					]
				]
				newLabel[
					text = '''«minimumToolBarHeight» ~ 40px'''
					foreground = COLOR_DARK_GRAY
				]
				
				newLabel[
					text = "Close Button Alignment"
				]
				newComposite[
					layout = newGridLayout[
						numColumns = VerticalAlignment.values.length
						marginWidth = 0
						marginHeight = 0
					]
					layoutData = newGridData[
						horizontalSpan = 2
					]
					closeButtonAlignButtons = newArrayList()
					for(each : VerticalAlignment.values){
						closeButtonAlignButtons += newRadioButton[
							text = each.getName()
							data = each
							onSelection = [
								requestFastUpdatePreview()
							]
						]
					}
				]
			]
			newComposite[
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 1
				]
				truncateTabItemsButton = newCheckbox[
					text = "Truncate Tab Items to show more Tab Items"
					onSelection = [requestFastUpdatePreview()]
				]
				newGroup[
					layoutData = FILL_HORIZONTAL
					layout = newGridLayout[
						numColumns = 2
					]
					newLabel[
						text = "Number of minimum charaters"
					]
					minimumCharactersSpinner = newSpinner[
						layoutData = newGridData[
							widthHint = 40
						]
						minimum = 1
						maximum = 20
						onSelection = [requestFastUpdatePreview()]
					]
					useEllipsesButton = newCheckbox[
						text = "Use Ellipses when truncate"
						layoutData = newGridData[
							horizontalSpan = 2
						]
						onSelection = [requestFastUpdatePreview()]
					]
					truncateEditorTabItemsButton = newCheckbox[
						text = "Truncate Editors also"
						layoutData = newGridData[
							horizontalSpan = 2
						]
						onSelection = [requestFastUpdatePreview()]
					]
					newLabel[
						text = "Number of minimum charaters for Editors"
						layoutData = newGridData[
							horizontalIndent = 16
						]
					]
					minimumCharactersForEditorSpinner = newSpinner[
						layoutData = newGridData[
							widthHint = 40
						]
						minimum = 1
						maximum = 20
						onSelection = [requestFastUpdatePreview()]
					]
				]
			]
			if(ENVHelper.INSTANCE.linux) {
				newLink[
					text = '''To reduce limit of minimum tab height, refer <a href="https://github.com/jeeeyul/eclipse-themes/wiki/Linux-User-Guide">Linux User Guide</a>.'''
					layoutData = newGridData[
						horizontalSpan = 3
					]
					onSelection = [
						Program.launch(it.text)
					]
				]

			}
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		renderSettings.borderRadius = borderRadiusScale.selection
		renderSettings.paddings = newInsets(paddingsScale.selection)
		renderSettings.tabItemPaddings = newInsets(tabItemPaddingsScale.selection)
		renderSettings.tabSpacing = tabSpacingScale.selection
		renderSettings.tabItemHorizontalSpacing = this.tabItemSpacingScale.selection

		renderSettings.useEllipses = useEllipsesButton.selection
		renderSettings.minimumCharacters = minimumCharactersSpinner.selection
		renderSettings.truncateTabItems = truncateTabItemsButton.selection
		renderSettings.closeButtonAlignment = closeButtonAlignButtons.findFirst[it.selection].data as VerticalAlignment

		useEllipsesButton.enabled = truncateTabItemsButton.selection
		minimumCharactersSpinner.enabled = truncateTabItemsButton.selection
		truncateEditorTabItemsButton.enabled = truncateTabItemsButton.selection
		minimumCharactersForEditorSpinner.enabled = truncateEditorTabItemsButton.selection && truncateTabItemsButton.selection
		

		folder.tabHeight = tabHeightScale.selection
		folder.notifyListeners(SWT.Resize, new Event())
		folder.layout(true, true)
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		this.borderRadiusScale.selection = store.getInt(JTPConstants.Layout.BORDER_RADIUS)
		this.paddingsScale.selection = store.getInt(JTPConstants.Layout.CONTENT_PADDING)
		this.tabHeightScale.selection = Math.max(store.getInt(JTPConstants.Layout.TAB_HEIGHT), minimumToolBarHeight)

		this.tabSpacingScale.selection = store.getInt(JTPConstants.Layout.TAB_SPACING)

		this.tabItemPaddingsScale.selection = store.getInt(JTPConstants.Layout.TAB_ITEM_PADDING)
		this.tabItemSpacingScale.selection = store.getInt(JTPConstants.Layout.TAB_ITEM_SPACING)

		this.truncateTabItemsButton.selection = store.getBoolean(JTPConstants.Layout.TRUNCATE_TAB_ITEMS)
		this.useEllipsesButton.selection = store.getBoolean(JTPConstants.Layout.USE_ELLIPSES)
		this.minimumCharactersSpinner.selection = store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS)
		this.truncateEditorTabItemsButton.selection = store.getBoolean(JTPConstants.Layout.TRUNCATE_EDITORS_TAB_ITEMS_ALSO)
		this.minimumCharactersForEditorSpinner.selection = store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS_FOR_EDITORS)
		
		var closeButtonAlign = VerticalAlignment.fromValue(store.getInt(JTPConstants.Layout.CLOSE_BUTTON_VERTICAL_ALIGNMENT))
		for(eachButton : closeButtonAlignButtons){
			eachButton.selection = eachButton.data == closeButtonAlign
		}
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.Layout.BORDER_RADIUS, this.borderRadiusScale.selection)
		store.setValue(JTPConstants.Layout.CONTENT_PADDING, this.paddingsScale.selection)
		store.setValue(JTPConstants.Layout.TAB_HEIGHT, this.tabHeightScale.selection)
		store.setValue(JTPConstants.Layout.TAB_SPACING, this.tabSpacingScale.selection)
		store.setValue(JTPConstants.Layout.TAB_ITEM_PADDING, this.tabItemPaddingsScale.selection)
		store.setValue(JTPConstants.Layout.TAB_ITEM_SPACING, this.tabItemSpacingScale.selection)

		store.setValue(JTPConstants.Layout.TRUNCATE_TAB_ITEMS, this.truncateTabItemsButton.selection)
		store.setValue(JTPConstants.Layout.USE_ELLIPSES, this.useEllipsesButton.selection)
		store.setValue(JTPConstants.Layout.MINIMUM_CHARACTERS, this.minimumCharactersSpinner.selection)
		store.setValue(JTPConstants.Layout.TRUNCATE_EDITORS_TAB_ITEMS_ALSO, truncateEditorTabItemsButton.selection)
		store.setValue(JTPConstants.Layout.MINIMUM_CHARACTERS_FOR_EDITORS, minimumCharactersForEditorSpinner.selection)
		
		var selectedAlign = closeButtonAlignButtons.findFirst[it.selection].data as VerticalAlignment
		store.setValue(JTPConstants.Layout.CLOSE_BUTTON_VERTICAL_ALIGNMENT, selectedAlign.value)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
