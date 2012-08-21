package net.jeeeyul.eclipse.themes.css;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ChromeEditorLiner {
	private StyledText text;
	private Image lineImage;
	private boolean dash;

	private Color lineColor;
	private Listener invalidator;
	private Listener disposer;
	private boolean visible;

	public static final HashSet<ChromeEditorLiner> instances = new HashSet<ChromeEditorLiner>();

	public static void disposeAll() {
		for (ChromeEditorLiner each : instances.toArray(new ChromeEditorLiner[instances.size()])) {
			each.dispose();
		}
	}

	public ChromeEditorLiner(StyledText text) {
		this.text = text;
		disposer = new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleDispose();
			}
		};
		text.addListener(SWT.Dispose, disposer);

		invalidator = new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleVerticalScroll();
			}
		};
		text.getVerticalBar().addListener(SWT.Selection, invalidator);
		instances.add(this);
		update();
	}

	public void dispose() {
		if (text != null || !text.isDisposed()) {
			text.removeListener(SWT.Dispose, disposer);
			text.getVerticalBar().removeListener(SWT.Selection, invalidator);
			text.setBackgroundImage(null);
			text.setData("chrome-liner", null);
		}
		disposeImage();
		instances.remove(this);
	}

	private void disposeImage() {
		if (lineImage != null && !lineImage.isDisposed()) {
			lineImage.dispose();
			lineImage = null;
		}
	}

	public Color getLineColor() {
		return lineColor;
	}

	private Image computeNewImage() {
		int offset = text.getLineHeight() - (text.getVerticalBar().getSelection() % text.getLineHeight());
		Image result = new Image(text.getDisplay(), 20, text.getLineHeight());

		GC gc = new GC(result);

		if (lineColor != null) {
			gc.setForeground(lineColor);
		} else {
			gc.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}

		if (dash) {
			gc.setLineStyle(SWT.LINE_DASH);
			gc.setLineDash(new int[] { 2, 2 });
		}

		gc.drawLine(0, offset - 1, 20, offset - 1);
		gc.dispose();

		return result;
	}

	protected void handleDispose() {
		dispose();
	}

	protected void handleVerticalScroll() {
		if (!visible) {
			return;
		}

		update();
	}

	public boolean isDash() {
		return dash;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setDash(boolean dash) {
		this.dash = dash;
		update();
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		update();
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		update();
	}

	private void update() {
		Image oldImage = lineImage;

		if (visible) {
			Image newImage = computeNewImage();
			text.setBackgroundImage(newImage);
			lineImage = newImage;
		} else {
			text.setBackgroundImage(null);
		}

		if (oldImage != null && !oldImage.isDisposed()) {
			oldImage.dispose();
		}
	}
}
