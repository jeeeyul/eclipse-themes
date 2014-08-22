package net.jeeeyul.eclipse.themes.css;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

public class SystemColorProvider {
	@SuppressWarnings("deprecation")
	private static final IPreferenceStore EDITOR_STORE = new ScopedPreferenceStore(new InstanceScope(), "org.eclipse.ui.editors");

	public boolean useSystemBackground() {
		return EDITOR_STORE.getBoolean(AbstractDecoratedTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT);
	}

	public boolean userSystemForeground() {
		return EDITOR_STORE.getBoolean(AbstractDecoratedTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT);
	}
}
