package net.jeeeyul.eclipse.themes.ui.internal;

import java.text.MessageFormat;

import net.jeeeyul.eclipse.themes.JTRuntime;
import net.jeeeyul.eclipse.themes.JThemesCore;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;

/**
 * This processor is contributed by "org.eclipse.e4.workbench.model" extension
 * point.
 * 
 * It installs {@link ActiveThemeProcessor}. And it uninstall deprecated or
 * removed addons.
 * 
 * @author Jeeeyul
 * @since 2.0.0
 */
public class OldAddonUninstaller {
	/**
	 * Installs {@link MAddon} to given application.
	 * 
	 * @param application
	 *            An application to install addons.
	 */
	@Execute
	public void addAddon(MApplication application) {
		Debug.println("Old Addon Uninstaller started");
		validateAddons(application);
	}

	/**
	 * 15: Removed Add-on cause error continuously
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/15
	 * 
	 * @param application
	 * @since 1.2
	 */
	private void validateAddons(MApplication application) {
		MAddon[] addonList = application.getAddons().toArray(new MAddon[application.getAddons().size()]);
		for (MAddon eachAddon : addonList) {
			if (isContributedByJTheme(eachAddon)) {
				application.getAddons().remove(eachAddon);
				JThemesCore.getDefault().logWarn(MessageFormat.format("{0} is deprecated addon, so it was removed.", eachAddon.getContributionURI()));
			}
		}
	}

	private boolean isContributedByJTheme(MAddon each) {
		String contributorURI = each.getContributorURI();
		return MessageFormat.format("plugin://{0}", JTRuntime.PLUGIN_ID).equals(contributorURI)
				|| MessageFormat.format("plugin://{0}", JThemesCore.PLUGIN_ID).equals(contributorURI);
	}

}