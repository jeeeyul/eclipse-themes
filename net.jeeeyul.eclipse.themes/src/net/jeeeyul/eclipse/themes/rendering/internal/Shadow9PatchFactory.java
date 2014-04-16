package net.jeeeyul.eclipse.themes.rendering.internal;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.NinePatch;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("javadoc")
public class Shadow9PatchFactory {

	private static ImageData blur(ImageData src, int radius) {
		ImageData dest = new ImageData(src.width, src.height, src.depth, src.palette);

		for (int x = 0; x < src.width; x++) {
			for (int y = 0; y < src.height; y++) {
				dest.setPixel(x, y, src.getPixel(x, y));
				double a = 0;
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						int rx = x + dx;
						int ry = y + dy;
						a = a + getAlpha(src, rx, ry);
					}
				}
				dest.setAlpha(x, y, limit(a / 9d));
			}
		}

		return dest;
	}

	public static NinePatch createShadowPatch(RGB foreground, int radius, int shadowRadius) {
		radius = Math.max(radius, 5);
		shadowRadius = Math.max(shadowRadius, 1);

		Rectangle shadow = new Rectangle(0, 0, radius * 3 + shadowRadius * 2, radius * 3 + shadowRadius * 2);
		Rectangle shadowShape = SWTExtensions.INSTANCE.getShrinked(shadow, shadowRadius);

		ImageData shadowData = generateShadowImageData(shadow.width, shadow.height, shadowShape, radius, foreground, shadowRadius);
		NinePatch patch = new NinePatch(shadowData, SWTExtensions.INSTANCE.getShrinked(shadowShape, radius));
		return patch;
	}

	private static void fill(ImageData data, int x, int y, int w, int h, RGB color, int radius, int alpha) {
		Rectangle topLeft = new Rectangle(x, y, radius, radius);
		Rectangle topRight = new Rectangle(x + w - radius, y, radius, radius);
		Rectangle bottomLeft = new Rectangle(x, y + h - radius, radius, radius);
		Rectangle bottomRight = new Rectangle(x + w - radius, y + h - radius, radius, radius);

		if (radius > 0) {
			for (int cx = x; cx < x + w; cx++) {
				for (int cy = y; cy < y + h; cy++) {
					if (SWTExtensions.INSTANCE.contains(topLeft, cx, cy)) {
						int ox = topLeft.x + topLeft.width;
						int oy = topLeft.y + topLeft.height;
						if (getDistance(cx, cy, ox, oy) <= radius) {
							setRGB(data, cx, cy, color, alpha);
						}

					} else if (SWTExtensions.INSTANCE.contains(topRight, cx, cy)) {
						int ox = topRight.x;
						int oy = topRight.y + topRight.height;
						if (getDistance(cx, cy, ox, oy) <= radius) {
							setRGB(data, cx, cy, color, alpha);
						}
					} else if (SWTExtensions.INSTANCE.contains(bottomLeft, cx, cy)) {
						int ox = bottomLeft.x + bottomLeft.width;
						int oy = bottomLeft.y;
						if (getDistance(cx, cy, ox, oy) <= radius) {
							setRGB(data, cx, cy, color, alpha);
						}
					} else if (SWTExtensions.INSTANCE.contains(bottomRight, cx, cy)) {
						int ox = bottomRight.x;
						int oy = bottomRight.y;
						if (getDistance(cx, cy, ox, oy) <= radius) {
							setRGB(data, cx, cy, color, alpha);
						}
					} else {
						setRGB(data, cx, cy, color, alpha);
					}
				}
			}
		} else {
			for (int cx = x; cx < x + w; cx++) {
				for (int cy = y; cy < y + h; cy++) {
					setRGB(data, cx, cy, color, alpha);
				}
			}
		}
	}

	private static ImageData generateShadowImageData(int width, int height, Rectangle rectangle, int cornerRadius, RGB shadowColor, int shadowRadius) {
		ImageData result = new ImageData(width, height, 32, new PaletteData(0xff0000, 0xff00, 0xff));
		fill(result, 0, 0, width, height, shadowColor, 0, 0);
		fill(result, rectangle.x, rectangle.y, rectangle.width, rectangle.height, shadowColor, cornerRadius, 255);
		for (int i = 0; i < shadowRadius; i++) {
			result = blur(result, shadowRadius);
		}
		return result;
	}

	private static int getAlpha(ImageData data, int x, int y) {
		x = Math.min(Math.max(0, x), data.width - 1);
		y = Math.min(Math.max(0, y), data.height - 1);
		return data.getAlpha(x, y);
	}

	private static int getDistance(int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	private static int limit(double v) {
		return (int) Math.min(Math.max(v, 0), 255);
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();

		final NinePatch patch = createShadowPatch(new RGB(255, 0, 0), 40, 40);

		Shell shell = new Shell(display);
		shell.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				patch.fill(event.gc, new Rectangle(200, 0, 300, 300));
				event.gc.drawRectangle(new Rectangle(200, 0, 300, 300));
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void setRGB(ImageData data, int x, int y, RGB rgb, int alpha) {
		data.setPixel(x, y, data.palette.getPixel(rgb));
		data.setAlpha(x, y, alpha);
	}

	public Shadow9PatchFactory() {
	}
}
