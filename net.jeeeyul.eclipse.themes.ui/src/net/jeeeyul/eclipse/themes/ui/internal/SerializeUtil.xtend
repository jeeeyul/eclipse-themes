package net.jeeeyul.eclipse.themes.ui.internal

import net.jeeeyul.swtend.ui.ColorStop
import net.jeeeyul.swtend.ui.Gradient
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle

class SerializeUtil {
	new() {
	}

	def dispatch String serialize(HSB hsb) {
		'''«hsb.hue», «hsb.saturation», «hsb.brightness»'''
	}

	def dispatch String serialize(Point point) {
		'''«point.x», «point.y»'''
	}

	def dispatch String serialize(Rectangle rect) {
		'''«rect.x», «rect.y», «rect.width», «rect.height»'''
	}

	def dispatch String serialize(Gradient grad) {
		grad.join("|")['''«serialize(it.color)», «it.percent»''']
	}

	def Gradient deserializeGradient(String exp) {
		try {
			val grad = new Gradient()
			var segments = exp.split("\\s*\\|\\s*")
			segments.forEach [
				var components = it.split("\\s*,\\s*").map[Float.parseFloat(it)]
				var color = new HSB(components.get(0), components.get(1), components.get(2))
				grad += new ColorStop(color, components.get(3).intValue)
			]
			return grad
		} catch(Exception e) {
			return null
		}
	}

	def HSB desrializeHSB(String exp) {
		try {
			var components = exp.split("\\s*,\\s*").map[Float.parseFloat(it)]
			return new HSB(components.get(0), components.get(1), components.get(2))

		} catch(Exception e) {
			return null
		}
	}

	def Rectangle desrializeRectangle(String exp) {
		try {
			var components = exp.split("\\s*,\\s*").map[Integer.parseInt(it)]
			new Rectangle(components.get(0), components.get(1), components.get(2), components.get(3))
		} catch(Exception e) {
			return null
		}
	}

	def Point desrializePoint(String exp) {
		try {
			var components = exp.split("\\s*,\\s*").map[Integer.parseInt(it)]
			new Point(components.get(0), components.get(1))
		} catch(Exception e) {
			return null
		}
	}

}
