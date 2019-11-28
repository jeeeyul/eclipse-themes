package net.jeeeyul.eclipse.themes.test.e4app.ui

import javax.annotation.PostConstruct
import com.google.inject.Inject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.di.UIEventTopic
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl
import org.eclipse.swt.SWT
import org.eclipse.swt.browser.ProgressEvent
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.ProgressBar
import org.eclipse.ui.progress.UIJob

class Progress {
	ProgressBar progressBar
	UIJob autoHide = new UIJob(Display.^default, "hiding"){
		override runInUIThread(IProgressMonitor monitor) {
			progressBar.visible = false
			return Status.OK_STATUS
		}
	}
	
	@PostConstruct def create(Composite composite, MToolControl item) {
		progressBar = new ProgressBar(composite, SWT.SMOOTH) => [
			maximum = 0
			selection = 0
			visible = false
		]
	}
	
	@Inject @Optional
	def selectionChanged(@UIEventTopic("progress/*") ProgressEvent e) {
		progressBar.maximum = e.total
		progressBar.selection = e.current
		autoHide()
	}
	
	def autoHide(){
		progressBar.visible = true
		autoHide.schedule(3000)
	}
}