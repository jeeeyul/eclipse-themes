package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.progress.UIJob;

public class GradientPreview extends UIJob {
	private CTabFolder folder;

	private RGB gradientStart = new RGB(0, 0, 0);
	private RGB gradientEnd = new RGB(0, 0, 0);
	private RGB outline = new RGB(0, 0, 0);

	private Color gradientStartColor;
	private Color gradientEndColor;

	private Color outlineColor;

	public GradientPreview(CTabFolder folder) {
		super("Preview Gradient");
		this.folder = folder;
		folder.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				dispose();
			}
		});
		setSystem(true);
	}

	private void dispose() {
		cancel();
		dispose(gradientStartColor);
		dispose(outlineColor);
		dispose(gradientEndColor);
	}

	private void dispose(Resource resource) {
		if (resource != null && !resource.isDisposed()) {
			resource.dispose();
		}
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

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		if (folder.isDisposed()) {
			return Status.OK_STATUS;
		}

		folder.setBackground(getGradientArray(), new int[] { 99, 100 }, true);
		ChromeTabRendering renderer = (ChromeTabRendering) folder.getRenderer();
		renderer.setOuterKeyline(getOutlineColor());
		renderer.setTabOutline(getOutlineColor());

		return Status.OK_STATUS;
	}

	private Color[] getGradientArray() {
		return new Color[] { getGradientStartColor(), getGradientEndColor(), folder.getDisplay().getSystemColor(SWT.COLOR_WHITE) };
	}

	public void setGradientEnd(RGB gradientEnd) {
		dispose(gradientEndColor);
		this.gradientEnd = gradientEnd;
		schedule(10);
	}

	public void setGradientStart(RGB gradientStart) {
		dispose(gradientStartColor);
		this.gradientStart = gradientStart;
		schedule(10);
	}

	public void setOutline(RGB outline) {
		dispose(outlineColor);
		this.outline = outline;
		schedule(10);
	}

}
