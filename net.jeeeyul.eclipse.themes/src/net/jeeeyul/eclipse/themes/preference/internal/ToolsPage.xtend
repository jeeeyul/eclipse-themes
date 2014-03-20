package net.jeeeyul.eclipse.themes.preference.internal

import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.SharedImages

class ToolsPage extends AbstractJTPreferencePage {
	new() {
		super("Tools")
		image = SharedImages.getImage(SharedImages.CONFIG)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[]
			newGroup[
				text = "Part Stack Batch Task"
				layout = newGridLayout
				layoutData = FILL_HORIZONTAL
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
		var stacksPage = allPages.filter(typeof(PartStacksPage)).head
		var sibilings =stacksPage.children
		var fromPage = sibilings.filter(typeof(PartStackPage)).findFirst[it.context == from]
		var toPage = sibilings.filter(typeof(PartStackPage)).findFirst[it.context == to]

		var fakeStore = new JThemePreferenceStore(new PreferenceStore())
		fakeStore.customKeyResolver = [it]

		fromPage.save(fakeStore, swtExtensions, helper)
		toPage.load(fakeStore, swtExtensions, helper)

		requestUpdatePreview()
	}

}
