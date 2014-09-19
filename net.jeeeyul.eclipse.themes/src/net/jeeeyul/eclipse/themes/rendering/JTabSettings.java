package net.jeeeyul.eclipse.themes.rendering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * settings for {@link JeeeyulsTabRenderer}
 * 
 * @author Jeeeyul
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public class JTabSettings {
	private int borderRadius = 2;
	private Rectangle margins = SWTExtensions.INSTANCE.newInsets(2);
	private Rectangle paddings = SWTExtensions.INSTANCE.newInsets(2);
	private HSB shadowColor = HSB.GRAY;
	private int shadowRadius = 0;
	private Point shadowPosition = new Point(0, 0);
	private HSB closeButtonColor = HSB.GRAY;
	private HSB closeButtonHotColor = HSB.DARK_GRAY;
	private HSB closeButtonActiveColor = HSB.RED;
	private int closeButtonLineWidth = 3;
	private int borderWidth = 1;
	private int tabSpacing = 2;
	private int tabItemHorizontalSpacing = 1;
	private HSB[] unselectedBackgroundColors = null;
	private int[] unselectedBackgroundPercents = null;
	private HSB[] hoverBackgroundColors = null;
	private int[] hoverBackgroundPercents = null;
	private HSB hoverForgroundColor = null;
	private HSB unselectedTextShadowColor = null;
	private HSB selectedTextShadowColor = null;
	private HSB hoverTextShadowColor = null;
	private HSB[] borderColors = new HSB[] { HSB.GRAY, HSB.GRAY };
	private int[] borderPercents = new int[] { 100 };
	private HSB[] selectedBorderColors = new HSB[] { HSB.GRAY, HSB.GRAY };
	private int[] selectedBorderPercents = new int[] { 100 };
	private HSB[] unselectedBorderColors = new HSB[] { HSB.GRAY, HSB.GRAY };
	private int[] unselectedBorderPercents = new int[] { 100 };
	private HSB[] hoverBorderColors = new HSB[] { HSB.GRAY, HSB.GRAY };
	private int[] hoverBorderPercents = new int[] { 100 };
	private HSB chevronColor = new HSB(0, 0, 0);
	private Point selectedTextShadowPosition = new Point(0, 1);
	private Point unselectedTextShadowPosition = new Point(0, 1);
	private Point hoverTextShadowPosition = new Point(0, 1);
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private JeeeyulsTabRenderer renderer;
	private Rectangle tabItemPaddings = new Rectangle(2, 0, 2, 0);
	private VerticalAlignment closeButtonAlignment = VerticalAlignment.MIDDLE;

	private boolean truncateTabItems = true;

	private boolean useEllipses = false;

	private int minimumCharacters = 1;

	public JTabSettings(JeeeyulsTabRenderer renderer) {
		this.renderer = renderer;
	}

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		pcs.addPropertyChangeListener(arg0);
	}

	public void addPropertyChangeListener(String arg0, PropertyChangeListener arg1) {
		pcs.addPropertyChangeListener(arg0, arg1);
	}

	private boolean areSame(Object a, Object b) {
		if (a == null && b == null) {
			return true;
		} else if ((a == null && b != null) || (a != null && b == null)) {
			return false;
		} else {
			return a.equals(b);
		}
	}

	public HSB[] getBorderColors() {
		return borderColors;
	}

	public int[] getBorderPercents() {
		return borderPercents;
	}

	public int getBorderRadius() {
		return borderRadius;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public HSB getChevronColor() {
		return chevronColor;
	}

	public HSB getCloseButtonActiveColor() {
		return closeButtonActiveColor;
	}

	public VerticalAlignment getCloseButtonAlignment() {
		return closeButtonAlignment;
	}

	public HSB getCloseButtonColor() {
		return closeButtonColor;
	}

	public HSB getCloseButtonHotColor() {
		return closeButtonHotColor;
	}

	public int getCloseButtonLineWidth() {
		return closeButtonLineWidth;
	}

	public HSB[] getHoverBackgroundColors() {
		return hoverBackgroundColors;
	}

	public int[] getHoverBackgroundPercents() {
		return hoverBackgroundPercents;
	}

	public HSB[] getHoverBorderColors() {
		return hoverBorderColors;
	}

	public int[] getHoverBorderPercents() {
		return hoverBorderPercents;
	}

	public HSB getHoverForgroundColor() {
		return hoverForgroundColor;
	}

	public HSB getHoverTextShadowColor() {
		return hoverTextShadowColor;
	}

	public Point getHoverTextShadowPosition() {
		return hoverTextShadowPosition;
	}

	public Rectangle getMargins() {
		return margins;
	}

	public int getMinimumCharacters() {
		return minimumCharacters;
	}

	public Rectangle getPaddings() {
		return paddings;
	}

	public JeeeyulsTabRenderer getRenderer() {
		return renderer;
	}

	public HSB[] getSelectedBorderColors() {
		return selectedBorderColors;
	}

	public int[] getSelectedBorderPercents() {
		return selectedBorderPercents;
	}

	public HSB getSelectedTextShadowColor() {
		return selectedTextShadowColor;
	}

	public Point getSelectedTextShadowPosition() {
		return selectedTextShadowPosition;
	}

	public HSB getShadowColor() {
		return shadowColor;
	}

	public Point getShadowPosition() {
		return shadowPosition;
	}

	public int getShadowRadius() {
		return shadowRadius;
	}

	public int getTabItemHorizontalSpacing() {
		return tabItemHorizontalSpacing;
	}

	public Rectangle getTabItemPaddings() {
		return tabItemPaddings;
	}

	public int getTabSpacing() {
		return tabSpacing;
	}

	public HSB[] getUnselectedBackgroundColors() {
		return unselectedBackgroundColors;
	}

	public int[] getUnselectedBackgroundPercents() {
		return unselectedBackgroundPercents;
	}

	public HSB[] getUnselectedBorderColors() {
		return unselectedBorderColors;
	}

	public int[] getUnselectedBorderPercents() {
		return unselectedBorderPercents;
	}

	public HSB getUnselectedTextShadowColor() {
		return unselectedTextShadowColor;
	}

	public Point getUnselectedTextShadowPosition() {
		return unselectedTextShadowPosition;
	}

	public boolean isTruncateTabItems() {
		return truncateTabItems;
	}

	public boolean isUseEllipses() {
		return useEllipses;
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		pcs.removePropertyChangeListener(arg0);
	}

	public void removePropertyChangeListener(String arg0, PropertyChangeListener arg1) {
		pcs.removePropertyChangeListener(arg0, arg1);
	}

	public void setBorderColors(HSB[] borderColors) {
		if (areSame(this.borderColors, borderColors)) {
			return;
		}
		HSB[] old = borderColors;
		this.borderColors = borderColors;
		pcs.firePropertyChange("border-colors", old, borderColors);
	}

	public void setBorderPercents(int[] borderPercents) {
		if (areSame(this.borderPercents, borderPercents)) {
			return;
		}
		int[] old = this.borderPercents;
		this.borderPercents = borderPercents;
		pcs.firePropertyChange("border-percents", old, borderPercents);
	}

	public void setBorderRadius(int borderRadius) {
		if (this.borderRadius == borderRadius) {
			return;
		}
		int old = this.borderRadius;
		this.borderRadius = borderRadius;
		pcs.firePropertyChange("border-radius", old, borderRadius);
	}

	public void setChevronColor(HSB chrveronColor) {
		if (areSame(this.chevronColor, chrveronColor)) {
			return;
		}

		HSB old = this.chevronColor;
		this.chevronColor = chrveronColor;
		pcs.firePropertyChange("chevron-color", old, chrveronColor);
	}

	public void setCloseButtonActiveColor(HSB closeButtonActiveColor) {
		if (areSame(this.closeButtonActiveColor, closeButtonActiveColor)) {
			return;
		}
		HSB old = this.closeButtonActiveColor;
		this.closeButtonActiveColor = closeButtonActiveColor;
		pcs.firePropertyChange("close-button-active-color", old, closeButtonActiveColor);
	}

	public void setCloseButtonAlignment(VerticalAlignment closeButtonAlignment) {
		if (this.closeButtonAlignment == closeButtonAlignment) {
			return;
		}
		VerticalAlignment old = this.closeButtonAlignment;
		this.closeButtonAlignment = closeButtonAlignment;
		pcs.firePropertyChange("close-button-alignement", old, closeButtonAlignment);
	}

	public void setCloseButtonColor(HSB closeButtonColor) {
		if (areSame(this.closeButtonColor, closeButtonColor)) {
			return;
		}
		HSB old = this.closeButtonColor;
		this.closeButtonColor = closeButtonColor;
		pcs.firePropertyChange("close-button-color", old, closeButtonColor);
	}

	public void setCloseButtonHotColor(HSB closeButtonHotColor) {
		if (areSame(this.closeButtonHotColor, closeButtonHotColor)) {
			return;
		}
		HSB old = this.closeButtonHotColor;
		this.closeButtonHotColor = closeButtonHotColor;
		pcs.firePropertyChange("close-button-hot-color", old, closeButtonHotColor);
	}

	public void setCloseButtonLineWidth(int closeButtonLineWidth) {
		if (this.closeButtonLineWidth == closeButtonLineWidth) {
			return;
		}
		int old = this.closeButtonLineWidth;
		this.closeButtonLineWidth = closeButtonLineWidth;
		pcs.firePropertyChange("close-button-line-width", old, closeButtonLineWidth);
	}

	public void setHoverBackgroundColors(HSB[] hoverBackgroundColors) {
		if (areSame(this.hoverBackgroundColors, hoverBackgroundColors)) {
			return;
		}
		HSB[] old = this.hoverBackgroundColors;
		this.hoverBackgroundColors = hoverBackgroundColors;
		pcs.firePropertyChange("hover-background-colors", old, hoverBackgroundColors);
	}

	public void setHoverBackgroundPercents(int[] hoverBackgroundPercents) {
		if (areSame(this.hoverBackgroundPercents, hoverBackgroundPercents)) {
			return;
		}
		int[] old = this.hoverBackgroundPercents;
		this.hoverBackgroundPercents = hoverBackgroundPercents;
		pcs.firePropertyChange("hover-background-percents", old, hoverBackgroundPercents);
	}

	public void setHoverBorderColors(HSB[] hoverBorderColors) {
		if (areSame(this.hoverBorderColors, hoverBorderColors)) {
			return;
		}
		HSB[] old = hoverBorderColors;
		this.hoverBorderColors = hoverBorderColors;
		pcs.firePropertyChange("hover-border-colors", old, hoverBorderColors);
	}

	public void setHoverBorderPercents(int[] hoverBorderPercents) {
		if (areSame(this.hoverBorderPercents, hoverBorderPercents)) {
			return;
		}
		int[] old = this.hoverBorderPercents;
		this.hoverBorderPercents = hoverBorderPercents;
		pcs.firePropertyChange("hover-border-percents", old, hoverBorderPercents);
	}

	public void setHoverForgroundColor(HSB hoverForgroundColor) {
		if (areSame(this.hoverForgroundColor, hoverForgroundColor)) {
			return;
		}
		HSB old = this.hoverForgroundColor;
		this.hoverForgroundColor = hoverForgroundColor;
		pcs.firePropertyChange("hover-forground-color", old, hoverForgroundColor);
	}

	public void setHoverTextShadowColor(HSB hoverTextShadowColor) {
		if (areSame(this.hoverTextShadowColor, hoverTextShadowColor)) {
			return;
		}
		HSB old = this.hoverTextShadowColor;
		this.hoverTextShadowColor = hoverTextShadowColor;
		pcs.firePropertyChange("hover-text-shadow-color", old, hoverTextShadowColor);
	}

	public void setHoverTextShadowPosition(Point hoverTextShadowPosition) {
		if (areSame(this.hoverTextShadowPosition, hoverTextShadowPosition)) {
			return;
		}
		Point old = this.hoverTextShadowPosition;
		this.hoverTextShadowPosition = hoverTextShadowPosition;
		pcs.firePropertyChange("hover-text-shadow-position", old, hoverTextShadowPosition);
	}

	public void setMargins(Rectangle margins) {
		if (areSame(this.margins, margins)) {
			return;
		}
		Rectangle old = this.margins;
		this.margins = margins;
		pcs.firePropertyChange("margins", old, margins);
	}

	public void setMinimumCharacters(int minimumChars) {
		if (areSame(this.minimumCharacters, minimumChars)) {
			return;
		}
		int old = this.minimumCharacters;
		this.minimumCharacters = minimumChars;
		pcs.firePropertyChange("minimum-characters", old, minimumChars);
	}

	public void setPaddings(Rectangle paddings) {
		if (areSame(this.paddings, paddings)) {
			return;
		}
		Rectangle old = this.paddings;
		this.paddings = paddings;
		pcs.firePropertyChange("paddings", old, paddings);
	}

	public void setSelectedBorderColors(HSB[] selectedBorderColors) {
		if (areSame(this.selectedBorderColors, selectedBorderColors)) {
			return;
		}

		HSB[] old = selectedBorderColors;
		this.selectedBorderColors = selectedBorderColors;
		pcs.firePropertyChange("selected-border-colors", old, selectedBorderColors);
	}

	public void setSelectedBorderPercents(int[] selectedBorderPercents) {
		if (areSame(this.selectedBorderPercents, selectedBorderPercents)) {
			return;
		}
		int[] old = this.selectedBorderPercents;
		this.selectedBorderPercents = selectedBorderPercents;
		pcs.firePropertyChange("selected-border-percents", old, selectedBorderPercents);
	}

	public void setSelectedTextShadowColor(HSB selectedTextShadowColor) {
		if (areSame(this.selectedTextShadowColor, selectedTextShadowColor)) {
			return;
		}
		HSB old = selectedTextShadowColor;
		this.selectedTextShadowColor = selectedTextShadowColor;
		pcs.firePropertyChange("selected-text-shadow-color", old, selectedTextShadowColor);
	}

	public void setSelectedTextShadowPosition(Point selectedTextShadowPosition) {
		if (areSame(this.selectedTextShadowPosition, selectedTextShadowPosition)) {
			return;
		}
		Point old = this.selectedTextShadowPosition;
		this.selectedTextShadowPosition = selectedTextShadowPosition;
		pcs.firePropertyChange("selected-text-shadow-position", old, selectedTextShadowPosition);
	}

	public void setShadowColor(HSB shadowColor) {
		if (areSame(this.shadowColor, shadowColor)) {
			return;
		}
		HSB old = this.shadowColor;
		this.shadowColor = shadowColor;
		pcs.firePropertyChange("shadow-color", old, shadowColor);
	}

	public void setShadowPosition(Point shadowPosition) {
		if (areSame(this.shadowPosition, shadowPosition)) {
			return;
		}
		Point old = this.shadowPosition;
		this.shadowPosition = shadowPosition;
		pcs.firePropertyChange("shadow-position", old, shadowPosition);
	}

	public void setShadowRadius(int shadowRadius) {
		if (this.shadowRadius == shadowRadius) {
			return;
		}
		int old = this.shadowRadius;
		this.shadowRadius = shadowRadius;
		pcs.firePropertyChange("shadow-radius", old, shadowRadius);

	}

	public void setTabItemHorizontalSpacing(int tabItemHorizontalSpacing) {
		if (this.tabItemHorizontalSpacing == tabItemHorizontalSpacing) {
			return;
		}
		int old = this.tabItemHorizontalSpacing;
		this.tabItemHorizontalSpacing = tabItemHorizontalSpacing;
		pcs.firePropertyChange("tab-item-horizontal-spacing", old, tabItemHorizontalSpacing);
	}

	public void setTabItemPaddings(Rectangle tabItemPaddings) {
		if (areSame(this.tabItemPaddings, tabItemPaddings)) {
			return;
		}
		Rectangle old = this.tabItemPaddings;
		this.tabItemPaddings = tabItemPaddings;
		pcs.firePropertyChange("tab-item-paddings", old, tabItemPaddings);
	}

	public void setTabSpacing(int tabSpacing) {
		if (this.tabSpacing == tabSpacing) {
			return;
		}
		int old = this.tabSpacing;
		this.tabSpacing = tabSpacing;
		pcs.firePropertyChange("tab-spacing", old, tabSpacing);
	}

	public void setTruncateTabItems(boolean truncateTabItems) {
		if (areSame(this.truncateTabItems, truncateTabItems)) {
			return;
		}
		boolean old = this.truncateTabItems;
		this.truncateTabItems = truncateTabItems;
		pcs.firePropertyChange("truncate-tab-items", old, truncateTabItems);
	}

	public void setUnselectedBackgroundColors(HSB[] unselectedBackgroundColors) {
		if (areSame(this.unselectedBackgroundColors, unselectedBackgroundColors)) {
			return;
		}
		HSB[] old = this.unselectedBackgroundColors;
		this.unselectedBackgroundColors = unselectedBackgroundColors;

		pcs.firePropertyChange("unselected-background-colors", old, unselectedBackgroundColors);

	}

	public void setUnselectedBackgroundPercents(int[] unselectedBackgroundPercents) {
		if (areSame(this.unselectedBackgroundPercents, unselectedBackgroundPercents)) {
			return;
		}

		int[] old = this.unselectedBackgroundPercents;
		this.unselectedBackgroundPercents = unselectedBackgroundPercents;
		this.pcs.firePropertyChange("unselected-background-percents", old, unselectedBackgroundPercents);
	}

	public void setUnselectedBorderColors(HSB[] unselectedBorderColors) {
		if (areSame(this.unselectedBorderColors, unselectedBorderColors)) {
			return;
		}
		HSB[] old = this.unselectedBorderColors;
		this.unselectedBorderColors = unselectedBorderColors;
		pcs.firePropertyChange("unselected-border-colors", old, unselectedBorderColors);
	}

	public void setUnselectedBorderPercents(int[] unselectedBorderPercents) {
		if (areSame(this.unselectedBorderPercents, unselectedBorderPercents)) {
			return;
		}
		int[] old = this.unselectedBorderPercents;
		this.unselectedBorderPercents = unselectedBorderPercents;
		pcs.firePropertyChange("unselected-border-percents", old, unselectedBorderPercents);
	}

	public void setUnselectedTextShadowColor(HSB unselectedTextShadowColor) {
		if (areSame(this.unselectedTextShadowColor, unselectedTextShadowColor)) {
			return;
		}
		HSB old = this.unselectedTextShadowColor;
		this.unselectedTextShadowColor = unselectedTextShadowColor;
		pcs.firePropertyChange("unselected-text-shadow-color", old, unselectedTextShadowColor);
	}

	public void setUnselectedTextShadowPosition(Point unselectedTextShadowPosition) {
		if (areSame(this.unselectedTextShadowPosition, unselectedTextShadowPosition)) {
			return;
		}
		Point old = this.unselectedTextShadowPosition;
		this.unselectedTextShadowPosition = unselectedTextShadowPosition;
		pcs.firePropertyChange("unselected-text-shadow-position", old, unselectedTextShadowPosition);
	}

	public void setUseEllipses(boolean useEllipses) {
		if (this.useEllipses == useEllipses) {
			return;
		}
		boolean old = this.useEllipses;
		this.useEllipses = useEllipses;
		pcs.firePropertyChange("use-ellipses", old, this.useEllipses);
	}
}
