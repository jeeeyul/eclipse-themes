package net.jeeeyul.eclipse.themes.rendering;

import net.jeeeyul.eclipse.themes.rendering.internal.HackedField;
import net.jeeeyul.eclipse.themes.rendering.internal.HackedMethod0;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.ToolBar;

public class KTabRendererHelper {
	static interface _CTabFolder {
		static final HackedMethod0<CTabFolder, ToolBar> getChevron = new HackedMethod0<CTabFolder, ToolBar>(CTabFolder.class, "getChevron");
		static final HackedField<CTabFolder, Boolean> chevronVisible = new HackedField<CTabFolder, Boolean>(CTabFolder.class, "chevronVisible");
		static final HackedField<CTabFolder, ToolBar> minMaxTb = new HackedField<CTabFolder, ToolBar>(CTabFolder.class, "minMaxTb");
		static final HackedField<CTabFolder, Color[]> gradientColors = new HackedField<CTabFolder, Color[]>(CTabFolder.class, "gradientColors");
		static final HackedField<CTabFolder, int[]> gradientPercents = new HackedField<CTabFolder, int[]>(CTabFolder.class, "gradientPercents");
		static final HackedField<CTabFolder, Color[]> selectionGradientColors = new HackedField<CTabFolder, Color[]>(CTabFolder.class,
				"selectionGradientColors");
		static final HackedField<CTabFolder, int[]> selectionGradientPercents = new HackedField<CTabFolder, int[]>(CTabFolder.class,
				"selectionGradientPercents");
	}

	static interface _CTabItem {
		static final HackedField<CTabItem, Boolean> showing = new HackedField<CTabItem, Boolean>(CTabItem.class, "showing");
		static final HackedField<CTabItem, String> shortenedText = new HackedField<CTabItem, String>(CTabItem.class, "shortenedText");
		static final HackedField<CTabItem, Integer> shortenedTextWidth = new HackedField<CTabItem, Integer>(CTabItem.class, "shortenedTextWidth");
		static final HackedField<CTabItem, Rectangle> closeRect = new HackedField<CTabItem, Rectangle>(CTabItem.class, "closeRect");
		static final HackedField<CTabItem, Integer> closeImageState = new HackedField<CTabItem, Integer>(CTabItem.class, "closeImageState");
	}

	static interface _CTabFolderRender {
		static final HackedField<CTabFolderRenderer, Color> selectedOuterColor = new HackedField<CTabFolderRenderer, Color>(CTabFolderRenderer.class,
				"selectedOuterColor");
	}

	public Color getSelectedOuterColor(CTabFolderRenderer me) {
		return _CTabFolderRender.selectedOuterColor.get(me);
	}

	public Color[] getSelectionGradientColor(CTabFolder me) {
		return _CTabFolder.selectionGradientColors.get(me);
	}

	public int[] getSelectionGradientPercents(CTabFolder me) {
		return _CTabFolder.selectionGradientPercents.get(me);
	}

	public Color[] getGradientColor(CTabFolder me) {
		return _CTabFolder.gradientColors.get(me);
	}

	public int[] getGradientPercents(CTabFolder me) {
		return _CTabFolder.gradientPercents.get(me);
	}

	public boolean getOnBottom(CTabFolder me) {
		return (me.getStyle() & SWT.BOTTOM) != 0;
	}

	public ToolBar getMinMaxToolbar(CTabFolder me) {
		return _CTabFolder.minMaxTb.get(me);
	}

	public ToolBar getChevron(CTabFolder me) {
		return _CTabFolder.getChevron.invoke(me);
	}

	public int getCloseImageState(CTabItem me) {
		return _CTabItem.closeImageState.get(me);
	}

	public Rectangle getCloseRect(CTabItem me) {
		return _CTabItem.closeRect.get(me);
	}

	public String getShortenText(CTabItem me) {
		return _CTabItem.shortenedText.get(me);
	}

	public int getShortenTextWidth(CTabItem me) {
		return _CTabItem.shortenedTextWidth.get(me);
	}

	public boolean getShowClose(CTabFolder folder) {
		return (folder.getStyle() & SWT.CLOSE) != 0;
	}

	public boolean isCheveronVisible(CTabFolder me) {
		return _CTabFolder.chevronVisible.get(me);
	}

	public boolean isShowing(CTabItem me) {
		return _CTabItem.showing.get(me);
	}

	public Rectangle setCloseRect(CTabItem me, Rectangle rect) {
		return _CTabItem.closeRect.set(me, rect);
	}

