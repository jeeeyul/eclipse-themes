package net.jeeeyul.eclipse.themes.rendering.internal;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.NinePatch;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class ShadowGenerator {

	public ShadowGenerator() {
	}

	public static NinePatch createNinePatch(RGB background, RGB foreground, int radius, int shadowRadius) {
		Rectangle shadow = new Rectangle(0, 0, radius * 3 + shadowRadius * 2, radius * 3 + shadowRadius * 2);
		Rectangle shadowShape = SWTExtensions.INSTANCE.getShrinked(shadow, shadowRadius);

		ImageData shadowData = generate(shadow.width, shadow.height, background, shadowShape, radius, foreground, shadowRadius);

		Image image = new Image(Display.getDefault(), shadowData);
		NinePatch patch = new NinePatch(image, SWTExtensions.INSTANCE.getShrinked(shadowShape, radius));
		return patch;
	}

	private static ImageData generate(int width, int height, RGB background, Rectangle rectangle, int cornerRadius, RGB shadowColor, int shadowRadius) {
		ImageData result = new ImageData(width, height, 24, new PaletteData(0xff0000, 0xff00, 0xff));
		fill(result, 0, 0, width, height, background);
		fill(result, rectangle.x, rectangle.y, rectangle.width, rectangle.height, shadowColor, cornerRadius);

		for (int i = 0; i < shadowRadius; i++) {
			result = blur(result, SWTExtensions.INSTANCE.getShrinked(rectangle, cornerRadius));
		}

		return result;
	}

	private static ImageData blur(ImageData data, Rectangle bypass) {
		ImageData result = new ImageData(data.width, data.height, data.depth, data.palette);

		for (int x = 0; x < data.width; x++) {
			for (int y = 0; y < data.height; y++) {
				if (SWTExtensions.INSTANCE.contains(bypass, x, y)) {
					continue;
				}
				int r = 0;
				int g = 0;
				int b = 0;
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						RGB rgb = getRGB(data, x + dx, y + dy);
						r += rgb.red;
						g += rgb.green;
						b += rgb.blue;
					}
				}
				setRGB(result, x, y, new RGB(r / 9, g / 9, b / 9));
			}
		}

		return result;
	}

	private static RGB getRGB(ImageData data, int x, int y) {
		x = Math.min(Math.max(0, x), data.width - 1);
		y = Math.min(Math.max(0, y), data.height - 1);
		return data.palette.getRGB(data.getPixel(x, y));
	}

	private static void setRGB(ImageData data, int x, int y, RGB rgb) {
		data.setPixel(x, y, data.palette.getPixel(rgb));
	}

	private static void fill(ImageData data, int x, int y, int w, int h, RGB color) {

		for (int cx = x; cx < x + w; cx++) {
			for (int cy = y; cy < y + h; cy++) {
				setRGB(data, cx, cy, color);
			}
		}
	}

	private static int getDistance(int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	private static void fill(ImageData data, int x, int y, int w, int h, RGB color, int radius) {
		Rectangle topLeft = new Rectangle(x, y, radius, radius);
		Rectangle topRight = new Rectangle(x + w - radius, y, radius, radius);
		Rectangle bottomLeft = new Rectangle(x, y + h - radius, radius, radius);
		Rectangle bottomRight = new Rectangle(x + w - radius, y + h - radius, radius, radius);

		for (int cx = x; cx < x + w; cx++) {
			for (int cy = y; cy < y + h; cy++) {
				if (SWTExtensions.INSTANCE.contains(topLeft, cx, cy)) {
					int ox = topLeft.x + topLeft.width;
					int oy = topLeft.y + topLeft.height;
					if (getDistance(cx, cy, ox, oy) <= radius) {
						setRGB(data, cx, cy, color);
					}

				} else if (SWTExtensions.INSTANCE.contains(topRight, cx, cy)) {
					int ox = topRight.x;
					int oy = topRight.y + topRight.height;
					if (getDistance(cx, cy, ox, oy) <= radius) {
						setRGB(data, cx, cy, color);
					}
				} else if (SWTExtensions.INSTANCE.contains(bottomLeft, cx, cy)) {
					int ox = bottomLeft.x + bottomLeft.width;
					int oy = bottomLeft.y;
					if (getDistance(cx, cy, ox, oy) <= radius) {
						setRGB(data, cx, cy, color);
					}
				} else if (SWTExtensions.INSTANCE.contains(bottomRight, cx, cy)) {
					int ox = bottomRight.x;
					int oy = bottomRight.y;
					if (getDistance(cx, cy, ox, oy) <= radius) {
						setRGB(data, cx, cy, color);
					}
				} else {
					setRGB(data, cx, cy, color);
				}
			}
		}
	}
}
