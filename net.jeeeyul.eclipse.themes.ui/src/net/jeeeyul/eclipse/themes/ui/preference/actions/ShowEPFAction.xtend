package net.jeeeyul.eclipse.themes.ui.preference.actions

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText

/**
 * Displays EPF content that will be generated with currently editing in preference page.
 */
class ShowEPFAction extends AbstractPreferenceAction {
	new(JTPreferencePage page) {
		super(page)
		this.text = "Show EPF"
		this.imageDescriptor = SharedImages.getImageDescriptor(SharedImages.PROPS)
	}

	override run() {
		val extension SWTExtension = SWTExtensions.INSTANCE

		var tempStore = new JThemePreferenceStore(new PreferenceStore)
		page.saveTo(tempStore)
		val presetKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET)
		val epf = '''
			«FOR key : presetKeys»
				«key»=«JTPUtil.saveConvert(tempStore.getString(key), false, true)»
			«ENDFOR»
		'''

		var shell = newShell(page.shell, SWT.APPLICATION_MODAL || SWT.SHELL_TRIM) [
			text = "EPF"
			image = SharedImages.getImage(SharedImages.JTHEME)
			background = COLOR_WHITE
			layout = newGridLayout[
				marginWidth = 5
				marginHeight = 5
			]
			new StyledText(it, SWT.READ_ONLY || SWT.MULTI || SWT.H_SCROLL || SWT.V_SCROLL) => [
				text = epf
				background = COLOR_WHITE
				foreground = COLOR_BLACK
				layoutData = FILL_BOTH[
					widthHint = 640
					heightHint = 480
				]
			]
			pack
		]

		var shellBounds = shell.bounds
		shell.bounds = shellBounds.relocateCenterWith(page.shell.monitor.bounds.center)
		shell.open
	}

}
