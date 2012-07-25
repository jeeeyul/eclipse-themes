package net.jeeeyul.eclipse.themes.e4;

import java.util.HashSet;
import java.util.Set;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationFactory;

@SuppressWarnings("restriction")
public class ChromeProcessor {
	@Execute
	void addAddon(MApplication application) {
		Set<String> contributionIds = getContributionURIs(application);

		if (!contributionIds.contains(getContributionURI(WidgetTracker.class))) {
			installAddon(application, WidgetTracker.class);
		}

		if (!contributionIds.contains(getContributionURI(ThemeTracker.class))) {
			installAddon(application, ThemeTracker.class);
		}
	}

	private String getContributionURI(Class<?> addonClass) {
		return "bundleclass://" + ChromeThemeCore.getDefault().getBundle().getSymbolicName() + "/" + addonClass.getCanonicalName();
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
		return "plugin://" + ChromeThemeCore.getDefault().getBundle().getSymbolicName();
	}

	private void installAddon(MApplication application, Class<?> addOn) {
		MAddon chromeAddon = MApplicationFactory.INSTANCE.createAddon();
		chromeAddon.setContributionURI(getContributionURI(addOn));
		chromeAddon.setContributorURI(getContributorURI());
		application.getAddons().add(chromeAddon);
	}
}
