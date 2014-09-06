package net.jeeeyul.eclipse.themes.ui;

import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Populates preset items on drop-down menu in toolbar those are made by user.
 * 
 * @author Jeeeyul
 */
public class UserPresetItems extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		IJTPresetManager presetManager = JThemesCore.getDefault().getPresetManager();
		List<IJTPreset> userPresets = presetManager.getUserCategory().getPresets();
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
