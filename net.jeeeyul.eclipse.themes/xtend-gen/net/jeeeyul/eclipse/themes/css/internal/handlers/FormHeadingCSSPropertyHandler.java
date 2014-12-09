package net.jeeeyul.eclipse.themes.css.internal.handlers;

import com.google.common.base.Objects;
import net.jeeeyul.eclipse.themes.css.EditorLineSupport;
import net.jeeeyul.eclipse.themes.css.internal.CSSCompabilityHelper;
import net.jeeeyul.eclipse.themes.css.internal.elements.FormHeadingElement;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.e4.ui.css.core.dom.properties.Gradient;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.internal.forms.widgets.FormHeading;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
@SuppressWarnings("all")
public class FormHeadingCSSPropertyHandler implements ICSSPropertyHandler {
  public boolean applyCSSProperty(final Object element, final String property, final CSSValue value, final String pseudo, final CSSEngine engine) throws Exception {
    FormHeadingElement fhElement = ((FormHeadingElement) element);
    Object _nativeWidget = fhElement.getNativeWidget();
    FormHeading formHeading = ((FormHeading) _nativeWidget);
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(property, "jtext-background")) {
        _matched=true;
        boolean _xifexpression = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            Display _display = formHeading.getDisplay();
            Color[] colors = CSSCompabilityHelper.getSWTColors(grad, _display, engine);
            int[] percents = CSSCompabilityHelper.getPercents(grad);
            formHeading.setTextBackground(colors, percents, true);
            _xblockexpression = true;
          }
          _xifexpression = _xblockexpression;
        } else {
          boolean _xifexpression_1 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_1 = false;
            {
              Display _display = formHeading.getDisplay();
              Object _convert = engine.convert(value, Color.class, _display);
              Color newColor = ((Color) _convert);
              formHeading.setTextBackground(new Color[] { newColor, newColor }, new int[] { 100 }, true);
              _xblockexpression_1 = true;
            }
            _xifexpression_1 = _xblockexpression_1;
          } else {
            _xifexpression_1 = false;
          }
          _xifexpression = _xifexpression_1;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jbottom-keyline-1-color")) {
        _matched=true;
        boolean _xifexpression_2 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_2 = false;
          {
            Display _display = formHeading.getDisplay();
            Object _convert = engine.convert(value, Color.class, _display);
            Color newColor = ((Color) _convert);
            formHeading.putColor(IFormColors.H_BOTTOM_KEYLINE1, newColor);
            _xblockexpression_2 = true;
          }
          _xifexpression_2 = _xblockexpression_2;
        } else {
          _xifexpression_2 = false;
        }
        _switchResult = _xifexpression_2;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jbottom-keyline-2-color")) {
        _matched=true;
        boolean _xifexpression_3 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_3 = false;
          {
            Display _display = formHeading.getDisplay();
            Object _convert = engine.convert(value, Color.class, _display);
            Color newColor = ((Color) _convert);
            formHeading.putColor(IFormColors.H_BOTTOM_KEYLINE2, newColor);
            _xblockexpression_3 = true;
          }
          _xifexpression_3 = _xblockexpression_3;
        } else {
          _xifexpression_3 = false;
        }
        _switchResult = _xifexpression_3;
      }
    }
    if (!_matched) {
      _switchResult = false;
    }
    return _switchResult;
  }
  
  public String retrieveCSSProperty(final Object element, final String property, final String pseudo, final CSSEngine engine) throws Exception {
    String _xblockexpression = null;
    {
      FormHeadingElement fhElement = ((FormHeadingElement) element);
      Object _nativeWidget = fhElement.getNativeWidget();
      FormHeading formHeading = ((FormHeading) _nativeWidget);
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(property, "jbottom-keyline-1-color")) {
          _matched=true;
          Color _color = formHeading.getColor(IFormColors.H_BOTTOM_KEYLINE1);
          RGB _rGB = _color.getRGB();
          HSB _hSB = new HSB(_rGB);
          _switchResult = _hSB.toHTMLCode();
        }
      }
      if (!_matched) {
        if (Objects.equal(property, "jbottom-keyline-2-color")) {
          _matched=true;
          Color _color_1 = formHeading.getColor(IFormColors.H_BOTTOM_KEYLINE2);
          RGB _rGB_1 = _color_1.getRGB();
          HSB _hSB_1 = new HSB(_rGB_1);
          _switchResult = _hSB_1.toHTMLCode();
        }
      }
      if (!_matched) {
        _switchResult = null;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
}
