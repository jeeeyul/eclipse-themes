package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment;
import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class LayoutPage extends AbstractJTPreferencePage {
  private Spinner borderRadiusScale;
  
  private Spinner paddingsScale;
  
  private Spinner tabItemPaddingsScale;
  
  private Spinner tabSpacingScale;
  
  private Spinner tabItemSpacingScale;
  
  private Spinner tabHeightScale;
  
  private Button truncateTabItemsButton;
  
  private Button truncateEditorTabItemsButton;
  
  private Button useEllipsesButton;
  
  private Spinner minimumCharactersSpinner;
  
  private Spinner minimumCharactersForEditorSpinner;
  
  private List<Button> closeButtonAlignButtons;
  
  public LayoutPage() {
    super("Layout");
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
        final Procedure1<Composite> _function_1 = new Procedure1<Composite>() {
          public void apply(final Composite it) {
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
                it.setText("Border Radius");
              }
            };
            swtExtensions.newLabel(it, _function_1);
            final Procedure1<Spinner> _function_2 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum(0);
                it.setMaximum(10);
                it.setSelection(3);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner = swtExtensions.newSpinner(it, _function_2);
            LayoutPage.this.borderRadiusScale = _newSpinner;
            final Procedure1<Label> _function_3 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("0 ~ 10px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            swtExtensions.newLabel(it, _function_3);
            final Procedure1<Label> _function_4 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Tab Item Paddings");
              }
            };
            swtExtensions.newLabel(it, _function_4);
            final Procedure1<Spinner> _function_5 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum(0);
                it.setMaximum(10);
                it.setSelection(2);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner_1 = swtExtensions.newSpinner(it, _function_5);
            LayoutPage.this.tabItemPaddingsScale = _newSpinner_1;
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("0 ~ 10px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            swtExtensions.newLabel(it, _function_6);
            final Procedure1<Label> _function_7 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Content Paddings");
              }
            };
            swtExtensions.newLabel(it, _function_7);
            final Procedure1<Spinner> _function_8 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum(0);
                it.setMaximum(10);
                it.setSelection(2);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner_2 = swtExtensions.newSpinner(it, _function_8);
            LayoutPage.this.paddingsScale = _newSpinner_2;
            final Procedure1<Label> _function_9 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("0 ~ 10px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            swtExtensions.newLabel(it, _function_9);
            final Procedure1<Label> _function_10 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Tab Spacing");
              }
            };
            swtExtensions.newLabel(it, _function_10);
            final Procedure1<Spinner> _function_11 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum((-1));
                it.setMaximum(20);
                it.setSelection(2);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner_3 = swtExtensions.newSpinner(it, _function_11);
            LayoutPage.this.tabSpacingScale = _newSpinner_3;
            final Procedure1<Label> _function_12 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("-1(Overlap) ~ 20px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            swtExtensions.newLabel(it, _function_12);
            final Procedure1<Label> _function_13 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Part Icon, Title and Close Spacing");
              }
            };
            swtExtensions.newLabel(it, _function_13);
            final Procedure1<Spinner> _function_14 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                it.setMinimum(0);
                it.setMaximum(10);
                it.setSelection(2);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner_4 = swtExtensions.newSpinner(it, _function_14);
            LayoutPage.this.tabItemSpacingScale = _newSpinner_4;
            final Procedure1<Label> _function_15 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("0 ~ 10px");
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            swtExtensions.newLabel(it, _function_15);
            final Procedure1<Label> _function_16 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Tab Height");
              }
            };
            swtExtensions.newLabel(it, _function_16);
            final Procedure1<Spinner> _function_17 = new Procedure1<Spinner>() {
              public void apply(final Spinner it) {
                int _minimumToolBarHeight = swtExtensions.getMinimumToolBarHeight();
                it.setMinimum(_minimumToolBarHeight);
                it.setMaximum(40);
                int _minimumToolBarHeight_1 = swtExtensions.getMinimumToolBarHeight();
                it.setSelection(_minimumToolBarHeight_1);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.widthHint = 40;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
              }
            };
            Spinner _newSpinner_5 = swtExtensions.newSpinner(it, _function_17);
            LayoutPage.this.tabHeightScale = _newSpinner_5;
            final Procedure1<Label> _function_18 = new Procedure1<Label>() {
              public void apply(final Label it) {
                StringConcatenation _builder = new StringConcatenation();
                int _minimumToolBarHeight = swtExtensions.getMinimumToolBarHeight();
                _builder.append(_minimumToolBarHeight, "");
                _builder.append(" ~ 40px");
                it.setText(_builder.toString());
                Color _COLOR_DARK_GRAY = swtExtensions.COLOR_DARK_GRAY();
                it.setForeground(_COLOR_DARK_GRAY);
              }
            };
            swtExtensions.newLabel(it, _function_18);
            final Procedure1<Label> _function_19 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Close Button Alignment");
              }
            };
            swtExtensions.newLabel(it, _function_19);
            final Procedure1<Composite> _function_20 = new Procedure1<Composite>() {
              public void apply(final Composite it) {
                final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
                  public void apply(final GridLayout it) {
                    VerticalAlignment[] _values = VerticalAlignment.values();
                    int _length = _values.length;
                    it.numColumns = _length;
                    it.marginWidth = 0;
                    it.marginHeight = 0;
                  }
                };
                GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
                it.setLayout(_newGridLayout);
                final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 2;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function_1);
                it.setLayoutData(_newGridData);
                ArrayList<Button> _newArrayList = CollectionLiterals.<Button>newArrayList();
                LayoutPage.this.closeButtonAlignButtons = _newArrayList;
                VerticalAlignment[] _values = VerticalAlignment.values();
                for (final VerticalAlignment each : _values) {
                  final Procedure1<Button> _function_2 = new Procedure1<Button>() {
                    public void apply(final Button it) {
                      String _name = each.getName();
                      it.setText(_name);
                      it.setData(each);
                      final Listener _function = new Listener() {
                        public void handleEvent(final Event it) {
                          helper.requestFastUpdatePreview();
                        }
                      };
                      swtExtensions.setOnSelection(it, _function);
                    }
                  };
                  Button _newRadioButton = swtExtensions.newRadioButton(it, _function_2);
                  LayoutPage.this.closeButtonAlignButtons.add(_newRadioButton);
                }
              }
            };
            swtExtensions.newComposite(it, _function_20);
          }
        };
        swtExtensions.newComposite(it, _function_1);
        final Procedure1<Composite> _function_2 = new Procedure1<Composite>() {
          public void apply(final Composite it) {
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 1;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<Button> _function_1 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Truncate Tab Items to show more Tab Items");
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            Button _newCheckbox = swtExtensions.newCheckbox(it, _function_1);
            LayoutPage.this.truncateTabItemsButton = _newCheckbox;
            final Procedure1<Group> _function_2 = new Procedure1<Group>() {
              public void apply(final Group it) {
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
                final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
                  public void apply(final GridLayout it) {
                    it.numColumns = 2;
                  }
                };
                GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
                it.setLayout(_newGridLayout);
                final Procedure1<Label> _function_1 = new Procedure1<Label>() {
                  public void apply(final Label it) {
                    it.setText("Minimum number of characters");
                  }
                };
                swtExtensions.newLabel(it, _function_1);
                final Procedure1<Spinner> _function_2 = new Procedure1<Spinner>() {
                  public void apply(final Spinner it) {
                    final Procedure1<GridData> _function = new Procedure1<GridData>() {
                      public void apply(final GridData it) {
                        it.widthHint = 40;
                      }
                    };
                    GridData _newGridData = swtExtensions.newGridData(_function);
                    it.setLayoutData(_newGridData);
                    it.setMinimum(1);
                    it.setMaximum(20);
                    final Listener _function_1 = new Listener() {
                      public void handleEvent(final Event it) {
                        helper.requestFastUpdatePreview();
                      }
                    };
                    swtExtensions.setOnSelection(it, _function_1);
                  }
                };
                Spinner _newSpinner = swtExtensions.newSpinner(it, _function_2);
                LayoutPage.this.minimumCharactersSpinner = _newSpinner;
                final Procedure1<Button> _function_3 = new Procedure1<Button>() {
                  public void apply(final Button it) {
                    it.setText("Use Ellipses when truncating");
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
                    swtExtensions.setOnSelection(it, _function_1);
                  }
                };
                Button _newCheckbox = swtExtensions.newCheckbox(it, _function_3);
                LayoutPage.this.useEllipsesButton = _newCheckbox;
                final Procedure1<Button> _function_4 = new Procedure1<Button>() {
                  public void apply(final Button it) {
                    it.setText("Truncate Editors also");
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
                    swtExtensions.setOnSelection(it, _function_1);
                  }
                };
                Button _newCheckbox_1 = swtExtensions.newCheckbox(it, _function_4);
                LayoutPage.this.truncateEditorTabItemsButton = _newCheckbox_1;
                final Procedure1<Label> _function_5 = new Procedure1<Label>() {
                  public void apply(final Label it) {
                    it.setText("Minimum number of characters for Editors");
                    final Procedure1<GridData> _function = new Procedure1<GridData>() {
                      public void apply(final GridData it) {
                        it.horizontalIndent = 16;
                      }
                    };
                    GridData _newGridData = swtExtensions.newGridData(_function);
                    it.setLayoutData(_newGridData);
                  }
                };
                swtExtensions.newLabel(it, _function_5);
                final Procedure1<Spinner> _function_6 = new Procedure1<Spinner>() {
                  public void apply(final Spinner it) {
                    final Procedure1<GridData> _function = new Procedure1<GridData>() {
                      public void apply(final GridData it) {
                        it.widthHint = 40;
                      }
                    };
                    GridData _newGridData = swtExtensions.newGridData(_function);
                    it.setLayoutData(_newGridData);
                    it.setMinimum(1);
                    it.setMaximum(20);
                    final Listener _function_1 = new Listener() {
                      public void handleEvent(final Event it) {
                        helper.requestFastUpdatePreview();
                      }
                    };
                    swtExtensions.setOnSelection(it, _function_1);
                  }
                };
                Spinner _newSpinner_1 = swtExtensions.newSpinner(it, _function_6);
                LayoutPage.this.minimumCharactersForEditorSpinner = _newSpinner_1;
              }
            };
            swtExtensions.newGroup(it, _function_2);
          }
        };
        swtExtensions.newComposite(it, _function_2);
        boolean _isLinux = ENVHelper.INSTANCE.isLinux();
        if (_isLinux) {
          final Procedure1<Link> _function_3 = new Procedure1<Link>() {
            public void apply(final Link it) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("To reduce limit on minimum tab height, refer to <a href=\"https://github.com/jeeeyul/eclipse-themes/wiki/Linux-User-Guide\">Linux User Guide</a>.");
              it.setText(_builder.toString());
              final Procedure1<GridData> _function = new Procedure1<GridData>() {
                public void apply(final GridData it) {
                  it.horizontalSpan = 3;
                }
              };
              GridData _newGridData = swtExtensions.newGridData(_function);
              it.setLayoutData(_newGridData);
              final Listener _function_1 = new Listener() {
                public void handleEvent(final Event it) {
                  Program.launch(it.text);
                }
              };
              swtExtensions.setOnSelection(it, _function_1);
            }
          };
          swtExtensions.newLink(it, _function_3);
        }
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    int _selection = this.borderRadiusScale.getSelection();
    renderSettings.setBorderRadius(_selection);
    int _selection_1 = this.paddingsScale.getSelection();
    Rectangle _newInsets = swtExtensions.newInsets(_selection_1);
    renderSettings.setPaddings(_newInsets);
    int _selection_2 = this.tabItemPaddingsScale.getSelection();
    Rectangle _newInsets_1 = swtExtensions.newInsets(_selection_2);
    renderSettings.setTabItemPaddings(_newInsets_1);
    int _selection_3 = this.tabSpacingScale.getSelection();
    renderSettings.setTabSpacing(_selection_3);
    int _selection_4 = this.tabItemSpacingScale.getSelection();
    renderSettings.setTabItemHorizontalSpacing(_selection_4);
    boolean _selection_5 = this.useEllipsesButton.getSelection();
    renderSettings.setUseEllipses(_selection_5);
    int _selection_6 = this.minimumCharactersSpinner.getSelection();
    renderSettings.setMinimumCharacters(_selection_6);
    boolean _selection_7 = this.truncateTabItemsButton.getSelection();
    renderSettings.setTruncateTabItems(_selection_7);
    final Function1<Button, Boolean> _function = new Function1<Button, Boolean>() {
      public Boolean apply(final Button it) {
        return Boolean.valueOf(it.getSelection());
      }
    };
    Button _findFirst = IterableExtensions.<Button>findFirst(this.closeButtonAlignButtons, _function);
    Object _data = _findFirst.getData();
    renderSettings.setCloseButtonAlignment(((VerticalAlignment) _data));
    boolean _selection_8 = this.truncateTabItemsButton.getSelection();
    this.useEllipsesButton.setEnabled(_selection_8);
    boolean _selection_9 = this.truncateTabItemsButton.getSelection();
    this.minimumCharactersSpinner.setEnabled(_selection_9);
    boolean _selection_10 = this.truncateTabItemsButton.getSelection();
    this.truncateEditorTabItemsButton.setEnabled(_selection_10);
    boolean _and = false;
    boolean _selection_11 = this.truncateEditorTabItemsButton.getSelection();
    if (!_selection_11) {
      _and = false;
    } else {
      boolean _selection_12 = this.truncateTabItemsButton.getSelection();
      _and = _selection_12;
    }
    this.minimumCharactersForEditorSpinner.setEnabled(_and);
    int _selection_13 = this.tabHeightScale.getSelection();
    folder.setTabHeight(_selection_13);
    Event _event = new Event();
    folder.notifyListeners(SWT.Resize, _event);
    folder.layout(true, true);
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    int _int = store.getInt(JTPConstants.Layout.BORDER_RADIUS);
    this.borderRadiusScale.setSelection(_int);
    int _int_1 = store.getInt(JTPConstants.Layout.CONTENT_PADDING);
    this.paddingsScale.setSelection(_int_1);
    int _int_2 = store.getInt(JTPConstants.Layout.TAB_HEIGHT);
    int _minimumToolBarHeight = swtExtensions.getMinimumToolBarHeight();
    int _max = Math.max(_int_2, _minimumToolBarHeight);
    this.tabHeightScale.setSelection(_max);
    int _int_3 = store.getInt(JTPConstants.Layout.TAB_SPACING);
    this.tabSpacingScale.setSelection(_int_3);
    int _int_4 = store.getInt(JTPConstants.Layout.TAB_ITEM_PADDING);
    this.tabItemPaddingsScale.setSelection(_int_4);
    int _int_5 = store.getInt(JTPConstants.Layout.TAB_ITEM_SPACING);
    this.tabItemSpacingScale.setSelection(_int_5);
    boolean _boolean = store.getBoolean(JTPConstants.Layout.TRUNCATE_TAB_ITEMS);
    this.truncateTabItemsButton.setSelection(_boolean);
    boolean _boolean_1 = store.getBoolean(JTPConstants.Layout.USE_ELLIPSES);
    this.useEllipsesButton.setSelection(_boolean_1);
    int _int_6 = store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS);
    this.minimumCharactersSpinner.setSelection(_int_6);
    boolean _boolean_2 = store.getBoolean(JTPConstants.Layout.TRUNCATE_EDITORS_TAB_ITEMS_ALSO);
    this.truncateEditorTabItemsButton.setSelection(_boolean_2);
    int _int_7 = store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS_FOR_EDITORS);
    this.minimumCharactersForEditorSpinner.setSelection(_int_7);
    int _int_8 = store.getInt(JTPConstants.Layout.CLOSE_BUTTON_VERTICAL_ALIGNMENT);
    VerticalAlignment closeButtonAlign = VerticalAlignment.fromValue(_int_8);
    for (final Button eachButton : this.closeButtonAlignButtons) {
      Object _data = eachButton.getData();
      boolean _equals = Objects.equal(_data, closeButtonAlign);
      eachButton.setSelection(_equals);
    }
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    int _selection = this.borderRadiusScale.getSelection();
    store.setValue(JTPConstants.Layout.BORDER_RADIUS, _selection);
    int _selection_1 = this.paddingsScale.getSelection();
    store.setValue(JTPConstants.Layout.CONTENT_PADDING, _selection_1);
    int _selection_2 = this.tabHeightScale.getSelection();
    store.setValue(JTPConstants.Layout.TAB_HEIGHT, _selection_2);
    int _selection_3 = this.tabSpacingScale.getSelection();
    store.setValue(JTPConstants.Layout.TAB_SPACING, _selection_3);
    int _selection_4 = this.tabItemPaddingsScale.getSelection();
    store.setValue(JTPConstants.Layout.TAB_ITEM_PADDING, _selection_4);
    int _selection_5 = this.tabItemSpacingScale.getSelection();
    store.setValue(JTPConstants.Layout.TAB_ITEM_SPACING, _selection_5);
    boolean _selection_6 = this.truncateTabItemsButton.getSelection();
    store.setValue(JTPConstants.Layout.TRUNCATE_TAB_ITEMS, _selection_6);
    boolean _selection_7 = this.useEllipsesButton.getSelection();
    store.setValue(JTPConstants.Layout.USE_ELLIPSES, _selection_7);
    int _selection_8 = this.minimumCharactersSpinner.getSelection();
    store.setValue(JTPConstants.Layout.MINIMUM_CHARACTERS, _selection_8);
    boolean _selection_9 = this.truncateEditorTabItemsButton.getSelection();
    store.setValue(JTPConstants.Layout.TRUNCATE_EDITORS_TAB_ITEMS_ALSO, _selection_9);
    int _selection_10 = this.minimumCharactersForEditorSpinner.getSelection();
    store.setValue(JTPConstants.Layout.MINIMUM_CHARACTERS_FOR_EDITORS, _selection_10);
    final Function1<Button, Boolean> _function = new Function1<Button, Boolean>() {
      public Boolean apply(final Button it) {
        return Boolean.valueOf(it.getSelection());
      }
    };
    Button _findFirst = IterableExtensions.<Button>findFirst(this.closeButtonAlignButtons, _function);
    Object _data = _findFirst.getData();
    VerticalAlignment selectedAlign = ((VerticalAlignment) _data);
    int _value = selectedAlign.getValue();
    store.setValue(JTPConstants.Layout.CLOSE_BUTTON_VERTICAL_ALIGNMENT, _value);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
}
