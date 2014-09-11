package net.jeeeyul.eclipse.themes.ui.shared

import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferenceInitializer
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.swt.SWTException
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.ImageData

class PresetIconGenerator {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def ImageDescriptor generatedDescriptor(IJTPreset preset) {
		if(Thread.currentThread != display.thread) {
			throw new SWTException("Invalid Thread Exception")
		}
		return ImageDescriptor.createFromImageData(generateData(preset))
	}

	def private ImageData generateData(IJTPreset preset) {
		var store = new JThemePreferenceStore(new PreferenceStore())
		new JTPreferenceInitializer().initializeDefaultPreset(store)
		for (keyObj : preset.properties.keySet) {
			var key = keyObj as String
			store.setValue(key, preset.properties.getProperty(key))
		}

		val image = newImage(16, 16)
		val bounds = newRectangle(0, 0, 16, 16).shrink(2)
		var gc = new GC(image)

		gc.fillGradientRectangle(bounds, store.getGradient(JTPConstants.ActivePartStack.HEADER_BACKGROUND_COLOR), true)
		var outline = newPath[
			addRectangle(bounds.getResized(-1, -1))
		]
		if(store.getBoolean(JTPConstants.ActivePartStack.BORDER_SHOW))
			gc.drawGradientPath(outline, store.getGradient(JTPConstants.ActivePartStack.BORDER_COLOR), true)

		var data = image.imageData
		outline.safeDispose()
		gc.safeDispose()
		image.safeDispose

		for (x : 0 ..< 16) {
			for (y : 0 ..< 16) {
				if(!bounds.contains(x, y)) {
					data.setAlpha(x, y, 0)
				} else {
					data.setAlpha(x, y, 255)
				}
			}
		}

		return data
	}
}
