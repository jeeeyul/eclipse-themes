package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.Activator;
import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.decorator.GradientDecorator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ChromePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "net.jeeeyul.eclipse.themes.ChromePreferencePage";
	private CTabFolder folder;
	private GradientDecorator decorator;
	private Scale startHueScale;
	private Scale startSaturationScale;
	private Scale startBrightnessScale;
	private Scale endHueScale;
	private Scale endSaturationScale;
	private Scale endBrightnessScale;
	private Button lockHueField;
	private Button autoEndColorField;

	public ChromePreferencePage() {
		IPreferenceStore store = getStore();
		float hsbStart[] = new float[3];
		hsbStart[0] = store.getFloat("chrome-active-start-hue");
		hsbStart[1] = store.getFloat("chrome-active-start-saturation");
		hsbStart[2] = store.getFloat("chrome-active-start-brightness");

		float hsbEnd[] = new float[3];
		hsbEnd[0] = store.getFloat("chrome-active-end-hue");
		hsbEnd[1] = store.getFloat("chrome-active-end-saturation");
		hsbEnd[2] = store.getFloat("chrome-active-end-brightness");
		decorator = new GradientDecorator(hsbStart, hsbEnd);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NORMAL);
		composite.setLayout(new GridLayout());

		folder = new CTabFolder(composite, SWT.NORMAL);
		folder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		CSSClasses tags = CSSClasses.getStyleClasses(folder);
		tags.add("chrome-tabfolder-preview");
		CSSClasses.setStyleClasses(folder, tags);

		CTabItem item = new CTabItem(folder, SWT.Close);
		Composite body = new Composite(folder, SWT.NORMAL);
		body.setBackgroundMode(SWT.INHERIT_FORCE);
		body.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		body.setLayout(new GridLayout());

		fillContents(body);
		item.setControl(body);
		item.setText("Active Gradient");

		folder.setSelection(item);
		folder.reskin(SWT.ALL);

		decorator.apply(folder);

		return composite;
	}

	private void fillContents(Composite body) {
		Group startGroup = new Group(body, SWT.NORMAL);
		startGroup.setText("Start Color");
		startGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		startGroup.setLayout(new GridLayout(2, false));

		new org.eclipse.swt.widgets.Label(startGroup, SWT.NORMAL).setText("Hue:");
		startHueScale = new Scale(startGroup, SWT.NORMAL);
		startHueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		startHueScale.setMinimum(0);
		startHueScale.setMaximum(360);
		startHueScale.setSelection((int) decorator.getStartHSB()[0]);
		startHueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (isAutomaticEndColor()) {
					computeEndColor();
				} else if (isHueSynchronized()) {
					endHueScale.setSelection(startHueScale.getSelection());
				}

				update();
			}
		});

		new org.eclipse.swt.widgets.Label(startGroup, SWT.NORMAL).setText("Saturation:");
		startSaturationScale = new Scale(startGroup, SWT.NORMAL);
		startSaturationScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		startSaturationScale.setMinimum(0);
		startSaturationScale.setMaximum(100);
		startSaturationScale.setSelection((int) (decorator.getStartHSB()[1] * 100));
		startSaturationScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (isAutomaticEndColor()) {
					computeEndColor();
				}
				update();
			}
		});

		new org.eclipse.swt.widgets.Label(startGroup, SWT.NORMAL).setText("Brightness:");
		startBrightnessScale = new Scale(startGroup, SWT.NORMAL);
		startBrightnessScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		startBrightnessScale.setMinimum(0);
		startBrightnessScale.setMaximum(100);
		startBrightnessScale.setSelection((int) (decorator.getStartHSB()[2] * 100));
		startBrightnessScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (isAutomaticEndColor()) {
					computeEndColor();
				}
				update();
			}
		});

		Group endGroup = new Group(body, SWT.NORMAL);
		endGroup.setText("End Color");
		endGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		endGroup.setLayout(new GridLayout(2, false));

		autoEndColorField = new Button(endGroup, SWT.CHECK);
		autoEndColorField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		autoEndColorField.setText("Choose Automatically");
		autoEndColorField.setSelection(getStore().getBoolean("chrome-auto-end-color"));
		autoEndColorField.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateAuto();
			}
		});

		lockHueField = new Button(endGroup, SWT.CHECK);
		lockHueField.setText("Lock Hue");
		lockHueField.setSelection(getStore().getBoolean("chrome-lock-hue"));
		lockHueField.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateLock();
			}
		});
		lockHueField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		new org.eclipse.swt.widgets.Label(endGroup, SWT.NORMAL).setText("Hue:");
		endHueScale = new Scale(endGroup, SWT.NORMAL);
		endHueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		endHueScale.setMinimum(0);
		endHueScale.setMaximum(360);
		endHueScale.setSelection((int) decorator.getEndHSB()[0]);
		endHueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		new org.eclipse.swt.widgets.Label(endGroup, SWT.NORMAL).setText("Saturation:");
		endSaturationScale = new Scale(endGroup, SWT.NORMAL);
		endSaturationScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		endSaturationScale.setMinimum(0);
		endSaturationScale.setMaximum(100);
		endSaturationScale.setSelection((int) (decorator.getEndHSB()[1] * 100));
		endSaturationScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		new org.eclipse.swt.widgets.Label(endGroup, SWT.NORMAL).setText("Brightness:");
		endBrightnessScale = new Scale(endGroup, SWT.NORMAL);
		endBrightnessScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		endBrightnessScale.setMinimum(0);
		endBrightnessScale.setMaximum(100);
		endBrightnessScale.setSelection((int) (decorator.getEndHSB()[2] * 100));
		endBrightnessScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		updateLock();
		updateAuto();
	}

	protected void computeEndColor() {
		endHueScale.setSelection(startHueScale.getSelection());
		endSaturationScale.setSelection(Math.min((int) (startSaturationScale.getSelection() * 1.15), 100));
		endBrightnessScale.setSelection((int) (startBrightnessScale.getSelection() * 0.95));
	}

	protected void updateAuto() {
		boolean isAutomaticEndColor = isAutomaticEndColor();
		lockHueField.setEnabled(!isAutomaticEndColor);
		endBrightnessScale.setEnabled(!isAutomaticEndColor);
		endHueScale.setEnabled(!isAutomaticEndColor);
		endSaturationScale.setEnabled(!isAutomaticEndColor);
		if (isAutomaticEndColor) {
			computeEndColor();
			update();
		}
	}

	private boolean isAutomaticEndColor() {
		return autoEndColorField.getSelection();
	}

	protected void updateLock() {
		if (isHueSynchronized()) {
			endHueScale.setEnabled(false);
			endHueScale.setSelection(startHueScale.getSelection());
		} else {
			endHueScale.setEnabled(true);
		}

		update();
	}

	private boolean isHueSynchronized() {
		return lockHueField.getSelection();
	}

	@Override
	public void dispose() {
		decorator.dispose();
		super.dispose();
	}

	private void update() {
		float[] start = decorator.getStartHSB();
		start[0] = startHueScale.getSelection();
		start[1] = startSaturationScale.getSelection() / 100f;
		start[2] = startBrightnessScale.getSelection() / 100f;
		decorator.setStartHSB(start);

		float[] end = decorator.getEndHSB();
		end[0] = endHueScale.getSelection();
		end[1] = endSaturationScale.getSelection() / 100f;
		end[2] = endBrightnessScale.getSelection() / 100f;
		decorator.setEndHSB(end);

		decorator.apply(folder);
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getStore();
		float[] start = decorator.getStartHSB();
		store.setValue("chrome-active-start-hue", start[0]);
		store.setValue("chrome-active-start-saturation", start[1]);
		store.setValue("chrome-active-start-brightness", start[2]);

		float[] end = decorator.getEndHSB();
		store.setValue("chrome-active-end-hue", end[0]);
		store.setValue("chrome-active-end-saturation", end[1]);
		store.setValue("chrome-active-end-brightness", end[2]);

		store.setValue("chrome-auto-end-color", autoEndColorField.getSelection());
		store.setValue("chrome-lock-hue", lockHueField.getSelection());
		return super.performOk();
	}

	private IPreferenceStore getStore() {
		return Activator.getDefault().getPreferenceStore();
	}
}
