package net.jeeeyul.eclipse.themes.ui.preference.internal;

import net.jeeeyul.swtend.sam.Procedure1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

@SuppressWarnings("javadoc")
public class LineWidthEditor {
	private Button thinButton;
	private Button normalButton;
	private Button boldButton;

	private Procedure1<Integer> selectionHandler;
	private Composite control;

	public Composite getControl() {
		return control;
	}

	public LineWidthEditor(Composite parent) {
		control = new Composite(parent, SWT.NORMAL);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = layout.marginHeight = 0;
		control.setLayout(layout);

		thinButton = new Button(control, SWT.RADIO);
		thinButton.setText("Thin");
		normalButton = new Button(control, SWT.RADIO);
		normalButton.setSelection(true);
		normalButton.setText("Normal");
		boldButton = new Button(control, SWT.RADIO);
		boldButton.setText("Bold");

		Listener hook = new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleSelection();
			}
		};

		thinButton.addListener(SWT.Selection, hook);
		normalButton.addListener(SWT.Selection, hook);
		boldButton.addListener(SWT.Selection, hook);
	}

	public int getSelection() {
		if (boldButton.getSelection()) {
			return 3;
		} else if (thinButton.getSelection()) {
			return 1;
		} else {
			return 2;
		}
	}

	public Procedure1<Integer> getSelectionHandler() {
		return selectionHandler;
	}

	private void handleSelection() {
		if (selectionHandler != null) {
			selectionHandler.apply(getSelection());
		}
	}

	public void setSelection(int selection) {
		if (getSelection() == selection) {
			return;
		}
		
		thinButton.setSelection(false);
		normalButton.setSelection(false);
		boldButton.setSelection(false);
		
		switch (selection) {
		case 3:
			boldButton.setSelection(true);
			break;
		case 1:
			thinButton.setSelection(true);
			break;
		default:
			normalButton.setSelection(true);
		}
	}

	public void setSelectionHandler(Procedure1<Integer> selectionHandler) {
		this.selectionHandler = selectionHandler;
	}
}
