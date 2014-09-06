package net.jeeeyul.eclipse.themes.ui.linux

import java.io.File
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.wizard.WizardPage
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.program.Program

class DownloadGTKRC2Page extends WizardPage {
	extension SWTExtensions = SWTExtensions.INSTANCE

	Button overwriteButton

	new() {
		super("Linux GTK Fix")
		title = "Updates ~/.gtkrc-2.0"
		description = '''Download and install GTK 2.0 Runtime Configuration'''
	}

	override createControl(Composite parent) {
		control = parent.newComposite [
			layout = newGridLayout
			newLink[
				text = '''
					This wizard will download <a href="https://github.com/jeeeyul/jeeeyul.github.io/blob/master/files/gtkrc-2.0">.gtkrc-2.0</a> into your home directory.
					It will fix various problems on GTK/SWT, for instance Huge Toolbar.
					
					After installation, You need to restart eclipse.
					Then choose a preset that you want once again.
					
					Read details in <a href="https://github.com/jeeeyul/eclipse-themes/wiki/Linux-User-Guide">Linux User Guide</a>
				'''
				
				onSelection = [
					Program.launch(it.text)
				]
			]
			overwriteButton = newCheckbox[
				text = '''Overwrite «System.getProperty("user.home")»/.gtkrc-2.0 if exists.'''
				onSelection = [
					validate()
				]
			]
		]

		validate()
	}

	def private void validate() {
		pageComplete = true
		errorMessage = null

		var file = new File(System.getProperty("user.home"), ".gtkrc-2.0")
		if(file.exists && overwriteButton.selection == false) {
			setErrorMessage(".gtkrc-2.0 is already exist.")
			pageComplete = false
		}

		container.updateButtons
	}

	override DownloadGTKRC2Wizard getWizard() {
		super.getWizard() as DownloadGTKRC2Wizard
	}

}
