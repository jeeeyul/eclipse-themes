package net.jeeeyul.eclipse.themes.preference.preset;

import java.io.IOException;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.preference.internal.UserPreset;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JTPresetPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, IUserPresetChangeListener {
	public static String ID = "net.jeeeyul.eclipse.themes.preference.preset.JTPresetPreferencePage";

	private TableViewer viewer;
	private Button deleteButton;
	private Button renameButton;

	public JTPresetPreferencePage() {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NORMAL);
		container.setLayout(new GridLayout(2, false));

		viewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.BORDER);
		viewer.setLabelProvider(new LabelProvider() {
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
		viewerLayoutData.verticalSpan = 2;
		viewer.getControl().setLayoutData(viewerLayoutData);

		deleteButton = new Button(container, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		deleteButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				UserPreset preset = (UserPreset) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
				if (preset != null) {
					preset.delete();
				}
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

		return container;
	}

	@Override
	public void dispose() {
		JThemesCore.getDefault().getPresetManager().removeListener(this);
		super.dispose();
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
