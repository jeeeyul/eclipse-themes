package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

public class ColorPicker extends Dialog {
	public static void main(String[] args) {
		new ColorPicker(null).open();
	}

	private HueCanvas hueCanvas;
	private HueScale hueScale;
	private HSB selection = new HSB(0f, 0f, 0f);

	private boolean lockHue;
	private ColorWell colorWell;
	private Procedure1<HSB> continuosSelectionHandler;

	public ColorPicker() {
		this(Display.getDefault().getActiveShell());
	}

	public ColorPicker(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.TITLE | SWT.CLOSE); 
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
				handleNewSelection();
			}
		});

		update();

		getShell().setText("Jeeeyul's Color Picker");
		return container;
	}

	public Procedure1<HSB> getContinuosSelectionHandler() {
		return continuosSelectionHandler;
	}

	public HSB getSelection() {
		return selection;
	}

	public boolean isLockHue() {
		return lockHue;
	}

	public void setContinuosSelectionHandler(Procedure1<HSB> continuosSelectionHandler) {
		this.continuosSelectionHandler = continuosSelectionHandler;
	}

	public void setLockHue(boolean lockHue) {
		this.lockHue = lockHue;
	}

	public void setSelection(HSB selection) {
		this.selection = selection;
		update();
	}

	private void update() {
		if (getShell() == null) {
			return;
		}

		hueCanvas.setSelection(selection.toArray());
		hueScale.setSelection(selection.hue);
		hueScale.setEnabled(!lockHue);
	}

	private void handleNewSelection() {
		selection = hueCanvas.getSelection();
		colorWell.setSelection(selection);
		if (continuosSelectionHandler != null) {
			continuosSelectionHandler.apply(selection);
		}
	}

}
