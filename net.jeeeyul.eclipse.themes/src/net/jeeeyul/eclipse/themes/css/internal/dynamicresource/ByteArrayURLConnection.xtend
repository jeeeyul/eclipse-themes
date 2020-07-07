package net.jeeeyul.eclipse.themes.css.internal.dynamicresource

import java.io.ByteArrayInputStream
import java.io.IOException
import java.net.URL
import java.net.URLConnection

class ByteArrayURLConnection extends URLConnection {
	byte[] fData;

	new(URL url, byte[] data) {
		super(url)
		fData = data;
	}

	override connect() throws IOException {
	}

	override getInputStream() throws IOException {
		new ByteArrayInputStream(fData)
	}

}
