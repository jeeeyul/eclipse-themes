package net.jeeeyul.eclipse.themes.preference;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.ColorPicker;
import net.jeeeyul.swtend.ui.ColorStop;
import net.jeeeyul.swtend.ui.ColorWell;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.GradientEdit;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;

public class PreperencePageHelper {

	private JTPartStackPreferencePage root;

	public PreperencePageHelper(JTPartStackPreferencePage root) {
		this.root = root;
	}

	public List<HSB> asSWTSafeHSBArray(Gradient gradient) {
		ArrayList<HSB> result = new ArrayList<HSB>();

		ColorStop first = gradient.get(0);
		if (first.percent != 0) {
			result.add(first.color);
		}

		for (ColorStop each : gradient) {
			result.add(each.color);
		}

		ColorStop last = gradient.get(gradient.size() - 1);
		if (last.percent != 100) {
			result.add(last.color);
		}

		return result;
	}

	public void requestUpdatePreview(boolean all) {
		root.updatePreview(root.getActivePage());
	}

	public GradientEdit newGradientEdit(Composite parent, Procedure1<GradientEdit> initializer) {
		final GradientEdit result = new GradientEdit(parent);

		if (initializer != null) {
			initializer.apply(result);
		}

		return result;
	}

	public ColorWell newColorWell(Composite parent, Procedure1<ColorWell> initializer) {
		final ColorWell result = new ColorWell(parent, SWT.NORMAL);

		result.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				HSB original = result.getSelection();
				ColorPicker picker = new ColorPicker(result.getShell());
				picker.setSelection(original);
				picker.setContinuosSelectionHandler(new Procedure1<HSB>() {
					@Override
					public void apply(HSB t) {
						result.setSelection(t);
					}
				});

				if (picker.open() == IDialogConstants.OK_ID) {
					result.setSelection(picker.getSelection());
				} else {
					result.setSelection(original);
				}
			}
		});

		if (initializer != null) {
			initializer.apply(result);
		}

		return result;
	}

	public Button appendOrderLockButton(final GradientEdit gradEdit, Procedure1<Button> initializer) {
		final Button button = new Button(gradEdit.getParent(), SWT.CHECK);
		button.setText("Lock Order");
		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				gradEdit.setLockOrder(button.getSelection());
			}
		});
		if (initializer != null) {
			initializer.apply(button);
		}

		return button;
	}

	public Label appendMonitor(final Scale scale, final String unit, final int delta) {
		final Label label = new Label(scale.getParent(), SWT.NORMAL);
		scale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				label.setText((scale.getSelection() + delta) + unit);
			}
		});

		label.setLayoutData(new GridData(40, -1));

		return label;
	}

	public List<Integer> asSWTSafePercentArray(Gradient gradient) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		ColorStop first = gradient.get(0);
		if (first.percent != 0) {
			result.add(first.percent);
		}

		for (int i = 1; i < gradient.size(); i++) {
			ColorStop each = gradient.get(i);
			result.add(each.percent);
		}

		ColorStop last = gradient.get(gradient.size() - 1);
		if (last.percent != 100) {
			result.add(100);
		}

		return result;
	}
}
