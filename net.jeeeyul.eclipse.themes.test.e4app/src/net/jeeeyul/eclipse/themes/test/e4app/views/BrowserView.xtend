package net.jeeeyul.eclipse.themes.test.e4app.views

import javax.annotation.PostConstruct
import javax.inject.Inject
import net.jeeeyul.eclipse.themes.test.e4app.model.BookmarkEntry
import net.jeeeyul.eclipse.themes.test.e4app.ui.HoverData
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.di.UIEventTopic
import org.eclipse.swt.SWT
import org.eclipse.swt.browser.Browser
import org.eclipse.swt.browser.ProgressEvent
import org.eclipse.swt.browser.ProgressListener
import org.eclipse.swt.widgets.Composite
import org.eclipse.e4.core.services.events.IEventBroker

class BrowserView {
	Browser browser

	@Inject IEventBroker eventBroker

	@PostConstruct def create(Composite composite) {

		browser = new Browser(composite, SWT.NORMAL) => [
			layoutData = new HoverData(false)
			addProgressListener(new ProgressListener {
				override changed(ProgressEvent e) {
					eventBroker.post("progress/changed", e)
				}

				override completed(ProgressEvent e) {
					eventBroker.post("progress/completed", e)
				}
			})
		]
	}

	@Inject @Optional
	def selectionChanged(@UIEventTopic("bookmark/*") BookmarkEntry newEntry) {
		if (newEntry?.url != null) {
			browser.url = newEntry.url
		}
	}
}