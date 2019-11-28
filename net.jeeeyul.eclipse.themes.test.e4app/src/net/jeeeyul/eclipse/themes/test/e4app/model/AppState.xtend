package net.jeeeyul.eclipse.themes.test.e4app.model

import com.google.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.services.events.IEventBroker
import org.eclipse.xtend.lib.annotations.Accessors

@Singleton
class AppState {
	@Inject
	IEventBroker eventBroker

	@Accessors String mode = "default"

	def void setMode(String newMode) {
		this.mode = newMode;
		eventBroker.post("appstate/mode", newMode)
	}

}