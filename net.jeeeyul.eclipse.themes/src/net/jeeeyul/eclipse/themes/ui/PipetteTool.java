package net.jeeeyul.eclipse.themes.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class PipetteTool {
	private ArrayList<Shell> pipetteShells = new ArrayList<Shell>();
	private boolean loopExpired = false;
	private HSB result;
	private ImageData pipetteColorMask;
	private Shell parentShell;

	public PipetteTool(Shell parentShell) {
		this.parentShell = parentShell;
	}

	public HSB open() {
		Display display = Display.getCurrent();
		pipetteColorMask = new ImageData(getClass().getResourceAsStream("pipette-mask.png"));

		for (Monitor monitor : display.getMonitors()) {
			Shell eachPipetteShell = createPipetteShell(monitor);
			pipetteShells.add(eachPipetteShell);
		}

		for (Shell each : pipetteShells) {
			each.setVisible(true);
		}

		while (!loopExpired) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		disposeAllShells();

		return result;
	}

	private Shell createPipetteShell(Monitor monitor) {
		Display display = Display.getCurrent();
		Rectangle monitorArea = monitor.getBounds();
		final Image image = new Image(display, monitorArea.width, monitorArea.height);
		GC gc = new GC(display);
		gc.copyArea(image, monitorArea.x, monitorArea.y);
		gc.dispose();
		final Shell shell = new Shell(parentShell, SWT.ON_TOP | SWT.NO_TRIM | SWT.DOUBLE_BUFFERED);
		shell.setBounds(monitorArea);
		shell.setBackgroundImage(image);

		shell.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				image.dispose();
			}
		});
		shell.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ImageData imageData = image.getImageData();
				int pixel = imageData.getPixel(event.x, event.y);
				RGB rgb = imageData.palette.getRGB(pixel);
				handleSelection(rgb);
			}
		});
		shell.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE) {
					cancel();
				}
			}
		});
		shell.addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ImageData imageData = image.getImageData();
				int pixel = imageData.getPixel(event.x, event.y);
				RGB rgb = imageData.palette.getRGB(pixel);

				ImageData cursorData = upmix(new ImageData(getClass().getResourceAsStream("pipette.png")));
				for (int x = 0; x < pipetteColorMask.width; x++) {
					for (int y = 0; y < pipetteColorMask.height; y++) {
						boolean override = pipetteColorMask.getAlpha(x, y) != 0;
						if (override) {
							cursorData.setPixel(x, y, cursorData.palette.getPixel(rgb));
						}
					}
				}

				if (shell.getCursor() != null) {
					shell.getCursor().dispose();
				}

				Cursor newCursor = new Cursor(shell.getDisplay(), cursorData, 1, 14);
				shell.setCursor(newCursor);
				shell.redraw(event.x - 100, event.y - 100, 200, 200, false);

			}
		});
		return shell;
	}

	private ImageData upmix(ImageData imageData) {
		if (imageData.palette.redMask == 0xff0000 && imageData.palette.greenMask == 0xff00) {
			return imageData;
		}
		PaletteData paletteData = new PaletteData(0xff0000, 0xff00, 0xff);

		ImageData result = new ImageData(imageData.width, imageData.height, 32, paletteData);
		for (int x = 0; x < imageData.width; x++) {
			for (int y = 0; y < imageData.height; y++) {
				RGB rgb = imageData.palette.getRGB(imageData.getPixel(x, y));
				result.setPixel(x, y, paletteData.getPixel(rgb));
				result.setAlpha(x, y, imageData.getAlpha(x, y));
			}
		}
		return result;
	}

	protected void cancel() {
		loopExpired = true;
	}

	private void disposeAllShells() {
		for (Shell each : pipetteShells) {
			if (!each.isDisposed()) {
				if (each.getCursor() != null) {
					each.getCursor().dispose();
				}
				each.dispose();
			}
		}
	}

	protected void handleSelection(RGB rgb) {
		result = new HSB(rgb);
		loopExpired = true;
	}

	public static void main(String[] args) {
		Display.getDefault();
		HSB result = new PipetteTool(null).open();
		System.out.println(result);
	}
}
