package net.jeeeyul.eclipse.themes.ui;

import java.util.ArrayList;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * populates all categories into menu.
 * 
 * @author Jeeeyul
 */
public class CategoriesContribution extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		IJTPresetManager presetManager = JThemesCore.getDefault().getPresetManager();
		IJTPresetCategory[] categories = presetManager.getCategories();

		ArrayList<IContributionItem> result = new ArrayList<IContributionItem>();
		for (IJTPresetCategory each : categories) {
			if (each.getPresets().size() == 0) {
				continue;
			}
			MenuManager manager = new MenuManager(each.getName(), SharedImages.getImageDescriptor(SharedImages.PRESET), each.getID());
			manager.add(new PresetsContribution(each));
			result.add(manager);
		}

		return result.toArray(new IContributionItem[result.size()]);
	}
}
