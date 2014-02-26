package net.jeeeyul.eclipse.themes.css;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class FrameFactory {
	public ImageData create(HSB backgroundColor) {
		ImageData source = SharedImages.getImageDescriptor(SharedImages.FRAME).getImageData();

		List<RGB> newRGBs = new ArrayList<RGB>();
		for (RGB each : source.palette.colors) {
			try {
				HSB copy = backgroundColor.getCopy();
				HSB hsb = new HSB(each);
				copy.brightness = hsb.brightness;
				copy = copy.mixWith(backgroundColor, 0.7f);
				newRGBs.add(copy.toRGB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		source.palette.colors = newRGBs.toArray(new RGB[newRGBs.size()]);
		return source;
	}
}
