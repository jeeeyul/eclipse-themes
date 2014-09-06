package net.jeeeyul.eclipse.themes.ui.store

import net.jeeeyul.eclipse.themes.JThemesCore
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants

class EPFGenerator {
	def String generate(){
		val store = JThemesCore.^default.preferenceStore
		val keys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET).filter[it != JTPConstants.Others.USER_CSS]
		return '''
			«FOR each : keys»
				«IF each == JTPConstants.Layout.TAB_HEIGHT»
					«each»=16
				«ELSE»
					«each»=«JTPUtil.saveConvert(store.getString(each), false, true)»
				«ENDIF»
			«ENDFOR»
		'''
	}
}