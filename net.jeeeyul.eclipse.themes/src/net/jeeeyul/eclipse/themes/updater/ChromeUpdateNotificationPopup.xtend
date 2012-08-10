package net.jeeeyul.eclipse.themes.updater

import java.net.URL
import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup
import org.eclipse.swt.SWT
import org.eclipse.swt.program.Program
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.PlatformUI
import org.eclipse.equinox.p2.operations.UpdateOperation

class ChromeUpdateNotificationPopup extends AbstractNotificationPopup {
	extension SWTExtensions = new SWTExtensions
	
	UpdateOperation operation
	
	new(Display display, UpdateOperation operation) {
		super(display)
		fadingEnabled = true
		delayClose = 10000L
		this.operation = operation
	}
	
	override protected getPopupShellTitle() {
		"Jeeeyul's theme has an update"
	}
	
	override protected getPopupShellImage(int maximumHeight) {
		SharedImages::getImage(SharedImages::PALETTE)
	}
	
	override protected createContentArea(Composite parent) {
		parent => [
			Label[
				text = "Update available"
			]
			
			Link[
				text = "<a href=\"update\">Update Now</a> or read <a href=\"release\">release note</a>"
				addListener(SWT::Selection)[
					if(it.text == "update"){
						doUpdate()
					}
					else if(it.text == "release"){
						openReleaseNote()	
					}
				]
			]
		]
	}
	
	def private void openReleaseNote(){
		if(PlatformUI::workbenchRunning){
			var browser = PlatformUI::workbench.browserSupport.createBrowser("chrome-theme-release-note")
			browser.openURL(new URL("https://github.com/jeeeyul/eclipse-themes/wiki/Release-Note"))
		}
		else{
			Program::launch("https://github.com/jeeeyul/eclipse-themes/wiki/Release-Note")
		}
	}
	
	def private void doUpdate() {
		new UpdateChromeJob(operation).schedule()
	}
}