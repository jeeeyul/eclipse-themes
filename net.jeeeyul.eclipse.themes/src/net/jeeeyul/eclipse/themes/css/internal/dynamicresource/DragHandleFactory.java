package net.jeeeyul.eclipse.themes.css.internal.dynamicresource;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import net.jeeeyul.eclipse.themes.CoreImages;
import net.jeeeyul.swtend.ui.HSB;

/**
 * A factory that generates drag handle image.
 * 
 * @author jeeeyul@gmail.com
 */
public class DragHandleFactory {

	/**
	 * 
	 * @param height
	 *            The height of drag handle to generate.
	 * @param backgroundColor
	 *            background color to generate drag handle.
	 * @param embossed
	 *            if <code>true</code> generated embossed drag handle, otherwise
	 *            generated engraved one.
	 * @return Drag handle {@link ImageData}.
	 */
	@SuppressWarnings("deprecation")
	public ImageData create(int height, HSB backgroundColor, boolean embossed, boolean rotated) {
		ImageData source = null;

		if (embossed) {
			if (rotated) {
				source = CoreImages.getImageDescriptor(CoreImages.HANDLE_EMBOSSED_ROTATED).getImageData();
			} else {
				source = CoreImages.getImageDescriptor(CoreImages.HANDLE_EMBOSSED).getImageData();
			}

		} else {
			if (rotated) {
				source = CoreImages.getImageDescriptor(CoreImages.HANDLE_ROTATED).getImageData();
			} else {
				source = CoreImages.getImageDescriptor(CoreImages.HANDLE).getImageData();
			}

		}

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
				hsb = hsb.mixWith(backgroundColor, 0.7f);
				newRGBs.add(hsb.toRGB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.palette.colors = newRGBs.toArray(new RGB[newRGBs.size()]);

		return result;
	}
}
