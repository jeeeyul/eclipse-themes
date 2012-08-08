package net.jeeeyul.eclipse.themes.experiment;

import net.jeeeyul.eclipse.themes.ui.KRectangle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class KultokiTabRenderer extends CTabFolderRenderer {
	private CTabFolder parent;
	Region oldClip = new Region();

	public KultokiTabRenderer(CTabFolder parent) {
		super(parent);
		this.parent = parent;
	}

	@Override
	protected void draw(int part, int state, Rectangle bounds, GC gc) {
		if (part >= 0 && (state & SWT.FOREGROUND) != 0) {

			gc.getClipping(oldClip);
			gc.setClipping(bounds);
			gc.drawRoundRectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height + 10, 10, 10);
			gc.setClipping(oldClip);

			CTabItem item = parent.getItem(part);
			if (item.getText() != null) {
				gc.drawString(item.getText(), bounds.x, bounds.y, true);
			}

			super.draw(part, state, bounds, gc);
		} else
			super.draw(part, state, bounds, gc);
	}

	@Override
	protected Point computeSize(int part, int state, GC gc, int wHint, int hHint) {
		if (part >= 0) {
			Point size = super.computeSize(part, state, gc, wHint, hHint);
			size.y = 30;
			return size;
		} else if (part == PART_CLOSE_BUTTON) {
			return new Point(40, 40);
		} else {
			return super.computeSize(part, state, gc, wHint, hHint);
		}
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		CTabFolder folder = new CTabFolder(shell, SWT.CLOSE);
		folder.setRenderer(new KultokiTabRenderer(folder));
		folder.setBorderVisible(true);

		for (int i = 0; i < 5; i++) {
			CTabItem item = new CTabItem(folder, SWT.NORMAL);
			Label label = new Label(folder, SWT.NORMAL);
			label.setText("content");
			item.setControl(label);
			item.setText("Tab");
		}
		folder.setSelection(0);

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
