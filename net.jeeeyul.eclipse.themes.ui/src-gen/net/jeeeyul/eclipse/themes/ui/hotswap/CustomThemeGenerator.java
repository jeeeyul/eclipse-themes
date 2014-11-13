package net.jeeeyul.eclipse.themes.ui.hotswap;

import com.google.common.base.Objects;
import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer;
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment;
import net.jeeeyul.eclipse.themes.ui.hotswap.ColorAndFontCSSGenerator;
import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.ColorStop;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Generates CSS content with {@link JThemePreferenceStore} as input.
 * @see #generate()
 * 
 * @since 2.1
 * @author Jeeeyul Lee
 */
@SuppressWarnings("all")
public class CustomThemeGenerator {
  @Accessors
  private JThemePreferenceStore store;
  
  @Extension
  private ENVHelper _eNVHelper = ENVHelper.INSTANCE;
  
  /**
   * Creates {@link CustomThemeGenerator} with input.
   * @param store An input to generate css.
   */
  public CustomThemeGenerator(final JThemePreferenceStore store) {
    boolean _equals = Objects.equal(store, null);
    if (_equals) {
      throw new IllegalArgumentException();
    }
    this.store = store;
  }
  
  /**
   * Generate CSS content.
   * 
   * @return generated CSS content.
   */
  public String generate() {
    StringConcatenation _builder = new StringConcatenation();
    String _header = this.header();
    _builder.append(_header, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _body = this.body();
    _builder.append(_body, "");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String header() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(".jeeeyul-custom-theme{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* This class must appear first. */");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  private String body() {
    String _xtrycatchfinallyexpression = null;
    try {
      return this.doGenerateBody();
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/*");
        _builder.newLine();
        Class<? extends Exception> _class = e.getClass();
        String _name = _class.getName();
        _builder.append(_name, "");
        _builder.append(": ");
        String _message = e.getMessage();
        _builder.append(_message, "");
        _builder.newLineIfNotEmpty();
        {
          StackTraceElement[] _stackTrace = e.getStackTrace();
          for(final StackTraceElement each : _stackTrace) {
            _builder.append("\t");
            _builder.append("at ");
            String _className = each.getClassName();
            _builder.append(_className, "\t");
            _builder.append(".");
            String _methodName = each.getMethodName();
            _builder.append(_methodName, "\t");
            _builder.append(" (");
            String _fileName = each.getFileName();
            _builder.append(_fileName, "\t");
            _builder.append(" : ");
            int _lineNumber = each.getLineNumber();
            _builder.append(_lineNumber, "\t");
            _builder.append(")");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("*/");
        _builder.newLine();
        _xtrycatchfinallyexpression = _builder.toString();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  private String doGenerateBody() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _comment = this.comment("Window");
    _builder.append(_comment, "");
    _builder.newLineIfNotEmpty();
    _builder.append("Shell.MTrimmedWindow {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("margin-top: ");
    Rectangle _windowMargins = this.windowMargins();
    _builder.append(_windowMargins.y, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("margin-right: ");
    Rectangle _windowMargins_1 = this.windowMargins();
    _builder.append(_windowMargins_1.width, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("margin-bottom: ");
    Rectangle _windowMargins_2 = this.windowMargins();
    _builder.append(_windowMargins_2.height, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("margin-left: ");
    Rectangle _windowMargins_3 = this.windowMargins();
    _builder.append(_windowMargins_3.x, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    {
      boolean _isLinux = this._eNVHelper.isLinux();
      if (_isLinux) {
        _builder.append("\t");
        _builder.append("background-color: ");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        HSB _hSB = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
        String _hTMLCode = _hSB.toHTMLCode();
        _builder.append(_hTMLCode, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        HSB _hSB_1 = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
        String _hTMLCode_1 = _hSB_1.toHTMLCode();
        _builder.append(_hTMLCode_1, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("100%;");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("background-color: ");
        HSB _hSB_2 = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
        String _hTMLCode_2 = _hSB_2.toHTMLCode();
        _builder.append(_hTMLCode_2, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPartSashContainer {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jsash-width : ");
    int _partSpacing = this.partSpacing();
    _builder.append(_partSpacing, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("background-color: ");
    HSB _hSB_3 = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
    String _hTMLCode_3 = _hSB_3.toHTMLCode();
    _builder.append(_hTMLCode_3, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_1 = this.comment("Main Toolbar");
    _builder.append(_comment_1, "");
    _builder.newLineIfNotEmpty();
    _builder.append("#org-eclipse-ui-main-toolbar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background-color:");
    Gradient _gradient = this.store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR);
    String _sWTCSSString = _gradient.toSWTCSSString();
    _builder.append(_sWTCSSString, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-main-toolbar > .Draggable {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("handle-image:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("url(jeeeyul://drag-handle?height=");
    int _olbarHeight = this.toolbarHeight();
    _builder.append(_olbarHeight, "\t\t");
    _builder.append("&background-color=");
    Gradient _gradient_1 = this.store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR);
    HSB _middlePointColor = _gradient_1.getMiddlePointColor();
    String _hTMLCode_4 = _middlePointColor.toHTMLCode();
    _builder.append(_hTMLCode_4, "\t\t");
    _builder.append("&embossed=false);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-main-toolbar > .TrimStack {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("frame-image: none;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("handle-image:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("url(jeeeyul://drag-handle?height=");
    int _olbarHeight_1 = this.toolbarHeight();
    _builder.append(_olbarHeight_1, "\t\t");
    _builder.append("&background-color=");
    Gradient _gradient_2 = this.store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR);
    HSB _middlePointColor_1 = _gradient_2.getMiddlePointColor();
    String _hTMLCode_5 = _middlePointColor_1.toHTMLCode();
    _builder.append(_hTMLCode_5, "\t\t");
    _builder.append("&embossed=false);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-main-toolbar #PerspectiveSwitcher {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background-color: ");
    Gradient _gradient_3 = this.store.getGradient(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR);
    String _sWTCSSString_1 = _gradient_3.toSWTCSSString();
    _builder.append(_sWTCSSString_1, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("handle-image: none;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eclipse-perspective-keyline-color: ");
    HSB _hSB_4 = this.store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR);
    String _hTMLCode_6 = _hSB_4.toHTMLCode();
    _builder.append(_hTMLCode_6, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-main-toolbar #PerspectiveSwitcher ToolBar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtool-item-color : ");
    HSB _hSB_5 = this.store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_TEXT_COLOR);
    String _hTMLCode_7 = _hSB_5.toHTMLCode();
    _builder.append(_hTMLCode_7, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_2 = this.comment("Status Bar");
    _builder.append(_comment_2, "");
    _builder.newLineIfNotEmpty();
    _builder.append("#org-eclipse-ui-trim-status{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background-color:");
    Gradient _gradient_4 = this.store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR);
    String _sWTCSSString_2 = _gradient_4.toSWTCSSString();
    _builder.append(_sWTCSSString_2, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-trim-status,");
    _builder.newLine();
    _builder.append("#org-eclipse-ui-trim-status *");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("color : ");
    HSB _hSB_6 = this.store.getHSB(JTPConstants.Window.STATUS_BAR_TEXT_COLOR);
    String _hTMLCode_8 = _hSB_6.toHTMLCode();
    _builder.append(_hTMLCode_8, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-trim-status ToolBar{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtool-item-color : ");
    HSB _hSB_7 = this.store.getHSB(JTPConstants.Window.STATUS_BAR_TEXT_COLOR);
    String _hTMLCode_9 = _hSB_7.toHTMLCode();
    _builder.append(_hTMLCode_9, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-trim-status .Draggable {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("handle-image:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("url(jeeeyul://drag-handle?height=");
    int _olbarHeight_2 = this.toolbarHeight();
    _builder.append(_olbarHeight_2, "\t\t");
    _builder.append("&background-color=");
    Gradient _gradient_5 = this.store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR);
    HSB _middlePointColor_2 = _gradient_5.getMiddlePointColor();
    String _hTMLCode_10 = _middlePointColor_2.toHTMLCode();
    _builder.append(_hTMLCode_10, "\t\t");
    _builder.append("&embossed=false);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#org-eclipse-ui-trim-status .TrimStack {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("handle-image:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("url(jeeeyul://drag-handle?height=");
    int _olbarHeight_3 = this.toolbarHeight();
    _builder.append(_olbarHeight_3, "\t\t");
    _builder.append("&background-color=");
    Gradient _gradient_6 = this.store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR);
    HSB _middlePointColor_3 = _gradient_6.getMiddlePointColor();
    String _hTMLCode_11 = _middlePointColor_3.toHTMLCode();
    _builder.append(_hTMLCode_11, "\t\t");
    _builder.append("&embossed=false);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("frame-image: url(jeeeyul://frame?background-color=");
    Gradient _gradient_7 = this.store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR);
    HSB _middlePointColor_4 = _gradient_7.getMiddlePointColor();
    String _hTMLCode_12 = _middlePointColor_4.toHTMLCode();
    _builder.append(_hTMLCode_12, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("frame-cuts: 4px 2px 5px 16px;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_3 = this.comment("Trim Stack");
    _builder.append(_comment_3, "");
    _builder.newLineIfNotEmpty();
    _builder.append("Shell.MTrimmedWindow > .MTrimBar > .TrimStack {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("frame-image: url(jeeeyul://frame?background-color=");
    HSB _hSB_8 = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
    String _hTMLCode_13 = _hSB_8.toHTMLCode();
    _builder.append(_hTMLCode_13, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("frame-cuts: 4px 2px 5px 16px;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("handle-image:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("url(jeeeyul://drag-handle?height=");
    int _olbarHeight_4 = this.toolbarHeight();
    _builder.append(_olbarHeight_4, "\t\t");
    _builder.append("&background-color=");
    HSB _hSB_9 = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
    String _hTMLCode_14 = _hSB_9.toHTMLCode();
    _builder.append(_hTMLCode_14, "\t\t");
    _builder.append("&embossed=false);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("Shell.MTrimmedWindow > .MTrimBar > .MToolBar.Draggable {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("handle-image:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("url(jeeeyul://drag-handle?height=");
    int _olbarHeight_5 = this.toolbarHeight();
    _builder.append(_olbarHeight_5, "\t\t");
    _builder.append("&background-color=");
    HSB _hSB_10 = this.store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
    String _hTMLCode_15 = _hSB_10.toHTMLCode();
    _builder.append(_hTMLCode_15, "\t\t");
    _builder.append("&embossed=false);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_4 = this.comment("Inactive Part Stack");
    _builder.append(_comment_4, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".MPartStack {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("swt-tab-renderer: url(\'");
    String _tabRendererClass = this.tabRendererClass();
    _builder.append(_tabRendererClass, "\t");
    _builder.append("\');");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("swt-mru-visible: ");
    boolean _boolean = this.store.getBoolean(JTPConstants.Others.MRU_VISIBLE);
    _builder.append(_boolean, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* layout */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("swt-tab-height: ");
    int _int = this.store.getInt(JTPConstants.Layout.TAB_HEIGHT);
    _builder.append(_int, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-border-radius: ");
    int _int_1 = this.store.getInt(JTPConstants.Layout.BORDER_RADIUS);
    _builder.append(_int_1, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-spacing: ");
    int _int_2 = this.store.getInt(JTPConstants.Layout.TAB_SPACING);
    _builder.append(_int_2, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-item-padding: 0px ");
    int _int_3 = this.store.getInt(JTPConstants.Layout.TAB_ITEM_PADDING);
    _builder.append(_int_3, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-item-horizontal-spacing: ");
    int _int_4 = this.store.getInt(JTPConstants.Layout.TAB_ITEM_SPACING);
    _builder.append(_int_4, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-padding : ");
    int _int_5 = this.store.getInt(JTPConstants.Layout.CONTENT_PADDING);
    _builder.append(_int_5, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-truncate-tab-items : ");
    boolean _boolean_1 = this.store.getBoolean(JTPConstants.Layout.TRUNCATE_TAB_ITEMS);
    _builder.append(_boolean_1, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-use-ellipses : ");
    boolean _boolean_2 = this.store.getBoolean(JTPConstants.Layout.USE_ELLIPSES);
    _builder.append(_boolean_2, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-minimum-characters : ");
    int _int_6 = this.store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS);
    _builder.append(_int_6, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-alignment : ");
    int _int_7 = this.store.getInt(JTPConstants.Layout.CLOSE_BUTTON_VERTICAL_ALIGNMENT);
    VerticalAlignment _fromValue = VerticalAlignment.fromValue(_int_7);
    String _cSSValue = _fromValue.getCSSValue();
    _builder.append(_cSSValue, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _boolean_3 = this.store.getBoolean(JTPConstants.Layout.SHOW_SHADOW);
      if (_boolean_3) {
        _builder.append("\t");
        _builder.append("jtab-margin : 0px 4px 4px 1px; /* top right bottom left */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-color: ");
        HSB _hSB_11 = this.store.getHSB(JTPConstants.Layout.SHADOW_COLOR);
        String _hTMLCode_16 = _hSB_11.toHTMLCode();
        _builder.append(_hTMLCode_16, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-shadow-position: 1px 1px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-radius: 3px;");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("jtab-margin : 0px 1px 1px 0px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-color: none;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-position: 0px 0px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-radius: 0px;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* tab background */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-header-background : ");
    Gradient _gradient_8 = this.store.getGradient(JTPConstants.InactivePartStack.HEADER_BACKGROUND_COLOR);
    String _sWTCSSString_3 = _gradient_8.toSWTCSSString();
    _builder.append(_sWTCSSString_3, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_4 = this.store.getBoolean(JTPConstants.InactivePartStack.BORDER_SHOW);
      if (_boolean_4) {
        _builder.append("\t");
        _builder.append("jtab-border-color : ");
        Gradient _gradient_9 = this.store.getGradient(JTPConstants.InactivePartStack.BORDER_COLOR);
        String _sWTCSSString_4 = _gradient_9.toSWTCSSString();
        _builder.append(_sWTCSSString_4, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-border-color : none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("background : ");
    HSB _hSB_12 = this.store.getHSB(JTPConstants.InactivePartStack.BODY_BACKGROUND_COLOR);
    String _hTMLCode_17 = _hSB_12.toHTMLCode();
    _builder.append(_hTMLCode_17, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* selected tabs */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-selected-tab-background: ");
    Gradient _gradient_10 = this.store.getGradient(JTPConstants.InactivePartStack.SELECTED_FILL_COLOR);
    String _sWTCSSString_5 = _gradient_10.toSWTCSSString();
    _builder.append(_sWTCSSString_5, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_5 = this.store.getBoolean(JTPConstants.InactivePartStack.SELECTED_BORDER_SHOW);
      if (_boolean_5) {
        _builder.append("\t");
        _builder.append("jtab-selected-border-color: ");
        Gradient _gradient_11 = this.store.getGradient(JTPConstants.InactivePartStack.SELECTED_BORDER_COLOR);
        String _sWTCSSString_6 = _gradient_11.toSWTCSSString();
        _builder.append(_sWTCSSString_6, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-selected-border-color: none;");
        _builder.newLine();
      }
    }
    {
      Point _point = this.store.getPoint(JTPConstants.InactivePartStack.SELECTED_TEXT_SHADOW_POSITION);
      boolean _isEmpty = this.isEmpty(_point);
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("jtab-selected-text-shadow-position: ");
        Point _point_1 = this.store.getPoint(JTPConstants.InactivePartStack.SELECTED_TEXT_SHADOW_POSITION);
        CharSequence _cSS = this.toCSS(_point_1);
        _builder.append(_cSS, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-selected-text-shadow-color: ");
        HSB _hSB_13 = this.store.getHSB(JTPConstants.InactivePartStack.SELECTED_TEXT_SHADOW_COLOR);
        String _hTMLCode_18 = _hSB_13.toHTMLCode();
        _builder.append(_hTMLCode_18, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-selected-text-shadow-color: none;");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* unselected tabs */");
    _builder.newLine();
    {
      boolean _boolean_6 = this.store.getBoolean(JTPConstants.InactivePartStack.UNSELECTED_FILL);
      if (_boolean_6) {
        _builder.append("\t");
        _builder.append("jtab-unselected-tabs-background: ");
        Gradient _gradient_12 = this.store.getGradient(JTPConstants.InactivePartStack.UNSELECTED_FILL_COLOR);
        String _sWTCSSString_7 = _gradient_12.toSWTCSSString();
        _builder.append(_sWTCSSString_7, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-unselected-tabs-background: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _boolean_7 = this.store.getBoolean(JTPConstants.InactivePartStack.UNSELECTED_BORDER_SHOW);
      if (_boolean_7) {
        _builder.append("\t");
        _builder.append("jtab-unselected-border-color: ");
        Gradient _gradient_13 = this.store.getGradient(JTPConstants.InactivePartStack.UNSELECTED_BORDER_COLOR);
        String _sWTCSSString_8 = _gradient_13.toSWTCSSString();
        _builder.append(_sWTCSSString_8, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-unselected-border-color: none;");
        _builder.newLine();
      }
    }
    {
      Point _point_2 = this.store.getPoint(JTPConstants.InactivePartStack.UNSELECTED_TEXT_SHADOW_POSITION);
      boolean _isEmpty_1 = this.isEmpty(_point_2);
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("\t");
        _builder.append("jtab-unselected-text-shadow-position: ");
        Point _point_3 = this.store.getPoint(JTPConstants.InactivePartStack.UNSELECTED_TEXT_SHADOW_POSITION);
        CharSequence _cSS_1 = this.toCSS(_point_3);
        _builder.append(_cSS_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-unselected-text-shadow-color: ");
        HSB _hSB_14 = this.store.getHSB(JTPConstants.InactivePartStack.UNSELECTED_TEXT_SHADOW_COLOR);
        String _hTMLCode_19 = _hSB_14.toHTMLCode();
        _builder.append(_hTMLCode_19, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-unselected-text-shadow-color: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* hover tabs */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-hover-color : ");
    HSB _hSB_15 = this.store.getHSB(JTPConstants.InactivePartStack.HOVER_TEXT_COLOR);
    String _hTMLCode_20 = _hSB_15.toHTMLCode();
    _builder.append(_hTMLCode_20, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_8 = this.store.getBoolean(JTPConstants.InactivePartStack.HOVER_FILL);
      if (_boolean_8) {
        _builder.append("\t");
        _builder.append("jtab-hover-tabs-background: ");
        Gradient _gradient_14 = this.store.getGradient(JTPConstants.InactivePartStack.HOVER_FILL_COLOR);
        String _sWTCSSString_9 = _gradient_14.toSWTCSSString();
        _builder.append(_sWTCSSString_9, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-hover-tabs-background: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _boolean_9 = this.store.getBoolean(JTPConstants.InactivePartStack.HOVER_BORDER_SHOW);
      if (_boolean_9) {
        _builder.append("\t");
        _builder.append("jtab-hover-border-color: ");
        Gradient _gradient_15 = this.store.getGradient(JTPConstants.InactivePartStack.HOVER_BORDER_COLOR);
        String _sWTCSSString_10 = _gradient_15.toSWTCSSString();
        _builder.append(_sWTCSSString_10, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-hover-border-color: none;");
        _builder.newLine();
      }
    }
    {
      Point _point_4 = this.store.getPoint(JTPConstants.InactivePartStack.HOVER_TEXT_SHADOW_POSITION);
      boolean _isEmpty_2 = this.isEmpty(_point_4);
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        _builder.append("\t");
        _builder.append("jtab-hover-text-shadow-position: ");
        Point _point_5 = this.store.getPoint(JTPConstants.InactivePartStack.HOVER_TEXT_SHADOW_POSITION);
        CharSequence _cSS_2 = this.toCSS(_point_5);
        _builder.append(_cSS_2, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-hover-text-shadow-color: ");
        HSB _hSB_16 = this.store.getHSB(JTPConstants.InactivePartStack.HOVER_TEXT_SHADOW_COLOR);
        String _hTMLCode_21 = _hSB_16.toHTMLCode();
        _builder.append(_hTMLCode_21, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-hover-text-shadow-color: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-close-button-color: ");
    HSB _hSB_17 = this.store.getHSB(JTPConstants.InactivePartStack.CLOSE_BUTTON_COLOR);
    String _hTMLCode_22 = _hSB_17.toHTMLCode();
    _builder.append(_hTMLCode_22, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-hot-color: ");
    HSB _hSB_18 = this.store.getHSB(JTPConstants.InactivePartStack.CLOSE_BUTTON_HOVER_COLOR);
    String _hTMLCode_23 = _hSB_18.toHTMLCode();
    _builder.append(_hTMLCode_23, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-active-color: ");
    HSB _hSB_19 = this.store.getHSB(JTPConstants.InactivePartStack.CLOSE_BUTTON_ACTIVE_COLOR);
    String _hTMLCode_24 = _hSB_19.toHTMLCode();
    _builder.append(_hTMLCode_24, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-line-width: ");
    int _int_8 = this.store.getInt(JTPConstants.InactivePartStack.CLOSE_BUTTON_LINE_WIDTH);
    _builder.append(_int_8, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-chevron-color: ");
    HSB _hSB_20 = this.store.getHSB(JTPConstants.InactivePartStack.CHEVRON_COLOR);
    String _hTMLCode_25 = _hSB_20.toHTMLCode();
    _builder.append(_hTMLCode_25, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPartStack>CTabItem {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* unselected tab text */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("color: ");
    HSB _hSB_21 = this.store.getHSB(JTPConstants.InactivePartStack.UNSELECTED_TEXT_COLOR);
    String _hTMLCode_26 = _hSB_21.toHTMLCode();
    _builder.append(_hTMLCode_26, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPartStack>CTabItem:selected {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* selected tab text */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("color: ");
    HSB _hSB_22 = this.store.getHSB(JTPConstants.InactivePartStack.SELECTED_TEXT_COLOR);
    String _hTMLCode_27 = _hSB_22.toHTMLCode();
    _builder.append(_hTMLCode_27, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_5 = this.comment("Active Part Stack");
    _builder.append(_comment_5, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".MPartStack.active {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("swt-tab-renderer: url(\'");
    String _tabRendererClass_1 = this.tabRendererClass();
    _builder.append(_tabRendererClass_1, "\t");
    _builder.append("\');");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* tab background */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-header-background : ");
    Gradient _gradient_16 = this.store.getGradient(JTPConstants.ActivePartStack.HEADER_BACKGROUND_COLOR);
    String _sWTCSSString_11 = _gradient_16.toSWTCSSString();
    _builder.append(_sWTCSSString_11, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_10 = this.store.getBoolean(JTPConstants.ActivePartStack.BORDER_SHOW);
      if (_boolean_10) {
        _builder.append("\t");
        _builder.append("jtab-border-color : ");
        Gradient _gradient_17 = this.store.getGradient(JTPConstants.ActivePartStack.BORDER_COLOR);
        String _sWTCSSString_12 = _gradient_17.toSWTCSSString();
        _builder.append(_sWTCSSString_12, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-border-color : none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("background : ");
    HSB _hSB_23 = this.store.getHSB(JTPConstants.ActivePartStack.BODY_BACKGROUND_COLOR);
    String _hTMLCode_28 = _hSB_23.toHTMLCode();
    _builder.append(_hTMLCode_28, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* selected tabs */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-selected-tab-background: ");
    Gradient _gradient_18 = this.store.getGradient(JTPConstants.ActivePartStack.SELECTED_FILL_COLOR);
    String _sWTCSSString_13 = _gradient_18.toSWTCSSString();
    _builder.append(_sWTCSSString_13, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_11 = this.store.getBoolean(JTPConstants.ActivePartStack.SELECTED_BORDER_SHOW);
      if (_boolean_11) {
        _builder.append("\t");
        _builder.append("jtab-selected-border-color: ");
        Gradient _gradient_19 = this.store.getGradient(JTPConstants.ActivePartStack.SELECTED_BORDER_COLOR);
        String _sWTCSSString_14 = _gradient_19.toSWTCSSString();
        _builder.append(_sWTCSSString_14, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-selected-border-color: none;");
        _builder.newLine();
      }
    }
    {
      Point _point_6 = this.store.getPoint(JTPConstants.ActivePartStack.SELECTED_TEXT_SHADOW_POSITION);
      boolean _isEmpty_3 = this.isEmpty(_point_6);
      boolean _not_3 = (!_isEmpty_3);
      if (_not_3) {
        _builder.append("\t");
        _builder.append("jtab-selected-text-shadow-position: ");
        Point _point_7 = this.store.getPoint(JTPConstants.ActivePartStack.SELECTED_TEXT_SHADOW_POSITION);
        CharSequence _cSS_3 = this.toCSS(_point_7);
        _builder.append(_cSS_3, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-selected-text-shadow-color: ");
        HSB _hSB_24 = this.store.getHSB(JTPConstants.ActivePartStack.SELECTED_TEXT_SHADOW_COLOR);
        String _hTMLCode_29 = _hSB_24.toHTMLCode();
        _builder.append(_hTMLCode_29, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-selected-text-shadow-color: none;");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* unselected tabs */");
    _builder.newLine();
    {
      boolean _boolean_12 = this.store.getBoolean(JTPConstants.ActivePartStack.UNSELECTED_FILL);
      if (_boolean_12) {
        _builder.append("\t");
        _builder.append("jtab-unselected-tabs-background: ");
        Gradient _gradient_20 = this.store.getGradient(JTPConstants.ActivePartStack.UNSELECTED_FILL_COLOR);
        String _sWTCSSString_15 = _gradient_20.toSWTCSSString();
        _builder.append(_sWTCSSString_15, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-unselected-tabs-background: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _boolean_13 = this.store.getBoolean(JTPConstants.ActivePartStack.UNSELECTED_BORDER_SHOW);
      if (_boolean_13) {
        _builder.append("\t");
        _builder.append("jtab-unselected-border-color: ");
        Gradient _gradient_21 = this.store.getGradient(JTPConstants.ActivePartStack.UNSELECTED_BORDER_COLOR);
        String _sWTCSSString_16 = _gradient_21.toSWTCSSString();
        _builder.append(_sWTCSSString_16, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-unselected-border-color: none;");
        _builder.newLine();
      }
    }
    {
      Point _point_8 = this.store.getPoint(JTPConstants.ActivePartStack.UNSELECTED_TEXT_SHADOW_POSITION);
      boolean _isEmpty_4 = this.isEmpty(_point_8);
      boolean _not_4 = (!_isEmpty_4);
      if (_not_4) {
        _builder.append("\t");
        _builder.append("jtab-unselected-text-shadow-position: ");
        Point _point_9 = this.store.getPoint(JTPConstants.ActivePartStack.UNSELECTED_TEXT_SHADOW_POSITION);
        CharSequence _cSS_4 = this.toCSS(_point_9);
        _builder.append(_cSS_4, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-unselected-text-shadow-color: ");
        HSB _hSB_25 = this.store.getHSB(JTPConstants.ActivePartStack.UNSELECTED_TEXT_SHADOW_COLOR);
        String _hTMLCode_30 = _hSB_25.toHTMLCode();
        _builder.append(_hTMLCode_30, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-unselected-text-shadow-color: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* hover tabs */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-hover-color : ");
    HSB _hSB_26 = this.store.getHSB(JTPConstants.ActivePartStack.HOVER_TEXT_COLOR);
    String _hTMLCode_31 = _hSB_26.toHTMLCode();
    _builder.append(_hTMLCode_31, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_14 = this.store.getBoolean(JTPConstants.ActivePartStack.HOVER_FILL);
      if (_boolean_14) {
        _builder.append("\t");
        _builder.append("jtab-hover-tabs-background: ");
        Gradient _gradient_22 = this.store.getGradient(JTPConstants.ActivePartStack.HOVER_FILL_COLOR);
        String _sWTCSSString_17 = _gradient_22.toSWTCSSString();
        _builder.append(_sWTCSSString_17, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-hover-tabs-background: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _boolean_15 = this.store.getBoolean(JTPConstants.ActivePartStack.HOVER_BORDER_SHOW);
      if (_boolean_15) {
        _builder.append("\t");
        _builder.append("jtab-hover-border-color: ");
        Gradient _gradient_23 = this.store.getGradient(JTPConstants.ActivePartStack.HOVER_BORDER_COLOR);
        String _sWTCSSString_18 = _gradient_23.toSWTCSSString();
        _builder.append(_sWTCSSString_18, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-hover-border-color: none;");
        _builder.newLine();
      }
    }
    {
      Point _point_10 = this.store.getPoint(JTPConstants.ActivePartStack.HOVER_TEXT_SHADOW_POSITION);
      boolean _isEmpty_5 = this.isEmpty(_point_10);
      boolean _not_5 = (!_isEmpty_5);
      if (_not_5) {
        _builder.append("\t");
        _builder.append("jtab-hover-text-shadow-position: ");
        Point _point_11 = this.store.getPoint(JTPConstants.ActivePartStack.HOVER_TEXT_SHADOW_POSITION);
        CharSequence _cSS_5 = this.toCSS(_point_11);
        _builder.append(_cSS_5, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-hover-text-shadow-color: ");
        HSB _hSB_27 = this.store.getHSB(JTPConstants.ActivePartStack.HOVER_TEXT_SHADOW_COLOR);
        String _hTMLCode_32 = _hSB_27.toHTMLCode();
        _builder.append(_hTMLCode_32, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-hover-text-shadow-color: none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-close-button-color: ");
    HSB _hSB_28 = this.store.getHSB(JTPConstants.ActivePartStack.CLOSE_BUTTON_COLOR);
    String _hTMLCode_33 = _hSB_28.toHTMLCode();
    _builder.append(_hTMLCode_33, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-hot-color: ");
    HSB _hSB_29 = this.store.getHSB(JTPConstants.ActivePartStack.CLOSE_BUTTON_HOVER_COLOR);
    String _hTMLCode_34 = _hSB_29.toHTMLCode();
    _builder.append(_hTMLCode_34, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-active-color: ");
    HSB _hSB_30 = this.store.getHSB(JTPConstants.ActivePartStack.CLOSE_BUTTON_ACTIVE_COLOR);
    String _hTMLCode_35 = _hSB_30.toHTMLCode();
    _builder.append(_hTMLCode_35, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-close-button-line-width: ");
    int _int_9 = this.store.getInt(JTPConstants.ActivePartStack.CLOSE_BUTTON_LINE_WIDTH);
    _builder.append(_int_9, "\t");
    _builder.append("px;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-chevron-color: ");
    HSB _hSB_31 = this.store.getHSB(JTPConstants.ActivePartStack.CHEVRON_COLOR);
    String _hTMLCode_36 = _hSB_31.toHTMLCode();
    _builder.append(_hTMLCode_36, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPartStack.active>CTabItem {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* unselected tab text */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("color: ");
    HSB _hSB_32 = this.store.getHSB(JTPConstants.ActivePartStack.UNSELECTED_TEXT_COLOR);
    String _hTMLCode_37 = _hSB_32.toHTMLCode();
    _builder.append(_hTMLCode_37, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPartStack.active>CTabItem:selected {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* selected tab text */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("color: ");
    HSB _hSB_33 = this.store.getHSB(JTPConstants.ActivePartStack.SELECTED_TEXT_COLOR);
    String _hTMLCode_38 = _hSB_33.toHTMLCode();
    _builder.append(_hTMLCode_38, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_6 = this.comment("Busy Part");
    _builder.append(_comment_6, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".MPart{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("font-style: normal;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPart.busy {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("font-style: italic;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_7 = this.comment("Editor Part Stack");
    _builder.append(_comment_7, "");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_16 = this.store.getBoolean(JTPConstants.Layout.TRUNCATE_TAB_ITEMS);
      if (_boolean_16) {
        _builder.append(".MPartStack.EditorStack{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-truncate-tab-items : ");
        boolean _boolean_17 = this.store.getBoolean(JTPConstants.Layout.TRUNCATE_EDITORS_TAB_ITEMS_ALSO);
        _builder.append(_boolean_17, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-minimum-characters : ");
        int _int_10 = this.store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS_FOR_EDITORS);
        _builder.append(_int_10, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_8 = this.comment("Empty Editor Stack");
    _builder.append(_comment_8, "");
    _builder.newLineIfNotEmpty();
    _builder.append("#org-eclipse-ui-editorss.MArea .MPartStack.empty{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-header-background : ");
    Gradient _gradient_24 = this.store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR);
    String _sWTCSSString_19 = _gradient_24.toSWTCSSString();
    _builder.append(_sWTCSSString_19, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_18 = this.store.getBoolean(JTPConstants.EmptyPartStack.BORDER_SHOW);
      if (_boolean_18) {
        _builder.append("\t");
        _builder.append("jtab-border-color : ");
        Gradient _gradient_25 = this.store.getGradient(JTPConstants.EmptyPartStack.BORDER_COLOR);
        String _sWTCSSString_20 = _gradient_25.toSWTCSSString();
        _builder.append(_sWTCSSString_20, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-border-color : none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("background : ");
    Gradient _gradient_26 = this.store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR);
    ColorStop _last = IterableExtensions.<ColorStop>last(_gradient_26);
    String _hTMLCode_39 = _last.color.toHTMLCode();
    _builder.append(_hTMLCode_39, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_9 = this.comment("Editors Area");
    _builder.append(_comment_9, "");
    _builder.newLineIfNotEmpty();
    _builder.append("CTabFolder#org-eclipse-ui-editorss.MArea{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("swt-tab-renderer: url(\'");
    String _tabRendererClass_2 = this.tabRendererClass();
    _builder.append(_tabRendererClass_2, "\t");
    _builder.append("\');");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("swt-tab-height: 10px;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtab-header-background : ");
    Gradient _gradient_27 = this.store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR);
    String _sWTCSSString_21 = _gradient_27.toSWTCSSString();
    _builder.append(_sWTCSSString_21, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _boolean_19 = this.store.getBoolean(JTPConstants.EditorsPartStack.BORDER_SHOW);
      if (_boolean_19) {
        _builder.append("\t");
        _builder.append("jtab-border-color : ");
        Gradient _gradient_28 = this.store.getGradient(JTPConstants.EditorsPartStack.BORDER_COLOR);
        String _sWTCSSString_22 = _gradient_28.toSWTCSSString();
        _builder.append(_sWTCSSString_22, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-border-color : none;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("jtab-selected-tab-background: ");
    Gradient _gradient_29 = this.store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR);
    String _sWTCSSString_23 = _gradient_29.toSWTCSSString();
    _builder.append(_sWTCSSString_23, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtab-selected-border-color: none;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("swt-single : true;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _boolean_20 = this.store.getBoolean(JTPConstants.Layout.SHOW_SHADOW);
      if (_boolean_20) {
        _builder.append("\t");
        _builder.append("jtab-margin : 0px 4px 4px 1px; /* top right bottom left */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-color: ");
        HSB _hSB_34 = this.store.getHSB(JTPConstants.Layout.SHADOW_COLOR);
        String _hTMLCode_40 = _hSB_34.toHTMLCode();
        _builder.append(_hTMLCode_40, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("jtab-shadow-position: 1px 1px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-radius: 3px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-padding : ");
        int _int_11 = this.store.getInt(JTPConstants.Layout.CONTENT_PADDING);
        _builder.append(_int_11, "\t");
        _builder.append("px ");
        int _int_12 = this.store.getInt(JTPConstants.Layout.CONTENT_PADDING);
        _builder.append(_int_12, "\t");
        _builder.append("px ");
        int _int_13 = this.store.getInt(JTPConstants.Layout.CONTENT_PADDING);
        int _plus = (_int_13 + 2);
        _builder.append(_plus, "\t");
        _builder.append("px ");
        int _int_14 = this.store.getInt(JTPConstants.Layout.CONTENT_PADDING);
        int _plus_1 = (_int_14 + 2);
        _builder.append(_plus_1, "\t");
        _builder.append("px;");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("jtab-margin : 0px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-color: none;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-position: 0px 0px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-shadow-radius: 0px;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("jtab-padding : ");
        int _int_15 = this.store.getInt(JTPConstants.Layout.CONTENT_PADDING);
        _builder.append(_int_15, "\t");
        _builder.append("px;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("CTabFolder#org-eclipse-ui-editorss.MArea Composite.MPartSashContainer {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background-color: ");
    Gradient _gradient_30 = this.store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR);
    ColorStop _last_1 = IterableExtensions.<ColorStop>last(_gradient_30);
    String _hTMLCode_41 = _last_1.color.toHTMLCode();
    _builder.append(_hTMLCode_41, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_10 = this.comment("Text Editor");
    _builder.append(_comment_10, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".MPart.Editor Canvas {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background-color: ");
    HSB _hSB_35 = this.store.getHSB(JTPConstants.TextEditor.RULER_COLOR);
    String _hTMLCode_42 = _hSB_35.toHTMLCode();
    _builder.append(_hTMLCode_42, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPart.Editor StyledText,");
    _builder.newLine();
    _builder.append(".MPart.Editor StyledTextWithoutVerticalBar /* PyDev support */{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jeditor-line-style : ");
    String _lineStyle = this.getLineStyle(this.store, JTPConstants.TextEditor.UNDER_LINE_STYLE);
    _builder.append(_lineStyle, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jeditor-line-color : ");
    HSB _hSB_36 = this.store.getHSB(JTPConstants.TextEditor.UNDER_LINE_COLOR);
    String _hTMLCode_43 = _hSB_36.toHTMLCode();
    _builder.append(_hTMLCode_43, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jeditor-range-indicator-color: ");
    HSB _rangeIndicatorColor = this.getRangeIndicatorColor();
    String _hTMLCode_44 = _rangeIndicatorColor.toHTMLCode();
    _builder.append(_hTMLCode_44, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_11 = this.comment("Customized Text Editor Support");
    _builder.append(_comment_11, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".MPart Section StyledText");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background : jtexteditor-background;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("color : jtexteditor-foreground;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_12 = this.comment("Forms");
    _builder.append(_comment_12, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".MPart FormHeading {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtext-background: ");
    Gradient _gradient_31 = this.store.getGradient(JTPConstants.Forms.FORM_HEADING_BACKGROUND);
    String _sWTCSSString_24 = _gradient_31.toSWTCSSString();
    _builder.append(_sWTCSSString_24, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("color : ");
    HSB _hSB_37 = this.store.getHSB(JTPConstants.Forms.FORM_HEADING_TITLE_COLOR);
    String _hTMLCode_45 = _hSB_37.toHTMLCode();
    _builder.append(_hTMLCode_45, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jbottom-keyline-1-color: ");
    HSB _hSB_38 = this.store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_1_COLOR);
    String _hTMLCode_46 = _hSB_38.toHTMLCode();
    _builder.append(_hTMLCode_46, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jbottom-keyline-2-color: ");
    HSB _hSB_39 = this.store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_2_COLOR);
    String _hTMLCode_47 = _hSB_39.toHTMLCode();
    _builder.append(_hTMLCode_47, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPart Section {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("jtitle-bar-background-color:  ");
    HSB _hSB_40 = this.store.getHSB(JTPConstants.Forms.SECTION_HEADER_TINT_COLOR);
    String _hTMLCode_48 = _hSB_40.toHTMLCode();
    _builder.append(_hTMLCode_48, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtitle-bar-border-color:  ");
    HSB _hSB_41 = this.store.getHSB(JTPConstants.Forms.SECTION_HEADER_BORDER_COLOR);
    String _hTMLCode_49 = _hSB_41.toHTMLCode();
    _builder.append(_hTMLCode_49, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtitle-bar-text-color :  ");
    HSB _hSB_42 = this.store.getHSB(JTPConstants.Forms.SECTION_HEADER_TITLE_COLOR);
    String _hTMLCode_50 = _hSB_42.toHTMLCode();
    _builder.append(_hTMLCode_50, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jtitle-bar-active-text-color :  ");
    HSB _hSB_43 = this.store.getHSB(JTPConstants.Forms.SECTION_HEADER_ACTIVE_TITLE_COLOR);
    String _hTMLCode_51 = _hSB_43.toHTMLCode();
    _builder.append(_hTMLCode_51, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append(".MPart FormText {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("hyperlink-color: ");
    HSB _hSB_44 = this.store.getHSB(JTPConstants.Forms.HYPER_LINK_COLOR);
    String _hTMLCode_52 = _hSB_44.toHTMLCode();
    _builder.append(_hTMLCode_52, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("active-hyperlink-color: ");
    HSB _hSB_45 = this.store.getHSB(JTPConstants.Forms.ACTIVE_HYPER_LINK_COLOR);
    String _hTMLCode_53 = _hSB_45.toHTMLCode();
    _builder.append(_hTMLCode_53, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_13 = this.comment("Others");
    _builder.append(_comment_13, "");
    _builder.newLineIfNotEmpty();
    _builder.append(".DragFeedback {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("background-color: ");
    HSB _hSB_46 = this.store.getHSB(JTPConstants.Others.DRAG_FEEDBACK_COLOR);
    String _hTMLCode_54 = _hSB_46.toHTMLCode();
    _builder.append(_hTMLCode_54, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("jswt-alpha: ");
    int _int_16 = this.store.getInt(JTPConstants.Others.DRAG_FEEDBACK_ALPHA);
    _builder.append(_int_16, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _comment_14 = this.comment("User Custom CSS");
    _builder.append(_comment_14, "");
    _builder.newLineIfNotEmpty();
    String _string = this.store.getString(JTPConstants.Others.USER_CSS);
    _builder.append(_string, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isLunaOrAbove = this._eNVHelper.isLunaOrAbove();
      if (_isLunaOrAbove) {
        CharSequence _comment_15 = this.comment("User Color and Font Changes");
        _builder.append(_comment_15, "");
        _builder.newLineIfNotEmpty();
        String _generateUserColorAndFontStylings = this.generateUserColorAndFontStylings();
        _builder.append(_generateUserColorAndFontStylings, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  private CharSequence comment(final String comment) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* ");
    _builder.append(comment, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("**************************************************/");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence toCSS(final Point point) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(point.x, "");
    _builder.append("px ");
    _builder.append(point.y, "");
    _builder.append("px");
    return _builder;
  }
  
  private boolean isEmpty(final Point point) {
    return ((point.x == 0) && (point.y == 0));
  }
  
  private String getLineStyle(final JThemePreferenceStore store, final String key) {
    String _switchResult = null;
    int _int = store.getInt(key);
    switch (_int) {
      case SWT.LINE_SOLID:
        _switchResult = "solid";
        break;
      case SWT.LINE_DASH:
        _switchResult = "dashed";
        break;
      case SWT.LINE_DOT:
        _switchResult = "dotted";
        break;
      default:
        _switchResult = "none";
        break;
    }
    return _switchResult;
  }
  
  private int toolbarHeight() {
    return SWTExtensions.INSTANCE.getMinimumToolBarHeight();
  }
  
  private Rectangle windowMargins() {
    Rectangle margins = this.store.getRectangle(JTPConstants.Window.MARGINS);
    boolean _boolean = this.store.getBoolean(JTPConstants.Layout.SHOW_SHADOW);
    if (_boolean) {
      int _max = Math.max((margins.x - 1), 0);
      margins.x = _max;
      int _max_1 = Math.max((margins.width - 4), 0);
      margins.width = _max_1;
      int _max_2 = Math.max((margins.height - 4), 0);
      margins.height = _max_2;
    } else {
      int _max_3 = Math.max((margins.width - 1), 0);
      margins.width = _max_3;
      int _max_4 = Math.max((margins.height - 1), 0);
      margins.height = _max_4;
    }
    return margins;
  }
  
  private String tabRendererClass() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("bundleclass://");
    _builder.append(JThemesCore.PLUGIN_ID, "");
    _builder.append("/");
    String _canonicalName = JeeeyulsTabRenderer.class.getCanonicalName();
    _builder.append(_canonicalName, "");
    return _builder.toString();
  }
  
  private int partSpacing() {
    int offset = this.store.getInt(JTPConstants.Window.SASH_WIDTH);
    boolean _boolean = this.store.getBoolean(JTPConstants.Layout.SHOW_SHADOW);
    if (_boolean) {
      offset = (offset - 4);
    }
    return Math.max(offset, 0);
  }
  
  private HSB getRangeIndicatorColor() {
    Display _default = Display.getDefault();
    Color _systemColor = _default.getSystemColor(SWT.COLOR_LIST_SELECTION);
    RGB _rGB = _systemColor.getRGB();
    HSB selectionColor = new HSB(_rGB);
    HSB _hSB = this.store.getHSB(JTPConstants.TextEditor.RULER_COLOR);
    return _hSB.getMixedWith(selectionColor, 0.5f);
  }
  
  private String generateUserColorAndFontStylings() {
    StringBuilder sb = new StringBuilder();
    ColorAndFontCSSGenerator colorAndFontGenerator = new ColorAndFontCSSGenerator();
    colorAndFontGenerator.run(sb);
    return sb.toString();
  }
  
  @Pure
  public JThemePreferenceStore getStore() {
    return this.store;
  }
  
  public void setStore(final JThemePreferenceStore store) {
    this.store = store;
  }
}
