package net.jeeeyul.eclipse.themes.preference.actions;

import java.util.List;

import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.preference.internal.UserPreset;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.CompoundContributionItem;

public class UserPresetItems extends CompoundContributionItem {
	private JTPreferencePage page;

	public UserPresetItems(JTPreferencePage page) {
		this.page = page;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		List<UserPreset> userPresets = page.getPresetManager().getUserPresets();
		if(userPresets.size() == 0){
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
