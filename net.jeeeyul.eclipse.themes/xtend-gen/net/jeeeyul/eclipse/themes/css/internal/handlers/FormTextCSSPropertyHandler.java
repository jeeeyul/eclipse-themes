package net.jeeeyul.eclipse.themes.css.internal.handlers;

import com.google.common.base.Objects;
import net.jeeeyul.eclipse.themes.css.EditorLineSupport;
import net.jeeeyul.eclipse.themes.css.internal.elements.FormTextElement;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.widgets.FormText;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
@SuppressWarnings("all")
public class FormTextCSSPropertyHandler implements ICSSPropertyHandler {
  public boolean applyCSSProperty(final Object element, final String property, final CSSValue value, final String pseudo, final CSSEngine engine) throws Exception {
    boolean _xblockexpression = false;
    {
      FormTextElement ftElement = ((FormTextElement) element);
      Object _nativeWidget = ftElement.getNativeWidget();
      FormText formText = ((FormText) _nativeWidget);
      if ((!(formText instanceof FormText))) {
        return false;
      }
      boolean _switchResult = false;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(property, "hyperlink-color")) {
          _matched=true;
          boolean _xifexpression = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_1 = false;
            {
              Display _display = formText.getDisplay();
              Object _convert = engine.convert(value, Color.class, _display);
              Color newColor = ((Color) _convert);
              HyperlinkSettings _hyperlinkSettings = formText.getHyperlinkSettings();
              _hyperlinkSettings.setForeground(newColor);
              _xblockexpression_1 = true;
            }
            _xifexpression = _xblockexpression_1;
          } else {
            _xifexpression = false;
          }
          _switchResult = _xifexpression;
        }
      }
      if (!_matched) {
        if (Objects.equal(property, "active-hyperlink-color")) {
          _matched=true;
          boolean _xifexpression_1 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_2 = false;
            {
              Display _display = formText.getDisplay();
              Object _convert = engine.convert(value, Color.class, _display);
              Color newColor = ((Color) _convert);
              HyperlinkSettings _hyperlinkSettings = formText.getHyperlinkSettings();
              _hyperlinkSettings.setActiveForeground(newColor);
              _xblockexpression_2 = true;
            }
            _xifexpression_1 = _xblockexpression_2;
          } else {
            _xifexpression_1 = false;
          }
          _switchResult = _xifexpression_1;
        }
      }
      if (!_matched) {
        _switchResult = false;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public String retrieveCSSProperty(final Object element, final String property, final String pseudo, final CSSEngine engine) throws Exception {
    return null;
  }
}
