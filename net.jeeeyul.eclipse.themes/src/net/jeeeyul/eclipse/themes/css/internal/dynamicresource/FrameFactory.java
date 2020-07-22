package net.jeeeyul.eclipse.themes.css.internal.dynamicresource;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import net.jeeeyul.eclipse.themes.CoreImages;
import net.jeeeyul.swtend.ui.HSB;

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
	@SuppressWarnings("deprecation")
	public ImageData create(HSB backgroundColor, boolean rotated) {
		ImageData source = null;
		if (!rotated) {
			source = CoreImages.getImageDescriptor(CoreImages.FRAME).getImageData();
		} else {
			source = CoreImages.getImageDescriptor(CoreImages.FRAME_ROTATED).getImageData();
		}

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
