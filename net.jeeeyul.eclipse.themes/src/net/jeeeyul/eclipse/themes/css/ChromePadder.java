package net.jeeeyul.eclipse.themes.css;

import java.util.HashSet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.workbench.renderers.swt.TrimBarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

@SuppressWarnings("restriction")
public class ChromePadder {
	private static HashSet<ChromePadder> INSTANCES = new HashSet<ChromePadder>();

	public static void disposeAll() {
		ChromePadder[] array = INSTANCES.toArray(new ChromePadder[INSTANCES.size()]);
		for (ChromePadder each : array) {
			each.dispose();
		}
	}

	public static ChromePadder get(Composite control) {
		ChromePadder padder = (ChromePadder) control.getData(ChromePadder.class.getName());
		if (padder == null) {
			padder = new ChromePadder(control);
		}
		return padder;
	}

	private UIJob updateJob;
	private Composite parent;
	private Listener controlHook;
	private int left;

	private int top;
	private int right;
	private int bottom;

	public ChromePadder(Composite parent) {
		Assert.isNotNull(parent);
		Assert.isLegal(!parent.isDisposed());
		this.parent = parent;

		controlHook = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ChromePadder.this.handleEvent(event);
			}
		};

		parent.addListener(SWT.Paint, controlHook);
		parent.addListener(SWT.Dispose, controlHook);
		parent.setData(ChromePadder.class.getName(), this);
		INSTANCES.add(this);
	}

	public void dispose() {
		if (!parent.isDisposed()) {
			parent.removeListener(SWT.Dispose, controlHook);
			parent.setData(ChromePadder.class.getName(), null);
		}
		INSTANCES.remove(this);
	}

	private void doUpdate() {
		Layout layout = parent.getLayout();

		if (layout instanceof GridLayout) {
			GridLayout gridLayout = (GridLayout) layout;
			gridLayout.marginTop = top;
			gridLayout.marginBottom = bottom;
			gridLayout.marginLeft = left;
			gridLayout.marginRight = right;
			parent.layout(true);
		}

		else if (layout instanceof TrimBarLayout) {
			TrimBarLayout tbLayout = (TrimBarLayout) layout;
			tbLayout.marginLeft = left;
			tbLayout.marginRight = right;
			tbLayout.marginTop = top;
			tbLayout.marginBottom = bottom;
			parent.layout(true);
		}
	}

	public int getBottom() {
		return bottom;
	}

	public int getLeft() {
		return left;
	}

	public Composite getParent() {
		return parent;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public UIJob getUpdateJob() {
		if (updateJob != null) {
			return updateJob;
		}

		updateJob = new UIJob("Update Padding") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (parent == null || parent.isDisposed()) {
					return Status.OK_STATUS;
				}

				doUpdate();
				return Status.OK_STATUS;
			}
		};
		updateJob.setSystem(true);
		updateJob.setDisplay(parent.getDisplay());

		return updateJob;
	}

	protected void handleEvent(Event event) {
		switch (event.type) {

		case SWT.Dispose:
			dispose();
			break;
		}
	}

	public void setBottom(int bottom) {
		if (this.bottom == bottom) {
			return;
		}
		this.bottom = bottom;
		update();
	}

	public void setLeft(int left) {
		if (this.left == left) {
			return;
		}
		this.left = left;
		update();
	}

	public void setRight(int right) {
		if (this.right == right) {
			return;
		}
		this.right = right;
		update();
	}

	public void setTop(int top) {
		if (this.top == top) {
			return;
		}
		this.top = top;
		update();
	}

	private void update() {
		getUpdateJob().schedule();
	}
}
