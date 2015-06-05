package net.jeeeyul.eclipse.themes.test.e4app

import java.util.List
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory
import org.eclipse.e4.ui.model.application.ui.menu.ItemType
import org.eclipse.e4.ui.di.AboutToShow

class DynamicMenu {
	
	@AboutToShow
	def populate(List<MMenuElement> items) {
		items += MMenuFactory.INSTANCE.createDirectMenuItem => [
			label = "hello"
			type = ItemType.RADIO
			selected = true
		]
		items += MMenuFactory.INSTANCE.createDirectMenuItem => [
			label = "hello 2"
			type = ItemType.RADIO
		]
		items += MMenuFactory.INSTANCE.createDirectMenuItem => [
			label = "hello 3"
			type = ItemType.RADIO
		]
	}
}