package net.jeeeyul.eclipse.themes.rendering

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.graphics.Point

class KTabRenderer extends CTabFolderRenderer {
	extension KTabRendererHelper = new KTabRendererHelper
	extension SWTExtensions = SWTExtensions.INSTANCE

	@Property KTabSettings settings = new KTabSettings()

	CTabFolder parent

	new(CTabFolder parent) {
		super(parent)
		this.parent = parent
		parent.simple = true
		parent.setBackground(#[COLOR_WHITE, COLOR_GRAY], #[100], true)
		parent.setSelectionBackground(#[COLOR_INFO_BACKGROUND, COLOR_WHITE], #[100], true)
		
		parent.selectionForeground = COLOR_BLACK
		parent.borderVisible = true;
	}

	override protected dispose() {
		super.dispose()
	}

	override protected computeTrim(int part, int state, int x, int y, int width, int height) {
		var result = new Rectangle(x, y, width, height)
		switch (part) {
			case PART_BORDER: {
				result.x = result.x - settings.margins.x
				result.width = result.width + settings.margins.x + settings.margins.width
				result.height = result.height + settings.margins.height
			}
			case PART_HEADER: {
				result.x = result.x - settings.margins.x
				result.width = result.width + settings.margins.x + settings.margins.width
			}
			case PART_BODY: {
				result.x = result.x - settings.margins.x - settings.paddings.x - settings.border
				result.width = result.width + settings.margins.width + settings.paddings.width + settings.margins.x + settings.paddings.x + settings.border * 2
				if(parent.onBottom) {
					result.y = result.y - settings.paddings.y - settings.border
					result.height = result.height + parent.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + settings.border * 2 + 2
				} else {
					result.y = result.y - parent.tabHeight - settings.paddings.y - settings.border - 1
					result.height = result.height + parent.tabHeight + settings.paddings.y + settings.margins.height + settings.paddings.height + settings.border * 2 + 1
				}
			}
			case PART_MAX_BUTTON: {
			}
			default: {
				result = super.computeTrim(part, state, x, y, width, height)
			}
		}
		return result

	}

	override protected draw(int part, int state, Rectangle bounds, GC gc) {
		switch (part) {
			case PART_HEADER: {
				var headerArea = if(!parent.onBottom) {
						new Rectangle(settings.margins.x, 0, parent.size.x - settings.margins.x - settings.margins.height, parent.tabHeight + 2)
					} else {
						new Rectangle(0, parent.size.y, parent.size.x, parent.size.y) => [
							shrink(settings.margins.x, 0, settings.margins.width, 0)
							height = parent.tabHeight
							translate(0, -parent.tabHeight - settings.margins.height - 2)
							resize(0, 2)
						]
					}
				parent.drawBackground(gc, headerArea, parent.gradientColor, parent.gradientPercents, true)

				draw(PART_BODY, SWT.FOREGROUND, bounds, gc)
			}
			case PART_CLOSE_BUTTON: {
				gc.background = COLOR_YELLOW
				gc.fill(bounds)
			}
			case PART_BORDER: {
			}
			case PART_BODY: {
				if(state.hasFlags(SWT.BACKGROUND)) {
					gc.background = parent.parent.background
					gc.fill(bounds)

					gc.background = settings.shadowColor
					gc.fill(tabArea.getTranslated(2, 2))

					gc.background = parent.selectionGradientColor.last
					gc.fill(tabArea)
				}
				if(state.hasFlags(SWT.FOREGROUND)) {
					gc.foreground = settings.borderColor;
					gc.draw(tabArea.getResized(-1, -1))
				}
			}
			case part >= 0 && state.hasFlags(SWT.SELECTED): {
				var item = parent.getItem(part)
				var itemBounds = item.bounds

				parent.drawBackground(gc, itemBounds, parent.selectionGradientColor, parent.selectionGradientPercents, true)

				var iconArea = item.image.bounds.relocateLeftWith(item.bounds).translate(4, 0);
				gc.drawImage(item.image, iconArea.topLeft)

				var textArea = newRectangleWithSize(gc.stringExtent(item.text)).relocateLeftWith(iconArea.right).translate(4, 0)
				gc.foreground = parent.selectionForeground
				gc.drawString(item.text, textArea.topLeft)

				draw(PART_CLOSE_BUTTON, state, item.closeRect, gc)
				
				gc.foreground =  settings.innerBorderColor
				gc.draw(itemBounds.getResized(-1, 0))
				
				var keyLine = 
				if(parent.onBottom){
					#[
						new Point(settings.margins.x + settings.border, itemBounds.top.y -1),
						new Point(parent.size.x - settings.margins.width - settings.border - 1, itemBounds.top.y -1)
					]
				}else{
					#[
						new Point(settings.margins.x + settings.border, itemBounds.bottom.y),
						new Point(parent.size.x - settings.margins.width - settings.border - 1, itemBounds.bottom.y)
					]
				}
				gc.antialias = SWT.OFF
				gc.drawLine(keyLine)
				gc.foreground = parent.selectionGradientColor.last
				gc.drawLine(itemBounds.bottomLeft.translate(1,0), itemBounds.bottomRight.translate(-2, 0))
				
				draw(PART_BODY, SWT.FOREGROUND, item.bounds, gc)
				
			}
			default:
				super.draw(part, state, bounds, gc)
		}
	}

	def tabArea() {
		newRectangle.setSize(parent.size).shrink(settings.margins.x, 0, settings.margins.width, settings.margins.height)
	}
}
