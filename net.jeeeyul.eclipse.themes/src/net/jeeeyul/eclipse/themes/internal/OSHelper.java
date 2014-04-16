package net.jeeeyul.eclipse.themes.internal;

/**
 * An utility that detecte some OS independent environment.
 * 
 * @author Jeeeyul
 */
public class OSHelper {
	/**
	 * single tone
	 */
	public static final OSHelper INSTANCE = new OSHelper();

	private OSHelper() {

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
