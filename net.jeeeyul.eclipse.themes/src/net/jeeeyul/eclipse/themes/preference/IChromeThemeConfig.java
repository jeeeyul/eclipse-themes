package net.jeeeyul.eclipse.themes.preference;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public interface IChromeThemeConfig {
	public abstract RGB getActiveOulineColor();

	public abstract RGB getActivePartGradientEnd();

	public abstract RGB getActivePartGradientStart();

	public abstract RGB getActiveSelectedTitleColor();

	public abstract RGB getActiveUnselectedTitleColor();

	public abstract RGB getInactiveOulineColor();

	public abstract RGB getInactivePartGradientEnd();

	public abstract RGB getInactivePartGradientStart();

	public abstract RGB getInactiveSelectedTitleColor();

	public abstract RGB getInactiveUnselectedTitleColor();

	public abstract FontData getPartFontData();

	public abstract int getSashWidth();

	public abstract Boolean useActivePartTitleShadow();

	public Boolean useInactivePartTitleShadow();

	public abstract boolean usePartShadow();

	public abstract RGB getToolbarGradientStart();

	public abstract RGB getToolbarGradientEnd();

	public abstract RGB getWindowBackgroundColor();

	public abstract RGB getPerspectiveStartColor();

	public abstract RGB getPartShadowColor();

	public abstract RGB getPerspectiveEndColor();

	public abstract RGB getPerspectiveOutlineColor();

	public abstract RGB getEmptyPartOutloneColor();

	public abstract RGB getEmptyPartBackgroundColor();

	public abstract RGB getInactiveSelectedTabStartColor();

	public abstract RGB getInactiveSelectedTabEndColor();

	public abstract RGB getActiveSelectedTabStartColor();

	public abstract RGB getActiveSelectedTabEndColor();

	public abstract Integer getPartStackPadding();

	/**
	 * 35: Expose the mru-visible css property
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/35
	 * 
	 * @return
	 * @since 1.4
	 */
	public abstract Boolean getMruVisible();

	public abstract Integer getPartStackCornerRadius();

	public abstract Boolean getUseStatusBarOutline();

	public abstract Boolean getUseWindowBackgroundAsStatusBarBackground();

	public abstract RGB getStatusBarBackgroundColor();

	public abstract RGB getStatusBarOutlineColor();

	public abstract Boolean getUseTrimStackImageBorder();

	public abstract Boolean getUseEmbossedDragHandle();

	public abstract String getUserCSS();
}