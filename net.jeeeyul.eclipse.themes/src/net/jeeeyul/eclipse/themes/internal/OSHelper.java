package net.jeeeyul.eclipse.themes.internal;

public class OSHelper {
	public static final OSHelper INSTANCE = new OSHelper();

	public OSHelper() {
	}

	public boolean isWindow() {
		return getOSName().startsWith("Windows");
	}

	private String getOSName() {
		return System.getProperty("os.name");
	}

	public boolean isLinux() {
		return getOSName().startsWith("Linux");
	}

	public int getMinimumTabHeight() {
		if (isLinux()) {
			return 30;
		} else {
			return 22;
		}
	}
}
