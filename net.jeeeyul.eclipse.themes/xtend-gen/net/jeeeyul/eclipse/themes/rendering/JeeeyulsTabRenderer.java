package net.jeeeyul.eclipse.themes.rendering;

import com.google.common.base.Objects;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import net.jeeeyul.eclipse.themes.CoreImages;
import net.jeeeyul.eclipse.themes.css.internal.CSSClasses;
import net.jeeeyul.eclipse.themes.internal.Debug;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment;
import net.jeeeyul.eclipse.themes.rendering.internal.JTabRendererHelper;
import net.jeeeyul.eclipse.themes.rendering.internal.Shadow9PatchFactory;
import net.jeeeyul.eclipse.themes.util.ImageDataUtil;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.HSB;
import net.jeeeyul.swtend.ui.NinePatch;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

/**
 * A new CTabFolder Renderer for Jeeeyul's eclipse themes 2.0
 * 
 * @since 2.0
 */
@SuppressWarnings("all")
public class JeeeyulsTabRenderer extends CTabFolderRenderer {
  @Extension
  private static JTabRendererHelper _jTabRendererHelper = new JTabRendererHelper();
  
  @Extension
  private static SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  private final static int CLOSE_BUTTON_WIDTH = 11;
  
  private final static int TEXT_FLAGS = JeeeyulsTabRenderer._sWTExtensions.operator_or(SWT.DRAW_TRANSPARENT, SWT.DRAW_MNEMONIC);
  
  private final static int MINIMUM_SIZE = (1 << 24);
  
  private JTabSettings settings = new JTabSettings(this);
  
  private CTabFolder tabFolder;
  
  private NinePatch shadowNinePatch;
  
  private PropertyChangeListener settingsListener = new PropertyChangeListener() {
    public void propertyChange(final PropertyChangeEvent it) {
      JeeeyulsTabRenderer.this.handleSettingChange(it);
    }
  };
  
  private Listener windowsRedrawHook = new Listener() {
    public void handleEvent(final Event it) {
      JeeeyulsTabRenderer.this.parent.redraw();
    }
  };
  
