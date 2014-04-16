package net.jeeeyul.eclipse.themes.internal.linux;

import net.jeeeyul.eclipse.themes.internal.OSHelper;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Populates menu items for linux users.
 * 
 * @author Jeeeyul
 */
public class LinuxContribution extends CompoundContributionItem {
	@Override
	protected IContributionItem[] getContributionItems() {
		if (OSHelper.INSTANCE.isLinux() == false || SWTExtensions.INSTANCE.getMinimumToolBarHeight() < 30) {
			return new IContributionItem[0];
		}

		return new IContributionItem[] { new ActionContributionItem(new LaunchGTKWizardAction()) };
	}
}
