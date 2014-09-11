package net.jeeeyul.eclipse.themes.ui.preference.internal

import java.io.File
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Properties
import net.jeeeyul.eclipse.themes.JThemesCore
import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer
import net.jeeeyul.eclipse.themes.ui.hotswap.RewriteCustomTheme
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.ui.preference.actions.AddUserPresetAction
import net.jeeeyul.eclipse.themes.ui.preference.actions.ContributedPresetItems
import net.jeeeyul.eclipse.themes.ui.preference.actions.ManagePresetAction
import net.jeeeyul.eclipse.themes.ui.preference.actions.ShowCSSAction
import net.jeeeyul.eclipse.themes.ui.preference.actions.ShowEPFAction
import net.jeeeyul.eclipse.themes.ui.preference.actions.UserPresetItems
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.core.runtime.Platform
import org.eclipse.jface.action.MenuManager
import org.eclipse.jface.action.Separator
import org.eclipse.jface.preference.PreferenceDialog
import org.eclipse.jface.preference.PreferenceManager
import org.eclipse.jface.preference.PreferenceNode
import org.eclipse.jface.preference.PreferencePage
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.ToolItem
import org.eclipse.ui.IWorkbench
import org.eclipse.ui.IWorkbenchPreferencePage
import org.eclipse.ui.progress.UIJob
import org.eclipse.xtend.lib.annotations.Accessors

class JTPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static val String ID = typeof(JTPreferencePage).canonicalName

	extension SWTExtensions swtExt = SWTExtensions.INSTANCE
	Map<AbstractJTPreferencePage, PreperencePageHelper> helperMap = new HashMap
	Composite rootView
	JeeeyulsTabRenderer renderer
	CTabFolder folder
	List<AbstractJTPreferencePage> pages = new ArrayList()
	MenuManager menuManager
	UIJob updatePreviewJob = newUIJob[
		if(control.alive)
			doUpdatePreview()
	]

	/*
	 * https://github.com/jeeeyul/eclipse-themes/issues/140
	 */
	@Accessors String lastChoosedPresetId

	new() {
		title = "Jeeeyul's Theme"
		pages += new GeneralPage
		pages += new PartStacksPage
		pages += new TextEditorPage
		pages += new FormsPage
		pages += new UserCSSPage
		pages += new ToolsPage
	}

	override init(IWorkbench workbench) {
		this.preferenceStore = JThemesCore.^default.preferenceStore
	}

	override public createContents(Composite parent) {
		for (e : pages) {
			e.init(e.helper)
		}

		rootView = parent.newComposite [
			layout = newGridLayout[]
			folder = new PreviewTabFolder(it, SWT.CLOSE)
			folder => [
				layoutData = FILL_HORIZONTAL[
					widthHint = 450
				]
				setUnselectedCloseVisible(false)
				maximizeVisible = true
				minimizeVisible = true
				addCTabFolder2Listener(new ClosePrevent)
				it.renderer = renderer = new JeeeyulsTabRenderer(it)
				for (each : pages) {
					newCTabItem[
						it.text = each.name
						it.image = each.image
						it.control = each.createContents(folder, swtExt, each.helper)
						it.control.background = COLOR_WHITE;
						(it.control as Composite).backgroundMode = SWT.INHERIT_FORCE
						it.data = each
					]
				}
				folder.selection = folder.items.head
				onSelection = [
					activePage.updatePreview()
				]
			]
			new DonationPanel(it) => [
				control.layoutData = FILL_HORIZONTAL
			]
		]

		folder.topRight = folder.newToolBar [
			newToolItem(SWT.DROP_DOWN) [
				image = SharedImages.getImage(SharedImages.JTHEME)
				onSelection = [
					menuManager.updateAll(true)
					var item = widget as ToolItem
					var m = menuManager.menu
					m.location = item.parent.toDisplay(item.bounds.bottomLeft.getTranslated(0, 2))
					m.visible = true
				]
			]
		]

		menuManager = new MenuManager()
		menuManager.createContextMenu(folder)

		createActions()

		doLoad()
		doUpdatePreview()

		return rootView
	}

	private def createActions() {
		menuManager => [
			if(presetManager != null) {
				add(
					new MenuManager(presetManager.userCategory.name, SharedImages.getImageDescriptor(SharedImages.PRESET), "user.preset") => [
						add(new AddUserPresetAction(this))
						add(new ManagePresetAction(this))
						add(new UserPresetItems(this))
					])
				for (eachCategory : presetManager.categories) {
					if(eachCategory != presetManager.userCategory) {
						add(
							new MenuManager(eachCategory.name, SharedImages.getImageDescriptor(SharedImages.PRESET), "contributed.preset") => [
								add(new ContributedPresetItems(this, eachCategory))
							])
					}
				}
			}
			add(new Separator)
			add(new ShowCSSAction(this))
			add(new ShowEPFAction(this))
		]
	}

	private def doLoad() {
		loadFrom(preferenceStore)
	}

	override performOk() {
		saveTo(preferenceStore)
		preferenceStore.save()
		if(Platform.running)
			new RewriteCustomTheme(true).rewrite()

		return true
	}

	public def void saveTo(JThemePreferenceStore store) {
		pages.forEach[it.save(store, swtExt, helper)]

		/*
		 * https://github.com/jeeeyul/eclipse-themes/issues/140
		 */
		if(lastChoosedPresetId != null) {
			store.setValue(JTPConstants.Memento.LAST_CHOOSED_PRESET, lastChoosedPresetId)
		}
	}

	public def void loadFrom(JThemePreferenceStore store) {
		for (each : pages) {
			each.load(store, swtExt, each.helper)
		}
		updatePreview()
	}

	override protected performDefaults() {
		var dummy = new JThemePreferenceStore(new PreferenceStore())
		val presetKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET)
		for (e : presetKeys) {
			dummy.setValue(e, preferenceStore.getDefaultString(e))
		}

		for (e : pages) {
			e.load(dummy, swtExt, e.helper)
		}

		updatePreview()
	}

	override JThemePreferenceStore getPreferenceStore() {
		super.preferenceStore as JThemePreferenceStore
	}

	def AbstractJTPreferencePage getActivePage() {
		folder.selection.data as AbstractJTPreferencePage
	}

	def private void updatePreview(AbstractJTPreferencePage page) {
		page.updatePreview(folder, renderer.settings, swtExt, page.helper)
	}

	def static void main(String[] args) {
		var manager = new PreferenceManager()
		var prefPage = new JTPreferencePage
		manager.addToRoot(new PreferenceNode("Active", prefPage))
		var userDir = System.getProperty("user.home");
		var file = new File(userDir, ".jet-dummy-pref");
		var store = new PreferenceStore(file.getAbsolutePath());
		var defaults = new Properties
		defaults.load(typeof(JTPreferencePage).getResourceAsStream("default.epf"))
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
		updatePreviewJob.schedule()
	}

	def void doUpdatePreview() {
		if(!control.alive) {
			return
		}

		for (p : pages) {
			p.updatePreview()
		}

		rootView.layout(#[folder])
	}

	override dispose() {
		pages.forEach[it.dispose(swtExt, helper)]
		super.dispose()
	}

	private def getHelper(AbstractJTPreferencePage page) {
		var result = helperMap.get(page)
		if(result == null) {
			result = new PreperencePageHelper(this, page)
			helperMap.put(page, result)
		}
		return result
	}

	def CTabFolder getFolder() {
		return this.folder
	}

	def IJTPresetManager getPresetManager() {
		if(Platform.running)
			return JThemesCore.^default.presetManager
		else
			null
	}

	def AbstractJTPreferencePage[] getAllPages() {
		var List<AbstractJTPreferencePage> result = new ArrayList<AbstractJTPreferencePage>()
		for (each : pages) {
			result += each
			result.addAll(each.children)
		}
		return result
	}
}
