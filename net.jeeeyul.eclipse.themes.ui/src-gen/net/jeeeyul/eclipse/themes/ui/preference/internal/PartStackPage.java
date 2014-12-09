package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import java.util.List;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.LineWidthEditor;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.eclipse.themes.ui.preference.internal.TextShadowEdit;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.xtend.lib.Property;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class PartStackPage extends AbstractJTPreferencePage {
  private Color[] headerBackgroundColors = {};
  
  private Color[] selectedBackgroundColors = {};
  
  private Color bodyBackgroundColor;
  
  private Color unselectedForeground;
  
  private Color selectedForeground;
  
  private GradientEdit headerBackgroundEdit;
  
  private GradientEdit borderEdit;
  
  private Button hideBorderButton;
  
  private ColorWell bodyBackgroundEdit;
  
  private GradientEdit selectedBackgroundEdit;
  
  private GradientEdit selectedBorderEdit;
  
  private Button hideSelectedBorderButton;
  
  private ColorWell selectedTextColorWell;
  
  private TextShadowEdit selectedShadowEdit;
  
  private GradientEdit unselectedBackgroundEdit;
  
  private Button hideUnselectedBackgroundButton;
  
  private GradientEdit unselectedBorderEdit;
  
  private Button hideUnselectedBorderButton;
  
  private ColorWell unselectedTextColorWell;
  
  private TextShadowEdit unselectedShadowEdit;
  
  private GradientEdit hoverBackgroundEdit;
  
  private Button hideHoverBackgroundButton;
  
  private GradientEdit hoverBorderEdit;
  
  private Button hideHoverBorderButton;
  
  private ColorWell hoverTextColorWell;
  
  private TextShadowEdit hoverShadowEdit;
  
  private ColorWell closeButtonColorWell;
  
  private ColorWell closeButtonHoverColorWell;
  
  private ColorWell closeButtonActiveColorWell;
  
  private LineWidthEditor closeButtonLineEdit;
  
  private ColorWell chevronColorWell;
  
  @Property
  private String _context;
  
  public PartStackPage(final String title, final String context) {
    super(title);
    this.setContext(context);
    Image _image = SharedImages.getImage(SharedImages.ACTIVE_PART);
    this.setImage(_image);
  }
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        GridLayout _newGridLayout = swtExtensions.newGridLayout();
        it.setLayout(_newGridLayout);
        final Procedure1<Group> _function = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Tab Header && Body");
            final Procedure1<GridData> _function = new Procedure1<GridData>() {
              public void apply(final GridData it) {
              }
            };
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL(_function);
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function_1 = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 4;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function_1);
            it.setLayout(_newGridLayout);
            final Procedure1<Label> _function_2 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Header Fill");
              }
            };
            swtExtensions.newLabel(it, _function_2);
            final Procedure1<GradientEdit> _function_3 = new Procedure1<GradientEdit>() {
              public void apply(final GradientEdit it) {
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function);
              }
            };
            GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_3);
            PartStackPage.this.headerBackgroundEdit = _newGradientEdit;
            final Procedure1<Button> _function_4 = new Procedure1<Button>() {
              public void apply(final Button it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 2;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            helper.appendOrderLockButton(PartStackPage.this.headerBackgroundEdit, _function_4);
            final Procedure1<Label> _function_5 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Border");
              }
            };
            swtExtensions.newLabel(it, _function_5);
            final Procedure1<GradientEdit> _function_6 = new Procedure1<GradientEdit>() {
              public void apply(final GradientEdit it) {
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function);
              }
            };
            GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_6);
            PartStackPage.this.borderEdit = _newGradientEdit_1;
            final Procedure1<Button> _function_7 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(PartStackPage.this.borderEdit, _function_7);
            final Procedure1<Button> _function_8 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Hide");
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            Button _newCheckbox = swtExtensions.newCheckbox(it, _function_8);
            PartStackPage.this.hideBorderButton = _newCheckbox;
            final Procedure1<Label> _function_9 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Background");
              }
            };
            swtExtensions.newLabel(it, _function_9);
            final Procedure1<ColorWell> _function_10 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function);
              }
            };
            ColorWell _newColorWell = helper.newColorWell(it, _function_10);
            PartStackPage.this.bodyBackgroundEdit = _newColorWell;
          }
        };
        swtExtensions.newGroup(it, _function);
        final Procedure1<TabFolder> _function_1 = new Procedure1<TabFolder>() {
          public void apply(final TabFolder it) {
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<TabItem> _function = new Procedure1<TabItem>() {
              public void apply(final TabItem it) {
                it.setText("Selected");
                final Procedure1<Composite> _function = new Procedure1<Composite>() {
                  public void apply(final Composite it) {
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
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                        it.setLayoutData(_FILL_HORIZONTAL);
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                      }
                    };
                    GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
                    PartStackPage.this.selectedBackgroundEdit = _newGradientEdit;
                    final Procedure1<Button> _function_3 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                        final Procedure1<GridData> _function = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 2;
                          }
                        };
                        GridData _newGridData = swtExtensions.newGridData(_function);
                        it.setLayoutData(_newGridData);
                      }
                    };
                    helper.appendOrderLockButton(PartStackPage.this.selectedBackgroundEdit, _function_3);
                    final Procedure1<Label> _function_4 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Border");
                      }
                    };
                    swtExtensions.newLabel(it, _function_4);
                    final Procedure1<GradientEdit> _function_5 = new Procedure1<GradientEdit>() {
                      public void apply(final GradientEdit it) {
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                        it.setLayoutData(_FILL_HORIZONTAL);
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                      }
                    };
                    GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_5);
                    PartStackPage.this.selectedBorderEdit = _newGradientEdit_1;
                    final Procedure1<Button> _function_6 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                      }
                    };
                    helper.appendOrderLockButton(PartStackPage.this.selectedBorderEdit, _function_6);
                    final Procedure1<Button> _function_7 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                        it.setText("Hide");
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnSelection(it, _function);
                      }
                    };
                    Button _newCheckbox = swtExtensions.newCheckbox(it, _function_7);
                    PartStackPage.this.hideSelectedBorderButton = _newCheckbox;
                    final Procedure1<Label> _function_8 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Text");
                      }
                    };
                    swtExtensions.newLabel(it, _function_8);
                    final Procedure1<ColorWell> _function_9 = new Procedure1<ColorWell>() {
                      public void apply(final ColorWell it) {
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                        final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 3;
                          }
                        };
                        GridData _newGridData = swtExtensions.newGridData(_function_1);
                        it.setLayoutData(_newGridData);
                      }
                    };
                    ColorWell _newColorWell = helper.newColorWell(it, _function_9);
                    PartStackPage.this.selectedTextColorWell = _newColorWell;
                    final Procedure1<Label> _function_10 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Text Shadow");
                      }
                    };
                    swtExtensions.newLabel(it, _function_10);
                    TextShadowEdit _textShadowEdit = new TextShadowEdit(it);
                    final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TextShadowEdit> _function_11 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TextShadowEdit>() {
                      public void apply(final TextShadowEdit it) {
                        Composite _control = it.getControl();
                        final Procedure1<GridData> _function = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 3;
                          }
                        };
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL(_function);
                        _control.setLayoutData(_FILL_HORIZONTAL);
                        final Procedure1<TextShadowEdit> _function_1 = new Procedure1<TextShadowEdit>() {
                          public void apply(final TextShadowEdit it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        it.setModifyHandler(_function_1);
                      }
                    };
                    TextShadowEdit _doubleArrow = ObjectExtensions.<TextShadowEdit>operator_doubleArrow(_textShadowEdit, _function_11);
                    PartStackPage.this.selectedShadowEdit = _doubleArrow;
                  }
                };
                swtExtensions.newComposite(it, _function);
              }
            };
            swtExtensions.newTabItem(it, _function);
            final Procedure1<TabItem> _function_1 = new Procedure1<TabItem>() {
              public void apply(final TabItem it) {
                it.setText("Unselected");
                final Procedure1<Composite> _function = new Procedure1<Composite>() {
                  public void apply(final Composite it) {
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
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                        it.setLayoutData(_FILL_HORIZONTAL);
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                      }
                    };
                    GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
                    PartStackPage.this.unselectedBackgroundEdit = _newGradientEdit;
                    final Procedure1<Button> _function_3 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                      }
                    };
                    helper.appendOrderLockButton(PartStackPage.this.unselectedBackgroundEdit, _function_3);
                    final Procedure1<Button> _function_4 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                        it.setText("Hide");
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnSelection(it, _function);
                      }
                    };
                    Button _newCheckbox = swtExtensions.newCheckbox(it, _function_4);
                    PartStackPage.this.hideUnselectedBackgroundButton = _newCheckbox;
                    final Procedure1<Label> _function_5 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Border");
                      }
                    };
                    swtExtensions.newLabel(it, _function_5);
                    final Procedure1<GradientEdit> _function_6 = new Procedure1<GradientEdit>() {
                      public void apply(final GradientEdit it) {
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                        it.setLayoutData(_FILL_HORIZONTAL);
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                      }
                    };
                    GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_6);
                    PartStackPage.this.unselectedBorderEdit = _newGradientEdit_1;
                    final Procedure1<Button> _function_7 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                      }
                    };
                    helper.appendOrderLockButton(PartStackPage.this.unselectedBorderEdit, _function_7);
                    final Procedure1<Button> _function_8 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                        it.setText("Hide");
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnSelection(it, _function);
                      }
                    };
                    Button _newCheckbox_1 = swtExtensions.newCheckbox(it, _function_8);
                    PartStackPage.this.hideUnselectedBorderButton = _newCheckbox_1;
                    final Procedure1<Label> _function_9 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Text");
                      }
                    };
                    swtExtensions.newLabel(it, _function_9);
                    final Procedure1<ColorWell> _function_10 = new Procedure1<ColorWell>() {
                      public void apply(final ColorWell it) {
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                        final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 3;
                          }
                        };
                        GridData _newGridData = swtExtensions.newGridData(_function_1);
                        it.setLayoutData(_newGridData);
                      }
                    };
                    ColorWell _newColorWell = helper.newColorWell(it, _function_10);
                    PartStackPage.this.unselectedTextColorWell = _newColorWell;
                    final Procedure1<Label> _function_11 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Text Shadow");
                      }
                    };
                    swtExtensions.newLabel(it, _function_11);
                    TextShadowEdit _textShadowEdit = new TextShadowEdit(it);
                    final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TextShadowEdit> _function_12 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TextShadowEdit>() {
                      public void apply(final TextShadowEdit it) {
                        Composite _control = it.getControl();
                        final Procedure1<GridData> _function = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 3;
                          }
                        };
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL(_function);
                        _control.setLayoutData(_FILL_HORIZONTAL);
                        final Procedure1<TextShadowEdit> _function_1 = new Procedure1<TextShadowEdit>() {
                          public void apply(final TextShadowEdit it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        it.setModifyHandler(_function_1);
                      }
                    };
                    TextShadowEdit _doubleArrow = ObjectExtensions.<TextShadowEdit>operator_doubleArrow(_textShadowEdit, _function_12);
                    PartStackPage.this.unselectedShadowEdit = _doubleArrow;
                  }
                };
                swtExtensions.newComposite(it, _function);
              }
            };
            swtExtensions.newTabItem(it, _function_1);
            final Procedure1<TabItem> _function_2 = new Procedure1<TabItem>() {
              public void apply(final TabItem it) {
                it.setText("Hover");
                final Procedure1<Composite> _function = new Procedure1<Composite>() {
                  public void apply(final Composite it) {
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
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                        it.setLayoutData(_FILL_HORIZONTAL);
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                      }
                    };
                    GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
                    PartStackPage.this.hoverBackgroundEdit = _newGradientEdit;
                    final Procedure1<Button> _function_3 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                      }
                    };
                    helper.appendOrderLockButton(PartStackPage.this.hoverBackgroundEdit, _function_3);
                    final Procedure1<Button> _function_4 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                        it.setText("Hide");
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnSelection(it, _function);
                      }
                    };
                    Button _newCheckbox = swtExtensions.newCheckbox(it, _function_4);
                    PartStackPage.this.hideHoverBackgroundButton = _newCheckbox;
                    final Procedure1<Label> _function_5 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Border");
                      }
                    };
                    swtExtensions.newLabel(it, _function_5);
                    final Procedure1<GradientEdit> _function_6 = new Procedure1<GradientEdit>() {
                      public void apply(final GradientEdit it) {
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                        it.setLayoutData(_FILL_HORIZONTAL);
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                      }
                    };
                    GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_6);
                    PartStackPage.this.hoverBorderEdit = _newGradientEdit_1;
                    final Procedure1<Button> _function_7 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                      }
                    };
                    helper.appendOrderLockButton(PartStackPage.this.hoverBorderEdit, _function_7);
                    final Procedure1<Button> _function_8 = new Procedure1<Button>() {
                      public void apply(final Button it) {
                        it.setText("Hide");
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnSelection(it, _function);
                      }
                    };
                    Button _newCheckbox_1 = swtExtensions.newCheckbox(it, _function_8);
                    PartStackPage.this.hideHoverBorderButton = _newCheckbox_1;
                    final Procedure1<Label> _function_9 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Text");
                      }
                    };
                    swtExtensions.newLabel(it, _function_9);
                    final Procedure1<ColorWell> _function_10 = new Procedure1<ColorWell>() {
                      public void apply(final ColorWell it) {
                        final Listener _function = new Listener() {
                          public void handleEvent(final Event it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        swtExtensions.setOnModified(it, _function);
                        final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 3;
                          }
                        };
                        GridData _newGridData = swtExtensions.newGridData(_function_1);
                        it.setLayoutData(_newGridData);
                      }
                    };
                    ColorWell _newColorWell = helper.newColorWell(it, _function_10);
                    PartStackPage.this.hoverTextColorWell = _newColorWell;
                    final Procedure1<Label> _function_11 = new Procedure1<Label>() {
                      public void apply(final Label it) {
                        it.setText("Text Shadow");
                      }
                    };
                    swtExtensions.newLabel(it, _function_11);
                    TextShadowEdit _textShadowEdit = new TextShadowEdit(it);
                    final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TextShadowEdit> _function_12 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TextShadowEdit>() {
                      public void apply(final TextShadowEdit it) {
                        Composite _control = it.getControl();
                        final Procedure1<GridData> _function = new Procedure1<GridData>() {
                          public void apply(final GridData it) {
                            it.horizontalSpan = 3;
                          }
                        };
                        GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL(_function);
                        _control.setLayoutData(_FILL_HORIZONTAL);
                        final Procedure1<TextShadowEdit> _function_1 = new Procedure1<TextShadowEdit>() {
                          public void apply(final TextShadowEdit it) {
                            helper.requestFastUpdatePreview();
                          }
                        };
                        it.setModifyHandler(_function_1);
                      }
                    };
                    TextShadowEdit _doubleArrow = ObjectExtensions.<TextShadowEdit>operator_doubleArrow(_textShadowEdit, _function_12);
                    PartStackPage.this.hoverShadowEdit = _doubleArrow;
                  }
                };
                swtExtensions.newComposite(it, _function);
              }
            };
            swtExtensions.newTabItem(it, _function_2);
          }
        };
        swtExtensions.newTabFolder(it, _function_1);
        final Procedure1<Composite> _function_2 = new Procedure1<Composite>() {
          public void apply(final Composite it) {
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 5;
                it.marginWidth = 0;
                it.marginHeight = 0;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<Label> _function_1 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Close Button Color");
              }
            };
            swtExtensions.newLabel(it, _function_1);
            final Procedure1<ColorWell> _function_2 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function);
              }
            };
            ColorWell _newColorWell = helper.newColorWell(it, _function_2);
            PartStackPage.this.closeButtonColorWell = _newColorWell;
            final Procedure1<ColorWell> _function_3 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function);
              }
            };
            ColorWell _newColorWell_1 = helper.newColorWell(it, _function_3);
            PartStackPage.this.closeButtonHoverColorWell = _newColorWell_1;
            final Procedure1<ColorWell> _function_4 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestUpdatePreview();
                  }
                };
                swtExtensions.setOnModified(it, _function);
              }
            };
            ColorWell _newColorWell_2 = helper.newColorWell(it, _function_4);
            PartStackPage.this.closeButtonActiveColorWell = _newColorWell_2;
            final Procedure1<Label> _function_5 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("(Normal, Hover, Active)");
              }
            };
            swtExtensions.newLabel(it, _function_5);
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Close Button Line Width");
              }
            };
            swtExtensions.newLabel(it, _function_6);
            LineWidthEditor _lineWidthEditor = new LineWidthEditor(it);
            final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<LineWidthEditor> _function_7 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<LineWidthEditor>() {
              public void apply(final LineWidthEditor it) {
                Composite _control = it.getControl();
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 4;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                _control.setLayoutData(_newGridData);
                final Procedure1<Integer> _function_1 = new Procedure1<Integer>() {
                  public void apply(final Integer it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                it.setSelectionHandler(_function_1);
              }
            };
            LineWidthEditor _doubleArrow = ObjectExtensions.<LineWidthEditor>operator_doubleArrow(_lineWidthEditor, _function_7);
            PartStackPage.this.closeButtonLineEdit = _doubleArrow;
            final Procedure1<Label> _function_8 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Chevron Color");
              }
            };
            swtExtensions.newLabel(it, _function_8);
            final Procedure1<ColorWell> _function_9 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalSpan = 4;
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
            ColorWell _newColorWell_3 = helper.newColorWell(it, _function_9);
            PartStackPage.this.chevronColorWell = _newColorWell_3;
          }
        };
        swtExtensions.newComposite(it, _function_2);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Gradient _selection = this.headerBackgroundEdit.getSelection();
    List<HSB> _asSWTSafeHSBArray = helper.asSWTSafeHSBArray(_selection);
    boolean _matches = helper.matches(this.headerBackgroundColors, ((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray, HSB.class)));
    boolean _not = (!_matches);
    if (_not) {
      Gradient _selection_1 = this.headerBackgroundEdit.getSelection();
      List<HSB> _asSWTSafeHSBArray_1 = helper.asSWTSafeHSBArray(_selection_1);
      Color[] newBackgroundColors = swtExtensions.createColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_1, HSB.class)));
      Gradient _selection_2 = this.headerBackgroundEdit.getSelection();
      List<Integer> _asSWTSafePercentArray = helper.asSWTSafePercentArray(_selection_2);
      folder.setBackground(newBackgroundColors, ((int[])Conversions.unwrapArray(_asSWTSafePercentArray, int.class)), true);
      swtExtensions.<Color>safeDispose(this.headerBackgroundColors);
      this.headerBackgroundColors = newBackgroundColors;
    } else {
      Gradient _selection_3 = this.headerBackgroundEdit.getSelection();
      List<Integer> _asSWTSafePercentArray_1 = helper.asSWTSafePercentArray(_selection_3);
      folder.setBackground(this.headerBackgroundColors, ((int[])Conversions.unwrapArray(_asSWTSafePercentArray_1, int.class)), true);
    }
    HSB _selection_4 = this.bodyBackgroundEdit.getSelection();
    boolean _matches_1 = helper.matches(this.bodyBackgroundColor, _selection_4);
    boolean _not_1 = (!_matches_1);
    if (_not_1) {
      HSB _selection_5 = this.bodyBackgroundEdit.getSelection();
      Color newBodyBackground = swtExtensions.newColor(_selection_5);
      folder.setBackground(newBodyBackground);
      swtExtensions.<Color>safeDispose(this.bodyBackgroundColor);
      this.bodyBackgroundColor = newBodyBackground;
    }
    boolean _selection_6 = this.hideBorderButton.getSelection();
    boolean _equals = (_selection_6 == false);
    if (_equals) {
      Gradient _selection_7 = this.borderEdit.getSelection();
      List<HSB> _asSWTSafeHSBArray_2 = helper.asSWTSafeHSBArray(_selection_7);
      renderSettings.setBorderColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_2, HSB.class)));
      Gradient _selection_8 = this.borderEdit.getSelection();
      List<Integer> _asSWTSafePercentArray_2 = helper.asSWTSafePercentArray(_selection_8);
      renderSettings.setBorderPercents(((int[])Conversions.unwrapArray(_asSWTSafePercentArray_2, int.class)));
    } else {
      renderSettings.setBorderColors(null);
      renderSettings.setBorderPercents(null);
    }
    {
      boolean _selection_9 = this.hideUnselectedBackgroundButton.getSelection();
      boolean _equals_1 = (_selection_9 == false);
      if (_equals_1) {
        Gradient _selection_10 = this.unselectedBackgroundEdit.getSelection();
        List<HSB> _asSWTSafeHSBArray_3 = helper.asSWTSafeHSBArray(_selection_10);
        renderSettings.setUnselectedBackgroundColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_3, HSB.class)));
        Gradient _selection_11 = this.unselectedBackgroundEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_3 = helper.asSWTSafePercentArray(_selection_11);
        renderSettings.setUnselectedBackgroundPercents(((int[])Conversions.unwrapArray(_asSWTSafePercentArray_3, int.class)));
      } else {
        renderSettings.setUnselectedBackgroundColors(null);
        renderSettings.setUnselectedBackgroundPercents(null);
      }
      boolean _selection_12 = this.hideUnselectedBorderButton.getSelection();
      boolean _equals_2 = (_selection_12 == false);
      if (_equals_2) {
        Gradient _selection_13 = this.unselectedBorderEdit.getSelection();
        List<HSB> _asSWTSafeHSBArray_4 = helper.asSWTSafeHSBArray(_selection_13);
        renderSettings.setUnselectedBorderColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_4, HSB.class)));
        Gradient _selection_14 = this.unselectedBorderEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_4 = helper.asSWTSafePercentArray(_selection_14);
        renderSettings.setUnselectedBorderPercents(((int[])Conversions.unwrapArray(_asSWTSafePercentArray_4, int.class)));
      } else {
        renderSettings.setUnselectedBorderColors(null);
        renderSettings.setUnselectedBorderPercents(null);
      }
      HSB _selection_15 = this.unselectedTextColorWell.getSelection();
      Color newUnselectedForeground = swtExtensions.createColor(_selection_15);
      folder.setForeground(newUnselectedForeground);
      swtExtensions.<Color>safeDispose(this.unselectedForeground);
      this.unselectedForeground = newUnselectedForeground;
      HSB _color = this.unselectedShadowEdit.getColor();
      renderSettings.setUnselectedTextShadowColor(_color);
      Point _shadowPosition = this.unselectedShadowEdit.getShadowPosition();
      renderSettings.setUnselectedTextShadowPosition(_shadowPosition);
    }
    {
      Gradient _selection_9 = this.selectedBackgroundEdit.getSelection();
      List<HSB> _asSWTSafeHSBArray_3 = helper.asSWTSafeHSBArray(_selection_9);
      boolean _matches_2 = helper.matches(this.selectedBackgroundColors, ((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_3, HSB.class)));
      boolean _not_2 = (!_matches_2);
      if (_not_2) {
        Gradient _selection_10 = this.selectedBackgroundEdit.getSelection();
        List<HSB> _asSWTSafeHSBArray_4 = helper.asSWTSafeHSBArray(_selection_10);
        Color[] newSelectedBackgroundColors = swtExtensions.createColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_4, HSB.class)));
        swtExtensions.<Color>safeDispose(this.selectedBackgroundColors);
        Gradient _selection_11 = this.selectedBackgroundEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_3 = helper.asSWTSafePercentArray(_selection_11);
        folder.setSelectionBackground(newSelectedBackgroundColors, ((int[])Conversions.unwrapArray(_asSWTSafePercentArray_3, int.class)), true);
        this.selectedBackgroundColors = newSelectedBackgroundColors;
      } else {
        Gradient _selection_12 = this.selectedBackgroundEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_4 = helper.asSWTSafePercentArray(_selection_12);
        folder.setSelectionBackground(this.selectedBackgroundColors, ((int[])Conversions.unwrapArray(_asSWTSafePercentArray_4, int.class)), true);
      }
      HSB _selection_13 = this.selectedTextColorWell.getSelection();
      Color newSelectedForeground = swtExtensions.createColor(_selection_13);
      swtExtensions.<Color>safeDispose(this.selectedForeground);
      folder.setSelectionForeground(newSelectedForeground);
      this.selectedForeground = newSelectedForeground;
      boolean _selection_14 = this.hideSelectedBorderButton.getSelection();
      if (_selection_14) {
        renderSettings.setSelectedBorderColors(null);
        renderSettings.setSelectedBorderPercents(null);
      } else {
        Gradient _selection_15 = this.selectedBorderEdit.getSelection();
        List<HSB> _asSWTSafeHSBArray_5 = helper.asSWTSafeHSBArray(_selection_15);
        renderSettings.setSelectedBorderColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_5, HSB.class)));
        Gradient _selection_16 = this.selectedBorderEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_5 = helper.asSWTSafePercentArray(_selection_16);
        renderSettings.setSelectedBorderPercents(((int[])Conversions.unwrapArray(_asSWTSafePercentArray_5, int.class)));
      }
      HSB _color = this.selectedShadowEdit.getColor();
      renderSettings.setSelectedTextShadowColor(_color);
      Point _shadowPosition = this.selectedShadowEdit.getShadowPosition();
      renderSettings.setSelectedTextShadowPosition(_shadowPosition);
    }
    {
      boolean _selection_9 = this.hideHoverBackgroundButton.getSelection();
      if (_selection_9) {
        renderSettings.setHoverBackgroundColors(null);
        renderSettings.setHoverBackgroundPercents(null);
      } else {
        Gradient _selection_10 = this.hoverBackgroundEdit.getSelection();
        List<HSB> _asSWTSafeHSBArray_3 = helper.asSWTSafeHSBArray(_selection_10);
        renderSettings.setHoverBackgroundColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_3, HSB.class)));
        Gradient _selection_11 = this.hoverBackgroundEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_3 = helper.asSWTSafePercentArray(_selection_11);
        renderSettings.setHoverBackgroundPercents(((int[])Conversions.unwrapArray(_asSWTSafePercentArray_3, int.class)));
      }
      boolean _selection_12 = this.hideHoverBorderButton.getSelection();
      if (_selection_12) {
        renderSettings.setHoverBorderColors(null);
      } else {
        Gradient _selection_13 = this.hoverBorderEdit.getSelection();
        List<HSB> _asSWTSafeHSBArray_4 = helper.asSWTSafeHSBArray(_selection_13);
        renderSettings.setHoverBorderColors(((HSB[])Conversions.unwrapArray(_asSWTSafeHSBArray_4, HSB.class)));
        Gradient _selection_14 = this.hoverBorderEdit.getSelection();
        List<Integer> _asSWTSafePercentArray_4 = helper.asSWTSafePercentArray(_selection_14);
        renderSettings.setHoverBorderPercents(((int[])Conversions.unwrapArray(_asSWTSafePercentArray_4, int.class)));
      }
      HSB _selection_15 = this.hoverTextColorWell.getSelection();
      renderSettings.setHoverForgroundColor(_selection_15);
      HSB _color = this.hoverShadowEdit.getColor();
      renderSettings.setHoverTextShadowColor(_color);
      Point _shadowPosition = this.hoverShadowEdit.getShadowPosition();
      renderSettings.setHoverTextShadowPosition(_shadowPosition);
    }
    {
      HSB _selection_9 = this.closeButtonColorWell.getSelection();
      renderSettings.setCloseButtonColor(_selection_9);
      HSB _selection_10 = this.closeButtonActiveColorWell.getSelection();
      renderSettings.setCloseButtonActiveColor(_selection_10);
      HSB _selection_11 = this.closeButtonHoverColorWell.getSelection();
      renderSettings.setCloseButtonHotColor(_selection_11);
      int _selection_12 = this.closeButtonLineEdit.getSelection();
      renderSettings.setCloseButtonLineWidth(_selection_12);
    }
    HSB _selection_9 = this.chevronColorWell.getSelection();
    renderSettings.setChevronColor(_selection_9);
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    String _context = this.getContext();
    final Procedure1<JThemePreferenceStore> _function = new Procedure1<JThemePreferenceStore>() {
      public void apply(final JThemePreferenceStore it) {
        Gradient borderColor = it.getGradient(JTPConstants.PartStack.BORDER_COLOR);
        boolean _notEquals = (!Objects.equal(borderColor, null));
        if (_notEquals) {
          PartStackPage.this.borderEdit.setSelection(borderColor);
        }
        Gradient headerBackground = it.getGradient(JTPConstants.PartStack.HEADER_BACKGROUND_COLOR);
        boolean _notEquals_1 = (!Objects.equal(headerBackground, null));
        if (_notEquals_1) {
          PartStackPage.this.headerBackgroundEdit.setSelection(headerBackground);
        }
        HSB bodyBackground = it.getHSB(JTPConstants.PartStack.BODY_BACKGROUND_COLOR);
        boolean _notEquals_2 = (!Objects.equal(bodyBackground, null));
        if (_notEquals_2) {
          PartStackPage.this.bodyBackgroundEdit.setSelection(bodyBackground);
        }
        boolean _boolean = it.getBoolean(JTPConstants.PartStack.BORDER_SHOW);
        boolean _not = (!_boolean);
        PartStackPage.this.hideBorderButton.setSelection(_not);
        {
          Gradient selectedBackground = it.getGradient(JTPConstants.PartStack.SELECTED_FILL_COLOR);
          boolean _notEquals_3 = (!Objects.equal(selectedBackground, null));
          if (_notEquals_3) {
            PartStackPage.this.selectedBackgroundEdit.setSelection(selectedBackground);
          }
          Gradient selectedBorder = it.getGradient(JTPConstants.PartStack.SELECTED_BORDER_COLOR);
          boolean _notEquals_4 = (!Objects.equal(selectedBorder, null));
          if (_notEquals_4) {
            PartStackPage.this.selectedBorderEdit.setSelection(selectedBorder);
          }
          boolean _boolean_1 = it.getBoolean(JTPConstants.PartStack.SELECTED_BORDER_SHOW);
          boolean _not_1 = (!_boolean_1);
          PartStackPage.this.hideSelectedBorderButton.setSelection(_not_1);
          HSB selectionForeground = it.getHSB(JTPConstants.PartStack.SELECTED_TEXT_COLOR);
          boolean _notEquals_5 = (!Objects.equal(selectionForeground, null));
          if (_notEquals_5) {
            PartStackPage.this.selectedTextColorWell.setSelection(selectionForeground);
          }
          HSB selectedShadowColor = it.getHSB(JTPConstants.PartStack.SELECTED_TEXT_SHADOW_COLOR);
          boolean _notEquals_6 = (!Objects.equal(selectedShadowColor, null));
          if (_notEquals_6) {
            PartStackPage.this.selectedShadowEdit.setColor(selectedShadowColor);
          }
          Point selectedShadowPosition = it.getPoint(JTPConstants.PartStack.SELECTED_TEXT_SHADOW_POSITION);
          boolean _notEquals_7 = (!Objects.equal(selectedShadowPosition, null));
          if (_notEquals_7) {
            PartStackPage.this.selectedShadowEdit.setShadowPosition(selectedShadowPosition);
          }
        }
        {
          Gradient unselectedBackground = it.getGradient(JTPConstants.PartStack.UNSELECTED_FILL_COLOR);
          boolean _notEquals_3 = (!Objects.equal(unselectedBackground, null));
          if (_notEquals_3) {
            PartStackPage.this.unselectedBackgroundEdit.setSelection(unselectedBackground);
          }
          boolean _boolean_1 = it.getBoolean(JTPConstants.PartStack.UNSELECTED_FILL);
          boolean _not_1 = (!_boolean_1);
          PartStackPage.this.hideUnselectedBackgroundButton.setSelection(_not_1);
          Gradient unselectedBorder = it.getGradient(JTPConstants.PartStack.UNSELECTED_BORDER_COLOR);
          boolean _notEquals_4 = (!Objects.equal(unselectedBorder, null));
          if (_notEquals_4) {
            PartStackPage.this.unselectedBorderEdit.setSelection(unselectedBorder);
          }
          boolean _boolean_2 = it.getBoolean(JTPConstants.PartStack.UNSELECTED_BORDER_SHOW);
          boolean _not_2 = (!_boolean_2);
          PartStackPage.this.hideUnselectedBorderButton.setSelection(_not_2);
          HSB foreground = it.getHSB(JTPConstants.PartStack.UNSELECTED_TEXT_COLOR);
          boolean _notEquals_5 = (!Objects.equal(foreground, null));
          if (_notEquals_5) {
            PartStackPage.this.unselectedTextColorWell.setSelection(foreground);
          }
          HSB unselectedShadowColor = it.getHSB(JTPConstants.PartStack.UNSELECTED_TEXT_SHADOW_COLOR);
          boolean _notEquals_6 = (!Objects.equal(unselectedShadowColor, null));
          if (_notEquals_6) {
            PartStackPage.this.unselectedShadowEdit.setColor(unselectedShadowColor);
          }
          Point unselectedShadowPosition = it.getPoint(JTPConstants.PartStack.UNSELECTED_TEXT_SHADOW_POSITION);
          boolean _notEquals_7 = (!Objects.equal(unselectedShadowPosition, null));
          if (_notEquals_7) {
            PartStackPage.this.unselectedShadowEdit.setShadowPosition(unselectedShadowPosition);
          }
        }
        {
          Gradient hoverBackground = it.getGradient(JTPConstants.PartStack.HOVER_FILL_COLOR);
          boolean _notEquals_3 = (!Objects.equal(hoverBackground, null));
          if (_notEquals_3) {
            PartStackPage.this.hoverBackgroundEdit.setSelection(hoverBackground);
          }
          boolean _boolean_1 = it.getBoolean(JTPConstants.PartStack.HOVER_FILL);
          boolean _not_1 = (!_boolean_1);
          PartStackPage.this.hideHoverBackgroundButton.setSelection(_not_1);
          Gradient hoverBorder = it.getGradient(JTPConstants.PartStack.HOVER_BORDER_COLOR);
          boolean _notEquals_4 = (!Objects.equal(hoverBorder, null));
          if (_notEquals_4) {
            PartStackPage.this.hoverBorderEdit.setSelection(hoverBorder);
          }
          boolean _boolean_2 = it.getBoolean(JTPConstants.PartStack.HOVER_BORDER_SHOW);
          boolean _not_2 = (!_boolean_2);
          PartStackPage.this.hideHoverBorderButton.setSelection(_not_2);
          HSB foreground = it.getHSB(JTPConstants.PartStack.HOVER_TEXT_COLOR);
          boolean _notEquals_5 = (!Objects.equal(foreground, null));
          if (_notEquals_5) {
            PartStackPage.this.hoverTextColorWell.setSelection(foreground);
          }
          HSB hoverShadowColor = it.getHSB(JTPConstants.PartStack.HOVER_TEXT_SHADOW_COLOR);
          boolean _notEquals_6 = (!Objects.equal(hoverShadowColor, null));
          if (_notEquals_6) {
            PartStackPage.this.hoverShadowEdit.setColor(hoverShadowColor);
          }
          Point hoverShadowPosition = it.getPoint(JTPConstants.PartStack.HOVER_TEXT_SHADOW_POSITION);
          boolean _notEquals_7 = (!Objects.equal(hoverShadowPosition, null));
          if (_notEquals_7) {
            PartStackPage.this.hoverShadowEdit.setShadowPosition(hoverShadowPosition);
          }
        }
        {
          HSB normal = it.getHSB(JTPConstants.PartStack.CLOSE_BUTTON_COLOR);
          boolean _notEquals_3 = (!Objects.equal(normal, null));
          if (_notEquals_3) {
            PartStackPage.this.closeButtonColorWell.setSelection(normal);
          }
          HSB hot = it.getHSB(JTPConstants.PartStack.CLOSE_BUTTON_HOVER_COLOR);
          boolean _notEquals_4 = (!Objects.equal(hot, null));
          if (_notEquals_4) {
            PartStackPage.this.closeButtonHoverColorWell.setSelection(hot);
          }
          HSB active = it.getHSB(JTPConstants.PartStack.CLOSE_BUTTON_ACTIVE_COLOR);
          boolean _notEquals_5 = (!Objects.equal(active, null));
          if (_notEquals_5) {
            PartStackPage.this.closeButtonActiveColorWell.setSelection(active);
          }
          int _int = it.getInt(JTPConstants.PartStack.CLOSE_BUTTON_LINE_WIDTH);
          PartStackPage.this.closeButtonLineEdit.setSelection(_int);
        }
        HSB chevronColor = it.getHSB(JTPConstants.PartStack.CHEVRON_COLOR);
        boolean _notEquals_3 = (!Objects.equal(chevronColor, null));
        if (_notEquals_3) {
          PartStackPage.this.chevronColorWell.setSelection(chevronColor);
        }
      }
    };
    store.withContext(_context, _function);
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    String _context = this.getContext();
    final Procedure1<JThemePreferenceStore> _function = new Procedure1<JThemePreferenceStore>() {
      public void apply(final JThemePreferenceStore it) {
        Gradient _selection = PartStackPage.this.borderEdit.getSelection();
        it.setValue(JTPConstants.PartStack.BORDER_COLOR, _selection);
        Gradient _selection_1 = PartStackPage.this.headerBackgroundEdit.getSelection();
        it.setValue(JTPConstants.PartStack.HEADER_BACKGROUND_COLOR, _selection_1);
        HSB _selection_2 = PartStackPage.this.bodyBackgroundEdit.getSelection();
        it.setValue(JTPConstants.PartStack.BODY_BACKGROUND_COLOR, _selection_2);
        boolean _selection_3 = PartStackPage.this.hideBorderButton.getSelection();
        boolean _not = (!_selection_3);
        it.setValue(JTPConstants.PartStack.BORDER_SHOW, _not);
        Gradient _selection_4 = PartStackPage.this.selectedBackgroundEdit.getSelection();
        it.setValue(JTPConstants.PartStack.SELECTED_FILL_COLOR, _selection_4);
        Gradient _selection_5 = PartStackPage.this.selectedBorderEdit.getSelection();
        it.setValue(JTPConstants.PartStack.SELECTED_BORDER_COLOR, _selection_5);
        boolean _selection_6 = PartStackPage.this.hideSelectedBorderButton.getSelection();
        boolean _not_1 = (!_selection_6);
        it.setValue(JTPConstants.PartStack.SELECTED_BORDER_SHOW, _not_1);
        HSB _selection_7 = PartStackPage.this.selectedTextColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.SELECTED_TEXT_COLOR, _selection_7);
        HSB _color = PartStackPage.this.selectedShadowEdit.getColor();
        it.setValue(JTPConstants.PartStack.SELECTED_TEXT_SHADOW_COLOR, _color);
        Point _shadowPosition = PartStackPage.this.selectedShadowEdit.getShadowPosition();
        it.setValue(JTPConstants.PartStack.SELECTED_TEXT_SHADOW_POSITION, _shadowPosition);
        Gradient _selection_8 = PartStackPage.this.unselectedBackgroundEdit.getSelection();
        it.setValue(JTPConstants.PartStack.UNSELECTED_FILL_COLOR, _selection_8);
        Gradient _selection_9 = PartStackPage.this.unselectedBorderEdit.getSelection();
        it.setValue(JTPConstants.PartStack.UNSELECTED_BORDER_COLOR, _selection_9);
        boolean _selection_10 = PartStackPage.this.hideUnselectedBorderButton.getSelection();
        boolean _not_2 = (!_selection_10);
        it.setValue(JTPConstants.PartStack.UNSELECTED_BORDER_SHOW, _not_2);
        boolean _selection_11 = PartStackPage.this.hideUnselectedBackgroundButton.getSelection();
        boolean _not_3 = (!_selection_11);
        it.setValue(JTPConstants.PartStack.UNSELECTED_FILL, _not_3);
        HSB _selection_12 = PartStackPage.this.unselectedTextColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.UNSELECTED_TEXT_COLOR, _selection_12);
        HSB _color_1 = PartStackPage.this.unselectedShadowEdit.getColor();
        it.setValue(JTPConstants.PartStack.UNSELECTED_TEXT_SHADOW_COLOR, _color_1);
        Point _shadowPosition_1 = PartStackPage.this.unselectedShadowEdit.getShadowPosition();
        it.setValue(JTPConstants.PartStack.UNSELECTED_TEXT_SHADOW_POSITION, _shadowPosition_1);
        Gradient _selection_13 = PartStackPage.this.hoverBackgroundEdit.getSelection();
        it.setValue(JTPConstants.PartStack.HOVER_FILL_COLOR, _selection_13);
        Gradient _selection_14 = PartStackPage.this.hoverBorderEdit.getSelection();
        it.setValue(JTPConstants.PartStack.HOVER_BORDER_COLOR, _selection_14);
        boolean _selection_15 = PartStackPage.this.hideHoverBorderButton.getSelection();
        boolean _not_4 = (!_selection_15);
        it.setValue(JTPConstants.PartStack.HOVER_BORDER_SHOW, _not_4);
        boolean _selection_16 = PartStackPage.this.hideHoverBackgroundButton.getSelection();
        boolean _not_5 = (!_selection_16);
        it.setValue(JTPConstants.PartStack.HOVER_FILL, _not_5);
        HSB _selection_17 = PartStackPage.this.hoverTextColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.HOVER_TEXT_COLOR, _selection_17);
        HSB _color_2 = PartStackPage.this.hoverShadowEdit.getColor();
        it.setValue(JTPConstants.PartStack.HOVER_TEXT_SHADOW_COLOR, _color_2);
        Point _shadowPosition_2 = PartStackPage.this.hoverShadowEdit.getShadowPosition();
        it.setValue(JTPConstants.PartStack.HOVER_TEXT_SHADOW_POSITION, _shadowPosition_2);
        HSB _selection_18 = PartStackPage.this.closeButtonColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.CLOSE_BUTTON_COLOR, _selection_18);
        HSB _selection_19 = PartStackPage.this.closeButtonActiveColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.CLOSE_BUTTON_ACTIVE_COLOR, _selection_19);
        HSB _selection_20 = PartStackPage.this.closeButtonHoverColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.CLOSE_BUTTON_HOVER_COLOR, _selection_20);
        int _selection_21 = PartStackPage.this.closeButtonLineEdit.getSelection();
        it.setValue(JTPConstants.PartStack.CLOSE_BUTTON_LINE_WIDTH, _selection_21);
        HSB _selection_22 = PartStackPage.this.chevronColorWell.getSelection();
        it.setValue(JTPConstants.PartStack.CHEVRON_COLOR, _selection_22);
      }
    };
    store.withContext(_context, _function);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    swtExtensions.<Color>safeDispose(this.unselectedForeground);
    swtExtensions.<Color>safeDispose(this.selectedForeground);
    swtExtensions.<Color>safeDispose(this.headerBackgroundColors);
    swtExtensions.<Color>safeDispose(this.bodyBackgroundColor);
    swtExtensions.<Color>safeDispose(this.selectedBackgroundColors);
  }
  
  @Pure
  public String getContext() {
    return this._context;
  }
  
  public void setContext(final String context) {
    this._context = context;
  }
}
