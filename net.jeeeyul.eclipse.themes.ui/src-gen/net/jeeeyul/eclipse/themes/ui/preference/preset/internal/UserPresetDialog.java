package net.jeeeyul.eclipse.themes.ui.preference.preset.internal;

import com.google.common.base.Objects;
import java.util.List;
import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;
import net.jeeeyul.eclipse.themes.ui.preference.preset.internal.PresetLabelProvider;
import net.jeeeyul.eclipse.themes.ui.shared.CollectionContentProvider;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Dialog for #157
 */
@SuppressWarnings("all")
public class UserPresetDialog extends Dialog {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  private Text presetNameField;
  
  private TableViewer presetViewer;
  
  @Accessors
  private String selectedPresetName;
  
  private Button newPresetButton;
  
  private Button existingPresetButton;
  
  private CLabel validationLabel;
  
  public UserPresetDialog(final Shell parentShell) {
    super(parentShell);
    int _or = this._sWTExtensions.operator_or(SWT.RESIZE, SWT.CLOSE);
    int _or_1 = this._sWTExtensions.operator_or(_or, SWT.TITLE);
    this.setShellStyle(_or_1);
  }
  
  public void create() {
    super.create();
    Shell _shell = this.getShell();
    _shell.setText("Save as preset");
  }
  
  protected Control createDialogArea(final Composite parent) {
    Composite _xblockexpression = null;
    {
      final Procedure1<Display> _function = new Procedure1<Display>() {
        public void apply(final Display it) {
          Shell _shell = UserPresetDialog.this.getShell();
          boolean _isDisposed = _shell.isDisposed();
          if (_isDisposed) {
            return;
          }
          UserPresetDialog.this.updateUIStates();
        }
      };
      this._sWTExtensions.schedule(_function);
      Control _createDialogArea = super.createDialogArea(parent);
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Composite> _function_1 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Composite>() {
        public void apply(final Composite it) {
          final Procedure1<Button> _function = new Procedure1<Button>() {
            public void apply(final Button it) {
              it.setText("Save as a new preset");
              it.setSelection(true);
              final Listener _function = new Listener() {
                public void handleEvent(final Event it) {
                  UserPresetDialog.this.updateUIStates();
                }
              };
              UserPresetDialog.this._sWTExtensions.setOnSelection(it, _function);
            }
          };
          Button _newRadioButton = UserPresetDialog.this._sWTExtensions.newRadioButton(it, _function);
          UserPresetDialog.this.newPresetButton = _newRadioButton;
          final Procedure1<Text> _function_1 = new Procedure1<Text>() {
            public void apply(final Text it) {
              final Procedure1<GridData> _function = new Procedure1<GridData>() {
                public void apply(final GridData it) {
                  it.horizontalIndent = 16;
                }
              };
              GridData _FILL_HORIZONTAL = UserPresetDialog.this._sWTExtensions.FILL_HORIZONTAL(_function);
              it.setLayoutData(_FILL_HORIZONTAL);
              it.setMessage("Enter a new preset name");
              final Listener _function_1 = new Listener() {
                public void handleEvent(final Event it) {
                  UserPresetDialog.this.validate();
                  String _text = UserPresetDialog.this.presetNameField.getText();
                  String _trim = _text.trim();
                  UserPresetDialog.this.selectedPresetName = _trim;
                }
              };
              UserPresetDialog.this._sWTExtensions.setOnModified(it, _function_1);
            }
          };
          Text _newTextField = UserPresetDialog.this._sWTExtensions.newTextField(it, _function_1);
          UserPresetDialog.this.presetNameField = _newTextField;
          final Procedure1<Button> _function_2 = new Procedure1<Button>() {
            public void apply(final Button it) {
              it.setText("Save as an existing preset");
              final Listener _function = new Listener() {
                public void handleEvent(final Event it) {
                  UserPresetDialog.this.updateUIStates();
                }
              };
              UserPresetDialog.this._sWTExtensions.setOnSelection(it, _function);
            }
          };
          Button _newRadioButton_1 = UserPresetDialog.this._sWTExtensions.newRadioButton(it, _function_2);
          UserPresetDialog.this.existingPresetButton = _newRadioButton_1;
          int _or = UserPresetDialog.this._sWTExtensions.operator_or(SWT.FULL_SELECTION, SWT.BORDER);
          TableViewer _tableViewer = new TableViewer(it, _or);
          final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TableViewer> _function_3 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TableViewer>() {
            public void apply(final TableViewer it) {
              Control _control = it.getControl();
              final Procedure1<GridData> _function = new Procedure1<GridData>() {
                public void apply(final GridData it) {
                  it.horizontalIndent = 16;
                }
              };
              GridData _FILL_BOTH = UserPresetDialog.this._sWTExtensions.FILL_BOTH(_function);
              _control.setLayoutData(_FILL_BOTH);
              PresetLabelProvider _presetLabelProvider = new PresetLabelProvider();
              it.setLabelProvider(_presetLabelProvider);
              CollectionContentProvider _collectionContentProvider = new CollectionContentProvider();
              it.setContentProvider(_collectionContentProvider);
              JThemesCore _default = JThemesCore.getDefault();
              IJTPresetManager _presetManager = _default.getPresetManager();
              IJTPresetCategory _userCategory = _presetManager.getUserCategory();
              List<IJTPreset> _presets = _userCategory.getPresets();
              it.setInput(_presets);
              final ISelectionChangedListener _function_1 = new ISelectionChangedListener() {
                public void selectionChanged(final SelectionChangedEvent it) {
                  ISelection _selection = UserPresetDialog.this.presetViewer.getSelection();
                  Object _firstElement = ((IStructuredSelection) _selection).getFirstElement();
                  IJTPreset selected = ((IJTPreset) _firstElement);
                  String _name = null;
                  if (selected!=null) {
                    _name=selected.getName();
                  }
                  UserPresetDialog.this.selectedPresetName = _name;
                  UserPresetDialog.this.validate();
                }
              };
              it.addSelectionChangedListener(_function_1);
            }
          };
          TableViewer _doubleArrow = ObjectExtensions.<TableViewer>operator_doubleArrow(_tableViewer, _function_3);
          UserPresetDialog.this.presetViewer = _doubleArrow;
          final Procedure1<CLabel> _function_4 = new Procedure1<CLabel>() {
            public void apply(final CLabel it) {
              GridData _FILL_HORIZONTAL = UserPresetDialog.this._sWTExtensions.FILL_HORIZONTAL();
              it.setLayoutData(_FILL_HORIZONTAL);
            }
          };
          CLabel _newCLabel = UserPresetDialog.this._sWTExtensions.newCLabel(it, _function_4);
          UserPresetDialog.this.validationLabel = _newCLabel;
        }
      };
      _xblockexpression = ObjectExtensions.<Composite>operator_doubleArrow(
        ((Composite) _createDialogArea), _function_1);
    }
    return _xblockexpression;
  }
  
