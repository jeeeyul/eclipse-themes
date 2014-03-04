package net.jeeeyul.eclipse.themes.rendering.internal;

import net.jeeeyul.eclipse.themes.css.internal.CSSClasses;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

@SuppressWarnings("restriction")
public class EmptyClassHook {

	private CTabFolder folder;
	private boolean isDisposed = false;

	private CTabFolder2Listener adapter = new CTabFolder2Adapter() {
		public void close(CTabFolderEvent event) {
			compute.schedule();
		};
	};

	private Listener selectionHook = new Listener() {
		@Override
		public void handleEvent(Event event) {
			compute.schedule();
		}
	};

	public EmptyClassHook(CTabFolder folder) {
		this.folder = folder;
		folder.addCTabFolder2Listener(adapter);
		folder.addListener(SWT.Selection, selectionHook);
		compute.schedule();
	}

	private UIJob compute = new UIJob(Display.getDefault(), "Update Empty Class") {
		public IStatus runInUIThread(IProgressMonitor monitor) {
			computeEmptyClass();
			return Status.OK_STATUS;
		};
	};

	private void computeEmptyClass() {
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

	private IThemeEngine getThemeEngine() {
		return (IThemeEngine) folder.getDisplay().getData("org.eclipse.e4.ui.css.swt.theme");
	}

	public void dispose() {
		if (isDisposed) {
			return;
		}
		if (folder != null && !folder.isDisposed()) {
			folder.removeCTabFolder2Listener(adapter);
			folder.removeListener(SWT.Selection, selectionHook);
		}

		isDisposed = true;
	}

}
