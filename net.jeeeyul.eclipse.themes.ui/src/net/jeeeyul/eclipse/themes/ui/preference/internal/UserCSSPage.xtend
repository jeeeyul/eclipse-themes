package net.jeeeyul.eclipse.themes.ui.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.bindings.keys.KeyStroke
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.TextViewerUndoManager
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.jface.text.source.SourceViewerConfiguration
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event

class UserCSSPage extends AbstractJTPreferencePage {
	SourceViewer viewer
	TextViewerUndoManager undoManager

	KeyStroke undoKey
	KeyStroke redoKey
	KeyStroke selectAllKey

	new() {
		this.name  = "Custom CSS"
		image = SharedImages.getImage(SharedImages.CSS)

		undoKey = KeyStroke.getInstance("M1+z")
		redoKey = if(System.properties.getProperty("os.name").contains("Mac"))
			KeyStroke.getInstance("M1+M2+z")
		else
			KeyStroke.getInstance("M1+y")
		selectAllKey = KeyStroke.getInstance("M1+a")
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout
			newCLabel[
				image = SharedImages.getImage(SharedImages.WARN_TSK)
				text = "Using this feature can cause side effects."
			]
			viewer = new SourceViewer(it, null, SWT.V_SCROLL || SWT.H_SCROLL || SWT.BORDER)
			viewer.document = new Document
			viewer.configure(new SourceViewerConfiguration)
			undoManager = new TextViewerUndoManager(100)
			undoManager.connect(viewer)
			viewer.setUndoManager(undoManager)
			viewer.control.onKeyDown = [
				handleKeyDown(it)
			]
			viewer.control.layoutData = FILL_BOTH[
				heightHint = 200
				widthHint = 200
			]
		]
	}

	private def handleKeyDown(Event e) {
		if(e.matches(undoKey)) {
			if(undoManager.undoable) {
				undoManager.undo()
			}
		} else if(e.matches(redoKey)) {
			if(undoManager.redoable) {
				undoManager.redo()
			}
		} else if(e.matches(selectAllKey)) {
			viewer.setSelectedRange(0, viewer.document.length)
		}
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		viewer.document.set(store.getString(JTPConstants.Others.USER_CSS))
		undoManager.reset()
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		store.setValue(JTPConstants.Others.USER_CSS, viewer.document.get())
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		undoManager.disconnect()
	}

	private def boolean matches(Event e, KeyStroke stroke) {
		return stroke.modifierKeys == e.stateMask && e.keyCode == stroke.naturalKey
	}

}
