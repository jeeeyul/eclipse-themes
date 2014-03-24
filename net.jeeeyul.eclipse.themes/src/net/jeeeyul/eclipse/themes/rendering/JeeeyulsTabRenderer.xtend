package net.jeeeyul.eclipse.themes.rendering

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import net.jeeeyul.eclipse.themes.rendering.internal.EmptyClassHook
import net.jeeeyul.eclipse.themes.rendering.internal.ImageDataUtil
import net.jeeeyul.eclipse.themes.rendering.internal.JTabRendererHelper
import net.jeeeyul.eclipse.themes.rendering.internal.Shadow9PatchFactory
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import net.jeeeyul.swtend.ui.NinePatch
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Path
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Listener

/**
 * A new CTabFolder Renderer for Jeeeyul's eclipse themes 2.0
 * 
 * @since 2.0
 */
class JeeeyulsTabRenderer extends CTabFolderRenderer {
	extension JTabRendererHelper = new JTabRendererHelper
	extension SWTExtensions = SWTExtensions.INSTANCE

	@Property boolean debug = false

	JTabSettings settings = new JTabSettings(this)
	CTabFolder tabFolder
	NinePatch shadowNinePatch;
	EmptyClassHook emptyClassHook
	PropertyChangeListener settingsListener = [
		handleSettingChange(it)
	]

	Listener windowsRedrawHook = [
		parent.redraw
	]

	private def handleSettingChange(PropertyChangeEvent event) {
		switch (event.propertyName) {
			case "shadow-color": {
				shadowNinePatch.safeDispose()
			}
			case "shadow-radius": {
				shadowNinePatch.safeDispose()
			}
			case "border-radius": {
				shadowNinePatch.safeDispose()
			}
		}

		tabFolder.redraw();
	}

	new(CTabFolder parent) {
		super(parent)
		this.tabFolder = parent
		this.emptyClassHook = new EmptyClassHook(parent)
		settings.addPropertyChangeListener(settingsListener)

		if(isWindow) {
			tabFolder.addListener(SWT.Resize, windowsRedrawHook)
		}
	}

	override protected dispose() {
		shadowNinePatch.safeDispose()
		emptyClassHook.dispose()
		settings.removePropertyChangeListener(settingsListener)

		if(window && parent.alive) {
			parent.removeListener(SWT.Resize, windowsRedrawHook)
		}
		super.dispose()
	}

