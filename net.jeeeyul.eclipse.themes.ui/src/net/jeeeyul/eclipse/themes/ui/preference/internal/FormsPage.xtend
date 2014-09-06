package net.jeeeyul.eclipse.themes.ui.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite

class FormsPage extends AbstractJTPreferencePage {
	GradientEdit formHeadingGradientEdit
	ColorWell formHeadingTitleTextColorWell
	ColorWell formHeadingBorder1ColorWell
	ColorWell formHeadingBorder2ColorWell

	ColorWell sectionHeaderTintColorWell
	ColorWell sectionHeaderTitleTextColorWell
	ColorWell sectionHeaderActiveTitleTextColorWell
	ColorWell sectionHeaderBorderColorWell
	ColorWell hyperLinkColorWell
	ColorWell activeHyperLinkColorWell

	new() {
		super("Forms")
		this.image = SharedImages.getImage(SharedImages.LAYOUT)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout
			newGroup[
				text = "Form Heading"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 3
				]
				newLabel[
					text = "Background"
				]
				formHeadingGradientEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				
				formHeadingGradientEdit.appendOrderLockButton[
				]
				
				newLabel[
					text = "Title Text"
				]
				
				formHeadingTitleTextColorWell = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
				
				newLabel[
					text = "Upper Bottom Border"
				]
				formHeadingBorder1ColorWell = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
				newLabel[
					text = "Below Bottom Border"
				]
				formHeadingBorder2ColorWell = newColorWell[
					layoutData = newGridData[
						horizontalSpan = 2
					]
				]
			]
			newGroup[
				text = "Section"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 5
				]
				newLabel[
					text= "Header"
				]
				newLabel[
					text = "Tint"
					layoutData = newGridData[
						horizontalAlignment = SWT.LEFT
					]
				]
				sectionHeaderTintColorWell = newColorWell[]
				newLabel[
					text = "Border"
					layoutData = newGridData[
						horizontalIndent = 20
						horizontalAlignment = SWT.LEFT
					]
				]
				sectionHeaderBorderColorWell = newColorWell[]
				newLabel[
					text = "Title Text"
				]
				newLabel[
					text = "Color"
					layoutData = newGridData[
						horizontalAlignment = SWT.LEFT
					]
				]
				sectionHeaderTitleTextColorWell = newColorWell[]
				
				newLabel[
					text = "Active Color"
					layoutData = newGridData[
						horizontalIndent = 20
						horizontalAlignment = SWT.LEFT
					]
				]
				sectionHeaderActiveTitleTextColorWell = newColorWell[]
				
				newLabel[
					text = "Hyperlink"
				]
				newLabel[
					text = "Color"
					layoutData = newGridData[
						horizontalAlignment = SWT.LEFT
					]
				]
				hyperLinkColorWell = newColorWell[]
				
				newLabel[
					text = "Active Color"
					layoutData = newGridData[
						horizontalIndent = 20
						horizontalAlignment = SWT.LEFT
					]
				]
				activeHyperLinkColorWell = newColorWell[]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		formHeadingGradientEdit.selection = store.getGradient(JTPConstants.Forms.FORM_HEADING_BACKGROUND)
		formHeadingTitleTextColorWell.selection = store.getHSB(JTPConstants.Forms.FORM_HEADING_TITLE_COLOR)
		formHeadingBorder1ColorWell.selection = store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_1_COLOR)
		formHeadingBorder2ColorWell.selection = store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_2_COLOR)
		
		sectionHeaderTintColorWell.selection = store.getHSB(JTPConstants.Forms.SECTION_HEADER_TINT_COLOR)
		sectionHeaderBorderColorWell.selection = store.getHSB(JTPConstants.Forms.SECTION_HEADER_BORDER_COLOR)
		sectionHeaderTitleTextColorWell.selection = store.getHSB(JTPConstants.Forms.SECTION_HEADER_TITLE_COLOR)
		sectionHeaderActiveTitleTextColorWell.selection = store.getHSB(JTPConstants.Forms.SECTION_HEADER_ACTIVE_TITLE_COLOR)
		
		hyperLinkColorWell.selection = store.getHSB(JTPConstants.Forms.HYPER_LINK_COLOR)
		activeHyperLinkColorWell.selection = store.getHSB(JTPConstants.Forms.ACTIVE_HYPER_LINK_COLOR)
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.Forms.FORM_HEADING_BACKGROUND, formHeadingGradientEdit.selection)
		store.setValue(JTPConstants.Forms.FORM_HEADING_TITLE_COLOR, formHeadingTitleTextColorWell.selection)
		store.setValue(JTPConstants.Forms.FORM_HEADING_BORDER_1_COLOR, formHeadingBorder1ColorWell.selection)
		store.setValue(JTPConstants.Forms.FORM_HEADING_BORDER_2_COLOR, formHeadingBorder2ColorWell.selection)
		
		store.setValue(JTPConstants.Forms.SECTION_HEADER_TINT_COLOR, sectionHeaderTintColorWell.selection)
		store.setValue(JTPConstants.Forms.SECTION_HEADER_BORDER_COLOR, sectionHeaderBorderColorWell.selection)
		store.setValue(JTPConstants.Forms.SECTION_HEADER_TITLE_COLOR, sectionHeaderTitleTextColorWell.selection)
		store.setValue(JTPConstants.Forms.SECTION_HEADER_ACTIVE_TITLE_COLOR, sectionHeaderActiveTitleTextColorWell.selection)
		
		store.setValue(JTPConstants.Forms.HYPER_LINK_COLOR, hyperLinkColorWell.selection)
		store.setValue(JTPConstants.Forms.ACTIVE_HYPER_LINK_COLOR, activeHyperLinkColorWell.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
