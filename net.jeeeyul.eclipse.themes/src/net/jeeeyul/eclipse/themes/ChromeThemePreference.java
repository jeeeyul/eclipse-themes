package net.jeeeyul.eclipse.themes;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class ChromeThemePreference implements IPropertyChangeListener {
	private static ChromeThemePreference INSTANCE;

	public static ChromeThemePreference getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new ChromeThemePreference();
		}
		return INSTANCE;
	}

	CTabFolderGradient activeGradient;
	CTabFolderGradient inactiveGradient;

	private ChromeThemePreference() {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	public CTabFolderGradient getActiveGradient() {
		if (activeGradient == null) {
			IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			float hsb[] = new float[3];
			hsb[0] = store.getFloat("chrome-active-hue");
			hsb[1] = store.getFloat("chrome-active-saturation");
			hsb[2] = store.getFloat("chrome-active-brightness");
			activeGradient = new CTabFolderGradient(hsb);
		}
		return activeGradient;
	}

	public CTabFolderGradient getInactiveGradient() {
		if (inactiveGradient == null) {
			IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			float hsb[] = new float[3];
			hsb[0] = store.getFloat("chrome-inactive-hue");
			hsb[1] = store.getFloat("chrome-inactive-saturation");
			hsb[2] = store.getFloat("chrome-inactive-brightness");
			inactiveGradient = new CTabFolderGradient(hsb);
		}
		return inactiveGradient;
	}

	private void invalidate() {
		if (activeGradient != null) {
			activeGradient.dispose();
			activeGradient = null;
		}

		if (inactiveGradient != null) {
			inactiveGradient.dispose();
			inactiveGradient = null;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		invalidate();
	}
}
