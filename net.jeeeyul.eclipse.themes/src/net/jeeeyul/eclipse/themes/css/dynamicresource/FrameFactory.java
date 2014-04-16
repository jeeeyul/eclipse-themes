package net.jeeeyul.eclipse.themes.css.dynamicresource;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

/**
 * A factory that generated {@link ImageData} for ImageBasedFrame.
 * 
 * @author Jeeeyul
 */
public class FrameFactory {
	/**
	 * Generates an image for frame-image.
	 * 
	 * @param backgroundColor
	 *            background color.
	 * @return An image data for frame.
	 */
	public ImageData create(HSB backgroundColor) {
		ImageData source = SharedImages.getImageDescriptor(SharedImages.FRAME).getImageData();

		List<RGB> newRGBs = new ArrayList<RGB>();
		for (RGB each : source.palette.colors) {
			try {
				HSB copy = backgroundColor.getCopy();
				HSB hsb = new HSB(each);
				copy.brightness = hsb.brightness;
				copy = copy.mixWith(backgroundColor, 0.6f);
				newRGBs.add(copy.toRGB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		source.palette.colors = newRGBs.toArray(new RGB[newRGBs.size()]);
		return source;
	}
}
