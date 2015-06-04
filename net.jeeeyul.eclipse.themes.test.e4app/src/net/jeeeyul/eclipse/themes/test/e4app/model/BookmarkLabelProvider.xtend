package net.jeeeyul.eclipse.themes.test.e4app.model

import org.eclipse.jface.viewers.LabelProvider

class BookmarkLabelProvider extends LabelProvider {
	def dispatch getText(BookmarkEntry entry) {
		entry.label
	}

	def dispatch getText(Object entry) {
		entry?.toString
	}
}