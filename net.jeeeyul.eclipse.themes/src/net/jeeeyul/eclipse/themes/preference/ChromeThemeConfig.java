package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.Activator;
import net.jeeeyul.eclipse.themes.decorator.AutoGradientDecorator;
import net.jeeeyul.eclipse.themes.decorator.EmptyDecorator;
import net.jeeeyul.eclipse.themes.decorator.ICTabFolderDecorator;
import net.jeeeyul.eclipse.themes.decorator.InactiveDecorator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class ChromeThemeConfig implements IPropertyChangeListener {
	private static ChromeThemeConfig INSTANCE;

	public static ChromeThemeConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChromeThemeConfig();
		}
		return INSTANCE;
	}

	ICTabFolderDecorator activeDecorator;
	ICTabFolderDecorator inactiveDecorator;
	ICTabFolderDecorator emptyDecorator;

	private ChromeThemeConfig() {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	public ICTabFolderDecorator getActiveDecorator() {
		if (activeDecorator == null) {
			IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			float hsb[] = new float[3];
			hsb[0] = store.getFloat("chrome-active-hue");
			hsb[1] = store.getFloat("chrome-active-saturation");
			hsb[2] = store.getFloat("chrome-active-brightness");
			activeDecorator = new AutoGradientDecorator(hsb);
		}
		return activeDecorator;
	}

	public ICTabFolderDecorator getEmptyDecorator() {
		if (emptyDecorator == null) {
			emptyDecorator = new EmptyDecorator();
		}
		return emptyDecorator;
	}

	public ICTabFolderDecorator getInactiveDecorator() {
		if (inactiveDecorator == null) {
			inactiveDecorator = new InactiveDecorator();
		}
		return inactiveDecorator;
	}

	private void invalidate() {
		if (activeDecorator != null) {
			activeDecorator.dispose();
			activeDecorator = null;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		invalidate();
	}
}
