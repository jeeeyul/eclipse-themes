package net.jeeeyul.eclipse.themes.userpreset;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class UserPresetDialog extends Dialog {
	private List<UserPreset> model;

	public UserPresetDialog(Shell parentShell, List<UserPreset> workingcopy) {
		super(parentShell);
		this.model = workingcopy;
		setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return super.createButtonBar(parent);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite control = (Composite) super.createDialogArea(parent);
		UserPresetEditor editor = new UserPresetEditor(control);
		editor.setModel(model);
		parent.getShell().setText("Presets");
		return control;
	}

	public List<UserPreset> getWorkingCopy() {
		return model;
	}

}