	public String setShortenText(CTabItem me, String shortenText) {
		return _CTabItem.shortenedText.set(me, shortenText);
	}

	public int setShortenTextWidth(CTabItem me, Integer width) {
		return _CTabItem.shortenedTextWidth.set(me, width);
	}

	public void drawBackground(CTabFolder me, GC gc, Rectangle bounds, Color[] colors, int[] percents, boolean vertical) {
		drawBackground(me, gc, null, bounds.x, bounds.y, bounds.width, bounds.height, colors[0], null, colors, percents, vertical);
	}

	public void drawBackground(CTabFolder parent, GC gc, int[] shape, int x, int y, int width, int height, Color defaultBackground, Image image,
			Color[] colors, int[] percents, boolean vertical) {
		Region clipping = null, region = null;
		if (shape != null) {
			clipping = new Region();
			gc.getClipping(clipping);
			region = new Region();
			region.add(shape);
			region.intersect(clipping);
			gc.setClipping(region);
		}
		if (image != null) {
			// draw the background image in shape
			gc.setBackground(defaultBackground);
			gc.fillRectangle(x, y, width, height);
			Rectangle imageRect = image.getBounds();
			gc.drawImage(image, imageRect.x, imageRect.y, imageRect.width, imageRect.height, x, y, width, height);
		} else if (colors != null) {
			// draw gradient
			if (colors.length == 1) {
				Color background = colors[0] != null ? colors[0] : defaultBackground;
				gc.setBackground(background);
				gc.fillRectangle(x, y, width, height);
			} else {
				if (vertical) {
					if (getOnBottom(parent)) {
						int pos = 0;
						if (percents[percents.length - 1] < 100) {
							pos = (100 - percents[percents.length - 1]) * height / 100;
							gc.setBackground(defaultBackground);
							gc.fillRectangle(x, y, width, pos);
						}
						Color lastColor = colors[colors.length - 1];
						if (lastColor == null)
							lastColor = defaultBackground;
						for (int i = percents.length - 1; i >= 0; i--) {
							gc.setForeground(lastColor);
							lastColor = colors[i];
							if (lastColor == null)
								lastColor = defaultBackground;
							gc.setBackground(lastColor);
							int percentage = i > 0 ? percents[i] - percents[i - 1] : percents[i];
							int gradientHeight = percentage * height / 100;
							gc.fillGradientRectangle(x, y + pos, width, gradientHeight, true);
							pos += gradientHeight;
						}
					} else {
						Color lastColor = colors[0];
						if (lastColor == null)
							lastColor = defaultBackground;
						int pos = 0;
						for (int i = 0; i < percents.length; i++) {
							gc.setForeground(lastColor);
							lastColor = colors[i + 1];
							if (lastColor == null)
								lastColor = defaultBackground;
							gc.setBackground(lastColor);
							int percentage = i > 0 ? percents[i] - percents[i - 1] : percents[i];
							int gradientHeight = percentage * height / 100;
							gc.fillGradientRectangle(x, y + pos, width, gradientHeight, true);
							pos += gradientHeight;
						}
						if (pos < height) {
							gc.setBackground(defaultBackground);
							gc.fillRectangle(x, pos, width, height - pos + 1);
						}
					}
				} else { // horizontal gradient
					y = 0;
					height = parent.getSize().y;
					Color lastColor = colors[0];
					if (lastColor == null)
						lastColor = defaultBackground;
					int pos = 0;
					for (int i = 0; i < percents.length; ++i) {
						gc.setForeground(lastColor);
						lastColor = colors[i + 1];
						if (lastColor == null)
							lastColor = defaultBackground;
						gc.setBackground(lastColor);
						int gradientWidth = (percents[i] * width / 100) - pos;
						gc.fillGradientRectangle(x + pos, y, gradientWidth, height, false);
						pos += gradientWidth;
					}
					if (pos < width) {
						gc.setBackground(defaultBackground);
						gc.fillRectangle(x + pos, y, width - pos, height);
					}
				}
			}
		} else {
			// draw a solid background using default background in shape
			if ((parent.getStyle() & SWT.NO_BACKGROUND) != 0 || !defaultBackground.equals(parent.getBackground())) {
				gc.setBackground(defaultBackground);
				gc.fillRectangle(x, y, width, height);
			}
		}
		if (shape != null) {
			gc.setClipping(clipping);
			clipping.dispose();
			region.dispose();
		}
	}
}
