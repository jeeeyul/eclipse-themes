package net.jeeeyul.eclipse.themes.test.e4app.ui

import javax.annotation.PostConstruct
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.graphics.Point

class Spacer {

	@PostConstruct def create(Composite composite, MToolControl item) {
		val height = Integer.parseInt(item.persistedState.get("height") ?: "10")
		new Composite(composite, SWT.NORMAL){
			override computeSize(int wHint, int hHint, boolean changed) {
				return new Point(1, height)
			}
			
		}
	}
}