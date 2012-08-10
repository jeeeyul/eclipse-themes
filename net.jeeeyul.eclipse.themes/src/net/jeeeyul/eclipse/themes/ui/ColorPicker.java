package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

public class ColorPicker extends Dialog {
	public static void main(String[] args) {
		ColorPicker picker = new ColorPicker(null);
		picker.setSelection(new HSB(255, 0, 0));
		picker.open();
	}

	private HueCanvas hueCanvas;
	private HueScale hueScale;
	private HSB selection = new HSB(0f, 0f, 0f);

	private Image pipetteImage;
	private boolean lockHue;
	private ColorWell colorWell;
	private Procedure1<HSB> continuosSelectionHandler;
	private ColorFieldSet fieldSet;
	private ToolItem pipette;

	public ColorPicker() {
		this(Display.getDefault().getActiveShell());
	}

	public ColorPicker(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.TITLE | SWT.CLOSE | SWT.APPLICATION_MODAL);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout mainLayout = (GridLayout) container.getLayout();
		mainLayout.numColumns = 2;
		mainLayout.marginWidth = mainLayout.marginHeight = 0;
		mainLayout.horizontalSpacing = 0;

		Composite left = new Composite(container, SWT.NORMAL);
		left.setLayout(new GridLayout());

		hueCanvas = new HueCanvas(left, SWT.NORMAL);
		hueCanvas.setPadding(6);
		hueCanvas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));

		hueCanvas.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleNewSelection();
			}
		});

		fieldSet = new ColorFieldSet(container);
		fieldSet.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		fieldSet.setSelectionHandler(new Procedure1<HSB>() {
			@Override
			public void apply(HSB hsb) {
				setSelection(hsb);
			}
		});

		new Label(container, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		Composite bottom = new Composite(container, SWT.NORMAL);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		GridLayout bottomLayout = new GridLayout(3, false);
		bottomLayout.marginHeight = 0;
		bottomLayout.marginWidth = 15;
		bottom.setLayout(bottomLayout);

		colorWell = new ColorWell(bottom, SWT.BORDER);

		ToolBar toolBar = new ToolBar(bottom, SWT.FLAT);

		pipette = new ToolItem(toolBar, SWT.PUSH);
		pipetteImage = new Image(getShell().getDisplay(), new ImageData(getClass().getResourceAsStream("pipette.png")));
		pipette.setImage(pipetteImage);
		pipette.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!isLockHue()) {
					openPipetteMode();
				}
			}
		});

		hueScale = new HueScale(bottom, SWT.NORMAL);
		hueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				hueCanvas.setHue(hueScale.getSelection());
			}
		});
		
		new Label(container, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		getShell().setText("Color Picker");
		update();

		hueCanvas.setFocus();

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
		update();
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
		pipette.setEnabled(!lockHue);
		fieldSet.setSelection(selection);
	}

	private void handleNewSelection() {
		selection = hueCanvas.getSelection();
		colorWell.setSelection(selection);
		fieldSet.setSelection(selection);
		if (continuosSelectionHandler != null) {
			continuosSelectionHandler.apply(selection);
		}
	}

	private void openPipetteMode() {
		HSB result = new PipetteTool(getShell()).open();
		if (result != null) {
			hueScale.setSelection(result.hue);
		}
		hueCanvas.setSelection(result.toArray());
	}

}
