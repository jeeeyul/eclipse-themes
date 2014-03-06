package net.jeeeyul.eclipse.themes.ui;

import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.preset.ContributedPreset;
import net.jeeeyul.eclipse.themes.preference.preset.JTPresetManager;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

public class ContributedPresetItem extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		JTPresetManager presetManager = JThemesCore.getDefault().getPresetManager();
		List<ContributedPreset> contributedPresets = presetManager.getContributedPresets();
		IContributionItem[] result = new IContributionItem[contributedPresets.size()];
		for (int i = 0; i < contributedPresets.size(); i++) {
			LoadPresetAction action = new LoadPresetAction(contributedPresets.get(i));
			result[i] = new ActionContributionItem(action);
		}

		return result;
	}

}
