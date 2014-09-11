package net.jeeeyul.eclipse.themes.ui.linux;

import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper;

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
		if (ENVHelper.INSTANCE.isLinux() == false) {
			return new IContributionItem[0];
		}

		return new IContributionItem[] { new ActionContributionItem(new LaunchGTKWizardAction()) };
	}
}
