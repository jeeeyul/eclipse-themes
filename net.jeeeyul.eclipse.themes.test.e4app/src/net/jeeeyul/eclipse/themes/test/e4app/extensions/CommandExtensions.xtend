package net.jeeeyul.eclipse.themes.test.e4app.extensions

import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem
import org.eclipse.core.commands.ParameterizedCommand
import org.eclipse.e4.core.commands.ECommandService
import com.google.inject.Inject
import javax.inject.Singleton
import java.util.HashMap
import org.eclipse.e4.core.di.annotations.Creatable

@Singleton
@Creatable
class CommandExtensions {
	@Inject ECommandService commandService

	def generateWBCommand(MHandledItem item) {
		var wbCommand = commandService.getCommand(item.command.elementId)

		val HashMap<String, String> map = new HashMap
		item.parameters.forEach [
			map.put(it.name, it.value)
		]
		return ParameterizedCommand.generateCommand(wbCommand, map)
	}
}