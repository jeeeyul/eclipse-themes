package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.util.Properties;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class UserCSSPage extends AbstractJTPreferencePage {
  private SourceViewer viewer;
  
  private TextViewerUndoManager undoManager;
  
  private KeyStroke undoKey;
  
  private KeyStroke redoKey;
  
  private KeyStroke selectAllKey;
  
  public UserCSSPage() {
    try {
      this.setName("Custom CSS");
      Image _image = SharedImages.getImage(SharedImages.CSS);
      this.setImage(_image);
      KeyStroke _instance = KeyStroke.getInstance("M1+z");
      this.undoKey = _instance;
      KeyStroke _xifexpression = null;
      Properties _properties = System.getProperties();
      String _property = _properties.getProperty("os.name");
      boolean _contains = _property.contains("Mac");
      if (_contains) {
        _xifexpression = KeyStroke.getInstance("M1+M2+z");
      } else {
        _xifexpression = KeyStroke.getInstance("M1+y");
      }
      this.redoKey = _xifexpression;
      KeyStroke _instance_1 = KeyStroke.getInstance("M1+a");
      this.selectAllKey = _instance_1;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        GridLayout _newGridLayout = swtExtensions.newGridLayout();
        it.setLayout(_newGridLayout);
        final Procedure1<CLabel> _function = new Procedure1<CLabel>() {
          public void apply(final CLabel it) {
            Image _image = SharedImages.getImage(SharedImages.WARN_TSK);
            it.setImage(_image);
            it.setText("Using this feature can cause side effects.");
          }
        };
        swtExtensions.newCLabel(it, _function);
        int _or = swtExtensions.operator_or(SWT.V_SCROLL, SWT.H_SCROLL);
        int _or_1 = swtExtensions.operator_or(_or, SWT.BORDER);
        SourceViewer _sourceViewer = new SourceViewer(it, null, _or_1);
        UserCSSPage.this.viewer = _sourceViewer;
        Document _document = new Document();
        UserCSSPage.this.viewer.setDocument(_document);
        SourceViewerConfiguration _sourceViewerConfiguration = new SourceViewerConfiguration();
        UserCSSPage.this.viewer.configure(_sourceViewerConfiguration);
        TextViewerUndoManager _textViewerUndoManager = new TextViewerUndoManager(100);
        UserCSSPage.this.undoManager = _textViewerUndoManager;
        UserCSSPage.this.undoManager.connect(UserCSSPage.this.viewer);
        UserCSSPage.this.viewer.setUndoManager(UserCSSPage.this.undoManager);
        Control _control = UserCSSPage.this.viewer.getControl();
        final Listener _function_1 = new Listener() {
          public void handleEvent(final Event it) {
            UserCSSPage.this.handleKeyDown(it);
          }
        };
        swtExtensions.setOnKeyDown(_control, _function_1);
        Control _control_1 = UserCSSPage.this.viewer.getControl();
        final Procedure1<GridData> _function_2 = new Procedure1<GridData>() {
          public void apply(final GridData it) {
            it.heightHint = 200;
            it.widthHint = 200;
          }
        };
        GridData _FILL_BOTH = swtExtensions.FILL_BOTH(_function_2);
        _control_1.setLayoutData(_FILL_BOTH);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  private void handleKeyDown(final Event e) {
    boolean _matches = this.matches(e, this.undoKey);
    if (_matches) {
      boolean _undoable = this.undoManager.undoable();
      if (_undoable) {
        this.undoManager.undo();
      }
    } else {
      boolean _matches_1 = this.matches(e, this.redoKey);
      if (_matches_1) {
        boolean _redoable = this.undoManager.redoable();
        if (_redoable) {
          this.undoManager.redo();
        }
      } else {
        boolean _matches_2 = this.matches(e, this.selectAllKey);
        if (_matches_2) {
          IDocument _document = this.viewer.getDocument();
          int _length = _document.getLength();
          this.viewer.setSelectedRange(0, _length);
        }
      }
    }
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    IDocument _document = this.viewer.getDocument();
    String _string = store.getString(JTPConstants.Others.USER_CSS);
    _document.set(_string);
    this.undoManager.reset();
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    IDocument _document = this.viewer.getDocument();
    String _get = _document.get();
    store.setValue(JTPConstants.Others.USER_CSS, _get);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    this.undoManager.disconnect();
  }
  
  private boolean matches(final Event e, final KeyStroke stroke) {
    boolean _and = false;
    int _modifierKeys = stroke.getModifierKeys();
    boolean _equals = (_modifierKeys == e.stateMask);
    if (!_equals) {
      _and = false;
    } else {
      int _naturalKey = stroke.getNaturalKey();
      boolean _equals_1 = (e.keyCode == _naturalKey);
      _and = _equals_1;
    }
    return _and;
  }
}
