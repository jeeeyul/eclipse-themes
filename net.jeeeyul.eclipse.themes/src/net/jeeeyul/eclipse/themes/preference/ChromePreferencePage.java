package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.Activator;
import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.decorator.AutoGradientDecorator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ChromePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "net.jeeeyul.eclipse.themes.ChromePreferencePage";
	private CTabFolder folder;
	private AutoGradientDecorator decorator;
	private Scale hueScale;
	private Scale satScale;

	public ChromePreferencePage() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		float hsb[] = new float[3];
		hsb[0] = store.getFloat("chrome-active-hue");
		hsb[1] = store.getFloat("chrome-active-saturation");
		hsb[2] = store.getFloat("chrome-active-brightness");
		decorator = new AutoGradientDecorator(hsb);
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
		item.setText("Example");

		folder.setSelection(item);
		folder.reskin(SWT.ALL);

		decorator.apply(folder);

		return composite;
	}

	private void fillContents(Composite body) {
		hueScale = new Scale(body, SWT.NORMAL);
		hueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueScale.setMinimum(0);
		hueScale.setMaximum(360);
		hueScale.setSelection((int) decorator.getHsb()[0]);
		hueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

		satScale = new Scale(body, SWT.NORMAL);
		satScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		satScale.setMinimum(0);
		satScale.setMaximum(100);
		satScale.setSelection((int) (decorator.getHsb()[1] * 100));
		satScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				update();
			}
		});

	}

	@Override
	public void dispose() {
		decorator.dispose();
		super.dispose();
	}

	private void update() {
		float[] old = decorator.getHsb();
		old[0] = hueScale.getSelection();
		old[1] = satScale.getSelection() / 100f;
		decorator.setHsb(old);
		decorator.apply(folder);
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		float[] current = decorator.getHsb();
		store.setValue("chrome-active-hue", current[0]);
		store.setValue("chrome-active-saturation", current[1]);
		store.setValue("chrome-active-brightness", current[2]);
		return super.performOk();
	}
}
