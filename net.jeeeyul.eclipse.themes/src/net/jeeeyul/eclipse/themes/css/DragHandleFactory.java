package net.jeeeyul.eclipse.themes.css;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.ui.HSB;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class DragHandleFactory {
	public ImageData create(int height, HSB backgroundColor) {
		ImageData source = SharedImages.getImageDescriptor(SharedImages.HANDLE).getImageData();
		ImageData result = new ImageData(source.width, height, source.depth, source.palette);

		int offset = (result.height - source.height) / 2;

		for (int x = 0; x < result.width; x++) {
			for (int y = 0; y < result.height; y++) {
				if (y >= offset && y < offset + source.height) {
					result.setPixel(x, y, source.getPixel(x, y - offset));
				} else {
					result.setPixel(x, y, source.transparentPixel);
				}
			}
		}
		result.transparentPixel = source.transparentPixel;

		List<RGB> newRGBs = new ArrayList<RGB>();
		for (RGB each : result.palette.colors) {
			try {
				HSB hsb = backgroundColor.getCopy();
				hsb.brightness = new HSB(each).brightness;
				hsb = hsb.mixWith(backgroundColor, 0.5f);
				newRGBs.add(hsb.toRGB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.palette.colors = newRGBs.toArray(new RGB[newRGBs.size()]);

		return result;
	}
}
