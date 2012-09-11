package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.preference.internal.FontFactory;
import net.jeeeyul.eclipse.themes.preference.internal.ResourceRegistry;
import net.jeeeyul.eclipse.themes.rendering.HackedCTabRendering;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class FontPreview {
	private CTabFolder folder;
	private String fontName;
	private float fontHeight;

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public float getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(float fontHeight) {
		this.fontHeight = fontHeight;
	}

	private ResourceRegistry<FontData, Font> fontRegistry = new ResourceRegistry<FontData, Font>(new FontFactory());

	public FontPreview(CTabFolder folder) {
		this.folder = folder;
	}

	public void dispose() {
		fontRegistry.dispose();
	}

	public void run() {
		if (folder.isDisposed()) {
			return;
		}

		FontData data = new FontData();
		try {
			data.setName(fontName);
			data.height = fontHeight;
		} catch (Exception e) {
			System.out.println("Fall back font was used to preview");
			data = Display.getDefault().getSystemFont().getFontData()[0];
		}

		Font newFont = fontRegistry.get(data);
		
		if(newFont == folder.getFont()){
			return;
		}
		
		folder.setFont(newFont);
		try {
			HackedCTabRendering.HACK_CTabFolder_updateItems.invoke(folder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		folder.getParent().layout(true, true);

	}

}
