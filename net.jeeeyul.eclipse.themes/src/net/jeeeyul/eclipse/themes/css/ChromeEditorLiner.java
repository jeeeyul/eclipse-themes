package net.jeeeyul.eclipse.themes.css;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

public class ChromeEditorLiner {
	public static ChromeEditorLiner get(StyledText client) {
		ChromeEditorLiner liner = (ChromeEditorLiner) client.getData(ChromeEditorLiner.class.getCanonicalName());
		if (liner == null) {
			liner = new ChromeEditorLiner(client);
		}
		return liner;
	}

	private StyledText client;

	/**
	 * @see SWT#NONE
	 * @see SWT#LINE_SOLID
	 * @see SWT#LINE_DASH
	 * @see SWT#LINE_DASHDOT
	 * @see SWT#LINE_DASHDOTDOT
	 * @see SWT#LINE_DOT
	 */
	private int lineStyle;
	private Color color;

	private UIJob refreshJob;

	private Image backgroundImage;

	private Listener listener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			doHandle(event);
		}
	};

	private ChromeEditorLiner(StyledText client) {
		this.client = client;
		client.setData(ChromeEditorLiner.class.getCanonicalName(), this);
		hook();
	}

	private Image createNewBackgroundImage() {
		if (lineStyle == SWT.NONE) {
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
		gc.setLineDash(new int[] { 2, 2 });
		gc.setLineStyle(lineStyle);
		gc.drawLine(0, offset, width, offset);
		gc.dispose();
		gc.dispose();

		return image;

	}

	public void dispose() {
		if (client != null && !client.isDisposed()) {
			client.setBackgroundImage(null);
			client.setData(ChromeEditorLiner.class.getCanonicalName(), null);
			unhook();
		}

		if (backgroundImage != null && !backgroundImage.isDisposed()) {
			backgroundImage.dispose();
		}
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

	public int getLineStyle() {
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

	public void setLineStyle(int lineStyle) {
		if (this.lineStyle == lineStyle) {
			return;
		}
		this.lineStyle = lineStyle;
		invalidate();
	}

	public void setLineStyle(String lineStyleString) {
		if (lineStyleString.equalsIgnoreCase("solid")) {
			setLineStyle(SWT.LINE_SOLID);
		}

		else if (lineStyleString.equalsIgnoreCase("dash")) {
			setLineStyle(SWT.LINE_DASH);
		}

		else if (lineStyleString.equalsIgnoreCase("dash-dot")) {
			setLineStyle(SWT.LINE_DASHDOT);
		}

		else if (lineStyleString.equalsIgnoreCase("dash-dot-dot")) {
			setLineStyle(SWT.LINE_DASHDOTDOT);
		}

		else if (lineStyleString.equalsIgnoreCase("dot")) {
			setLineStyle(SWT.LINE_DOT);
		}

		else {
			setLineStyle(SWT.NONE);
		}
	}

	private void unhook() {
		client.removeListener(SWT.Dispose, listener);
		client.getVerticalBar().removeListener(SWT.Selection, listener);
	}
}
