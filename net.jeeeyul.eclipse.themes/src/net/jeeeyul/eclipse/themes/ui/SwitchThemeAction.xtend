package net.jeeeyul.eclipse.themes.ui

import org.eclipse.e4.ui.css.swt.theme.ITheme
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.jface.action.Action
import org.eclipse.ui.PlatformUI

class SwitchThemeAction extends Action {
	ITheme theme

	new(ITheme theme) {
		this.theme = theme
		text = theme.label
	}

	override run() {
		themeEngine.setTheme(this.theme, true)
	}

	private def MApplication getApplication() {
		return PlatformUI.getWorkbench().getService(typeof(MApplication)) as MApplication
	}

	private def IThemeEngine getThemeEngine() {
		return application.context.get(typeof(IThemeEngine)) as IThemeEngine
	}

}
