package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.ChromePage
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.widgets.Composite
import net.jeeeyul.eclipse.themes.ui.SWTExtensions

class PartPage extends ChromePage {
	extension SWTExtensions = SWTExtensions::INSTANCE
	
	CommonPartPage commonPage
	StateBasedPartPage activePage
	StateBasedPartPage inactivePage
	
	new(){
		super("Part", null);
		
		commonPage = new CommonPartPage
		activePage = new StateBasedPartPage("Active", true)
		inactivePage = new StateBasedPartPage("Inactive", false);
	}

	override create(Composite parent) {
		parent.layout = newGridLayout
		parent.newTabFolder[
			layoutData = FILL_HORIZONTAL
			
			newTabItem[
				text = "Common"
				
				control = it.parent.newComposite[
					commonPage.parentPage = parentPage
					commonPage.tabFolder = tabFolder
					commonPage.create(it)
				]
			]
			
			newTabItem[
				text = "Active Part"
				
				control = it.parent.newComposite[
					activePage.parentPage = parentPage
					activePage.tabFolder = tabFolder
					activePage.create(it)
				]
			]
			
			newTabItem[
				text = "Inactive Part"
				
				control = it.parent.newComposite[
					inactivePage.parentPage = parentPage
					inactivePage.tabFolder = tabFolder
					inactivePage.create(it)
				]
			]
		]
	}
	
	override load(IPreferenceStore preferenceStore) {
		commonPage.load(preferenceStore)
		activePage.load(preferenceStore)
		inactivePage.load(preferenceStore)
	}
	
	override save(IPreferenceStore preferenceStore) {
		commonPage.save(preferenceStore)
		activePage.save(preferenceStore)
		inactivePage.save(preferenceStore)
	}
	
	override setToDefault(IPreferenceStore preferenceStore) {
		commonPage.setToDefault(preferenceStore)
		activePage.setToDefault(preferenceStore)
		inactivePage.setToDefault(preferenceStore)
	}
}