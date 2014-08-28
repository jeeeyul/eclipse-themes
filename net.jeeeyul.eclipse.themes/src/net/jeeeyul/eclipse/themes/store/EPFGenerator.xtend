package net.jeeeyul.eclipse.themes.store

import net.jeeeyul.eclipse.themes.JThemesCore
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil
import net.jeeeyul.eclipse.themes.preference.internal.IPreferenceFilter

class EPFGenerator {
	def String generate(){
		val store = JThemesCore.^default.preferenceStore
		val keys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET)
		
		return '''
			«FOR each : keys»
				«each»=«JTPUtil.saveConvert(store.getString(each), false, true)»
			«ENDFOR»
		'''
	}
}