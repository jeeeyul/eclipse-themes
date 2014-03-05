package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.internal.JTPreferenceKeyCollector
import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Text

class DebugPage extends AbstractJTPreferencePage {
	Text epfView

	new() {
		super("Debug")
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout
			
			epfView = newTextArea [
				it.text = "Empty"
				layoutData = FILL_BOTH[
					widthHint = 200
					heightHint = 200
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		epfView.text = '''
			«FOR key : new JTPreferenceKeyCollector().collect»
				«key»=«preferenceStore.getString(key)»
			«ENDFOR»
		'''
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

}
