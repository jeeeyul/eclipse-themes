package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.ChromePage
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.swt.SWT
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.jface.text.Document
import net.jeeeyul.eclipse.themes.SharedImages

/**
 * 58: User Custom CSS
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/58
 */
class UserCSSPage extends ChromePage {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	ISourceViewer srcViewer
	
	new() {
		super("CSS", null);
	}

	override create(Composite parent) {
		parent.layout = newGridLayout
		parent.newLabel[
			text = "Custom CSS:"
		]
		
		srcViewer = new SourceViewer(parent, null, SWT::V_SCROLL || SWT::BORDER);
		srcViewer.textWidget.layoutData = FILL_BOTH
		
		parent.newComposite[
			layoutData = FILL_HORIZONTAL
			layout = newGridLayout[
				numColumns = 2
			]
			
			newCLabel[
				image = SharedImages::getImage(SharedImages::WARN_TSK)
				text = "Using this feature may not safe."	
			]
			
			newPushButton[
				text = "Clear"
				onClick = [
					srcViewer.document.set("")
				]
				layoutData = newGridData[
					grabExcessHorizontalSpace = true
					horizontalAlignment = SWT::RIGHT
				]
			]
		]
	}
	
	override load(IPreferenceStore preferenceStore) {
		var content = preferenceStore.getString(ChromeConstants::CHROME_USER_CSS);
		srcViewer.document = new Document(content)
	}
	
	override save(IPreferenceStore preferenceStore) {
		preferenceStore.setValue(ChromeConstants::CHROME_USER_CSS, srcViewer.document.get)
	}
	
	override setToDefault(IPreferenceStore preferenceStore) {
		var content = preferenceStore.getDefaultString(ChromeConstants::CHROME_USER_CSS);
		srcViewer.document = new Document(content)
	}
}