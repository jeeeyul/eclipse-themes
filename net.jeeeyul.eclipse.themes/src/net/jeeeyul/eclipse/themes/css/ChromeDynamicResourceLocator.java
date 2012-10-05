package net.jeeeyul.eclipse.themes.css;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.jeeeyul.eclipse.themes.ui.HSB;

import org.eclipse.e4.ui.css.core.util.resources.IResourceLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

@SuppressWarnings("restriction")
public class ChromeDynamicResourceLocator implements IResourceLocator {
	DragHandleFactory dragHandleFactory = new DragHandleFactory();
	FrameFactory frameFactory = new FrameFactory();

	@Override
	public String resolve(String uri) {
		if (uri.startsWith("chrome://")) {
			String[] segments = uri.substring(9).split("/");
			if (segments.length > 0) {
				return uri;
			}
		}

		return null;
	}

	@Override
	public InputStream getInputStream(String uri) throws Exception {
		String[] segments = uri.substring(9).split("/");
		if (segments[0].equals("drag-handle")) {
			int height = Integer.parseInt(segments[1].trim());
			HSB hue = HSB.createFromString(segments[2].trim());

			ImageData image = dragHandleFactory.create(height, hue);
			ImageLoader save = new ImageLoader();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			save.data = new ImageData[] { image };
			save.save(baos, SWT.IMAGE_GIF);
			return new ByteArrayInputStream(baos.toByteArray());
		}

		else if (segments[0].equals("frame")) {
			HSB hue = HSB.createFromString(segments[1].trim());

			ImageData image = frameFactory.create(hue);
			ImageLoader save = new ImageLoader();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			save.data = new ImageData[] { image };
			save.save(baos, SWT.IMAGE_GIF);
			return new ByteArrayInputStream(baos.toByteArray());
		}

		return null;
	}

	@Override
	public Reader getReader(String uri) throws Exception {
		return new InputStreamReader(getInputStream(uri));
	}

}
