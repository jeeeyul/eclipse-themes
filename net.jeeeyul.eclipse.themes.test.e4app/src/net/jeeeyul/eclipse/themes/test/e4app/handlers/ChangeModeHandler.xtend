package net.jeeeyul.eclipse.themes.test.e4app.handlers

import java.util.List
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import net.jeeeyul.eclipse.themes.test.e4app.model.AppState
import org.eclipse.e4.core.di.annotations.Execute
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.di.AboutToShow
import org.eclipse.e4.ui.di.UIEventTopic
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.e4.ui.model.application.commands.MCommand
import org.eclipse.e4.ui.model.application.commands.MParameter
import org.eclipse.e4.ui.model.application.ui.menu.ItemType
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem
import org.eclipse.e4.ui.model.application.ui.menu.MItem
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement
import org.eclipse.e4.ui.workbench.modeling.EModelService

@Singleton
class ChangeModeHandler {
	public static val COMMNAD_ID = "net.jeeeyul.eclipse.themes.test.e4app.changemode"

	new() {
		println("created")
	}

	@Inject extension EModelService

	@Execute def void execute(AppState appState, @Named("mode") String mode, MItem item) {
		if (!item.selected || appState.mode == mode) {
			return;
		}
		println('''Mode will be changed into «mode».''')
		appState.mode = mode
	}

	@Inject @Optional
	def void didChangeMode(@UIEventTopic("appstate/mode") String newMode, MApplication app) {
		updateWithMode(app, newMode)
		println("update")
	}

	@PostConstruct
	def void init(MApplication app, AppState state) {
		updateWithMode(app, state.mode)
	}

	@AboutToShow
	def void aboutToShow(List<MMenuElement> items, extension EModelService modelService, MApplication app,
		AppState appState) {
		val command = app.findElements(COMMNAD_ID, MCommand, #[]).head

		items += MHandledMenuItem.createModelElement => [
			it.command = command
			it.type = ItemType.RADIO
			it.parameters += MParameter.createModelElement => [
				it.name = "mode"
				it.value = "default"
			]
		]

		items += MHandledMenuItem.createModelElement => [
			it.command = command
			it.type = ItemType.RADIO
			it.parameters += MParameter.createModelElement => [
				it.name = "mode"
				it.value = "advanced"
			]
		]

		(items as MHandledItem[]).updateItemWithMode(appState.mode)
	}

	def private void updateWithMode(MApplication app, String mode) {
		var items = app.findElements(MHandledItem, EModelService.ANYWHERE.bitwiseOr(EModelService.IN_PART)) [
			var item = it as MHandledItem
			return item.command.elementId == COMMNAD_ID
		] as MHandledItem[]

		items.updateItemWithMode(mode)
	}

	def private void updateItemWithMode(MHandledItem[] items, String mode) {
		items.forEach [
			it.label = it.parameters.findFirst[name == "mode"]?.value ?: "null"
			it.tooltip = it.parameters.findFirst[name == "mode"]?.value ?: "null"
			it.selected = it.parameters.findFirst[name == "mode"]?.value == mode
		]
	}
}
