package net.jeeeyul.eclipse.themes.css;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public class ChromeEditorLiner {
	public static final HashSet<ChromeEditorLiner> instances = new HashSet<ChromeEditorLiner>();

	private StyledText text;
	private Image lineImage;
	private LineProperty imageInfo = new LineProperty();

	private boolean dash;

	private Color lineColor;
	private Listener invalidator;
	private Listener disposer;
	private boolean visible;

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
		ScrollBar verticalBar = text.getVerticalBar();
		if (verticalBar != null) {
			verticalBar.addListener(SWT.Selection, invalidator);
		}
		instances.add(this);
		update();
	}

	private int computeLineOffset() {
		int offset = text.getLineHeight() - (text.getVerticalBar().getSelection() % text.getLineHeight());
		return offset;
	}

	private Image computeNewImage() {
		int lineOffset = computeLineOffset();

		imageInfo.setOffset(lineOffset);
		imageInfo.setColor(lineColor);
		imageInfo.setUseDash(dash);

		Image result = new Image(text.getDisplay(), 20, text.getLineHeight());

		GC gc = new GC(result);
		gc.setBackground(text.getBackground());
		gc.fillRectangle(0, 0, 20, text.getLineHeight());

		if (lineColor != null) {
			gc.setForeground(lineColor);
		} else {
			gc.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}

		if (dash) {
			gc.setLineStyle(SWT.LINE_DASH);
			gc.setLineDash(new int[] { 2, 2 });
		}

		gc.drawLine(0, lineOffset - 1, 20, lineOffset - 1);
		gc.dispose();

		return result;
	}

	public void dispose() {
		if (text != null || !text.isDisposed()) {
			text.removeListener(SWT.Dispose, disposer);
			if (text != null && text.getVerticalBar() != null)
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

	protected void handleDispose() {
		dispose();
	}

	protected void handleVerticalScroll() {
		if (!visible) {
			return;
		}

		update();
	}

	private boolean hasToCreateNewImage() {
		if (!visible) {
			return false;
		}
		int offset = computeLineOffset();
		return imageInfo.isUseDash() != dash || imageInfo.getOffset() != offset || imageInfo.getColor() != lineColor || lineImage == null
				|| lineImage.isDisposed();
	}

	public boolean isDash() {
		return dash;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setDash(boolean dash) {
		if (this.dash == dash) {
			return;
		}

		this.dash = dash;
		update();
	}

	public void setLineColor(Color lineColor) {
		if (this.lineColor == lineColor) {
			return;
		}
		this.lineColor = lineColor;
		update();
	}

	public void setVisible(boolean visible) {
		if (this.visible == visible) {
			return;
		}
		this.visible = visible;
		update();
	}

	private void update() {
		Image oldImage = lineImage;

		if (visible) {
			if (hasToCreateNewImage()) {
				Image newImage = computeNewImage();
				text.setBackgroundImage(newImage);
				lineImage = newImage;
				if (oldImage != null && !oldImage.isDisposed()) {
					oldImage.dispose();
				}
			}
		} else {
			text.setBackgroundImage(null);
			if (oldImage != null && !oldImage.isDisposed()) {
				oldImage.dispose();
			}
		}
	}
}
