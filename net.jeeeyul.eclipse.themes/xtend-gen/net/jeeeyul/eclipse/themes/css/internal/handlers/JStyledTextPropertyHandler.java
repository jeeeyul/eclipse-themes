package net.jeeeyul.eclipse.themes.css.internal.handlers;

import com.google.common.base.Objects;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.jeeeyul.eclipse.themes.css.EditorLineSupport;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
@SuppressWarnings("all")
public class JStyledTextPropertyHandler implements ICSSPropertyHandler {
  public boolean applyCSSProperty(final Object element, final String property, final CSSValue value, final String pseudo, final CSSEngine engine) throws Exception {
    boolean _xblockexpression = false;
    {
      CompositeElement compositeElement = ((CompositeElement) element);
      Object _nativeWidget = compositeElement.getNativeWidget();
      Composite composite = ((Composite) _nativeWidget);
      if ((!(composite instanceof StyledText))) {
        return false;
      }
      StyledText styledText = ((StyledText) composite);
      EditorLineSupport els = EditorLineSupport.get(styledText);
      boolean _switchResult = false;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(property, "jeditor-line-style")) {
          _matched=true;
          boolean _xifexpression = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_1 = false;
            {
              int _switchResult_1 = (int) 0;
              String _cssText = ((CSSPrimitiveValue)value).getCssText();
              boolean _matched_1 = false;
              if (!_matched_1) {
                if (Objects.equal(_cssText, "solid")) {
                  _matched_1=true;
                  _switchResult_1 = SWT.LINE_SOLID;
                }
              }
              if (!_matched_1) {
                if (Objects.equal(_cssText, "dashed")) {
                  _matched_1=true;
                  _switchResult_1 = SWT.LINE_DASH;
                }
              }
              if (!_matched_1) {
                if (Objects.equal(_cssText, "dotted")) {
                  _matched_1=true;
                  _switchResult_1 = SWT.LINE_DOT;
                }
              }
              if (!_matched_1) {
                _switchResult_1 = SWT.NONE;
              }
              els.setLineStyle(_switchResult_1);
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
        if (Objects.equal(property, "jeditor-line-color")) {
          _matched=true;
          boolean _xblockexpression_2 = false;
          {
            RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
            boolean _xifexpression_1 = false;
            boolean _notEquals = (!Objects.equal(rgb, null));
            if (_notEquals) {
              boolean _xblockexpression_3 = false;
              {
                HSB _hSB = new HSB(rgb.red, rgb.green, rgb.blue);
                els.setLineColor(_hSB);
                _xblockexpression_3 = true;
              }
              _xifexpression_1 = _xblockexpression_3;
            } else {
              _xifexpression_1 = false;
            }
            _xblockexpression_2 = _xifexpression_1;
          }
          _switchResult = _xblockexpression_2;
        }
      }
      if (!_matched) {
        if (Objects.equal(property, "jeditor-range-indicator-color")) {
          _matched=true;
          boolean _xblockexpression_3 = false;
          {
            RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
            boolean _xifexpression_1 = false;
            boolean _notEquals = (!Objects.equal(rgb, null));
            if (_notEquals) {
              boolean _xblockexpression_4 = false;
              {
                Field field = DefaultRangeIndicator.class.getDeclaredField("fgPaletteData");
                field.setAccessible(true);
                ArrayList<RGB> _newArrayList = CollectionLiterals.<RGB>newArrayList(rgb, rgb);
                PaletteData paletteData = new PaletteData(((RGB[])Conversions.unwrapArray(_newArrayList, RGB.class)));
                field.set(DefaultRangeIndicator.class, paletteData);
                _xblockexpression_4 = true;
              }
              _xifexpression_1 = _xblockexpression_4;
            } else {
              _xifexpression_1 = false;
            }
            _xblockexpression_3 = _xifexpression_1;
          }
          _switchResult = _xblockexpression_3;
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
    String _xblockexpression = null;
    {
      CompositeElement compositeElement = ((CompositeElement) element);
      Object _nativeWidget = compositeElement.getNativeWidget();
      Composite composite = ((Composite) _nativeWidget);
      if ((!(composite instanceof StyledText))) {
        return null;
      }
      StyledText styledText = ((StyledText) composite);
      EditorLineSupport els = EditorLineSupport.get(styledText);
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(property, "jeditor-line-style")) {
          _matched=true;
          String _switchResult_1 = null;
          int _lineStyle = els.getLineStyle();
          switch (_lineStyle) {
            case SWT.LINE_SOLID:
              _switchResult_1 = "solid";
              break;
            case SWT.LINE_DASH:
              _switchResult_1 = "dashed";
              break;
            case SWT.LINE_DOT:
              _switchResult_1 = "dotted";
              break;
            default:
              _switchResult_1 = "none";
              break;
          }
          _switchResult = _switchResult_1;
        }
      }
      if (!_matched) {
        if (Objects.equal(property, "jeditor-line-color")) {
          _matched=true;
          HSB _lineColor = els.getLineColor();
          _switchResult = _lineColor.toHTMLCode();
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
