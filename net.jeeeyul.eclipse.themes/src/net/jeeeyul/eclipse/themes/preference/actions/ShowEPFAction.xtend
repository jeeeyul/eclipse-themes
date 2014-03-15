package net.jeeeyul.eclipse.themes.preference.actions

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText

class ShowEPFAction extends AbstractPreferenceAction {
	new(JTPreferencePage page) {
		super(page)
		this.text = "Show EPF"
	}

	override run() {
		val extension SWTExtension = SWTExtensions.INSTANCE
		
		var tempStore = new JThemePreferenceStore(new PreferenceStore)
		page.saveTo(tempStore)

		val epf = '''
			«FOR key : JTPUtil.listPreferenceKeys»
				«key»=«JTPUtil.saveConvert(tempStore.getString(key), false, true)»
			«ENDFOR»
		'''
		
		newShell(page.shell, SWT.APPLICATION_MODAL || SWT.SHELL_TRIM)[
			text = "EPF"
			image = SharedImages.getImage(SharedImages.JTHEME)
			layout = newGridLayout[
				marginWidth = 0 
				marginHeight = 0
			]
			new StyledText(it, SWT.READ_ONLY || SWT.MULTI || SWT.H_SCROLL || SWT.V_SCROLL) => [
				text = epf
				layoutData = FILL_BOTH[
					widthHint = 640
					heightHint = 480
				]
			]
			
			pack
		].open
	}
		
}