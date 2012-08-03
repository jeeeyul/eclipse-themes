package net.jeeeyul.eclipse.themes.preference;

import java.util.ArrayList;

import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.progress.UIJob;

public class PartPreview extends UIJob {
	private CTabFolder folder;

	private RGB gradientStart = new RGB(0, 0, 0);
	private RGB gradientEnd = new RGB(0, 0, 0);
	private RGB outline = new RGB(0, 0, 0);
	private RGB selectedTitle = new RGB(0, 0, 0);
	private RGB unselectedTitle = new RGB(0, 0, 0);
	private boolean castShinyShadow;
	private String fontName = "Segoe UI";
	private float fontSize = 9f;
	private long delay = 25;

	private Color gradientStartColor;
	private Color gradientEndColor;
	private Color outlineColor;
	private Color selectedTitleColor;
	private Color unselectedTitleColor;
	private Font font;

	private ArrayList<Resource> disposeQueue = new ArrayList<Resource>();

	public PartPreview(CTabFolder folder) {
		super("Preview Gradient");
		this.folder = folder;
		setSystem(true);
		setDisplay(folder.getDisplay());
	}

	private void flush() {
		for (Resource each : disposeQueue) {
			safeDispose(each);
		}
		disposeQueue.clear();
	}

	public void dispose() {
		safeDispose(font);
		safeDispose(gradientEndColor);
		safeDispose(gradientStartColor);
		safeDispose(outlineColor);
		safeDispose(selectedTitleColor);
		safeDispose(unselectedTitleColor);
	}

	private void safeDispose(Resource each) {
		if (each != null && !each.isDisposed())
			each.dispose();
	}

	public Font getFont() {
		scheduleDispose(font);

		try {
			FontData data = new FontData();
			data.setName(fontName);
			data.height = fontSize;
			font = new Font(folder.getDisplay(), data);
		} catch (Exception e) {
			FontData[] fallbackDatas = folder.getDisplay().getSystemFont().getFontData();
			font = new Font(folder.getDisplay(), fallbackDatas);
		}
		return font;
	}

	public String getFontName() {
		return fontName;
	}

	public float getFontSize() {
		return fontSize;
	}

	private Color[] getGradientArray() {
		return new Color[] { getGradientStartColor(), getGradientEndColor(), folder.getDisplay().getSystemColor(SWT.COLOR_WHITE) };
	}

	public RGB getGradientEnd() {
		return gradientEnd;
	}

	private Color getGradientEndColor() {
		scheduleDispose(gradientEndColor);
		gradientEndColor = new Color(folder.getDisplay(), gradientEnd);
		return gradientEndColor;
	}

	public RGB getGradientStart() {
		return gradientStart;
	}

	private Color getGradientStartColor() {
		scheduleDispose(gradientStartColor);
		gradientStartColor = new Color(folder.getDisplay(), gradientStart);
		return gradientStartColor;
	}

	public RGB getOutline() {
		return outline;
	}

	private Color getOutlineColor() {
		scheduleDispose(outlineColor);
		outlineColor = new Color(folder.getDisplay(), outline);
		return outlineColor;
	}

	public RGB getSelectedTitle() {
		return selectedTitle;
	}

	protected Color getSelectedTitleColor() {
		scheduleDispose(selectedTitleColor);
		selectedTitleColor = new Color(folder.getDisplay(), selectedTitle);
		return selectedTitleColor;
	}

	public RGB getUnselectedTitle() {
		return unselectedTitle;
	}

	protected Color getUnselectedTitleColor() {
		scheduleDispose(unselectedTitleColor);
		unselectedTitleColor = new Color(folder.getDisplay(), unselectedTitle);
		return unselectedTitleColor;
	}

	public boolean isCastShinyShadow() {
		return castShinyShadow;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		if (folder.isDisposed()) {
			return Status.OK_STATUS;
		}

		folder.setBackground(getGradientArray(), new int[] { 99, 100 }, true);
		ChromeTabRendering renderer = (ChromeTabRendering) folder.getRenderer();
		Color outlineColor = getOutlineColor();

		renderer.setOuterKeyline(outlineColor);
		renderer.setTabOutline(outlineColor);

		renderer.setSelectedTabItemColor(getSelectedTitleColor());
		renderer.setUnselectedTabItemColor(getUnselectedTitleColor());
		renderer.setShowShineyShadow(castShinyShadow);

		boolean hasToUpdateFont = false;

		FontData current = folder.getFont().getFontData()[0];
		hasToUpdateFont = !current.getName().equals(fontName) || current.height != fontSize;

		if (hasToUpdateFont) {
			try {
				folder.setFont(getFont());
				folder.getParent().layout(new Control[] { folder });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		folder.update();
		flush();

		return Status.OK_STATUS;
	}

	private void scheduleDispose(Resource resource) {
		if (resource != null && !resource.isDisposed() && !disposeQueue.contains(resource))
			disposeQueue.add(resource);
	}

	public void setCastShinyShadow(boolean castShinyShadow) {
		this.castShinyShadow = castShinyShadow;
		schedule(delay);
	}

	public void setFontName(String fontName) {
		if (this.fontName == null && fontName == null) {
			return;
		}
		if (this.fontName != null && this.fontName.equals(fontName)) {
			return;
		}

		this.fontName = fontName;
		schedule(delay);
	}

	public void setFontSize(float fontSize) {
		if (this.fontSize == fontSize) {
			return;
		}
		this.fontSize = fontSize;
		schedule(delay);
	}

	public void setGradientEnd(RGB gradientEnd) {
		this.gradientEnd = gradientEnd;
		schedule(delay);
	}

	public void setGradientStart(RGB gradientStart) {
		this.gradientStart = gradientStart;
		schedule(delay);
	}

	public void setOutline(RGB outline) {
		this.outline = outline;
		schedule(delay);
	}

	public void setSelectedTitle(RGB selectedTitle) {
		this.selectedTitle = selectedTitle;
		schedule(delay);
	}

	public void setUnselectedTitle(RGB unselectedTitle) {
		this.unselectedTitle = unselectedTitle;
		schedule(delay);
	}

}
