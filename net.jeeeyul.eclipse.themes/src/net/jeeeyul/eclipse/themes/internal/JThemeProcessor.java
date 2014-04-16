package net.jeeeyul.eclipse.themes.internal;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import net.jeeeyul.eclipse.themes.JThemesCore;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationFactory;

/**
 * This processor is contributed by "org.eclipse.e4.workbench.model" extension
 * point.
 * 
 * It installs {@link ActiveThemeTracker}. And it uninstall deprecated or
 * removed addons.
 * 
 * @author Jeeeyul
 * @since 2.0.0
 */
public class JThemeProcessor {
	/**
	 * Installs {@link MAddon} to given application.
	 * 
	 * @param application
	 *            An application to install addons.
	 */
	@Execute
	public void addAddon(MApplication application) {
		validateAddons(application);

		Set<String> contributionIds = getContributionURIs(application);

		if (!contributionIds.contains(getContributionURI(ActiveThemeTracker.class))) {
			installAddon(application, ActiveThemeTracker.class);
		}
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
				boolean validate = isValidAddon(eachAddon);
				if (!validate) {
					application.getAddons().remove(eachAddon);
					JThemesCore.getDefault().logWarn(MessageFormat.format("{0} is deprecated addon, so it was removed.", eachAddon.getContributionURI()));
				}
			}
		}
	}

	private boolean isContributedByJTheme(MAddon each) {
		return getContributorURI().equals(each.getContributorURI());
	}

	private String getContributionURI(Class<?> addonClass) {
		return MessageFormat.format("bundleclass://{0}/{1}", JThemesCore.getDefault().getBundle().getSymbolicName(), addonClass.getCanonicalName());
	}

	private Set<String> getContributionURIs(MApplication application) {
		HashSet<String> result = new HashSet<String>();

		for (MAddon each : application.getAddons()) {
			String contributionURI = each.getContributionURI();
			if (contributionURI != null) {
				result.add(contributionURI);
			}
		}
		return result;
	}

	private String getContributorURI() {
		return MessageFormat.format("plugin://{0}", JThemesCore.getDefault().getBundle().getSymbolicName());
	}

	private void installAddon(MApplication application, Class<?> addOn) {
		MAddon addonInstance = MApplicationFactory.INSTANCE.createAddon();
		addonInstance.setContributionURI(getContributionURI(addOn));
		addonInstance.setContributorURI(getContributorURI());
		application.getAddons().add(addonInstance);
	}

	/**
	 * 15: Removed Add-on cause error continuously
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/15
	 * 
	 * @param addon
	 * @return
	 * @since 1.2
	 */
	private boolean isValidAddon(MAddon addon) {
		String uri = addon.getContributionURI();
		IPath path = new Path(uri);
		Class<?> type = null;
		try {
			type = Class.forName(path.lastSegment());
		} catch (ClassNotFoundException e) {
			return false;
		}
		return type != null;
	}
}