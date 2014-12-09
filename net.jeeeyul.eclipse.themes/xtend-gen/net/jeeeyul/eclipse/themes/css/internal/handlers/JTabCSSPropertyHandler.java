package net.jeeeyul.eclipse.themes.css.internal.handlers;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.jeeeyul.eclipse.themes.css.internal.CSSCompabilityHelper;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer;
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment;
import net.jeeeyul.eclipse.themes.rendering.internal.JTabRendererHelper;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.e4.ui.css.core.dom.properties.Gradient;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.impl.dom.Measure;
import org.eclipse.e4.ui.css.swt.dom.CTabFolderElement;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

/**
 * A CSS property handlers for {@link CTabFolder} that uses {@link JeeeyulsTabRenderer} as renderer.
 * 
 * @see CTabFolderRenderer#setRenderer(CTabFolderRenderer)
 */
@SuppressWarnings("all")
public class JTabCSSPropertyHandler implements ICSSPropertyHandler {
  public boolean applyCSSProperty(final Object element, final String property, final CSSValue value, final String pseudo, @Extension final CSSEngine engine) throws Exception {
    CTabFolderElement tabFolderElement = ((CTabFolderElement) element);
    Object _nativeWidget = tabFolderElement.getNativeWidget();
    CTabFolder tabFolder = ((CTabFolder) _nativeWidget);
    CTabFolderRenderer _renderer = tabFolder.getRenderer();
    if ((!(_renderer instanceof JeeeyulsTabRenderer))) {
      return false;
    }
    CTabFolderRenderer _renderer_1 = tabFolder.getRenderer();
    JeeeyulsTabRenderer renderer = ((JeeeyulsTabRenderer) _renderer_1);
    JTabSettings settings = renderer.getSettings();
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-tab-background")) {
        _matched=true;
        boolean _xifexpression = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            Display _display = tabFolder.getDisplay();
            Color[] colors = CSSCompabilityHelper.getSWTColors(grad, _display, engine);
            int[] percents = CSSCompabilityHelper.getPercents(grad);
            tabFolder.setSelectionBackground(colors, percents, true);
            _xblockexpression = true;
          }
          _xifexpression = _xblockexpression;
        } else {
          boolean _xifexpression_1 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_1 = false;
            {
              Display _display = tabFolder.getDisplay();
              Object _convert = engine.convert(value, Color.class, _display);
              Color newColor = ((Color) _convert);
              tabFolder.setSelectionBackground(new Color[] { newColor, newColor }, new int[] { 100 }, true);
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
      if (Objects.equal(property, "jtab-header-background")) {
        _matched=true;
        boolean _xifexpression_2 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_2 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            Display _display = tabFolder.getDisplay();
            Color[] colors = CSSCompabilityHelper.getSWTColors(grad, _display, engine);
            int[] percents = CSSCompabilityHelper.getPercents(grad);
            tabFolder.setBackground(colors, percents, true);
            _xblockexpression_2 = true;
          }
          _xifexpression_2 = _xblockexpression_2;
        } else {
          boolean _xifexpression_3 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_3 = false;
            {
              Display _display = tabFolder.getDisplay();
              Object _convert = engine.convert(value, Color.class, _display);
              Color newColor = ((Color) _convert);
              tabFolder.setBackground(new Color[] { newColor, newColor }, new int[] { 100 }, true);
              _xblockexpression_3 = true;
            }
            _xifexpression_3 = _xblockexpression_3;
          } else {
            _xifexpression_3 = false;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _switchResult = _xifexpression_2;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-border-color")) {
        _matched=true;
        boolean _xifexpression_4 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_4 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            HSB[] _hSBArray = this.toHSBArray(grad);
            settings.setBorderColors(_hSBArray);
            int[] _percents = CSSSWTColorHelper.getPercents(grad);
            settings.setBorderPercents(_percents);
            _xblockexpression_4 = true;
          }
          _xifexpression_4 = _xblockexpression_4;
        } else {
          boolean _xifexpression_5 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_5 = false;
            {
              RGB rgb = CSSSWTColorHelper.getRGB(((CSSPrimitiveValue)value));
              boolean _xifexpression_6 = false;
              boolean _notEquals = (!Objects.equal(rgb, null));
              if (_notEquals) {
                boolean _xblockexpression_6 = false;
                {
                  HSB hsb = new HSB(rgb);
                  settings.setBorderColors(new HSB[] { hsb, hsb });
                  settings.setBorderPercents(new int[] { 100 });
                  _xblockexpression_6 = true;
                }
                _xifexpression_6 = _xblockexpression_6;
              } else {
                boolean _xifexpression_7 = false;
                String _cssText = ((CSSPrimitiveValue)value).getCssText();
                boolean _equals = Objects.equal(_cssText, "none");
                if (_equals) {
                  boolean _xblockexpression_7 = false;
                  {
                    settings.setBorderColors(null);
                    _xblockexpression_7 = true;
                  }
                  _xifexpression_7 = _xblockexpression_7;
                } else {
                  _xifexpression_7 = false;
                }
                _xifexpression_6 = _xifexpression_7;
              }
              _xblockexpression_5 = _xifexpression_6;
            }
            _xifexpression_5 = _xblockexpression_5;
          } else {
            _xifexpression_5 = false;
          }
          _xifexpression_4 = _xifexpression_5;
        }
        _switchResult = _xifexpression_4;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-border-color")) {
        _matched=true;
        boolean _xifexpression_6 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_6 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            HSB[] _hSBArray = this.toHSBArray(grad);
            settings.setSelectedBorderColors(_hSBArray);
            int[] _percents = CSSSWTColorHelper.getPercents(grad);
            settings.setSelectedBorderPercents(_percents);
            _xblockexpression_6 = true;
          }
          _xifexpression_6 = _xblockexpression_6;
        } else {
          boolean _xifexpression_7 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_7 = false;
            {
              RGB rgb = CSSSWTColorHelper.getRGB(((CSSPrimitiveValue)value));
              boolean _xifexpression_8 = false;
              boolean _notEquals = (!Objects.equal(rgb, null));
              if (_notEquals) {
                boolean _xblockexpression_8 = false;
                {
                  HSB hsb = new HSB(rgb);
                  settings.setSelectedBorderColors(new HSB[] { hsb, hsb });
                  settings.setSelectedBorderPercents(new int[] { 100 });
                  _xblockexpression_8 = true;
                }
                _xifexpression_8 = _xblockexpression_8;
              } else {
                boolean _xifexpression_9 = false;
                String _cssText = ((CSSPrimitiveValue)value).getCssText();
                boolean _equals = Objects.equal(_cssText, "none");
                if (_equals) {
                  boolean _xblockexpression_9 = false;
                  {
                    settings.setSelectedBorderColors(null);
                    _xblockexpression_9 = true;
                  }
                  _xifexpression_9 = _xblockexpression_9;
                } else {
                  _xifexpression_9 = false;
                }
                _xifexpression_8 = _xifexpression_9;
              }
              _xblockexpression_7 = _xifexpression_8;
            }
            _xifexpression_7 = _xblockexpression_7;
          } else {
            _xifexpression_7 = false;
          }
          _xifexpression_6 = _xifexpression_7;
        }
        _switchResult = _xifexpression_6;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-border-color")) {
        _matched=true;
        boolean _xifexpression_8 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_8 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            HSB[] _hSBArray = this.toHSBArray(grad);
            settings.setUnselectedBorderColors(_hSBArray);
            int[] _percents = CSSSWTColorHelper.getPercents(grad);
            settings.setUnselectedBorderPercents(_percents);
            _xblockexpression_8 = true;
          }
          _xifexpression_8 = _xblockexpression_8;
        } else {
          boolean _xifexpression_9 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_9 = false;
            {
              RGB rgb = CSSSWTColorHelper.getRGB(((CSSPrimitiveValue)value));
              boolean _xifexpression_10 = false;
              boolean _notEquals = (!Objects.equal(rgb, null));
              if (_notEquals) {
                boolean _xblockexpression_10 = false;
                {
                  HSB hsb = new HSB(rgb);
                  settings.setUnselectedBorderColors(new HSB[] { hsb, hsb });
                  settings.setUnselectedBorderPercents(new int[] { 100 });
                  _xblockexpression_10 = true;
                }
                _xifexpression_10 = _xblockexpression_10;
              } else {
                boolean _xifexpression_11 = false;
                String _cssText = ((CSSPrimitiveValue)value).getCssText();
                boolean _equals = Objects.equal(_cssText, "none");
                if (_equals) {
                  boolean _xblockexpression_11 = false;
                  {
                    settings.setUnselectedBorderColors(null);
                    _xblockexpression_11 = true;
                  }
                  _xifexpression_11 = _xblockexpression_11;
                } else {
                  _xifexpression_11 = false;
                }
                _xifexpression_10 = _xifexpression_11;
              }
              _xblockexpression_9 = _xifexpression_10;
            }
            _xifexpression_9 = _xblockexpression_9;
          } else {
            _xifexpression_9 = false;
          }
          _xifexpression_8 = _xifexpression_9;
        }
        _switchResult = _xifexpression_8;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-border-radius")) {
        _matched=true;
        boolean _xifexpression_10 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_10 = false;
          {
            float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
            int radius = ((int) _floatValue);
            settings.setBorderRadius(radius);
            _xblockexpression_10 = true;
          }
          _xifexpression_10 = _xblockexpression_10;
        } else {
          _xifexpression_10 = false;
        }
        _switchResult = _xifexpression_10;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-spacing")) {
        _matched=true;
        boolean _xifexpression_11 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_11 = false;
          {
            float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
            int tabSpacing = ((int) _floatValue);
            settings.setTabSpacing(tabSpacing);
            _xblockexpression_11 = true;
          }
          _xifexpression_11 = _xblockexpression_11;
        } else {
          _xifexpression_11 = false;
        }
        _switchResult = _xifexpression_11;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-color")) {
        _matched=true;
        boolean _xblockexpression_12 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setCloseButtonColor(hsb);
          } else {
            settings.setCloseButtonColor(null);
          }
          _xblockexpression_12 = true;
        }
        _switchResult = _xblockexpression_12;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-line-width")) {
        _matched=true;
        boolean _xifexpression_12 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_13 = false;
          {
            float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
            int lineWidth = ((int) _floatValue);
            settings.setCloseButtonLineWidth(lineWidth);
            _xblockexpression_13 = true;
          }
          _xifexpression_12 = _xblockexpression_13;
        } else {
          _xifexpression_12 = false;
        }
        _switchResult = _xifexpression_12;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-color")) {
        _matched=true;
        boolean _xblockexpression_14 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setHoverForgroundColor(hsb);
          } else {
            settings.setHoverForgroundColor(null);
          }
          _xblockexpression_14 = true;
        }
        _switchResult = _xblockexpression_14;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-border-color")) {
        _matched=true;
        boolean _xifexpression_13 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_15 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            HSB[] _hSBArray = this.toHSBArray(grad);
            settings.setHoverBorderColors(_hSBArray);
            int[] _percents = CSSSWTColorHelper.getPercents(grad);
            settings.setHoverBorderPercents(_percents);
            _xblockexpression_15 = true;
          }
          _xifexpression_13 = _xblockexpression_15;
        } else {
          boolean _xifexpression_14 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_16 = false;
            {
              RGB rgb = CSSSWTColorHelper.getRGB(((CSSPrimitiveValue)value));
              boolean _xifexpression_15 = false;
              boolean _notEquals = (!Objects.equal(rgb, null));
              if (_notEquals) {
                boolean _xblockexpression_17 = false;
                {
                  HSB hsb = new HSB(rgb);
                  settings.setHoverBorderColors(new HSB[] { hsb, hsb });
                  settings.setHoverBorderPercents(new int[] { 100 });
                  _xblockexpression_17 = true;
                }
                _xifexpression_15 = _xblockexpression_17;
              } else {
                boolean _xifexpression_16 = false;
                String _cssText = ((CSSPrimitiveValue)value).getCssText();
                boolean _equals = Objects.equal(_cssText, "none");
                if (_equals) {
                  boolean _xblockexpression_18 = false;
                  {
                    settings.setHoverBorderColors(null);
                    _xblockexpression_18 = true;
                  }
                  _xifexpression_16 = _xblockexpression_18;
                } else {
                  _xifexpression_16 = false;
                }
                _xifexpression_15 = _xifexpression_16;
              }
              _xblockexpression_16 = _xifexpression_15;
            }
            _xifexpression_14 = _xblockexpression_16;
          } else {
            _xifexpression_14 = false;
          }
          _xifexpression_13 = _xifexpression_14;
        }
        _switchResult = _xifexpression_13;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-hot-color")) {
        _matched=true;
        boolean _xblockexpression_17 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setCloseButtonHotColor(hsb);
          } else {
            settings.setCloseButtonHotColor(null);
          }
          _xblockexpression_17 = true;
        }
        _switchResult = _xblockexpression_17;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-active-color")) {
        _matched=true;
        boolean _xblockexpression_18 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setCloseButtonActiveColor(hsb);
          } else {
            settings.setCloseButtonActiveColor(null);
          }
          _xblockexpression_18 = true;
        }
        _switchResult = _xblockexpression_18;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-tabs-background")) {
        _matched=true;
        boolean _xifexpression_15 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_19 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            HSB[] _hSBArray = this.toHSBArray(grad);
            settings.setUnselectedBackgroundColors(_hSBArray);
            int[] _percents = CSSSWTColorHelper.getPercents(grad);
            settings.setUnselectedBackgroundPercents(_percents);
            _xblockexpression_19 = true;
          }
          _xifexpression_15 = _xblockexpression_19;
        } else {
          boolean _xifexpression_16 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_20 = false;
            {
              RGB rgb = CSSSWTColorHelper.getRGB(((CSSPrimitiveValue)value));
              boolean _xifexpression_17 = false;
              boolean _notEquals = (!Objects.equal(rgb, null));
              if (_notEquals) {
                boolean _xblockexpression_21 = false;
                {
                  HSB hsb = new HSB(rgb);
                  settings.setUnselectedBackgroundColors(new HSB[] { hsb, hsb });
                  settings.setUnselectedBackgroundPercents(new int[] { 100 });
                  _xblockexpression_21 = true;
                }
                _xifexpression_17 = _xblockexpression_21;
              } else {
                boolean _xifexpression_18 = false;
                String _cssText = ((CSSPrimitiveValue)value).getCssText();
                boolean _equals = Objects.equal(_cssText, "none");
                if (_equals) {
                  boolean _xblockexpression_22 = false;
                  {
                    settings.setUnselectedBackgroundColors(null);
                    _xblockexpression_22 = true;
                  }
                  _xifexpression_18 = _xblockexpression_22;
                } else {
                  _xifexpression_18 = false;
                }
                _xifexpression_17 = _xifexpression_18;
              }
              _xblockexpression_20 = _xifexpression_17;
            }
            _xifexpression_16 = _xblockexpression_20;
          } else {
            _xifexpression_16 = false;
          }
          _xifexpression_15 = _xifexpression_16;
        }
        _switchResult = _xifexpression_15;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-tabs-background")) {
        _matched=true;
        boolean _xifexpression_17 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_21 = false;
          {
            Gradient grad = CSSCompabilityHelper.getGradient(((CSSValueList) value));
            HSB[] _hSBArray = this.toHSBArray(grad);
            settings.setHoverBackgroundColors(_hSBArray);
            int[] _percents = CSSSWTColorHelper.getPercents(grad);
            settings.setHoverBackgroundPercents(_percents);
            _xblockexpression_21 = true;
          }
          _xifexpression_17 = _xblockexpression_21;
        } else {
          boolean _xifexpression_18 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_22 = false;
            {
              RGB rgb = CSSSWTColorHelper.getRGB(((CSSPrimitiveValue)value));
              boolean _xifexpression_19 = false;
              boolean _notEquals = (!Objects.equal(rgb, null));
              if (_notEquals) {
                boolean _xblockexpression_23 = false;
                {
                  HSB hsb = new HSB(rgb);
                  settings.setHoverBackgroundColors(new HSB[] { hsb, hsb });
                  settings.setHoverBackgroundPercents(new int[] { 100 });
                  _xblockexpression_23 = true;
                }
                _xifexpression_19 = _xblockexpression_23;
              } else {
                boolean _xifexpression_20 = false;
                String _cssText = ((CSSPrimitiveValue)value).getCssText();
                boolean _equals = Objects.equal(_cssText, "none");
                if (_equals) {
                  boolean _xblockexpression_24 = false;
                  {
                    settings.setHoverBackgroundColors(null);
                    _xblockexpression_24 = true;
                  }
                  _xifexpression_20 = _xblockexpression_24;
                } else {
                  _xifexpression_20 = false;
                }
                _xifexpression_19 = _xifexpression_20;
              }
              _xblockexpression_22 = _xifexpression_19;
            }
            _xifexpression_18 = _xblockexpression_22;
          } else {
            _xifexpression_18 = false;
          }
          _xifexpression_17 = _xifexpression_18;
        }
        _switchResult = _xifexpression_17;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-margin")) {
        _matched=true;
        boolean _xifexpression_19 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_23 = false;
          {
            Rectangle insets = this.toInset(((CSSValueList) value));
            settings.setMargins(insets);
            _xblockexpression_23 = true;
          }
          _xifexpression_19 = _xblockexpression_23;
        } else {
          boolean _xifexpression_20 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_24 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int margin = ((int) _floatValue);
              Rectangle _rectangle = new Rectangle(margin, margin, margin, margin);
              settings.setMargins(_rectangle);
              _xblockexpression_24 = true;
            }
            _xifexpression_20 = _xblockexpression_24;
          } else {
            _xifexpression_20 = false;
          }
          _xifexpression_19 = _xifexpression_20;
        }
        _switchResult = _xifexpression_19;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-padding")) {
        _matched=true;
        boolean _xifexpression_21 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_25 = false;
          {
            Rectangle insets = this.toInset(((CSSValueList) value));
            settings.setPaddings(insets);
            _xblockexpression_25 = true;
          }
          _xifexpression_21 = _xblockexpression_25;
        } else {
          boolean _xifexpression_22 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_26 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int margin = ((int) _floatValue);
              Rectangle _rectangle = new Rectangle(margin, margin, margin, margin);
              settings.setPaddings(_rectangle);
              _xblockexpression_26 = true;
            }
            _xifexpression_22 = _xblockexpression_26;
          } else {
            _xifexpression_22 = false;
          }
          _xifexpression_21 = _xifexpression_22;
        }
        _switchResult = _xifexpression_21;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-shadow-color")) {
        _matched=true;
        boolean _xblockexpression_27 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setShadowColor(hsb);
          } else {
            settings.setShadowColor(null);
          }
          _xblockexpression_27 = true;
        }
        _switchResult = _xblockexpression_27;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-shadow-position")) {
        _matched=true;
        boolean _xifexpression_23 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_28 = false;
          {
            Point position = this.toPoint(((CSSValueList) value));
            settings.setShadowPosition(position);
            _xblockexpression_28 = true;
          }
          _xifexpression_23 = _xblockexpression_28;
        } else {
          boolean _xifexpression_24 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_29 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int v = ((int) _floatValue);
              Point _point = new Point(v, v);
              settings.setShadowPosition(_point);
              _xblockexpression_29 = true;
            }
            _xifexpression_24 = _xblockexpression_29;
          } else {
            _xifexpression_24 = false;
          }
          _xifexpression_23 = _xifexpression_24;
        }
        _switchResult = _xifexpression_23;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-shadow-radius")) {
        _matched=true;
        boolean _xifexpression_25 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_30 = false;
          {
            float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
            int v = ((int) _floatValue);
            settings.setShadowRadius(v);
            _xblockexpression_30 = true;
          }
          _xifexpression_25 = _xblockexpression_30;
        } else {
          _xifexpression_25 = false;
        }
        _switchResult = _xifexpression_25;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-text-shadow-color")) {
        _matched=true;
        boolean _xblockexpression_31 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setSelectedTextShadowColor(hsb);
          } else {
            settings.setSelectedTextShadowColor(null);
          }
          _xblockexpression_31 = true;
        }
        _switchResult = _xblockexpression_31;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-text-shadow-position")) {
        _matched=true;
        boolean _xifexpression_26 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_32 = false;
          {
            Point position = this.toPoint(((CSSValueList) value));
            settings.setSelectedTextShadowPosition(position);
            _xblockexpression_32 = true;
          }
          _xifexpression_26 = _xblockexpression_32;
        } else {
          boolean _xifexpression_27 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_33 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int v = ((int) _floatValue);
              Point _point = new Point(v, v);
              settings.setSelectedTextShadowPosition(_point);
              _xblockexpression_33 = true;
            }
            _xifexpression_27 = _xblockexpression_33;
          } else {
            _xifexpression_27 = false;
          }
          _xifexpression_26 = _xifexpression_27;
        }
        _switchResult = _xifexpression_26;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-text-shadow-color")) {
        _matched=true;
        boolean _xblockexpression_34 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setUnselectedTextShadowColor(hsb);
          } else {
            settings.setUnselectedTextShadowColor(null);
          }
          _xblockexpression_34 = true;
        }
        _switchResult = _xblockexpression_34;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-text-shadow-position")) {
        _matched=true;
        boolean _xifexpression_28 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_35 = false;
          {
            Point position = this.toPoint(((CSSValueList) value));
            settings.setUnselectedTextShadowPosition(position);
            _xblockexpression_35 = true;
          }
          _xifexpression_28 = _xblockexpression_35;
        } else {
          boolean _xifexpression_29 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_36 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int v = ((int) _floatValue);
              Point _point = new Point(v, v);
              settings.setUnselectedTextShadowPosition(_point);
              _xblockexpression_36 = true;
            }
            _xifexpression_29 = _xblockexpression_36;
          } else {
            _xifexpression_29 = false;
          }
          _xifexpression_28 = _xifexpression_29;
        }
        _switchResult = _xifexpression_28;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-text-shadow-color")) {
        _matched=true;
        boolean _xblockexpression_37 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setHoverTextShadowColor(hsb);
          } else {
            settings.setHoverTextShadowColor(null);
          }
          _xblockexpression_37 = true;
        }
        _switchResult = _xblockexpression_37;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-text-shadow-position")) {
        _matched=true;
        boolean _xifexpression_30 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_38 = false;
          {
            Point position = this.toPoint(((CSSValueList) value));
            settings.setHoverTextShadowPosition(position);
            _xblockexpression_38 = true;
          }
          _xifexpression_30 = _xblockexpression_38;
        } else {
          boolean _xifexpression_31 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_39 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int v = ((int) _floatValue);
              Point _point = new Point(v, v);
              settings.setHoverTextShadowPosition(_point);
              _xblockexpression_39 = true;
            }
            _xifexpression_31 = _xblockexpression_39;
          } else {
            _xifexpression_31 = false;
          }
          _xifexpression_30 = _xifexpression_31;
        }
        _switchResult = _xifexpression_30;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-item-padding")) {
        _matched=true;
        boolean _xifexpression_32 = false;
        if ((value instanceof CSSValueList)) {
          boolean _xblockexpression_40 = false;
          {
            Rectangle insets = this.toInset(((CSSValueList) value));
            settings.setTabItemPaddings(insets);
            _xblockexpression_40 = true;
          }
          _xifexpression_32 = _xblockexpression_40;
        } else {
          boolean _xifexpression_33 = false;
          if ((value instanceof CSSPrimitiveValue)) {
            boolean _xblockexpression_41 = false;
            {
              float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
              int margin = ((int) _floatValue);
              Rectangle _rectangle = new Rectangle(margin, margin, margin, margin);
              settings.setTabItemPaddings(_rectangle);
              _xblockexpression_41 = true;
            }
            _xifexpression_33 = _xblockexpression_41;
          } else {
            _xifexpression_33 = false;
          }
          _xifexpression_32 = _xifexpression_33;
        }
        _switchResult = _xifexpression_32;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-item-horizontal-spacing")) {
        _matched=true;
        boolean _xifexpression_34 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_42 = false;
          {
            float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
            int radius = ((int) _floatValue);
            settings.setTabItemHorizontalSpacing(radius);
            _xblockexpression_42 = true;
          }
          _xifexpression_34 = _xblockexpression_42;
        } else {
          _xifexpression_34 = false;
        }
        _switchResult = _xifexpression_34;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-chevron-color")) {
        _matched=true;
        boolean _xblockexpression_43 = false;
        {
          RGB rgb = CSSSWTColorHelper.getRGB(((CSSValue) value));
          boolean _notEquals = (!Objects.equal(rgb, null));
          if (_notEquals) {
            HSB hsb = new HSB(rgb.red, rgb.green, rgb.blue);
            settings.setChevronColor(hsb);
          } else {
            settings.setChevronColor(null);
          }
          _xblockexpression_43 = true;
        }
        _switchResult = _xblockexpression_43;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-truncate-tab-items")) {
        _matched=true;
        boolean _xblockexpression_44 = false;
        {
          Display _display = tabFolder.getDisplay();
          Object _convert = engine.convert(value, Boolean.class, _display);
          Boolean truncateTabItems = ((Boolean) _convert);
          settings.setTruncateTabItems((truncateTabItems).booleanValue());
          _xblockexpression_44 = true;
        }
        _switchResult = _xblockexpression_44;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-use-ellipses")) {
        _matched=true;
        boolean _xblockexpression_45 = false;
        {
          Display _display = tabFolder.getDisplay();
          Object _convert = engine.convert(value, Boolean.class, _display);
          Boolean useEllipse = ((Boolean) _convert);
          settings.setUseEllipses((useEllipse).booleanValue());
          _xblockexpression_45 = true;
        }
        _switchResult = _xblockexpression_45;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-minimum-characters")) {
        _matched=true;
        boolean _xifexpression_35 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_46 = false;
          {
            float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
            int v = ((int) _floatValue);
            settings.setMinimumCharacters(v);
            _xblockexpression_46 = true;
          }
          _xifexpression_35 = _xblockexpression_46;
        } else {
          _xifexpression_35 = false;
        }
        _switchResult = _xifexpression_35;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-alignment")) {
        _matched=true;
        boolean _xblockexpression_47 = false;
        {
          if ((value instanceof Measure)) {
            Measure measureValue = ((Measure) value);
            try {
              String _stringValue = measureValue.getStringValue();
              VerticalAlignment alignment = VerticalAlignment.fromCSSValue(_stringValue);
              settings.setCloseButtonAlignment(alignment);
            } catch (final Throwable _t) {
              if (_t instanceof IllegalArgumentException) {
                final IllegalArgumentException e = (IllegalArgumentException)_t;
                return false;
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
          _xblockexpression_47 = false;
        }
        _switchResult = _xblockexpression_47;
      }
    }
    if (!_matched) {
      _switchResult = false;
    }
    boolean applied = _switchResult;
    return applied;
  }
  
  public String retrieveCSSProperty(final Object element, final String property, final String pseudo, final CSSEngine engine) throws Exception {
    @Extension
    final JTabRendererHelper JTabRendererHelper = new net.jeeeyul.eclipse.themes.rendering.internal.JTabRendererHelper();
    CTabFolderElement tabFolderElement = ((CTabFolderElement) element);
    Object _nativeWidget = tabFolderElement.getNativeWidget();
    CTabFolder tabFolder = ((CTabFolder) _nativeWidget);
    CTabFolderRenderer _renderer = tabFolder.getRenderer();
    if ((!(_renderer instanceof JeeeyulsTabRenderer))) {
      return null;
    }
    CTabFolderRenderer _renderer_1 = tabFolder.getRenderer();
    JeeeyulsTabRenderer renderer = ((JeeeyulsTabRenderer) _renderer_1);
    JTabSettings settings = renderer.getSettings();
    String _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(property, "jtab-border-color")) {
        _matched=true;
        HSB[] _borderColors = settings.getBorderColors();
        int[] _borderPercents = settings.getBorderPercents();
        _switchResult = this.toHTML(_borderColors, _borderPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-border-color")) {
        _matched=true;
        HSB[] _selectedBorderColors = settings.getSelectedBorderColors();
        int[] _selectedBorderPercents = settings.getSelectedBorderPercents();
        _switchResult = this.toHTML(_selectedBorderColors, _selectedBorderPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-border-color")) {
        _matched=true;
        HSB[] _unselectedBorderColors = settings.getUnselectedBorderColors();
        int[] _unselectedBorderPercents = settings.getUnselectedBorderPercents();
        _switchResult = this.toHTML(_unselectedBorderColors, _unselectedBorderPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-border-color")) {
        _matched=true;
        HSB[] _hoverBorderColors = settings.getHoverBorderColors();
        int[] _hoverBorderPercents = settings.getHoverBorderPercents();
        _switchResult = this.toHTML(_hoverBorderColors, _hoverBorderPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-border-radius")) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        int _borderRadius = settings.getBorderRadius();
        _builder.append(_borderRadius, "");
        _builder.append("px");
        _switchResult = _builder.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-spacing")) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        int _tabSpacing = settings.getTabSpacing();
        _builder_1.append(_tabSpacing, "");
        _builder_1.append("px");
        _switchResult = _builder_1.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-color")) {
        _matched=true;
        HSB _closeButtonColor = settings.getCloseButtonColor();
        _switchResult = this.safeHTML(_closeButtonColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-hot-color")) {
        _matched=true;
        HSB _closeButtonHotColor = settings.getCloseButtonHotColor();
        _switchResult = this.safeHTML(_closeButtonHotColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-active-color")) {
        _matched=true;
        HSB _closeButtonActiveColor = settings.getCloseButtonActiveColor();
        _switchResult = this.safeHTML(_closeButtonActiveColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-line-width")) {
        _matched=true;
        StringConcatenation _builder_2 = new StringConcatenation();
        int _closeButtonLineWidth = settings.getCloseButtonLineWidth();
        _builder_2.append(_closeButtonLineWidth, "");
        _builder_2.append("px");
        _switchResult = _builder_2.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-header-background")) {
        _matched=true;
        Color[] _gradientColor = JTabRendererHelper.getGradientColor(tabFolder);
        int[] _gradientPercents = JTabRendererHelper.getGradientPercents(tabFolder);
        _switchResult = this.toHTML(_gradientColor, _gradientPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-tab-background")) {
        _matched=true;
        Color[] _selectionGradientColor = JTabRendererHelper.getSelectionGradientColor(tabFolder);
        int[] _selectionGradientPercents = JTabRendererHelper.getSelectionGradientPercents(tabFolder);
        _switchResult = this.toHTML(_selectionGradientColor, _selectionGradientPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-tabs-background")) {
        _matched=true;
        HSB[] _unselectedBackgroundColors = settings.getUnselectedBackgroundColors();
        int[] _unselectedBackgroundPercents = settings.getUnselectedBackgroundPercents();
        _switchResult = this.toHTML(_unselectedBackgroundColors, _unselectedBackgroundPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-tabs-background")) {
        _matched=true;
        HSB[] _hoverBackgroundColors = settings.getHoverBackgroundColors();
        int[] _hoverBackgroundPercents = settings.getHoverBackgroundPercents();
        _switchResult = this.toHTML(_hoverBackgroundColors, _hoverBackgroundPercents);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-color")) {
        _matched=true;
        HSB _hoverForgroundColor = settings.getHoverForgroundColor();
        _switchResult = this.safeHTML(_hoverForgroundColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-margin")) {
        _matched=true;
        StringConcatenation _builder_3 = new StringConcatenation();
        Rectangle _margins = settings.getMargins();
        _builder_3.append(_margins.y, "");
        _builder_3.append("px ");
        Rectangle _margins_1 = settings.getMargins();
        _builder_3.append(_margins_1.width, "");
        _builder_3.append("px ");
        Rectangle _margins_2 = settings.getMargins();
        _builder_3.append(_margins_2.height, "");
        _builder_3.append("px ");
        Rectangle _margins_3 = settings.getMargins();
        _builder_3.append(_margins_3.x, "");
        _builder_3.append("px");
        _switchResult = _builder_3.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-padding")) {
        _matched=true;
        StringConcatenation _builder_4 = new StringConcatenation();
        Rectangle _paddings = settings.getPaddings();
        _builder_4.append(_paddings.y, "");
        _builder_4.append("px ");
        Rectangle _paddings_1 = settings.getPaddings();
        _builder_4.append(_paddings_1.width, "");
        _builder_4.append("px ");
        Rectangle _paddings_2 = settings.getPaddings();
        _builder_4.append(_paddings_2.height, "");
        _builder_4.append("px ");
        Rectangle _paddings_3 = settings.getPaddings();
        _builder_4.append(_paddings_3.x, "");
        _builder_4.append("px");
        _switchResult = _builder_4.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-shadow-color")) {
        _matched=true;
        HSB _shadowColor = settings.getShadowColor();
        _switchResult = this.safeHTML(_shadowColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-shadow-radius")) {
        _matched=true;
        StringConcatenation _builder_5 = new StringConcatenation();
        int _shadowRadius = settings.getShadowRadius();
        _builder_5.append(_shadowRadius, "");
        _builder_5.append("px");
        _switchResult = _builder_5.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-shadow-position")) {
        _matched=true;
        Point _shadowPosition = settings.getShadowPosition();
        _switchResult = this.safeHTML(_shadowPosition);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-text-shadow-color")) {
        _matched=true;
        HSB _selectedTextShadowColor = settings.getSelectedTextShadowColor();
        _switchResult = this.safeHTML(_selectedTextShadowColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-text-shadow-color")) {
        _matched=true;
        HSB _unselectedTextShadowColor = settings.getUnselectedTextShadowColor();
        _switchResult = this.safeHTML(_unselectedTextShadowColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-text-shadow-color")) {
        _matched=true;
        HSB _hoverTextShadowColor = settings.getHoverTextShadowColor();
        _switchResult = this.safeHTML(_hoverTextShadowColor);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-selected-text-shadow-position")) {
        _matched=true;
        Point _selectedTextShadowPosition = settings.getSelectedTextShadowPosition();
        _switchResult = this.safeHTML(_selectedTextShadowPosition);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-unselected-text-shadow-position")) {
        _matched=true;
        Point _unselectedTextShadowPosition = settings.getUnselectedTextShadowPosition();
        _switchResult = this.safeHTML(_unselectedTextShadowPosition);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-hover-text-shadow-position")) {
        _matched=true;
        Point _hoverTextShadowPosition = settings.getHoverTextShadowPosition();
        _switchResult = this.safeHTML(_hoverTextShadowPosition);
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-item-padding")) {
        _matched=true;
        StringConcatenation _builder_6 = new StringConcatenation();
        _builder_6.append("0px ");
        Rectangle _tabItemPaddings = settings.getTabItemPaddings();
        _builder_6.append(_tabItemPaddings.width, "");
        _builder_6.append("px 0px ");
        Rectangle _tabItemPaddings_1 = settings.getTabItemPaddings();
        _builder_6.append(_tabItemPaddings_1.x, "");
        _builder_6.append("px");
        _switchResult = _builder_6.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-item-horizontal-spacing")) {
        _matched=true;
        StringConcatenation _builder_7 = new StringConcatenation();
        int _tabItemHorizontalSpacing = settings.getTabItemHorizontalSpacing();
        _builder_7.append(_tabItemHorizontalSpacing, "");
        _builder_7.append("px");
        _switchResult = _builder_7.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-chevron-color")) {
        _matched=true;
        StringConcatenation _builder_8 = new StringConcatenation();
        HSB _chevronColor = settings.getChevronColor();
        String _hTMLCode = _chevronColor.toHTMLCode();
        _builder_8.append(_hTMLCode, "");
        _switchResult = _builder_8.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-truncate-tab-items")) {
        _matched=true;
        StringConcatenation _builder_9 = new StringConcatenation();
        boolean _isTruncateTabItems = settings.isTruncateTabItems();
        _builder_9.append(_isTruncateTabItems, "");
        _switchResult = _builder_9.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-use-ellipses")) {
        _matched=true;
        StringConcatenation _builder_10 = new StringConcatenation();
        boolean _isUseEllipses = settings.isUseEllipses();
        _builder_10.append(_isUseEllipses, "");
        _switchResult = _builder_10.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-minimum-characters")) {
        _matched=true;
        StringConcatenation _builder_11 = new StringConcatenation();
        int _minimumCharacters = settings.getMinimumCharacters();
        _builder_11.append(_minimumCharacters, "");
        _switchResult = _builder_11.toString();
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtab-close-button-alignment")) {
        _matched=true;
        VerticalAlignment _closeButtonAlignment = settings.getCloseButtonAlignment();
        _switchResult = _closeButtonAlignment.getCSSValue();
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }
  
  private String safeHTML(final HSB hsb) {
    String _xifexpression = null;
    boolean _equals = Objects.equal(hsb, null);
    if (_equals) {
      _xifexpression = "none";
    } else {
      _xifexpression = hsb.toHTMLCode();
    }
    return _xifexpression;
  }
  
  private String safeHTML(final Point point) {
    String _xifexpression = null;
    boolean _equals = Objects.equal(point, null);
    if (_equals) {
      _xifexpression = "none";
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(point.x, "");
      _builder.append("px ");
      _builder.append(point.y, "");
      _builder.append("px");
      _xifexpression = _builder.toString();
    }
    return _xifexpression;
  }
  
  private String toHTML(final HSB[] colors, final int[] percents) {
    boolean _or = false;
    boolean _equals = Objects.equal(colors, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _equals_1 = Objects.equal(percents, null);
      _or = _equals_1;
    }
    if (_or) {
      return "none";
    } else {
      StringConcatenation _builder = new StringConcatenation();
      final Function1<HSB, String> _function = new Function1<HSB, String>() {
        public String apply(final HSB it) {
          return it.toHTMLCode();
        }
      };
      String _join = IterableExtensions.<HSB>join(((Iterable<HSB>)Conversions.doWrapArray(colors)), " ", _function);
      _builder.append(_join, "");
      _builder.append(" ");
      final Function1<Integer, String> _function_1 = new Function1<Integer, String>() {
        public String apply(final Integer it) {
          return (it + "%");
        }
      };
      String _join_1 = IterableExtensions.<Integer>join(((Iterable<Integer>)Conversions.doWrapArray(percents)), " ", _function_1);
      _builder.append(_join_1, "");
      return _builder.toString();
    }
  }
  
  private String toHTML(final Color[] colors, final int[] percents) {
    boolean _or = false;
    boolean _equals = Objects.equal(colors, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _equals_1 = Objects.equal(percents, null);
      _or = _equals_1;
    }
    if (_or) {
      return "none";
    } else {
      StringConcatenation _builder = new StringConcatenation();
      final Function1<Color, String> _function = new Function1<Color, String>() {
        public String apply(final Color it) {
          RGB _rGB = it.getRGB();
          HSB _hSB = new HSB(_rGB);
          return _hSB.toHTMLCode();
        }
      };
      String _join = IterableExtensions.<Color>join(((Iterable<Color>)Conversions.doWrapArray(colors)), " ", _function);
      _builder.append(_join, "");
      _builder.append(" ");
      final Function1<Integer, String> _function_1 = new Function1<Integer, String>() {
        public String apply(final Integer it) {
          return (it + "%");
        }
      };
      String _join_1 = IterableExtensions.<Integer>join(((Iterable<Integer>)Conversions.doWrapArray(percents)), " ", _function_1);
      _builder.append(_join_1, "");
      return _builder.toString();
    }
  }
  
  private Rectangle toInset(final CSSValueList list) {
    int top = 0;
    int right = 0;
    int bottom = 0;
    int left = 0;
    int _length = list.getLength();
    switch (_length) {
      case 4:
        CSSValue _item = list.item(0);
        int _asPX = this.asPX(_item);
        top = _asPX;
        CSSValue _item_1 = list.item(1);
        int _asPX_1 = this.asPX(_item_1);
        right = _asPX_1;
        CSSValue _item_2 = list.item(2);
        int _asPX_2 = this.asPX(_item_2);
        bottom = _asPX_2;
        CSSValue _item_3 = list.item(3);
        int _asPX_3 = this.asPX(_item_3);
        left = _asPX_3;
        break;
      case 2:
        CSSValue _item_4 = list.item(0);
        int _asPX_4 = this.asPX(_item_4);
        top = _asPX_4;
        CSSValue _item_5 = list.item(1);
        int _asPX_5 = this.asPX(_item_5);
        right = _asPX_5;
        CSSValue _item_6 = list.item(0);
        int _asPX_6 = this.asPX(_item_6);
        bottom = _asPX_6;
        CSSValue _item_7 = list.item(1);
        int _asPX_7 = this.asPX(_item_7);
        left = _asPX_7;
        break;
    }
    return new Rectangle(left, top, right, bottom);
  }
  
  private Point toPoint(final CSSValueList list) {
    int x = 0;
    int y = 0;
    int _length = list.getLength();
    switch (_length) {
      case 2:
        CSSValue _item = list.item(0);
        int _asPX = this.asPX(_item);
        x = _asPX;
        CSSValue _item_1 = list.item(1);
        int _asPX_1 = this.asPX(_item_1);
        y = _asPX_1;
        break;
    }
    return new Point(x, y);
  }
  
  private int asPX(final CSSValue value) {
    if ((value instanceof CSSPrimitiveValue)) {
      float _floatValue = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
      return ((int) _floatValue);
    }
    return 0;
  }
  
  private HSB[] toHSBArray(final Gradient grad) {
    final ArrayList<HSB> hsb = CollectionLiterals.<HSB>newArrayList();
    List _rGBs = grad.getRGBs();
    final Consumer<Object> _function = new Consumer<Object>() {
      public void accept(final Object it) {
        HSB _hSB = new HSB(((RGB) it));
        hsb.add(_hSB);
      }
    };
    _rGBs.forEach(_function);
    return ((HSB[])Conversions.unwrapArray(hsb, HSB.class));
  }
}
