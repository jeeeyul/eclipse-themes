package net.jeeeyul.eclipse.themes.ui;

import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.internal.UserPreset;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.CompoundContributionItem;

public class UserPresetItems extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		IJTPresetManager presetManager = JThemesCore.getDefault().getPresetManager();
		List<UserPreset> userPresets = presetManager.getUserPresets();
		if (userPresets.size() == 0) {
			return new IContributionItem[0];
		}

		IContributionItem[] result = new IContributionItem[userPresets.size() + 1];
		for (int i = 0; i < userPresets.size(); i++) {
			ApplyPresetAction action = new ApplyPresetAction(userPresets.get(i));
			result[i] = new ActionContributionItem(action);
		}
		result[result.length - 1] = new Separator();

		return result;
	}

}
