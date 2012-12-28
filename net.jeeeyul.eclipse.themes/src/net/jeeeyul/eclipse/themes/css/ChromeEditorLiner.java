package net.jeeeyul.eclipse.themes.css;

import java.util.HashSet;

import net.jeeeyul.eclipse.themes.ui.LineStyle;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

public class ChromeEditorLiner {
	private static ILock GLOBAL_LOCK = Job.getJobManager().newLock();

	private static HashSet<ChromeEditorLiner> INSTANCES = new HashSet<ChromeEditorLiner>();

	public static void disposeAll() {
		GLOBAL_LOCK.acquire();
		ChromeEditorLiner[] array = INSTANCES.toArray(new ChromeEditorLiner[INSTANCES.size()]);
		for (ChromeEditorLiner each : array) {
			each.dispose();
		}
		INSTANCES.clear();
		GLOBAL_LOCK.release();
	}

	public static ChromeEditorLiner get(StyledText client) {
		GLOBAL_LOCK.acquire();
		ChromeEditorLiner liner = (ChromeEditorLiner) client.getData(ChromeEditorLiner.class.getCanonicalName());
		if (liner == null) {
			liner = new ChromeEditorLiner(client);
		}
		GLOBAL_LOCK.release();
		return liner;
	}

	private StyledText client;
	private LineStyle lineStyle;
	private Color color;
	private UIJob refreshJob;
	private Image backgroundImage;
	private boolean isDisposed = false;

	private Listener listener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			if (!isDisposed) {
				doHandle(event);
			}
		}
	};

	private ChromeEditorLiner(StyledText client) {
		this.client = client;
		INSTANCES.add(this);
		client.setData(ChromeEditorLiner.class.getCanonicalName(), this);
		hook();
	}

	private Image createNewBackgroundImage() {
		if (lineStyle == LineStyle.NONE || isDisposed) {
			return null;
		}

		int width = 36;
		int height = client.getLineHeight();
		int offset = height - (client.getVerticalBar().getSelection() % height) - 1;
		Image image = new Image(client.getDisplay(), width, height);
		GC gc = new GC(image);
		gc.setBackground(client.getBackground());
		gc.fillRectangle(0, 0, width, height);
		gc.setForeground(color);

		switch (lineStyle) {
		case DASH:
			gc.setLineDash(new int[] { 2, 2 });
			break;
		case SOLID:
			break;
		case NONE:
			break;
		default:
			break;
		}

		gc.drawLine(0, offset, width, offset);
		gc.dispose();
		gc.dispose();

		return image;

	}

	public void dispose() {
		if (isDisposed) {
			return;
		}
		GLOBAL_LOCK.acquire();
		if (client != null && !client.isDisposed()) {
			client.setBackgroundImage(null);
			client.setData(ChromeEditorLiner.class.getCanonicalName(), null);
			unhook();
		}

		if (backgroundImage != null && !backgroundImage.isDisposed()) {
			backgroundImage.dispose();
		}

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
		if (backgroundImage != null && !backgroundImage.isDisposed()) {
			backgroundImage.dispose();
		}
		backgroundImage = createNewBackgroundImage();
		client.setBackgroundImage(backgroundImage);
	}

	public Color getColor() {
		return color;
	}

	public LineStyle getLineStyle() {
		return lineStyle;
	}

	private UIJob getRefreshJob() {
		if (refreshJob == null) {
			refreshJob = new UIJob(client.getDisplay(), "refresh") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (client != null && !client.isDisposed()) {
						doRefresh();
					}
					return Status.OK_STATUS;
				}
			};
			refreshJob.setSystem(true);
		}
		return refreshJob;
	}

	private void hook() {
		client.addListener(SWT.Dispose, listener);
		client.getVerticalBar().addListener(SWT.Selection, listener);
	}

	private void invalidate() {
		getRefreshJob().schedule();
	}

	public void setColor(Color color) {
		if (this.color == color) {
			return;
		}
		this.color = color;
		invalidate();
	}

	public void setLineStyle(LineStyle lineStyle) {
		if (this.lineStyle == lineStyle) {
			return;
		}
		this.lineStyle = lineStyle;
		invalidate();
	}

	public void setLineStyle(String lineStyleString) {
		setLineStyle(LineStyle.getByLiteral(lineStyleString));
	}

	private void unhook() {
		client.removeListener(SWT.Dispose, listener);
		client.getVerticalBar().removeListener(SWT.Selection, listener);
	}
}
