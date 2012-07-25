package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.decorator.GradientDecorator;
import net.jeeeyul.eclipse.themes.ui.HueScale;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.progress.UIJob;

/**
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
public class ChromePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "net.jeeeyul.eclipse.themes.ChromePreferencePage";
	private CTabFolder folder;
	private GradientDecorator decorator;
	private HueScale startHueScale;
	private Scale startSaturationScale;
	private Scale startBrightnessScale;
	private HueScale endHueScale;
	private Scale endSaturationScale;
	private Scale endBrightnessScale;
	private Button lockHueField;
	private Button autoEndColorField;
	private Scale sashWidthScale;
	private Button useShadowField;
	private UIJob updateJob;
	private Button thinSashButton;
	private Button standardSashButton;
	private Button manualSashButton;

	public ChromePreferencePage() {
		IPreferenceStore store = getStore();
		setPreferenceStore(store);

		float hsbStart[] = new float[3];
		hsbStart[0] = store.getFloat("chrome-active-start-hue");
		hsbStart[1] = store.getFloat("chrome-active-start-saturation");
		hsbStart[2] = store.getFloat("chrome-active-start-brightness");

		float hsbEnd[] = new float[3];
		hsbEnd[0] = store.getFloat("chrome-active-end-hue");
		hsbEnd[1] = store.getFloat("chrome-active-end-saturation");
		hsbEnd[2] = store.getFloat("chrome-active-end-brightness");
		decorator = new GradientDecorator(hsbStart, hsbEnd);

		setDescription("Chrome Theme Configuration");
	}

	protected void computeEndColor() {
		endHueScale.setSelection(startHueScale.getSelection());
		endSaturationScale.setSelection(Math.min((int) (startSaturationScale.getSelection() * 1.15), 100));
		endBrightnessScale.setSelection((int) (startBrightnessScale.getSelection() * 0.95));
	}

	private CTabItem createColorTab() {
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setImage(SharedImages.getImage(SharedImages.PALETTE));
		Composite body = new Composite(folder, SWT.NORMAL);
		body.setBackgroundMode(SWT.INHERIT_FORCE);
		body.setBackground(folder.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		body.setLayout(new GridLayout());

		fillContents(body);
		item.setControl(body);
		item.setText("Active Gradient");
		return item;
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NORMAL);
		composite.setLayout(new GridLayout());

		folder = new CTabFolder(composite, SWT.NORMAL);
		folder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				event.doit = false;
			}
		});
		createFakeToolbar();

		CSSClasses tags = CSSClasses.getStyleClasses(folder);
		tags.add("chrome-tabfolder-preview");
		CSSClasses.setStyleClasses(folder, tags);

		CTabItem colorTab = createColorTab();
		createSashTab();

		folder.setSelection(colorTab);

		decorator.apply(folder);

		createLink(composite, "Only works with Chrome Theme, You can change on <a href=\"org.eclipse.ui.preferencePages.Views\">Appearance page</a>");

		return composite;
	}

	private void createFakeToolbar() {
		ToolBar toolBar = new ToolBar(folder, SWT.NORMAL);
		folder.setTopRight(toolBar);
		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(SharedImages.getImage(SharedImages.ECLIPSE));

		ToolItem toolItem2 = new ToolItem(toolBar, SWT.PUSH);
		toolItem2.setImage(SharedImages.getImage(SharedImages.MENU));

		ToolItem toolItem3 = new ToolItem(toolBar, SWT.PUSH);
		toolItem3.setImage(SharedImages.getImage(SharedImages.MINIMIZE));

		ToolItem toolItem4 = new ToolItem(toolBar, SWT.PUSH);
		toolItem4.setImage(SharedImages.getImage(SharedImages.MAXMIZE));
	}

	private CTabItem createSashTab() {
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Layout");
		item.setImage(SharedImages.getImage(SharedImages.LAYOUT));
		Composite body = new Composite(folder, SWT.NORMAL);
		body.setBackgroundMode(SWT.INHERIT_FORCE);
		body.setBackground(folder.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		body.setLayout(new GridLayout());
		item.setControl(body);

		Group group = new Group(body, SWT.NORMAL);
		group.setText("Sash Width");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		thinSashButton = new Button(group, SWT.RADIO);
		thinSashButton.setText("Thin Sash (Classic)");

		standardSashButton = new Button(group, SWT.RADIO);
		standardSashButton.setText("Standard");

		manualSashButton = new Button(group, SWT.RADIO);
		manualSashButton.setText("Manual (Advanced)");
		String preset = getStore().getString(ChromeConstants.CHROME_SASH_PRESET);
		if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(preset)) {
			thinSashButton.setSelection(true);
		} else if (ChromeConstants.CHROME_SASH_PRESET_STANDARD.equals(preset)) {
			standardSashButton.setSelection(true);
		} else {
			manualSashButton.setSelection(true);
		}
		manualSashButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateAdvanced();
			}
		});

		Group advancedGroup = new Group(body, SWT.NORMAL);
		advancedGroup.setText("Advanced");
		advancedGroup.setLayout(new GridLayout(2, false));
		advancedGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		useShadowField = new Button(advancedGroup, SWT.CHECK);
		useShadowField.setText("Cast shadows for parts");
		useShadowField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		useShadowField.setSelection(ChromeThemeConfig.getInstance().usePartShadow());

		new Label(advancedGroup, SWT.NORMAL).setText("Sash Width:");
		sashWidthScale = new Scale(advancedGroup, SWT.NORMAL);
		sashWidthScale.setMinimum(1);
		sashWidthScale.setMaximum(10);
		sashWidthScale.setSelection(ChromeThemeConfig.getInstance().getSashWidth());
		sashWidthScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		updateAdvanced();
		return item;
	}

	@Override
	public void dispose() {
		decorator.dispose();
		super.dispose();
	}

	private void fillContents(Composite body) {
		Group startGroup = new Group(body, SWT.NORMAL);
		startGroup.setText("Start Color");
		startGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		startGroup.setLayout(new GridLayout(2, false));

		new org.eclipse.swt.widgets.Label(startGroup, SWT.NORMAL).setText("Hue:");
		startHueScale = new HueScale(startGroup, SWT.NORMAL);
		startHueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		endHueScale = new HueScale(endGroup, SWT.NORMAL);
		endHueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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

	private IPreferenceStore getStore() {
		return ChromeThemeCore.getDefault().getPreferenceStore();
	}

	private UIJob getUpdateJob() {
		if (updateJob == null) {
			updateJob = new UIJob("Update") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (getControl() == null || getControl().isDisposed()) {
						return Status.OK_STATUS;
					}

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
					return Status.OK_STATUS;
				}
			};
			updateJob.setSystem(true);
		}
		return updateJob;
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	private boolean isAutomaticEndColor() {
		return autoEndColorField.getSelection();
	}

	private boolean isHueSynchronized() {
		return lockHueField.getSelection();
	}

	@Override
	protected void performDefaults() {
		IPreferenceStore store = getStore();
		float[] start = new float[3];

		start[0] = store.getDefaultFloat("chrome-active-start-hue");
		start[1] = store.getDefaultFloat("chrome-active-start-saturation");
		start[2] = store.getDefaultFloat("chrome-active-start-brightness");

		float[] end = new float[3];
		end[0] = store.getDefaultFloat("chrome-active-end-hue");
		end[1] = store.getDefaultFloat("chrome-active-end-saturation");
		end[2] = store.getDefaultFloat("chrome-active-end-brightness");

		decorator.setStartHSB(start);
		decorator.setEndHSB(end);

		startHueScale.setSelection((int) start[0]);
		startSaturationScale.setSelection((int) (start[1] * 100));
		startBrightnessScale.setSelection((int) (start[2] * 100));

		endHueScale.setSelection((int) end[0]);
		endSaturationScale.setSelection((int) (end[1] * 100));
		endBrightnessScale.setSelection((int) (end[2] * 100));

		autoEndColorField.setSelection(store.getDefaultBoolean("chrome-auto-end-color"));
		lockHueField.setSelection(store.getDefaultBoolean("chrome-lock-hue"));

		sashWidthScale.setSelection(store.getDefaultInt(ChromeConstants.CHOME_PART_CONTAINER_SASH_WIDTH));
		useShadowField.setSelection(store.getDefaultBoolean(ChromeConstants.CHOME_PART_SHADOW));

		thinSashButton.setSelection(false);
		standardSashButton.setSelection(false);
		manualSashButton.setSelection(false);

		String defaultPreset = store.getDefaultString(ChromeConstants.CHROME_SASH_PRESET);
		if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(defaultPreset)) {
			thinSashButton.setSelection(true);
		} else if (ChromeConstants.CHROME_SASH_PRESET_STANDARD.equals(defaultPreset)) {
			standardSashButton.setSelection(true);
		} else {
			manualSashButton.setSelection(true);
		}

		updateLock();
		updateAuto();
		update();
		updateAdvanced();
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

		store.setValue(ChromeConstants.CHOME_PART_CONTAINER_SASH_WIDTH, sashWidthScale.getSelection());
		store.setValue(ChromeConstants.CHOME_PART_SHADOW, useShadowField.getSelection());

		if (thinSashButton.getSelection()) {
			store.setValue(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_THIN);
		} else if (standardSashButton.getSelection()) {
			store.setValue(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_STANDARD);
		} else {
			store.setValue(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_ADVANCED);
		}

		return super.performOk();
	}

	private void update() {
		getUpdateJob().schedule();
	}

	private void updateAdvanced() {
		sashWidthScale.setEnabled(manualSashButton.getSelection());
		useShadowField.setEnabled(manualSashButton.getSelection());
	}

	private void createLink(Composite contaienr, String text) {
		Link link = new Link(contaienr, SWT.NORMAL);
		link.setText(text);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				navigateToOtherPage(e.text);
			}
		});
	}

	protected void navigateToOtherPage(String pageId) {
		PreferencesUtil.createPreferenceDialogOn(getShell(), pageId, null, null);
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

	protected void updateLock() {
		if (isHueSynchronized()) {
			endHueScale.setEnabled(false);
			endHueScale.setSelection(startHueScale.getSelection());
		} else {
			endHueScale.setEnabled(true);
		}

		update();
	}
}
