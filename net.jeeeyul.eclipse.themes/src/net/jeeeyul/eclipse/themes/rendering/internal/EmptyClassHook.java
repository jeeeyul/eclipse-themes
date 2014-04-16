package net.jeeeyul.eclipse.themes.rendering.internal;

import net.jeeeyul.eclipse.themes.css.internal.CSSClasses;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

/**
 * Tracks CTabFolder to add "empty" class when it has no items.
 *
 * @author Jeeeyul
 * @since 2.0.0
 */
@SuppressWarnings({ "restriction", "javadoc" })
public class EmptyClassHook {
	private CTabFolder folder;
	private boolean isDisposed = false;

	private Listener selectionHook = new Listener() {
		@Override
		public void handleEvent(Event event) {
			getComputeJob().schedule();
		}
	};

	private UIJob computeJob;

	public EmptyClassHook(CTabFolder folder) {
		this.folder = folder;

		folder.addListener(SWT.Paint, selectionHook);
		getComputeJob().schedule();
	}

	private void computeEmptyClass() {
		if (folder == null || folder.isDisposed()) {
			return;
		}

		IThemeEngine engine = getThemeEngine();
		if (engine == null) {
			return;
		}

		CSSClasses styleClasses = CSSClasses.getStyleClasses(folder);
		boolean isEmpty = folder.getItemCount() == 0;
		if (isEmpty) {
			if (!styleClasses.contains("empty")) {
				styleClasses.add("empty");
				CSSClasses.setStyleClasses(folder, styleClasses);
				engine.applyStyles(folder, true);
			}
		} else {
			if (styleClasses.contains("empty")) {
				styleClasses.remove("empty");
				CSSClasses.setStyleClasses(folder, styleClasses);
				engine.applyStyles(folder, true);
			}
		}
	}

	public void dispose() {
		if (isDisposed) {
			return;
		}
		if (folder != null && !folder.isDisposed()) {
			folder.removeListener(SWT.Paint, selectionHook);
		}

		isDisposed = true;
	}

	private UIJob getComputeJob() {
		if (computeJob == null) {
			computeJob = new UIJob(Display.getDefault(), "Update Empty Class") {
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (!isDisposed) {
						computeEmptyClass();
					}
					return Status.OK_STATUS;
				};
			};
			computeJob.setUser(false);
			computeJob.setSystem(true);
		}
		return computeJob;
	}

	private IThemeEngine getThemeEngine() {
		return (IThemeEngine) folder.getDisplay().getData("org.eclipse.e4.ui.css.swt.theme");
	}

}
