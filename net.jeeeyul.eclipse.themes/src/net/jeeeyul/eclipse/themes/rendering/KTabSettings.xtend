package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.graphics.Point

class KTabSettings {
	extension SWTExtensions = SWTExtensions.INSTANCE

	@Property int borderRadius = 2
	@Property Rectangle margins = newInsets(0)
	@Property Rectangle paddings = newInsets(2)
	@Property int tabHeight = 22


	@Property HSB shadowColor = COLOR_DARK_GRAY.toHSB
	@Property int shadowRadius = 0
	@Property Point shadowPosition = new Point(0, 0)
	
	@Property boolean showSelectedBorder = true
	@Property boolean showUnselectedBorder = true
	
	@Property HSB closeButtonColor = COLOR_GRAY.toHSB
	@Property HSB closeButtonHotColor = COLOR_DARK_RED.toHSB
	@Property HSB closeButtonActiveColor = COLOR_RED.toHSB
	@Property int closeButtonLineWidth = 3
	
	@Property int borderWidth = 1
	
	@Property int tabSpacing = 2
	@Property int tabItemHorizontalSpacing = 4
	
	@Property HSB borderColor = COLOR_GRAY.toHSB
	@Property HSB selectedBorderColor = COLOR_DARK_GRAY.toHSB
	
	@Property HSB unselectedBorderColor = COLOR_GRAY.toHSB
	@Property HSB[] unselectedBackgroundColors = #[COLOR_BLUE.toHSB, COLOR_RED.toHSB];
	@Property int[] unselectedBackgroundPercents = #[100];
}
