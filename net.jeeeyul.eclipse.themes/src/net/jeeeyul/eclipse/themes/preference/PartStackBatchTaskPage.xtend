package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import org.eclipse.jface.preference.PreferenceStore

class PartStackBatchTaskPage extends AbstractJTPreferencePage {
	new() {
		super("Tools")
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[]
			newPushButton[
				text = "Copy settings from Active to Inactive"
				onSelection = [
					copy(JTPConstants.ActivePartStack.PREFIX, JTPConstants.InactivePartStack.PREFIX, swtExtensions, helper)
				]
			]
			newPushButton[
				text = "Copy settings from Inactive to Active"
				onSelection = [
					copy(JTPConstants.InactivePartStack.PREFIX, JTPConstants.ActivePartStack.PREFIX, swtExtensions, helper)
				]
			]
		]
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	private def void copy(String from, String to, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		var sibilings = (activePage as PartStacksPage).partStackPage
		var fromPage = sibilings.filter(typeof(PartStackPage)).findFirst[it.context == from]
		var toPage = sibilings.filter(typeof(PartStackPage)).findFirst[it.context == to]
		
		var fakeStore = new JThemePreferenceStore(new PreferenceStore())
		fakeStore.customKeyResolver = [it]
		
		fromPage.save(fakeStore, swtExtensions, helper)
		toPage.load(fakeStore, swtExtensions, helper)
		
		requestUpdatePreview()
	}

}
