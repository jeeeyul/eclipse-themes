package net.jeeeyul.eclipse.themes.css.internal.dynamicresource

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.Hashtable
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.ImageData
import org.eclipse.swt.graphics.ImageLoader
import org.osgi.framework.BundleContext
import org.osgi.service.url.AbstractURLStreamHandlerService
import org.osgi.service.url.URLConstants
import org.osgi.service.url.URLStreamHandlerService

class JeeeyulProtocol extends AbstractURLStreamHandlerService {
	DragHandleFactory fDragHandleFactory = new DragHandleFactory();
	FrameFactory fFrameFactory = new FrameFactory();

	override openConnection(URL url) throws IOException {
		val curi = new JTResourceURI(url.toURI.toString())

		switch curi.command {
			case "drag-handle": {
				var int height = Integer.parseInt(curi.getParameterValue("height", "22"))
				var HSB backgroundColor = new HSB(curi.getParameterValue("background-color"))
				var boolean embossed = Boolean.parseBoolean(curi.getParameterValue("embossed", "false"))
				var ImageData image = fDragHandleFactory.create(height, backgroundColor, embossed)
				var ImageLoader save = new ImageLoader()
				var ByteArrayOutputStream baos = new ByteArrayOutputStream()
				save.data = #[image]
				save.save(baos, SWT.IMAGE_PNG)
				new ByteArrayURLConnection(url, baos.toByteArray)
			}
			case "frame": {
				var HSB hue = new HSB(curi.getParameterValue("background-color"))
				var ImageData image = fFrameFactory.create(hue)
				var ImageLoader save = new ImageLoader()
				var ByteArrayOutputStream baos = new ByteArrayOutputStream()
				save.data = #[image]
				save.save(baos, SWT.IMAGE_PNG)
				new ByteArrayURLConnection(url, baos.toByteArray())
			}
			default:
				throw new MalformedURLException('''«curi.command» is unknown command''')
		}
	}

	def void setup(BundleContext context) {
		context.registerService(
			URLStreamHandlerService.name,
			this,
			new Hashtable(#{
				URLConstants.URL_HANDLER_PROTOCOL -> #["jeeeyul"] as String[]
			})
		)
	}
}
