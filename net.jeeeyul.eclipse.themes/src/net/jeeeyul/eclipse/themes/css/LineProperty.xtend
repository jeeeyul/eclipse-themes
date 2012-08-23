package net.jeeeyul.eclipse.themes.css

import org.eclipse.swt.graphics.Color

class LineProperty {
	@Property boolean useDash
	@Property int offset
	@Property Color color

	def boolean match(LineProperty other){
		if(other == null) {
			return false
		}
		return this.useDash == other.useDash && this.offset == other.offset && this.color == other.color
	}
}


