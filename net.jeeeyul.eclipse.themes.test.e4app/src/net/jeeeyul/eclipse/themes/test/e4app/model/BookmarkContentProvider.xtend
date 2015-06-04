package net.jeeeyul.eclipse.themes.test.e4app.model

import org.eclipse.jface.viewers.ITreeContentProvider
import org.eclipse.jface.viewers.Viewer

class BookmarkContentProvider implements ITreeContentProvider {

	override getChildren(Object e) {
		e.elements
	}

	override getElements(Object inputElement) {
		switch (inputElement) {
			BookmarkEntry: inputElement.children
			default: #[]
		}
	}

	override getParent(Object element) {
		null
	}

	override hasChildren(Object element) {
		switch (element) {
			BookmarkEntry: !element.children.empty
			default: false
		}
	}

	override dispose() {
	}

	override inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}