package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
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
	private static final int STATE_NONE = 0;

	private static final int STATE_DRAGGING = 1;

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
	private float saturation = 0.5f;
	private float brightness = 0.5f;
	private int padding = 5;

	private int state = STATE_NONE;

	private int selectionRadius = 4;

	private Runnable notifySelection = new Runnable() {
		@Override
		public void run() {
			doNotifySelection();
		}
	};

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

		addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseDown(event);
			}
		});

		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseMove(event);
			}
		});

		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseUp(event);
			}
		});

		setCursor(getDisplay().getSystemCursor(SWT.CURSOR_CROSS));
	}

	private void computeNewSelection(Event event) {
		Rectangle clientArea = getClientArea();
		float newSaturation = limit((event.x - clientArea.x) / (float) clientArea.width, 0f, 1f);
		float newBrightness = limit(1f - (event.y - clientArea.y) / (float) clientArea.height, 0f, 1f);
		setSaturation(newSaturation);
		setBrightness(newBrightness);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(200, 200);
	}

	private void doNotifySelection() {
		Event e = new Event();
		e.widget = this;
		notifyListeners(SWT.Selection, e);
		setData("notify-selection", null);
	}

	public float getBrightness() {
		return brightness;
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

	public int getPadding() {
		return padding;
	}

	public float getSaturation() {
		return saturation;
	}

	/**
	 * 
	 * @return HSB selection.
	 * @since 1.2
	 */
	public HSB getSelection() {
		return new HSB(hue, saturation, brightness);
	}

	private void handleDispose() {
		if (hueImage != null && !hueImage.isDisposed()) {
			hueImage.dispose();
		}
	}

	private void invalidate() {
		if (hueImage != null && !hueImage.isDisposed()) {
			hueImage.dispose();
		}
		redraw();
	}

	private float limit(float value, float min, float max) {
		return Math.max(Math.min(value, max), min);
	}

	private void onMouseDown(Event event) {
		computeNewSelection(event);

		if (event.button == 1)
			state = STATE_DRAGGING;
	}

	private void onMouseMove(Event event) {
		if (state == STATE_DRAGGING) {
			computeNewSelection(event);
		}
	}

	private void onMouseUp(Event event) {
		if (event.button == 1) {
			state = STATE_NONE;
		}
	}

	private void onPaint(Event event) {
		Rectangle clientArea = getClientArea();
		GC gc = event.gc;

		gc.drawRectangle(clientArea.x - 1, clientArea.y - 1, clientArea.width + 1, clientArea.height + 1);

		gc.drawImage(getHueImage(), clientArea.x, clientArea.y);

		int selectionX = (int) (clientArea.width * saturation + clientArea.x);
		int selectionY = (int) (clientArea.height * (1f - brightness) + clientArea.y);

		RGB rgb = new RGB(hue, saturation, brightness);
		Color selectionColor = new Color(getDisplay(), rgb);

		gc.setAntialias(SWT.ON);
		gc.setBackground(selectionColor);

		gc.fillOval(selectionX - selectionRadius, selectionY - selectionRadius, selectionRadius * 2 + 1, selectionRadius * 2 + 1);

		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gc.drawOval(selectionX - selectionRadius, selectionY - selectionRadius, selectionRadius * 2, selectionRadius * 2);

		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		gc.drawOval(selectionX - selectionRadius - 1, selectionY - selectionRadius - 1, selectionRadius * 2 + 2, selectionRadius * 2 + 2);

		selectionColor.dispose();

	}

	private void scheduleNotifySelection() {
		if (getData("notify-selection") != null) {
			return;
		}

		setData("notify-selection", true);
		getDisplay().asyncExec(notifySelection);
	}

	public void setBrightness(float brightness) {
		this.brightness = brightness;
		invalidate();
		scheduleNotifySelection();
	}

	public void setHue(float hue) {
		this.hue = hue;
		invalidate();
		scheduleNotifySelection();
	}

	public void setPadding(int padding) {
		this.padding = padding;
		invalidate();
	}

	public void setSaturation(float saturation) {
		this.saturation = saturation;
		invalidate();
		scheduleNotifySelection();
	}

	public void setSelection(float[] selection) {
		setSelection(selection, true);
	}

	public void setSelection(float[] selection, boolean notify) {

		this.hue = selection[0];
		this.saturation = selection[1];
		this.brightness = selection[2];
		invalidate();
		if (notify)
			scheduleNotifySelection();
	}
}
