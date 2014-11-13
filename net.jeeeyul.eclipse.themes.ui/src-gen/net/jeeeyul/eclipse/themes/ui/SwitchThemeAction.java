package net.jeeeyul.eclipse.themes.ui;

import com.google.common.base.Objects;
import net.jeeeyul.eclipse.themes.SharedImages;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

@SuppressWarnings("all")
public class SwitchThemeAction extends Action {
  private ITheme theme;
  
  public SwitchThemeAction(final ITheme theme) {
    super(theme.getLabel(), SWT.CHECK);
    this.theme = theme;
    ImageDescriptor _imageDescriptor = SharedImages.getImageDescriptor(SharedImages.CSS);
    this.setImageDescriptor(_imageDescriptor);
    IThemeEngine _themeEngine = this.getThemeEngine();
    ITheme _activeTheme = _themeEngine.getActiveTheme();
    boolean _equals = Objects.equal(_activeTheme, theme);
    this.setChecked(_equals);
  }
  
  public void run() {
    IThemeEngine _themeEngine = this.getThemeEngine();
    _themeEngine.setTheme(this.theme, true);
  }
  
  private MApplication getApplication() {
    IWorkbench _workbench = PlatformUI.getWorkbench();
    Object _service = _workbench.getService(MApplication.class);
    return ((MApplication) _service);
  }
  
  private IThemeEngine getThemeEngine() {
    MApplication _application = this.getApplication();
    IEclipseContext _context = _application.getContext();
    IThemeEngine _get = _context.<IThemeEngine>get(IThemeEngine.class);
    return ((IThemeEngine) _get);
  }
}
