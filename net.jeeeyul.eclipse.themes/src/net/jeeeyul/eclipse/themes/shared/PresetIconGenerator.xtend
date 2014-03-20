package net.jeeeyul.eclipse.themes.shared

import net.jeeeyul.eclipse.themes.preference.JTPConstants
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferenceInitializer
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset
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
		new JTPreferenceInitializer().initializeDefault(store)
		for (keyObj : preset.properties.keySet) {
			var key = keyObj as String
			store.setValue(key, preset.properties.getProperty(key))
		}

		val image = newImage(16, 16)
		val bounds = newRectangle(0, 0, 16, 16).shrink(2)
		var gc = new GC(image)

		gc.fillGradientRectangle(bounds, store.getGradient(JTPConstants.ActivePartStack.BACKGROUND_COLOR), true)
		var outline = newPath[
			addRectangle(bounds.getResized(-1, -1))
		]
		gc.drawGradientPath(outline, store.getGradient(JTPConstants.ActivePartStack.BORDER_COLOR), true)

		var data = image.imageData
		outline.safeDispose()
		gc.safeDispose()
		image.safeDispose
		

		var x = 0
		while(x < 16) {
			var y = 0
			while(y < 16) {
				if(!bounds.contains(x, y)){
					data.setAlpha(x, y, 0)
				}else{
					data.setAlpha(x, y, 255)
				}
				y = y + 1
			}
			x = x + 1
		}

		return data
	}
}
