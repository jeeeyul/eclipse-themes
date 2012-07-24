package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
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

	public static void main(String[] args) {
		Display display = Display.getDefault();

		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		HueScale hueScale = new HueScale(shell, SWT.NORMAL);
		hueScale.setSelection(180f);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static final int STATE_NORMAL = 0;
	private static final int STATE_HOVER = 1;
	private Image hueImage;
	private float selection;
	private int state;

	private void setState(int state) {
		if (this.state == state) {
			return;
		}
		this.state = state;
		redraw();
	}

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
	}

	protected void onMouseMove(Event event) {
		if (getSelctionArea().contains(event.x, event.y)) {
			setState(STATE_HOVER);
		}
	}

	private Rectangle getGradientArea() {
		Rectangle clientArea = getClientArea();
		Rectangle gradientArea = getClientArea();
		gradientArea.height = 10;
		gradientArea.y = (clientArea.height - gradientArea.height) / 2;
		gradientArea.width -= 10;
		gradientArea.x = (clientArea.width - gradientArea.width) / 2;
		return gradientArea;
	}

	public Image getHueImage() {
		if (hueImage == null) {
			PaletteData palette = new PaletteData(0xff0000, 0xff00, 0xff);
			ImageData data = new ImageData(360, 1, 32, palette);
			for (int x = 0; x < 360; x++) {
				data.setPixel(x, 0, palette.getPixel(new RGB((float) x, 1.0f, 1.0f)));
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

	public float getSelection() {
		return selection;
	}

	protected void onPaint(Event event) {
		GC gc = event.gc;
		gc.setAntialias(SWT.ON);
		drawGradient(gc);
		drawSelection(gc);
	}

	private void drawSelection(GC gc) {
		gc.setAlpha(255);
		Rectangle selectionBox = getSelctionArea();

		Rectangle outline = expand(selectionBox, 3, 3);
		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gc.fillRoundRectangle(outline.x, outline.y, outline.width, outline.height, 5, 5);
		gc.drawRoundRectangle(outline.x, outline.y, outline.width, outline.height, 5, 5);

		Color color = new Color(getDisplay(), new RGB(selection, 1f, 1f));
		gc.setBackground(color);
		gc.fillRectangle(selectionBox);
		gc.drawRectangle(selectionBox);

		color.dispose();
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

	private Rectangle getSelctionArea() {
		Rectangle gradientArea = getGradientArea();
		int selectionOffset = (int) ((gradientArea.width / 360f) * selection + gradientArea.x);
		Rectangle selectionBox = new Rectangle(selectionOffset - 5, gradientArea.y - 5, 10, gradientArea.height + 10);
		return selectionBox;
	}

	public void setSelection(float selection) {
		this.selection = selection;
		redraw();
	}

	private static Rectangle expand(Rectangle original, int dx, int dy) {
		return new Rectangle(original.x - dx, original.y - dy, original.width + dx * 2, original.height + dy * 2);
	}

}
