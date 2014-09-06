package net.jeeeyul.eclipse.themes.css.internal.dynamicresource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.e4.ui.css.core.util.resources.IResourceLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * Provides dynamic resources for URI that starts with "jeeeyul://"
 * 
 * Currently provides
 * "jeeeyul://drag-handle?height=[number]&background-color=[hsb]" and
 * "jeeeyul://frame?background-color=[hsb]".
 * 
 * @see DragHandleFactory
 * @see FrameFactory
 * 
 * @author Jeeeyul Lee <jeeeyul@gmail.com>
 */
@SuppressWarnings("restriction")
public class JTDynamicResourceLocator implements IResourceLocator {
	DragHandleFactory dragHandleFactory = new DragHandleFactory();
	FrameFactory frameFactory = new FrameFactory();

	@Override
	public String resolve(String uri) {
		if (uri.startsWith("jeeeyul://")) {
			String[] segments = uri.substring(9).split("/");
			if (segments.length > 0) {
				return uri;
			}
		}

		return null;
	}

	@Override
	public InputStream getInputStream(String uri) throws Exception {
		JTResourceURI curi = new JTResourceURI(uri);

		String command = curi.getCommand();

		if (command.equals("drag-handle")) {
			int height = Integer.parseInt(curi.getParameterValue("height", "22"));
			HSB backgroundColor = new HSB(curi.getParameterValue("background-color"));
			boolean embossed = Boolean.parseBoolean(curi.getParameterValue("embossed", "false"));

			ImageData image = dragHandleFactory.create(height, backgroundColor, embossed);
			ImageLoader save = new ImageLoader();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			save.data = new ImageData[] { image };
			save.save(baos, SWT.IMAGE_GIF);
			return new ByteArrayInputStream(baos.toByteArray());
		}
		
		else if (command.equals("frame")) {
			HSB hue = new HSB(curi.getParameterValue("background-color"));

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
