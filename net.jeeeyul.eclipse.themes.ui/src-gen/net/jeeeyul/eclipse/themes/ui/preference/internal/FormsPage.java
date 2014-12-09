package net.jeeeyul.eclipse.themes.ui.preference.internal;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class FormsPage extends AbstractJTPreferencePage {
  private GradientEdit formHeadingGradientEdit;
  
  private ColorWell formHeadingTitleTextColorWell;
  
  private ColorWell formHeadingBorder1ColorWell;
  
  private ColorWell formHeadingBorder2ColorWell;
  
  private ColorWell sectionHeaderTintColorWell;
  
  private ColorWell sectionHeaderTitleTextColorWell;
  
  private ColorWell sectionHeaderActiveTitleTextColorWell;
  
  private ColorWell sectionHeaderBorderColorWell;
  
  private ColorWell hyperLinkColorWell;
  
  private ColorWell activeHyperLinkColorWell;
  
  public FormsPage() {
    super("Forms");
    Image _image = SharedImages.getImage(SharedImages.LAYOUT);
    this.setImage(_image);
  }
  
  public Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        GridLayout _newGridLayout = swtExtensions.newGridLayout();
        it.setLayout(_newGridLayout);
        final Procedure1<Group> _function = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Form Heading");
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
            final Procedure1<GradientEdit> _function_2 = new Procedure1<GradientEdit>() {
              public void apply(final GradientEdit it) {
                GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
                it.setLayoutData(_FILL_HORIZONTAL);
              }
            };
            GradientEdit _newGradientEdit = helper.newGradientEdit(it, _function_2);
            FormsPage.this.formHeadingGradientEdit = _newGradientEdit;
            final Procedure1<Button> _function_3 = new Procedure1<Button>() {
              public void apply(final Button it) {
              }
            };
            helper.appendOrderLockButton(FormsPage.this.formHeadingGradientEdit, _function_3);
            final Procedure1<Label> _function_4 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Title Text");
              }
            };
            swtExtensions.newLabel(it, _function_4);
            final Procedure1<ColorWell> _function_5 = new Procedure1<ColorWell>() {
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
            ColorWell _newColorWell = helper.newColorWell(it, _function_5);
            FormsPage.this.formHeadingTitleTextColorWell = _newColorWell;
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Upper Bottom Border");
              }
            };
            swtExtensions.newLabel(it, _function_6);
            final Procedure1<ColorWell> _function_7 = new Procedure1<ColorWell>() {
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
            ColorWell _newColorWell_1 = helper.newColorWell(it, _function_7);
            FormsPage.this.formHeadingBorder1ColorWell = _newColorWell_1;
            final Procedure1<Label> _function_8 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Below Bottom Border");
              }
            };
            swtExtensions.newLabel(it, _function_8);
            final Procedure1<ColorWell> _function_9 = new Procedure1<ColorWell>() {
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
            ColorWell _newColorWell_2 = helper.newColorWell(it, _function_9);
            FormsPage.this.formHeadingBorder2ColorWell = _newColorWell_2;
          }
        };
        swtExtensions.newGroup(it, _function);
        final Procedure1<Group> _function_1 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Section");
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
              public void apply(final GridLayout it) {
                it.numColumns = 5;
              }
            };
            GridLayout _newGridLayout = swtExtensions.newGridLayout(_function);
            it.setLayout(_newGridLayout);
            final Procedure1<Label> _function_1 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Header");
              }
            };
            swtExtensions.newLabel(it, _function_1);
            final Procedure1<Label> _function_2 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Tint");
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalAlignment = SWT.LEFT;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            swtExtensions.newLabel(it, _function_2);
            final Procedure1<ColorWell> _function_3 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
              }
            };
            ColorWell _newColorWell = helper.newColorWell(it, _function_3);
            FormsPage.this.sectionHeaderTintColorWell = _newColorWell;
            final Procedure1<Label> _function_4 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Border");
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalIndent = 20;
                    it.horizontalAlignment = SWT.LEFT;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            swtExtensions.newLabel(it, _function_4);
            final Procedure1<ColorWell> _function_5 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
              }
            };
            ColorWell _newColorWell_1 = helper.newColorWell(it, _function_5);
            FormsPage.this.sectionHeaderBorderColorWell = _newColorWell_1;
            final Procedure1<Label> _function_6 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Title Text");
              }
            };
            swtExtensions.newLabel(it, _function_6);
            final Procedure1<Label> _function_7 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Color");
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalAlignment = SWT.LEFT;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            swtExtensions.newLabel(it, _function_7);
            final Procedure1<ColorWell> _function_8 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
              }
            };
            ColorWell _newColorWell_2 = helper.newColorWell(it, _function_8);
            FormsPage.this.sectionHeaderTitleTextColorWell = _newColorWell_2;
            final Procedure1<Label> _function_9 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Active Color");
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalIndent = 20;
                    it.horizontalAlignment = SWT.LEFT;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            swtExtensions.newLabel(it, _function_9);
            final Procedure1<ColorWell> _function_10 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
              }
            };
            ColorWell _newColorWell_3 = helper.newColorWell(it, _function_10);
            FormsPage.this.sectionHeaderActiveTitleTextColorWell = _newColorWell_3;
            final Procedure1<Label> _function_11 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Hyperlink");
              }
            };
            swtExtensions.newLabel(it, _function_11);
            final Procedure1<Label> _function_12 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Color");
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalAlignment = SWT.LEFT;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            swtExtensions.newLabel(it, _function_12);
            final Procedure1<ColorWell> _function_13 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
              }
            };
            ColorWell _newColorWell_4 = helper.newColorWell(it, _function_13);
            FormsPage.this.hyperLinkColorWell = _newColorWell_4;
            final Procedure1<Label> _function_14 = new Procedure1<Label>() {
              public void apply(final Label it) {
                it.setText("Active Color");
                final Procedure1<GridData> _function = new Procedure1<GridData>() {
                  public void apply(final GridData it) {
                    it.horizontalIndent = 20;
                    it.horizontalAlignment = SWT.LEFT;
                  }
                };
                GridData _newGridData = swtExtensions.newGridData(_function);
                it.setLayoutData(_newGridData);
              }
            };
            swtExtensions.newLabel(it, _function_14);
            final Procedure1<ColorWell> _function_15 = new Procedure1<ColorWell>() {
              public void apply(final ColorWell it) {
              }
            };
            ColorWell _newColorWell_5 = helper.newColorWell(it, _function_15);
            FormsPage.this.activeHyperLinkColorWell = _newColorWell_5;
          }
        };
        swtExtensions.newGroup(it, _function_1);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Gradient _gradient = store.getGradient(JTPConstants.Forms.FORM_HEADING_BACKGROUND);
    this.formHeadingGradientEdit.setSelection(_gradient);
    HSB _hSB = store.getHSB(JTPConstants.Forms.FORM_HEADING_TITLE_COLOR);
    this.formHeadingTitleTextColorWell.setSelection(_hSB);
    HSB _hSB_1 = store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_1_COLOR);
    this.formHeadingBorder1ColorWell.setSelection(_hSB_1);
    HSB _hSB_2 = store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_2_COLOR);
    this.formHeadingBorder2ColorWell.setSelection(_hSB_2);
    HSB _hSB_3 = store.getHSB(JTPConstants.Forms.SECTION_HEADER_TINT_COLOR);
    this.sectionHeaderTintColorWell.setSelection(_hSB_3);
    HSB _hSB_4 = store.getHSB(JTPConstants.Forms.SECTION_HEADER_BORDER_COLOR);
    this.sectionHeaderBorderColorWell.setSelection(_hSB_4);
    HSB _hSB_5 = store.getHSB(JTPConstants.Forms.SECTION_HEADER_TITLE_COLOR);
    this.sectionHeaderTitleTextColorWell.setSelection(_hSB_5);
    HSB _hSB_6 = store.getHSB(JTPConstants.Forms.SECTION_HEADER_ACTIVE_TITLE_COLOR);
    this.sectionHeaderActiveTitleTextColorWell.setSelection(_hSB_6);
    HSB _hSB_7 = store.getHSB(JTPConstants.Forms.HYPER_LINK_COLOR);
    this.hyperLinkColorWell.setSelection(_hSB_7);
    HSB _hSB_8 = store.getHSB(JTPConstants.Forms.ACTIVE_HYPER_LINK_COLOR);
    this.activeHyperLinkColorWell.setSelection(_hSB_8);
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    Gradient _selection = this.formHeadingGradientEdit.getSelection();
    store.setValue(JTPConstants.Forms.FORM_HEADING_BACKGROUND, _selection);
    HSB _selection_1 = this.formHeadingTitleTextColorWell.getSelection();
    store.setValue(JTPConstants.Forms.FORM_HEADING_TITLE_COLOR, _selection_1);
    HSB _selection_2 = this.formHeadingBorder1ColorWell.getSelection();
    store.setValue(JTPConstants.Forms.FORM_HEADING_BORDER_1_COLOR, _selection_2);
    HSB _selection_3 = this.formHeadingBorder2ColorWell.getSelection();
    store.setValue(JTPConstants.Forms.FORM_HEADING_BORDER_2_COLOR, _selection_3);
    HSB _selection_4 = this.sectionHeaderTintColorWell.getSelection();
    store.setValue(JTPConstants.Forms.SECTION_HEADER_TINT_COLOR, _selection_4);
    HSB _selection_5 = this.sectionHeaderBorderColorWell.getSelection();
    store.setValue(JTPConstants.Forms.SECTION_HEADER_BORDER_COLOR, _selection_5);
    HSB _selection_6 = this.sectionHeaderTitleTextColorWell.getSelection();
    store.setValue(JTPConstants.Forms.SECTION_HEADER_TITLE_COLOR, _selection_6);
    HSB _selection_7 = this.sectionHeaderActiveTitleTextColorWell.getSelection();
    store.setValue(JTPConstants.Forms.SECTION_HEADER_ACTIVE_TITLE_COLOR, _selection_7);
    HSB _selection_8 = this.hyperLinkColorWell.getSelection();
    store.setValue(JTPConstants.Forms.HYPER_LINK_COLOR, _selection_8);
    HSB _selection_9 = this.activeHyperLinkColorWell.getSelection();
    store.setValue(JTPConstants.Forms.ACTIVE_HYPER_LINK_COLOR, _selection_9);
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
}
