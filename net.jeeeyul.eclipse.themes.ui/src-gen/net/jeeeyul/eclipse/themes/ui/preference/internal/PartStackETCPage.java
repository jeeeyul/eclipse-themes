package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import java.util.Locale;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
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
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.Scale;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class PartStackETCPage extends AbstractJTPreferencePage {
  private GradientEdit emptyFillEdit;
  
  private GradientEdit emptyBorderEdit;
  
  private Button emptyBorderHideEdit;
  
  private GradientEdit editorsFillEdit;
  
  private GradientEdit editorsBorderEdit;
  
  private Button editorsBorderHideEdit;
  
  private ColorWell dragFeedbackColorWell;
  
  private Scale dragFeedbackOpacityScale;
  
  private Label dragFeedbackOpacityLabel;
  
  private Button mruVisibilityButton;
  
  public PartStackETCPage() {
    super("Others");
    Image _image = SharedImages.getImage(SharedImages.MAXMIZE);
    this.setImage(_image);
  }
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        GridLayout _newGridLayout = swtExtensions.newGridLayout();
        it.setLayout(_newGridLayout);
        final Procedure1<Group> _function = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Empty Part Stack");
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
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
              }
            };
            GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
            PartStackETCPage.this.emptyFillEdit = _newGradientEdit;
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
            helper.appendOrderLockButton(PartStackETCPage.this.emptyFillEdit, _function_3);
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
              }
            };
            GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_5);
            PartStackETCPage.this.emptyBorderEdit = _newGradientEdit_1;
            final Procedure1<Button> _function_6 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(PartStackETCPage.this.emptyBorderEdit, _function_6);
            final Procedure1<Button> _function_7 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Hide");
              }
            };
            Button _newCheckbox = swtExtensions.newCheckbox(it, _function_7);
            PartStackETCPage.this.emptyBorderHideEdit = _newCheckbox;
          }
        };
        swtExtensions.newGroup(it, _function);
        final Procedure1<Group> _function_1 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Editors Part Stack");
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
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
              }
            };
            GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
            PartStackETCPage.this.editorsFillEdit = _newGradientEdit;
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
            helper.appendOrderLockButton(PartStackETCPage.this.editorsFillEdit, _function_3);
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
              }
            };
            GradientEdit _newGradientEdit_1 = helper.newGradientEdit(it, _function_5);
            PartStackETCPage.this.editorsBorderEdit = _newGradientEdit_1;
            final Procedure1<Button> _function_6 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(PartStackETCPage.this.editorsBorderEdit, _function_6);
            final Procedure1<Button> _function_7 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Hide");
              }
            };
            Button _newCheckbox = swtExtensions.newCheckbox(it, _function_7);
            PartStackETCPage.this.editorsBorderHideEdit = _newCheckbox;
          }
        };
        swtExtensions.newGroup(it, _function_1);
        final Procedure1<Group> _function_2 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Other Colors");
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
                it.setText("Drag Feedback");
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
            PartStackETCPage.this.dragFeedbackColorWell = _newColorWell;
            final Procedure1<Label> _function_3 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Opacity");
              }
            };
            swtExtensions.newLabel(it, _function_3);
            final Procedure1<Scale> _function_4 = new Procedure1<Scale>() {
              public void apply(final Scale it) {
                it.setMinimum(0);
                it.setMaximum(255);
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    helper.requestFastUpdatePreview();
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            Scale _newScale = swtExtensions.newScale(it, _function_4);
            PartStackETCPage.this.dragFeedbackOpacityScale = _newScale;
            final Procedure1<Label> _function_5 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("100%");
              }
            };
            Label _newLabel = swtExtensions.newLabel(it, _function_5);
            PartStackETCPage.this.dragFeedbackOpacityLabel = _newLabel;
          }
        };
        swtExtensions.newGroup(it, _function_2);
        final Procedure1<Composite> _function_3 = new Procedure1<Composite>() {
          public void apply(final Composite it) {
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 1;
                it.marginHeight = 0;
                it.marginWidth = 0;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<GridData> _function_1 = new Procedure1<GridData>() {
              public void apply(final GridData it) {
              }
            };
            GridData _newGridData = swtExtensions.newGridData(_function_1);
            it.setLayoutData(_newGridData);
            final Procedure1<Button> _function_2 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("MRU Visibility");
              }
            };
            Button _newCheckbox = swtExtensions.newCheckbox(it, _function_2);
            PartStackETCPage.this.mruVisibilityButton = _newCheckbox;
            final String mruLink = "http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fswt%2Fcustom%2FCTabFolder.html&anchor=setMRUVisible(boolean)";
            final Procedure1<Link> _function_3 = new Procedure1<Link>() {
              public void apply(final Link it) {
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalIndent = 18;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
                StringConcatenation _builder = new StringConcatenation();
                _builder.append("Get details from <a href=\"");
                _builder.append(mruLink, "");
                _builder.append("\">JavaDoc of CTabFolder</a>");
                it.setText(_builder.toString());
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
        };
        swtExtensions.newComposite(it, _function_3);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    int _selection = this.dragFeedbackOpacityScale.getSelection();
    double _divide = (_selection / 255.0);
    double _multiply = (_divide * 100);
    String percentText = String.format(Locale.ENGLISH, "%3.0f %%", Double.valueOf(_multiply));
    this.dragFeedbackOpacityLabel.setText(percentText);
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Gradient emptyFill = store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR);
    boolean _notEquals = (!Objects.equal(emptyFill, null));
    if (_notEquals) {
      this.emptyFillEdit.setSelection(emptyFill);
    }
    Gradient emptyBorder = store.getGradient(JTPConstants.EmptyPartStack.BORDER_COLOR);
    boolean _notEquals_1 = (!Objects.equal(emptyBorder, null));
    if (_notEquals_1) {
      this.emptyBorderEdit.setSelection(emptyBorder);
    }
    boolean _boolean = store.getBoolean(JTPConstants.EmptyPartStack.BORDER_SHOW);
    boolean _not = (!_boolean);
    this.emptyBorderHideEdit.setSelection(_not);
    Gradient editorsFill = store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR);
    boolean _notEquals_2 = (!Objects.equal(editorsFill, null));
    if (_notEquals_2) {
      this.editorsFillEdit.setSelection(editorsFill);
    }
    Gradient editrosBorder = store.getGradient(JTPConstants.EditorsPartStack.BORDER_COLOR);
    boolean _notEquals_3 = (!Objects.equal(editrosBorder, null));
    if (_notEquals_3) {
      this.editorsBorderEdit.setSelection(editrosBorder);
    }
    boolean _boolean_1 = store.getBoolean(JTPConstants.EditorsPartStack.BORDER_SHOW);
    boolean _not_1 = (!_boolean_1);
    this.editorsBorderHideEdit.setSelection(_not_1);
    HSB _hSB = store.getHSB(JTPConstants.Others.DRAG_FEEDBACK_COLOR);
    this.dragFeedbackColorWell.setSelection(_hSB);
    int _int = store.getInt(JTPConstants.Others.DRAG_FEEDBACK_ALPHA);
    this.dragFeedbackOpacityScale.setSelection(_int);
    boolean _boolean_2 = store.getBoolean(JTPConstants.Others.MRU_VISIBLE);
    this.mruVisibilityButton.setSelection(_boolean_2);
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Gradient _selection = this.emptyFillEdit.getSelection();
    store.setValue(JTPConstants.EmptyPartStack.FILL_COLOR, _selection);
    Gradient _selection_1 = this.emptyBorderEdit.getSelection();
    store.setValue(JTPConstants.EmptyPartStack.BORDER_COLOR, _selection_1);
    boolean _selection_2 = this.emptyBorderHideEdit.getSelection();
    boolean _not = (!_selection_2);
    store.setValue(JTPConstants.EmptyPartStack.BORDER_SHOW, _not);
    Gradient _selection_3 = this.editorsFillEdit.getSelection();
    store.setValue(JTPConstants.EditorsPartStack.FILL_COLOR, _selection_3);
    Gradient _selection_4 = this.editorsBorderEdit.getSelection();
    store.setValue(JTPConstants.EditorsPartStack.BORDER_COLOR, _selection_4);
    boolean _selection_5 = this.editorsBorderHideEdit.getSelection();
    boolean _not_1 = (!_selection_5);
    store.setValue(JTPConstants.EditorsPartStack.BORDER_SHOW, _not_1);
    HSB _selection_6 = this.dragFeedbackColorWell.getSelection();
    store.setValue(JTPConstants.Others.DRAG_FEEDBACK_COLOR, _selection_6);
    int _selection_7 = this.dragFeedbackOpacityScale.getSelection();
    store.setValue(JTPConstants.Others.DRAG_FEEDBACK_ALPHA, _selection_7);
    boolean _selection_8 = this.mruVisibilityButton.getSelection();
    store.setValue(JTPConstants.Others.MRU_VISIBLE, _selection_8);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
}
