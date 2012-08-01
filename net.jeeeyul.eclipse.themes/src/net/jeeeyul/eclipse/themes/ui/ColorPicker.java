package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ColorPicker extends Dialog {
	public static void main(String[] args) {
		new ColorPicker(null).open();
	}

	private HueCanvas hueCanvas;
	private HueScale hueScale;
	private RGB selection = new RGB(0, 0, 0);

	private boolean lockHue;
	private ColorWell colorWell;

	public ColorPicker() {
		super(Display.getDefault().getActiveShell());
	}

	public ColorPicker(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout layout = (GridLayout) container.getLayout();
		layout.numColumns = 2;
		layout.marginWidth = layout.marginHeight = 5;

		hueCanvas = new HueCanvas(container, SWT.NORMAL);
		hueCanvas.setPadding(6);
		hueCanvas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		colorWell = new ColorWell(container, SWT.BORDER);

		hueScale = new HueScale(container, SWT.NORMAL);
		hueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				hueCanvas.setHue(hueScale.getSelection());
			}
		});

		hueCanvas.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				float[] hsb = hueCanvas.getSelection();
				selection = new RGB(hsb[0], hsb[1], hsb[2]);
				colorWell.setSelection(new RGB(hsb[0], hsb[1], hsb[2]));
			}
		});

		update();

		getShell().setText("Jeeeyul's Color Picker");
		return container;
	}

	public RGB getSelection() {
		return selection;
	}

	public boolean isLockHue() {
		return lockHue;
	}

	public void setLockHue(boolean lockHue) {
		this.lockHue = lockHue;
	}

	public void setSelection(RGB selection) {
		this.selection = selection;
		update();
	}

	private void update() {
		if (getShell() == null) {
			return;
		}

		float[] hsb = selection.getHSB();
		hueCanvas.setSelection(hsb);
		hueScale.setSelection(hsb[0]);
		hueScale.setEnabled(!lockHue);
	}

}
