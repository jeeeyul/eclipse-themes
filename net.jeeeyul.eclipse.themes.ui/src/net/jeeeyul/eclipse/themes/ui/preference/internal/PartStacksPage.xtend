package net.jeeeyul.eclipse.themes.ui.preference.internal

import java.util.HashMap
import java.util.Map
import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite

class PartStacksPage extends AbstractJTPreferencePage {
	AbstractJTPreferencePage[] pages = #[
		new PartStackPage("Active", JTPConstants.ActivePartStack.PREFIX),
		new PartStackPage("Inactive", JTPConstants.InactivePartStack.PREFIX),
		new LayoutPage,
		new PartStackETCPage
	]
	CTabFolder folder
	Map<AbstractJTPreferencePage, PreperencePageHelper> helperMap = new HashMap
	PreperencePageHelper helper

	new() {
		super("Parts")
		image = SharedImages.getImage(SharedImages.PART)
	}

	override init(extension PreperencePageHelper helper) {
		this.helper = helper
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for(e : pages){
			e.init(e.helper)
		}
		
		parent.newComposite [
			layout = newFillLayout[
				marginWidth = 5
				marginHeight = 5
			]
			folder = newCTabFolder(SWT.BOTTOM) [
				tabHeight = 22
			]
			folder => [
				for (e : pages) {
					newCTabItem[
						it.text = e.name
						it.image = e.image
						it.control = e.createContents(folder, swtExtensions, e.helper)
						it.data = e
					]
				}
			]
			folder.setSelection(0)
			folder.onSelection = [
				activePartStackPage.helper.requestFastUpdatePreview()
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
				var page = e as PartStackPage
				if(page.context == JTPConstants.InactivePartStack.PREFIX) {
					update = activePage == this && activePartStackPage == e
				} else {
					update = true
				}
			} else {
				update = true
			}

			if(update) {
				e.updatePreview(folder, renderSettings, swtExtensions, e.helper)
			}
		}
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			e.load(store, swtExtensions, e.helper)
		}
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			e.save(store, swtExtensions, e.helper)
		}
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		for (e : pages) {
			e.dispose(swtExtensions, e.helper)
		}
	}

	private def getHelper(AbstractJTPreferencePage page) {
		var result = helperMap.get(page)
		if(result == null) {
			result = new PreperencePageHelper(this.helper.rootPage, page)
			helperMap.put(page, result)
		}
		return result
	}
	
	override getChildren() {
		return pages
	}
	
}
