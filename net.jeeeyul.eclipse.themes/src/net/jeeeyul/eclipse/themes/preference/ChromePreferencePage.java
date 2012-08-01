package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.css.RewriteChormeCSS;
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;
import net.jeeeyul.eclipse.themes.rendering.GradientDecorator;
import net.jeeeyul.eclipse.themes.ui.HueScale;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FontDialog;
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
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.preferences.WorkingCopyManager;
import org.eclipse.ui.progress.UIJob;
import org.osgi.service.prefs.BackingStoreException;

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
	private Button lockEndHueField;
	private Button autoEndColorField;
	private Scale sashWidthScale;
	private Button useShadowField;
	private UIJob updateJob;
	private Button thinSashButton;
	private Button standardSashButton;
	private Button manualSashButton;

	private IWorkingCopyManager workingCopyManager;
	private Label fontPreviewLabel;

	private Color outlineColor;

	private Font previewFont;

	private Button usePartTextShadowButton;

	private HueScale outlineHueScale;

	private Scale outlineSaturationScale;

	private Scale outlineBrightnessScale;

	private Button autoOutlineColorField;

	private Button lockOutlineHueField;

	public ChromePreferencePage() {
		String bundleId = ChromeThemeCore.getDefault().getBundle().getSymbolicName();
		workingCopyManager = new WorkingCopyManager();

		ScopedPreferenceStore store = new ScopedPreferenceStore(new WorkingCopyScopeContext(workingCopyManager, InstanceScope.INSTANCE), bundleId);
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

	protected void computeOutlineColor() {
		outlineHueScale.setSelection(startHueScale.getSelection());
		outlineSaturationScale.setSelection(Math.min((int) (startSaturationScale.getSelection() * 3), 100));
		outlineBrightnessScale.setSelection((int) (startBrightnessScale.getSelection() * 0.7));
	}

	private CTabItem createActivePartTab() {
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setImage(SharedImages.getImage(SharedImages.PALETTE));
		Composite body = new Composite(folder, SWT.NORMAL);
		body.setBackgroundMode(SWT.INHERIT_FORCE);
		body.setBackground(folder.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		body.setLayout(new GridLayout(2, true));

		fillActivePartTab(body);
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

		CTabItem colorTab = createActivePartTab();
		createSashTab();
		createTitleTab();

		folder.setSelection(colorTab);

		decorator.apply(folder);

		createLink(composite, "Only works with Chrome Theme, You can change on <a href=\"org.eclipse.ui.preferencePages.Views\">Appearance page</a>");

		getUpdateJob().schedule(100);
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
		String preset = getPreferenceStore().getString(ChromeConstants.CHROME_SASH_PRESET);
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

	private void createTitleTab() {
		CTabItem fontItem = new CTabItem(folder, SWT.CLOSE);
		fontItem.setText("Title");
		fontItem.setImage(SharedImages.getImage(SharedImages.FONT));

		Composite composite = new Composite(folder, SWT.NORMAL);
		composite.setBackground(folder.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		composite.setLayout(new GridLayout(3, false));

		new Label(composite, SWT.NORMAL).setText("Part Title Font:");
		fontPreviewLabel = new Label(composite, SWT.NORMAL);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		fontPreviewLabel.setLayoutData(layoutData);
		Button changeFontButton = new Button(composite, SWT.PUSH);
		changeFontButton.setText("Change");
		changeFontButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				openFontDialog();
			}
		});
		fontItem.setControl(composite);

		/*
		 * 17: Shiny Shadow in unselected tab items should be customized
		 * https://github.com/jeeeyul/eclipse-themes/issues/issue/17
		 */
		usePartTextShadowButton = new Button(composite, SWT.CHECK);
		usePartTextShadowButton.setText("Cast shiny shadows for unselected tabs");
		usePartTextShadowButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		updateFontTab();
	}

	@Override
	public void dispose() {
		decorator.dispose();
		if (outlineColor != null && !outlineColor.isDisposed()) {
			outlineColor.dispose();
		}
		if (previewFont != null && !previewFont.isDisposed()) {
			previewFont.dispose();
		}
		super.dispose();
	}

	private void doUpdate() {
		ChromeTabRendering rendering = (ChromeTabRendering) folder.getRenderer();

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

		if (outlineColor != null && !outlineColor.isDisposed()) {
			outlineColor.dispose();
		}
		float[] out = decorator.getEndHSB();
		out[0] = outlineHueScale.getSelection();
		out[1] = outlineSaturationScale.getSelection() / 100f;
		out[2] = outlineBrightnessScale.getSelection() / 100f;
		outlineColor = new Color(getShell().getDisplay(), new RGB(out[0], out[1], out[2]));

		rendering.setTabOutline(outlineColor);
		rendering.setOuterKeyline(outlineColor);

		decorator.apply(folder);
	}

	private void fillActivePartTab(Composite body) {
		Group startGroup = new Group(body, SWT.NORMAL);
		startGroup.setText("Start Color");
		startGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
				}

				else if (isEndHueSynchronized()) {
					endHueScale.setSelection(startHueScale.getSelection());
				}

				if (autoOutlineColorField.getSelection()) {
					computeOutlineColor();
				}

				else if (lockOutlineHueField.getSelection()) {
					outlineHueScale.setSelection(startHueScale.getSelection());
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
				
				if (autoOutlineColorField.getSelection()) {
					computeOutlineColor();
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
				
				if (autoOutlineColorField.getSelection()) {
					computeOutlineColor();
				}
				update();
			}
		});

		Group endGroup = new Group(body, SWT.NORMAL);
		endGroup.setText("End Color");
		endGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		endGroup.setLayout(new GridLayout(2, false));

		autoEndColorField = new Button(endGroup, SWT.CHECK);
		autoEndColorField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		autoEndColorField.setText("Choose Automatically");
		autoEndColorField.setSelection(getPreferenceStore().getBoolean(ChromeConstants.CHROME_AUTO_ACTIVE_END_COLOR));
		autoEndColorField.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateAuto();
			}
		});

		lockEndHueField = new Button(endGroup, SWT.CHECK);
		lockEndHueField.setText("Lock Hue");
		lockEndHueField.setSelection(getPreferenceStore().getBoolean(ChromeConstants.CHROME_LOCK_ACTIVE_END_HUE));
		lockEndHueField.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateLock();
			}
		});
		lockEndHueField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

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

		Group outlineGroup = new Group(body, SWT.NORMAL);
		outlineGroup.setText("Outline");
		outlineGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		outlineGroup.setLayout(new GridLayout(2, false));

		autoOutlineColorField = new Button(outlineGroup, SWT.CHECK);
		autoOutlineColorField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		autoOutlineColorField.setText("Choose Automatically");
		autoOutlineColorField.setSelection(getPreferenceStore().getBoolean(ChromeConstants.CHROME_AUTO_ACTIVE_OUTLINE_COLOR));
		autoOutlineColorField.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateAuto();
			}
		});

		lockOutlineHueField = new Button(outlineGroup, SWT.CHECK);
		lockOutlineHueField.setText("Lock Hue");
		lockOutlineHueField.setSelection(getPreferenceStore().getBoolean(ChromeConstants.CHROME_LOCK_ACTIVE_OUTLINE_HUE));
		lockOutlineHueField.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateLock();
			}
		});
		lockOutlineHueField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		new org.eclipse.swt.widgets.Label(outlineGroup, SWT.NORMAL).setText("Hue:");
		outlineHueScale = new HueScale(outlineGroup, SWT.NORMAL);
		outlineHueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		outlineHueScale.setSelection(getPreferenceStore().getFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_HUE));
		outlineHueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		new org.eclipse.swt.widgets.Label(outlineGroup, SWT.NORMAL).setText("Saturation:");
		outlineSaturationScale = new Scale(outlineGroup, SWT.NORMAL);
		outlineSaturationScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		outlineSaturationScale.setMinimum(0);
		outlineSaturationScale.setMaximum(100);
		outlineSaturationScale.setSelection((int) (getPreferenceStore().getFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_SATURATION) * 100));
		outlineSaturationScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		new org.eclipse.swt.widgets.Label(outlineGroup, SWT.NORMAL).setText("Brightness:");
		outlineBrightnessScale = new Scale(outlineGroup, SWT.NORMAL);
		outlineBrightnessScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		outlineBrightnessScale.setMinimum(0);
		outlineBrightnessScale.setMaximum(100);
		outlineBrightnessScale.setSelection((int) (getPreferenceStore().getFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_BRIGHTNESS) * 100));
		outlineBrightnessScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		updateLock();
		updateAuto();
	}

	private UIJob getUpdateJob() {
		if (updateJob == null) {
			updateJob = new UIJob("Update") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (getControl() == null || getControl().isDisposed()) {
						return Status.OK_STATUS;
					}
					doUpdate();
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

	private boolean isEndHueSynchronized() {
		return lockEndHueField.getSelection();
	}

	protected void navigateToOtherPage(String pageId) {
		PreferencesUtil.createPreferenceDialogOn(getShell(), pageId, null, null);
	}

	private void openFontDialog() {
		FontDialog dialog = new FontDialog(getShell());

		if (fontPreviewLabel.getData() instanceof FontData) {
			dialog.setFontList(new FontData[] { (FontData) fontPreviewLabel.getData() });
		}

		FontData fontData = dialog.open();
		if (fontData == null) {
			return;
		}

		if (previewFont != null && !previewFont.isDisposed()) {
			previewFont.dispose();
		}
		fontPreviewLabel.setData(fontData);
		fontPreviewLabel.getFont();
		previewFont = new Font(getShell().getDisplay(), fontData);
		fontPreviewLabel.setFont(previewFont);
		fontPreviewLabel.setText(fontData.getName() + " " + fontData.height + "px");
		fontPreviewLabel.setFont(previewFont);
		fontPreviewLabel.getParent().layout(true);
	}

	@Override
	protected void performDefaults() {
		IPreferenceStore store = getPreferenceStore();
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

		autoEndColorField.setSelection(store.getDefaultBoolean(ChromeConstants.CHROME_AUTO_ACTIVE_END_COLOR));
		lockEndHueField.setSelection(store.getDefaultBoolean(ChromeConstants.CHROME_LOCK_ACTIVE_END_HUE));

		outlineHueScale.setSelection((int) getPreferenceStore().getDefaultFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_HUE));
		outlineSaturationScale.setSelection((int) (getPreferenceStore().getDefaultFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_SATURATION) * 100));
		outlineBrightnessScale.setSelection((int) (getPreferenceStore().getDefaultFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_BRIGHTNESS) * 100));

		autoOutlineColorField.setSelection(store.getDefaultBoolean(ChromeConstants.CHROME_AUTO_ACTIVE_OUTLINE_COLOR));
		lockOutlineHueField.setSelection(store.getDefaultBoolean(ChromeConstants.CHROME_LOCK_ACTIVE_OUTLINE_HUE));

		sashWidthScale.setSelection(store.getDefaultInt(ChromeConstants.CHROME_PART_CONTAINER_SASH_WIDTH));
		useShadowField.setSelection(store.getDefaultBoolean(ChromeConstants.CHROME_PART_SHADOW));

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

		if (previewFont != null && !previewFont.isDisposed()) {
			previewFont.dispose();
		}

		String fontName = getPreferenceStore().getDefaultString(ChromeConstants.CHROME_PART_FONT_NAME);
		float height = getPreferenceStore().getDefaultFloat(ChromeConstants.CHROME_PART_FONT_SIZE);
		FontData fontData = new FontData();
		fontData.setName(fontName);
		fontData.height = height;

		fontPreviewLabel.setData(fontData);
		fontPreviewLabel.setText(fontData.getName() + " " + fontData.height + "px");

		previewFont = new Font(getShell().getDisplay(), fontData);
		fontPreviewLabel.setFont(previewFont);
		fontPreviewLabel.getParent().layout(true);

		usePartTextShadowButton.setSelection(getPreferenceStore().getDefaultBoolean(ChromeConstants.CHROME_PART_FONT_SHADOW));

		updateLock();
		updateAuto();
		update();
		updateAdvanced();
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		float[] start = decorator.getStartHSB();
		store.setValue("chrome-active-start-hue", start[0]);
		store.setValue("chrome-active-start-saturation", start[1]);
		store.setValue("chrome-active-start-brightness", start[2]);

		float[] end = decorator.getEndHSB();
		store.setValue("chrome-active-end-hue", end[0]);
		store.setValue("chrome-active-end-saturation", end[1]);
		store.setValue("chrome-active-end-brightness", end[2]);

		store.setValue(ChromeConstants.CHROME_AUTO_ACTIVE_END_COLOR, autoEndColorField.getSelection());
		store.setValue(ChromeConstants.CHROME_LOCK_ACTIVE_END_HUE, lockEndHueField.getSelection());

		store.setValue(ChromeConstants.CHROME_ACTIVE_OUTLINE_HUE, (float) outlineHueScale.getSelection());
		store.setValue(ChromeConstants.CHROME_ACTIVE_OUTLINE_SATURATION, outlineSaturationScale.getSelection() / 100f);
		store.setValue(ChromeConstants.CHROME_ACTIVE_OUTLINE_BRIGHTNESS, outlineBrightnessScale.getSelection() / 100f);

		store.setValue(ChromeConstants.CHROME_AUTO_ACTIVE_OUTLINE_COLOR, autoOutlineColorField.getSelection());
		store.setValue(ChromeConstants.CHROME_LOCK_ACTIVE_OUTLINE_HUE, lockOutlineHueField.getSelection());

		store.setValue(ChromeConstants.CHROME_PART_CONTAINER_SASH_WIDTH, sashWidthScale.getSelection());
		store.setValue(ChromeConstants.CHROME_PART_SHADOW, useShadowField.getSelection());

		if (thinSashButton.getSelection()) {
			store.setValue(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_THIN);
		} else if (standardSashButton.getSelection()) {
			store.setValue(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_STANDARD);
		} else {
			store.setValue(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_ADVANCED);
		}

		Object data = fontPreviewLabel.getData();
		if (data instanceof FontData) {
			FontData fontData = (FontData) data;
			store.setValue(ChromeConstants.CHROME_PART_FONT_NAME, fontData.getName());
			store.setValue(ChromeConstants.CHROME_PART_FONT_SIZE, fontData.height);
		}

		store.setValue(ChromeConstants.CHROME_PART_FONT_SHADOW, usePartTextShadowButton.getSelection());

		try {
			workingCopyManager.applyChanges();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

		new RewriteChormeCSS().rewrite();

		return super.performOk();
	}

	private void update() {
		getUpdateJob().schedule();
	}

	private void updateAdvanced() {
		sashWidthScale.setEnabled(manualSashButton.getSelection());
		useShadowField.setEnabled(manualSashButton.getSelection());
	}

	protected void updateAuto() {
		boolean isAutomaticEndColor = isAutomaticEndColor();
		lockEndHueField.setEnabled(!isAutomaticEndColor);
		endBrightnessScale.setEnabled(!isAutomaticEndColor);
		endHueScale.setEnabled(!isAutomaticEndColor);
		endSaturationScale.setEnabled(!isAutomaticEndColor);

		if (isAutomaticEndColor) {
			computeEndColor();
			update();
		}

		boolean isAutomaticOutlineColor = autoOutlineColorField.getSelection();

		lockOutlineHueField.setEnabled(!isAutomaticOutlineColor);
		outlineBrightnessScale.setEnabled(!isAutomaticOutlineColor);
		outlineHueScale.setEnabled(!isAutomaticOutlineColor);
		outlineSaturationScale.setEnabled(!isAutomaticOutlineColor);
		if (isAutomaticOutlineColor) {
			computeOutlineColor();
			update();
		}

		updateLock();
	}

	private void updateFontTab() {
		if (previewFont != null && !previewFont.isDisposed()) {
			previewFont.dispose();
		}

		String fontName = getPreferenceStore().getString(ChromeConstants.CHROME_PART_FONT_NAME);
		float height = getPreferenceStore().getFloat(ChromeConstants.CHROME_PART_FONT_SIZE);
		FontData fontData = new FontData();
		fontData.setName(fontName);
		fontData.height = height;

		fontPreviewLabel.setData(fontData);
		fontPreviewLabel.setText(fontData.getName() + " " + fontData.height + "px");

		previewFont = new Font(getShell().getDisplay(), fontData);
		fontPreviewLabel.setFont(previewFont);
		fontPreviewLabel.getParent().layout(true);

		usePartTextShadowButton.setSelection(getPreferenceStore().getBoolean(ChromeConstants.CHROME_PART_FONT_SHADOW));
	}

	protected void updateLock() {
		if (isEndHueSynchronized()) {
			endHueScale.setEnabled(false);
			endHueScale.setSelection(startHueScale.getSelection());
		} else {
			endHueScale.setEnabled(true);
		}

		if (lockOutlineHueField.getSelection()) {
			outlineHueScale.setEnabled(false);
			outlineHueScale.setSelection(startHueScale.getSelection());
		} else {
			outlineHueScale.setEnabled(true);
		}

		update();
	}
}
