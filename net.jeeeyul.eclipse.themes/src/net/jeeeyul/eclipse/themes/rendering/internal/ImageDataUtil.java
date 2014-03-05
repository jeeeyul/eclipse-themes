package net.jeeeyul.eclipse.themes.rendering.internal;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ImageDataUtil {

	public ImageDataUtil() {
	}

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

	public static void main(String[] args) {
		Display display = Display.getDefault();
		ImageData data = convertBrightnessToAlpha(SharedImages.getImage(SharedImages.ACTIVE_PART).getImageData(), HSB.RED);
		Image image = new Image(display, data);

		Shell shell = new Shell(display, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shell.setLayout(new FillLayout());
		Button button = new Button(shell, SWT.PUSH);
		button.setImage(image);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
