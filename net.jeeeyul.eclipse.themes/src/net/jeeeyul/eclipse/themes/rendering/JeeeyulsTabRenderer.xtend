package net.jeeeyul.eclipse.themes.rendering

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import net.jeeeyul.eclipse.themes.rendering.internal.ShadowGenerator
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import net.jeeeyul.swtend.ui.NinePatch
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Path
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle

class JeeeyulsTabRenderer extends CTabFolderRenderer {
	extension JTabRendererHelper = new JTabRendererHelper
	extension SWTExtensions = SWTExtensions.INSTANCE

	JTabSettings settings = new JTabSettings(this)
	CTabFolder tabFolder
	NinePatch shadowNinePatch;
	PropertyChangeListener settingsListener = [
		handleSettingChange(it)
	]

	def handleSettingChange(PropertyChangeEvent event) {
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
		settings.addPropertyChangeListener(settingsListener)
	}

	override protected dispose() {
		shadowNinePatch.safeDispose()
		settings.removePropertyChangeListener(settingsListener)
		super.dispose()
	}

	override protected computeSize(int part, int state, GC gc, int wHint, int hHint) {
		switch (part) {
			case (part >= 0): {
				var result = super.computeSize(part, state, gc, wHint, hHint)
				result.x = result.x + Math.max(settings.tabSpacing, 0)
				return result
			}
			case PART_CLOSE_BUTTON: {
				return new Point(10, 10)
			}
			default: {
				super.computeSize(part, state, gc, wHint, hHint)
			}
		}

	}

	override protected computeTrim(int part, int state, int x, int y, int width, int height) {
		var result = new Rectangle(x, y, width, height)
		switch (part) {
			case PART_BORDER: {
				result.x = result.x - settings.margins.x
				result.width = result.width + settings.margins.x + settings.margins.width + settings.borderRadius / 2
				result.height = result.height + settings.margins.height
			}
			case PART_HEADER: {
				result.x = result.x - settings.margins.x
				result.width = result.width + settings.margins.x + settings.margins.width
			}
			case PART_BODY: {
				result.x = result.x - settings.margins.x - settings.paddings.x - settings.borderWidth
				result.width = result.width + settings.margins.x + settings.paddings.x + settings.borderWidth * 2 + settings.paddings.width + settings.margins.width

				if(tabFolder.onBottom) {
					result.y = result.y - settings.paddings.y - settings.borderWidth
					result.height = result.height + tabFolder.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + settings.borderWidth * 2 + 2
				} else {
					result.y = result.y - tabFolder.tabHeight - settings.paddings.y - settings.borderWidth - 1
					result.height = result.height + tabFolder.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + settings.borderWidth * 2 + 1
				}
			}
			case (part >= 0): {
				var item = tabFolder.getItem(part)
				if(item == tabFolder.lastVisibleItem) {
					result.width = result.width + 3 - settings.tabSpacing
				}
			}
			default: {
				result = super.computeTrim(part, state, x, y, width, height)
			}
		}
		return result

	}

	override protected draw(int part, int state, Rectangle bounds, GC gc) {
		gc.antialias = SWT.ON
		gc.alpha = 255
		gc.background = tabFolder.background
		gc.foreground = tabFolder.foreground
		gc.lineWidth = 1
		gc.lineStyle = SWT.LINE_SOLID

		switch (part) {
			case PART_HEADER: {
				drawTabHeader(part, bounds, state, gc)
			}
			case PART_CLOSE_BUTTON: {
				drawCloseButton(part, bounds, state, gc)
			}
			case PART_BORDER: {
			}
			case PART_BODY: {
				drawTabBody(part, state, bounds, gc)
			}
			case part >= 0: {
				drawTabItem(part, state, bounds, gc)
			}
			default:
				super.draw(part, state, bounds, gc)
		}
	}

