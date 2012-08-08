package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.preference.internal.ColorFactory;
import net.jeeeyul.eclipse.themes.preference.internal.FontFactory;
import net.jeeeyul.eclipse.themes.preference.internal.ResourceRegistry;
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public class PartPreview {
	private CTabFolder folder;

	private ResourceRegistry<RGB, Color> colorRegistry = new ResourceRegistry<RGB, Color>(new ColorFactory());
	private ResourceRegistry<FontData, Font> fontRegistry = new ResourceRegistry<FontData, Font>(new FontFactory());

	private RGB gradientStart = new RGB(0, 0, 0);
	private RGB gradientEnd = new RGB(0, 0, 0);
	private RGB outline = new RGB(0, 0, 0);
	private RGB selectedTitle = new RGB(0, 0, 0);
	private RGB unselectedTitle = new RGB(0, 0, 0);
	private RGB selectedTabStart = new RGB(0, 0, 0);
	private RGB selectedTabEnd = new RGB(0, 0, 0);
	private boolean castShinyShadow;

	public PartPreview(CTabFolder folder) {
		this.folder = folder;
	}

	public void dispose() {
		colorRegistry.dispose();
		fontRegistry.dispose();
	}

	private Color getColor(RGB rgb) {
		return colorRegistry.get(rgb);
	}

	public RGB getGradientEnd() {
		return gradientEnd;
	}

	public RGB getGradientStart() {
		return gradientStart;
	}

	public RGB getOutline() {
		return outline;
	}

	public RGB getSelectedTabEnd() {
		return selectedTabEnd;
	}

	public RGB getSelectedTabStart() {
		return selectedTabStart;
	}

	public RGB getSelectedTitle() {
		return selectedTitle;
	}

	public RGB getUnselectedTitle() {
		return unselectedTitle;
	}

	public boolean isCastShinyShadow() {
		return castShinyShadow;
	}

	public void run() {
		if (folder.isDisposed()) {
			return;
		}

		ChromeTabRendering renderer = (ChromeTabRendering) folder.getRenderer();

		renderer.setShowShineyShadow(castShinyShadow);
		renderer.setOuterKeyline(getColor(outline));
		renderer.setTabOutline(getColor(outline));
		renderer.setSelectedTabItemColor(getColor(selectedTitle));
		renderer.setUnselectedTabItemColor(getColor(unselectedTitle));
		renderer.setSelectedTabFill(getColor(selectedTabEnd));
		renderer.setSelectedTabFillHighlightColor(getColor(selectedTabStart));
		Color[] gradient = new Color[] { getColor(gradientStart), getColor(gradientEnd), folder.getDisplay().getSystemColor(SWT.COLOR_WHITE) };
		folder.setBackground(gradient, new int[] { 99, 100 }, true);
		
		folder.setBackground(getColor(selectedTabEnd));
	}

	public void setCastShinyShadow(boolean castShinyShadow) {
		this.castShinyShadow = castShinyShadow;
	}

	public void setGradientEnd(RGB gradientEnd) {
		this.gradientEnd = gradientEnd;
	}

	public void setGradientStart(RGB gradientStart) {
		this.gradientStart = gradientStart;
	}

	public void setOutline(RGB outline) {
		this.outline = outline;
	}

	public void setSelectedTabEnd(RGB selectedTabEnd) {
		this.selectedTabEnd = selectedTabEnd;
	}

	public void setSelectedTabStart(RGB selectedTabStart) {
		this.selectedTabStart = selectedTabStart;
	}

	public void setSelectedTitle(RGB selectedTitle) {
		this.selectedTitle = selectedTitle;
	}

	public void setUnselectedTitle(RGB unselectedTitle) {
		this.unselectedTitle = unselectedTitle;
	}

}
