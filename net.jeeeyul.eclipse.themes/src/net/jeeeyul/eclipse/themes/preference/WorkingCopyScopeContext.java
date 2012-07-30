package net.jeeeyul.eclipse.themes.preference;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.ui.preferences.IWorkingCopyManager;

public class WorkingCopyScopeContext implements IScopeContext {
	private final IWorkingCopyManager fWorkingCopyManager;
	private final IScopeContext fOriginal;

	public WorkingCopyScopeContext(IWorkingCopyManager workingCopyManager, IScopeContext original) {
		fWorkingCopyManager = workingCopyManager;
		fOriginal = original;
	}

	public IScopeContext getfOriginal() {
		return fOriginal;
	}

	public IWorkingCopyManager getfWorkingCopyManager() {
		return fWorkingCopyManager;
	}

	public IPath getLocation() {
		return fOriginal.getLocation();
	}

	public String getName() {
		return fOriginal.getName();
	}

	public IEclipsePreferences getNode(String qualifier) {
		return fWorkingCopyManager.getWorkingCopy(fOriginal.getNode(qualifier));
	}
}
