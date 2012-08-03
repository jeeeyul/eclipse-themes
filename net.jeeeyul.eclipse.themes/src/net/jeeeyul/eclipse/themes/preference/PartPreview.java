package net.jeeeyul.eclipse.themes.preference;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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

	public PartPreview(CTabFolder folder) {
		super("Preview Gradient");
		this.folder = folder;
		folder.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				dispose();
			}
		});
		setSystem(true);
		setDisplay(folder.getDisplay());
	}

	private void dispose() {
		cancel();
		dispose(gradientStartColor);
		dispose(outlineColor);
		dispose(gradientEndColor);
		dispose(selectedTitleColor);
		dispose(unselectedTitleColor);
		dispose(font);
	}

	private void dispose(Resource resource) {
		if (resource != null && !resource.isDisposed()) {
			resource.dispose();
		}
	}

	public Font getFont() {
		if (font == null || font.isDisposed()) {
			try {
				FontData data = new FontData();
				data.setName(fontName);
				data.height = fontSize;
				font = new Font(folder.getDisplay(), data);
			} catch (Exception e) {
				FontData[] fallbackDatas = folder.getDisplay().getSystemFont().getFontData();
				font = new Font(folder.getDisplay(), fallbackDatas);
			}
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
		if (gradientEndColor == null || gradientEndColor.isDisposed()) {
			gradientEndColor = new Color(folder.getDisplay(), gradientEnd);
		}
		return gradientEndColor;
	}

	public RGB getGradientStart() {
		return gradientStart;
	}

	private Color getGradientStartColor() {
		if (gradientStartColor == null || gradientStartColor.isDisposed()) {
			gradientStartColor = new Color(folder.getDisplay(), gradientStart);
		}
		return gradientStartColor;
	}

	public RGB getOutline() {
		return outline;
	}

	private Color getOutlineColor() {
		if (outlineColor == null || outlineColor.isDisposed()) {
			outlineColor = new Color(folder.getDisplay(), outline);
		}
		return outlineColor;
	}

	public RGB getSelectedTitle() {
		return selectedTitle;
	}

	protected Color getSelectedTitleColor() {
		if (selectedTitleColor == null || selectedTitleColor.isDisposed()) {
			selectedTitleColor = new Color(folder.getDisplay(), selectedTitle);
		}
		return selectedTitleColor;
	}

	public RGB getUnselectedTitle() {
		return unselectedTitle;
	}

	protected Color getUnselectedTitleColor() {
		if (unselectedTitleColor == null || unselectedTitleColor.isDisposed()) {
			unselectedTitleColor = new Color(folder.getDisplay(), unselectedTitle);
		}
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

		dispose();

		folder.setBackground(getGradientArray(), new int[] { 99, 100 }, true);
		ChromeTabRendering renderer = (ChromeTabRendering) folder.getRenderer();
		renderer.setOuterKeyline(getOutlineColor());
		renderer.setTabOutline(getOutlineColor());
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
				folder.getParent().update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		folder.update();

		return Status.OK_STATUS;
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
