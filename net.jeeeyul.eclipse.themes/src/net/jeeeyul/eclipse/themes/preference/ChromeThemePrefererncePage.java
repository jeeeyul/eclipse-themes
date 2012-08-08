package net.jeeeyul.eclipse.themes.preference;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.preference.action.LoadPresetAction;
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ChromeThemePrefererncePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage";

	private ArrayList<ChromePage> pages = new ArrayList<ChromePage>();
	private CTabFolder folder;

	private ToolItem presetItem;

	public ChromeThemePrefererncePage() {
		setPreferenceStore(ChromeThemeCore.getDefault().getPreferenceStore());
		setupPages();
	}

	protected void addPage(ChromePage page) {
		pages.add(page);
		page.setParentPage(this);
	}
	
	public ChromePage getActivePage(){
		return getPages().get(folder.getSelectionIndex());
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NORMAL);
		container.setLayout(new GridLayout());

		folder = new CTabFolder(container, SWT.MULTI);
		
		ChromeTabRendering renderer = new ChromeTabRendering(folder);
		renderer.setSelectedTabFill(folder.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		renderer.setOuterKeyline(folder.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		renderer.setPadding(9, 9, 0, 10);

		folder.setRenderer(renderer);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		folder.setLayoutData(layoutData);
		folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				event.doit = false;
			}
		});
		
		folder.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				getPages().get(folder.getSelectionIndex()).onPageSelected();
			}
		});
		
		folder.setUnselectedCloseVisible(false);

		for (int i = 0; i < pages.size(); i++) {
			ChromePage page = pages.get(i);
			Composite control = new Composite(folder, SWT.NORMAL);
			control.setBackgroundMode(SWT.INHERIT_FORCE);
			control.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
			page.setTabFolder(folder);
			page.create(control);

			CTabItem item = new CTabItem(folder, SWT.NORMAL);
			item.setControl(control);
			item.setText(page.getTitle());
			item.setImage(page.getPageImage());
		}
		folder.setSelection(0);

		createToolbar();
		createLink(container, "Only works with Chrome Theme, You can change on <a href=\"org.eclipse.ui.preferencePages.Views\">Appearance page</a>");

		load();

		return container;
	}

	private void createToolbar() {
		ToolBar toolBar = new ToolBar(folder, SWT.FLAT | SWT.RIGHT);
		folder.setTopRight(toolBar);

		presetItem = new ToolItem(toolBar, SWT.DROP_DOWN);
		presetItem.setImage(SharedImages.getImage(SharedImages.PALETTE));
		presetItem.setToolTipText("Preset");
		presetItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {

			}
		});

		createPresetMenu();
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

	private void createPresetMenu() {
		MenuManager manager = new MenuManager();

		Action label = new Action() {
		};
		label.setText("Editor's Presets");
		label.setEnabled(false);
		manager.add(label);

		manager.add(new Separator());

		Enumeration<URL> presets = ChromeThemeCore.getDefault().getBundle().findEntries("presets/", "*.epf", false);
		while (presets.hasMoreElements()) {
			URL url = presets.nextElement();
			IPath path = new Path(url.getFile());
			LoadPresetAction loadPresetAction = new LoadPresetAction(this, url);
			loadPresetAction.setText(path.removeFileExtension().lastSegment());
			manager.add(loadPresetAction);
		}

		final Menu menu = manager.createContextMenu(getShell());

		presetItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle rect = presetItem.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = presetItem.getParent().toDisplay(pt);
				menu.setLocation(pt.x, pt.y);
				menu.setVisible(true);
			}
		});
	}

	@Override
	public void dispose() {
		for (ChromePage page : pages) {
			page.dispose();
		}
		super.dispose();
	}

	public ArrayList<ChromePage> getPages() {
		return pages;
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	private void load() {
		for (ChromePage page : pages) {
			page.load(getPreferenceStore());
		}
	}

	private void navigateToOtherPage(String pageId) {
		PreferencesUtil.createPreferenceDialogOn(getShell(), pageId, null, null);
	}

	@Override
	protected void performDefaults() {
		for (ChromePage page : pages) {
			page.setToDefault(getPreferenceStore());
		}
	}

	@Override
	public boolean performOk() {
		for (ChromePage page : pages) {
			page.save(getPreferenceStore());
		}
		return true;
	}

	private void setupPages() {
		addPage(new PartPage("Active Part", true));
		addPage(new PartPage("Inactive Part", false));
		addPage(new CommonPartPage());
		addPage(new WindowPage());
		addPage(new ToolbarPage());
	}
}
