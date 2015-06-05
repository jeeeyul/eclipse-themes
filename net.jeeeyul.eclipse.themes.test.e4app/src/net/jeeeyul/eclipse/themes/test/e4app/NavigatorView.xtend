package net.jeeeyul.eclipse.themes.test.e4app

import javax.annotation.PostConstruct
import javax.inject.Inject
import net.jeeeyul.eclipse.themes.test.e4app.model.BookmarkContentProvider
import net.jeeeyul.eclipse.themes.test.e4app.model.BookmarkEntry
import net.jeeeyul.eclipse.themes.test.e4app.model.BookmarkLabelProvider
import org.eclipse.e4.core.services.events.IEventBroker
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.TreeViewer
import org.eclipse.swt.widgets.Composite

class NavigatorView {
	@Inject IEventBroker eventBroker
	
	@PostConstruct def create(Composite composite) {
		var viewer = new TreeViewer(composite);
		viewer.labelProvider = new BookmarkLabelProvider
		viewer.contentProvider = new BookmarkContentProvider
		viewer.input = createModel

		viewer.addOpenListener [ e |
			var selected = (e.selection as IStructuredSelection).firstElement as BookmarkEntry
			eventBroker.post("bookmark/selection", selected)
		]
	}

	def private createModel() {
		return new BookmarkEntry => [
			label = "Root"
			
			children += new BookmarkEntry => [
				label = "Bookmarks"
				
				children += new BookmarkEntry => [
					label = "eclipse"
					url = "http://eclipse.org"
				]
				children += new BookmarkEntry => [
					label = "github"
					url = "http://github.com"
				]
			]
			
			children += new BookmarkEntry => [
				label = "Jeeeyul's"
				
				children += new BookmarkEntry => [
					label = "Theme Store"
					url = "http://themes.jeeeyul.net"
				]
				children += new BookmarkEntry => [
					label = "github"
					url = "https://github.com/jeeeyul/eclipse-themes"
				]
			]

		]
	}
}