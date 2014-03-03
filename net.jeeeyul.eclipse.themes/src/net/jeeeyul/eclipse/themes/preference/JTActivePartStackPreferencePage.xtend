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
import org.eclipse.ui.progress.UIJob

class JTActivePartStackPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	extension SWTExtensions swtExt = SWTExtensions.INSTANCE
	extension PreperencePageHelper helper = new PreperencePageHelper(this);

	JeeeyulsTabRenderer renderer
	CTabFolder folder

	List<AbstractJTPreferencePage> pages = newArrayList(new PartStackPage, new LayoutPage)

	UIJob updatePreview = newDeferredJob("Update Preview") [
		doUpdatePreview()
	]

	override init(IWorkbench workbench) {
	}

	override public createContents(Composite parent) {
		folder = parent.newCTabFolder(SWT.CLOSE)[]
		folder => [
			setUnselectedCloseVisible(false)
			addCTabFolder2Listener(new ClosePrevent)
			it.renderer = renderer = new JeeeyulsTabRenderer(it) => [
				debug = false
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
		return true
	}

	override JThemePreferenceStore getPreferenceStore() {
		new JThemePreferenceStore(super.preferenceStore as PreferenceStore)
	}

	def AbstractJTPreferencePage getActivePage() {
		folder.selection.data as AbstractJTPreferencePage
	}

	def private void updatePreview(AbstractJTPreferencePage page) {
		page.updatePreview(folder, renderer.settings, swtExt, helper)
	}

	def static void main(String[] args) {
		var manager = new PreferenceManager()
		var activePage = new JTActivePartStackPreferencePage
		manager.addToRoot(new PreferenceNode("Active", activePage))
		var userDir = System.getProperty("user.home");
		var file = new File(userDir, ".jet-dummy-pref");
		var store = new PreferenceStore(file.getAbsolutePath());
		var defaults = new Properties
		defaults.load(typeof(JTActivePartStackPreferencePage).getResourceAsStream("default.epf"))
		for(each : defaults.keySet){
			store.setDefault(each as String, defaults.getProperty(each as String))
		}		
		try {
			store.load()
		} catch(Exception e) {
		}
		activePage.setPreferenceStore(store)
		new PreferenceDialog(null, manager).open

	}

	def void updatePreview() {

		//		updatePreview.schedule()
		doUpdatePreview()
	}

	def void doUpdatePreview() {
		for (p : pages) {
			p.updatePreview()
		}
	}

	override dispose() {
		pages.forEach[it.dispose(swtExt, helper)]
		super.dispose()
	}

}
