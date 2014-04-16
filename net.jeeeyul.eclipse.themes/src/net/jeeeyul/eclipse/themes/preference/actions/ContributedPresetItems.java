package net.jeeeyul.eclipse.themes.preference.actions;

import java.util.List;

import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.preference.preset.internal.ContributedPreset;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Populate menu items that loads preset which are contributed by extension
 * point in preference page.
 * 
 * @author Jeeeyul
 * 
 * @see ContributedPreset
 * @see LoadPresetAction
 */
public class ContributedPresetItems extends CompoundContributionItem {
	private JTPreferencePage page;

	/**
	 * creates
	 * 
	 * @param page
	 */
	public ContributedPresetItems(JTPreferencePage page) {
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
