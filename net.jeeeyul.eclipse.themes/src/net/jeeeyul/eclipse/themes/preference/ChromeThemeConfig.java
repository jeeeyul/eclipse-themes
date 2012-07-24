package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.Activator;
import net.jeeeyul.eclipse.themes.decorator.EmptyDecorator;
import net.jeeeyul.eclipse.themes.decorator.GradientDecorator;
import net.jeeeyul.eclipse.themes.decorator.ICTabFolderDecorator;
import net.jeeeyul.eclipse.themes.decorator.InactiveDecorator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
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
			float start[] = new float[3];
			start[0] = store.getFloat("chrome-active-start-hue");
			start[1] = store.getFloat("chrome-active-start-saturation");
			start[2] = store.getFloat("chrome-active-start-brightness");

			float end[] = new float[3];
			end[0] = store.getFloat("chrome-active-end-hue");
			end[1] = store.getFloat("chrome-active-end-saturation");
			end[2] = store.getFloat("chrome-active-end-brightness");
			activeDecorator = new GradientDecorator(start, end);
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
