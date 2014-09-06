package net.jeeeyul.eclipse.themes.css;

import java.util.HashSet;

import net.jeeeyul.eclipse.themes.css.internal.EditBoxTracker;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * Renders text underlines for {@link StyledText}. Client can customize line
 * color by {@link #setLineColor(HSB)} and customize line style through
 * {@link #setLineStyle(int)}.
 * 
 * @since 2.1
 * @author Jeeeyul
 */
public class EditorLineSupport {
	private static final SWTExtensions swtToolkit = SWTExtensions.INSTANCE;
	private static ILock GLOBAL_LOCK = Job.getJobManager().newLock();
	private static HashSet<EditorLineSupport> INSTANCES = new HashSet<EditorLineSupport>();

	/**
	 * Dispose all {@link EditorLineSupport} instances.
	 */
	public static void disposeAll() {
		GLOBAL_LOCK.acquire();
		EditorLineSupport[] array = INSTANCES.toArray(new EditorLineSupport[INSTANCES.size()]);
		for (EditorLineSupport each : array) {
			each.dispose();
		}
		INSTANCES.clear();
		GLOBAL_LOCK.release();
	}

	/**
	 * Creates a new {@link EditorLineSupport}.
	 * 
	 * @param client
	 *            A {@link StyledText} widget to draw lines.
	 * @return {@link EditorLineSupport} instance for given {@link StyledText}
	 *         widget.
	 */
	public static EditorLineSupport get(StyledText client) {
		GLOBAL_LOCK.acquire();
		EditorLineSupport liner = (EditorLineSupport) client.getData(EditorLineSupport.class.getCanonicalName());
		if (liner == null) {
			liner = new EditorLineSupport(client);
		}
		GLOBAL_LOCK.release();
		return liner;
	}

	static {
		/*
		 * Refreshes all EditorLiningSupport when EditBox was deactivated.
		 */
		EditBoxTracker.INSTANCE.setEditBoxEnablementHandler(new Procedure1<Boolean>() {
			@Override
			public void apply(Boolean active) {
				if (active == false) {
					for (EditorLineSupport each : INSTANCES) {
						each.getRefreshJob().schedule();
					}
				}
			}
		});
	}

	private StyledText client;
	private HSB lineColor = HSB.BLACK;
	private int lineStyle = SWT.NONE;
	private Image backgroundImage;
	private UIJob refreshJob;
	private boolean isDisposed = false;

	private Listener listener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			if (!isDisposed) {
				doHandle(event);
			}
		}
	};

	private EditorLineSupport(StyledText client) {
		this.client = client;
		INSTANCES.add(this);
		client.setData(EditorLineSupport.class.getCanonicalName(), this);
		hook();
	}

	private Image createNewBackgroundImage() {
		int width = 24;
		int height = client.getLineHeight();

		int offset = height - 1;
		if (client.getVerticalBar() != null) {
			offset = height - (client.getVerticalBar().getSelection() % height) - 1;
		}

		Image image = new Image(client.getDisplay(), width, height);
		GC gc = new GC(image);
		gc.setBackground(client.getBackground());
		gc.fillRectangle(0, 0, width, height);
		gc.setForeground(SWTExtensions.INSTANCE.toAutoDisposeColor(lineColor));
		gc.setAntialias(SWT.OFF);
		switch (lineStyle) {
		case SWT.LINE_DASH:
			gc.setLineStyle(SWT.LINE_DASH);
			gc.setLineDash(new int[] { 2, 1 });
			break;
		case SWT.LINE_DOT:
			gc.setLineStyle(SWT.LINE_DOT);
			gc.setLineDash(new int[] { 1, 2 });
			break;
		case SWT.LINE_SOLID:
			break;
		default:
			break;
		}

		gc.drawLine(0, offset, width, offset);
		gc.dispose();
		return image;
	}

	/**
	 * Dispose {@link EditorLineSupport} instance and stop to draw underlines
	 * for it's {@link StyledText}.
	 */
	public void dispose() {
		if (isDisposed) {
			return;
		}
		GLOBAL_LOCK.acquire();
		if (client != null && !client.isDisposed()) {
			client.setBackgroundImage(null);
			client.setData(EditorLineSupport.class.getCanonicalName(), null);
			unhook();
		}

		swtToolkit.safeDispose(backgroundImage);
		INSTANCES.remove(this);
		isDisposed = true;
		GLOBAL_LOCK.release();
	}

	private void doHandle(Event event) {
		switch (event.type) {
		case SWT.Dispose:
			dispose();
			break;

		case SWT.Selection:
			invalidate();
			break;
		}
	}

	private void doRefresh() {
		if (client.isDisposed()) {
			return;
		}
		swtToolkit.safeDispose(backgroundImage);

		// What if EditBox is activated, Don't refresh.
		if (!EditBoxTracker.INSTANCE.isEditBoxActive()) {
			if (lineStyle == SWT.NONE) {
				client.setBackgroundImage(null);
				dispose();
			} else {
				backgroundImage = createNewBackgroundImage();
				client.setBackgroundImage(backgroundImage);
			}
		}
	}

	/**
	 * 
	 * @return Color of underline.
	 */
	public HSB getLineColor() {
		return lineColor;
	}

	/**
	 * 
	 * @return line style flag.
	 * 
	 * @see SWT#LINE_DASH
	 * @see SWT#LINE_SOLID
	 * @see SWT#LINE_DOT
	 * @see SWT#NONE
	 */
	public int getLineStyle() {
		return lineStyle;
	}

	private UIJob getRefreshJob() {
		if (refreshJob == null) {
			refreshJob = new UIJob(Display.getDefault(), "Refersh Editor Line") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					doRefresh();
					return Status.OK_STATUS;
				}
			};
			refreshJob.setUser(false);
			refreshJob.setSystem(true);
		}
		return refreshJob;
	}

	private void hook() {
		client.addListener(SWT.Dispose, listener);
		if (client.getVerticalBar() != null)
			client.getVerticalBar().addListener(SWT.Selection, listener);
	}

	private void invalidate() {
		getRefreshJob().schedule();
	}

	/**
	 * Sets color of underline.
	 * 
	 * @param lineColor
	 *            color for under line.
	 */
	public void setLineColor(HSB lineColor) {
		if (this.lineColor == lineColor) {
			return;
		} else if (this.lineColor != null) {
			if (this.lineColor.equals(lineColor)) {
				return;
			}
		}
		this.lineColor = lineColor;
		invalidate();
	}

	/**
	 * 
	 * @param lineStyle
	 * 
	 * @see SWT#LINE_DASH
	 * @see SWT#LINE_SOLID
	 * @see SWT#LINE_DOT
	 * @see SWT#NONE
	 */
	public void setLineStyle(int lineStyle) {
		if (this.lineStyle == lineStyle) {
			return;
		}
		this.lineStyle = lineStyle;
		invalidate();
	}

	private void unhook() {
		client.removeListener(SWT.Dispose, listener);
		if (client.getVerticalBar() != null)
			client.getVerticalBar().removeListener(SWT.Selection, listener);
	}
}
