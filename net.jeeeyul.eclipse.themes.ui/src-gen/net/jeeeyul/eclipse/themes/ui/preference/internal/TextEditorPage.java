package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.css.EditorLineSupport;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.LineStyleEditor;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreferenceLink;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.ColorWell;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

@SuppressWarnings("all")
public class TextEditorPage extends AbstractJTPreferencePage {
  private final static String PREF_ID_COLOR_THEME = "com.github.eclipsecolortheme.preferences.ColorThemePreferencePage";
  
  private final static String PREF_ID_EDITBOX = "pm.eclipse.editbox.pref.default";
  
  private final static String PREF_ID_GENERAL_TEXT_EDITORS = "org.eclipse.ui.preferencePages.GeneralTextEditor";
  
  public TextEditorPage() {
    super("Editor");
    Image _image = SharedImages.getImage(SharedImages.FILE);
    this.setImage(_image);
  }
  
  private SourceViewer preview;
  
  private LineStyleEditor underLineStyleEdit;
  
  private ColorWell underLineColorEdit;
  
  private ColorWell rulerColorEdit;
  
  private Composite previewWrap;
  
  private IVerticalRulerColumn annotationRulerColumn;
  
  private LineNumberRulerColumn lineNumberRulerColumn;
  
  private Color textEditorBackground;
  
  private Color textEditorForeground;
  
  private Color rulerColor;
  
