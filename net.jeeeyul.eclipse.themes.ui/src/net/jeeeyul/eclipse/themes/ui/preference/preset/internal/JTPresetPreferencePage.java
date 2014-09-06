package net.jeeeyul.eclipse.themes.ui.preference.preset.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.ApplyPresetAction;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManagerListener;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("javadoc")
public class JTPresetPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, IJTPresetManagerListener {
	private SWTExtensions $ = SWTExtensions.INSTANCE;
	public static final String ID = JTPresetPreferencePage.class.getCanonicalName();

	private TableViewer viewer;
	private Button deleteButton;
	private Button renameButton;

	private Button importButton;
	private Button exportButton;
	private Button activateButton;

	public JTPresetPreferencePage() {
	}

	private void activate() {
		IJTPreset selected = (IJTPreset) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		if (selected == null) {
			return;
		}

		new ApplyPresetAction(selected).run();

		copySettingsIfAvailable(selected);
	}

	private void copySettingsIfAvailable(IJTPreset selected) {
		IPreferencePageContainer container = getContainer();
		if (!(container instanceof PreferenceDialog)) {
			return;
		}
		PreferenceManager manager = ((PreferenceDialog) container).getPreferenceManager();
		List<IPreferenceNode> elements = manager.getElements(PreferenceManager.POST_ORDER);
		IPreferenceNode node = IterableExtensions.findFirst(elements, new Function1<IPreferenceNode, Boolean>() {
			@Override
			public Boolean apply(IPreferenceNode t) {
				return t.getId().equals(JTPreferencePage.ID);
			}
		});

		if (node == null || node.getPage() == null) {
			return;
		}

		JTPreferencePage page = (JTPreferencePage) node.getPage();
		JThemePreferenceStore copy = JThemesCore.getDefault().getPreferenceStore().getCopyWithContext(null);
		Properties properties = selected.getProperties();

		for (Object keyObj : properties.keySet()) {
			String key = (String) keyObj;
			String value = properties.getProperty(key);
			if (key.equals(JTPConstants.Layout.TAB_HEIGHT)) {
				int intValue = Integer.parseInt(value);
				copy.setValue(key, Math.max(intValue, SWTExtensions.INSTANCE.getMinimumToolBarHeight()));
			} else {
				copy.setValue(key, value);
			}
		}

		page.loadFrom(copy);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NORMAL);
		container.setLayout(new GridLayout(2, false));

		viewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.BORDER | SWT.MULTI);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				IJTPreset preset = (IJTPreset) element;
				Image image = preset.getImageDescriptor().createImage();
				$.shouldDisposeWith(image, viewer.getControl());
				return image;
			}

			@Override
			public String getText(Object element) {
				return ((IJTPreset) element).getName();
			}
		});
		viewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return JThemesCore.getDefault().getPresetManager().getUserCategory().getPresets().toArray();
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
		viewer.setInput(JThemesCore.getDefault().getPresetManager().getUserCategory());
		GridData viewerLayoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		viewerLayoutData.grabExcessVerticalSpace = true;
		viewerLayoutData.widthHint = 200;
		viewerLayoutData.heightHint = 200;
		viewerLayoutData.verticalSpan = 5;
		viewer.getControl().setLayoutData(viewerLayoutData);

		activateButton = new Button(container, SWT.PUSH);
		activateButton.setText("Apply");
		activateButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		activateButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				activate();
			}
		});

		deleteButton = new Button(container, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		deleteButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				deleteSelection();
			}
		});

		renameButton = new Button(container, SWT.PUSH);
		renameButton.setText("Rename");
		renameButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		renameButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				UserPreset preset = (UserPreset) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
				if (preset != null) {
					rename(preset);
				}
			}
		});

		importButton = new Button(container, SWT.PUSH);
		importButton.setText("Import...");
		importButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		importButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				doImport();
			}
		});

		exportButton = new Button(container, SWT.PUSH);
		exportButton.setText("Export...");
		exportButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		exportButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				doExport();
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});

		updateButtons();

		return container;
	}

	private void deleteSelection() {
		List<UserPreset> selection = getSelection();
		if (selection.isEmpty()) {
			return;
		}
		boolean confirmed = MessageDialog.openQuestion(getShell(), "Jeeeyul's Eclipse Themes", "Are you sure to remove selected use presets?");
		if (confirmed) {
			for (UserPreset each : selection) {
				each.delete();
			}
		}
	}

	@Override
	public void dispose() {
		JThemesCore.getDefault().getPresetManager().removeListener(this);
		super.dispose();
	}

	private void doExport() {
		FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		UserPreset userPreset = getSelection().get(0);
		dialog.setFileName(userPreset.getName() + ".epf");
		dialog.setOverwrite(true);
		dialog.setFilterExtensions(new String[] { "*.epf" });
		dialog.setFilterNames(new String[] { "Eclipse Preference File" });
		String target = dialog.open();
		if (target != null) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(target);
				userPreset.getProperties().store(fos, "Jeeeyul's Eclipse Themes Preset");
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doImport() {
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.MULTI | SWT.SHEET);
		dialog.setFilterExtensions(new String[] { "*.epf" });
		dialog.setFilterNames(new String[] { "Eclipse Preference File" });
		String firstFile = dialog.open();
		if (firstFile != null) {
			File dir = new File(firstFile).getParentFile();
			for (String each : dialog.getFileNames()) {
				importEPF(new File(dir, each));
			}
		}

	}

	private List<UserPreset> getSelection() {
		ArrayList<UserPreset> selection = new ArrayList<UserPreset>();
		Object[] array = ((IStructuredSelection) viewer.getSelection()).toArray();
		for (Object each : array) {
			if (each instanceof UserPreset) {
				selection.add((UserPreset) each);
			}
		}
		return selection;
	}

	private void importEPF(File file) {
		try {
			String name = file.getName().replaceFirst("[.][^.]+$", "");

			IInputValidator nameValidator = JTPUtil.getPresetNameValidator();
			String error = nameValidator.isValid(name);
			if (error != null) {
				String initialInput;
				int number = 2;
				do {
					initialInput = MessageFormat.format("{0}({1})", name, number);
				} while (nameValidator.isValid(initialInput) != null);

				InputDialog nameDialog = new InputDialog(getShell(), "Import Preset", MessageFormat.format("{0} Enter a new preset name:", error),
						initialInput, nameValidator);
				if (nameDialog.open() != IDialogConstants.OK_ID) {
					return;
				} else {
					name = nameDialog.getValue().trim();
				}
			}

			UserPreset newPreset = new UserPreset(name);
			FileInputStream fis = new FileInputStream(file);
			newPreset.getProperties().load(fis);
			fis.close();
			newPreset.save();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(IWorkbench workbench) {
		JThemesCore.getDefault().getPresetManager().addListener(this);
	}

	private void rename(UserPreset preset) {
		InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "New Preset", "Enter a new preset name:", null,
				JTPUtil.getPresetNameValidator());
		if (dialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		preset.setName(dialog.getValue().trim());
		try {
			preset.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateButtons() {
		List<UserPreset> selection = getSelection();
		activateButton.setEnabled(selection.size() == 1);
		deleteButton.setEnabled(selection.size() > 0);
		renameButton.setEnabled(selection.size() == 1);
		exportButton.setEnabled(selection.size() == 1);
		importButton.setEnabled(true);
	}

	@Override
	public void userPresetModified() {
		if (viewer != null && !viewer.getControl().isDisposed()) {
			viewer.refresh();
		}
	}
}
