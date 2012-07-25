package net.jeeeyul.eclipse.themes.e4;

import java.util.HashSet;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationFactory;

@SuppressWarnings("restriction")
public class ChromeProcessor {
	@Execute
	void addAddon(MApplication application) {
		HashSet<String> contributionIds = new HashSet<String>();
		for (MAddon a : application.getAddons()) {
			if (a.getContributionURI() != null) {
				contributionIds.add(a.getContributionURI());
			}
		}

		if (!contributionIds.contains(getContributionURI(SashTracker.class))) {
			MAddon chromeAddon = MApplicationFactory.INSTANCE.createAddon();
			chromeAddon.setContributionURI(getContributionURI(SashTracker.class));
			chromeAddon.setContributorURI("plugin://" + ChromeThemeCore.getDefault().getBundle().getSymbolicName());
			application.getAddons().add(chromeAddon);
		}

		if (!contributionIds.contains(getContributionURI(ThemeTracker.class))) {
			MAddon chromeAddon = MApplicationFactory.INSTANCE.createAddon();
			chromeAddon.setContributionURI(getContributionURI(ThemeTracker.class));
			chromeAddon.setContributorURI("plugin://" + ChromeThemeCore.getDefault().getBundle().getSymbolicName());
			application.getAddons().add(chromeAddon);
		}

	}

	private String getContributionURI(Class<?> addonClass) {
		return "bundleclass://" + ChromeThemeCore.getDefault().getBundle().getSymbolicName() + "/" + addonClass.getCanonicalName();
	}
}
