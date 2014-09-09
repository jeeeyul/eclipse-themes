package net.jeeeyul.eclipse.themes.css.internal;

import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT;

import java.lang.reflect.Field;
import java.util.Map;

import net.jeeeyul.eclipse.themes.internal.Debug;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.AbstractTextEditor;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class NamedColorHack {
	private static final String FIELD_NAME__COLOR_NAMES_MAP = "colorNamesMap";

	private static final String PLUGIN_ID__TEXT_EDITORS = "org.eclipse.ui.editors";

	/**
	 * Singleton
	 */
	public static final NamedColorHack INSTANCE = new NamedColorHack();

	private Map<String, String> namedColorMap;
	private UIJob updateJob;
	private IPreferenceStore fEditorStore;
	private IPropertyChangeListener listener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			getUpdateJob().schedule(500);
		}
	};

	private NamedColorHack() {
	}

	private IPreferenceStore getEditorStore() {
		if (fEditorStore == null) {
			fEditorStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID__TEXT_EDITORS);
		}
		return fEditorStore;
	}

	private HSB getHSBFromEditorPreference(String key) {
		String bgExp = getEditorStore().getString(key);
		if (bgExp == null || bgExp.trim().length() == 0) {
			return new HSB(0, 0, 0);
		}
		String[] segments = bgExp.split("[\\s,]+");
		int r = Integer.parseInt(segments[0], 10);
		int g = Integer.parseInt(segments[1], 10);
		int b = Integer.parseInt(segments[2], 10);
		HSB hsb = new HSB(r, g, b);
		return hsb;
	}

	private HSB getHSBFromSystemColor(int key) {
		return new HSB(Display.getDefault().getSystemColor(key).getRGB());
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getNameColorMap() throws NoSuchFieldException, IllegalAccessException {
		if (namedColorMap == null) {
			Field mapField = CSS2ColorHelper.class.getDeclaredField(FIELD_NAME__COLOR_NAMES_MAP);
			mapField.setAccessible(true);
			namedColorMap = (Map<String, String>) mapField.get(CSS2ColorHelper.class);
		}
		return namedColorMap;
	}

	private synchronized UIJob getUpdateJob() {
		if (updateJob == null) {
			updateJob = new UIJob(Display.getDefault(), "Update Named Colors") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					updateAll();
					return Status.OK_STATUS;
				}
			};
			updateJob.setSystem(true);
			updateJob.setUser(false);
		}
		return updateJob;
	}

	/**
	 * Starts to watch {@link AbstractTextEditor}'s preferences and updates
	 * named colors in
	 */
	public void start() {
		if (textEditorAvailable()) {
			getEditorStore().addPropertyChangeListener(listener);
			getUpdateJob().schedule();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		if (textEditorAvailable()) {
			getEditorStore().removePropertyChangeListener(listener);
		}
	}

	private boolean textEditorAvailable() {
		return Platform.getBundle(PLUGIN_ID__TEXT_EDITORS) != null;
	}

	private void update(String colorname, String defaultKey, int defaultSWTKey, String valueKey) {
		try {
			Map<String, String> map = getNameColorMap();
			if (getEditorStore().getBoolean(defaultKey)) {
				map.put(colorname, getHSBFromSystemColor(defaultSWTKey).toHTMLCode());
			} else {
				map.put(colorname, getHSBFromEditorPreference(valueKey).toHTMLCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateAll() {
		Debug.println("Start Updating Named Colors");

		update("jtexteditor-background", PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_BACKGROUND, PREFERENCE_COLOR_BACKGROUND);
		update("jtexteditor-foreground", PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_FOREGROUND, PREFERENCE_COLOR_FOREGROUND);
		update("jtexteditor-selection-background", PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_SELECTION,
				PREFERENCE_COLOR_SELECTION_BACKGROUND);
		update("jtexteditor-selection-foreground", PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_SELECTION_TEXT,
				PREFERENCE_COLOR_SELECTION_FOREGROUND);

		Debug.println("Updating Named Colors is Done");
	}
}
