package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.css.internal.EditorLineSupport
import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorWell
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.core.runtime.Platform
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.source.AnnotationRulerColumn
import org.eclipse.jface.text.source.CompositeRuler
import org.eclipse.jface.text.source.IVerticalRulerColumn
import org.eclipse.jface.text.source.LineNumberRulerColumn
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.editors.text.EditorsUI

class TextEditorPage extends AbstractJTPreferencePage {

	new() {
		super("Editor")
		this.image = SharedImages.getImage(SharedImages.FILE)
	}

	SourceViewer preview

	LineStyleEditor underLineStyleEdit
	ColorWell underLineColorEdit
	ColorWell rulerColorEdit

	Composite previewWrap
	IVerticalRulerColumn annotationRulerColumn
	LineNumberRulerColumn lineNumberRulerColumn
	Color textEditorBackground
	Color textEditorForeground
	Color rulerColor
	Color lineNumberColor

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[
				numColumns = 2
			]
			previewWrap = newComposite(SWT.BORDER) [
				layoutData = FILL_HORIZONTAL[
					horizontalSpan = 2
				]
				layout = newGridLayout[
					marginWidth = 0
					marginHeight = 0
				]
				var ruler = new CompositeRuler(1)
				annotationRulerColumn = new AnnotationRulerColumn(12)
				ruler.addDecorator(0, annotationRulerColumn)
				lineNumberRulerColumn = new LineNumberRulerColumn
				ruler.addDecorator(1, lineNumberRulerColumn)
				preview = new SourceViewer(it, ruler, SWT.V_SCROLL)
				
				preview.document = new Document(
					'''
						Jeeeyul's Eclipse Themes Text Editor Preview
						
						까꿍아, 건강하게 태어나, 행복하게 살렴. 사랑한다.
						개발자는 하지 말고.
						
						2014년 3월 21일 아빠가.
					''')
				preview.control.layoutData = FILL_HORIZONTAL[
					widthHint = 200
					heightHint = 100
				]
			]
			newLabel[text = "Underline Style"]
			underLineStyleEdit = new LineStyleEditor(it) => [
				control.layoutData = FILL_HORIZONTAL
				selectionHandler = [requestFastUpdatePreview]
			]
			newLabel[text = "Underline Color"]
			underLineColorEdit = newColorWell[
				onModified = [requestFastUpdatePreview]
			]
			newLabel[text = "Ruler Color"]
			rulerColorEdit = newColorWell[
				onModified = [requestFastUpdatePreview]
			]
			newHorizontalSeparator[]
			val linkText = if(colorThemeInstalled) {
					'''See <a href="org.eclipse.ui.preferencePages.GeneralTextEditor">Text Editors</a> and <a href="com.github.eclipsecolortheme.preferences.ColorThemePreferencePage">Eclipse Color Theme</a> also.'''
				} else {
					'''See <a href="org.eclipse.ui.preferencePages.GeneralTextEditor">Text Editors Section</a> also.'''
				}
			newLink[
				layoutData = newGridData[
					horizontalSpan = 2
				]
				text = linkText
				onSelection = [
					navigateTo(it.text)
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		EditorLineSupport.get(preview.textWidget) => [
			lineColor = underLineColorEdit.selection
			lineStyle = underLineStyleEdit.selection
		]

		preview.textWidget.background.safeDispose

		textEditorBackground.safeDispose
		preview.textWidget.background = textEditorBackground = computeTextEditorBackground.newColor
		lineNumberRulerColumn.background = textEditorBackground

		lineNumberColor.safeDispose
		lineNumberRulerColumn.foreground = lineNumberColor = computeLineNumberForeground.newColor

		textEditorForeground.safeDispose
		textEditorForeground = computeTextEditorForeground.newColor
		preview.textWidget.foreground = textEditorForeground

		rulerColor.safeDispose
		previewWrap.background = rulerColor = rulerColorEdit.selection.newColor

	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		underLineStyleEdit.selection = store.getInt(JTPConstants.TextEditor.UNDER_LINE_STYLE)
		var underLineColor = store.getHSB(JTPConstants.TextEditor.UNDER_LINE_COLOR)
		if(underLineColor != null) {
			underLineColorEdit.selection = underLineColor
		}

		var rulerColor = store.getHSB(JTPConstants.TextEditor.RULER_COLOR)
		if(rulerColor != null) {
			rulerColorEdit.selection = rulerColor
		}
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.TextEditor.UNDER_LINE_STYLE, underLineStyleEdit.selection)
		store.setValue(JTPConstants.TextEditor.UNDER_LINE_COLOR, underLineColorEdit.selection)
		store.setValue(JTPConstants.TextEditor.RULER_COLOR, rulerColorEdit.selection)
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		#[textEditorBackground, textEditorForeground, rulerColor, lineNumberColor].safeDispose
	}

	def private HSB computeTextEditorBackground() {
		if(Platform.running == false) {
			return HSB.WHITE
		}

		var exp = EditorsUI.preferenceStore.getString("AbstractTextEditor.Color.Background")
		var rgbArr = exp.split(",").map[Integer.parseInt(it)]
		var rgb = new RGB(rgbArr.get(0), rgbArr.get(1), rgbArr.get(2))
		return new HSB(rgb)
	}

	def private HSB computeTextEditorForeground() {
		if(Platform.running == false) {
			return HSB.BLACK
		}

		var exp = EditorsUI.preferenceStore.getString("AbstractTextEditor.Color.Foreground")
		var rgbArr = exp.split(",").map[Integer.parseInt(it)]
		var rgb = new RGB(rgbArr.get(0), rgbArr.get(1), rgbArr.get(2))
		return new HSB(rgb)
	}

	def private HSB computeLineNumberForeground() {
		if(Platform.running == false) {
			return HSB.BLACK
		}

		var exp = EditorsUI.preferenceStore.getString("lineNumberColor")
		var rgbArr = exp.split(",").map[Integer.parseInt(it)]
		var rgb = new RGB(rgbArr.get(0), rgbArr.get(1), rgbArr.get(2))
		return new HSB(rgb)
	}

	def private isColorThemeInstalled() {
		if(Platform.running == false) {
			return false
		}
		val prefNodes = Platform.extensionRegistry.getExtensionPoint("org.eclipse.ui.preferencePages")

		prefNodes.configurationElements.exists [
			it.getAttribute("id") == "com.github.eclipsecolortheme.preferences.ColorThemePreferencePage"
		]
	}
}