	override protected computeSize(int part, int state, GC gc, int wHint, int hHint) {
		switch (part) {
			case (part >= 0): {
				var item = parent.getItem(part)
				var width = 0
				var height = 0
				width = width + settings.tabItemPaddings.x

				if(item.image != null) {
					width = width + item.image.bounds.width
					width = width + settings.tabItemHorizontalSpacing
					height = item.image.bounds.height
				}

				if(item.text.trim.length > 0) {
					var textSize = item.text.computeTextExtent(#[item.font, parent.font].firstNotNull)
					width = width + textSize.x + 1
					height = Math.max(height, textSize.y)
				}

				if(parent.showClose || item.showClose) {
					if(state.hasFlags(SWT.SELECTED) || parent.showUnselectedClose) {
						var closeButtonSize = computeSize(PART_CLOSE_BUTTON, SWT.NONE, gc, SWT.DEFAULT, SWT.DEFAULT)
						width = width + settings.tabItemHorizontalSpacing + 1
						width = width + closeButtonSize.x
						height = Math.max(height, closeButtonSize.y)
					}
				}

				width = width + Math.max(settings.tabItemPaddings.width, 0)
				width = width + settings.tabSpacing

				return new Point(width, height)
			}
			case PART_HEADER: {
				var size = new Point(0, parent.tabHeight)
				if(parent.itemCount == 0) {
					size.y = Math.max(gc.textExtent("Default").y, size.y)
				} else {
					for (i : 0 ..< parent.itemCount) {
						var eachSize = computeSize(i, SWT.NONE, gc, wHint, hHint)
						size.y = Math.max(size.y, eachSize.y)
					}
				}
				size.y = size.y + 2
				return size
			}
			case PART_CLOSE_BUTTON: {
				return new Point(11, 11)
			}
			case PART_CHEVRON_BUTTON: {
				return new Point(20, 16)
			}
			default: {
				super.computeSize(part, state, gc, wHint, hHint)
			}
		}

	}

	override protected computeTrim(int part, int state, int x, int y, int width, int height) {
		var result = new Rectangle(x, y, width, height)
		switch (part) {
			case PART_BODY: {
				result.x = result.x - settings.margins.x - settings.paddings.x
				result.width = result.width + settings.margins.x + settings.paddings.x + settings.paddings.width + settings.margins.width

				if(tabFolder.onBottom) {
					throw new UnsupportedOperationException
				}

				result.y = result.y - tabFolder.tabHeight - settings.paddings.y - 2
				result.height = result.height + tabFolder.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + 2

				if(settings.borderColors != null) {
					result.x = result.x - 1
					result.width = result.width + 2
					result.height = result.height + 1
				}
			}
			case PART_BACKGROUND: {
				result.height = result.height + 10
			}
			case PART_BORDER: {
				result.x = result.x - settings.margins.x
				result.width = result.width + settings.margins.x + settings.margins.width + settings.borderRadius / 2
			}
			case PART_HEADER: {
				result.x = result.x - settings.margins.x
				result.width = result.width + settings.margins.x + settings.margins.width
			}
			case (part == PART_CHEVRON_BUTTON || part == PART_MIN_BUTTON || part == PART_MAX_BUTTON): {
				// Don't trim
			}
			case part >= 0: {
			}
			default: {
				result = super.computeTrim(part, state, x, y, width, height)
			}
		}
		return result

	}

	override protected draw(int part, int state, Rectangle bounds, GC gc) {
		try {
			doDraw(part, state, bounds, gc)
		} catch(Exception e) {
			e.printStackTrace
		}
	}

	def private doDraw(int part, int state, Rectangle bounds, GC gc) {
		gc.advanced = true
		gc.antialias = SWT.ON
		gc.interpolation = SWT.HIGH
		gc.lineJoin = SWT.JOIN_ROUND
		gc.alpha = 255
		gc.fillRule = SWT.FILL_WINDING
		gc.background = tabFolder.background
		gc.foreground = tabFolder.foreground
		gc.lineWidth = 1
		gc.lineStyle = SWT.LINE_SOLID

		switch (part) {
			case PART_HEADER: {
				drawTabHeader(part, state, bounds, gc)
				updateChevronImage()
			}
			case PART_CLOSE_BUTTON: {
				drawCloseButton(part, state, bounds, gc)
			}
			case PART_BORDER: {
			}
			case PART_BODY: {
				drawTabBody(part, state, bounds, gc)
			}
			case PART_CHEVRON_BUTTON: {
				drawChevronButton(part, state, bounds, gc)
			}
			case part >= 0: {
				drawTabItem(part, state, bounds, gc)
			}
			default:
				super.draw(part, state, bounds, gc)
		}
	}

	protected def drawChevronButton(int part, int state, Rectangle rectangle, GC gc) {
	}

	private def updateChevronImage() {
		val chevronSize = parent.chevron.size
		if(chevronSize.x == 0 || chevronSize.y == 0) {
			return
		}

		var HSB lastColor = parent.chevron.getData("last-render-color") as HSB
		var Integer lastCount = parent.chevron.getData("last-render-count") as Integer
		val count = Math.min(parent.items.filter[it.showing == false].size, 99)
		if(lastColor == settings.getChevronColor && lastCount == count) {
			return;
		}

		var size = computeSize(PART_CHEVRON_BUTTON, SWT.NONE, null, SWT.DEFAULT, SWT.DEFAULT)
		parent.chevron.backgroundImage = null

		val mask = new Image(display, size.x, size.y)
		val mgc = new GC(mask)

		var path = newTemporaryPath[
			moveTo(0, 0)
			lineTo(3, 3)
			lineTo(0, 6)
			moveTo(3, 0)
			lineTo(6, 3)
			lineTo(3, 6)
		]
		mgc.background = COLOR_BLACK
		mgc.fill(mask.bounds)

		mgc.foreground = COLOR_WHITE
		mgc.draw(path)

		var fd = parent.font.fontData.head
		fd.setHeight(72 * 10 / display.DPI.y)
		mgc.font = new Font(display, fd).autoDispose
		mgc.drawString(count.toString, 6, 5, true)
		mgc.dispose()

		var data = ImageDataUtil.convertBrightnessToAlpha(mask.imageData, settings.getChevronColor)
		mask.dispose();

		var itemImage = new Image(display, data).shouldDisposeWith(parent)
		var toolbarImg = new Image(display, chevronSize.x, chevronSize.y).shouldDisposeWith(parent)
		var tgc = new GC(toolbarImg)

		tgc.fillGradientRectangle(new Rectangle(0, -parent.chevron.bounds.y, chevronSize.x, parent.tabHeight + 3), parent.gradientColor, parent.gradientPercents, true)
		tgc.dispose();

		parent.chevron.backgroundImage.safeDispose()
		parent.chevron.backgroundImage = null
		parent.chevron.backgroundImage = toolbarImg

		parent.chevronItem.image.safeDispose()
		parent.chevronItem.image = null
		parent.chevronItem.image = itemImage

		parent.chevron.setData("last-render-color", settings.chevronColor)
		parent.chevron.setData("last-render-count", count)
	}

	private def getHeaderArea() {
		var headerArea = if(tabFolder.onTop) {
				new Rectangle(settings.margins.x, 0, tabFolder.size.x - settings.margins.x - settings.margins.width, tabFolder.tabHeight + 2)
			} else {
				new Rectangle(0, tabFolder.size.y, tabFolder.size.x, tabFolder.size.y) => [
					shrink(settings.margins.x, 0, settings.margins.width, 0)
					height = tabFolder.tabHeight
					translate(0, -tabFolder.tabHeight - settings.margins.height - 2)
					resize(0, 2)
				]
			}
		return headerArea;
	}

	protected def drawTabHeader(int part, int state, Rectangle bounds, GC gc) {
		if(parent.onBottom) {
			throw new UnsupportedOperationException
		}

		val fillArea = getHeaderArea()
		if(settings.borderColors != null)
			fillArea.shrink(1, 0)
		if(tabFolder.gradientColor != null) {
			gc.fillGradientRoundRectangle(fillArea, settings.borderRadius, CORNER_TOP, tabFolder.gradientColor, tabFolder.gradientPercents, true)
		} else {
			gc.background = tabFolder.background
			gc.fillRoundRectangle(fillArea, settings.borderRadius, CORNER_TOP)
		}

		val outlineArea = headerArea.getResized(-1, 0)
		if(settings.borderColors != null) {
			gc.foreground = settings.borderColors.head.toAutoDisposeColor
			var path = newTemporaryPath[
				if(parent.simple) {
					moveTo(outlineArea.bottomLeft)
					lineTo(outlineArea.topLeft.getTranslated(0, settings.borderRadius))
					addArc(newRectangleWithSize(settings.borderRadius * 2).relocateTopLeftWith(outlineArea), 180, -90)
				} else {
					moveTo(outlineArea.topLeft.getTranslated(settings.borderRadius, 0))
				}
				lineTo(outlineArea.topRight.getTranslated(-settings.borderRadius, 0))
				addArc(newRectangleWithSize(settings.borderRadius * 2).relocateTopRightWith(outlineArea), 90, -90)
				lineTo(outlineArea.bottomRight)
			]
			gc.drawGradientPath(path, settings.borderColors.toAutoDisposeColors, settings.borderPercents, true)
		}
	}

	protected def drawTabBody(int part, int state, Rectangle bounds, GC gc) {
		if(parent.onBottom) {
			throw new UnsupportedOperationException
		}

		// Fill Background
		gc.background = tabFolder.parent.background
		gc.fill(bounds)

		if(settings.shadowColor != null) {
			drawShadow(part, state, bounds, gc)
		}

		gc.background = if(parent.itemCount > 0)
			#[tabFolder.selectionGradientColor?.last, tabFolder.selectionBackground].findFirst[it != null]
		else
			#[tabFolder.gradientColor?.last, tabFolder.background].findFirst[it != null]

		var fillArea = tabArea
		fillArea.setTop(headerArea.bottom.y)

		gc.fillRoundRectangle(fillArea, settings.borderRadius, CORNER_BOTTOM)

		fillArea.shrink(settings.paddings)
		if(settings.borderColors != null) {
			fillArea.shrink(1, 0, 1, 1)
		}
		gc.background = parent.background
		gc.fill(fillArea)

		// Draw Border
		if(settings.borderWidth > 0 && settings.borderColors != null && settings.borderPercents != null) {
			val offset = tabArea.getResized(-1, -1)
			gc.lineWidth = settings.borderWidth
			val bodyPath = newTemporaryPath[
				var corner = newRectangleWithSize(settings.borderRadius * 2)
				moveTo(headerArea.bottomLeft)
				corner.relocateBottomLeftWith(offset)
				lineTo(corner.left)
				addArc(corner, 180, 90)
				corner.relocateBottomRightWith(offset)
				lineTo(corner.bottom)
				addArc(corner, 270, 90)
				lineTo(headerArea.bottomRight.getTranslated(-1, 0))
			]
			gc.foreground = settings.borderColors.last.toAutoReleaseColor

			// draw twice reduce mip-map problem
			gc.draw(bodyPath)
			gc.draw(bodyPath)
		}
	}

	protected def drawCloseButton(int part, int state, Rectangle bounds, GC gc) {
		if(debug) {
			gc.background = COLOR_MAGENTA
			gc.fill(bounds)
		}
		val box = bounds.getShrinked(2)
		var path = newTemporaryPath[
			moveTo(box.topLeft)
			lineTo(box.bottomRight)
			moveTo(box.topRight)
			lineTo(box.bottomLeft)
		]

		var color = switch (state) {
			case state.hasFlags(SWT.HOT): #[settings.closeButtonHotColor, settings.closeButtonColor, HSB.BLACK].firstNotNull
			case state.hasFlags(SWT.SELECTED): #[settings.closeButtonActiveColor, settings.closeButtonColor, HSB.BLACK].firstNotNull
			default: #[settings.closeButtonColor, HSB.BLACK].firstNotNull
		}
		gc.lineWidth = Math.max(settings.closeButtonLineWidth, 1)
		gc.foreground = color.toAutoReleaseColor
		gc.draw(path)

	}

	protected def drawTabItem(int part, int state, Rectangle bounds, GC gc) {
		val item = tabFolder.getItem(part)

		val itemBounds = if(tabFolder.onBottom)
				throw new UnsupportedOperationException
			else
				item.bounds.getResized(-Math.max(settings.tabSpacing, 0), 0)

		if(settings.tabSpacing == -1) {
			itemBounds.resize(1, 0)
		}

		// Fill tab item bounds
		drawTabItemBackground(part, state, itemBounds, gc)

		// Draw Icon
		var iconArea = if(item.image != null)
				item.image.bounds.relocateLeftWith(item.bounds).translate(settings.tabItemPaddings.x, 0)
			else
				new Rectangle(itemBounds.x + settings.tabItemPaddings.x, 0, 0, itemBounds.height)

		if(item.image != null) {
			gc.drawImage(item.image, iconArea.topLeft)
		}

		// Draw Close Button
		if((tabFolder.showClose || item.showClose) && item.closeRect.width > 0) {
			item.closeRect.x = item.bounds.right.x - item.closeRect.width - 4 - settings.tabItemPaddings.width
			item.closeRect.translate(-Math.max(settings.tabSpacing, 0) + 3, 0)

			if(state.hasFlags(SWT.SELECTED) || state.hasFlags(SWT.HOT)) {
				gc.withClip(item.closeRect) [
					draw(PART_CLOSE_BUTTON, item.closeImageState, item.closeRect, gc)
				]
			}
		}
		gc.lineWidth = 1

		// Draw Text
		gc.font = if(item.font != null) {
			item.font
		} else {
			tabFolder.font
		}

		val textSize = item.text.computeTextExtent(gc.font);
		val textArea = newRectangleWithSize(textSize).relocateLeftWith(iconArea.right)
		if(item.image != null)
			textArea.translate(settings.tabItemHorizontalSpacing, 0)

		if((item.showClose || tabFolder.showClose) && item.closeRect != null && item.closeRect.width > 0) {
			textArea.setRight(item.closeRect.x - settings.tabItemHorizontalSpacing);
		} else {
			textArea.setRight(itemBounds.right.x - settings.tabItemPaddings.width)
		}

		if(debug) {
			gc.foreground = COLOR_MAGENTA
			gc.lineWidth = 1
			gc.draw(textArea.getResized(-1, -1))
			gc.drawLine(textArea.left, textArea.left.getTranslated(textSize.x, 0))
		}

		gc.withClip(textArea) [
			var text = if(textSize.x > textArea.width)
					gc.shortenText(item.text, textArea.width, "...")
				else
					item.text
			val textShadowColor = settings.getTextShadowColorFor(state)
			val textShadowPosition = settings.getTextShadowPositionFor(state)
			if(textShadowColor != null && textShadowPosition != null && !textShadowPosition.empty) {
				var shadowPosition = settings.getTextShadowPositionFor(state)
				gc.foreground = settings.getTextShadowColorFor(state).toAutoReleaseColor
				gc.drawString(text, textArea.topLeft.getTranslated(shadowPosition))
			}
			gc.foreground = settings.getTextColorFor(state).toAutoReleaseColor
			gc.drawString(text, textArea.topLeft)
		]

		// Draw Border and Keyline
		drawTabItemBorder(part, state, itemBounds, gc)

		if(settings.borderColors != null) {
			if(parent.firstVisibleItem == item) {
				var path = newTemporaryPath[
					moveTo(itemBounds.topRight.getTranslated(Math.max(settings.tabSpacing, 0) + 1, 0))
					lineTo(itemBounds.topLeft.getTranslated(settings.borderRadius, 0))
					addArc(newRectangle(itemBounds.x, itemBounds.y, settings.borderRadius * 2, settings.borderRadius * 2), 90, 90)
					lineTo(itemBounds.bottomLeft.getTranslated(0, 1))
				]
				gc.drawGradientPath(path, settings.borderColors.toAutoDisposeColors, settings.borderPercents, true)
			} else {
				gc.foreground = settings.borderColors.head.toAutoDisposeColor
				gc.drawLine(itemBounds.topLeft.getTranslated(-1, 0), itemBounds.topRight.getTranslated(Math.max(settings.tabSpacing, 0) + 1, 0))
			}
		}

	}

	protected def void drawTabItemBorder(int part, int state, Rectangle bounds, GC gc) {
		if(settings.getBorderColorsFor(state) == null || settings.getBorderPercentsFor(state) == null) {
			return;
		}

		val itemOutlineBounds = bounds.getResized(-1, 0)
		val CTabItem item = tabFolder.getItem(part)
		val outlineOffset = itemOutlineBounds.shrink(settings.borderWidth / 2)
		var Path outline = null;

		if(tabFolder.onBottom) {
			throw new UnsupportedOperationException
		}
		outline = newTemporaryPath[
			var keyLineY = item.bounds.bottom.y - 1
			if(settings.borderRadius > 0) {
				var corner = newRectangle(outlineOffset.topLeft, new Point(settings.borderRadius * 2, settings.borderRadius * 2))
				corner.relocateTopRightWith(outlineOffset)

				if(state.hasFlags(SWT.SELECTED)) {
					moveTo(tabFolder.size.x - settings.margins.width - settings.borderWidth, keyLineY)
					lineTo(itemOutlineBounds.bottomRight.x, keyLineY)
				} else {
					moveTo(outlineOffset.bottomRight.x, keyLineY)
				}
				lineTo(corner.right)
				addArc(corner, 0, 90)
				corner.relocateTopLeftWith(outlineOffset)
				lineTo(corner.top)

				if(settings.borderColors == null || settings.borderColors != null && item != parent.firstVisibleItem) {
					addArc(corner, 90, 90)
					lineTo(outlineOffset.x, keyLineY)
					if(state.hasFlags(SWT.SELECTED)) {
						var left = new Point(settings.margins.x, keyLineY)
						if(settings.borderColors != null) {
							left.translate(1, 0)
						}
						lineTo(left)
					}
				}
			} else {
				if(state.hasFlags(SWT.SELECTED)) {
					moveTo(tabFolder.size.x - settings.margins.width - settings.borderWidth, keyLineY)
					lineTo(itemOutlineBounds.bottomRight.x, keyLineY)
				} else {
					moveTo(outlineOffset.bottomRight.x, keyLineY)
				}
				lineTo(itemOutlineBounds.topRight)
				lineTo(itemOutlineBounds.topLeft)

				if(settings.borderColors == null || settings.borderColors != null && item != parent.firstVisibleItem) {
					lineTo(itemOutlineBounds.bottomLeft.x, keyLineY)

					if(state.hasFlags(SWT.SELECTED)) {
						var left = new Point(settings.margins.x, keyLineY)
						if(settings.borderColors != null) {
							left.translate(settings.borderWidth, 0)
						}
						lineTo(left)
					}
				}
			}
		]

		gc.lineWidth = settings.borderWidth
		gc.drawGradientPath(outline, settings.getBorderColorsFor(state).toAutoReleaseColor, settings.getBorderPercentsFor(state), true)
	}

	protected def drawTabItemBackground(int part, int state, Rectangle bounds, GC gc) {
		val itemBounds = bounds.copy
		if(settings.getBorderColorsFor(state) != null)
			itemBounds.shrink(1, 0)

		var colors = settings.getItemFillFor(state)
		if(colors != null)
			gc.fillGradientRoundRectangle(itemBounds, settings.borderRadius, CORNER_TOP, colors, settings.getItemFillPercentsFor(state), true)
	}

	def protected drawShadow(int part, int state, Rectangle bounds, GC gc) {
		gc.withClip(bounds) [
			getShadow().fill(gc, tabArea.getExpanded(settings.shadowRadius).translate(settings.shadowPosition))
		]
	}

	def protected tabArea() {
		newRectangle.setSize(tabFolder.size).shrink(settings.margins.x, 0, settings.margins.width, settings.margins.height)
	}

	def protected getShadow() {
		if(shadowNinePatch == null || shadowNinePatch.disposed) {
			shadowNinePatch = Shadow9PatchFactory.createShadowPatch(settings.shadowColor.toRGB, settings.borderRadius + 3, settings.shadowRadius);
		}
		return shadowNinePatch;
	}

	def getSettings() {
		return this.settings;
	}

	def CTabFolder getTabFolder() {
		return this.tabFolder
	}
}
