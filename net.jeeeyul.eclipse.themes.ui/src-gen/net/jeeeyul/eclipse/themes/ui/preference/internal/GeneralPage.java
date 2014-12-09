package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.ColorWell;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.GradientEdit;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

/**
 * @since 2.0
 */
@SuppressWarnings("all")
public class GeneralPage extends AbstractJTPreferencePage {
  private GradientEdit toolbarEdit;
  
  private GradientEdit statusEdit;
  
  private GradientEdit perspectiveSwitcherEdit;
  
  private ColorWell statusTextColorWell;
  
  private ColorWell perspectiveSwitcherKeyLineColorWell;
  
  private ColorWell perspectiveTextColorWell;
  
  private ColorWell backgroundEdit;
  
  private Button castShadowEdit;
  
  private ColorWell shadowColorWell;
  
  private Spinner partStackSpacingEdit;
  
  private Label partStackSpacingRangeLabel;
  
  private Spinner windowMarginsEdit;
  
  private Label windowMarginsRangeLabel;
  
  public GeneralPage() {
    super("General");
    Image _image = SharedImages.getImage(SharedImages.LAYOUT);
    this.setImage(_image);
  }
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
          public void apply(final GridLayout it) {
          }
        };
        GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
        it.setLayout(_newGridLayout);
        final Procedure1<Group> _function_1 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Window");
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 3;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<Label> _function_1 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Background");
              }
            };
            swtExtensions.newLabel(it, _function_1);
            final Procedure1<ColorWell> _function_2 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 2;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            ColorWell _newColorWell = helper.newColorWell(it, _function_2);
            GeneralPage.this.backgroundEdit = _newColorWell;
            final Procedure1<Label> _function_3 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Tool Bar");
              }
            };
            swtExtensions.newLabel(it, _function_3);
            final Procedure1<GradientEdit> _function_4 = new Procedure1<GradientEdit>() {
              public void apply(final GradientEdit it) {
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
              }
            };
            GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_4);
            GeneralPage.this.toolbarEdit = _newGradientEdit;
            final Procedure1<Button> _function_5 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(GeneralPage.this.toolbarEdit, _function_5);
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Status Bar");
              }
            };
            swtExtensions.newLabel(it, _function_6);
            final Procedure1<GradientEdit> _function_7 = new Procedure1<GradientEdit>() {
              public void apply(final GradientEdit it) {
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
              }
            };
            GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_7);
            GeneralPage.this.statusEdit = _newGradientEdit_1;
            final Procedure1<Button> _function_8 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(GeneralPage.this.statusEdit, _function_8);
            final Procedure1<Label> _function_9 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Status Bar Text");
              }
            };
            swtExtensions.newLabel(it, _function_9);
            final Procedure1<ColorWell> _function_10 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 2;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            ColorWell _newColorWell_1 = helper.newColorWell(it, _function_10);
            GeneralPage.this.statusTextColorWell = _newColorWell_1;
          }
        };
        swtExtensions.newGroup(it, _function_1);
        final Procedure1<Group> _function_2 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Perspective Switcher");
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 4;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<Label> _function_1 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Fill");
              }
            };
            swtExtensions.newLabel(it, _function_1);
            final Procedure1<GradientEdit> _function_2 = new Procedure1<GradientEdit>() {
              public void apply(final GradientEdit it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 2;
                  }
                };
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL(_function);
                it.setLayoutData(_FILL_HORIZONTAL);
              }
            };
            GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
            GeneralPage.this.perspectiveSwitcherEdit = _newGradientEdit;
            final Procedure1<Button> _function_3 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(GeneralPage.this.perspectiveSwitcherEdit, _function_3);
            final Procedure1<Label> _function_4 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Key Line");
              }
            };
            swtExtensions.newLabel(it, _function_4);
            final Procedure1<ColorWell> _function_5 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 3;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            ColorWell _newColorWell = helper.newColorWell(it, _function_5);
            GeneralPage.this.perspectiveSwitcherKeyLineColorWell = _newColorWell;
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Text");
              }
            };
            swtExtensions.newLabel(it, _function_6);
            final Procedure1<ColorWell> _function_7 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                boolean _isWindow8 = ENVHelper.INSTANCE.isWindow8();
                if (_isWindow8) {
                  final Procedure1<GridData> _function = new Procedure1<GridData>() {
                    public void apply(final GridData it) {
                      it.horizontalSpan = 3;
                    }
                  };
                  GridData _newGridData = swtExtensions.newGridData(_function);
                  it.setLayoutData(_newGridData);
                }
              }
            };
            ColorWell _newColorWell_1 = helper.newColorWell(it, _function_7);
            GeneralPage.this.perspectiveTextColorWell = _newColorWell_1;
            boolean _isWindow8 = ENVHelper.INSTANCE.isWindow8();
            if (_isWindow8) {
              final Procedure1<Link> _function_8 = new Procedure1<Link>() {
                public void apply(final Link it) {
                  StringConcatenation _builder = new StringConcatenation();
                  _builder.append("Not works on Windows 8 (<a href=\"https://bugs.eclipse.org/bugs/show_bug.cgi?id=443156\">Bug 443156</a>)");
                  it.setText(_builder.toString());
                  final Procedure1<GridData> _function = new Procedure1<GridData>() {
                    public void apply(final GridData it) {
                      it.horizontalSpan = 2;
                    }
                  };
                  GridData _newGridData = swtExtensions.newGridData(_function);
                  it.setLayoutData(_newGridData);
                }
              };
              swtExtensions.newLink(it, _function_8);
            }
          }
        };
        swtExtensions.newGroup(it, _function_2);
        final Procedure1<Group> _function_3 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Window Spacing");
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 3;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<Button> _function_1 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Cast Shadow");
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    GeneralPage.this.updateRangeLabels();
                    helper.requestUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            Button _newCheckbox = swtExtensions.newCheckbox(it, _function_1);
            GeneralPage.this.castShadowEdit = _newCheckbox;
            final Procedure1<ColorWell> _function_2 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 2;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
                final Listener _function_1 = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function_1);
              }
            };
            ColorWell _newColorWell = helper.newColorWell(it, _function_2);
            GeneralPage.this.shadowColorWell = _newColorWell;
            final Procedure1<Label> _function_3 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Part Stack Spacing");
              }
            };
            swtExtensions.newLabel(it, _function_3);
            final Procedure1<Spinner> _function_4 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum(0);
                it.setMaximum(10);
                it.setSelection(2);
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner = swtExtensions.newSpinner(it, _function_4);
            GeneralPage.this.partStackSpacingEdit = _newSpinner;
            final Procedure1<Label> _function_5 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("2px ~ 10px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            Label _newLabel = swtExtensions.newLabel(it, _function_5);
            GeneralPage.this.partStackSpacingRangeLabel = _newLabel;
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Margins");
              }
            };
            swtExtensions.newLabel(it, _function_6);
            final Procedure1<Spinner> _function_7 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum(0);
                it.setMaximum(10);
                it.setSelection(4);
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner_1 = swtExtensions.newSpinner(it, _function_7);
            GeneralPage.this.windowMarginsEdit = _newSpinner_1;
            final Procedure1<Label> _function_8 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("2px ~ 10px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            Label _newLabel_1 = swtExtensions.newLabel(it, _function_8);
            GeneralPage.this.windowMarginsRangeLabel = _newLabel_1;
          }
        };
        swtExtensions.newGroup(it, _function_3);
        final Procedure1<Link> _function_4 = new Procedure1<Link>() {
          public void apply(final Link it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Margins of window requires <a href=\"https://github.com/jeeeyul/eclipse-themes/wiki/Settings-that-require-opening-a-new-window\">opening new window</a> to take full effect.");
            _builder.newLine();
            it.setText(_builder.toString());
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                Program.launch(it.text);
              }
            };
            swtExtensions.setOnSelection(it, _function);
          }
        };
        swtExtensions.newLink(it, _function_4);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  private void updateRangeLabels() {
    boolean _selection = this.castShadowEdit.getSelection();
    if (_selection) {
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner> _function = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner>() {
        public void apply(final Spinner it) {
          it.setMinimum(6);
          int _selection = it.getSelection();
          int _max = Math.max(_selection, 4);
          it.setSelection(_max);
        }
      };
      ObjectExtensions.<Spinner>operator_doubleArrow(
        this.partStackSpacingEdit, _function);
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner> _function_1 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner>() {
        public void apply(final Spinner it) {
          it.setMinimum(4);
          int _selection = it.getSelection();
          int _max = Math.max(_selection, 4);
          it.setSelection(_max);
        }
      };
      ObjectExtensions.<Spinner>operator_doubleArrow(
        this.windowMarginsEdit, _function_1);
    } else {
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner> _function_2 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner>() {
        public void apply(final Spinner it) {
          it.setMinimum(2);
        }
      };
      ObjectExtensions.<Spinner>operator_doubleArrow(
        this.partStackSpacingEdit, _function_2);
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner> _function_3 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Spinner>() {
        public void apply(final Spinner it) {
          it.setMinimum(0);
        }
      };
      ObjectExtensions.<Spinner>operator_doubleArrow(
        this.windowMarginsEdit, _function_3);
    }
    StringConcatenation _builder = new StringConcatenation();
    int _minimum = this.partStackSpacingEdit.getMinimum();
    _builder.append(_minimum, "");
    _builder.append(" ~ ");
    int _maximum = this.partStackSpacingEdit.getMaximum();
    _builder.append(_maximum, "");
    _builder.append("px");
    this.partStackSpacingRangeLabel.setText(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    int _minimum_1 = this.windowMarginsEdit.getMinimum();
    _builder_1.append(_minimum_1, "");
    _builder_1.append(" ~ ");
    int _maximum_1 = this.windowMarginsEdit.getMaximum();
    _builder_1.append(_maximum_1, "");
    _builder_1.append("px");
    this.windowMarginsRangeLabel.setText(_builder_1.toString());
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    boolean _selection = this.castShadowEdit.getSelection();
    if (_selection) {
      Rectangle _rectangle = new Rectangle(1, 0, 4, 4);
      renderSettings.setMargins(_rectangle);
      HSB _selection_1 = this.shadowColorWell.getSelection();
      renderSettings.setShadowColor(_selection_1);
      Point _point = new Point(1, 1);
      renderSettings.setShadowPosition(_point);
      renderSettings.setShadowRadius(3);
    } else {
      Rectangle _rectangle_1 = new Rectangle(0, 0, 0, 0);
      renderSettings.setMargins(_rectangle_1);
      renderSettings.setShadowColor(null);
      renderSettings.setShadowRadius(0);
    }
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Gradient toolbarFill = store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR);
    boolean _notEquals = (!Objects.equal(toolbarFill, null));
    if (_notEquals) {
      this.toolbarEdit.setSelection(toolbarFill);
    }
    final Gradient statusBarFill = store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR);
    boolean _notEquals_1 = (!Objects.equal(statusBarFill, null));
    if (_notEquals_1) {
      this.statusEdit.setSelection(statusBarFill);
    }
    HSB _hSB = store.getHSB(JTPConstants.Window.STATUS_BAR_TEXT_COLOR);
    this.statusTextColorWell.setSelection(_hSB);
    final HSB background = store.getHSB(JTPConstants.Window.BACKGROUND_COLOR);
    boolean _notEquals_2 = (!Objects.equal(background, null));
    if (_notEquals_2) {
      this.backgroundEdit.setSelection(background);
    }
    Gradient psFill = store.getGradient(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR);
    boolean _notEquals_3 = (!Objects.equal(psFill, null));
    if (_notEquals_3) {
      this.perspectiveSwitcherEdit.setSelection(psFill);
    }
    HSB psKeyline = store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR);
    boolean _notEquals_4 = (!Objects.equal(psKeyline, null));
    if (_notEquals_4) {
      this.perspectiveSwitcherKeyLineColorWell.setSelection(psKeyline);
    }
    HSB _hSB_1 = store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_TEXT_COLOR);
    this.perspectiveTextColorWell.setSelection(_hSB_1);
    boolean _boolean = store.getBoolean(JTPConstants.Layout.SHOW_SHADOW);
    this.castShadowEdit.setSelection(_boolean);
    final HSB shadowColor = store.getHSB(JTPConstants.Layout.SHADOW_COLOR);
    boolean _notEquals_5 = (!Objects.equal(shadowColor, null));
    if (_notEquals_5) {
      this.shadowColorWell.setSelection(shadowColor);
    }
    int _int = store.getInt(JTPConstants.Window.SASH_WIDTH);
    this.partStackSpacingEdit.setSelection(_int);
    this.updateRangeLabels();
    Rectangle _rectangle = store.getRectangle(JTPConstants.Window.MARGINS);
    this.windowMarginsEdit.setSelection(_rectangle.x);
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Gradient _selection = this.toolbarEdit.getSelection();
    store.setValue(JTPConstants.Window.TOOLBAR_FILL_COLOR, _selection);
    Gradient _selection_1 = this.statusEdit.getSelection();
    store.setValue(JTPConstants.Window.STATUS_BAR_FILL_COLOR, _selection_1);
    HSB _selection_2 = this.statusTextColorWell.getSelection();
    store.setValue(JTPConstants.Window.STATUS_BAR_TEXT_COLOR, _selection_2);
    HSB _selection_3 = this.backgroundEdit.getSelection();
    store.setValue(JTPConstants.Window.BACKGROUND_COLOR, _selection_3);
    Gradient _selection_4 = this.perspectiveSwitcherEdit.getSelection();
    store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR, _selection_4);
    HSB _selection_5 = this.perspectiveSwitcherKeyLineColorWell.getSelection();
    store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR, _selection_5);
    HSB _selection_6 = this.perspectiveTextColorWell.getSelection();
    store.setValue(JTPConstants.Window.PERSPECTIVE_SWITCHER_TEXT_COLOR, _selection_6);
    int _selection_7 = this.partStackSpacingEdit.getSelection();
    store.setValue(JTPConstants.Window.SASH_WIDTH, _selection_7);
    boolean _selection_8 = this.castShadowEdit.getSelection();
    store.setValue(JTPConstants.Layout.SHOW_SHADOW, _selection_8);
    HSB _selection_9 = this.shadowColorWell.getSelection();
    store.setValue(JTPConstants.Layout.SHADOW_COLOR, _selection_9);
    int windowMargin = this.windowMarginsEdit.getSelection();
    Rectangle _rectangle = new Rectangle(windowMargin, windowMargin, windowMargin, windowMargin);
    store.setValue(JTPConstants.Window.MARGINS, _rectangle);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
}
