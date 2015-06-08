package net.jeeeyul.eclipse.themes.test.e4app.handlers

import java.util.List
import org.eclipse.e4.ui.di.AboutToShow
import org.eclipse.e4.ui.model.application.ui.menu.ItemType
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement
import org.eclipse.e4.ui.workbench.modeling.EModelService

class Dyna {
	@AboutToShow
	def void aboutToShow(List<MMenuElement> items, extension EModelService modelService) {
		items += MDirectMenuItem.createModelElement => [
			type = ItemType.CHECK
			label = "test"
			contributorURI = "platform:/plugin/net.jeeeyul.eclipse.themes.test.e4app"
			contributionURI = "bundleclass://net.jeeeyul.eclipse.themes.test.e4app/net.jeeeyul.eclipse.themes.test.e4app.handlers.AboutHandler"
		]
	}

}