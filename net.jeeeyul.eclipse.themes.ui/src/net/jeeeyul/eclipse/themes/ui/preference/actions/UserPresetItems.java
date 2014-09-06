package net.jeeeyul.eclipse.themes.ui.preference.actions;

import java.util.List;

import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Populate load user preset items in preference page menu.
 * 
 * @author Jeeeyul
 */
public class UserPresetItems extends CompoundContributionItem {
	private JTPreferencePage page;

	/**
	 * Creates {@link UserPresetItems}
	 * 
	 * @param page
	 *            preference page.
	 */
	public UserPresetItems(JTPreferencePage page) {
		this.page = page;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		List<IJTPreset> userPresets = page.getPresetManager().getUserCategory().getPresets();
		if (userPresets.size() == 0) {
			return new IContributionItem[0];
		}

		IContributionItem[] result = new IContributionItem[userPresets.size() + 1];
		result[0] = new Separator();
		for (int i = 0; i < userPresets.size(); i++) {
			LoadPresetAction action = new LoadPresetAction(page, userPresets.get(i));
			result[i + 1] = new ActionContributionItem(action);
		}

		return result;
	}

}
