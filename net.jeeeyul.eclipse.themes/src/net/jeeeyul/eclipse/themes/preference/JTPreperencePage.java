package net.jeeeyul.eclipse.themes.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JTPreperencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public JTPreperencePage() {
	}

	public JTPreperencePage(String title) {
		super(title);
	}

	public JTPreperencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		return new Composite(parent, SWT.DEFAULT);
	}

	@Override
	public void init(IWorkbench workbench) {

	}

}