  protected IDialogSettings getDialogBoundsSettings() {
    JThemesCore _default = JThemesCore.getDefault();
    IDialogSettings _dialogSettings = _default.getDialogSettings();
    String _canonicalName = UserPresetDialog.class.getCanonicalName();
    IDialogSettings settings = _dialogSettings.getSection(_canonicalName);
    boolean _equals = Objects.equal(settings, null);
    if (_equals) {
      JThemesCore _default_1 = JThemesCore.getDefault();
      IDialogSettings _dialogSettings_1 = _default_1.getDialogSettings();
      String _canonicalName_1 = UserPresetDialog.class.getCanonicalName();
      IDialogSettings _addNewSection = _dialogSettings_1.addNewSection(_canonicalName_1);
      settings = _addNewSection;
    }
    return settings;
  }
  
  private void updateUIStates() {
    boolean _selection = this.newPresetButton.getSelection();
    this.presetNameField.setEnabled(_selection);
    Control _control = this.presetViewer.getControl();
    boolean _selection_1 = this.existingPresetButton.getSelection();
    _control.setEnabled(_selection_1);
    this.validate();
  }
  
  private void validate() {
    String error = null;
    boolean _selection = this.newPresetButton.getSelection();
    if (_selection) {
      IInputValidator nameValidator = JTPUtil.getPresetNameValidator();
      String _text = this.presetNameField.getText();
      String name = _text.trim();
      String _isValid = nameValidator.isValid(name);
      error = _isValid;
    } else {
      ISelection _selection_1 = this.presetViewer.getSelection();
      Object _firstElement = ((IStructuredSelection) _selection_1).getFirstElement();
      IJTPreset selected = ((IJTPreset) _firstElement);
      boolean _equals = Objects.equal(selected, null);
      if (_equals) {
        error = "Choose a preset to overwrite.";
      }
    }
    Button okButton = this.getButton(IDialogConstants.OK_ID);
    boolean _equals_1 = Objects.equal(error, null);
    if (_equals_1) {
      this.validationLabel.setText("");
      this.validationLabel.setImage(null);
    } else {
      this.validationLabel.setText(error);
      IWorkbench _workbench = PlatformUI.getWorkbench();
      ISharedImages _sharedImages = _workbench.getSharedImages();
      Image _image = _sharedImages.getImage(ISharedImages.IMG_OBJS_WARN_TSK);
      this.validationLabel.setImage(_image);
    }
    boolean _equals_2 = Objects.equal(error, null);
    okButton.setEnabled(_equals_2);
  }
  
  protected Control createButtonBar(final Composite parent) {
    Control _xblockexpression = null;
    {
      final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Composite> _function = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Composite>() {
        public void apply(final Composite it) {
          final Procedure1<Label> _function = new Procedure1<Label>() {
            public void apply(final Label it) {
            }
          };
          UserPresetDialog.this._sWTExtensions.newHorizontalSeparator(it, _function);
        }
      };
      ObjectExtensions.<Composite>operator_doubleArrow(parent, _function);
      _xblockexpression = super.createButtonBar(parent);
    }
    return _xblockexpression;
  }
  
  public static void main(final String[] args) {
    UserPresetDialog _userPresetDialog = new UserPresetDialog(null);
    _userPresetDialog.open();
  }
  
  @Pure
  public String getSelectedPresetName() {
    return this.selectedPresetName;
  }
  
  public void setSelectedPresetName(final String selectedPresetName) {
    this.selectedPresetName = selectedPresetName;
  }
}
