package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.ColorPicker
import net.jeeeyul.eclipse.themes.ui.ColorWell
import net.jeeeyul.eclipse.themes.ui.HSB
import net.jeeeyul.eclipse.themes.ui.SWTExtensions
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.jface.preference.IPreferenceStore
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.ToolBar

import static net.jeeeyul.eclipse.themes.preference.ChromeConstants.*

class ToolbarPage extends ChromePage {
	extension SWTExtensions = new SWTExtensions
	extension ChromePreferenceExtensions = new ChromePreferenceExtensions
	
	ColorWell toolBarStartColorWell
	ColorWell toolBarEndColorWell
	ColorWell perspectiveStartColorWell
	ColorWell perspectiveEndColorWell
	ColorWell perspectiveOutlineColorWell
	Button useWBColorAsPerspectiveColorButton;
	Composite previewWrap
	ToolBar previewBar
	ToolBar perspectiveBar
	
	Button useWBColorAsStatusBarColorButton;
	ColorWell statusBarBackgroundColorWell;
	
	Button useStatusBarOutlineButton;
	ColorWell statusBarOutlineColorWell;
	

	new(){
		super("Toolbar", SharedImages::TOOLBAR)
	}

	override create(Composite parent) {
		parent => [
			layout = GridLayout
			
			Label[
				text = "Configurations for main tool bar and Perspective Switcher"
			]
			
			previewWrap = Composite(SWT::DOUBLE_BUFFERED || SWT::BORDER)[
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					makeColumnsEqualWidth = false
					numColumns = 2
					marginWidth = 3
					marginHeight = 3
				]
				onPaint = [
					renderPreview(it)
				]
				previewBar = ToolBar(SWT::FLAT || SWT::RIGHT)[
					layoutData = FILL_HORIZONTAL
					onResize = [
						updateToolbarBackgroundImage()
					]
					ToolItem(SWT::DROP_DOWN)[
						it.image = SharedImages::getImage(SharedImages::ECLIPSE)
					]
					ToolItem(SWT::^SEPARATOR)[]
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::ECLIPSE)
					]
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::TOOLBAR)
					]
				]
				perspectiveBar = ToolBar(SWT::RIGHT || SWT::FLAT)[
					onResize = [updatePerspectiveBarBackgroundImage()]
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::OPEN_PERSPECTIVE)
					]
					ToolItem(SWT::^SEPARATOR)[]
					ToolItem[
						it.image = SharedImages::getImage(SharedImages::PLUGIN)
						it.text = "Plug-in Development"
					]
				]
			]
			Group[
				layout = GridLayout[
					numColumns = 3
				]
				text = "Main Tool Bar"
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
			Group[
				layout = GridLayout[
					numColumns = 4
				]
				text = "Perspective Switcher"
				layoutData = FILL_HORIZONTAL
				Label[
					text = "Start Color"
				]
				perspectiveStartColorWell = ColorWell[
					onSelection = [updatePreview]
				]
				PushButton[
					text = "Change"
					layoutData = GridData[
						horizontalSpan = 2
					]
					onClick = [
						perspectiveStartColorWell.showColorPicker()
					]
				]
				Label[
					text = "End Color"
				]
				perspectiveEndColorWell = ColorWell[
					onSelection = [updatePreview]
				]
				PushButton[
					text = "Change"
					onClick = [
						perspectiveEndColorWell.showColorPicker()
					]
				]
				useWBColorAsPerspectiveColorButton = Checkbox[
					text = "Use Window Background Color"
					onSelection = [
						updateAutoColors()
						updateEnablement()
					]
				]
				Label[
					text = "Outline Color"
				]
				perspectiveOutlineColorWell = ColorWell[
					onSelection = [updatePreview]
				]
				PushButton[
					layoutData = GridData[
						horizontalSpan = 2
					]
					text = "Change"
					onClick = [
						perspectiveOutlineColorWell.showColorPicker()
					]
				]
			]// Group
			
			Group[
				text = "Status Bar"
				layoutData = FILL_HORIZONTAL
				layout = GridLayout[
					numColumns = 4
				]
				
				
				Label[
					text = "Background:"
				]
				
				statusBarBackgroundColorWell = ColorWell[
					onSelection = [
						updatePreview()
					]
				]
				
				PushButton[
					text = "Change"
					onClick = [
						statusBarBackgroundColorWell.showColorPicker()
					]
				]
				
				useWBColorAsStatusBarColorButton = Checkbox[
					text = "Use Window Background Color"
					onSelection = [
						updateEnablement()
					]
				]
				
				Label[
					text = "Outline:"
				]
				
				statusBarOutlineColorWell = ColorWell[
					updatePreview()
				]
				
				PushButton[
					text = "Change"
					onClick = [
						statusBarOutlineColorWell.showColorPicker()
					]
				]
				
				useStatusBarOutlineButton = Checkbox[
					text = "Show Outline"
					onSelection = [
						updateEnablement()
					]
				]
				
			] // End of Group
		]
	}

	def updateAutoColors() {
		if(useWBColorAsPerspectiveColorButton.selection) {
			perspectiveEndColorWell.selection = getCompanionPage(typeof(GeneralPage)).windowBackgroundColorWell.selection
		}
	}

	def void updateEnablement() {
		perspectiveEndColorWell.next.enabled = !useWBColorAsPerspectiveColorButton.selection
		statusBarBackgroundColorWell.next.enabled = !useWBColorAsStatusBarColorButton.selection
	}

	def void renderPreview(Event e) {
		var rect = previewWrap.clientArea
		rect.width = previewBar.bounds.width + previewBar.bounds.x
		var start = new Color(getTabFolder.display, toolBarStartColorWell.selection.toRGB)
		var end = new Color(getTabFolder.display, toolBarEndColorWell.selection.toRGB)
		var outline = new Color(getTabFolder.display, perspectiveOutlineColorWell.selection.toRGB)
		e.gc.foreground = start
		e.gc.background = end
		e.gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true)
		e.gc.foreground = outline
		e.gc.drawLine(rect.x, rect.y + rect.height-1, rect.x + rect.width,  rect.y + rect.height-1);
		e.gc.drawLine(rect.x + rect.width,  rect.y, rect.x + rect.width,  rect.y + rect.height-1);
		start.safeDispose()
		end.safeDispose()
		outline.safeDispose()
		rect = previewWrap.clientArea
		rect.width = perspectiveBar.bounds.width + 7
		rect.x = perspectiveBar.bounds.x - 4
		start = new Color(getTabFolder.display, perspectiveStartColorWell.selection.toRGB)
		end = new Color(getTabFolder.display, perspectiveEndColorWell.selection.toRGB)
		e.gc.foreground = start
		e.gc.background = end
		e.gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true)
		start.safeDispose()
		end.safeDispose()
	}

	def private void updatePreview() {
		previewWrap.redraw()
		updateToolbarBackgroundImage()
		updatePerspectiveBarBackgroundImage()
	}

	def private void updateToolbarBackgroundImage(){
		var size = previewBar.size
		if(size.x <= 0 || size.y <=0) {
			return
		}
		previewBar.backgroundImage.safeDispose()
		var image = new Image(getTabFolder.display, 20, size.y)
		var gc = new GC(image)
		var start = new Color(getTabFolder.display, toolBarStartColorWell.selection.toRGB)
		var end = new Color(getTabFolder.display, toolBarEndColorWell.selection.toRGB)
		gc.foreground = start
		gc.background = end
		gc.fillGradientRectangle(0, -3, size.x, size.y+6, true)
		start.dispose()
		end.dispose()
		gc.dispose()
		previewBar.backgroundImage = image
	}

	def private void updatePerspectiveBarBackgroundImage(){
		var size = previewBar.size
		if(size.y <=0) {
			return
		}
		perspectiveBar.backgroundImage.safeDispose()
		var image = new Image(getTabFolder.display, 20, size.y)
		var gc = new GC(image)
		var start = new Color(getTabFolder.display, perspectiveStartColorWell.selection.toRGB)
		var end = new Color(getTabFolder.display, perspectiveEndColorWell.selection.toRGB)
		gc.foreground = start
		gc.background = end
		gc.fillGradientRectangle(0, -3, size.x, size.y+6, true)
		start.dispose()
		end.dispose()
		gc.dispose()
		perspectiveBar.backgroundImage = image
	}

	override dispose() {
		previewBar.backgroundImage.safeDispose()
		perspectiveBar.backgroundImage.safeDispose()
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
		perspectiveStartColorWell.selection = new HSB(
			store.getFloat(CHROME_PERSPECTIVE_START_HUE),
			store.getFloat(CHROME_PERSPECTIVE_START_SATURATION), 
			store.getFloat(CHROME_PERSPECTIVE_START_BRIGHTNESS)
		)
		perspectiveEndColorWell.selection = new HSB(
			store.getFloat(CHROME_PERSPECTIVE_END_HUE),
			store.getFloat(CHROME_PERSPECTIVE_END_SATURATION), 
			store.getFloat(CHROME_PERSPECTIVE_END_BRIGHTNESS)
		)
		perspectiveOutlineColorWell.selection = new HSB(
			store.getFloat(CHROME_PERSPECTIVE_OUTLINE_HUE),
			store.getFloat(CHROME_PERSPECTIVE_OUTLINE_SATURATION), 
			store.getFloat(CHROME_PERSPECTIVE_OUTLINE_BRIGHTNESS)
		)
		useWBColorAsPerspectiveColorButton.selection = store.getBoolean(CHROME_USE_WINDOW_BACKGROUND_COLOR_AS_PERSPECTIVE_END_COLOR)
		
		useWBColorAsStatusBarColorButton.selection = store.getBoolean(CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND)
		statusBarBackgroundColorWell.selection = store.getHSB(CHROME_STATUS_BAR_BACKGROUND_COLOR)
		useStatusBarOutlineButton.selection = store.getBoolean(CHROME_USE_STATUS_BAR_OUTLINE)
		statusBarOutlineColorWell.selection = store.getHSB(CHROME_STATUS_BAR_OUTLINE_COLOR)
		
		updateAutoColors()
		updateEnablement()
		updatePreview()
	}

	override save(IPreferenceStore store) {
		store.setValue(CHROME_TOOLBAR_START_HUE, toolBarStartColorWell.selection.hue)
		store.setValue(CHROME_TOOLBAR_START_SATURATION, toolBarStartColorWell.selection.saturation)
		store.setValue(CHROME_TOOLBAR_START_BRIGHTNESS, toolBarStartColorWell.selection.brightness)
		store.setValue(CHROME_TOOLBAR_END_HUE, toolBarEndColorWell.selection.hue)
		store.setValue(CHROME_TOOLBAR_END_SATURATION, toolBarEndColorWell.selection.saturation)
		store.setValue(CHROME_TOOLBAR_END_BRIGHTNESS, toolBarEndColorWell.selection.brightness)
		store.setValue(CHROME_PERSPECTIVE_START_HUE, perspectiveStartColorWell.selection.hue)
		store.setValue(CHROME_PERSPECTIVE_START_SATURATION, perspectiveStartColorWell.selection.saturation)
		store.setValue(CHROME_PERSPECTIVE_START_BRIGHTNESS, perspectiveStartColorWell.selection.brightness)
		store.setValue(CHROME_PERSPECTIVE_END_HUE, perspectiveEndColorWell.selection.hue)
		store.setValue(CHROME_PERSPECTIVE_END_SATURATION, perspectiveEndColorWell.selection.saturation)
		store.setValue(CHROME_PERSPECTIVE_END_BRIGHTNESS, perspectiveEndColorWell.selection.brightness)
		store.setValue(CHROME_PERSPECTIVE_OUTLINE_HUE, perspectiveOutlineColorWell.selection.hue)
		store.setValue(CHROME_PERSPECTIVE_OUTLINE_SATURATION, perspectiveOutlineColorWell.selection.saturation)
		store.setValue(CHROME_PERSPECTIVE_OUTLINE_BRIGHTNESS, perspectiveOutlineColorWell.selection.brightness)
		
		store.setValue(CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND, useWBColorAsStatusBarColorButton.selection)
		store.setValue(CHROME_STATUS_BAR_BACKGROUND_COLOR, statusBarBackgroundColorWell.selection)
		store.setValue(CHROME_USE_STATUS_BAR_OUTLINE, useStatusBarOutlineButton.selection)
		store.setValue(CHROME_STATUS_BAR_OUTLINE_COLOR, statusBarOutlineColorWell.selection)
		
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
		perspectiveStartColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_PERSPECTIVE_START_HUE),
			store.getDefaultFloat(CHROME_PERSPECTIVE_START_SATURATION), 
			store.getDefaultFloat(CHROME_PERSPECTIVE_START_BRIGHTNESS)
		)
		perspectiveEndColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_PERSPECTIVE_END_HUE),
			store.getDefaultFloat(CHROME_PERSPECTIVE_END_SATURATION), 
			store.getDefaultFloat(CHROME_PERSPECTIVE_END_BRIGHTNESS)
		)
		perspectiveOutlineColorWell.selection = new HSB(
			store.getDefaultFloat(CHROME_PERSPECTIVE_OUTLINE_HUE),
			store.getDefaultFloat(CHROME_PERSPECTIVE_OUTLINE_SATURATION), 
			store.getDefaultFloat(CHROME_PERSPECTIVE_OUTLINE_BRIGHTNESS)
		)
		useWBColorAsPerspectiveColorButton.selection = store.getDefaultBoolean(CHROME_USE_WINDOW_BACKGROUND_COLOR_AS_PERSPECTIVE_END_COLOR)
		
		useWBColorAsStatusBarColorButton.selection = store.getDefaultBoolean(CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND)
		statusBarBackgroundColorWell.selection = store.getDefaultHSB(CHROME_STATUS_BAR_BACKGROUND_COLOR)
		useStatusBarOutlineButton.selection = store.getDefaultBoolean(CHROME_USE_STATUS_BAR_OUTLINE)
		statusBarOutlineColorWell.selection = store.getDefaultHSB(CHROME_STATUS_BAR_OUTLINE_COLOR)
		
		updateAutoColors()
		updateEnablement()
		updatePreview()
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