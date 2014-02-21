package net.jeeeyul.eclipse.themes.rendering;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class KTabRendererTest {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);

		FillLayout layout = new FillLayout();
		layout.marginWidth = layout.marginHeight = 10;
		layout.spacing = 10;
		shell.setLayout(layout);

		SWTExtensions.INSTANCE.showTestGrid(shell);

		createExampleTab(shell, SWT.TOP);
		createExampleTab(shell, SWT.BOTTOM);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

	private static void createExampleTab(Shell shell, int tabPosition) {
		CTabFolder tabFolder = new CTabFolder(shell, SWT.NONE);
		tabFolder.setMaximizeVisible(true);
		tabFolder.setMinimizeVisible(true);
		tabFolder.setMRUVisible(true);
		tabFolder.setTabPosition(tabPosition);
		tabFolder.setRenderer(new KTabRenderer(tabFolder));

		for (int i = 0; i < 20; i++) {
			CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
			item.setText("item" + i);
			item.setImage(SharedImages.getImage(SharedImages.PALETTE));
			SizeBox content = new SizeBox(tabFolder, SWT.NORMAL);
			content.setSize(200, 200);
			content.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_CYAN));
			item.setControl(content);
		}

		tabFolder.setSelection(tabFolder.getItems()[0]);
	}
}
