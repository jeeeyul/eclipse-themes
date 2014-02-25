package net.jeeeyul.eclipse.themes.rendering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

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
	private int tabItemHorizontalSpacing = 4;
	private HSB borderColor = HSB.GRAY;
	private HSB selectedBorderColor = HSB.GRAY;
	private HSB unselectedBorderColor = HSB.GRAY;
	private HSB[] unselectedBackgroundColors = null;
	private int[] unselectedBackgroundPercents = null;
	private HSB[] hoverBackgroundColors = null;
	private int[] hoverBackgroundPercents = null;
	private HSB hoverBorderColor = null;
	private HSB hoverForgroundColor = null;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public JTabSettings() {
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

	public HSB getBorderColor() {
		return borderColor;
	}

	public int getBorderRadius() {
		return borderRadius;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public HSB getCloseButtonActiveColor() {
		return closeButtonActiveColor;
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

	public HSB getHoverBorderColor() {
		return hoverBorderColor;
	}

	public HSB getHoverForgroundColor() {
		return hoverForgroundColor;
	}

	public Rectangle getMargins() {
		return margins;
	}

	public Rectangle getPaddings() {
		return paddings;
	}

	public HSB getSelectedBorderColor() {
		return selectedBorderColor;
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

	public int getTabSpacing() {
		return tabSpacing;
	}

	public HSB[] getUnselectedBackgroundColors() {
		return unselectedBackgroundColors;
	}

	public int[] getUnselectedBackgroundPercents() {
		return unselectedBackgroundPercents;
	}

	public HSB getUnselectedBorderColor() {
		return unselectedBorderColor;
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		pcs.removePropertyChangeListener(arg0);
	}

	public void removePropertyChangeListener(String arg0, PropertyChangeListener arg1) {
		pcs.removePropertyChangeListener(arg0, arg1);
	}

	public void setBorderColor(HSB borderColor) {
		if (areSame(this.borderColor, borderColor)) {
			return;
		}
		HSB old = this.borderColor;
		this.borderColor = borderColor;
		pcs.firePropertyChange("border-color", old, borderColor);
	}

	public void setBorderRadius(int borderRadius) {
		if (this.borderRadius == borderRadius) {
			return;
		}
		int old = this.borderRadius;
		this.borderRadius = borderRadius;
		pcs.firePropertyChange("border-radius", old, borderRadius);
	}

	public void setBorderWidth(int borderWidth) {
		if (this.borderWidth == borderWidth) {
			return;
		}
		int old = this.borderWidth;
		this.borderWidth = borderWidth;
		pcs.firePropertyChange("border-width", old, borderWidth);
	}

	public void setCloseButtonActiveColor(HSB closeButtonActiveColor) {
		if (areSame(this.closeButtonActiveColor, closeButtonActiveColor)) {
			return;
		}
		HSB old = this.closeButtonActiveColor;
		this.closeButtonActiveColor = closeButtonActiveColor;
		pcs.firePropertyChange("close-button-active-color", old, closeButtonActiveColor);
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

	public void setHoverBorderColor(HSB hoverBorderColor) {
		if (areSame(this.hoverBorderColor, hoverBorderColor)) {
			return;
		}
		HSB old = this.hoverBorderColor;
		this.hoverBorderColor = hoverBorderColor;
		pcs.firePropertyChange("hover-border-color", old, hoverBorderColor);
	}

	public void setHoverForgroundColor(HSB hoverForgroundColor) {
		if (areSame(this.hoverForgroundColor, hoverForgroundColor)) {
			return;
		}
		HSB old = this.hoverForgroundColor;
		this.hoverForgroundColor = hoverForgroundColor;
		pcs.firePropertyChange("hover-forground-color", old, hoverForgroundColor);
	}

	public void setMargins(Rectangle margins) {
		if (areSame(this.margins, margins)) {
			return;
		}
		Rectangle old = this.margins;
		this.margins = margins;
		pcs.firePropertyChange("margins", old, margins);
	}

	public void setPaddings(Rectangle paddings) {
		if (areSame(this.paddings, paddings)) {
			return;
		}
		Rectangle old = this.paddings;
		this.paddings = paddings;
		pcs.firePropertyChange("paddings", old, paddings);
	}

	public void setSelectedBorderColor(HSB selectedBorderColor) {
		if (areSame(this.selectedBorderColor, selectedBorderColor)) {
			return;
		}
		HSB old = this.selectedBorderColor;
		this.selectedBorderColor = selectedBorderColor;
		pcs.firePropertyChange("selected-border-color", old, selectedBorderColor);
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

	public void setTabSpacing(int tabSpacing) {
		if (this.tabSpacing == tabSpacing) {
			return;
		}
		int old = this.tabSpacing;
		this.tabSpacing = tabSpacing;
		pcs.firePropertyChange("tab-spacing", old, tabSpacing);
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

	public void setUnselectedBorderColor(HSB unselectedBorderColor) {
		if (areSame(this.unselectedBorderColor, unselectedBorderColor)) {
			return;
		}
		HSB old = this.unselectedBorderColor;
		this.unselectedBorderColor = unselectedBorderColor;
		pcs.firePropertyChange("unselected-border-color", old, unselectedBorderColor);
	}

}
