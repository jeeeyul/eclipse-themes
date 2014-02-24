package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.graphics.Path

class KTabRenderer extends CTabFolderRenderer {
	extension KTabRendererHelper = new KTabRendererHelper
	extension SWTExtensions = SWTExtensions.INSTANCE

	@Property KTabSettings settings = new KTabSettings()

	CTabFolder parent

	new(CTabFolder parent) {
		super(parent)
		this.parent = parent
	}

	override protected dispose() {
		super.dispose()
	}

	override protected computeSize(int part, int state, GC gc, int wHint, int hHint) {
		switch (part) {
			case PART_CLOSE_BUTTON: {
				return new Point(8, 8)
			}
			case (part >= 0): {
				var result = super.computeSize(part, state, gc, wHint, hHint)
				return result
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

				if(parent.onBottom) {
					result.y = result.y - settings.paddings.y - settings.borderWidth
					result.height = result.height + parent.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + settings.borderWidth * 2 + 2
				} else {
					result.y = result.y - parent.tabHeight - settings.paddings.y - settings.borderWidth - 1
					result.height = result.height + parent.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + settings.borderWidth * 2 + 1
				}
			}
			case PART_MAX_BUTTON: {
			}
			case (part >= 0): {
				result = super.computeTrim(part, state, x, y, width, height).getResized(settings.tabSpacing, 0).translate(-settings.borderWidth, 0).resize(settings.borderWidth * 2, 0)
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
		gc.background = parent.background
		gc.foreground = parent.foreground
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

	protected def drawTabHeader(int part, Rectangle bounds, int state, GC gc) {
		val headerArea = if(!parent.onBottom) {
				new Rectangle(settings.margins.x, 0, parent.size.x - settings.margins.x - settings.margins.height, parent.tabHeight + 2)
			} else {
				new Rectangle(0, parent.size.y, parent.size.x, parent.size.y) => [
					shrink(settings.margins.x, 0, settings.margins.width, 0)
					height = parent.tabHeight
					translate(0, -parent.tabHeight - settings.margins.height - 2)
					resize(0, 2)
				]
			}

		val corner = new Rectangle(0, 0, settings.borderRadius * 2, settings.borderRadius * 2)
		gc.withClip(
			newPath[
				autoRelease()
				if(parent.onBottom == false) {
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
			if(parent.gradientColor != null) {
				if(parent.onTop) {
					gc.fillGradientRectangle(headerArea, parent.gradientColor, parent.gradientPercents, true)
				} else {
					var reverseRect = newRectangle(headerArea.bottomLeft, new Point(headerArea.width, -headerArea.height))
					gc.fillGradientRectangle(reverseRect, parent.gradientColor, parent.gradientPercents, true)
				}
			} else {
				gc.background = parent.background
				gc.fill(headerArea)
			}
		]

		draw(PART_BODY, SWT.FOREGROUND, bounds, gc)
	}

	protected def drawTabBody(int part, int state, Rectangle bounds, GC gc) {
		gc.withClip(bounds) [
			// Fill Background
			if(state.hasFlags(SWT.BACKGROUND)) {
				gc.background = parent.parent.background
				gc.fill(bounds)

				if(settings.shadowRadius > 0) {
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

				gc.background = #[parent.selectionGradientColor?.last, parent.selectionBackground].findFirst[it != null]
				gc.fill(path)
			}
			// Draw Border
			if(state.hasFlags(SWT.FOREGROUND) && settings.borderWidth > 0) {
				gc.foreground = settings.borderColor.toAutoReleaseColor;
				gc.lineWidth = settings.borderWidth

				val offset = tabArea.getResized(-1, -1).shrink(settings.borderWidth / 2)

				val path = newPath[
					autoRelease()
					if(settings.borderRadius > 0) {
						addRoundRectangle(offset, settings.borderRadius)
					} else {
						addRectangle(offset)
					}
				]
				gc.draw(path)
			}
		]
	}

	protected def drawCloseButton(int part, Rectangle bounds, int state, GC gc) {
		val box = bounds.getShrinked(1, 1)
		var path = newPath[
			autoRelease()
			moveTo(box.topLeft)
			lineTo(box.bottomRight)
			moveTo(box.topRight)
			lineTo(box.bottomLeft)
		]

		var color = switch (state) {
			case state.hasFlags(SWT.HOT): settings.closeButtonHotColor
			case state.hasFlags(SWT.SELECTED): settings.closeButtonActiveColor
			default: settings.closeButtonColor
		}
		gc.lineWidth = Math.max(settings.closeButtonLineWidth, 1)
		gc.foreground = color.toAutoReleaseColor
		gc.draw(path)
	}

	protected def drawTabItem(int part, int state, Rectangle bounds, GC gc) {
		val item = parent.getItem(part)

		val itemBounds = if(parent.onBottom)
				item.bounds.getTranslated(0, -1).getResized(-settings.tabSpacing, 0)
			else
				item.bounds.getResized(-settings.tabSpacing, 1)

		// Fill tab item bounds
		var Path tabItemFillArea = null
		if(parent.onTop) {
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
			if(parent.selectionGradientColor != null)
				gc.withClip(tabItemFillArea) [
					if(parent.onTop)
						gc.fillGradientRectangle(itemBounds.getExpanded(0, 1), parent.selectionGradientColor, parent.selectionGradientPercents, true)
					else {
						var reverseRect = newRectangle(itemBounds.bottomLeft, new Point(itemBounds.width, -itemBounds.height));
						gc.fillGradientRectangle(reverseRect, parent.selectionGradientColor, parent.selectionGradientPercents, true)
					}
				]
			else {
				gc.background = parent.selectionBackground
				gc.fill(tabItemFillArea)
			}
		} else {
			if(settings.unselectedBackgroundColors != null) {
				gc.withClip(tabItemFillArea) [
					if(parent.onTop) {
						gc.fillGradientRectangle(itemBounds.getExpanded(0, 1), settings.unselectedBackgroundColors, settings.unselectedBackgroundPercents, true)
					} else {
						var reverseRect = newRectangle(itemBounds.bottomLeft, new Point(itemBounds.width, -itemBounds.height));
						gc.fillGradientRectangle(reverseRect, settings.unselectedBackgroundColors, settings.unselectedBackgroundPercents, true)
					}
				]
			}
		}

		// Draw Icon
		// fixme: ICON may not exists.
		var iconArea = item.image.bounds.relocateLeftWith(item.bounds).translate(settings.borderWidth + settings.tabItemHorizontalSpacing, 0);
		gc.drawImage(item.image, iconArea.topLeft)

		// Draw Text
		gc.font = if(item.font != null) {
			item.font
		} else {
			parent.font
		}

		val textArea = newRectangleWithSize(gc.stringExtent(item.text)).relocateLeftWith(iconArea.right).translate(4, 0)
		if((item.showClose || parent.showClose) && item.closeRect.width > 0) {
			textArea.setRight(item.closeRect.x - settings.tabItemHorizontalSpacing);
		} else {
			textArea.setRight(itemBounds.right.x - settings.tabItemHorizontalSpacing)
		}

		gc.foreground = if(state.hasFlags(SWT.SELECTED))
			parent.selectionForeground
		else
			parent.foreground

		gc.withClip(textArea) [
			if(item.text.computeTextExtent(gc.font).x > textArea.width) {
				var stext = gc.shortenText(item.text, textArea.width, "...")
				gc.drawString(stext, textArea.topLeft)
			} else {
				gc.drawString(item.text, textArea.topLeft)
			}
		]

		// Draw Close Button
		if((parent.showClose || item.showClose) && item.closeRect.width > 0) {
			gc.withClip(item.closeRect) [
				draw(PART_CLOSE_BUTTON, item.closeImageState, item.closeRect, gc)
			]
		}

		// Draw Border and Keyline
		if((settings.showSelectedBorder && state.hasFlags(SWT.SELECTED)) || (settings.showUnselectedBorder && !state.hasFlags(SWT.SELECTED))) {
			val outlineOffset = itemBounds.shrink(settings.borderWidth / 2)
			var Path outline = null;
			if(parent.onTop) {
				outline = newPath[
					autoRelease()
					var keyLineY = item.bounds.bottom.y
					if(settings.borderRadius > 0) {
						var corner = newRectangle(outlineOffset.topLeft, new Point(settings.borderRadius * 2, settings.borderRadius * 2))
						corner.relocateTopRightWith(outlineOffset)

						if(state.hasFlags(SWT.SELECTED)) {
							moveTo(parent.size.x - settings.margins.width - settings.borderWidth, keyLineY)
							lineTo(itemBounds.bottomRight.x, keyLineY)
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
							moveTo(parent.size.x - settings.margins.width - settings.borderWidth, keyLineY)
							lineTo(itemBounds.bottomRight.x, keyLineY)
						} else {
							moveTo(outlineOffset.bottomRight.x, keyLineY)
						}
						lineTo(itemBounds.topRight)
						lineTo(itemBounds.topLeft)
						lineTo(itemBounds.bottomLeft.x, keyLineY)
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
							moveTo(parent.size.x - settings.margins.width - settings.borderWidth, keyLineY)
							lineTo(itemBounds.topRight.x, keyLineY)
						} else {
							moveTo(outlineOffset.topRight.x, keyLineY)
						}
						lineTo(corner.right)
						addArc(corner, 0, -90)

						corner.relocateBottomLeftWith(itemBounds)
						lineTo(corner.bottom)
						addArc(corner, 270, -90)

						lineTo(itemBounds.x, keyLineY)
						if(state.hasFlags(SWT.SELECTED)) {
							lineTo(settings.margins.x, keyLineY)
						}

					} else {
						if(state.hasFlags(SWT.SELECTED)) {
							moveTo(parent.size.x - settings.margins.width - settings.borderWidth, keyLineY)
							lineTo(itemBounds.bottomRight.x, keyLineY)
						} else {
							moveTo(outlineOffset.bottomRight.x, keyLineY)
						}
						lineTo(itemBounds.topRight)
						lineTo(itemBounds.topLeft)
						lineTo(itemBounds.bottomLeft.x, keyLineY)
						if(state.hasFlags(SWT.SELECTED)) {
							lineTo(settings.margins.x, keyLineY)
						}
					}
				]
			}

			gc.foreground = if(state.hasFlags(SWT.SELECTED))
				settings.selectedBorderColor.toAutoReleaseColor
			else
				settings.unselectedBorderColor.toAutoReleaseColor

			gc.lineWidth = settings.borderWidth
			gc.draw(outline)
		}

		// draw tab border
		draw(PART_BODY, SWT.FOREGROUND, new Rectangle(0, 0, parent.size.x, itemBounds.bottom.y), gc)
	}

	def protected drawShadow(int part, int state, Rectangle bounds, GC gc) {
		gc.withClip(bounds)[]
	}

	def protected tabArea() {
		newRectangle.setSize(parent.size).shrink(settings.margins.x, 0, settings.margins.width, settings.margins.height)
	}
}
