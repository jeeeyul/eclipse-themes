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

public class HueScale extends Canvas {

	private static final int STATE_NORMAL = 0;

	private static final int STATE_HOVER = 1;
	private static final int STATE_DRAGGING = 2;

	private static Rectangle expand(Rectangle original, int dx, int dy) {
		return new Rectangle(original.x - dx, original.y - dy, original.width + dx * 2, original.height + dy * 2);
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();

		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		HueScale hueScale = new HueScale(shell, SWT.NORMAL);
		hueScale.setSelection(180f);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Image hueImage;
	private float selection;

	private int state;
	private float saturation = 1f;
	private float brightness = 1f;

	public HueScale(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onPaint(event);
			}
		});
		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseMove(event);

			}
		});
		addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseDown(event);
			}
		});

		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseUp(event);
			}
		});
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(150, 30);
	}

	private void drawGradient(GC gc) {
		Rectangle gradientArea = getGradientArea();
		gc.drawImage(getHueImage(), 0, 0, 360, 1, gradientArea.x, gradientArea.y, gradientArea.width, gradientArea.height);
		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		gc.setAlpha(127);
		gc.drawLine(gradientArea.x, gradientArea.y, gradientArea.x + gradientArea.width, gradientArea.y);
		gc.drawLine(gradientArea.x, gradientArea.y, gradientArea.x, gradientArea.y + gradientArea.height);
		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gc.drawLine(gradientArea.x, gradientArea.y + gradientArea.height, gradientArea.x + gradientArea.width, gradientArea.y + gradientArea.height);
		gc.drawLine(gradientArea.x + gradientArea.width, gradientArea.y + gradientArea.height, gradientArea.x + gradientArea.width, gradientArea.y);
	}

	private void drawSelection(GC gc) {
		gc.setAlpha(255);
		Rectangle selectionBox = getSelctionArea();

		Rectangle outline = expand(selectionBox, 4, 4);
		if (isEnabled()) {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
		if (state == STATE_NORMAL) {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		} else {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
		}
		gc.fillRoundRectangle(outline.x, outline.y, outline.width, outline.height, 5, 5);
		gc.drawRoundRectangle(outline.x, outline.y, outline.width, outline.height, 5, 5);

		Color color = new Color(getDisplay(), new RGB(selection, saturation, brightness));
		gc.setBackground(color);
		gc.fillRectangle(selectionBox);
		gc.drawRectangle(selectionBox);

		color.dispose();
	}

	public float getBrightness() {
		return brightness;
	}

	private Rectangle getGradientArea() {
		int horizontalMargin = 10;
		int gradientHeight = 10;
		Rectangle clientArea = getClientArea();
		Rectangle gradientArea = getClientArea();
		gradientArea.height = gradientHeight;
		gradientArea.y = (clientArea.height - gradientArea.height) / 2;
		gradientArea.width -= horizontalMargin * 2;
		gradientArea.x = (clientArea.width - gradientArea.width) / 2;
		return gradientArea;
	}

	public Image getHueImage() {
		if (hueImage == null) {
			PaletteData palette = new PaletteData(0xff0000, 0xff00, 0xff);
			ImageData data = new ImageData(360, 1, 32, palette);
			for (int x = 0; x < 360; x++) {
				data.setPixel(x, 0, palette.getPixel(new RGB((float) x, saturation, brightness)));
			}
			hueImage = new Image(getDisplay(), data);
			addListener(SWT.Dispose, new Listener() {
				@Override
				public void handleEvent(Event event) {
					hueImage.dispose();
				}
			});
		}
		return hueImage;
	}

	public float getSaturation() {
		return saturation;
	}

	private Rectangle getSelctionArea() {
		Rectangle gradientArea = getGradientArea();
		int selectionOffset = (int) ((gradientArea.width / 360f) * selection + gradientArea.x);
		Rectangle selectionBox = new Rectangle(selectionOffset - 5, gradientArea.y - 5, 10, gradientArea.height + 10);
		return selectionBox;
	}

	public float getSelection() {
		return selection;
	}

	protected void onMouseDown(Event event) {
		switch (state) {
		case STATE_HOVER:
			setState(STATE_DRAGGING);
			break;

		default:
			int offset = event.x - getGradientArea().x;
			float newSelection = (offset / (float) getGradientArea().width) * 360f;
			newSelection = Math.min(Math.max(0, newSelection), 360f);
			setSelection(newSelection);
			setState(STATE_DRAGGING);
			break;
		}
	}

	protected void onMouseMove(Event event) {
		switch (state) {
		case STATE_NORMAL:
			if (getSelctionArea().contains(event.x, event.y)) {
				setState(STATE_HOVER);
			}
			break;

		case STATE_HOVER:
			if (!getSelctionArea().contains(event.x, event.y)) {
				setState(STATE_NORMAL);
			}
			break;
		case STATE_DRAGGING:
			int offset = event.x - getGradientArea().x;
			float newSelection = (offset / (float) getGradientArea().width) * 360f;
			newSelection = Math.min(Math.max(0, newSelection), 360f);
			setSelection(newSelection);
			break;
		}

	}

	protected void onMouseUp(Event event) {
		switch (state) {
		case STATE_DRAGGING:
			if (getSelctionArea().contains(event.x, event.y))
				setState(STATE_HOVER);
			else
				setState(STATE_NORMAL);
			break;

		default:
			setState(STATE_NORMAL);
			break;
		}
	}

	protected void onPaint(Event event) {
		GC gc = event.gc;
		gc.setAntialias(SWT.ON);
		drawGradient(gc);
		drawSelection(gc);
	}

	public void setBrightness(float brightness) {
		if (brightness < 0f || brightness > 1f) {
			throw new IllegalArgumentException();
		}
		if (this.brightness == brightness) {
			return;
		}
		this.brightness = brightness;
		invalidateHueImage();
		redraw();
	}

	private void invalidateHueImage() {
		if (hueImage != null && !hueImage.isDisposed()) {
			hueImage.dispose();
		}
		hueImage = null;
	}

	public void setSaturation(float saturation) {
		if (saturation < 0f || saturation > 1f) {
			throw new IllegalArgumentException();
		}
		if (this.saturation == saturation) {
			return;
		}
		this.saturation = saturation;
		invalidateHueImage();
		redraw();
	}

	public void setSelection(float selection) {
		if (this.selection == selection) {
			return;
		}
		this.selection = selection;
		redraw();
		Event event = new Event();
		event.widget = this;
		event.display = getDisplay();
		notifyListeners(SWT.Selection, event);
	}

	private void setState(int state) {
		if (this.state == state) {
			return;
		}
		this.state = state;
		redraw();
	}

}
