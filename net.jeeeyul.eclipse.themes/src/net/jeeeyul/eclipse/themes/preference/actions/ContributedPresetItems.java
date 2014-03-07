package net.jeeeyul.eclipse.themes.preference.actions;

import java.util.List;

import net.jeeeyul.eclipse.themes.preference.JTPreperencePage;
import net.jeeeyul.eclipse.themes.preference.preset.ContributedPreset;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

public class ContributedPresetItems extends CompoundContributionItem {
	private JTPreperencePage page;

	public ContributedPresetItems(JTPreperencePage page) {
		this.page = page;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		if (!Platform.isRunning()) {
			return new IContributionItem[0];
		}

		List<ContributedPreset> presets = page.getPresetManager().getContributedPresets();
		IContributionItem[] result = new IContributionItem[presets.size()];
		for (int i = 0; i < presets.size(); i++) {
			LoadPresetAction action = new LoadPresetAction(page, presets.get(i));
			result[i] = new ActionContributionItem(action);
		}

		return result;
	}

}
