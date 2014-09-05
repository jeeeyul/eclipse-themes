package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.SharedImages

class FormsPage extends AbstractJTPreferencePage {
	GradientEdit formHeadingGradientEdit
	ColorWell formHeadingTitleTextColorWell
	ColorWell formHeadingBorder1ColorWell
	ColorWell formHeadingBorder2ColorWell

	ColorWell sectionHeaderTintColorWell
	ColorWell sectionHeaderTitleTextColorWell
	ColorWell sectionHeaderActiveTitleTextColorWell
	ColorWell sectionHeaderBorderColorWell

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
					numColumns = 2
				]
				newLabel[
					text = "Background"
				]
				formHeadingGradientEdit = newGradientEdit[
					layoutData = FILL_HORIZONTAL
				]
				
				newLabel[
					text = "Title Text"
				]
				
				formHeadingTitleTextColorWell = newColorWell[
				]
				
				newLabel[
					text = "Upper Bottom Border"
				]
				formHeadingBorder1ColorWell = newColorWell[]
				newLabel[
					text = "Below Bottom Border"
				]
				formHeadingBorder2ColorWell = newColorWell[]
			]
			newGroup[
				text = "Section"
				layoutData = FILL_HORIZONTAL
				layout = newGridLayout[
					numColumns = 2
				]
				newLabel[
					text = "Header Tint"
				]
				sectionHeaderTintColorWell = newColorWell[]
				newLabel[
					text = "Header Border"
				]
				sectionHeaderBorderColorWell = newColorWell[]
				newLabel[
					text = "Title Text"
				]
				sectionHeaderTitleTextColorWell = newColorWell[]
				
				newLabel[
					text = "Active Title Text"
				]
				sectionHeaderActiveTitleTextColorWell = newColorWell[]
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
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
