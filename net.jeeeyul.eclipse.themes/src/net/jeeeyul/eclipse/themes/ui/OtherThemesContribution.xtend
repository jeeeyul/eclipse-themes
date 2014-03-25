package net.jeeeyul.eclipse.themes.ui

import java.util.ArrayList
import net.jeeeyul.eclipse.themes.JThemesCore
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.jface.action.ActionContributionItem
import org.eclipse.jface.action.IContributionItem
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.actions.CompoundContributionItem

class OtherThemesContribution extends CompoundContributionItem {

	override protected getContributionItems() {
		var result = new ArrayList<IContributionItem>

		for (each : themeEngine.themes) {
			if(each.id != JThemesCore.CUSTOM_THEME_ID)
				result += new ActionContributionItem(new SwitchThemeAction(each))
		}

		return result
	}

	private def MApplication getApplication() {
		return PlatformUI.getWorkbench().getService(typeof(MApplication)) as MApplication
	}

	private def IThemeEngine getThemeEngine() {
		return application.context.get(typeof(IThemeEngine)) as IThemeEngine
	}

}
