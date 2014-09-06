package net.jeeeyul.eclipse.themes.ui.preference.internal;

import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.ColorPicker;
import net.jeeeyul.swtend.ui.ColorWell;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

@SuppressWarnings("javadoc")
public class TextShadowEdit {
	private ColorWell colorWell;
	private Button noneButton;
	private Button belowButton;
	private Button upperButton;
	private Composite control;

	private Procedure1<TextShadowEdit> modifyHandler;

	public TextShadowEdit(Composite composite) {
		control = new Composite(composite, SWT.NORMAL);
		GridLayout layout = new GridLayout(4, false);
		layout.marginWidth = layout.marginHeight = 0;
		control.setLayout(layout);

		colorWell = new ColorWell(control, SWT.NORMAL);

		noneButton = new Button(control, SWT.RADIO);
		noneButton.setText("None");

		belowButton = new Button(control, SWT.RADIO);
		belowButton.setText("Below");

		upperButton = new Button(control, SWT.RADIO);
		upperButton.setText("Upper");

		colorWell.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ColorPicker picker = new ColorPicker(control.getShell());
				HSB original = colorWell.getSelection();
				picker.setSelection(original);
				picker.setContinuosSelectionHandler(new Procedure1<HSB>() {
					@Override
					public void apply(HSB t) {
						colorWell.setSelection(t, true);
					}
				});

				if (picker.open() == IDialogConstants.OK_ID) {
					colorWell.setSelection(picker.getSelection(), true);
				} else {
					colorWell.setSelection(original, true);
				}
			}
		});

		Listener dispatchModify = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (modifyHandler != null) {
					modifyHandler.apply(TextShadowEdit.this);
				}
			}
		};

		noneButton.addListener(SWT.Selection, dispatchModify);
		belowButton.addListener(SWT.Selection, dispatchModify);
		upperButton.addListener(SWT.Selection, dispatchModify);
		colorWell.addListener(SWT.Modify, dispatchModify);
	}

	public HSB getColor() {
		return colorWell.getSelection();
	}

	public Composite getControl() {
		return control;
	}

	public Procedure1<TextShadowEdit> getModifyHandler() {
		return modifyHandler;
	}

	public Point getShadowPosition() {
		if (noneButton.getSelection()) {
			return new Point(0, 0);
		} else if (belowButton.getSelection()) {
			return new Point(0, 1);
		} else {
			return new Point(0, -1);
		}
	}

	public void setColor(HSB color) {
		colorWell.setSelection(color, false);
	}

	public void setModifyHandler(Procedure1<TextShadowEdit> modifyHandler) {
		this.modifyHandler = modifyHandler;
	}

	public void setShadowPosition(Point point) {
		if (point.y < 0) {
			upperButton.setSelection(true);
			belowButton.setSelection(false);
			noneButton.setSelection(false);
		} else if (point.y > 0) {
			upperButton.setSelection(false);
			belowButton.setSelection(true);
			noneButton.setSelection(false);
		} else {
			upperButton.setSelection(false);
			belowButton.setSelection(false);
			noneButton.setSelection(true);
		}
	}

}
