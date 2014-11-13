package net.jeeeyul.eclipse.themes.ui;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.SwitchThemeAction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class OtherThemesContribution extends CompoundContributionItem {
  protected IContributionItem[] getContributionItems() {
    ArrayList<IContributionItem> result = new ArrayList<IContributionItem>();
    IThemeEngine _themeEngine = this.getThemeEngine();
    List<ITheme> _themes = _themeEngine.getThemes();
    for (final ITheme each : _themes) {
      String _id = each.getId();
      boolean _notEquals = (!Objects.equal(_id, JThemesCore.CUSTOM_THEME_ID));
      if (_notEquals) {
        SwitchThemeAction _switchThemeAction = new SwitchThemeAction(each);
        ActionContributionItem _actionContributionItem = new ActionContributionItem(_switchThemeAction);
        result.add(_actionContributionItem);
      }
    }
    return ((IContributionItem[])Conversions.unwrapArray(result, IContributionItem.class));
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
