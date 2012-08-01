package net.jeeeyul.eclipse.themes.preference;

import java.util.ArrayList;

import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.SharedImages;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ChromeThemePrefererncePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage";

	private ArrayList<IChromePage> pages = new ArrayList<IChromePage>();
	private CTabFolder folder;

	public ChromeThemePrefererncePage() {
		setPreferenceStore(ChromeThemeCore.getDefault().getPreferenceStore());
		setupPages();
	}

	protected void addPage(IChromePage page) {
		pages.add(page);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NORMAL);
		container.setLayout(new GridLayout());

		folder = new CTabFolder(container, SWT.CLOSE);
		folder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				event.doit = false;
			}
		});
		folder.setUnselectedCloseVisible(false);
		CSSClasses tags = CSSClasses.getStyleClasses(folder);
		tags.add("chrome-tabfolder-preview");
		CSSClasses.setStyleClasses(folder, tags);

		for (int i = 0; i < pages.size(); i++) {
			IChromePage page = pages.get(i);
			Composite control = new Composite(folder, SWT.NORMAL);
			control.setBackgroundMode(SWT.INHERIT_FORCE);
			control.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
			page.create(control);

			CTabItem item = new CTabItem(folder, SWT.NORMAL);
			item.setControl(control);
			item.setText(page.getTitle());
			item.setImage(page.getImage());
		}
		folder.setSelection(0);

		createFakeToolbar();
		createLink(container, "Only works with Chrome Theme, You can change on <a href=\"org.eclipse.ui.preferencePages.Views\">Appearance page</a>");
		load();

		return container;
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

	@Override
	public void init(IWorkbench workbench) {

	}

	private void load() {
		for (IChromePage page : pages) {
			page.load(getPreferenceStore());
		}
	}

	private void navigateToOtherPage(String pageId) {
		PreferencesUtil.createPreferenceDialogOn(getShell(), pageId, null, null);
	}

	@Override
	protected void performDefaults() {
		for (IChromePage page : pages) {
			page.setToDefault(getPreferenceStore());
		}
	}

	@Override
	public boolean performOk() {
		for (IChromePage page : pages) {
			page.save(getPreferenceStore());
		}
		return true;
	}

	private void setupPages() {
		addPage(new GradientPage());
		addPage(new LayoutPage());
		addPage(new PartTitlePage());
		addPage(new ToolbarPage());
	}

}
