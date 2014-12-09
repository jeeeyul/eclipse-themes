package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.Map;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.LayoutPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PartStackETCPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PartStackPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

@SuppressWarnings("all")
public class PartStacksPage extends AbstractJTPreferencePage {
  private AbstractJTPreferencePage[] pages = new AbstractJTPreferencePage[] { new PartStackPage("Active", JTPConstants.ActivePartStack.PREFIX), new PartStackPage("Inactive", JTPConstants.InactivePartStack.PREFIX), new LayoutPage(), new PartStackETCPage() };
  
  private CTabFolder folder;
  
  private Map<AbstractJTPreferencePage, PreperencePageHelper> helperMap = new HashMap<AbstractJTPreferencePage, PreperencePageHelper>();
  
  private PreperencePageHelper helper;
  
  public PartStacksPage() {
    super("Parts");
    Image _image = SharedImages.getImage(SharedImages.PART);
    this.setImage(_image);
  }
  
  public void init(@Extension final PreperencePageHelper helper) {
    this.helper = helper;
  }
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Composite _xblockexpression = null;
    {
      for (final AbstractJTPreferencePage e : this.pages) {
        PreperencePageHelper _helper = this.getHelper(e);
        e.init(_helper);
      }
      final Procedure1<Composite> _function = new Procedure1<Composite>() {
        public void apply(final Composite it) {
          final Procedure1<FillLayout> _function = new Procedure1<FillLayout>() {
            public void apply(final FillLayout it) {
              it.marginWidth = 5;
              it.marginHeight = 5;
            }
          };
          FillLayout _newFillLayout = swtExtensions.newFillLayout(_function);
          it.setLayout(_newFillLayout);
          final Procedure1<CTabFolder> _function_1 = new Procedure1<CTabFolder>() {
            public void apply(final CTabFolder it) {
              it.setTabHeight(22);
            }
          };
          CTabFolder _newCTabFolder = swtExtensions.newCTabFolder(it, SWT.BOTTOM, _function_1);
          PartStacksPage.this.folder = _newCTabFolder;
          final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<CTabFolder> _function_2 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<CTabFolder>() {
            public void apply(final CTabFolder it) {
              for (final AbstractJTPreferencePage e : PartStacksPage.this.pages) {
                final Procedure1<CTabItem> _function = new Procedure1<CTabItem>() {
                  public void apply(final CTabItem it) {
                    String _name = e.getName();
                    it.setText(_name);
                    Image _image = e.getImage();
                    it.setImage(_image);
                    PreperencePageHelper _helper = PartStacksPage.this.getHelper(e);
                    Control _createContents = e.createContents(PartStacksPage.this.folder, swtExtensions, _helper);
                    it.setControl(_createContents);
                    it.setData(e);
                  }
                };
                swtExtensions.newCTabItem(it, _function);
              }
            }
          };
          ObjectExtensions.<CTabFolder>operator_doubleArrow(
            PartStacksPage.this.folder, _function_2);
          PartStacksPage.this.folder.setSelection(0);
          final Listener _function_3 = new Listener() {
            public void handleEvent(final Event it) {
              AbstractJTPreferencePage _activePartStackPage = PartStacksPage.this.getActivePartStackPage();
              PreperencePageHelper _helper = PartStacksPage.this.getHelper(_activePartStackPage);
              _helper.requestFastUpdatePreview();
            }
          };
          swtExtensions.setOnSelection(PartStacksPage.this.folder, _function_3);
        }
      };
      _xblockexpression = swtExtensions.newComposite(parent, _function);
    }
    return _xblockexpression;
  }
  
  public AbstractJTPreferencePage getActivePartStackPage() {
    CTabItem _selection = this.folder.getSelection();
    Object _data = _selection.getData();
    return ((AbstractJTPreferencePage) _data);
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    for (final AbstractJTPreferencePage e : this.pages) {
      {
        boolean update = false;
        if ((e instanceof PartStackPage)) {
          PartStackPage page = ((PartStackPage) e);
          String _context = page.getContext();
          boolean _equals = Objects.equal(_context, JTPConstants.InactivePartStack.PREFIX);
          if (_equals) {
            boolean _and = false;
            AbstractJTPreferencePage _activePage = helper.getActivePage();
            boolean _equals_1 = Objects.equal(_activePage, this);
            if (!_equals_1) {
              _and = false;
            } else {
              AbstractJTPreferencePage _activePartStackPage = this.getActivePartStackPage();
              boolean _equals_2 = Objects.equal(_activePartStackPage, e);
              _and = _equals_2;
            }
            update = _and;
          } else {
            update = true;
          }
        } else {
          update = true;
        }
        if (update) {
          PreperencePageHelper _helper = this.getHelper(e);
          e.updatePreview(folder, renderSettings, swtExtensions, _helper);
        }
      }
    }
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    for (final AbstractJTPreferencePage e : this.pages) {
      PreperencePageHelper _helper = this.getHelper(e);
      e.load(store, swtExtensions, _helper);
    }
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    for (final AbstractJTPreferencePage e : this.pages) {
      PreperencePageHelper _helper = this.getHelper(e);
      e.save(store, swtExtensions, _helper);
    }
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    for (final AbstractJTPreferencePage e : this.pages) {
      PreperencePageHelper _helper = this.getHelper(e);
      e.dispose(swtExtensions, _helper);
    }
  }
  
  private PreperencePageHelper getHelper(final AbstractJTPreferencePage page) {
    PreperencePageHelper result = this.helperMap.get(page);
    boolean _equals = Objects.equal(result, null);
    if (_equals) {
      JTPreferencePage _rootPage = this.helper.getRootPage();
      PreperencePageHelper _preperencePageHelper = new PreperencePageHelper(_rootPage, page);
      result = _preperencePageHelper;
      this.helperMap.put(page, result);
    }
    return result;
  }
  
  public AbstractJTPreferencePage[] getChildren() {
    return this.pages;
  }
}
