package net.jeeeyul.eclipse.themes.preference

import java.util.List
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.GradientEdit
import org.eclipse.jface.preference.PreferencePage
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IWorkbench
import org.eclipse.ui.IWorkbenchPreferencePage
import org.eclipse.swt.layout.FillLayout

class JTPartStackPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	extension SWTExtensions swtExt = SWTExtensions.INSTANCE
	extension PreperencePageHelper helper = new PreperencePageHelper(this);

	JeeeyulsTabRenderer renderer
	GradientEdit unselectedFillEdit
	GradientEdit unselectedBorderEdit
	Color[] tabBackground
	CTabFolder folder

	List<AbstractJTPreferencePage> pages = newArrayList(new PartPage)

	override init(IWorkbench workbench) {
	}

	override protected createContents(Composite parent) {
		folder = parent.newCTabFolder[]
		folder => [
			it.renderer = renderer = new JeeeyulsTabRenderer(it)
			for (each : pages) {
				newCTabItem[
					it.text = each.name
					it.control = each.createContents(folder, swtExt, helper)
					it.data = each
				]
			}
			folder.selection = folder.items.head
		]

		for (each : pages) {
			each.updatePreview()
		}

		return folder
	}

	def AbstractJTPreferencePage getActivePage() {
		folder.selection.data as AbstractJTPreferencePage
	}

	def void updatePreview(AbstractJTPreferencePage page) {
		page.updatePreview(folder, renderer.settings, swtExt, helper)
	}

	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE

		val shell = newShell[
			layout = new FillLayout => [
				marginWidth = 10
				marginHeight = 10
			]
			new JTPartStackPreferencePage().createContents(it)
		]
		
		asyncExec[
			shell.pack()
			shell.open()
		]

		shell.runLoop()
	}

}
