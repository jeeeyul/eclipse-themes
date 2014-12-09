package net.jeeeyul.eclipse.themes.ui.internal;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import net.jeeeyul.swtend.ui.ColorStop;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class SerializeUtil {
  public SerializeUtil() {
  }
  
  protected String _serialize(final HSB hsb) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(hsb.hue, "");
    _builder.append(", ");
    _builder.append(hsb.saturation, "");
    _builder.append(", ");
    _builder.append(hsb.brightness, "");
    return _builder.toString();
  }
  
  protected String _serialize(final Point point) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(point.x, "");
    _builder.append(", ");
    _builder.append(point.y, "");
    return _builder.toString();
  }
  
  protected String _serialize(final Rectangle rect) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(rect.x, "");
    _builder.append(", ");
    _builder.append(rect.y, "");
    _builder.append(", ");
    _builder.append(rect.width, "");
    _builder.append(", ");
    _builder.append(rect.height, "");
    return _builder.toString();
  }
  
  protected String _serialize(final Gradient grad) {
    final Function1<ColorStop, String> _function = new Function1<ColorStop, String>() {
      public String apply(final ColorStop it) {
        StringConcatenation _builder = new StringConcatenation();
        String _serialize = SerializeUtil.this.serialize(it.color);
        _builder.append(_serialize, "");
        _builder.append(", ");
        _builder.append(it.percent, "");
        return _builder.toString();
      }
    };
    return IterableExtensions.<ColorStop>join(grad, "|", _function);
  }
  
  public Gradient deserializeGradient(final String exp) {
    try {
      final Gradient grad = new Gradient();
      String[] segments = exp.split("\\s*\\|\\s*");
      final String[] _converted_segments = (String[])segments;
      final Consumer<String> _function = new Consumer<String>() {
        public void accept(final String it) {
          String[] _split = it.split("\\s*,\\s*");
          final Function1<String, Float> _function = new Function1<String, Float>() {
            public Float apply(final String it) {
              return Float.valueOf(Float.parseFloat(it));
            }
          };
          List<Float> components = ListExtensions.<String, Float>map(((List<String>)Conversions.doWrapArray(_split)), _function);
          Float _get = components.get(0);
          Float _get_1 = components.get(1);
          Float _get_2 = components.get(2);
          HSB color = new HSB((_get).floatValue(), (_get_1).floatValue(), (_get_2).floatValue());
          Float _get_3 = components.get(3);
          int _intValue = _get_3.intValue();
          ColorStop _colorStop = new ColorStop(color, _intValue);
          grad.add(_colorStop);
        }
      };
      ((List<String>)Conversions.doWrapArray(_converted_segments)).forEach(_function);
      return grad;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  public HSB desrializeHSB(final String exp) {
    try {
      String[] _split = exp.split("\\s*,\\s*");
      final Function1<String, Float> _function = new Function1<String, Float>() {
        public Float apply(final String it) {
          return Float.valueOf(Float.parseFloat(it));
        }
      };
      List<Float> components = ListExtensions.<String, Float>map(((List<String>)Conversions.doWrapArray(_split)), _function);
      Float _get = components.get(0);
      Float _get_1 = components.get(1);
      Float _get_2 = components.get(2);
      return new HSB((_get).floatValue(), (_get_1).floatValue(), (_get_2).floatValue());
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  public Rectangle desrializeRectangle(final String exp) {
    Rectangle _xtrycatchfinallyexpression = null;
    try {
      Rectangle _xblockexpression = null;
      {
        String[] _split = exp.split("\\s*,\\s*");
        final Function1<String, Integer> _function = new Function1<String, Integer>() {
          public Integer apply(final String it) {
            return Integer.valueOf(Integer.parseInt(it));
          }
        };
        List<Integer> components = ListExtensions.<String, Integer>map(((List<String>)Conversions.doWrapArray(_split)), _function);
        Integer _get = components.get(0);
        Integer _get_1 = components.get(1);
        Integer _get_2 = components.get(2);
        Integer _get_3 = components.get(3);
        _xblockexpression = new Rectangle((_get).intValue(), (_get_1).intValue(), (_get_2).intValue(), (_get_3).intValue());
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public Point desrializePoint(final String exp) {
    Point _xtrycatchfinallyexpression = null;
    try {
      Point _xblockexpression = null;
      {
        String[] _split = exp.split("\\s*,\\s*");
        final Function1<String, Integer> _function = new Function1<String, Integer>() {
          public Integer apply(final String it) {
            return Integer.valueOf(Integer.parseInt(it));
          }
        };
        List<Integer> components = ListExtensions.<String, Integer>map(((List<String>)Conversions.doWrapArray(_split)), _function);
        Integer _get = components.get(0);
        Integer _get_1 = components.get(1);
        _xblockexpression = new Point((_get).intValue(), (_get_1).intValue());
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public String serialize(final Object grad) {
    if (grad instanceof Gradient) {
      return _serialize((Gradient)grad);
    } else if (grad instanceof Point) {
      return _serialize((Point)grad);
    } else if (grad instanceof Rectangle) {
      return _serialize((Rectangle)grad);
    } else if (grad instanceof HSB) {
      return _serialize((HSB)grad);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(grad).toString());
    }
  }
}