	private def getHeaderArea() {
		var headerArea = if(!tabFolder.onBottom) {
				new Rectangle(settings.margins.x, 0, tabFolder.size.x - settings.margins.x - settings.margins.height, tabFolder.tabHeight + 2)
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

	protected def drawTabHeader(int part, Rectangle bounds, int state, GC gc) {
		val headerArea = getHeaderArea()

		val corner = new Rectangle(0, 0, settings.borderRadius * 2, settings.borderRadius * 2)
		gc.withClip(
			newPath[
				autoRelease()
				if(tabFolder.onBottom == false) {
					if(settings.borderRadius > 0) {
						moveTo(headerArea.bottomRight)
						corner.relocateTopRightWith(headerArea)
						lineTo(corner.right)
						addArc(corner, 0, 90)

						corner.relocateTopLeftWith(headerArea)
						lineTo(corner.top)
						addArc(corner, 90, 90)
						lineTo(headerArea.bottomLeft)
						close()
					} else {
						addRectangle(headerArea)
					}
				} else {
					if(settings.borderRadius > 0) {
						moveTo(headerArea.topLeft)
						corner.relocateBottomLeftWith(headerArea)
						lineTo(corner.left)
						addArc(corner, 180, 90)

						corner.relocateBottomRightWith(headerArea)
						lineTo(corner.bottom)
						addArc(corner, 270, 90)
						lineTo(headerArea.topRight)

						close()
					} else {
						addRectangle(headerArea)
					}
				}
			]) [
			if(tabFolder.gradientColor != null) {
				if(tabFolder.onTop) {
					gc.fillGradientRectangle(headerArea, tabFolder.gradientColor, tabFolder.gradientPercents, true)
				} else {
					var reverseRect = newRectangle(headerArea.bottomLeft, new Point(headerArea.width, -headerArea.height))
					gc.fillGradientRectangle(reverseRect, tabFolder.gradientColor, tabFolder.gradientPercents, true)
				}
			} else {
				gc.background = tabFolder.background
				gc.fill(headerArea)
			}
		]

		draw(PART_BODY, SWT.FOREGROUND, bounds, gc)
	}

	protected def drawTabBody(int part, int state, Rectangle bounds, GC gc) {

		// Fill Background
		if(state.hasFlags(SWT.BACKGROUND)) {
			gc.background = tabFolder.parent.background
			gc.fill(bounds)

			if(settings.shadowColor != null) {
				drawShadow(part, state, bounds, gc)
			}

			val path = newPath[
				autoRelease()
				if(settings.borderRadius > 0) {
					addRoundRectangle(tabArea, settings.borderRadius)
				} else {
					addRectangle(tabArea)
				}
			]

			gc.background = #[tabFolder.selectionGradientColor?.last, tabFolder.selectionBackground].findFirst[it != null]
			gc.fill(path)
		}

		// Draw Border
		if(state.hasFlags(SWT.FOREGROUND) && settings.borderWidth > 0 && settings.borderColors != null && settings.borderPercents != null) {
			val offset = tabArea.getResized(-1, -1).shrink(settings.borderWidth / 2)
			gc.lineWidth = settings.borderWidth

			val headerPath = newPath[
				autoRelease()
				if(settings.borderRadius > 0) {
					moveTo(headerArea.bottomRight)
					var corner = newRectangleWithSize(settings.borderRadius * 2)
					corner.relocateTopRightWith(headerArea)
					lineTo(corner.right)
					addArc(corner, 0, 90)
					corner.relocateTopLeftWith(headerArea)
					lineTo(corner.top)
					addArc(corner, 90, 90)
					lineTo(headerArea.bottomLeft)

				} else {
					addRectangle(offset)
				}
			]
			gc.drawGradientPath(headerPath, settings.borderColors.toAutoReleaseColor, settings.borderPercents, true)
			val bodyPath = newPath[
				var corner = newRectangleWithSize(settings.borderRadius * 2)
				moveTo(headerArea.bottomLeft)
				corner.relocateBottomLeftWith(tabArea)
				lineTo(corner.left)
				addArc(corner, 180, 90)
				corner.relocateBottomRightWith(tabArea)
				lineTo(corner.bottom)
				addArc(corner, 270, 90)
				lineTo(headerArea.bottomRight)
			]
			gc.foreground = settings.borderColors.last.toAutoReleaseColor
			gc.draw(bodyPath)
		}
	}

	protected def drawCloseButton(int part, Rectangle bounds, int state, GC gc) {
		val box = bounds.getShrinked(2)
		var path = newPath[
			autoRelease()
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
				item.bounds.getTranslated(-Math.max(settings.tabSpacing, 0) - settings.borderWidth, -1).getResized(-settings.tabSpacing, 0)
			else
				item.bounds.getResized(-Math.max(settings.tabSpacing, 0), 1)
		if(item == tabFolder.lastVisibleItem) {
			itemBounds.resize(Math.max(settings.tabSpacing, 0) - 3, 0)
		}

		// Fill tab item bounds
		drawTabItemBackground(part, state, itemBounds, gc)

		// Draw Icon
		// fixme: ICON may not exists.
		var iconArea = item.image.bounds.relocateLeftWith(item.bounds).translate(settings.tabItemHorizontalSpacing, 0);
		gc.drawImage(item.image, iconArea.topLeft)

		// Draw Text
		gc.font = if(item.font != null) {
			item.font
		} else {
			tabFolder.font
		}

		val textSize = item.text.computeTextExtent(gc.font);
		val textArea = newRectangleWithSize(textSize).relocateLeftWith(iconArea.right).translate(settings.tabItemHorizontalSpacing, 0)
		if((item.showClose || tabFolder.showClose) && item.closeRect.width > 0) {
			textArea.setRight(item.closeRect.x - settings.tabItemHorizontalSpacing);
		} else {
			textArea.setRight(itemBounds.right.x - settings.tabItemHorizontalSpacing)
		}

		gc.withClip(textArea) [
			var text = if(textSize.x > textArea.width)
					gc.shortenText(item.text, textArea.width, "...")
				else
					item.text
			if(settings.getTextShadowColorFor(state) != null) {
				var shadowPosition = settings.getTextShadowPositionFor(state)
				gc.foreground = settings.getTextShadowColorFor(state).toAutoReleaseColor
				gc.drawString(text, textArea.topLeft.getTranslated(shadowPosition))
			}
			gc.foreground = settings.getTextColorFor(state).toAutoReleaseColor
			gc.drawString(text, textArea.topLeft)
		]

		// Draw Close Button
		if((tabFolder.showClose || item.showClose) && item.closeRect.width > 0) {
			item.closeRect.x = item.bounds.right.x - item.closeRect.width - 6
			if(item != tabFolder.lastVisibleItem) {
				item.closeRect.translate(-Math.max(settings.tabSpacing, 0) + 3, 0)
			}
			gc.withClip(item.closeRect) [
				draw(PART_CLOSE_BUTTON, item.closeImageState, item.closeRect, gc)
			]
		}

		// Draw Border and Keyline
		drawTabItemBorder(part, state, itemBounds, gc)

		// draw tab border
		draw(PART_BODY, SWT.FOREGROUND, new Rectangle(0, 0, tabFolder.size.x, itemBounds.bottom.y), gc)
	}

	protected def drawTabItemBorder(int part, int state, Rectangle bounds, GC gc) {
		if(settings.getBorderColorsFor(state) == null || settings.getBorderPercentsFor(state) == null){
			return;
		}
		
		val itemOutlineBounds = bounds.getResized(-1, 0)
		val CTabItem item = tabFolder.getItem(part)
		val outlineOffset = itemOutlineBounds.shrink(settings.borderWidth / 2)
		var Path outline = null;
		if(tabFolder.onTop) {
			outline = newPath[
				autoRelease()
				var keyLineY = item.bounds.bottom.y
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
					addArc(corner, 90, 90)
					lineTo(outlineOffset.x, keyLineY)
					if(state.hasFlags(SWT.SELECTED)) {
						lineTo(settings.margins.x, keyLineY)
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
					lineTo(itemOutlineBounds.bottomLeft.x, keyLineY)
					if(state.hasFlags(SWT.SELECTED)) {
						lineTo(settings.margins.x, keyLineY)
					}
				}
			]
		} else {
			outline = newPath[
				autoRelease()
				var keyLineY = item.bounds.y - 1
				if(settings.borderRadius > 0) {
					var corner = newRectangle(outlineOffset.topLeft, new Point(settings.borderRadius * 2, settings.borderRadius * 2))
					corner.relocateBottomRightWith(outlineOffset)
					if(state.hasFlags(SWT.SELECTED)) {
						moveTo(tabFolder.size.x - settings.margins.width - settings.borderWidth, keyLineY)
						lineTo(itemOutlineBounds.topRight.x, keyLineY)
					} else {
						moveTo(outlineOffset.topRight.x, keyLineY)
					}
					lineTo(corner.right)
					addArc(corner, 0, -90)

					corner.relocateBottomLeftWith(itemOutlineBounds)
					lineTo(corner.bottom)
					addArc(corner, 270, -90)

					lineTo(itemOutlineBounds.x, keyLineY)
					if(state.hasFlags(SWT.SELECTED)) {
						lineTo(settings.margins.x, keyLineY)
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
					lineTo(itemOutlineBounds.bottomLeft.x, keyLineY)
					if(state.hasFlags(SWT.SELECTED)) {
						lineTo(settings.margins.x, keyLineY)
					}
				}
			]
		}
		
		gc.lineWidth = settings.borderWidth
		gc.drawGradientPath(outline, settings.getBorderColorsFor(state).toAutoReleaseColor, settings.getBorderPercentsFor(state), true)
	}

	protected def drawTabItemBackground(int part, int state, Rectangle itemBounds, GC gc) {
		var Path tabItemFillArea = null
		if(tabFolder.onTop) {
			tabItemFillArea = newPath[
				autoRelease()
				if(settings.borderRadius > 0) {
					var corner = newRectangle(itemBounds.topLeft, new Point(settings.borderRadius * 2, settings.borderRadius * 2))
					corner.relocateTopRightWith(itemBounds)
					moveTo(corner.right)
					addArc(corner, 0, 90)
					corner.relocateTopLeftWith(itemBounds)
					lineTo(corner.top)
					addArc(corner, 90, 90)
					lineTo(itemBounds.bottomLeft)
					lineTo(itemBounds.bottomRight)
					close()
				} else {
					addRectangle(itemBounds)
				}
			]
		} else {
			tabItemFillArea = newPath[
				autoRelease()
				if(settings.borderRadius > 0) {
					var corner = newRectangle(itemBounds.topLeft, new Point(settings.borderRadius * 2, settings.borderRadius * 2))
					corner.relocateBottomLeftWith(itemBounds)
					moveTo(itemBounds.topLeft)
					lineTo(corner.left)
					addArc(corner, 180, 90)

					corner.relocateBottomRightWith(itemBounds)
					lineTo(corner.bottom)
					addArc(corner, 270, 90)
					lineTo(itemBounds.topRight)
					close()
				} else {
					addRectangle(itemBounds)
				}
			]
		}

		if(state.hasFlags(SWT.SELECTED)) {
			if(tabFolder.selectionGradientColor != null)
				gc.withClip(tabItemFillArea) [
					if(tabFolder.onTop)
						gc.fillGradientRectangle(itemBounds.getExpanded(0, 1), tabFolder.selectionGradientColor, tabFolder.selectionGradientPercents, true)
					else {
						var reverseRect = newRectangle(itemBounds.bottomLeft, new Point(itemBounds.width, -itemBounds.height));
						gc.fillGradientRectangle(reverseRect, tabFolder.selectionGradientColor, tabFolder.selectionGradientPercents, true)
					}
				]
			else {
				gc.background = tabFolder.selectionBackground
				gc.fill(tabItemFillArea)
			}
		} else {
			if(settings.unselectedBackgroundColors != null && settings.unselectedBackgroundPercents != null && !state.hasFlags(SWT.HOT)) {
				gc.withClip(tabItemFillArea) [
					if(tabFolder.onTop) {
						gc.fillGradientRectangle(itemBounds.getExpanded(0, 1), settings.unselectedBackgroundColors, settings.unselectedBackgroundPercents, true)
					} else {
						var reverseRect = newRectangle(itemBounds.bottomLeft, new Point(itemBounds.width, -itemBounds.height));
						gc.fillGradientRectangle(reverseRect, settings.unselectedBackgroundColors, settings.unselectedBackgroundPercents, true)
					}
				]
			} else if(settings.hoverBackgroundColors != null && settings.hoverBackgroundPercents != null && state.hasFlags(SWT.HOT)) {
				gc.withClip(tabItemFillArea) [
					if(tabFolder.onTop) {
						gc.fillGradientRectangle(itemBounds.getExpanded(0, 1), settings.hoverBackgroundColors, settings.hoverBackgroundPercents, true)
					} else {
						var reverseRect = newRectangle(itemBounds.bottomLeft, new Point(itemBounds.width, -itemBounds.height));
						gc.fillGradientRectangle(reverseRect, settings.hoverBackgroundColors, settings.hoverBackgroundPercents, true)
					}
				]
			}
		}
	}

	def protected drawShadow(int part, int state, Rectangle bounds, GC gc) {
		gc.withClip(bounds) [
			getShadow().fill(gc, tabArea.getExpanded(settings.shadowRadius).translate(settings.shadowPosition))
		]
	}

	def protected tabArea() {
		newRectangle.setSize(tabFolder.size).shrink(settings.margins.x, 0, settings.margins.width, settings.margins.height)
	}

	def getShadow() {
		if(shadowNinePatch == null || shadowNinePatch.disposed) {
			var background = #[settings.backgroundColor?.toRGB, tabFolder.background.RGB].firstNotNull
			shadowNinePatch = ShadowGenerator.createNinePatch(background, settings.shadowColor.toRGB, settings.borderRadius, settings.shadowRadius);
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
