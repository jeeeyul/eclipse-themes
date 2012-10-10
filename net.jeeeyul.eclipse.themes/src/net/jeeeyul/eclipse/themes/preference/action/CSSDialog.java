package net.jeeeyul.eclipse.themes.preference.action;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CSSDialog extends Dialog {
	public static void main(String[] args) {
		new CSSDialog(null).open();
	}

	private Text text;

	private String cssContent;

	public CSSDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.SHELL_TRIM);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("CSS");

		Composite container = (Composite) super.createDialogArea(parent);

		text = new Text(container, SWT.V_SCROLL | SWT.BORDER | SWT.MULTI);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.setEditable(false);
		if (cssContent != null) {
			text.setText(cssContent);
		}

		return container;
	}

	public String getCssContent() {
		return cssContent;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 600);
	}

	public void setCssContent(String cssContent) {
		if (cssContent == null) {
			cssContent = "";
		}
		this.cssContent = cssContent;
		if (text != null && !text.isDisposed()) {
			text.setText(cssContent);
		}
	}

}
