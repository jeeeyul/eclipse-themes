package net.jeeeyul.eclipse.themes.preference.actions

import net.jeeeyul.eclipse.themes.css.CustomThemeGenerator
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import net.jeeeyul.eclipse.themes.SharedImages

class ShowCSSAction extends AbstractPreferenceAction {
	new(JTPreferencePage page) {
		super(page)
		this.text = "Show CSS"
	}

	override run() {
		val extension SWTExtension = SWTExtensions.INSTANCE
		
		var tempStore = new JThemePreferenceStore(new PreferenceStore)
		page.saveTo(tempStore)
		
		var generator = new CustomThemeGenerator(tempStore)
		val css = generator.generate
		
		newShell(page.shell, SWT.APPLICATION_MODAL || SWT.SHELL_TRIM)[
			text = "CSS"
			image = SharedImages.getImage(SharedImages.JTHEME)
			layout = newGridLayout[
				marginWidth = 0 
				marginHeight = 0
			]
			new StyledText(it, SWT.READ_ONLY || SWT.MULTI || SWT.H_SCROLL || SWT.V_SCROLL) => [
				text = css
				layoutData = FILL_BOTH[
					widthHint = 640
					heightHint = 480
				]
			]
			
			pack
		].open
	}
		
}