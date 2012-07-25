package net.jeeeyul.eclipse.themes.preference;

import java.util.Set;

import net.jeeeyul.eclipse.themes.decorator.ChromeTabRendering;
import net.jeeeyul.eclipse.themes.e4.WidgetTracker;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;

/**
 * 
 * @author Jeeeyul
 * @since 1.2
 */
public class ApplyChromeThemePreferenceJob extends UIJob {

	public ApplyChromeThemePreferenceJob() {
		super("Update Chrome Renderings");
		setSystem(true);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		// gets alive chrome tab renderings.
		Set<ChromeTabRendering> instances = ChromeTabRendering.getInstances();
		ChromeTabRendering[] renderings = new ChromeTabRendering[instances.size()];
		renderings = instances.toArray(renderings);

		for (ChromeTabRendering each : renderings) {
			try {
				// apply chrome theme preferences to each tab folder.
				each.applyChromeThemePreference();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// update sash settings to all sash containers
		WidgetTracker.getInstance().applyChromeThemeToAllTopWindows();

		return Status.OK_STATUS;
	}
}
