package net.jeeeyul.eclipse.themes.ui;

import java.util.List;

import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Populate preset items on drop down menu in toolbar from contributed preset
 * through extension point.
 * 
 * @author Jeeeyul
 * @since 2.1
 */
public class PresetsContribution extends CompoundContributionItem {

	private IJTPresetCategory category;

	/**
	 * @param category
	 */
	public PresetsContribution(IJTPresetCategory category) {
		this.category = category;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		List<IJTPreset> contributedPresets = category.getPresets();
		if (contributedPresets.size() == 0) {
			return new IContributionItem[0];
		}

		IContributionItem[] result = new IContributionItem[contributedPresets.size()];
		for (int i = 0; i < contributedPresets.size(); i++) {
			ApplyPresetAction action = new ApplyPresetAction(contributedPresets.get(i));
			result[i] = new ActionContributionItem(action);
		}

		return result;
	}

}
