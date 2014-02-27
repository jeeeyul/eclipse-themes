package net.jeeeyul.eclipse.themes.internal;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

public class Nature {
	public static final Nature INSTANCE = new Nature();

	public final VersionRange JUNO_RANGE = new VersionRange("[4.2.0, 4.2.1)");
	public final VersionRange JUNO_SR1_RANGE = new VersionRange("[4.2.1, 4.2.2)");
	public final VersionRange KEPLER_RANGE = new VersionRange("[4.3.0, 4.4.0)");
	public final VersionRange LUNA_RANGE = new VersionRange("[4.4.0, 4.5.0)");

	private Version version;

	public String getOS() {
		return Platform.getOS();
	}

	public int getToolbarHeight() {
		if (Platform.getOS().equals(Platform.OS_LINUX)) {
			return 30;
		} else {
			return 22;
		}
	}

	public Version getVersion() {
		if (version == null) {
			Bundle bundle = Platform.getBundle("org.eclipse.platform");
			String versionString = bundle.getHeaders().get(Constants.BUNDLE_VERSION);
			version = new Version(versionString);
		}
		return version;
	}

	public boolean isAfter(Version version, VersionRange range) {
		return version.compareTo(range.getMinimum()) >= 0;
	}
	
	public boolean isIncluded(Version version, VersionRange range) {
		return range.isIncluded(version);
	}
}
