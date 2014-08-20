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
import java.util.ArrayList

class TextEditorPage extends AbstractJTPreferencePage {
	static val String PREF_ID_COLOR_THEME = "com.github.eclipsecolortheme.preferences.ColorThemePreferencePage"
	static val String PREF_ID_EDITBOX = "pm.eclipse.editbox.pref.default"
	static val String PREF_ID_GENERAL_TEXT_EDITORS = "org.eclipse.ui.preferencePages.GeneralTextEditor"

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
						
						윤슬아 탄생 100일을 축하한다.
						오래오래 건강하고 행복하게 살자.
						
						2014년 8월 20일 아빠가.
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
			
			newLink[
				layoutData = newGridData[
					horizontalSpan = 2
				]
				text = '''See also «links.map[it.toHTML].smartJoin(", ", " and ")».'''
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

	def private PreferenceLink[] getLinks(){
		if(Platform.running == false){
			return #[]
		}
		
		var result = new ArrayList<PreferenceLink>
		
		if(isPreferencePageExists(PREF_ID_EDITBOX)){
			result += new PreferenceLink("EditBox", PREF_ID_EDITBOX)
		}
		
		if(isPreferencePageExists(PREF_ID_COLOR_THEME)){
			result += new PreferenceLink("Eclipse Color Theme", PREF_ID_COLOR_THEME)
		}
		
		result += new PreferenceLink("Text Editors Section", PREF_ID_GENERAL_TEXT_EDITORS)
		
		println(result);
		return result;
	}
	
	def private isPreferencePageExists(String id){
		val prefNodes = Platform.extensionRegistry.getExtensionPoint("org.eclipse.ui.preferencePages")

		prefNodes.configurationElements.exists [
			it.getAttribute("id") == id
		]
	}
	
	def private String smartJoin(Iterable<String> segments, String delimeter1, String delimeter2){
		if(segments.length > 2){
			return segments.toList.subList(0, segments.length - 1).join(delimeter1) + delimeter2 + segments.last
		}else{
			return segments.join(delimeter2)
		}
	}
	
	def private String toHTML(PreferenceLink link){
		return '''<a href="«link.prefId»">«link.name»</a>'''	
	}
}
