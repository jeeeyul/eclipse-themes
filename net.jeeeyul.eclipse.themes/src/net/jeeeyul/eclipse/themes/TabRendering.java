package net.jeeeyul.eclipse.themes;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.css.swt.CSSSWTConstants;
import org.eclipse.e4.ui.workbench.renderers.swt.CTabRendering;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

@SuppressWarnings("restriction")
public class TabRendering extends CTabRendering {
	private CTabFolder folder;
	private UpdateClassJob updateClassJob = new UpdateClassJob();

	public TabRendering(final CTabFolder folder) {
		super(folder);
		this.folder = folder;

		folder.addListener(SWT.Skin, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("½ºÅ²");
			}
		});
		updateClassJob.schedule();
	}

	@Override
	protected void draw(int part, int state, Rectangle bounds, GC gc) {
		String classNames = (String) folder
				.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY);

		CSSClasses cc = new CSSClasses(classNames);
		if (folder.getItemCount() == 0) {
			if (cc.add("empty")) {
				folder.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY,
						cc.toString());
				folder.reskin(SWT.ALL);
			}
		} else {
			if (cc.remove("empty")) {
				folder.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY,
						cc.toString());
				folder.reskin(SWT.ALL);
			}
		}

		super.draw(part, state, bounds, gc);
	}

	private class UpdateClassJob extends UIJob {
		public UpdateClassJob() {
			super("update-class");
			setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			System.out.println(folder
					.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY));
			return Status.OK_STATUS;
		}

		@Override
		public boolean shouldRun() {
			return folder != null && !folder.isDisposed();
		}
	}
}
