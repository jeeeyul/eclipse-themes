package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.ToolBar
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.GC
import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*
import net.jeeeyul.eclipse.themes.ui.HSB

class ToolbarPage extends AbstractChromePage {
	extension SWTExtensions = new SWTExtensions
	ColorWell toolBarStartColorWell
	ColorWell toolBarEndColorWell
	Composite previewWrap
	ToolBar previewBar

	new(){
		super("Toolbar", SharedImages::TOOLBAR)
	}

	override create(Composite parent) {
		parent => [
			layout = GridLayout
			previewWrap = Composite(SWT::BORDER)[
				layout = FillLayout[
					marginWidth = 3
					marginHeight = 3
				]
				layoutData = FILL_HORIZONTAL
				onPaint = [
					renderPreview(it)
				]
				
				previewBar = ToolBar[
					onResize = [updateToolbarBackgroundImage()]
				
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::ECLIPSE)
					]
					
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::TOOLBAR)
					]
					
					ToolItem(SWT::^SEPARATOR)[]
					
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::ECLIPSE)
					]
					
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::TOOLBAR)
					]
					
					ToolItem(SWT::^SEPARATOR)[]
					
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::ECLIPSE)
					]
					
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::TOOLBAR)
					]
				]
			]
			
			Group[
				layout = GridLayout[
					numColumns = 3
				]
				text = "Gradient"
				layoutData = FILL_HORIZONTAL
				Label[
					text = "Start Color"
				]
				toolBarStartColorWell = ColorWell[
					onSelection = [updatePreview]
				]
				PushButton[
					text = "Change"
					onClick = [
						toolBarStartColorWell.showColorPicker()
					]
				]
				Label[
					text = "End Color"
				]
				toolBarEndColorWell = ColorWell[
					onSelection = [updatePreview]
				]
				PushButton[
					text = "Change"
					onClick = [
						toolBarEndColorWell.showColorPicker()
					]
				]
			]
		]
	}

	def void renderPreview(Event e) {
		var rect = previewWrap.clientArea
		var start = new Color(tabFolder.display, toolBarStartColorWell.selection.toRGB)
		var end = new Color(tabFolder.display, toolBarEndColorWell.selection.toRGB)
		e.gc.foreground = start
		e.gc.background = end
		e.gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true)
		start.safeDispose()
		end.safeDispose()
	}

	def private void updatePreview() {
		previewWrap.redraw()
		updateToolbarBackgroundImage()
	}
	
	def private void updateToolbarBackgroundImage(){
		var size = previewBar.size
		if(size.x <= 0 || size.y <=0){
			return
		}
		
		previewBar.backgroundImage.safeDispose()
		
		var image = new Image(tabFolder.display, size.x, size.y)
		var gc = new GC(image)
		
		var start = new Color(tabFolder.display, toolBarStartColorWell.selection.toRGB)
		var end = new Color(tabFolder.display, toolBarEndColorWell.selection.toRGB)
		
		gc.foreground = start
		gc.background = end
		gc.fillGradientRectangle(0, -3, size.x, size.y+6, true)
		
		start.dispose()
		end.dispose()
		gc.dispose()
		
		previewBar.backgroundImage = image
	}
	
	override dispose() {
		previewBar.background.safeDispose()
	}

	override load(IPreferenceStore store) {
		toolBarStartColorWell.selection = new HSB(
			store.getFloat(CHROME_TOOLBAR_START_HUE),
			store.getFloat(CHROME_TOOLBAR_START_SATURATION), 
			store.getFloat(CHROME_TOOLBAR_START_BRIGHTNESS)
		)
		
		toolBarEndColorWell.selection = new HSB(
			store.getFloat(CHROME_TOOLBAR_END_HUE),
			store.getFloat(CHROME_TOOLBAR_END_SATURATION), 
			store.getFloat(CHROME_TOOLBAR_END_BRIGHTNESS)
		)
	}

	override save(IPreferenceStore store) {
		store.setValue(CHROME_TOOLBAR_START_HUE, toolBarStartColorWell.selection.hue)
		store.setValue(CHROME_TOOLBAR_START_SATURATION, toolBarStartColorWell.selection.saturation)
		store.setValue(CHROME_TOOLBAR_START_BRIGHTNESS, toolBarStartColorWell.selection.brightness)
		
		store.setValue(CHROME_TOOLBAR_END_HUE, toolBarEndColorWell.selection.hue)
		store.setValue(CHROME_TOOLBAR_END_SATURATION, toolBarEndColorWell.selection.saturation)
		store.setValue(CHROME_TOOLBAR_END_BRIGHTNESS, toolBarEndColorWell.selection.brightness)
	}

	override setToDefault(IPreferenceStore store) {
		toolBarStartColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_TOOLBAR_START_HUE),
			store.getDefaultFloat(CHROME_TOOLBAR_START_SATURATION), 
			store.getDefaultFloat(CHROME_TOOLBAR_START_BRIGHTNESS)
		)
		
		toolBarEndColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_TOOLBAR_END_HUE),
			store.getDefaultFloat(CHROME_TOOLBAR_END_SATURATION), 
			store.getDefaultFloat(CHROME_TOOLBAR_END_BRIGHTNESS)
		)
	}

	def private void showColorPicker(ColorWell well) {
		var picker = new ColorPicker()
		var original = well.selection
		picker.selection = well.selection
		picker.continuosSelectionHandler = [
			well.selection = it
		]
		if(well.getData("lock-hue") == true) {
			picker.lockHue = true
		}
		if(picker.open() == IDialogConstants::OK_ID) {
			well.selection = picker.selection
		} else {
			well.selection = original
		}
	}
}