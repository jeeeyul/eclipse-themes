package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite

class PartStacksPage extends AbstractJTPreferencePage {
	AbstractJTPreferencePage[] pages = #[
		new PartStackPage("Active", JTPConstants.ActivePartStack.PREFIX), 
		new PartStackPage("Inactive", JTPConstants.InactivePartStack.PREFIX), 
		new SpecialPartStackPage,
		new LayoutPage,
		new PartStackBatchTaskPage
	]
	CTabFolder folder

	new() {
		super("Part Stacks")
		image = SharedImages.getImage(SharedImages.ACTIVE_PART)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newFillLayout[
				marginWidth = 5
				marginHeight = 5
			]
			folder = newCTabFolder(SWT.BOTTOM)[
				tabHeight = 22
			]
			folder => [
				for (e : pages) {
					newCTabItem[
						it.text = e.name
						it.image = e.image
						it.control = e.createContents(folder, swtExtensions, helper)
						it.data = e
					]
				}
			]
			folder.setSelection(0)
			folder.onSelection = [
				requestUpdatePreview()
			]
		]
	}

	def getActivePartStackPage() {
		folder.selection.data as AbstractJTPreferencePage
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			var update = false
			if(e instanceof PartStackPage) {
				if(e.context == JTPConstants.InactivePartStack.PREFIX) {
					update = activePage == this && activePartStackPage == e
				} else {
					update = true
				}
			} else {
				update = true
			}

			if(update) {
				e.updatePreview(folder, renderSettings, swtExtensions, helper)
			}
		}
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			e.load(store, swtExtensions, helper)
		}
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			e.save(store, swtExtensions, helper)
		}
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			e.dispose(swtExtensions, helper)
		}
	}

	def AbstractJTPreferencePage[] getPartStackPage() {
		return this.pages
	}
}
