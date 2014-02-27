package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IWorkbench
import org.eclipse.ui.IWorkbenchPreferencePage

class JTPartStackPreferencePage extends AbstractJTPreferencePage implements IWorkbenchPreferencePage {
	JeeeyulsTabRenderer renderer
	GradientEdit unselectedFillEdit
	GradientEdit unselectedBorderEdit
	Color[] tabBackground
	CTabFolder folder

	override init(IWorkbench workbench) {
	}

	override protected createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE

		newShell[
			new JTPartStackPreferencePage().createContents(it)
		].openAndRunLoop()
	}

}
