package net.jeeeyul.eclipse.themes.preference;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.preference.action.GlobalAdjustmentAction;
import net.jeeeyul.eclipse.themes.preference.action.LoadPresetAction;
import net.jeeeyul.eclipse.themes.preference.action.ShowCurrentCSSAction;
import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;
import net.jeeeyul.eclipse.themes.userpreset.AddUserPresetAction;
import net.jeeeyul.eclipse.themes.userpreset.LoadUserPresetAction;
import net.jeeeyul.eclipse.themes.userpreset.ManageUserPresetAction;
import net.jeeeyul.eclipse.themes.userpreset.UserPreset;
import net.jeeeyul.eclipse.themes.userpreset.UserPresetRepository;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
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
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
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
	private int previousSelection = 0;

	public ChromeThemePrefererncePage() {
		setPreferenceStore(ChromeThemeCore.getDefault().getPreferenceStore());
		setupPages();
	}

	protected void addPage(ChromePage page) {
		pages.add(page);
		page.setParentPage(this);
	}

	public ChromePage getActivePage() {
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
		renderer.setPadding(5, 5, 0, 7);

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
				handleTabSwitching();
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
		}
		folder.setSelection(0);

		createToolbar();
		createLink(container, "Only works with Chrome Theme, You can change on <a href=\"org.eclipse.ui.preferencePages.Views\">Appearance page</a>");

		createGitHubLink(container);

		load();

		return container;
	}

	private void createGitHubLink(Composite container) {
		Composite composite = new Composite(container, SWT.NORMAL);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = layout.marginHeight = 0;
		composite.setLayout(layout);

		Label icon = new Label(composite, SWT.NORMAL);
		icon.setImage(SharedImages.getImage(SharedImages.GITHUB));

		Link forkMeLink = new Link(composite, SWT.NORMAL);
		forkMeLink
				.setText("Fork me on <a href=\"https://github.com/jeeeyul/eclipse-themes\">GitHub</a>, also don't forget <a href=\"http://marketplace.eclipse.org/content/eclipse-4-chrome-theme\">favorite me on marketplace</a>.");
		forkMeLink.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Program.launch(event.text);
			}
		});
	}

	private void handleTabSwitching() {
		ChromePage oldPage = getPages().get(previousSelection);
		ChromePage newPage = getPages().get(folder.getSelectionIndex());
		if (oldPage != newPage) {
			if (oldPage != null) {
				oldPage.onPageDeselected();
			}
			if (newPage != null) {
				newPage.onPageSelected();
			}
		}
		previousSelection = folder.getSelectionIndex();
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

		createMenu();
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

	private void createMenu() {
		MenuManager manager = new MenuManager();

		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				updateMenu(manager);
			}
		});

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

	private void updateMenu(IMenuManager manager) {
		MenuManager presetMenu = new MenuManager("Editor's Presets", SharedImages.getImageDescriptor(SharedImages.PRESET), "preset");
		Enumeration<URL> presets = ChromeThemeCore.getDefault().getBundle().findEntries("presets/", "*.epf", false);
		while (presets.hasMoreElements()) {
			URL url = presets.nextElement();
			IPath path = new Path(url.getFile());
			LoadPresetAction loadPresetAction = new LoadPresetAction(this, url);
			loadPresetAction.setText(path.removeFileExtension().lastSegment());
			presetMenu.add(loadPresetAction);
		}
		manager.add(presetMenu);

		MenuManager userPresetMenu = new MenuManager("User's Presets", SharedImages.getImageDescriptor(SharedImages.PRESET), "preset");
		userPresetMenu.add(new ManageUserPresetAction(this));
		userPresetMenu.add(new AddUserPresetAction(this));
		userPresetMenu.add(new Separator());

		for (UserPreset each : UserPresetRepository.INSTANCE.getUserPresets(true)) {
			userPresetMenu.add(new LoadUserPresetAction(this, each));
		}
		manager.add(userPresetMenu);

		manager.add(new GlobalAdjustmentAction(this));
		manager.add(new Separator());
		manager.add(new ShowCurrentCSSAction(this));

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
		addPage(new GeneralPage());
		addPage(new PartPage("Active", true));
		addPage(new PartPage("Inactive", false));
		addPage(new CommonPartPage());
		addPage(new ToolbarPage());
	}
}
