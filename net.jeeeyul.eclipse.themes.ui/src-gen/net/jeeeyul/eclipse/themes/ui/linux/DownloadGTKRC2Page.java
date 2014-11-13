package net.jeeeyul.eclipse.themes.ui.linux;

import java.io.File;
import net.jeeeyul.eclipse.themes.ui.linux.DownloadGTKRC2Wizard;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DownloadGTKRC2Page extends WizardPage {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  private Button overwriteButton;
  
  public DownloadGTKRC2Page() {
    super("Linux GTK Fix");
    this.setTitle("Updates ~/.gtkrc-2.0");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Download and install GTK 2.0 Runtime Configuration");
    this.setDescription(_builder.toString());
  }
  
  public void createControl(final Composite parent) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        GridLayout _newGridLayout = DownloadGTKRC2Page.this._sWTExtensions.newGridLayout();
        it.setLayout(_newGridLayout);
        final Procedure1<Link> _function = new Procedure1<Link>() {
          public void apply(final Link it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("This wizard will download <a href=\"https://github.com/jeeeyul/jeeeyul.github.io/blob/master/files/gtkrc-2.0\">.gtkrc-2.0</a> into your home directory.");
            _builder.newLine();
            _builder.append("It will fix various problems on GTK/SWT, for instance Huge Toolbar.");
            _builder.newLine();
            _builder.newLine();
            _builder.append("After installation, You need to restart eclipse.");
            _builder.newLine();
            _builder.append("Then choose a preset that you want once again.");
            _builder.newLine();
            _builder.newLine();
            _builder.append("Read details in <a href=\"https://github.com/jeeeyul/eclipse-themes/wiki/Linux-User-Guide\">Linux User Guide</a>");
            _builder.newLine();
            it.setText(_builder.toString());
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                Program.launch(it.text);
              }
            };
            DownloadGTKRC2Page.this._sWTExtensions.setOnSelection(it, _function);
          }
        };
        DownloadGTKRC2Page.this._sWTExtensions.newLink(it, _function);
        final Procedure1<Button> _function_1 = new Procedure1<Button>() {
          public void apply(final Button it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Overwrite ");
            String _property = System.getProperty("user.home");
            _builder.append(_property, "");
            _builder.append("/.gtkrc-2.0 if exists.");
            it.setText(_builder.toString());
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                DownloadGTKRC2Page.this.validate();
              }
            };
            DownloadGTKRC2Page.this._sWTExtensions.setOnSelection(it, _function);
          }
        };
        Button _newCheckbox = DownloadGTKRC2Page.this._sWTExtensions.newCheckbox(it, _function_1);
        DownloadGTKRC2Page.this.overwriteButton = _newCheckbox;
      }
    };
    Composite _newComposite = this._sWTExtensions.newComposite(parent, _function);
    this.setControl(_newComposite);
    this.validate();
  }
  
  private void validate() {
    this.setPageComplete(true);
    this.setErrorMessage(null);
    String _property = System.getProperty("user.home");
    File file = new File(_property, ".gtkrc-2.0");
    boolean _and = false;
    boolean _exists = file.exists();
    if (!_exists) {
      _and = false;
    } else {
      boolean _selection = this.overwriteButton.getSelection();
      boolean _equals = (_selection == false);
      _and = _equals;
    }
    if (_and) {
      this.setErrorMessage(".gtkrc-2.0 is already exist.");
      this.setPageComplete(false);
    }
    IWizardContainer _container = this.getContainer();
    _container.updateButtons();
  }
  
  public DownloadGTKRC2Wizard getWizard() {
    IWizard _wizard = super.getWizard();
    return ((DownloadGTKRC2Wizard) _wizard);
  }
}
