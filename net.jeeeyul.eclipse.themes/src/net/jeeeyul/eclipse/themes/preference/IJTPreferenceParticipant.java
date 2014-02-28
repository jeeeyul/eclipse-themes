package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.rendering.JTabSettings;

import org.eclipse.swt.custom.CTabFolder;

public interface IJTPreferenceParticipant {
	public void load(JThemePreferenceStore preferenceStore);
	public void save(JThemePreferenceStore preferenceStore);
	public void updatePreview(CTabFolder folder, JTabSettings renderSettings);
}
