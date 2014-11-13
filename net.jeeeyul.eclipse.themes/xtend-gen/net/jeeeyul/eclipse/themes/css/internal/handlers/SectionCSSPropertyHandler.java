package net.jeeeyul.eclipse.themes.css.internal.handlers;

import com.google.common.base.Objects;
import net.jeeeyul.eclipse.themes.css.EditorLineSupport;
import net.jeeeyul.eclipse.themes.css.internal.elements.SectionElement;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.xtext.xbase.lib.Extension;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
@SuppressWarnings("all")
public class SectionCSSPropertyHandler implements ICSSPropertyHandler {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  public boolean applyCSSProperty(final Object element, final String property, final CSSValue value, final String pseudo, final CSSEngine engine) throws Exception {
    SectionElement sElement = ((SectionElement) element);
    Object _nativeWidget = sElement.getNativeWidget();
    Section section = ((Section) _nativeWidget);
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(property, "jtitle-bar-border-color")) {
        _matched=true;
        boolean _xifexpression = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression = false;
          {
            Display _display = section.getDisplay();
            Object _convert = engine.convert(value, Color.class, _display);
            Color newColor = ((Color) _convert);
            section.setTitleBarBorderColor(newColor);
            section.redraw();
            _xblockexpression = true;
          }
          _xifexpression = _xblockexpression;
        } else {
          _xifexpression = false;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtitle-bar-background-color")) {
        _matched=true;
        boolean _xifexpression_1 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_1 = false;
          {
            Display _display = section.getDisplay();
            Object _convert = engine.convert(value, Color.class, _display);
            Color newColor = ((Color) _convert);
            Color _titleBarBackground = section.getTitleBarBackground();
            boolean _equals = Objects.equal(_titleBarBackground, newColor);
            if (_equals) {
              return true;
            }
            section.setTitleBarBackground(newColor);
            this.updateBackgroundImage(section);
            section.redraw();
            _xblockexpression_1 = true;
          }
          _xifexpression_1 = _xblockexpression_1;
        } else {
          _xifexpression_1 = false;
        }
        _switchResult = _xifexpression_1;
      }
    }
    if (!_matched) {
      if (Objects.equal(property, "jtitle-bar-text-color")) {
        _matched=true;
        boolean _xifexpression_2 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_2 = false;
          {
            Display _display = section.getDisplay();
            Object _convert = engine.convert(value, Color.class, _display);
            Color newColor = ((Color) _convert);
            section.setTitleBarForeground(newColor);
            section.setToggleColor(newColor);
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
      if (Objects.equal(property, "jtitle-bar-active-text-color")) {
        _matched=true;
        boolean _xifexpression_3 = false;
        if ((value instanceof CSSPrimitiveValue)) {
          boolean _xblockexpression_3 = false;
          {
            Display _display = section.getDisplay();
            Object _convert = engine.convert(value, Color.class, _display);
            Color newColor = ((Color) _convert);
            section.setActiveToggleColor(newColor);
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
      SectionElement sElement = ((SectionElement) element);
      Object _nativeWidget = sElement.getNativeWidget();
      Section section = ((Section) _nativeWidget);
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (Objects.equal(property, "jtitle-bar-border-color")) {
          _matched=true;
          Color _titleBarBorderColor = section.getTitleBarBorderColor();
          RGB _rGB = _titleBarBorderColor.getRGB();
          HSB _hSB = new HSB(_rGB);
          _switchResult = _hSB.toHTMLCode();
        }
      }
      if (!_matched) {
        if (Objects.equal(property, "jtitle-bar-background-color")) {
          _matched=true;
          Color _titleBarBackground = section.getTitleBarBackground();
          RGB _rGB_1 = _titleBarBackground.getRGB();
          HSB _hSB_1 = new HSB(_rGB_1);
          _switchResult = _hSB_1.toHTMLCode();
        }
      }
      if (!_matched) {
        if (Objects.equal(property, "jtitle-bar-text-color")) {
          _matched=true;
          Color _titleBarForeground = section.getTitleBarForeground();
          RGB _rGB_2 = _titleBarForeground.getRGB();
          HSB _hSB_2 = new HSB(_rGB_2);
          _switchResult = _hSB_2.toHTMLCode();
        }
      }
      if (!_matched) {
        _switchResult = null;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  private void updateBackgroundImage(final Section section) {
    Image _backgroundImage = section.getBackgroundImage();
    boolean _isAlive = this._sWTExtensions.isAlive(_backgroundImage);
    boolean _not = (!_isAlive);
    if (_not) {
      return;
    }
    Image _backgroundImage_1 = section.getBackgroundImage();
    ImageData data = _backgroundImage_1.getImageData();
    int bgColor = data.getPixel(0, (data.height - 1));
    int from = 0;
    int to = (data.height - 1);
    while ((data.getPixel(0, (from + 1)) == bgColor)) {
      from++;
    }
    while ((data.getPixel(0, (to - 1)) == bgColor)) {
      to--;
    }
    Image _backgroundImage_2 = section.getBackgroundImage();
    GC gc = new GC(_backgroundImage_2);
    Color _titleBarBackground = section.getTitleBarBackground();
    gc.setForeground(_titleBarBackground);
    Color _background = section.getBackground();
    gc.setBackground(_background);
    gc.fillGradientRectangle(0, from, data.width, (to - from), true);
    gc.dispose();
  }
}
