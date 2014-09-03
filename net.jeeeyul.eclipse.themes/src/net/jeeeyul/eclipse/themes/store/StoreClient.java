package net.jeeeyul.eclipse.themes.store;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.css.RewriteCustomTheme;
import net.jeeeyul.eclipse.themes.internal.Debug;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

/**
 * Store client
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class StoreClient extends EditorPart {
	/**
	 * Store Client Editor ID.
	 */
	public static final String EDITOR_ID = StoreClient.class.getCanonicalName();
	private Browser browser;

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		try {
			browser = new Browser(parent, SWT.NONE);
			if (Debug.useLocalStore()) {
				browser.setUrl("http://localhost:3000/");
			} else {
				browser.setUrl("http://themes.jeeeyul.net");
			}

			browser.addOpenWindowListener(new OpenWindowListener() {
				@Override
				public void open(WindowEvent event) {
					handleOpen(event);
				}

			});

			installFunctions();
		} catch (SWTError e) {
			createBrowserHelpPage(parent);
		}

	}

	private void createBrowserHelpPage(Composite parent) {
		Link link = new Link(parent, SWT.NORMAL);
		link.setText("Could not create browser, Please read <a href=\"https://github.com/jeeeyul/eclipse-themes/wiki/Linux-Theme-Store-Problem\">Linux User Guide</a>");
		link.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Program.launch(event.text);
			}
		});
	}

	private void installFunctions() {
		new BrowserFunction(browser, "__install") {
			public Object function(Object[] arguments) {
				if (arguments.length == 1 && arguments[0] instanceof String) {
					String soruce = (String) arguments[0];
					installEPF(soruce);
				}
				return null;
			}
		};

		new BrowserFunction(browser, "__getCurrentEPF") {
			public Object function(Object[] args) {
				return new EPFGenerator().generate();
			}
		};
	}

	private void installEPF(String epfString) {
		try {
			Properties properties = new Properties();
			properties.load(new ByteArrayInputStream(epfString.getBytes("ISO-8859-1")));
			JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();

			for (Object keyObj : properties.keySet()) {
				String key = (String) keyObj;
				String value = properties.getProperty(key);
				if (key.equals(JTPConstants.Layout.TAB_HEIGHT)) {
					int intValue = Integer.parseInt(value);
					store.setValue(key, Math.max(intValue, SWTExtensions.INSTANCE.getMinimumToolBarHeight()));
				} else {
					store.setValue(key, value);
				}
			}
			new RewriteCustomTheme(true).rewrite();
			MApplication application = (MApplication) PlatformUI.getWorkbench().getService(MApplication.class);
			IEclipseContext context = application.getContext();
			IThemeEngine engine = context.get(IThemeEngine.class);
			if (engine.getActiveTheme() == null || !engine.getActiveTheme().getId().equals(JThemesCore.CUSTOM_THEME_ID)) {
				engine.setTheme(JThemesCore.CUSTOM_THEME_ID, true);
			}

			store.setValue(JTPConstants.Memento.LAST_CHOOSED_PRESET, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleOpen(WindowEvent event) {
		final Shell shell = new Shell(browser.getShell(), SWT.SHELL_TRIM);
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		shell.setLayout(layout);
		Browser browser = new Browser(shell, SWT.NORMAL);

		event.browser = browser;
		browser.addCloseWindowListener(new CloseWindowListener() {
			@Override
			public void close(WindowEvent event) {
				shell.dispose();
			}
		});

		GridData data = new GridData(GridData.FILL_BOTH);
		Point size = event.size != null ? event.size : new Point(640, 480);

		data.widthHint = size.x;
		data.heightHint = size.y;

		browser.setLayoutData(data);
		shell.pack();

		shell.open();
	}

	@Override
	public void setFocus() {
		if (SWTExtensions.INSTANCE.isAlive(browser))
			browser.setFocus();
	}

}
