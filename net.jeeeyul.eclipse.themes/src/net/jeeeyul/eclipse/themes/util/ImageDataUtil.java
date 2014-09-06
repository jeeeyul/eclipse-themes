package net.jeeeyul.eclipse.themes.util;

import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

@SuppressWarnings("javadoc")
public class ImageDataUtil {

	public static ImageData convertBrightnessToAlpha(ImageData data, HSB foregroundColor) {
		for (int x = 0; x < data.width; x++) {
			for (int y = 0; y < data.height; y++) {
				int pixel = data.getPixel(x, y);
				RGB rgb = data.palette.getRGB(pixel);
				HSB hsb = new HSB(rgb);
				int alpha = (int) (hsb.brightness * 255);
				data.setPixel(x, y, data.palette.getPixel(foregroundColor.toRGB()));
				data.setAlpha(x, y, alpha);
			}
		}

		return data;
	}
}
