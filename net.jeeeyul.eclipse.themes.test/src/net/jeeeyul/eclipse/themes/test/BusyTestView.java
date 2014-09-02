package net.jeeeyul.eclipse.themes.test;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;

public class BusyTestView extends ViewPart {

	public BusyTestView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NORMAL);
		composite.setLayout(new GridLayout());

		Button testButton = new Button(composite, SWT.PUSH);
		testButton.setText("Do some job");
		testButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				doTest();
			}
		});
	}

	private void doTest() {
		IWorkbenchSiteProgressService service = (IWorkbenchSiteProgressService) getSite()
				.getService(IWorkbenchSiteProgressService.class);
		service.schedule(new Job("Test Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Test", 100);
				for (int i = 0; i < 100; i++) {
					try {
						Thread.sleep(100);
						monitor.worked(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		});
	}

	@Override
	public void setFocus() {
	}

}
