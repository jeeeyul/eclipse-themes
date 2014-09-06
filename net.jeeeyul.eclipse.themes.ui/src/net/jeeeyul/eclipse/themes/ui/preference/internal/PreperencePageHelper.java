package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.ColorPicker;
import net.jeeeyul.swtend.ui.ColorStop;
import net.jeeeyul.swtend.ui.ColorWell;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.GradientEdit;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.dialogs.PreferencesUtil;

@SuppressWarnings("javadoc")
public class PreperencePageHelper {

	private JTPreferencePage root;
	private AbstractJTPreferencePage client;

	public PreperencePageHelper(JTPreferencePage root, AbstractJTPreferencePage client) {
		this.root = root;
		this.client = client;
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
		label.setText((scale.getSelection() + delta) + unit);

		SWTExtensions.INSTANCE.asyncExec(new Procedure1<Void>() {
			@Override
			public void apply(Void t) {
				if (scale.isDisposed()) {
					return;
				}

				label.setText((scale.getSelection() + delta) + unit);
			}

		});

		return label;
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

	public AbstractJTPreferencePage getActivePage() {
		return root.getActivePage();
	}

	public JThemePreferenceStore getPreferenceStore() {
		return root.getPreferenceStore();
	}

	public JTPreferencePage getRootPage() {
		return root;
	}

	/**
	 * Compares {@link Color} array and {@link HSB} array.
	 * 
	 * @param colors
	 *            colors to compare.
	 * @param hsbArray
	 *            hsb arrays to compare.
	 * @return what if they abstracts same color it returns true, otherwhise
	 *         false.
	 * 
	 * @since 2.0.0
	 */
	public boolean matches(Color[] colors, HSB[] hsbArray) {
		if (colors == null) {
			return false;
		}

		if (colors.length != hsbArray.length) {
			return false;
		}

		for (int i = 0; i < colors.length; i++) {
			if (colors[i].isDisposed()) {
				return false;
			}

			if (!colors[i].getRGB().equals(hsbArray[i].toRGB())) {
				return false;
			}
		}

		return true;
	}

	public boolean matches(Color color, HSB hsb) {
		if (color == null || color.isDisposed()) {
			return false;
		}

		return new HSB(color.getRGB()).equals(hsb);
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

	public GradientEdit newGradientEdit(Composite parent, Procedure1<GradientEdit> initializer) {
		final GradientEdit result = new GradientEdit(parent);

		if (initializer != null) {
			initializer.apply(result);
		}

		return result;
	}

	/**
	 * request fast preview, it will call clients
	 * {@link AbstractJTPreferencePage#updatePreview(CTabFolder, JTabSettings, SWTExtensions, PreperencePageHelper)}
	 * Immediately. What it there is changing in layout, call
	 * {@link #requestUpdatePreview()} instead.
	 * 
	 * @since 2.0.0
	 */
	public void requestFastUpdatePreview() {
		CTabFolder folder = getRootPage().getFolder();
		JeeeyulsTabRenderer renderer = (JeeeyulsTabRenderer) folder.getRenderer();
		JTabSettings settings = renderer.getSettings();
		client.updatePreview(folder, settings, SWTExtensions.INSTANCE, this);
	}

	/**
	 * Request full update for preview. It contains relayout.
	 */
	public void requestUpdatePreview() {
		root.updatePreview();
	}

	public AbstractJTPreferencePage[] getAllPages() {
		return root.getAllPages();
	}

	public void navigateTo(String id) {
		PreferencesUtil.createPreferenceDialogOn(root.getShell(), id, null, null);
	}

	public JThemePreferenceStore createWorkingCopy() {
		JThemePreferenceStore fakeStore = new JThemePreferenceStore(new PreferenceStore());
		root.saveTo(fakeStore);
		return fakeStore;
	}

	public void loadFromWorkingCopy(JThemePreferenceStore store) {
		root.loadFrom(store);
	}
}
