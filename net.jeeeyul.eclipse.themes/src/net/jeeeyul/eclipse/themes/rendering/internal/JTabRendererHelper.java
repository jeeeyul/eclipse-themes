package net.jeeeyul.eclipse.themes.rendering.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer;
import net.jeeeyul.eclipse.themes.util.HackedField;
import net.jeeeyul.eclipse.themes.util.HackedMethod0;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Xtend extensions for {@link JeeeyulsTabRenderer}. It helps renderer with high
 * level abstraction for hack {@link CTabFolder}. It also provides method to
 * access {@link JTabSettings} easily.
 * 
 * @author Jeeeyul
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public class JTabRendererHelper {
	private static final String DATA_KEY__LAST_KNOWN_STATE = "_lastKnownState";

	static interface _CTabFolder {
		static final HackedField<CTabFolder, Boolean> onBottom = new HackedField<CTabFolder, Boolean>(CTabFolder.class, "onBottom");
		static final HackedMethod0<CTabFolder, ToolBar> getChevron = new HackedMethod0<CTabFolder, ToolBar>(CTabFolder.class, "getChevron");
		static final HackedField<CTabFolder, Boolean> chevronVisible = new HackedField<CTabFolder, Boolean>(CTabFolder.class, "chevronVisible");
		static final HackedField<CTabFolder, Boolean> showUnselectedClose = new HackedField<CTabFolder, Boolean>(CTabFolder.class, "showUnselectedClose");
		static final HackedField<CTabFolder, ToolBar> minMaxTb = new HackedField<CTabFolder, ToolBar>(CTabFolder.class, "minMaxTb");
		static final HackedField<CTabFolder, Color[]> gradientColors = new HackedField<CTabFolder, Color[]>(CTabFolder.class, "gradientColors");
		static final HackedField<CTabFolder, int[]> gradientPercents = new HackedField<CTabFolder, int[]>(CTabFolder.class, "gradientPercents");
		static final HackedField<CTabFolder, Color[]> selectionGradientColors = new HackedField<CTabFolder, Color[]>(CTabFolder.class,
				"selectionGradientColors");
		static final HackedField<CTabFolder, int[]> selectionGradientPercents = new HackedField<CTabFolder, int[]>(CTabFolder.class,
				"selectionGradientPercents");
		static final HackedField<CTabFolder, Integer> firstIndex = new HackedField<CTabFolder, Integer>(CTabFolder.class, "firstIndex");

		static final HackedField<CTabFolder, Image> chevronImage = new HackedField<CTabFolder, Image>(CTabFolder.class, "chevronImage");
		static final HackedField<CTabFolder, ToolItem> chevronItem = new HackedField<CTabFolder, ToolItem>(CTabFolder.class, "chevronItem");
		static final HackedField<CTabFolder, Control[]> controls = new HackedField<CTabFolder, Control[]>(CTabFolder.class, "controls");
		static final HackedField<CTabFolder, Image[]> controlBkImages = new HackedField<CTabFolder, Image[]>(CTabFolder.class, "controlBkImages");
	}

	static interface _CTabItem {
		static final HackedField<CTabItem, Boolean> showing = new HackedField<CTabItem, Boolean>(CTabItem.class, "showing");
		static final HackedField<CTabItem, String> shortenedText = new HackedField<CTabItem, String>(CTabItem.class, "shortenedText");
		static final HackedField<CTabItem, Integer> shortenedTextWidth = new HackedField<CTabItem, Integer>(CTabItem.class, "shortenedTextWidth");
		static final HackedField<CTabItem, Rectangle> closeRect = new HackedField<CTabItem, Rectangle>(CTabItem.class, "closeRect");
		static final HackedField<CTabItem, Integer> closeImageState = new HackedField<CTabItem, Integer>(CTabItem.class, "closeImageState");
		static final HackedField<CTabItem, Integer> state = new HackedField<CTabItem, Integer>(CTabItem.class, "state");
	}

	static interface _CTabFolderRender {
		static final HackedField<CTabFolderRenderer, Color> selectedOuterColor = new HackedField<CTabFolderRenderer, Color>(CTabFolderRenderer.class,
				"selectedOuterColor");
	}

	public boolean getShowUnselectedClose(CTabFolder me) {
		return _CTabFolder.showUnselectedClose.get(me);
	}

	public int getFirstVisibleIndex(CTabFolder me) {
		return _CTabFolder.firstIndex.get(me);
	}

	public boolean getOnTop(CTabFolder folder) {
		return !getOnBottom(folder);
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

	public ToolItem getChevronItem(CTabFolder me) {
		return _CTabFolder.chevronItem.get(me);
	}

	public Image getChevronImage(CTabFolder me) {
		return _CTabFolder.chevronImage.get(me);
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

	public CTabItem getLastVisibleItem(CTabFolder me) {
		List<CTabItem> visibles = new ArrayList<CTabItem>();
		for (CTabItem each : me.getItems()) {
			if (each.isShowing()) {
				visibles.add(each);
			}
		}

		if (visibles.size() == 0) {
			return null;
		}
		Collections.sort(visibles, new Comparator<CTabItem>() {
			@Override
			public int compare(CTabItem o1, CTabItem o2) {
				return o1.getBounds().x - o2.getBounds().x;
			}
		});

		return visibles.get(visibles.size() - 1);
	}

	public CTabItem getFirstVisibleItem(CTabFolder me) {
		List<CTabItem> visibles = new ArrayList<CTabItem>();
		for (CTabItem each : me.getItems()) {
			if (each.isShowing()) {
				visibles.add(each);
			}
		}

		if (visibles.size() == 0) {
			return null;
		}
		Collections.sort(visibles, new Comparator<CTabItem>() {
			@Override
			public int compare(CTabItem o1, CTabItem o2) {
				return o1.getBounds().x - o2.getBounds().x;
			}
		});

		return visibles.get(0);
	}

	public boolean isLastVisibleItem(CTabItem me) {
		return getLastVisibleItem(me.getParent()) == me;
	}

	public String setShortenText(CTabItem me, String shortenText) {
		return _CTabItem.shortenedText.set(me, shortenText);
	}

	public int setShortenTextWidth(CTabItem me, Integer width) {
		return _CTabItem.shortenedTextWidth.set(me, width);
	}

	public <T> T getFirstNotNull(Iterable<T> items) {
		Iterator<T> iter = items.iterator();
		while (iter.hasNext()) {
			T next = iter.next();
			if (next != null) {
				return next;
			}
		}
		return null;
	}

	public GC fillGradientRectangle(GC gc, Rectangle bounds, HSB[] hsbs, int[] percents, boolean vertical) {
		Color[] colors = new Color[hsbs.length];
		for (int i = 0; i < hsbs.length; i++) {
			colors[i] = SWTExtensions.INSTANCE.toAutoDisposeColor(hsbs[i]);
		}

		SWTExtensions.INSTANCE.fillGradientRectangle(gc, bounds, colors, percents, vertical);
		return gc;
	}

	public HSB getTextShadowColorFor(JTabSettings me, int state) {
		if ((state & SWT.SELECTED) != 0) {
			return me.getSelectedTextShadowColor();
		} else if ((state & SWT.HOT) != 0) {
			return me.getHoverTextShadowColor();
		} else {
			return me.getUnselectedTextShadowColor();
		}
	}

	public Point getTextShadowPositionFor(JTabSettings me, int state) {
		if ((state & SWT.SELECTED) != 0) {
			return me.getSelectedTextShadowPosition();
		} else if ((state & SWT.HOT) != 0) {
			return me.getHoverTextShadowPosition();
		} else {
			return me.getUnselectedTextShadowPosition();
		}
	}

	public HSB getTextColorFor(JTabSettings me, int state) {
		if ((state & SWT.SELECTED) != 0) {
			return new HSB(me.getRenderer().getTabFolder().getSelectionForeground().getRGB());
		} else if ((state & SWT.HOT) != 0) {
			HSB hoverForgroundColor = me.getHoverForgroundColor();
			if (hoverForgroundColor == null) {
				return getTextColorFor(me, SWT.NONE);
			} else {
				return hoverForgroundColor;
			}
		} else {
			return new HSB(me.getRenderer().getTabFolder().getForeground().getRGB());
		}
	}

	public HSB[] getBorderColorsFor(JTabSettings me, int state) {
		if ((state & SWT.SELECTED) != 0) {
			return me.getSelectedBorderColors();
		} else if ((state & SWT.HOT) != 0) {
			return me.getHoverBorderColors();
		} else {
			return me.getUnselectedBorderColors();
		}
	}

	public int[] getBorderPercentsFor(JTabSettings me, int state) {
		if ((state & SWT.SELECTED) != 0) {
			return me.getSelectedBorderPercents();
		} else if ((state & SWT.HOT) != 0) {
			return me.getHoverBorderPercents();
		} else {
			return me.getUnselectedBorderPercents();
		}
	}

	public HSB[] getItemFillFor(JTabSettings me, int state) {
		CTabFolder tabFolder = me.getRenderer().getTabFolder();
		if ((state & SWT.SELECTED) != 0) {
			Color[] gradient = _CTabFolder.selectionGradientColors.get(tabFolder);
			if (gradient != null) {
				return SWTExtensions.INSTANCE.toHSBArray(gradient);
			} else {
				HSB hsb = new HSB(tabFolder.getSelectionBackground().getRGB());
				return new HSB[] { hsb, hsb };
			}
		}

		else if ((state & SWT.HOT) != 0) {
			HSB[] gradient = me.getHoverBackgroundColors();
			if (gradient != null) {
				return gradient;
			} else {
				return getItemFillFor(me, SWT.NONE);
			}
		}

		else {
			return me.getUnselectedBackgroundColors();
		}
	}

	public int[] getItemFillPercentsFor(JTabSettings me, int state) {
		CTabFolder tabFolder = me.getRenderer().getTabFolder();
		if ((state & SWT.SELECTED) != 0) {
			int[] percents = _CTabFolder.selectionGradientPercents.get(tabFolder);
			if (percents != null) {
				return percents;
			} else {
				return new int[] { 100 };
			}
		}

		else if ((state & SWT.HOT) != 0) {
			int[] percents = me.getHoverBackgroundPercents();
			if (percents != null) {
				return percents;
			} else {
				return getItemFillPercentsFor(me, SWT.NONE);
			}
		}

		else {
			return me.getUnselectedBackgroundPercents();
		}
	}

	public Control[] getControls(CTabFolder me) {
		return _CTabFolder.controls.get(me);
	}

	public Image[] getControlBkImages(CTabFolder me) {
		return _CTabFolder.controlBkImages.get(me);
	}

	public boolean isWindow() {
		return getOSName().startsWith("Windows");
	}

	private String getOSName() {
		return System.getProperty("os.name");
	}

	public boolean isLinux() {
		return getOSName().startsWith("Linux");
	}

	public int lastKnownState(CTabItem item) {
		Integer state = (Integer) item.getData(DATA_KEY__LAST_KNOWN_STATE);
		if (state == null) {
			state = 0;
		}
		return state;
	}

	public int setLastKnownState(CTabItem item, int state) {
		item.setData(DATA_KEY__LAST_KNOWN_STATE, state);
		return state;
	}

	public int getState(CTabItem item) {
		return _CTabItem.state.get(item);
	}
}
