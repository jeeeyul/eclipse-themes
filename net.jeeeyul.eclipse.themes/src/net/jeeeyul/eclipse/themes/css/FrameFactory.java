package net.jeeeyul.eclipse.themes.css;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.ui.HSB;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class FrameFactory {
	public ImageData create(HSB backgroundColor) {
		ImageData source = SharedImages.getImageDescriptor(SharedImages.FRAME).getImageData();

		List<RGB> newRGBs = new ArrayList<RGB>();
		for (RGB each : source.palette.colors) {
			try {
				HSB hsb = new HSB(each);
				hsb = hsb.mixWith(backgroundColor, 0.6f);
				newRGBs.add(hsb.toRGB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		source.palette.colors = newRGBs.toArray(new RGB[newRGBs.size()]);
		return source;
	}
}
