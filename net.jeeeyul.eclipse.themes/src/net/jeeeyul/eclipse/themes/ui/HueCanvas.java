package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class HueCanvas extends Canvas {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		HueCanvas canvas = new HueCanvas(shell, SWT.NORMAL);
		canvas.setHue(160f);
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Image hueImage;

	private float hue;

	private int padding = 5;

	public HueCanvas(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleDispose();
			}
		});
		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onPaint(event);
			}
		});
		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				invalidate();
			}
		});

		setCursor(getDisplay().getSystemCursor(SWT.CURSOR_CROSS));
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(200, 200);
	}

	public float getHue() {
		return hue;
	}

	public Image getHueImage() {
		if (hueImage == null || hueImage.isDisposed()) {
			Rectangle clientArea = getClientArea();

			PaletteData palette = new PaletteData(0xff0000, 0xff00, 0xff);
			ImageData imageData = new ImageData(clientArea.width, clientArea.height, 32, palette);

			for (int x = 0; x < imageData.width; x++) {
				float sat = x / (imageData.width - 1f);

				for (int y = 0; y < imageData.height; y++) {
					float bri = 1f - y / (imageData.height - 1f);

					imageData.setPixel(x, y, palette.getPixel(new RGB(hue, sat, bri)));
				}
			}
			hueImage = new Image(getDisplay(), imageData);
		}
		return hueImage;
	}

	protected void handleDispose() {
		if (hueImage != null && !hueImage.isDisposed()) {
			hueImage.dispose();
		}
	}

	protected void invalidate() {
		if (hueImage != null && !hueImage.isDisposed()) {
			hueImage.dispose();
		}

		redraw();
	}

	protected void onPaint(Event event) {
		Rectangle clientArea = getClientArea();
		event.gc.drawImage(getHueImage(), clientArea.x, clientArea.y);
	}

	@Override
	public Rectangle getClientArea() {
		Rectangle clientArea = super.getClientArea();
		clientArea.x += padding;
		clientArea.y += padding;
		clientArea.width -= padding * 2;
		clientArea.height -= padding * 2;
		return clientArea;
	}

	public void setHue(float hue) {
		this.hue = hue;
		invalidate();
	}
}
