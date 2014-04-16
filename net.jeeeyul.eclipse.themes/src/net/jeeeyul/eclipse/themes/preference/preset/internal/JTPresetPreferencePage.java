package net.jeeeyul.eclipse.themes.preference.preset.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManagerListener;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
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

@SuppressWarnings("javadoc")
public class JTPresetPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, IJTPresetManagerListener {
	private SWTExtensions $ = SWTExtensions.INSTANCE;
	public static final String ID = JTPresetPreferencePage.class.getCanonicalName();

	private TableViewer viewer;
	private Button deleteButton;
	private Button renameButton;

	private Button importButton;
	private Button exportButton;

	public JTPresetPreferencePage() {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NORMAL);
		container.setLayout(new GridLayout(2, false));

		viewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.BORDER | SWT.MULTI);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IJTPreset) element).getName();
			}

			@Override
			public Image getImage(Object element) {
				IJTPreset preset = (IJTPreset) element;
				Image image = preset.getImageDescriptor().createImage();
				$.shouldDisposeWith(image, viewer.getControl());
				return image;
			}
		});
		viewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return JThemesCore.getDefault().getPresetManager().getUserPresets().toArray();
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
		viewer.setInput(JThemesCore.getDefault().getPresetManager().getUserPresets());
		GridData viewerLayoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		viewerLayoutData.grabExcessVerticalSpace = true;
		viewerLayoutData.widthHint = 200;
		viewerLayoutData.heightHint = 200;
		viewerLayoutData.verticalSpan = 4;
		viewer.getControl().setLayoutData(viewerLayoutData);

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

	private void updateButtons() {
		List<UserPreset> selection = getSelection();
		deleteButton.setEnabled(selection.size() > 0);
		renameButton.setEnabled(selection.size() == 1);
		exportButton.setEnabled(selection.size() == 1);
		importButton.setEnabled(true);
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
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.epf" });
		dialog.setFilterNames(new String[] { "Eclipse Preference File" });
		String target = dialog.open();
		if (target != null) {
			File file = new File(target);
			try {
				String name = file.getName().replaceFirst("[.][^.]+$", "");

				IInputValidator nameValidator = JTPUtil.getPresetNameValidator();
				if (nameValidator.isValid(name) != null) {
					InputDialog nameDialog = new InputDialog(getShell(), "New Preset", "Enter a new preset name:", null, nameValidator);
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

	@Override
	public void userPresetModified() {
		if (viewer != null && !viewer.getControl().isDisposed()) {
			viewer.refresh();
		}
	}
}
