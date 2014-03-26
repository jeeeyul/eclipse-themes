package net.jeeeyul.eclipse.themes.ui

import net.jeeeyul.eclipse.themes.SharedImages
import org.eclipse.e4.ui.css.swt.theme.ITheme
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.jface.action.Action
import org.eclipse.swt.SWT
import org.eclipse.ui.PlatformUI

class SwitchThemeAction extends Action {
	ITheme theme

	new(ITheme theme) {
		super(theme.label, SWT.CHECK)
		this.theme = theme
		imageDescriptor = SharedImages.getImageDescriptor(SharedImages.CSS)
		
		/*
		 * https://github.com/jeeeyul/eclipse-themes/issues/140
		 */
		setChecked(themeEngine.activeTheme == theme)
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