  private void handleSettingChange(final PropertyChangeEvent event) {
    String _propertyName = event.getPropertyName();
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_propertyName, "shadow-color")) {
        _matched=true;
      }
      if (!_matched) {
        if (Objects.equal(_propertyName, "shadow-radius")) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (Objects.equal(_propertyName, "border-radius")) {
          _matched=true;
        }
      }
      if (_matched) {
        JeeeyulsTabRenderer._sWTExtensions.<NinePatch>safeDispose(this.shadowNinePatch);
      }
    }
    if (!_matched) {
      if (Objects.equal(_propertyName, "border-colors")) {
        _matched=true;
      }
      if (!_matched) {
        if (Objects.equal(_propertyName, "margins")) {
          _matched=true;
        }
      }
      if (_matched) {
        boolean _or = false;
        Object _oldValue = event.getOldValue();
        boolean _equals = Objects.equal(_oldValue, null);
        if (_equals) {
          _or = true;
        } else {
          Object _newValue = event.getNewValue();
          boolean _equals_1 = Objects.equal(_newValue, null);
          _or = _equals_1;
        }
        if (_or) {
          Shell _shell = this.tabFolder.getShell();
          _shell.layout(true, true);
        }
      }
    }
    this.tabFolder.redraw();
  }
  
  public JeeeyulsTabRenderer(final CTabFolder parent) {
    super(parent);
    this.tabFolder = parent;
    this.settings.addPropertyChangeListener(this.settingsListener);
    boolean _isWindow = JeeeyulsTabRenderer._jTabRendererHelper.isWindow();
    if (_isWindow) {
      this.tabFolder.addListener(SWT.Resize, this.windowsRedrawHook);
    }
  }
  
  protected void dispose() {
    JeeeyulsTabRenderer._sWTExtensions.<NinePatch>safeDispose(this.shadowNinePatch);
    this.settings.removePropertyChangeListener(this.settingsListener);
    boolean _and = false;
    boolean _isWindow = JeeeyulsTabRenderer._jTabRendererHelper.isWindow();
    if (!_isWindow) {
      _and = false;
    } else {
      boolean _isAlive = JeeeyulsTabRenderer._sWTExtensions.isAlive(this.parent);
      _and = _isAlive;
    }
    if (_and) {
      this.parent.removeListener(SWT.Resize, this.windowsRedrawHook);
    }
    super.dispose();
  }
  
  protected Point computeSize(final int part, final int state, final GC gc, final int wHint, final int hHint) {
    Point _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if ((part >= 0)) {
        _matched=true;
        CTabItem item = this.parent.getItem(part);
        int width = 0;
        int height = 0;
        width = (width + this.settings.getTabItemPaddings().x);
        Image _image = item.getImage();
        boolean _notEquals = (!Objects.equal(_image, null));
        if (_notEquals) {
          width = (width + item.getImage().getBounds().width);
          int _tabItemHorizontalSpacing = this.settings.getTabItemHorizontalSpacing();
          int _plus = (width + _tabItemHorizontalSpacing);
          width = _plus;
          Image _image_1 = item.getImage();
          Rectangle _bounds = _image_1.getBounds();
          height = _bounds.height;
        }
        String _text = item.getText();
        String itemText = _text.trim();
        boolean _and = false;
        boolean _and_1 = false;
        boolean _hasFlags = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, JeeeyulsTabRenderer.MINIMUM_SIZE);
        if (!_hasFlags) {
          _and_1 = false;
        } else {
          boolean _isTruncateTabItems = this.settings.isTruncateTabItems();
          _and_1 = _isTruncateTabItems;
        }
        if (!_and_1) {
          _and = false;
        } else {
          int _length = itemText.length();
          int _minimumCharacters = this.settings.getMinimumCharacters();
          boolean _greaterThan = (_length > _minimumCharacters);
          _and = _greaterThan;
        }
        if (_and) {
          int _minimumCharacters_1 = this.settings.getMinimumCharacters();
          String _substring = itemText.substring(0, _minimumCharacters_1);
          String _xifexpression = null;
          boolean _isUseEllipses = this.settings.isUseEllipses();
          if (_isUseEllipses) {
            _xifexpression = "...";
          } else {
            _xifexpression = "";
          }
          String _plus_1 = (_substring + _xifexpression);
          itemText = _plus_1;
        }
        String _trim = itemText.trim();
        int _length_1 = _trim.length();
        boolean _greaterThan_1 = (_length_1 > 0);
        if (_greaterThan_1) {
          Font _font = item.getFont();
          Font _font_1 = this.parent.getFont();
          Font _firstNotNull = JeeeyulsTabRenderer._jTabRendererHelper.<Font>getFirstNotNull(Collections.<Font>unmodifiableList(CollectionLiterals.<Font>newArrayList(_font, _font_1)));
          Point textSize = JeeeyulsTabRenderer._sWTExtensions.computeTextExtent(itemText, _firstNotNull);
          width = (width + textSize.x);
          int _max = Math.max(height, textSize.y);
          height = _max;
        }
        boolean _or = false;
        boolean _showClose = JeeeyulsTabRenderer._jTabRendererHelper.getShowClose(this.parent);
        if (_showClose) {
          _or = true;
        } else {
          boolean _showClose_1 = item.getShowClose();
          _or = _showClose_1;
        }
        if (_or) {
          boolean _or_1 = false;
          boolean _hasFlags_1 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
          if (_hasFlags_1) {
            _or_1 = true;
          } else {
            boolean _showUnselectedClose = JeeeyulsTabRenderer._jTabRendererHelper.getShowUnselectedClose(this.parent);
            _or_1 = _showUnselectedClose;
          }
          if (_or_1) {
            int _tabItemHorizontalSpacing_1 = this.settings.getTabItemHorizontalSpacing();
            int _plus_2 = (width + _tabItemHorizontalSpacing_1);
            width = _plus_2;
            width = (width + JeeeyulsTabRenderer.CLOSE_BUTTON_WIDTH);
          }
        }
        int _tabHeight = this.parent.getTabHeight();
        int _plus_3 = (_tabHeight + 2);
        int _max_1 = Math.max(height, _plus_3);
        height = _max_1;
        Rectangle _tabItemPaddings = this.settings.getTabItemPaddings();
        int _max_2 = Math.max(_tabItemPaddings.width, 0);
        int _plus_4 = (width + _max_2);
        width = _plus_4;
        int _tabSpacing = this.settings.getTabSpacing();
        int _plus_5 = (width + _tabSpacing);
        width = _plus_5;
        return new Point(width, height);
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_HEADER)) {
        _matched=true;
        int _tabHeight_1 = this.parent.getTabHeight();
        Point size = new Point(0, _tabHeight_1);
        int _itemCount = this.parent.getItemCount();
        boolean _equals = (_itemCount == 0);
        if (_equals) {
          Point _textExtent = gc.textExtent("Default");
          int _max_3 = Math.max(_textExtent.y, size.y);
          size.y = _max_3;
        } else {
          int _itemCount_1 = this.parent.getItemCount();
          ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _itemCount_1, true);
          for (final Integer i : _doubleDotLessThan) {
            {
              Point eachSize = this.computeSize((i).intValue(), SWT.NONE, gc, wHint, hHint);
              int _max_4 = Math.max(size.y, eachSize.y);
              size.y = _max_4;
            }
          }
        }
        int _minimumToolBarHeight = JeeeyulsTabRenderer._sWTExtensions.getMinimumToolBarHeight();
        int _plus_6 = (_minimumToolBarHeight + 2);
        int _max_4 = Math.max(size.y, _plus_6);
        size.y = _max_4;
        return size;
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_CLOSE_BUTTON)) {
        _matched=true;
        Point _computeSize = this.computeSize(CTabFolderRenderer.PART_HEADER, 0, gc, (-1), (-1));
        return new Point(11, _computeSize.y);
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_CHEVRON_BUTTON)) {
        _matched=true;
        return new Point(20, 16);
      }
    }
    if (!_matched) {
      _switchResult = super.computeSize(part, state, gc, wHint, hHint);
    }
    return _switchResult;
  }
  
  protected Rectangle computeTrim(final int part, final int state, final int x, final int y, final int width, final int height) {
    Rectangle result = new Rectangle(x, y, width, height);
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_BODY)) {
        _matched=true;
        result.x = ((result.x - this.settings.getMargins().x) - this.settings.getPaddings().x);
        result.width = ((((result.width + this.settings.getMargins().x) + this.settings.getPaddings().x) + this.settings.getPaddings().width) + 
          this.settings.getMargins().width);
        boolean _onBottom = JeeeyulsTabRenderer._jTabRendererHelper.getOnBottom(this.tabFolder);
        if (_onBottom) {
          throw new UnsupportedOperationException();
        }
        int _tabHeight = this.tabFolder.getTabHeight();
        int _minus = (result.y - _tabHeight);
        Rectangle _paddings = this.settings.getPaddings();
        int _minus_1 = (_minus - _paddings.y);
        int _minus_2 = (_minus_1 - 2);
        result.y = _minus_2;
        int _tabHeight_1 = this.tabFolder.getTabHeight();
        int _plus = (result.height + _tabHeight_1);
        Rectangle _paddings_1 = this.settings.getPaddings();
        int _plus_1 = (_plus + _paddings_1.y);
        Rectangle _margins = this.settings.getMargins();
        int _plus_2 = (_plus_1 + _margins.height);
        Rectangle _paddings_2 = this.settings.getPaddings();
        int _plus_3 = (_plus_2 + 
          _paddings_2.height);
        int _plus_4 = (_plus_3 + 2);
        result.height = _plus_4;
        HSB[] _borderColors = this.settings.getBorderColors();
        boolean _notEquals = (!Objects.equal(_borderColors, null));
        if (_notEquals) {
          result.x = (result.x - 1);
          result.width = (result.width + 2);
          result.height = (result.height + 1);
        }
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_BACKGROUND)) {
        _matched=true;
        result.height = (result.height + 10);
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_BORDER)) {
        _matched=true;
        result.x = ((-this.settings.getMargins().x) - 4);
        result.width = ((this.settings.getMargins().x + this.settings.getMargins().width) + ((int) ((this.settings.getBorderRadius() / 1.8) + 0.5)));
        HSB[] _borderColors_1 = this.settings.getBorderColors();
        boolean _notEquals_1 = (!Objects.equal(_borderColors_1, null));
        if (_notEquals_1) {
          int _width = result.width;
          result.width = (_width + 2);
        }
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_HEADER)) {
        _matched=true;
        result.x = (result.x - this.settings.getMargins().x);
        result.width = ((result.width + this.settings.getMargins().x) + this.settings.getMargins().width);
      }
    }
    if (!_matched) {
      if ((((part == CTabFolderRenderer.PART_CHEVRON_BUTTON) || (part == CTabFolderRenderer.PART_MIN_BUTTON)) || (part == CTabFolderRenderer.PART_MAX_BUTTON))) {
        _matched=true;
      }
    }
    if (!_matched) {
      if ((part >= 0)) {
        _matched=true;
        Rectangle _tabItemPaddings = this.settings.getTabItemPaddings();
        int _tabSpacing = this.settings.getTabSpacing();
        int _plus_5 = (_tabItemPaddings.width + _tabSpacing);
        result.x = _plus_5;
      }
    }
    if (!_matched) {
      Rectangle _computeTrim = super.computeTrim(part, state, x, y, width, height);
      result = _computeTrim;
    }
    return result;
  }
  
  protected void draw(final int part, final int state, final Rectangle bounds, final GC gc) {
    try {
      Debug.countFrame();
      this.doDraw(part, state, bounds, gc);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private void doDraw(final int part, final int state, final Rectangle bounds, final GC gc) {
    gc.setAdvanced(true);
    gc.setAntialias(SWT.ON);
    gc.setInterpolation(SWT.HIGH);
    gc.setLineJoin(SWT.JOIN_ROUND);
    gc.setAlpha(255);
    gc.setFillRule(SWT.FILL_WINDING);
    Color _background = this.tabFolder.getBackground();
    gc.setBackground(_background);
    Color _foreground = this.tabFolder.getForeground();
    gc.setForeground(_foreground);
    gc.setLineWidth(1);
    gc.setLineStyle(SWT.LINE_SOLID);
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_HEADER)) {
        _matched=true;
        this.drawTabHeader(part, state, bounds, gc);
        this.updateChevronImage();
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_CLOSE_BUTTON)) {
        _matched=true;
        this.drawCloseButton(part, state, bounds, gc);
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_BORDER)) {
        _matched=true;
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_BODY)) {
        _matched=true;
        this.drawTabBody(part, state, bounds, gc);
      }
    }
    if (!_matched) {
      if (Objects.equal(part, CTabFolderRenderer.PART_CHEVRON_BUTTON)) {
        _matched=true;
        this.drawChevronButton(part, state, bounds, gc);
      }
    }
    if (!_matched) {
      if ((part >= 0)) {
        _matched=true;
        boolean _single = this.tabFolder.getSingle();
        if (_single) {
          return;
        }
        this.drawTabItem(part, state, bounds, gc);
      }
    }
    if (!_matched) {
      super.draw(part, state, bounds, gc);
    }
  }
  
  protected Object drawChevronButton(final int part, final int state, final Rectangle rectangle, final GC gc) {
    return null;
  }
  
  private void updateChevronImage() {
    ToolBar _chevron = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    final Point chevronSize = _chevron.getSize();
    if (((chevronSize.x == 0) || (chevronSize.y == 0))) {
      return;
    }
    ToolBar _chevron_1 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    Object _data = _chevron_1.getData("last-render-color");
    HSB lastColor = ((HSB) _data);
    ToolBar _chevron_2 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    Object _data_1 = _chevron_2.getData("last-render-count");
    Integer lastCount = ((Integer) _data_1);
    CTabItem[] _items = this.parent.getItems();
    final Function1<CTabItem, Boolean> _function = new Function1<CTabItem, Boolean>() {
      public Boolean apply(final CTabItem it) {
        boolean _isShowing = it.isShowing();
        return Boolean.valueOf((_isShowing == false));
      }
    };
    Iterable<CTabItem> _filter = IterableExtensions.<CTabItem>filter(((Iterable<CTabItem>)Conversions.doWrapArray(_items)), _function);
    int _size = IterableExtensions.size(_filter);
    final int count = Math.min(_size, 99);
    boolean _and = false;
    HSB _chevronColor = this.settings.getChevronColor();
    boolean _equals = Objects.equal(lastColor, _chevronColor);
    if (!_equals) {
      _and = false;
    } else {
      _and = ((lastCount).intValue() == count);
    }
    if (_and) {
      return;
    }
    Point size = this.computeSize(CTabFolderRenderer.PART_CHEVRON_BUTTON, SWT.NONE, null, SWT.DEFAULT, SWT.DEFAULT);
    ToolBar _chevron_3 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    _chevron_3.setBackgroundImage(null);
    Display _display = JeeeyulsTabRenderer._sWTExtensions.getDisplay();
    final Image mask = new Image(_display, size.x, size.y);
    final GC mgc = new GC(mask);
    final Procedure1<Path> _function_1 = new Procedure1<Path>() {
      public void apply(final Path it) {
        it.moveTo(0, 0);
        it.lineTo(3, 3);
        it.lineTo(0, 6);
        it.moveTo(3, 0);
        it.lineTo(6, 3);
        it.lineTo(3, 6);
      }
    };
    Path path = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function_1);
    Color _COLOR_BLACK = JeeeyulsTabRenderer._sWTExtensions.COLOR_BLACK();
    mgc.setBackground(_COLOR_BLACK);
    Rectangle _bounds = mask.getBounds();
    JeeeyulsTabRenderer._sWTExtensions.fill(mgc, _bounds);
    Color _COLOR_WHITE = JeeeyulsTabRenderer._sWTExtensions.COLOR_WHITE();
    mgc.setForeground(_COLOR_WHITE);
    JeeeyulsTabRenderer._sWTExtensions.draw(mgc, path);
    Font _font = this.parent.getFont();
    FontData[] _fontData = _font.getFontData();
    FontData fd = IterableExtensions.<FontData>head(((Iterable<FontData>)Conversions.doWrapArray(_fontData)));
    fd.setHeight(((72 * 10) / JeeeyulsTabRenderer._sWTExtensions.getDisplay().getDPI().y));
    Display _display_1 = JeeeyulsTabRenderer._sWTExtensions.getDisplay();
    Font _font_1 = new Font(_display_1, fd);
    Font _autoDispose = JeeeyulsTabRenderer._sWTExtensions.<Font>autoDispose(_font_1);
    mgc.setFont(_autoDispose);
    String _string = Integer.valueOf(count).toString();
    mgc.drawString(_string, 6, 5, true);
    mgc.dispose();
    ImageData _imageData = mask.getImageData();
    HSB _chevronColor_1 = this.settings.getChevronColor();
    ImageData data = ImageDataUtil.convertBrightnessToAlpha(_imageData, _chevronColor_1);
    mask.dispose();
    Display _display_2 = JeeeyulsTabRenderer._sWTExtensions.getDisplay();
    Image _image = new Image(_display_2, data);
    Image itemImage = JeeeyulsTabRenderer._sWTExtensions.<Image>shouldDisposeWith(_image, this.parent);
    Display _display_3 = JeeeyulsTabRenderer._sWTExtensions.getDisplay();
    Image _image_1 = new Image(_display_3, chevronSize.x, chevronSize.y);
    Image toolbarImg = JeeeyulsTabRenderer._sWTExtensions.<Image>shouldDisposeWith(_image_1, this.parent);
    GC tgc = new GC(toolbarImg);
    int _tabHeight = this.parent.getTabHeight();
    int _plus = (_tabHeight + 3);
    Rectangle _rectangle = new Rectangle(0, (-JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent).getBounds().y), chevronSize.x, _plus);
    Color[] _gradientColor = JeeeyulsTabRenderer._jTabRendererHelper.getGradientColor(this.parent);
    int[] _gradientPercents = JeeeyulsTabRenderer._jTabRendererHelper.getGradientPercents(this.parent);
    JeeeyulsTabRenderer._sWTExtensions.fillGradientRectangle(tgc, _rectangle, _gradientColor, _gradientPercents, true);
    tgc.dispose();
    ToolBar _chevron_4 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    Image _backgroundImage = _chevron_4.getBackgroundImage();
    JeeeyulsTabRenderer._sWTExtensions.<Image>safeDispose(_backgroundImage);
    ToolBar _chevron_5 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    _chevron_5.setBackgroundImage(null);
    ToolBar _chevron_6 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    _chevron_6.setBackgroundImage(toolbarImg);
    ToolItem _chevronItem = JeeeyulsTabRenderer._jTabRendererHelper.getChevronItem(this.parent);
    Image _image_2 = _chevronItem.getImage();
    JeeeyulsTabRenderer._sWTExtensions.<Image>safeDispose(_image_2);
    ToolItem _chevronItem_1 = JeeeyulsTabRenderer._jTabRendererHelper.getChevronItem(this.parent);
    _chevronItem_1.setImage(null);
    ToolItem _chevronItem_2 = JeeeyulsTabRenderer._jTabRendererHelper.getChevronItem(this.parent);
    _chevronItem_2.setImage(itemImage);
    ToolBar _chevron_7 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    HSB _chevronColor_2 = this.settings.getChevronColor();
    _chevron_7.setData("last-render-color", _chevronColor_2);
    ToolBar _chevron_8 = JeeeyulsTabRenderer._jTabRendererHelper.getChevron(this.parent);
    _chevron_8.setData("last-render-count", Integer.valueOf(count));
  }
  
  private Rectangle getHeaderArea() {
    Rectangle _xifexpression = null;
    boolean _onTop = JeeeyulsTabRenderer._jTabRendererHelper.getOnTop(this.tabFolder);
    if (_onTop) {
      Rectangle _margins = this.settings.getMargins();
      int _tabHeight = this.tabFolder.getTabHeight();
      int _plus = (_tabHeight + 2);
      _xifexpression = new Rectangle(_margins.x, 0, ((this.tabFolder.getSize().x - this.settings.getMargins().x) - this.settings.getMargins().width), _plus);
    } else {
      Point _size = this.tabFolder.getSize();
      Point _size_1 = this.tabFolder.getSize();
      Point _size_2 = this.tabFolder.getSize();
      Rectangle _rectangle = new Rectangle(0, _size.y, _size_1.x, _size_2.y);
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Rectangle> _function = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Rectangle>() {
        public void apply(final Rectangle it) {
          Rectangle _margins = JeeeyulsTabRenderer.this.settings.getMargins();
          Rectangle _margins_1 = JeeeyulsTabRenderer.this.settings.getMargins();
          JeeeyulsTabRenderer._sWTExtensions.shrink(it, _margins.x, 0, _margins_1.width, 0);
          int _tabHeight = JeeeyulsTabRenderer.this.tabFolder.getTabHeight();
          it.height = _tabHeight;
          int _tabHeight_1 = JeeeyulsTabRenderer.this.tabFolder.getTabHeight();
          int _minus = (-_tabHeight_1);
          Rectangle _margins_2 = JeeeyulsTabRenderer.this.settings.getMargins();
          int _minus_1 = (_minus - _margins_2.height);
          int _minus_2 = (_minus_1 - 2);
          JeeeyulsTabRenderer._sWTExtensions.translate(it, 0, _minus_2);
          JeeeyulsTabRenderer._sWTExtensions.resize(it, 0, 2);
        }
      };
      _xifexpression = ObjectExtensions.<Rectangle>operator_doubleArrow(_rectangle, _function);
    }
    Rectangle headerArea = _xifexpression;
    return headerArea;
  }
  
  protected GC drawTabHeader(final int part, final int state, final Rectangle bounds, final GC gc) {
    GC _xblockexpression = null;
    {
      boolean _onBottom = JeeeyulsTabRenderer._jTabRendererHelper.getOnBottom(this.parent);
      if (_onBottom) {
        throw new UnsupportedOperationException();
      }
      final Rectangle fillArea = this.getHeaderArea();
      HSB[] _borderColors = this.settings.getBorderColors();
      boolean _notEquals = (!Objects.equal(_borderColors, null));
      if (_notEquals) {
        JeeeyulsTabRenderer._sWTExtensions.shrink(fillArea, 1, 0);
      }
      Color[] _gradientColor = JeeeyulsTabRenderer._jTabRendererHelper.getGradientColor(this.tabFolder);
      boolean _notEquals_1 = (!Objects.equal(_gradientColor, null));
      if (_notEquals_1) {
        int _borderRadius = this.settings.getBorderRadius();
        Color[] _gradientColor_1 = JeeeyulsTabRenderer._jTabRendererHelper.getGradientColor(this.tabFolder);
        int[] _gradientPercents = JeeeyulsTabRenderer._jTabRendererHelper.getGradientPercents(this.tabFolder);
        JeeeyulsTabRenderer._sWTExtensions.fillGradientRoundRectangle(gc, fillArea, _borderRadius, JeeeyulsTabRenderer._sWTExtensions.CORNER_TOP, _gradientColor_1, _gradientPercents, true);
      } else {
        Color _background = this.tabFolder.getBackground();
        gc.setBackground(_background);
        int _borderRadius_1 = this.settings.getBorderRadius();
        JeeeyulsTabRenderer._sWTExtensions.fillRoundRectangle(gc, fillArea, _borderRadius_1, JeeeyulsTabRenderer._sWTExtensions.CORNER_TOP);
      }
      Rectangle _headerArea = this.getHeaderArea();
      final Rectangle outlineArea = JeeeyulsTabRenderer._sWTExtensions.getResized(_headerArea, (-1), 0);
      HSB[] _borderColors_1 = this.settings.getBorderColors();
      boolean _notEquals_2 = (!Objects.equal(_borderColors_1, null));
      if (_notEquals_2) {
        HSB[] _borderColors_2 = this.settings.getBorderColors();
        HSB _head = IterableExtensions.<HSB>head(((Iterable<HSB>)Conversions.doWrapArray(_borderColors_2)));
        Color _autoDisposeColor = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(_head);
        gc.setForeground(_autoDisposeColor);
        final Procedure1<Path> _function = new Procedure1<Path>() {
          public void apply(final Path it) {
            Point _bottomLeft = JeeeyulsTabRenderer._sWTExtensions.getBottomLeft(outlineArea);
            JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _bottomLeft);
            Point _topLeft = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(outlineArea);
            int _borderRadius = JeeeyulsTabRenderer.this.settings.getBorderRadius();
            Point _translated = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topLeft, 0, _borderRadius);
            JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated);
            int _borderRadius_1 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
            int _multiply = (_borderRadius_1 * 2);
            Rectangle _newRectangleWithSize = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(_multiply);
            Rectangle _relocateTopLeftWith = JeeeyulsTabRenderer._sWTExtensions.relocateTopLeftWith(_newRectangleWithSize, outlineArea);
            JeeeyulsTabRenderer._sWTExtensions.addArc(it, _relocateTopLeftWith, 180, (-90));
            Point _topRight = JeeeyulsTabRenderer._sWTExtensions.getTopRight(outlineArea);
            int _borderRadius_2 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
            int _minus = (-_borderRadius_2);
            Point _translated_1 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topRight, _minus, 0);
            JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated_1);
            int _borderRadius_3 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
            int _multiply_1 = (_borderRadius_3 * 2);
            Rectangle _newRectangleWithSize_1 = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(_multiply_1);
            Rectangle _relocateTopRightWith = JeeeyulsTabRenderer._sWTExtensions.relocateTopRightWith(_newRectangleWithSize_1, outlineArea);
            JeeeyulsTabRenderer._sWTExtensions.addArc(it, _relocateTopRightWith, 90, (-90));
            Point _bottomRight = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(outlineArea);
            JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _bottomRight);
          }
        };
        Path path = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function);
        HSB[] _borderColors_3 = this.settings.getBorderColors();
        Color[] _autoDisposeColors = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_borderColors_3);
        int[] _borderPercents = this.settings.getBorderPercents();
        JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, path, _autoDisposeColors, _borderPercents, true);
      }
      GC _xifexpression = null;
      int _tabSpacing = this.settings.getTabSpacing();
      boolean _equals = (_tabSpacing == (-1));
      if (_equals) {
        GC _xblockexpression_1 = null;
        {
          CTabItem lastItem = JeeeyulsTabRenderer._jTabRendererHelper.getLastVisibleItem(this.parent);
          GC _xifexpression_1 = null;
          boolean _notEquals_3 = (!Objects.equal(lastItem, null));
          if (_notEquals_3) {
            GC _xblockexpression_2 = null;
            {
              final Rectangle itemBounds = lastItem.getBounds();
              final Procedure1<Path> _function_1 = new Procedure1<Path>() {
                public void apply(final Path it) {
                  Point _bottomRight = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(itemBounds);
                  Point _translated = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_bottomRight, 0, (-1));
                  JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _translated);
                  Point _topRight = JeeeyulsTabRenderer._sWTExtensions.getTopRight(itemBounds);
                  int _borderRadius = JeeeyulsTabRenderer.this.settings.getBorderRadius();
                  Point _translated_1 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topRight, 0, _borderRadius);
                  JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated_1);
                  int _borderRadius_1 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
                  int _multiply = (_borderRadius_1 * 2);
                  Rectangle _newRectangleWithSize = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(_multiply);
                  Point _topRight_1 = JeeeyulsTabRenderer._sWTExtensions.getTopRight(itemBounds);
                  Rectangle _relocateTopRightWith = JeeeyulsTabRenderer._sWTExtensions.relocateTopRightWith(_newRectangleWithSize, _topRight_1);
                  JeeeyulsTabRenderer._sWTExtensions.addArc(it, _relocateTopRightWith, 0, 
                    90);
                }
              };
              Path path_1 = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function_1);
              int _state = JeeeyulsTabRenderer._jTabRendererHelper.getState(lastItem);
              HSB[] _itemFillFor = JeeeyulsTabRenderer._jTabRendererHelper.getItemFillFor(this.settings, _state);
              boolean _notEquals_4 = (!Objects.equal(_itemFillFor, null));
              if (_notEquals_4) {
                int _state_1 = JeeeyulsTabRenderer._jTabRendererHelper.getState(lastItem);
                HSB[] _itemFillFor_1 = JeeeyulsTabRenderer._jTabRendererHelper.getItemFillFor(this.settings, _state_1);
                Color[] _autoDisposeColors_1 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_itemFillFor_1);
                int _state_2 = JeeeyulsTabRenderer._jTabRendererHelper.getState(lastItem);
                int[] _itemFillPercentsFor = JeeeyulsTabRenderer._jTabRendererHelper.getItemFillPercentsFor(this.settings, _state_2);
                JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, path_1, _autoDisposeColors_1, _itemFillPercentsFor, true);
              }
              GC _xifexpression_2 = null;
              int _state_3 = JeeeyulsTabRenderer._jTabRendererHelper.getState(lastItem);
              HSB[] _borderColorsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderColorsFor(this.settings, _state_3);
              boolean _notEquals_5 = (!Objects.equal(_borderColorsFor, null));
              if (_notEquals_5) {
                int _state_4 = JeeeyulsTabRenderer._jTabRendererHelper.getState(lastItem);
                HSB[] _borderColorsFor_1 = JeeeyulsTabRenderer._jTabRendererHelper.getBorderColorsFor(this.settings, _state_4);
                Color[] _autoDisposeColors_2 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_borderColorsFor_1);
                int _state_5 = JeeeyulsTabRenderer._jTabRendererHelper.getState(lastItem);
                int[] _borderPercentsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderPercentsFor(this.settings, _state_5);
                _xifexpression_2 = JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, path_1, _autoDisposeColors_2, _borderPercentsFor, true);
              }
              _xblockexpression_2 = _xifexpression_2;
            }
            _xifexpression_1 = _xblockexpression_2;
          }
          _xblockexpression_1 = _xifexpression_1;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected GC drawTabBody(final int part, final int state, final Rectangle bounds, final GC gc) {
    GC _xblockexpression = null;
    {
      boolean _onBottom = JeeeyulsTabRenderer._jTabRendererHelper.getOnBottom(this.parent);
      if (_onBottom) {
        throw new UnsupportedOperationException();
      }
      Composite _parent = this.tabFolder.getParent();
      Color _background = _parent.getBackground();
      gc.setBackground(_background);
      JeeeyulsTabRenderer._sWTExtensions.fill(gc, bounds);
      HSB _shadowColor = this.settings.getShadowColor();
      boolean _notEquals = (!Objects.equal(_shadowColor, null));
      if (_notEquals) {
        this.drawShadow(part, state, bounds, gc);
      }
      Color _xifexpression = null;
      int _itemCount = this.parent.getItemCount();
      boolean _greaterThan = (_itemCount > 0);
      if (_greaterThan) {
        Color[] _selectionGradientColor = JeeeyulsTabRenderer._jTabRendererHelper.getSelectionGradientColor(this.tabFolder);
        Color _last = null;
        if (((Iterable<Color>)Conversions.doWrapArray(_selectionGradientColor))!=null) {
          _last=IterableExtensions.<Color>last(((Iterable<Color>)Conversions.doWrapArray(_selectionGradientColor)));
        }
        Color _selectionBackground = this.tabFolder.getSelectionBackground();
        final Function1<Color, Boolean> _function = new Function1<Color, Boolean>() {
          public Boolean apply(final Color it) {
            return Boolean.valueOf((!Objects.equal(it, null)));
          }
        };
        _xifexpression = IterableExtensions.<Color>findFirst(Collections.<Color>unmodifiableList(CollectionLiterals.<Color>newArrayList(_last, _selectionBackground)), _function);
      } else {
        Color[] _gradientColor = JeeeyulsTabRenderer._jTabRendererHelper.getGradientColor(this.tabFolder);
        Color _last_1 = null;
        if (((Iterable<Color>)Conversions.doWrapArray(_gradientColor))!=null) {
          _last_1=IterableExtensions.<Color>last(((Iterable<Color>)Conversions.doWrapArray(_gradientColor)));
        }
        Color _background_1 = this.tabFolder.getBackground();
        final Function1<Color, Boolean> _function_1 = new Function1<Color, Boolean>() {
          public Boolean apply(final Color it) {
            return Boolean.valueOf((!Objects.equal(it, null)));
          }
        };
        _xifexpression = IterableExtensions.<Color>findFirst(Collections.<Color>unmodifiableList(CollectionLiterals.<Color>newArrayList(_last_1, _background_1)), _function_1);
      }
      gc.setBackground(_xifexpression);
      Rectangle fillArea = this.tabArea();
      Rectangle _headerArea = this.getHeaderArea();
      Point _bottom = JeeeyulsTabRenderer._sWTExtensions.getBottom(_headerArea);
      JeeeyulsTabRenderer._sWTExtensions.setTop(fillArea, _bottom.y);
      int _borderRadius = this.settings.getBorderRadius();
      JeeeyulsTabRenderer._sWTExtensions.fillRoundRectangle(gc, fillArea, _borderRadius, JeeeyulsTabRenderer._sWTExtensions.CORNER_BOTTOM);
      Rectangle _paddings = this.settings.getPaddings();
      JeeeyulsTabRenderer._sWTExtensions.shrink(fillArea, _paddings);
      HSB[] _borderColors = this.settings.getBorderColors();
      boolean _notEquals_1 = (!Objects.equal(_borderColors, null));
      if (_notEquals_1) {
        JeeeyulsTabRenderer._sWTExtensions.shrink(fillArea, 1, 0, 1, 1);
      }
      Color _background_2 = this.parent.getBackground();
      gc.setBackground(_background_2);
      JeeeyulsTabRenderer._sWTExtensions.fill(gc, fillArea);
      GC _xifexpression_1 = null;
      boolean _and = false;
      boolean _and_1 = false;
      int _borderWidth = this.settings.getBorderWidth();
      boolean _greaterThan_1 = (_borderWidth > 0);
      if (!_greaterThan_1) {
        _and_1 = false;
      } else {
        HSB[] _borderColors_1 = this.settings.getBorderColors();
        boolean _notEquals_2 = (!Objects.equal(_borderColors_1, null));
        _and_1 = _notEquals_2;
      }
      if (!_and_1) {
        _and = false;
      } else {
        int[] _borderPercents = this.settings.getBorderPercents();
        boolean _notEquals_3 = (!Objects.equal(_borderPercents, null));
        _and = _notEquals_3;
      }
      if (_and) {
        GC _xblockexpression_1 = null;
        {
          Rectangle _tabArea = this.tabArea();
          final Rectangle offset = JeeeyulsTabRenderer._sWTExtensions.getResized(_tabArea, (-1), (-1));
          int _borderWidth_1 = this.settings.getBorderWidth();
          gc.setLineWidth(_borderWidth_1);
          final Procedure1<Path> _function_2 = new Procedure1<Path>() {
            public void apply(final Path it) {
              int _borderRadius = JeeeyulsTabRenderer.this.settings.getBorderRadius();
              int _multiply = (_borderRadius * 2);
              Rectangle corner = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(_multiply);
              Rectangle _headerArea = JeeeyulsTabRenderer.this.getHeaderArea();
              Point _bottomLeft = JeeeyulsTabRenderer._sWTExtensions.getBottomLeft(_headerArea);
              JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _bottomLeft);
              JeeeyulsTabRenderer._sWTExtensions.relocateBottomLeftWith(corner, offset);
              Point _left = JeeeyulsTabRenderer._sWTExtensions.getLeft(corner);
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _left);
              JeeeyulsTabRenderer._sWTExtensions.addArc(it, corner, 180, 90);
              JeeeyulsTabRenderer._sWTExtensions.relocateBottomRightWith(corner, offset);
              Point _bottom = JeeeyulsTabRenderer._sWTExtensions.getBottom(corner);
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _bottom);
              JeeeyulsTabRenderer._sWTExtensions.addArc(it, corner, 270, 90);
              Rectangle _headerArea_1 = JeeeyulsTabRenderer.this.getHeaderArea();
              Point _bottomRight = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(_headerArea_1);
              Point _translated = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_bottomRight, (-1), 0);
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated);
            }
          };
          final Path bodyPath = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function_2);
          HSB[] _borderColors_2 = this.settings.getBorderColors();
          HSB _last_2 = IterableExtensions.<HSB>last(((Iterable<HSB>)Conversions.doWrapArray(_borderColors_2)));
          Color _autoDisposeColor = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(_last_2);
          gc.setForeground(_autoDisposeColor);
          JeeeyulsTabRenderer._sWTExtensions.draw(gc, bodyPath);
          _xblockexpression_1 = JeeeyulsTabRenderer._sWTExtensions.draw(gc, bodyPath);
        }
        _xifexpression_1 = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression_1;
    }
    return _xblockexpression;
  }
  
  protected GC drawCloseButton(final int part, final int state, final Rectangle bounds, final GC gc) {
    GC _xblockexpression = null;
    {
      Rectangle _newRectangleWithSize = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(bounds.width);
      Rectangle _relocateCenterWith = JeeeyulsTabRenderer._sWTExtensions.relocateCenterWith(_newRectangleWithSize, bounds);
      final Rectangle box = JeeeyulsTabRenderer._sWTExtensions.shrink(_relocateCenterWith, 2);
      VerticalAlignment _closeButtonAlignment = this.settings.getCloseButtonAlignment();
      boolean _equals = Objects.equal(_closeButtonAlignment, VerticalAlignment.BASE_LINE);
      if (_equals) {
        FontMetrics _fontMetrics = gc.getFontMetrics();
        int _height = _fontMetrics.getHeight();
        int _minus = (bounds.height - _height);
        int _divide = (_minus / 2);
        FontMetrics _fontMetrics_1 = gc.getFontMetrics();
        int _leading = _fontMetrics_1.getLeading();
        int _plus = (_divide + _leading);
        FontMetrics _fontMetrics_2 = gc.getFontMetrics();
        int _ascent = _fontMetrics_2.getAscent();
        int _plus_1 = (_plus + _ascent);
        int _minus_1 = (_plus_1 - box.height);
        box.y = _minus_1;
        int _y = box.y;
        int _switchResult = (int) 0;
        int _closeButtonLineWidth = this.settings.getCloseButtonLineWidth();
        switch (_closeButtonLineWidth) {
          case 1:
            _switchResult = 1;
            break;
          case 2:
            _switchResult = 1;
            break;
          case 3:
            _switchResult = 0;
            break;
          default:
            _switchResult = 1;
            break;
        }
        box.y = (_y + _switchResult);
      }
      boolean _isDebuggingGUI = Debug.isDebuggingGUI();
      if (_isDebuggingGUI) {
        Color _COLOR_MAGENTA = JeeeyulsTabRenderer._sWTExtensions.COLOR_MAGENTA();
        gc.setBackground(_COLOR_MAGENTA);
        JeeeyulsTabRenderer._sWTExtensions.fill(gc, box);
      }
      final Procedure1<Path> _function = new Procedure1<Path>() {
        public void apply(final Path it) {
          Point _topLeft = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(box);
          JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _topLeft);
          Point _bottomRight = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(box);
          JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _bottomRight);
          Point _topRight = JeeeyulsTabRenderer._sWTExtensions.getTopRight(box);
          JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _topRight);
          Point _bottomLeft = JeeeyulsTabRenderer._sWTExtensions.getBottomLeft(box);
          JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _bottomLeft);
        }
      };
      Path path = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function);
      HSB _switchResult_1 = null;
      boolean _matched = false;
      if (!_matched) {
        boolean _hasFlags = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.HOT);
        if (_hasFlags) {
          _matched=true;
          HSB _closeButtonHotColor = this.settings.getCloseButtonHotColor();
          HSB _closeButtonColor = this.settings.getCloseButtonColor();
          _switchResult_1 = JeeeyulsTabRenderer._jTabRendererHelper.<HSB>getFirstNotNull(Collections.<HSB>unmodifiableList(CollectionLiterals.<HSB>newArrayList(_closeButtonHotColor, _closeButtonColor, HSB.BLACK)));
        }
      }
      if (!_matched) {
        boolean _hasFlags_1 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
        if (_hasFlags_1) {
          _matched=true;
          HSB _closeButtonActiveColor = this.settings.getCloseButtonActiveColor();
          HSB _closeButtonColor_1 = this.settings.getCloseButtonColor();
          _switchResult_1 = JeeeyulsTabRenderer._jTabRendererHelper.<HSB>getFirstNotNull(Collections.<HSB>unmodifiableList(CollectionLiterals.<HSB>newArrayList(_closeButtonActiveColor, _closeButtonColor_1, HSB.BLACK)));
        }
      }
      if (!_matched) {
        HSB _closeButtonColor_2 = this.settings.getCloseButtonColor();
        _switchResult_1 = JeeeyulsTabRenderer._jTabRendererHelper.<HSB>getFirstNotNull(Collections.<HSB>unmodifiableList(CollectionLiterals.<HSB>newArrayList(_closeButtonColor_2, HSB.BLACK)));
      }
      HSB color = _switchResult_1;
      int _closeButtonLineWidth_1 = this.settings.getCloseButtonLineWidth();
      int _max = Math.max(_closeButtonLineWidth_1, 1);
      gc.setLineWidth(_max);
      Color _autoDisposeColor = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(color);
      gc.setForeground(_autoDisposeColor);
      _xblockexpression = JeeeyulsTabRenderer._sWTExtensions.draw(gc, path);
    }
    return _xblockexpression;
  }
  
  protected int drawTabItem(final int part, final int state, final Rectangle bounds, final GC gc) {
    int _xblockexpression = (int) 0;
    {
      final CTabItem item = this.tabFolder.getItem(part);
      Rectangle _xifexpression = null;
      boolean _onBottom = JeeeyulsTabRenderer._jTabRendererHelper.getOnBottom(this.tabFolder);
      if (_onBottom) {
        throw new UnsupportedOperationException();
      } else {
        Rectangle _bounds = item.getBounds();
        int _tabSpacing = this.settings.getTabSpacing();
        int _max = Math.max(_tabSpacing, 0);
        int _minus = (-_max);
        _xifexpression = JeeeyulsTabRenderer._sWTExtensions.getResized(_bounds, _minus, 0);
      }
      final Rectangle itemBounds = _xifexpression;
      int _tabSpacing_1 = this.settings.getTabSpacing();
      boolean _equals = (_tabSpacing_1 == (-1));
      if (_equals) {
        JeeeyulsTabRenderer._sWTExtensions.resize(itemBounds, 1, 0);
      }
      this.drawTabItemBackground(part, state, itemBounds, gc);
      Rectangle _xifexpression_1 = null;
      Image _image = item.getImage();
      boolean _notEquals = (!Objects.equal(_image, null));
      if (_notEquals) {
        Image _image_1 = item.getImage();
        Rectangle _bounds_1 = _image_1.getBounds();
        Rectangle _bounds_2 = item.getBounds();
        Rectangle _relocateLeftWith = JeeeyulsTabRenderer._sWTExtensions.relocateLeftWith(_bounds_1, _bounds_2);
        Rectangle _tabItemPaddings = this.settings.getTabItemPaddings();
        _xifexpression_1 = JeeeyulsTabRenderer._sWTExtensions.translate(_relocateLeftWith, _tabItemPaddings.x, 0);
      } else {
        _xifexpression_1 = new Rectangle((itemBounds.x + this.settings.getTabItemPaddings().x), 0, 0, itemBounds.height);
      }
      Rectangle iconArea = _xifexpression_1;
      Image _image_2 = item.getImage();
      boolean _notEquals_1 = (!Objects.equal(_image_2, null));
      if (_notEquals_1) {
        CSSClasses _styleClasses = CSSClasses.getStyleClasses(item);
        final boolean isBusy = _styleClasses.contains("busy");
        if (isBusy) {
          Image _image_3 = item.getImage();
          Point _topLeft = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(iconArea);
          JeeeyulsTabRenderer._sWTExtensions.drawImage(gc, _image_3, _topLeft);
          final Image watingIcon = CoreImages.getImage(CoreImages.WAITING);
          Rectangle _bounds_3 = watingIcon.getBounds();
          final Point iconSize = JeeeyulsTabRenderer._sWTExtensions.getSize(_bounds_3);
          Point _bottomRight = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(iconArea);
          Point _negated = JeeeyulsTabRenderer._sWTExtensions.getNegated(iconSize);
          Point _translated = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_bottomRight, _negated);
          Point _translated_1 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_translated, 1, 1);
          JeeeyulsTabRenderer._sWTExtensions.drawImage(gc, watingIcon, _translated_1);
        } else {
          Image _image_4 = item.getImage();
          Point _topLeft_1 = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(iconArea);
          JeeeyulsTabRenderer._sWTExtensions.drawImage(gc, _image_4, _topLeft_1);
        }
      }
      Font _xifexpression_2 = null;
      Font _font = item.getFont();
      boolean _notEquals_2 = (!Objects.equal(_font, null));
      if (_notEquals_2) {
        _xifexpression_2 = item.getFont();
      } else {
        _xifexpression_2 = this.tabFolder.getFont();
      }
      gc.setFont(_xifexpression_2);
      boolean closeVisible = false;
      boolean _and = false;
      boolean _or = false;
      boolean _showClose = JeeeyulsTabRenderer._jTabRendererHelper.getShowClose(this.tabFolder);
      if (_showClose) {
        _or = true;
      } else {
        boolean _showClose_1 = item.getShowClose();
        _or = _showClose_1;
      }
      if (!_or) {
        _and = false;
      } else {
        _and = (JeeeyulsTabRenderer._jTabRendererHelper.getCloseRect(item).width > 0);
      }
      if (_and) {
        boolean _or_1 = false;
        boolean _hasFlags = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
        if (_hasFlags) {
          _or_1 = true;
        } else {
          boolean _hasFlags_1 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.HOT);
          _or_1 = _hasFlags_1;
        }
        if (_or_1) {
          Rectangle _closeRect = JeeeyulsTabRenderer._jTabRendererHelper.getCloseRect(item);
          final Procedure1<GC> _function = new Procedure1<GC>() {
            public void apply(final GC it) {
              int _closeImageState = JeeeyulsTabRenderer._jTabRendererHelper.getCloseImageState(item);
              Rectangle _closeRect = JeeeyulsTabRenderer._jTabRendererHelper.getCloseRect(item);
              JeeeyulsTabRenderer.this.draw(CTabFolderRenderer.PART_CLOSE_BUTTON, _closeImageState, _closeRect, gc);
            }
          };
          JeeeyulsTabRenderer._sWTExtensions.withClip(gc, _closeRect, _function);
          closeVisible = true;
        }
      }
      gc.setLineWidth(1);
      String _text = item.getText();
      Font _font_1 = gc.getFont();
      final Point textSize = JeeeyulsTabRenderer._sWTExtensions.computeTextExtent(_text, _font_1);
      Rectangle _newRectangleWithSize = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(textSize);
      Point _right = JeeeyulsTabRenderer._sWTExtensions.getRight(iconArea);
      final Rectangle textArea = JeeeyulsTabRenderer._sWTExtensions.relocateLeftWith(_newRectangleWithSize, _right);
      Image _image_5 = item.getImage();
      boolean _notEquals_3 = (!Objects.equal(_image_5, null));
      if (_notEquals_3) {
        int _tabItemHorizontalSpacing = this.settings.getTabItemHorizontalSpacing();
        JeeeyulsTabRenderer._sWTExtensions.translate(textArea, _tabItemHorizontalSpacing, 0);
      }
      boolean _and_1 = false;
      boolean _and_2 = false;
      if (!closeVisible) {
        _and_2 = false;
      } else {
        Rectangle _closeRect_1 = JeeeyulsTabRenderer._jTabRendererHelper.getCloseRect(item);
        boolean _notEquals_4 = (!Objects.equal(_closeRect_1, null));
        _and_2 = _notEquals_4;
      }
      if (!_and_2) {
        _and_1 = false;
      } else {
        _and_1 = (JeeeyulsTabRenderer._jTabRendererHelper.getCloseRect(item).width > 0);
      }
      if (_and_1) {
        Rectangle _closeRect_2 = JeeeyulsTabRenderer._jTabRendererHelper.getCloseRect(item);
        int _tabItemHorizontalSpacing_1 = this.settings.getTabItemHorizontalSpacing();
        int _minus_1 = (_closeRect_2.x - _tabItemHorizontalSpacing_1);
        JeeeyulsTabRenderer._sWTExtensions.setRight(textArea, _minus_1);
      } else {
        JeeeyulsTabRenderer._sWTExtensions.setRight(textArea, (JeeeyulsTabRenderer._sWTExtensions.getRight(itemBounds).x - this.settings.getTabItemPaddings().width));
      }
      boolean _isDebuggingGUI = Debug.isDebuggingGUI();
      if (_isDebuggingGUI) {
        Color _COLOR_MAGENTA = JeeeyulsTabRenderer._sWTExtensions.COLOR_MAGENTA();
        gc.setForeground(_COLOR_MAGENTA);
        gc.setLineStyle(SWT.LINE_DASH);
        gc.setLineDash(new int[] { 2, 2 });
        gc.drawRectangle(textArea);
        gc.setLineStyle(SWT.LINE_SOLID);
        gc.setLineWidth(1);
        Color _COLOR_RED = JeeeyulsTabRenderer._sWTExtensions.COLOR_RED();
        gc.setForeground(_COLOR_RED);
        Point _left = JeeeyulsTabRenderer._sWTExtensions.getLeft(textArea);
        Point _top = JeeeyulsTabRenderer._sWTExtensions.getTop(textArea);
        FontMetrics _fontMetrics = gc.getFontMetrics();
        int _leading = _fontMetrics.getLeading();
        int _plus = (_top.y + _leading);
        FontMetrics _fontMetrics_1 = gc.getFontMetrics();
        int _ascent = _fontMetrics_1.getAscent();
        int _plus_1 = (_plus + _ascent);
        Point _right_1 = JeeeyulsTabRenderer._sWTExtensions.getRight(textArea);
        Point _top_1 = JeeeyulsTabRenderer._sWTExtensions.getTop(textArea);
        FontMetrics _fontMetrics_2 = gc.getFontMetrics();
        int _leading_1 = _fontMetrics_2.getLeading();
        int _plus_2 = (_top_1.y + _leading_1);
        FontMetrics _fontMetrics_3 = gc.getFontMetrics();
        int _ascent_1 = _fontMetrics_3.getAscent();
        int _plus_3 = (_plus_2 + _ascent_1);
        gc.drawLine(_left.x, _plus_1, _right_1.x, _plus_3);
        Color _COLOR_BLUE = JeeeyulsTabRenderer._sWTExtensions.COLOR_BLUE();
        gc.setForeground(_COLOR_BLUE);
        Point _left_1 = JeeeyulsTabRenderer._sWTExtensions.getLeft(textArea);
        Point _top_2 = JeeeyulsTabRenderer._sWTExtensions.getTop(textArea);
        FontMetrics _fontMetrics_4 = gc.getFontMetrics();
        int _leading_2 = _fontMetrics_4.getLeading();
        int _plus_4 = (_top_2.y + _leading_2);
        FontMetrics _fontMetrics_5 = gc.getFontMetrics();
        int _ascent_2 = _fontMetrics_5.getAscent();
        int _plus_5 = (_plus_4 + _ascent_2);
        FontMetrics _fontMetrics_6 = gc.getFontMetrics();
        int _descent = _fontMetrics_6.getDescent();
        int _plus_6 = (_plus_5 + _descent);
        Point _right_2 = JeeeyulsTabRenderer._sWTExtensions.getRight(textArea);
        Point _top_3 = JeeeyulsTabRenderer._sWTExtensions.getTop(textArea);
        FontMetrics _fontMetrics_7 = gc.getFontMetrics();
        int _leading_3 = _fontMetrics_7.getLeading();
        int _plus_7 = (_top_3.y + _leading_3);
        FontMetrics _fontMetrics_8 = gc.getFontMetrics();
        int _ascent_3 = _fontMetrics_8.getAscent();
        int _plus_8 = (_plus_7 + _ascent_3);
        FontMetrics _fontMetrics_9 = gc.getFontMetrics();
        int _descent_1 = _fontMetrics_9.getDescent();
        int _plus_9 = (_plus_8 + _descent_1);
        gc.drawLine(_left_1.x, _plus_6, 
          _right_2.x, _plus_9);
      }
      String _xifexpression_3 = null;
      if ((textSize.x > textArea.width)) {
        String _text_1 = item.getText();
        _xifexpression_3 = this.shortenText(gc, _text_1, textArea.width);
      } else {
        _xifexpression_3 = item.getText();
      }
      String text = _xifexpression_3;
      boolean _and_3 = false;
      boolean _isFocusControl = this.parent.isFocusControl();
      if (!_isFocusControl) {
        _and_3 = false;
      } else {
        boolean _hasFlags_2 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
        _and_3 = _hasFlags_2;
      }
      if (_and_3) {
        gc.setAlpha(90);
        HSB _textColorFor = JeeeyulsTabRenderer._jTabRendererHelper.getTextColorFor(this.settings, state);
        Color _autoDisposeColor = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(_textColorFor);
        gc.setForeground(_autoDisposeColor);
        Point _topLeft_2 = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(textArea);
        FontMetrics _fontMetrics_10 = gc.getFontMetrics();
        int _ascent_4 = _fontMetrics_10.getAscent();
        int _plus_10 = (_ascent_4 + 1);
        Point lineFrom = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topLeft_2, 0, _plus_10);
        String _trim = text.trim();
        Point _textExtent = gc.textExtent(_trim, JeeeyulsTabRenderer.TEXT_FLAGS);
        Point lineTo = JeeeyulsTabRenderer._sWTExtensions.getTranslated(lineFrom, _textExtent.x, 0);
        JeeeyulsTabRenderer._sWTExtensions.draw(gc, lineFrom, lineTo);
        gc.setAlpha(255);
      }
      final HSB textShadowColor = JeeeyulsTabRenderer._jTabRendererHelper.getTextShadowColorFor(this.settings, state);
      final Point textShadowPosition = JeeeyulsTabRenderer._jTabRendererHelper.getTextShadowPositionFor(this.settings, state);
      boolean _and_4 = false;
      boolean _and_5 = false;
      boolean _notEquals_5 = (!Objects.equal(textShadowColor, null));
      if (!_notEquals_5) {
        _and_5 = false;
      } else {
        boolean _notEquals_6 = (!Objects.equal(textShadowPosition, null));
        _and_5 = _notEquals_6;
      }
      if (!_and_5) {
        _and_4 = false;
      } else {
        boolean _isEmpty = JeeeyulsTabRenderer._sWTExtensions.isEmpty(textShadowPosition);
        boolean _not = (!_isEmpty);
        _and_4 = _not;
      }
      if (_and_4) {
        Point shadowPosition = JeeeyulsTabRenderer._jTabRendererHelper.getTextShadowPositionFor(this.settings, state);
        HSB _textShadowColorFor = JeeeyulsTabRenderer._jTabRendererHelper.getTextShadowColorFor(this.settings, state);
        Color _autoDisposeColor_1 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(_textShadowColorFor);
        gc.setForeground(_autoDisposeColor_1);
        Point _topLeft_3 = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(textArea);
        Point delta = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topLeft_3, shadowPosition);
        gc.drawText(text, delta.x, delta.y, JeeeyulsTabRenderer.TEXT_FLAGS);
      }
      HSB _textColorFor_1 = JeeeyulsTabRenderer._jTabRendererHelper.getTextColorFor(this.settings, state);
      Color _autoDisposeColor_2 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(_textColorFor_1);
      gc.setForeground(_autoDisposeColor_2);
      gc.drawText(text, textArea.x, textArea.y, JeeeyulsTabRenderer.TEXT_FLAGS);
      this.drawTabItemBorder(part, state, itemBounds, gc);
      int _tabSpacing_2 = this.settings.getTabSpacing();
      boolean _equals_1 = (_tabSpacing_2 == (-1));
      if (_equals_1) {
        CTabItem _xifexpression_4 = null;
        if ((part > 0)) {
          _xifexpression_4 = this.parent.getItem((part - 1));
        } else {
          _xifexpression_4 = null;
        }
        CTabItem prevItem = _xifexpression_4;
        boolean _and_6 = false;
        boolean _and_7 = false;
        boolean _and_8 = false;
        boolean _hasFlags_3 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
        boolean _not_1 = (!_hasFlags_3);
        if (!_not_1) {
          _and_8 = false;
        } else {
          boolean _notEquals_7 = (!Objects.equal(prevItem, null));
          _and_8 = _notEquals_7;
        }
        if (!_and_8) {
          _and_7 = false;
        } else {
          int _state = JeeeyulsTabRenderer._jTabRendererHelper.getState(prevItem);
          boolean _hasFlags_4 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(_state, SWT.SELECTED);
          boolean _not_2 = (!_hasFlags_4);
          _and_7 = _not_2;
        }
        if (!_and_7) {
          _and_6 = false;
        } else {
          int _state_1 = JeeeyulsTabRenderer._jTabRendererHelper.getState(prevItem);
          boolean _hasFlags_5 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(_state_1, SWT.HOT);
          _and_6 = _hasFlags_5;
        }
        boolean hasToDrawLeftBorder = _and_6;
        if (hasToDrawLeftBorder) {
          final Procedure1<Path> _function_1 = new Procedure1<Path>() {
            public void apply(final Path it) {
              Point _bottomLeft = JeeeyulsTabRenderer._sWTExtensions.getBottomLeft(itemBounds);
              JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _bottomLeft);
              Point _topLeft = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(itemBounds);
              int _borderRadius = JeeeyulsTabRenderer.this.settings.getBorderRadius();
              Point _translated = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topLeft, 0, _borderRadius);
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated);
              int _borderRadius_1 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
              int _multiply = (_borderRadius_1 * 2);
              Rectangle _newRectangleWithSize = JeeeyulsTabRenderer._sWTExtensions.newRectangleWithSize(_multiply);
              Point _topLeft_1 = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(itemBounds);
              Rectangle _relocateTopRightWith = JeeeyulsTabRenderer._sWTExtensions.relocateTopRightWith(_newRectangleWithSize, _topLeft_1);
              JeeeyulsTabRenderer._sWTExtensions.addArc(it, _relocateTopRightWith, 0, 
                90);
            }
          };
          Path path = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function_1);
          HSB[] _hoverBackgroundColors = this.settings.getHoverBackgroundColors();
          boolean _notEquals_8 = (!Objects.equal(_hoverBackgroundColors, null));
          if (_notEquals_8) {
            HSB[] _hoverBackgroundColors_1 = this.settings.getHoverBackgroundColors();
            Color[] _autoDisposeColors = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_hoverBackgroundColors_1);
            int[] _hoverBackgroundPercents = this.settings.getHoverBackgroundPercents();
            JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, path, _autoDisposeColors, _hoverBackgroundPercents, true);
          }
          HSB[] _hoverBorderColors = this.settings.getHoverBorderColors();
          boolean _notEquals_9 = (!Objects.equal(_hoverBorderColors, null));
          if (_notEquals_9) {
            HSB[] _borderColorsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderColorsFor(this.settings, SWT.HOT);
            Color[] _autoDisposeColors_1 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_borderColorsFor);
            int[] _borderPercentsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderPercentsFor(this.settings, SWT.HOT);
            JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, path, _autoDisposeColors_1, _borderPercentsFor, true);
          }
        }
        boolean _and_9 = false;
        boolean _hasFlags_6 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
        boolean _not_3 = (!_hasFlags_6);
        if (!_not_3) {
          _and_9 = false;
        } else {
          boolean _or_2 = false;
          boolean _hasFlags_7 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.HOT);
          if (_hasFlags_7) {
            _or_2 = true;
          } else {
            int _lastKnownState = JeeeyulsTabRenderer._jTabRendererHelper.lastKnownState(item);
            boolean _hasFlags_8 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(_lastKnownState, SWT.HOT);
            _or_2 = _hasFlags_8;
          }
          _and_9 = _or_2;
        }
        boolean hasToFix = _and_9;
        if (hasToFix) {
          this.parent.redraw(((itemBounds.x + itemBounds.width) - 1), itemBounds.y, 1, itemBounds.height, false);
          this.parent.update();
        }
      }
      HSB[] _borderColors = this.settings.getBorderColors();
      boolean _notEquals_10 = (!Objects.equal(_borderColors, null));
      if (_notEquals_10) {
        CTabItem _firstVisibleItem = JeeeyulsTabRenderer._jTabRendererHelper.getFirstVisibleItem(this.parent);
        boolean _equals_2 = Objects.equal(_firstVisibleItem, item);
        if (_equals_2) {
          final Procedure1<Path> _function_2 = new Procedure1<Path>() {
            public void apply(final Path it) {
              Point _topRight = JeeeyulsTabRenderer._sWTExtensions.getTopRight(itemBounds);
              int _tabSpacing = JeeeyulsTabRenderer.this.settings.getTabSpacing();
              int _max = Math.max(_tabSpacing, 0);
              int _plus = (_max + 1);
              Point _translated = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topRight, _plus, 0);
              JeeeyulsTabRenderer._sWTExtensions.moveTo(it, _translated);
              Point _topLeft = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(itemBounds);
              int _borderRadius = JeeeyulsTabRenderer.this.settings.getBorderRadius();
              Point _translated_1 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topLeft, _borderRadius, 0);
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated_1);
              int _borderRadius_1 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
              int _multiply = (_borderRadius_1 * 2);
              int _borderRadius_2 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
              int _multiply_1 = (_borderRadius_2 * 2);
              Rectangle _newRectangle = JeeeyulsTabRenderer._sWTExtensions.newRectangle(itemBounds.x, itemBounds.y, _multiply, _multiply_1);
              JeeeyulsTabRenderer._sWTExtensions.addArc(it, _newRectangle, 
                90, 90);
              Point _bottomLeft = JeeeyulsTabRenderer._sWTExtensions.getBottomLeft(itemBounds);
              Point _translated_2 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_bottomLeft, 0, 1);
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _translated_2);
            }
          };
          Path path_1 = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function_2);
          HSB[] _borderColors_1 = this.settings.getBorderColors();
          Color[] _autoDisposeColors_2 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_borderColors_1);
          int[] _borderPercents = this.settings.getBorderPercents();
          JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, path_1, _autoDisposeColors_2, _borderPercents, true);
        } else {
          HSB[] _borderColors_2 = this.settings.getBorderColors();
          HSB _head = IterableExtensions.<HSB>head(((Iterable<HSB>)Conversions.doWrapArray(_borderColors_2)));
          Color _autoDisposeColor_3 = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColor(_head);
          gc.setForeground(_autoDisposeColor_3);
          Point _topLeft_4 = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(itemBounds);
          Point _translated_2 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topLeft_4, (-1), 0);
          Point _topRight = JeeeyulsTabRenderer._sWTExtensions.getTopRight(itemBounds);
          int _tabSpacing_3 = this.settings.getTabSpacing();
          int _max_1 = Math.max(_tabSpacing_3, 0);
          int _plus_11 = (_max_1 + 1);
          Point _translated_3 = JeeeyulsTabRenderer._sWTExtensions.getTranslated(_topRight, _plus_11, 0);
          JeeeyulsTabRenderer._sWTExtensions.drawLine(gc, _translated_2, _translated_3);
        }
      }
      _xblockexpression = JeeeyulsTabRenderer._jTabRendererHelper.setLastKnownState(item, state);
    }
    return _xblockexpression;
  }
  
  protected void drawTabItemBorder(final int part, final int state, final Rectangle bounds, final GC gc) {
    boolean _or = false;
    HSB[] _borderColorsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderColorsFor(this.settings, state);
    boolean _equals = Objects.equal(_borderColorsFor, null);
    if (_equals) {
      _or = true;
    } else {
      int[] _borderPercentsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderPercentsFor(this.settings, state);
      boolean _equals_1 = Objects.equal(_borderPercentsFor, null);
      _or = _equals_1;
    }
    if (_or) {
      return;
    }
    final Rectangle itemOutlineBounds = JeeeyulsTabRenderer._sWTExtensions.getResized(bounds, (-1), 0);
    final CTabItem item = this.tabFolder.getItem(part);
    int _borderWidth = this.settings.getBorderWidth();
    int _divide = (_borderWidth / 2);
    final Rectangle outlineOffset = JeeeyulsTabRenderer._sWTExtensions.shrink(itemOutlineBounds, _divide);
    Path outline = null;
    boolean _onBottom = JeeeyulsTabRenderer._jTabRendererHelper.getOnBottom(this.tabFolder);
    if (_onBottom) {
      throw new UnsupportedOperationException();
    }
    final Procedure1<Path> _function = new Procedure1<Path>() {
      public void apply(final Path it) {
        Rectangle _bounds = item.getBounds();
        Point _bottom = JeeeyulsTabRenderer._sWTExtensions.getBottom(_bounds);
        int keyLineY = (_bottom.y - 1);
        int _borderRadius = JeeeyulsTabRenderer.this.settings.getBorderRadius();
        boolean _greaterThan = (_borderRadius > 0);
        if (_greaterThan) {
          Point _topLeft = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(outlineOffset);
          int _borderRadius_1 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
          int _multiply = (_borderRadius_1 * 2);
          int _borderRadius_2 = JeeeyulsTabRenderer.this.settings.getBorderRadius();
          int _multiply_1 = (_borderRadius_2 * 2);
          Point _point = new Point(_multiply, _multiply_1);
          Rectangle corner = JeeeyulsTabRenderer._sWTExtensions.newRectangle(_topLeft, _point);
          JeeeyulsTabRenderer._sWTExtensions.relocateTopRightWith(corner, outlineOffset);
          boolean _hasFlags = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
          if (_hasFlags) {
            int _borderWidth = JeeeyulsTabRenderer.this.settings.getBorderWidth();
            int _minus = ((JeeeyulsTabRenderer.this.tabFolder.getSize().x - JeeeyulsTabRenderer.this.settings.getMargins().width) - _borderWidth);
            it.moveTo(_minus, keyLineY);
            Point _bottomRight = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(itemOutlineBounds);
            it.lineTo(_bottomRight.x, keyLineY);
          } else {
            Point _bottomRight_1 = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(outlineOffset);
            it.moveTo(_bottomRight_1.x, keyLineY);
          }
          Point _right = JeeeyulsTabRenderer._sWTExtensions.getRight(corner);
          JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _right);
          JeeeyulsTabRenderer._sWTExtensions.addArc(it, corner, 0, 90);
          JeeeyulsTabRenderer._sWTExtensions.relocateTopLeftWith(corner, outlineOffset);
          Point _top = JeeeyulsTabRenderer._sWTExtensions.getTop(corner);
          JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _top);
          boolean _or = false;
          HSB[] _borderColors = JeeeyulsTabRenderer.this.settings.getBorderColors();
          boolean _equals = Objects.equal(_borderColors, null);
          if (_equals) {
            _or = true;
          } else {
            boolean _and = false;
            HSB[] _borderColors_1 = JeeeyulsTabRenderer.this.settings.getBorderColors();
            boolean _notEquals = (!Objects.equal(_borderColors_1, null));
            if (!_notEquals) {
              _and = false;
            } else {
              CTabItem _firstVisibleItem = JeeeyulsTabRenderer._jTabRendererHelper.getFirstVisibleItem(JeeeyulsTabRenderer.this.parent);
              boolean _notEquals_1 = (!Objects.equal(item, _firstVisibleItem));
              _and = _notEquals_1;
            }
            _or = _and;
          }
          if (_or) {
            JeeeyulsTabRenderer._sWTExtensions.addArc(it, corner, 90, 90);
            it.lineTo(outlineOffset.x, keyLineY);
            boolean _hasFlags_1 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
            if (_hasFlags_1) {
              Rectangle _margins = JeeeyulsTabRenderer.this.settings.getMargins();
              Point left = new Point(_margins.x, keyLineY);
              HSB[] _borderColors_2 = JeeeyulsTabRenderer.this.settings.getBorderColors();
              boolean _notEquals_2 = (!Objects.equal(_borderColors_2, null));
              if (_notEquals_2) {
                JeeeyulsTabRenderer._sWTExtensions.translate(left, 1, 0);
              }
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, left);
            }
          }
        } else {
          boolean _hasFlags_2 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
          if (_hasFlags_2) {
            int _borderWidth_1 = JeeeyulsTabRenderer.this.settings.getBorderWidth();
            int _minus_1 = ((JeeeyulsTabRenderer.this.tabFolder.getSize().x - JeeeyulsTabRenderer.this.settings.getMargins().width) - _borderWidth_1);
            it.moveTo(_minus_1, keyLineY);
            Point _bottomRight_2 = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(itemOutlineBounds);
            it.lineTo(_bottomRight_2.x, keyLineY);
          } else {
            Point _bottomRight_3 = JeeeyulsTabRenderer._sWTExtensions.getBottomRight(outlineOffset);
            it.moveTo(_bottomRight_3.x, keyLineY);
          }
          Point _topRight = JeeeyulsTabRenderer._sWTExtensions.getTopRight(itemOutlineBounds);
          JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _topRight);
          Point _topLeft_1 = JeeeyulsTabRenderer._sWTExtensions.getTopLeft(itemOutlineBounds);
          JeeeyulsTabRenderer._sWTExtensions.lineTo(it, _topLeft_1);
          boolean _or_1 = false;
          HSB[] _borderColors_3 = JeeeyulsTabRenderer.this.settings.getBorderColors();
          boolean _equals_1 = Objects.equal(_borderColors_3, null);
          if (_equals_1) {
            _or_1 = true;
          } else {
            boolean _and_1 = false;
            HSB[] _borderColors_4 = JeeeyulsTabRenderer.this.settings.getBorderColors();
            boolean _notEquals_3 = (!Objects.equal(_borderColors_4, null));
            if (!_notEquals_3) {
              _and_1 = false;
            } else {
              CTabItem _firstVisibleItem_1 = JeeeyulsTabRenderer._jTabRendererHelper.getFirstVisibleItem(JeeeyulsTabRenderer.this.parent);
              boolean _notEquals_4 = (!Objects.equal(item, _firstVisibleItem_1));
              _and_1 = _notEquals_4;
            }
            _or_1 = _and_1;
          }
          if (_or_1) {
            Point _bottomLeft = JeeeyulsTabRenderer._sWTExtensions.getBottomLeft(itemOutlineBounds);
            it.lineTo(_bottomLeft.x, keyLineY);
            boolean _hasFlags_3 = JeeeyulsTabRenderer._sWTExtensions.hasFlags(state, SWT.SELECTED);
            if (_hasFlags_3) {
              Rectangle _margins_1 = JeeeyulsTabRenderer.this.settings.getMargins();
              Point left_1 = new Point(_margins_1.x, keyLineY);
              HSB[] _borderColors_5 = JeeeyulsTabRenderer.this.settings.getBorderColors();
              boolean _notEquals_5 = (!Objects.equal(_borderColors_5, null));
              if (_notEquals_5) {
                int _borderWidth_2 = JeeeyulsTabRenderer.this.settings.getBorderWidth();
                JeeeyulsTabRenderer._sWTExtensions.translate(left_1, _borderWidth_2, 0);
              }
              JeeeyulsTabRenderer._sWTExtensions.lineTo(it, left_1);
            }
          }
        }
      }
    };
    Path _newTemporaryPath = JeeeyulsTabRenderer._sWTExtensions.newTemporaryPath(_function);
    outline = _newTemporaryPath;
    int _borderWidth_1 = this.settings.getBorderWidth();
    gc.setLineWidth(_borderWidth_1);
    HSB[] _borderColorsFor_1 = JeeeyulsTabRenderer._jTabRendererHelper.getBorderColorsFor(this.settings, state);
    Color[] _autoDisposeColors = JeeeyulsTabRenderer._sWTExtensions.toAutoDisposeColors(_borderColorsFor_1);
    int[] _borderPercentsFor_1 = JeeeyulsTabRenderer._jTabRendererHelper.getBorderPercentsFor(this.settings, state);
    JeeeyulsTabRenderer._sWTExtensions.drawGradientPath(gc, outline, _autoDisposeColors, _borderPercentsFor_1, true);
  }
  
  protected GC drawTabItemBackground(final int part, final int state, final Rectangle bounds, final GC gc) {
    GC _xblockexpression = null;
    {
      final Rectangle itemBounds = JeeeyulsTabRenderer._sWTExtensions.getCopy(bounds);
      HSB[] _borderColorsFor = JeeeyulsTabRenderer._jTabRendererHelper.getBorderColorsFor(this.settings, state);
      boolean _notEquals = (!Objects.equal(_borderColorsFor, null));
      if (_notEquals) {
        JeeeyulsTabRenderer._sWTExtensions.shrink(itemBounds, 1, 0);
      }
      HSB[] colors = JeeeyulsTabRenderer._jTabRendererHelper.getItemFillFor(this.settings, state);
      GC _xifexpression = null;
      boolean _notEquals_1 = (!Objects.equal(colors, null));
      if (_notEquals_1) {
        int _borderRadius = this.settings.getBorderRadius();
        int[] _itemFillPercentsFor = JeeeyulsTabRenderer._jTabRendererHelper.getItemFillPercentsFor(this.settings, state);
        _xifexpression = JeeeyulsTabRenderer._sWTExtensions.fillGradientRoundRectangle(gc, itemBounds, _borderRadius, JeeeyulsTabRenderer._sWTExtensions.CORNER_TOP, colors, _itemFillPercentsFor, true);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected GC drawShadow(final int part, final int state, final Rectangle bounds, final GC gc) {
    final Procedure1<GC> _function = new Procedure1<GC>() {
      public void apply(final GC it) {
        NinePatch _shadow = JeeeyulsTabRenderer.this.getShadow();
        Rectangle _tabArea = JeeeyulsTabRenderer.this.tabArea();
        int _shadowRadius = JeeeyulsTabRenderer.this.settings.getShadowRadius();
        Rectangle _expanded = JeeeyulsTabRenderer._sWTExtensions.getExpanded(_tabArea, _shadowRadius);
        Point _shadowPosition = JeeeyulsTabRenderer.this.settings.getShadowPosition();
        Rectangle _translate = JeeeyulsTabRenderer._sWTExtensions.translate(_expanded, _shadowPosition);
        _shadow.fill(gc, _translate);
      }
    };
    return JeeeyulsTabRenderer._sWTExtensions.withClip(gc, bounds, _function);
  }
  
  protected Rectangle tabArea() {
    Rectangle _newRectangle = JeeeyulsTabRenderer._sWTExtensions.newRectangle();
    Point _size = this.tabFolder.getSize();
    Rectangle _setSize = JeeeyulsTabRenderer._sWTExtensions.setSize(_newRectangle, _size);
    Rectangle _margins = this.settings.getMargins();
    Rectangle _margins_1 = this.settings.getMargins();
    Rectangle _margins_2 = this.settings.getMargins();
    return JeeeyulsTabRenderer._sWTExtensions.shrink(_setSize, _margins.x, 0, _margins_1.width, 
      _margins_2.height);
  }
  
  protected NinePatch getShadow() {
    boolean _or = false;
    boolean _equals = Objects.equal(this.shadowNinePatch, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _isDisposed = this.shadowNinePatch.isDisposed();
      _or = _isDisposed;
    }
    if (_or) {
      HSB _shadowColor = this.settings.getShadowColor();
      RGB _rGB = _shadowColor.toRGB();
      int _borderRadius = this.settings.getBorderRadius();
      int _plus = (_borderRadius + 3);
      int _shadowRadius = this.settings.getShadowRadius();
      NinePatch _createShadowPatch = Shadow9PatchFactory.createShadowPatch(_rGB, _plus, _shadowRadius);
      this.shadowNinePatch = _createShadowPatch;
    }
    return this.shadowNinePatch;
  }
  
  public JTabSettings getSettings() {
    return this.settings;
  }
  
  public CTabFolder getTabFolder() {
    return this.tabFolder;
  }
  
  private String shortenText(final GC gc, final String text, final int width) {
    String _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isUseEllipses = this.settings.isUseEllipses();
      if (_isUseEllipses) {
        _xifexpression = "...";
      } else {
        _xifexpression = "";
      }
      String ellipses = _xifexpression;
      _xblockexpression = JeeeyulsTabRenderer._sWTExtensions.shortenText(gc, text, width, ellipses, JeeeyulsTabRenderer.TEXT_FLAGS);
    }
    return _xblockexpression;
  }
}
