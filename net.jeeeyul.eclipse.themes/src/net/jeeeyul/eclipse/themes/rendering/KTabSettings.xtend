package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.graphics.Rectangle

class KTabSettings {
	extension SWTExtensions = SWTExtensions.INSTANCE

	@Property int borderRadius = 2
	@Property Rectangle margins = newInsets(0)
	@Property Rectangle paddings = newInsets(2)
	@Property int tabHeight = 22

	@Property HSB shadowColor = COLOR_DARK_GRAY.toHSB
	@Property HSB borderColor = COLOR_GRAY.toHSB
	@Property HSB innerBorderColor = COLOR_GRAY.toHSB
	@Property int shadowRadius = 0
	
	@Property HSB closeButtonColor = COLOR_GRAY.toHSB
	@Property HSB closeButtonHotColor = COLOR_DARK_RED.toHSB
	@Property HSB closeButtonActiveColor = COLOR_RED.toHSB
	@Property int closeButtonLineWidth = 3
	
	@Property int borderWidth = 1
	@Property int borderAlpha = 255
	
	@Property int tabSpacing = 2
	@Property int tabItemHorizontalSpacing = 4
}
