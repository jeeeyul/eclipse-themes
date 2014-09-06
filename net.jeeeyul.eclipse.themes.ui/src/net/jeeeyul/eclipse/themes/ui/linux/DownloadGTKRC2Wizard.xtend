package net.jeeeyul.eclipse.themes.ui.linux

import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.jface.wizard.Wizard

class DownloadGTKRC2Wizard extends Wizard {
	@Property boolean dontDownload

	new() {
		needsProgressMonitor = true
		windowTitle = "Linux GTK2 RC Wizard"
	}

	override addPages() {
		addPage(new DownloadGTKRC2Page)
	}

	override performFinish() {
		if(dontDownload) {
			return true
		}

		container.run(true, false) [
			it.beginTask("Downloading", IProgressMonitor.UNKNOWN)
			var file = new File(System.getProperty("user.home"), ".gtkrc-2.0")
			var url = new URL("http://eclipse.jeeeyul.net/files/gtkrc-2.0");
			var rbc = Channels.newChannel(url.openStream)
			var fos = new FileOutputStream(file)
			fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
			it.done
			new AskingRestart().schedule()
		]

		true
	}
}
