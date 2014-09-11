package net.jeeeyul.eclipse.themes.internal;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;

/**
 * An utility that detecte some OS independent environment.
 * 
 * @author Jeeeyul
 */
public class ENVHelper {
	/**
	 * single tone
	 */
	public static final ENVHelper INSTANCE = new ENVHelper();

	private ENVHelper() {
		getE4WorkbenchVersion();
	}

	private Version getE4WorkbenchVersion() {
		if (Platform.isRunning()) {
			return Platform.getBundle("org.eclipse.e4.ui.workbench").getVersion();
		} else {
			return new Version(0, 0, 0);
		}
	}

	/**
	 * @return whether the eclipse is higher version than luna
	 */
	public boolean isLunaOrAbove() {
		return getE4WorkbenchVersion().compareTo(new Version(1, 1, 0)) >= 0;
	}

	/**
	 * 
	 * @return <code>true</code> if user using MS Windows.
	 */
	public boolean isWindow() {
		return getOSName().startsWith("Windows");
	}

	private String getOSName() {
		return System.getProperty("os.name");
	}

	/**
	 * 
	 * @return <code>true</code> if user using linux.
	 */
	public boolean isLinux() {
		return getOSName().startsWith("Linux");
	}
}
