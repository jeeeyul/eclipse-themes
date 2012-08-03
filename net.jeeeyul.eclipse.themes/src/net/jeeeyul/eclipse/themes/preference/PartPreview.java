package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.preference.internal.ColorFactory;
import net.jeeeyul.eclipse.themes.preference.internal.FontFactory;
import net.jeeeyul.eclipse.themes.preference.internal.ResourceRegistry;
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;
import net.jeeeyul.eclipse.themes.rendering.HackedCTabRendering;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class PartPreview {
	private CTabFolder folder;

	private ResourceRegistry<RGB, Color> colorRegistry = new ResourceRegistry<RGB, Color>(new ColorFactory());
	private ResourceRegistry<FontData, Font> fontRegistry = new ResourceRegistry<FontData, Font>(new FontFactory());

	private RGB gradientStart = new RGB(0, 0, 0);
	private RGB gradientEnd = new RGB(0, 0, 0);
	private RGB outline = new RGB(0, 0, 0);
	private RGB selectedTitle = new RGB(0, 0, 0);
	private RGB unselectedTitle = new RGB(0, 0, 0);
	private boolean castShinyShadow;
	private String fontName = "Segoe UI";
	private float fontSize = 9f;

	public PartPreview(CTabFolder folder) {
		this.folder = folder;
	}

	private Color getColor(RGB rgb) {
		return colorRegistry.get(rgb);
	}

	public void dispose() {
		colorRegistry.dispose();
		fontRegistry.dispose();
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

		Color[] gradient = new Color[] { getColor(gradientStart), getColor(gradientEnd), folder.getDisplay().getSystemColor(SWT.COLOR_WHITE) };
		folder.setBackground(gradient, new int[] { 99, 100 }, true);

		FontData oldFont = folder.getFont().getFontData()[0];
		FontData newFont = getFontData();

		boolean updateFont = !oldFont.getName().equals(newFont.getName()) || oldFont.height != newFont.height;
		if (updateFont) {
			Font font = fontRegistry.get(newFont);
			folder.setFont(font);
			try {
				HackedCTabRendering.HACK_CTabFolder_updateItems.invoke(folder);
			} catch (Exception e) {
				e.printStackTrace();
			}
			folder.getParent().layout(new Control[] { folder });
		}

	}

	public void setCastShinyShadow(boolean castShinyShadow) {
		this.castShinyShadow = castShinyShadow;
	}

	private FontData getFontData() {
		FontData data = new FontData();
		data.setName(fontName);
		data.height = fontSize;
		return data;
	}

	public void setFontName(String fontName) {
		if (fontName == null) {
			fontName = Display.getCurrent().getSystemFont().getFontData()[0].getName();
		}
		this.fontName = fontName;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
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

	public void setSelectedTitle(RGB selectedTitle) {
		this.selectedTitle = selectedTitle;
	}

	public void setUnselectedTitle(RGB unselectedTitle) {
		this.unselectedTitle = unselectedTitle;
	}

}
