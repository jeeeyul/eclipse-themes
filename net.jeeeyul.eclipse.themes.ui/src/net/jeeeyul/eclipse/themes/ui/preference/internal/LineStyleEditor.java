package net.jeeeyul.eclipse.themes.ui.preference.internal;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

@SuppressWarnings("javadoc")
public class LineStyleEditor {

	private Button[] buttons;

	private Procedure1<LineStyleEditor> selectionHandler;
	private Composite composite;

	public LineStyleEditor(Composite parent) {
		composite = new Composite(parent, SWT.NORMAL);
		GridLayout layout = new GridLayout(4, false);
		composite.setLayout(layout);

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				fireSelection();
			}
		};

		buttons = new Button[4];

		buttons[0] = new Button(composite, SWT.RADIO);
		buttons[0].setText("None");
		buttons[0].setData(SWT.NONE);

		buttons[1] = new Button(composite, SWT.RADIO);
		buttons[1].setText("Solid");
		buttons[1].setData(SWT.LINE_SOLID);

		buttons[2] = new Button(composite, SWT.RADIO);
		buttons[2].setText("Dashed");
		buttons[2].setData(SWT.LINE_DASH);

		buttons[3] = new Button(composite, SWT.RADIO);
		buttons[3].setText("Dotted");
		buttons[3].setData(SWT.LINE_DOT);

		SWTExtensions.INSTANCE.attachTo(listener, SWT.Selection, buttons);
	}

	private void fireSelection() {
		if (selectionHandler != null) {
			selectionHandler.apply(this);
		}
	}

	/**
	 * 
	 * @return line style value.
	 * 
	 * @see SWT#LINE_SOLID
	 * @see SWT#LINE_DASH
	 * @see SWT#LINE_DOT
	 * @see SWT#NONE
	 */
	public int getSelection() {
		for (Button each : buttons) {
			if (each.getSelection()) {
				return (Integer) each.getData();
			}
		}
		return SWT.NONE;
	}

	public Procedure1<LineStyleEditor> getSelectionHandler() {
		return selectionHandler;
	}

	public Control getControl() {
		return composite;
	}

	public void setSelection(int lineStyle) {
		for (Button each : buttons) {
			int data = (Integer) each.getData();
			each.setSelection(data == lineStyle);
		}
	}

	public void setSelectionHandler(Procedure1<LineStyleEditor> selectionHandler) {
		this.selectionHandler = selectionHandler;
	}

}
