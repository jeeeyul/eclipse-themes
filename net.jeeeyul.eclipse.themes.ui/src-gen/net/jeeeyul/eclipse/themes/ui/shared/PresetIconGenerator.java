package net.jeeeyul.eclipse.themes.ui.shared;

import com.google.common.base.Objects;
import java.util.Properties;
import java.util.Set;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferenceInitializer;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.Gradient;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class PresetIconGenerator {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  public ImageDescriptor generatedDescriptor(final IJTPreset preset) {
    Thread _currentThread = Thread.currentThread();
    Display _display = this._sWTExtensions.getDisplay();
    Thread _thread = _display.getThread();
    boolean _notEquals = (!Objects.equal(_currentThread, _thread));
    if (_notEquals) {
      throw new SWTException("Invalid Thread Exception");
    }
    ImageData _generateData = this.generateData(preset);
    return ImageDescriptor.createFromImageData(_generateData);
  }
  
  private ImageData generateData(final IJTPreset preset) {
    PreferenceStore _preferenceStore = new PreferenceStore();
    JThemePreferenceStore store = new JThemePreferenceStore(_preferenceStore);
    JTPreferenceInitializer _jTPreferenceInitializer = new JTPreferenceInitializer();
    _jTPreferenceInitializer.initializeDefaultPreset(store);
    Properties _properties = preset.getProperties();
    Set<Object> _keySet = _properties.keySet();
    for (final Object keyObj : _keySet) {
      {
        String key = ((String) keyObj);
        Properties _properties_1 = preset.getProperties();
        String _property = _properties_1.getProperty(key);
        store.setValue(key, _property);
      }
    }
    final Image image = this._sWTExtensions.newImage(16, 16);
    Rectangle _newRectangle = this._sWTExtensions.newRectangle(0, 0, 16, 16);
    final Rectangle bounds = this._sWTExtensions.shrink(_newRectangle, 2);
    GC gc = new GC(image);
    Gradient _gradient = store.getGradient(JTPConstants.ActivePartStack.HEADER_BACKGROUND_COLOR);
    this._sWTExtensions.fillGradientRectangle(gc, bounds, _gradient, true);
    final Procedure1<Path> _function = new Procedure1<Path>() {
      public void apply(final Path it) {
        Rectangle _resized = PresetIconGenerator.this._sWTExtensions.getResized(bounds, (-1), (-1));
        PresetIconGenerator.this._sWTExtensions.addRectangle(it, _resized);
      }
    };
    Path outline = this._sWTExtensions.newPath(_function);
    boolean _boolean = store.getBoolean(JTPConstants.ActivePartStack.BORDER_SHOW);
    if (_boolean) {
      Gradient _gradient_1 = store.getGradient(JTPConstants.ActivePartStack.BORDER_COLOR);
      this._sWTExtensions.drawGradientPath(gc, outline, _gradient_1, true);
    }
    ImageData data = image.getImageData();
    this._sWTExtensions.<Path>safeDispose(outline);
    this._sWTExtensions.<GC>safeDispose(gc);
    this._sWTExtensions.<Image>safeDispose(image);
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, 16, true);
    for (final Integer x : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, 16, true);
      for (final Integer y : _doubleDotLessThan_1) {
        boolean _contains = bounds.contains((x).intValue(), (y).intValue());
        boolean _not = (!_contains);
        if (_not) {
          data.setAlpha((x).intValue(), (y).intValue(), 0);
        } else {
          data.setAlpha((x).intValue(), (y).intValue(), 255);
        }
      }
    }
    return data;
  }
}
