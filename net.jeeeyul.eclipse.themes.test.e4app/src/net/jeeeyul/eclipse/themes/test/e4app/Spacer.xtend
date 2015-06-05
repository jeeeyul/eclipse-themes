package net.jeeeyul.eclipse.themes.test.e4app

import javax.annotation.PostConstruct
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label

class Spacer {

	@PostConstruct def create(Composite composite) {
		new Label(composite, SWT.NORMAL)
	}
}