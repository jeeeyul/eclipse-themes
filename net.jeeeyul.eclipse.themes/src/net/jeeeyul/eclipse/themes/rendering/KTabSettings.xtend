package net.jeeeyul.eclipse.themes.rendering

import org.eclipse.swt.graphics.Color
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.graphics.Point

class KTabSettings {
	extension SWTExtensions = SWTExtensions.INSTANCE

	@Property int borderRadius = 10
	@Property Rectangle margins = newInsets(0, 0, 3, 3)
	@Property Rectangle paddings = newInsets(2)
	@Property Point buttonSize = new Point(16, 16)

	@Property Color tabHeaderStartColor = COLOR_WHITE
	@Property Color tabHeaderEndColor = COLOR_GRAY
	@Property Color shadowColor = COLOR_DARK_GRAY
	@Property Color borderColor = COLOR_DARK_RED
	
	@Property int border = 1
}
