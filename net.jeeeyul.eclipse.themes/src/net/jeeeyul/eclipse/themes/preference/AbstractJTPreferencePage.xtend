package net.jeeeyul.eclipse.themes.preference

import org.eclipse.jface.preference.PreferencePage
import org.eclipse.ui.IWorkbenchPreferencePage
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IWorkbench
import org.eclipse.swt.widgets.Control
import net.jeeeyul.swtend.SWTExtensions

abstract class AbstractJTPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	override protected createContents(Composite parent) {
		return createContents(parent, SWTExtensions.INSTANCE, new PreperencePageHelper)
	}

	override init(IWorkbench workbench) {
	}

	protected abstract def Control createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper);

}
