package net.jeeeyul.eclipse.themes;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

public class Nature {
	public static final Nature INSTANCE = new Nature();

	public static final VersionRange JUNO_RANGE = new VersionRange("[4.2.0, 4.2.1)");
	public static final VersionRange JUNO_SR1_RANGE = new VersionRange("[4.2.1, 4.2.2)");

	private Version version;

	public Version getVersion() {
		if (version == null) {
			Bundle bundle = Platform.getBundle("org.eclipse.platform");
			String versionString = bundle.getHeaders().get(Constants.BUNDLE_VERSION);
			version = new Version(versionString);
		}
		return version;
	}

	public boolean afterJunoSR1() {
		return getVersion().compareTo(JUNO_SR1_RANGE.getMinimum()) >= 0;
	}

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

	public static void main(String[] args) {
	}
}
