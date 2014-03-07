package net.jeeeyul.eclipse.themes.preference.preset;

import java.io.File;
import java.util.List;

import net.jeeeyul.eclipse.themes.preference.internal.UserPreset;

public interface IJTPresetManager {

	public abstract List<ContributedPreset> getContributedPresets();

	public abstract File getUserPresetFolder();

	public abstract List<UserPreset> getUserPresets();

	public abstract void invalidateUserPreset();
	
	public void addListener(IUserPresetChangeListener listener);
	
	public void removeListener(IUserPresetChangeListener listener);

}