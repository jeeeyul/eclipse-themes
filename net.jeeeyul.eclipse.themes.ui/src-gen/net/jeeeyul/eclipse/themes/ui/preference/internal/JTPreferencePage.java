package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer;
import net.jeeeyul.eclipse.themes.ui.hotswap.RewriteCustomTheme;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.actions.AddUserPresetAction;
import net.jeeeyul.eclipse.themes.ui.preference.actions.ContributedPresetItems;
import net.jeeeyul.eclipse.themes.ui.preference.actions.ManagePresetAction;
import net.jeeeyul.eclipse.themes.ui.preference.actions.ShowCSSAction;
import net.jeeeyul.eclipse.themes.ui.preference.actions.ShowEPFAction;
import net.jeeeyul.eclipse.themes.ui.preference.actions.UserPresetItems;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.ClosePrevent;
import net.jeeeyul.eclipse.themes.ui.preference.internal.DonationPanel;
import net.jeeeyul.eclipse.themes.ui.preference.internal.FormsPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.GeneralPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PartStacksPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreviewTabFolder;
import net.jeeeyul.eclipse.themes.ui.preference.internal.TextEditorPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.ToolsPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.UserCSSPage;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class JTPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
  public final static String ID = JTPreferencePage.class.getCanonicalName();
  
  @Extension
  private SWTExtensions swtExt = SWTExtensions.INSTANCE;
  
  private Map<AbstractJTPreferencePage, PreperencePageHelper> helperMap = new HashMap<AbstractJTPreferencePage, PreperencePageHelper>();
  
  private Composite rootView;
  
  private JeeeyulsTabRenderer renderer;
  
  private CTabFolder folder;
  
  private List<AbstractJTPreferencePage> pages = new ArrayList<AbstractJTPreferencePage>();
  
  private MenuManager menuManager;
  
  private UIJob updatePreviewJob = this.swtExt.newUIJob(
    new Procedure1<Void>() {
      public void apply(final Void it) {
        Control _control = JTPreferencePage.this.getControl();
        boolean _isAlive = JTPreferencePage.this.swtExt.isAlive(_control);
        if (_isAlive) {
          JTPreferencePage.this.doUpdatePreview();
        }
      }
    });
  
  /**
   * https://github.com/jeeeyul/eclipse-themes/issues/140
   */
  @Accessors
  private String lastChoosedPresetId;
  
  public JTPreferencePage() {
    this.setTitle("Jeeeyul\'s Theme");
    GeneralPage _generalPage = new GeneralPage();
    this.pages.add(_generalPage);
    PartStacksPage _partStacksPage = new PartStacksPage();
    this.pages.add(_partStacksPage);
    TextEditorPage _textEditorPage = new TextEditorPage();
    this.pages.add(_textEditorPage);
    FormsPage _formsPage = new FormsPage();
    this.pages.add(_formsPage);
    UserCSSPage _userCSSPage = new UserCSSPage();
    this.pages.add(_userCSSPage);
    ToolsPage _toolsPage = new ToolsPage();
    this.pages.add(_toolsPage);
  }
  
  public void init(final IWorkbench workbench) {
    JThemesCore _default = JThemesCore.getDefault();
    JThemePreferenceStore _preferenceStore = _default.getPreferenceStore();
    this.setPreferenceStore(_preferenceStore);
  }
  
  public Control createContents(final Composite parent) {
    for (final AbstractJTPreferencePage e : this.pages) {
      PreperencePageHelper _helper = this.getHelper(e);
      e.init(_helper);
    }
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
          public void apply(final GridLayout it) {
          }
        };
        GridLayout _newGridLayout = JTPreferencePage.this.swtExt.newGridLayout(_function);
        it.setLayout(_newGridLayout);
        PreviewTabFolder _previewTabFolder = new PreviewTabFolder(it, SWT.CLOSE);
        JTPreferencePage.this.folder = _previewTabFolder;
        final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<CTabFolder> _function_1 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<CTabFolder>() {
          public void apply(final CTabFolder it) {
            final Procedure1<GridData> _function = new Procedure1<GridData>() {
              public void apply(final GridData it) {
                it.widthHint = 450;
              }
            };
            GridData _FILL_HORIZONTAL = JTPreferencePage.this.swtExt.FILL_HORIZONTAL(_function);
            it.setLayoutData(_FILL_HORIZONTAL);
            it.setUnselectedCloseVisible(false);
            it.setMaximizeVisible(true);
            it.setMinimizeVisible(true);
            ClosePrevent _closePrevent = new ClosePrevent();
            it.addCTabFolder2Listener(_closePrevent);
            JeeeyulsTabRenderer _jeeeyulsTabRenderer = new JeeeyulsTabRenderer(it);
            it.setRenderer((JTPreferencePage.this.renderer = _jeeeyulsTabRenderer));
            for (final AbstractJTPreferencePage each : JTPreferencePage.this.pages) {
              final Procedure1<CTabItem> _function_1 = new Procedure1<CTabItem>() {
                public void apply(final CTabItem it) {
                  String _name = each.getName();
                  it.setText(_name);
                  Image _image = each.getImage();
                  it.setImage(_image);
                  PreperencePageHelper _helper = JTPreferencePage.this.getHelper(each);
                  Control _createContents = each.createContents(JTPreferencePage.this.folder, JTPreferencePage.this.swtExt, _helper);
                  it.setControl(_createContents);
                  Control _control = it.getControl();
                  Color _COLOR_WHITE = JTPreferencePage.this.swtExt.COLOR_WHITE();
                  _control.setBackground(_COLOR_WHITE);
                  Control _control_1 = it.getControl();
                  ((Composite) _control_1).setBackgroundMode(SWT.INHERIT_FORCE);
                  it.setData(each);
                }
              };
              JTPreferencePage.this.swtExt.newCTabItem(it, _function_1);
            }
            CTabItem[] _items = JTPreferencePage.this.folder.getItems();
            CTabItem _head = IterableExtensions.<CTabItem>head(((Iterable<CTabItem>)Conversions.doWrapArray(_items)));
            JTPreferencePage.this.folder.setSelection(_head);
            final Listener _function_2 = new Listener() {
              public void handleEvent(final Event it) {
                AbstractJTPreferencePage _activePage = JTPreferencePage.this.getActivePage();
                JTPreferencePage.this.updatePreview(_activePage);
              }
            };
            JTPreferencePage.this.swtExt.setOnSelection(it, _function_2);
          }
        };
        ObjectExtensions.<CTabFolder>operator_doubleArrow(
          JTPreferencePage.this.folder, _function_1);
        DonationPanel _donationPanel = new DonationPanel(it);
        final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<DonationPanel> _function_2 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<DonationPanel>() {
          public void apply(final DonationPanel it) {
            Control _control = JTPreferencePage.this.getControl();
            GridData _FILL_HORIZONTAL = JTPreferencePage.this.swtExt.FILL_HORIZONTAL();
            _control.setLayoutData(_FILL_HORIZONTAL);
          }
        };
        ObjectExtensions.<DonationPanel>operator_doubleArrow(_donationPanel, _function_2);
      }
    };
    Composite _newComposite = this.swtExt.newComposite(parent, _function);
    this.rootView = _newComposite;
    final Procedure1<ToolBar> _function_1 = new Procedure1<ToolBar>() {
      public void apply(final ToolBar it) {
        final Procedure1<ToolItem> _function = new Procedure1<ToolItem>() {
          public void apply(final ToolItem it) {
            Image _image = SharedImages.getImage(SharedImages.JTHEME);
            it.setImage(_image);
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                JTPreferencePage.this.menuManager.updateAll(true);
                ToolItem item = ((ToolItem) it.widget);
                Menu m = JTPreferencePage.this.menuManager.getMenu();
                ToolBar _parent = item.getParent();
                Rectangle _bounds = item.getBounds();
                Point _bottomLeft = JTPreferencePage.this.swtExt.getBottomLeft(_bounds);
                Point _translated = JTPreferencePage.this.swtExt.getTranslated(_bottomLeft, 0, 2);
                Point _display = _parent.toDisplay(_translated);
                m.setLocation(_display);
                m.setVisible(true);
              }
            };
            JTPreferencePage.this.swtExt.setOnSelection(it, _function);
          }
        };
        JTPreferencePage.this.swtExt.newToolItem(it, SWT.DROP_DOWN, _function);
      }
    };
    ToolBar _newToolBar = this.swtExt.newToolBar(this.folder, _function_1);
    this.folder.setTopRight(_newToolBar);
    MenuManager _menuManager = new MenuManager();
    this.menuManager = _menuManager;
    this.menuManager.createContextMenu(this.folder);
    this.createActions();
    this.doLoad();
    this.doUpdatePreview();
    return this.rootView;
  }
  
  private MenuManager createActions() {
    final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<MenuManager> _function = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<MenuManager>() {
      public void apply(final MenuManager it) {
        IJTPresetManager _presetManager = JTPreferencePage.this.getPresetManager();
        boolean _notEquals = (!Objects.equal(_presetManager, null));
        if (_notEquals) {
          IJTPresetManager _presetManager_1 = JTPreferencePage.this.getPresetManager();
          IJTPresetCategory _userCategory = _presetManager_1.getUserCategory();
          String _name = _userCategory.getName();
          ImageDescriptor _imageDescriptor = SharedImages.getImageDescriptor(SharedImages.PRESET);
          MenuManager _menuManager = new MenuManager(_name, _imageDescriptor, "user.preset");
          final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<MenuManager> _function = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<MenuManager>() {
            public void apply(final MenuManager it) {
              AddUserPresetAction _addUserPresetAction = new AddUserPresetAction(JTPreferencePage.this);
              it.add(_addUserPresetAction);
              ManagePresetAction _managePresetAction = new ManagePresetAction(JTPreferencePage.this);
              it.add(_managePresetAction);
              UserPresetItems _userPresetItems = new UserPresetItems(JTPreferencePage.this);
              it.add(_userPresetItems);
            }
          };
          MenuManager _doubleArrow = ObjectExtensions.<MenuManager>operator_doubleArrow(_menuManager, _function);
          it.add(_doubleArrow);
          IJTPresetManager _presetManager_2 = JTPreferencePage.this.getPresetManager();
          IJTPresetCategory[] _categories = _presetManager_2.getCategories();
          for (final IJTPresetCategory eachCategory : _categories) {
            IJTPresetManager _presetManager_3 = JTPreferencePage.this.getPresetManager();
            IJTPresetCategory _userCategory_1 = _presetManager_3.getUserCategory();
            boolean _notEquals_1 = (!Objects.equal(eachCategory, _userCategory_1));
            if (_notEquals_1) {
              String _name_1 = eachCategory.getName();
              ImageDescriptor _imageDescriptor_1 = SharedImages.getImageDescriptor(SharedImages.PRESET);
              MenuManager _menuManager_1 = new MenuManager(_name_1, _imageDescriptor_1, "contributed.preset");
              final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<MenuManager> _function_1 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<MenuManager>() {
                public void apply(final MenuManager it) {
                  ContributedPresetItems _contributedPresetItems = new ContributedPresetItems(JTPreferencePage.this, eachCategory);
                  it.add(_contributedPresetItems);
                }
              };
              MenuManager _doubleArrow_1 = ObjectExtensions.<MenuManager>operator_doubleArrow(_menuManager_1, _function_1);
              it.add(_doubleArrow_1);
            }
          }
        }
        Separator _separator = new Separator();
        it.add(_separator);
        ShowCSSAction _showCSSAction = new ShowCSSAction(JTPreferencePage.this);
        it.add(_showCSSAction);
        ShowEPFAction _showEPFAction = new ShowEPFAction(JTPreferencePage.this);
        it.add(_showEPFAction);
      }
    };
    return ObjectExtensions.<MenuManager>operator_doubleArrow(
      this.menuManager, _function);
  }
  
  private void doLoad() {
    JThemePreferenceStore _preferenceStore = this.getPreferenceStore();
    this.loadFrom(_preferenceStore);
  }
  
  public boolean performOk() {
    JThemePreferenceStore _preferenceStore = this.getPreferenceStore();
    this.saveTo(_preferenceStore);
    JThemePreferenceStore _preferenceStore_1 = this.getPreferenceStore();
    _preferenceStore_1.save();
    boolean _isRunning = Platform.isRunning();
    if (_isRunning) {
      RewriteCustomTheme _rewriteCustomTheme = new RewriteCustomTheme(true);
      _rewriteCustomTheme.rewrite();
    }
    return true;
  }
  
  public void saveTo(final JThemePreferenceStore store) {
    final Consumer<AbstractJTPreferencePage> _function = new Consumer<AbstractJTPreferencePage>() {
      public void accept(final AbstractJTPreferencePage it) {
        PreperencePageHelper _helper = JTPreferencePage.this.getHelper(it);
        it.save(store, JTPreferencePage.this.swtExt, _helper);
      }
    };
    this.pages.forEach(_function);
    boolean _notEquals = (!Objects.equal(this.lastChoosedPresetId, null));
    if (_notEquals) {
      store.setValue(JTPConstants.Memento.LAST_CHOOSED_PRESET, this.lastChoosedPresetId);
    }
  }
  
  public void loadFrom(final JThemePreferenceStore store) {
    for (final AbstractJTPreferencePage each : this.pages) {
      PreperencePageHelper _helper = this.getHelper(each);
      each.load(store, this.swtExt, _helper);
    }
    this.updatePreview();
  }
  
  protected void performDefaults() {
    PreferenceStore _preferenceStore = new PreferenceStore();
    JThemePreferenceStore dummy = new JThemePreferenceStore(_preferenceStore);
    final List<String> presetKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET);
    for (final String e : presetKeys) {
      JThemePreferenceStore _preferenceStore_1 = this.getPreferenceStore();
      String _defaultString = _preferenceStore_1.getDefaultString(e);
      dummy.setValue(e, _defaultString);
    }
    for (final AbstractJTPreferencePage e_1 : this.pages) {
      PreperencePageHelper _helper = this.getHelper(e_1);
      e_1.load(dummy, this.swtExt, _helper);
    }
    this.updatePreview();
  }
  
  public JThemePreferenceStore getPreferenceStore() {
    IPreferenceStore _preferenceStore = super.getPreferenceStore();
    return ((JThemePreferenceStore) _preferenceStore);
  }
  
  public AbstractJTPreferencePage getActivePage() {
    CTabItem _selection = this.folder.getSelection();
    Object _data = _selection.getData();
    return ((AbstractJTPreferencePage) _data);
  }
  
  private void updatePreview(final AbstractJTPreferencePage page) {
    JTabSettings _settings = this.renderer.getSettings();
    PreperencePageHelper _helper = this.getHelper(page);
    page.updatePreview(this.folder, _settings, this.swtExt, _helper);
  }
  
  public static void main(final String[] args) {
    try {
      PreferenceManager manager = new PreferenceManager();
      JTPreferencePage prefPage = new JTPreferencePage();
      PreferenceNode _preferenceNode = new PreferenceNode("Active", prefPage);
      manager.addToRoot(_preferenceNode);
      String userDir = System.getProperty("user.home");
      File file = new File(userDir, ".jet-dummy-pref");
      String _absolutePath = file.getAbsolutePath();
      PreferenceStore store = new PreferenceStore(_absolutePath);
      Properties defaults = new Properties();
      InputStream _resourceAsStream = JTPreferencePage.class.getResourceAsStream("default.epf");
      defaults.load(_resourceAsStream);
      Set<Object> _keySet = defaults.keySet();
      for (final Object each : _keySet) {
        String _property = defaults.getProperty(((String) each));
        store.setDefault(((String) each), _property);
      }
      try {
        store.load();
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      JThemePreferenceStore _jThemePreferenceStore = new JThemePreferenceStore(store);
      prefPage.setPreferenceStore(_jThemePreferenceStore);
      PreferenceDialog _preferenceDialog = new PreferenceDialog(null, manager);
      _preferenceDialog.open();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void updatePreview() {
    this.updatePreviewJob.schedule();
  }
  
  public void doUpdatePreview() {
    Control _control = this.getControl();
    boolean _isAlive = this.swtExt.isAlive(_control);
    boolean _not = (!_isAlive);
    if (_not) {
      return;
    }
    for (final AbstractJTPreferencePage p : this.pages) {
      this.updatePreview(p);
    }
    this.rootView.layout(new Control[] { this.folder });
  }
  
  public void dispose() {
    final Consumer<AbstractJTPreferencePage> _function = new Consumer<AbstractJTPreferencePage>() {
      public void accept(final AbstractJTPreferencePage it) {
        PreperencePageHelper _helper = JTPreferencePage.this.getHelper(it);
        it.dispose(JTPreferencePage.this.swtExt, _helper);
      }
    };
    this.pages.forEach(_function);
    super.dispose();
  }
  
  private PreperencePageHelper getHelper(final AbstractJTPreferencePage page) {
    PreperencePageHelper result = this.helperMap.get(page);
    boolean _equals = Objects.equal(result, null);
    if (_equals) {
      PreperencePageHelper _preperencePageHelper = new PreperencePageHelper(this, page);
      result = _preperencePageHelper;
      this.helperMap.put(page, result);
    }
    return result;
  }
  
  public CTabFolder getFolder() {
    return this.folder;
  }
  
  public IJTPresetManager getPresetManager() {
    Object _xifexpression = null;
    boolean _isRunning = Platform.isRunning();
    if (_isRunning) {
      JThemesCore _default = JThemesCore.getDefault();
      return _default.getPresetManager();
    } else {
      _xifexpression = null;
    }
    return ((IJTPresetManager)_xifexpression);
  }
  
  public AbstractJTPreferencePage[] getAllPages() {
    List<AbstractJTPreferencePage> result = new ArrayList<AbstractJTPreferencePage>();
    for (final AbstractJTPreferencePage each : this.pages) {
      {
        result.add(each);
        AbstractJTPreferencePage[] _children = each.getChildren();
        CollectionExtensions.<AbstractJTPreferencePage>addAll(result, _children);
      }
    }
    return ((AbstractJTPreferencePage[])Conversions.unwrapArray(result, AbstractJTPreferencePage.class));
  }
  
  @Pure
  public String getLastChoosedPresetId() {
    return this.lastChoosedPresetId;
  }
  
  public void setLastChoosedPresetId(final String lastChoosedPresetId) {
    this.lastChoosedPresetId = lastChoosedPresetId;
  }
}
