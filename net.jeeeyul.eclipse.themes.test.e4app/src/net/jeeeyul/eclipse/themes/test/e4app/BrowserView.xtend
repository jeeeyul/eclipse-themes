package net.jeeeyul.eclipse.themes.test.e4app

import javax.annotation.PostConstruct
import javax.inject.Inject
import net.jeeeyul.eclipse.themes.test.e4app.model.BookmarkEntry
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.di.UIEventTopic
import org.eclipse.swt.SWT
import org.eclipse.swt.browser.Browser
import org.eclipse.swt.widgets.Composite

class BrowserView {
	Browser browser

	@PostConstruct def create(Composite composite) {
		browser = new Browser(composite, SWT.NORMAL)
	}

	@Inject @Optional
	def selectionChanged(@UIEventTopic("bookmark/*") BookmarkEntry newEntry) {
		browser.url = newEntry.url
	}
}