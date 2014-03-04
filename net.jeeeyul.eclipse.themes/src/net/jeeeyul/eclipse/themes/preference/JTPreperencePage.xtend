package net.jeeeyul.eclipse.themes.preference

import java.io.File
import java.util.List
import java.util.Properties
import net.jeeeyul.eclipse.themes.preference.internal.ClosePrevent
import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.preference.PreferenceDialog
import org.eclipse.jface.preference.PreferenceManager
import org.eclipse.jface.preference.PreferenceNode
import org.eclipse.jface.preference.PreferencePage
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IWorkbench
import org.eclipse.ui.IWorkbenchPreferencePage
import net.jeeeyul.eclipse.themes.JThemesCore
import net.jeeeyul.eclipse.themes.css.RewriteCustomTheme
import org.eclipse.core.runtime.Platform

class JTPreperencePage extends PreferencePage implements IWorkbenchPreferencePage {
	extension SWTExtensions swtExt = SWTExtensions.INSTANCE
	extension PreperencePageHelper helper = new PreperencePageHelper(this);

	JeeeyulsTabRenderer renderer
	CTabFolder folder

	List<AbstractJTPreferencePage> pages = newArrayList(
		new WindowPage,
		new PartStackPage("Active Stack", JTPConstants.ActivePartStack.PREFIX),
		new PartStackPage("Inactive Stack", JTPConstants.InactivePartStack.PREFIX),
		new EmptyPartStackPage,
		new LayoutPage
	)

	new() {
		title = "Jeeeyul's Theme"
	}

	override init(IWorkbench workbench) {
		this.preferenceStore = JThemesCore.^default.preferenceStore
	}

	override public createContents(Composite parent) {
		folder = parent.newCTabFolder(SWT.CLOSE)[]
		folder => [
			
			setUnselectedCloseVisible(false)
			addCTabFolder2Listener(new ClosePrevent)
			it.renderer = renderer = new JeeeyulsTabRenderer(it) => [
				debug = false
			]
			onSelection = [
				updatePreview()
			]
			for (each : pages) {
				newCTabItem[
					it.text = each.name
					it.image = each.image
					it.control = each.createContents(folder, swtExt, helper)
					it.data = each
				]
			}
			folder.selection = folder.items.head
		]

		for (each : pages) {
			each.load(preferenceStore, swtExt, helper)
			each.updatePreview()
		}

		return folder
	}

	override performOk() {
		pages.forEach[it.save(preferenceStore, swtExt, helper)]
		preferenceStore.save()
		if(Platform.running)
			new RewriteCustomTheme().rewrite()
			
		return true
	}

	override JThemePreferenceStore getPreferenceStore() {
		super.preferenceStore as JThemePreferenceStore
	}

	def AbstractJTPreferencePage getActivePage() {
		folder.selection.data as AbstractJTPreferencePage
	}

	def private void updatePreview(AbstractJTPreferencePage page) {
		page.updatePreview(folder, renderer.settings, swtExt, helper)
	}

	def static void main(String[] args) {
		var manager = new PreferenceManager()
		var prefPage = new JTPreperencePage
		manager.addToRoot(new PreferenceNode("Active", prefPage))
		var userDir = System.getProperty("user.home");
		var file = new File(userDir, ".jet-dummy-pref");
		var store = new PreferenceStore(file.getAbsolutePath());
		var defaults = new Properties
		defaults.load(typeof(JTPreperencePage).getResourceAsStream("default.epf"))
		for (each : defaults.keySet) {
			store.setDefault(each as String, defaults.getProperty(each as String))
		}
		try {
			store.load()
		} catch(Exception e) {
		}
		prefPage.setPreferenceStore(new JThemePreferenceStore(store))
		new PreferenceDialog(null, manager).open

	}

	def void updatePreview() {
		doUpdatePreview()
	}

	def void doUpdatePreview() {
		for (p : pages) {
			if(p instanceof PartStackPage) {
				if(activePage == p || p.context == JTPConstants.ActivePartStack.PREFIX) {
					p.updatePreview()
				}
			} else {
				p.updatePreview()
			}
		}
	}

	override dispose() {
		pages.forEach[it.dispose(swtExt, helper)]
		super.dispose()
	}

}
