package net.jeeeyul.eclipse.themes.preference.action;

import net.jeeeyul.eclipse.themes.preference.ChromePage;
import net.jeeeyul.eclipse.themes.preference.ChromePreferenceInitializer;
import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;
import net.jeeeyul.eclipse.themes.preference.Messages;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferenceStore;

public class GlobalAdjustmentAction extends PreferenceAction {
	public GlobalAdjustmentAction(ChromeThemePrefererncePage prefererncePage) {
		super(prefererncePage);
		setText(Messages.ADJUST_COLORS_GLOBALLY);
	}

	@Override
	public void run() {
		PreferenceStore originalStore = createBackup();

		int result = new GlobalAdjustmentDialog(getPrefererncePage()).open();

		if (result != IDialogConstants.OK_ID) {
			for (ChromePage each : getPrefererncePage().getPages()) {
				each.load(originalStore);
			}
		}
	}

	private PreferenceStore createBackup() {
		PreferenceStore originalStore = new PreferenceStore();
		new ChromePreferenceInitializer().initializeDefaultPreferences(originalStore);
		for (ChromePage each : getPrefererncePage().getPages()) {
			each.save(originalStore);
		}
		return originalStore;
	}
}
