package net.jeeeyul.eclipse.themes.rendering;

import net.jeeeyul.eclipse.themes.SharedImages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class KTabRendererTest {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);

		FillLayout layout = new FillLayout();
		layout.marginWidth = layout.marginHeight = 20;
		shell.setLayout(layout);
		CTabFolder tabFolder = new CTabFolder(shell, SWT.NORMAL);
		tabFolder.setRenderer(new KTabRenderer(tabFolder));
		tabFolder.setMaximizeVisible(true);
		tabFolder.setMinimizeVisible(true);
		tabFolder.setBorderVisible(true);
		for (int i = 0; i < 20; i++) {
			CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
			item.setText("item" + i);
			item.setImage(SharedImages.getImage(SharedImages.PALETTE));
			Label label = new Label(tabFolder, SWT.NORMAL);
			label.setText("Content " + i);
			label.setSize(200, 200);
			item.setControl(label);
		}
		shell.setSize(400, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}
}
