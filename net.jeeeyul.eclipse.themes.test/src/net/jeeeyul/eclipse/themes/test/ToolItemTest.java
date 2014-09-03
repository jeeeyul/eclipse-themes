package net.jeeeyul.eclipse.themes.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.graphics.Color;

public class ToolItemTest {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		ToolBar toolBar = new ToolBar(shell, SWT.FLAT);
		ToolItem item1 = new ToolItem(toolBar, SWT.PUSH);
		item1.setText("Hello");

		// Changes color
		toolBar.setForeground(new Color(Display.getDefault(), 255, 0, 0));

		ToolItem item2 = new ToolItem(toolBar, 0);
		item2.setText("World");

		item1.setText("");
		item1.setText("Hello");

		shell.pack();

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