  private Color lineNumberColor;
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
          public void apply(final GridLayout it) {
            it.numColumns = 2;
          }
        };
        GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
        it.setLayout(_newGridLayout);
        final Procedure1<Composite> _function_1 = new Procedure1<Composite>() {
          public void apply(final Composite it) {
            final Procedure1<GridData> _function = new Procedure1<GridData>() {
              public void apply(final GridData it) {
                it.horizontalSpan = 2;
              }
            };
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL(_function);
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function_1 = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.marginWidth = 0;
                it.marginHeight = 0;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function_1);
            it.setLayout(_newGridLayout);
            CompositeRuler ruler = new CompositeRuler(1);
            AnnotationRulerColumn _annotationRulerColumn = new AnnotationRulerColumn(12);
            TextEditorPage.this.annotationRulerColumn = _annotationRulerColumn;
            ruler.addDecorator(0, TextEditorPage.this.annotationRulerColumn);
            LineNumberRulerColumn _lineNumberRulerColumn = new LineNumberRulerColumn();
            TextEditorPage.this.lineNumberRulerColumn = _lineNumberRulerColumn;
            ruler.addDecorator(1, TextEditorPage.this.lineNumberRulerColumn);
            SourceViewer _sourceViewer = new SourceViewer(it, ruler, SWT.V_SCROLL);
            TextEditorPage.this.preview = _sourceViewer;
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Jeeeyul\'s Eclipse Themes Text Editor Preview");
            _builder.newLine();
            _builder.newLine();
            _builder.append("\uc724\uc2ac\uc544 \ud0c4\uc0dd 100\uc77c\uc744 \ucd95\ud558\ud55c\ub2e4.");
            _builder.newLine();
            _builder.append("\uc624\ub798\uc624\ub798 \uac74\uac15\ud558\uace0 \ud589\ubcf5\ud558\uac8c \uc0b4\uc790.");
            _builder.newLine();
            _builder.newLine();
            _builder.append("2014\ub144 8\uc6d4 20\uc77c \uc544\ube60\uac00.");
            _builder.newLine();
            Document _document = new Document(_builder.toString());
            TextEditorPage.this.preview.setDocument(_document);
            Control _control = TextEditorPage.this.preview.getControl();
            final Procedure1<GridData> _function_2 = new Procedure1<GridData>() {
              public void apply(final GridData it) {
                it.widthHint = 200;
                it.heightHint = 100;
              }
            };
            GridData _FILL_HORIZONTAL_1 = swtExtensions.FILL_HORIZONTAL(_function_2);
            _control.setLayoutData(_FILL_HORIZONTAL_1);
          }
        };
        Composite _newComposite = swtExtensions.newComposite(it, SWT.BORDER, _function_1);
        TextEditorPage.this.previewWrap = _newComposite;
        final Procedure1<Label> _function_2 = new Procedure1<Label>() {
          public void apply(final Label it) {
            it.setText("Underline Style");
          }
        };
        swtExtensions.newLabel(it, _function_2);
        LineStyleEditor _lineStyleEditor = new LineStyleEditor(it);
        final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<LineStyleEditor> _function_3 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<LineStyleEditor>() {
          public void apply(final LineStyleEditor it) {
            Control _control = it.getControl();
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            _control.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<LineStyleEditor> _function = new Procedure1<LineStyleEditor>() {
              public void apply(final LineStyleEditor it) {
                helper.requestFastUpdatePreview();
              }
            };
            it.setSelectionHandler(_function);
          }
        };
        LineStyleEditor _doubleArrow = ObjectExtensions.<LineStyleEditor>operator_doubleArrow(_lineStyleEditor, _function_3);
        TextEditorPage.this.underLineStyleEdit = _doubleArrow;
        final Procedure1<Label> _function_4 = new Procedure1<Label>() {
          public void apply(final Label it) {
            it.setText("Underline Color");
          }
        };
        swtExtensions.newLabel(it, _function_4);
        final Procedure1<ColorWell> _function_5 = new Procedure1<ColorWell>() {
          public void apply(final ColorWell it) {
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                helper.requestFastUpdatePreview();
              }
            };
            swtExtensions.setOnModified(it, _function);
          }
        };
        ColorWell _newColorWell = helper.newColorWell(it, _function_5);
        TextEditorPage.this.underLineColorEdit = _newColorWell;
        final Procedure1<Label> _function_6 = new Procedure1<Label>() {
          public void apply(final Label it) {
            it.setText("Ruler Color");
          }
        };
        swtExtensions.newLabel(it, _function_6);
        final Procedure1<ColorWell> _function_7 = new Procedure1<ColorWell>() {
          public void apply(final ColorWell it) {
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                helper.requestFastUpdatePreview();
              }
            };
            swtExtensions.setOnModified(it, _function);
          }
        };
        ColorWell _newColorWell_1 = helper.newColorWell(it, _function_7);
        TextEditorPage.this.rulerColorEdit = _newColorWell_1;
        final Procedure1<Label> _function_8 = new Procedure1<Label>() {
          public void apply(final Label it) {
          }
        };
        swtExtensions.newHorizontalSeparator(it, _function_8);
        final Procedure1<Link> _function_9 = new Procedure1<Link>() {
          public void apply(final Link it) {
            final Procedure1<GridData> _function = new Procedure1<GridData>() {
              public void apply(final GridData it) {
                it.horizontalSpan = 2;
              }
            };
            GridData _newGridData = swtExtensions.newGridData(_function);
            it.setLayoutData(_newGridData);
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("See also ");
            PreferenceLink[] _links = TextEditorPage.this.getLinks();
            final Function1<PreferenceLink, String> _function_1 = new Function1<PreferenceLink, String>() {
              public String apply(final PreferenceLink it) {
                return TextEditorPage.this.toHTML(it);
              }
            };
            List<String> _map = ListExtensions.<PreferenceLink, String>map(((List<PreferenceLink>)Conversions.doWrapArray(_links)), _function_1);
            String _smartJoin = TextEditorPage.this.smartJoin(_map, ", ", " and ");
            _builder.append(_smartJoin, "");
            _builder.append(".");
            it.setText(_builder.toString());
            final Listener _function_2 = new Listener() {
              public void handleEvent(final Event it) {
                helper.navigateTo(it.text);
              }
            };
            swtExtensions.setOnSelection(it, _function_2);
          }
        };
        swtExtensions.newLink(it, _function_9);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    StyledText _textWidget = this.preview.getTextWidget();
    EditorLineSupport _get = EditorLineSupport.get(_textWidget);
    final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<EditorLineSupport> _function = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<EditorLineSupport>() {
      public void apply(final EditorLineSupport it) {
        HSB _selection = TextEditorPage.this.underLineColorEdit.getSelection();
        it.setLineColor(_selection);
        int _selection_1 = TextEditorPage.this.underLineStyleEdit.getSelection();
        it.setLineStyle(_selection_1);
      }
    };
    ObjectExtensions.<EditorLineSupport>operator_doubleArrow(_get, _function);
    StyledText _textWidget_1 = this.preview.getTextWidget();
    Color _background = _textWidget_1.getBackground();
    swtExtensions.<Color>safeDispose(_background);
    swtExtensions.<Color>safeDispose(this.textEditorBackground);
    StyledText _textWidget_2 = this.preview.getTextWidget();
    HSB _computeTextEditorBackground = this.computeTextEditorBackground();
    Color _newColor = swtExtensions.newColor(_computeTextEditorBackground);
    _textWidget_2.setBackground((this.textEditorBackground = _newColor));
    this.lineNumberRulerColumn.setBackground(this.textEditorBackground);
    swtExtensions.<Color>safeDispose(this.lineNumberColor);
    HSB _computeLineNumberForeground = this.computeLineNumberForeground();
    Color _newColor_1 = swtExtensions.newColor(_computeLineNumberForeground);
    this.lineNumberRulerColumn.setForeground((this.lineNumberColor = _newColor_1));
    swtExtensions.<Color>safeDispose(this.textEditorForeground);
    HSB _computeTextEditorForeground = this.computeTextEditorForeground();
    Color _newColor_2 = swtExtensions.newColor(_computeTextEditorForeground);
    this.textEditorForeground = _newColor_2;
    StyledText _textWidget_3 = this.preview.getTextWidget();
    _textWidget_3.setForeground(this.textEditorForeground);
    swtExtensions.<Color>safeDispose(this.rulerColor);
    HSB _selection = this.rulerColorEdit.getSelection();
    Color _newColor_3 = swtExtensions.newColor(_selection);
    this.previewWrap.setBackground((this.rulerColor = _newColor_3));
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    int _int = store.getInt(JTPConstants.TextEditor.UNDER_LINE_STYLE);
    this.underLineStyleEdit.setSelection(_int);
    HSB underLineColor = store.getHSB(JTPConstants.TextEditor.UNDER_LINE_COLOR);
    boolean _notEquals = (!Objects.equal(underLineColor, null));
    if (_notEquals) {
      this.underLineColorEdit.setSelection(underLineColor);
    }
    HSB rulerColor = store.getHSB(JTPConstants.TextEditor.RULER_COLOR);
    boolean _notEquals_1 = (!Objects.equal(rulerColor, null));
    if (_notEquals_1) {
      this.rulerColorEdit.setSelection(rulerColor);
    }
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    int _selection = this.underLineStyleEdit.getSelection();
    store.setValue(JTPConstants.TextEditor.UNDER_LINE_STYLE, _selection);
    HSB _selection_1 = this.underLineColorEdit.getSelection();
    store.setValue(JTPConstants.TextEditor.UNDER_LINE_COLOR, _selection_1);
    HSB _selection_2 = this.rulerColorEdit.getSelection();
    store.setValue(JTPConstants.TextEditor.RULER_COLOR, _selection_2);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    swtExtensions.<Color>safeDispose(Collections.<Color>unmodifiableList(CollectionLiterals.<Color>newArrayList(this.textEditorBackground, this.textEditorForeground, this.rulerColor, this.lineNumberColor)));
  }
  
  private HSB computeTextEditorBackground() {
    boolean _isRunning = Platform.isRunning();
    boolean _equals = (_isRunning == false);
    if (_equals) {
      return HSB.WHITE;
    }
    IPreferenceStore _preferenceStore = EditorsUI.getPreferenceStore();
    String exp = _preferenceStore.getString("AbstractTextEditor.Color.Background");
    String[] _split = exp.split(",");
    final Function1<String, Integer> _function = new Function1<String, Integer>() {
      public Integer apply(final String it) {
        return Integer.valueOf(Integer.parseInt(it));
      }
    };
    List<Integer> rgbArr = ListExtensions.<String, Integer>map(((List<String>)Conversions.doWrapArray(_split)), _function);
    Integer _get = rgbArr.get(0);
    Integer _get_1 = rgbArr.get(1);
    Integer _get_2 = rgbArr.get(2);
    RGB rgb = new RGB((_get).intValue(), (_get_1).intValue(), (_get_2).intValue());
    return new HSB(rgb);
  }
  
  private HSB computeTextEditorForeground() {
    boolean _isRunning = Platform.isRunning();
    boolean _equals = (_isRunning == false);
    if (_equals) {
      return HSB.BLACK;
    }
    IPreferenceStore _preferenceStore = EditorsUI.getPreferenceStore();
    String exp = _preferenceStore.getString("AbstractTextEditor.Color.Foreground");
    String[] _split = exp.split(",");
    final Function1<String, Integer> _function = new Function1<String, Integer>() {
      public Integer apply(final String it) {
        return Integer.valueOf(Integer.parseInt(it));
      }
    };
    List<Integer> rgbArr = ListExtensions.<String, Integer>map(((List<String>)Conversions.doWrapArray(_split)), _function);
    Integer _get = rgbArr.get(0);
    Integer _get_1 = rgbArr.get(1);
    Integer _get_2 = rgbArr.get(2);
    RGB rgb = new RGB((_get).intValue(), (_get_1).intValue(), (_get_2).intValue());
    return new HSB(rgb);
  }
  
  private HSB computeLineNumberForeground() {
    boolean _isRunning = Platform.isRunning();
    boolean _equals = (_isRunning == false);
    if (_equals) {
      return HSB.BLACK;
    }
    IPreferenceStore _preferenceStore = EditorsUI.getPreferenceStore();
    String exp = _preferenceStore.getString("lineNumberColor");
    String[] _split = exp.split(",");
    final Function1<String, Integer> _function = new Function1<String, Integer>() {
      public Integer apply(final String it) {
        return Integer.valueOf(Integer.parseInt(it));
      }
    };
    List<Integer> rgbArr = ListExtensions.<String, Integer>map(((List<String>)Conversions.doWrapArray(_split)), _function);
    Integer _get = rgbArr.get(0);
    Integer _get_1 = rgbArr.get(1);
    Integer _get_2 = rgbArr.get(2);
    RGB rgb = new RGB((_get).intValue(), (_get_1).intValue(), (_get_2).intValue());
    return new HSB(rgb);
  }
  
  private PreferenceLink[] getLinks() {
    boolean _isRunning = Platform.isRunning();
    boolean _equals = (_isRunning == false);
    if (_equals) {
      return new PreferenceLink[] {};
    }
    ArrayList<PreferenceLink> result = new ArrayList<PreferenceLink>();
    boolean _isPreferencePageExists = this.isPreferencePageExists(TextEditorPage.PREF_ID_EDITBOX);
    if (_isPreferencePageExists) {
      PreferenceLink _preferenceLink = new PreferenceLink("EditBox", TextEditorPage.PREF_ID_EDITBOX);
      result.add(_preferenceLink);
    }
    boolean _isPreferencePageExists_1 = this.isPreferencePageExists(TextEditorPage.PREF_ID_COLOR_THEME);
    if (_isPreferencePageExists_1) {
      PreferenceLink _preferenceLink_1 = new PreferenceLink("Eclipse Color Theme", TextEditorPage.PREF_ID_COLOR_THEME);
      result.add(_preferenceLink_1);
    }
    PreferenceLink _preferenceLink_2 = new PreferenceLink("Text Editors Section", TextEditorPage.PREF_ID_GENERAL_TEXT_EDITORS);
    result.add(_preferenceLink_2);
    return ((PreferenceLink[])Conversions.unwrapArray(result, PreferenceLink.class));
  }
  
  private boolean isPreferencePageExists(final String id) {
    boolean _xblockexpression = false;
    {
      IExtensionRegistry _extensionRegistry = Platform.getExtensionRegistry();
      final IExtensionPoint prefNodes = _extensionRegistry.getExtensionPoint("org.eclipse.ui.preferencePages");
      IConfigurationElement[] _configurationElements = prefNodes.getConfigurationElements();
      final Function1<IConfigurationElement, Boolean> _function = new Function1<IConfigurationElement, Boolean>() {
        public Boolean apply(final IConfigurationElement it) {
          String _attribute = it.getAttribute("id");
          return Boolean.valueOf(Objects.equal(_attribute, id));
        }
      };
      _xblockexpression = IterableExtensions.<IConfigurationElement>exists(((Iterable<IConfigurationElement>)Conversions.doWrapArray(_configurationElements)), _function);
    }
    return _xblockexpression;
  }
  
  private String smartJoin(final Iterable<String> segments, final String delimeter1, final String delimeter2) {
    int _length = ((Object[])Conversions.unwrapArray(segments, Object.class)).length;
    boolean _greaterThan = (_length > 2);
    if (_greaterThan) {
      List<String> _list = IterableExtensions.<String>toList(segments);
      int _length_1 = ((Object[])Conversions.unwrapArray(segments, Object.class)).length;
      int _minus = (_length_1 - 1);
      List<String> _subList = _list.subList(0, _minus);
      String _join = IterableExtensions.join(_subList, delimeter1);
      String _plus = (_join + delimeter2);
      String _last = IterableExtensions.<String>last(segments);
      return (_plus + _last);
    } else {
      return IterableExtensions.join(segments, delimeter2);
    }
  }
  
  private String toHTML(final PreferenceLink link) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<a href=\"");
    String _prefId = link.getPrefId();
    _builder.append(_prefId, "");
    _builder.append("\">");
    String _name = link.getName();
    _builder.append(_name, "");
    _builder.append("</a>");
    return _builder.toString();
  }
}
