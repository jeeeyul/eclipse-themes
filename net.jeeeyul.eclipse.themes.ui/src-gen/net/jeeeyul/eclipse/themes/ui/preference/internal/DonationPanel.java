package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.collect.Iterators;
import java.util.Iterator;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

@SuppressWarnings("all")
public class DonationPanel {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  private Control control;
  
  public DonationPanel(final Composite parent) {
    final Procedure1<Composite> _function = new Procedure1<Composite>() {
      public void apply(final Composite it) {
        GridLayout _gridLayout = new GridLayout(2, false);
        it.setLayout(_gridLayout);
        final Procedure1<Label> _function = new Procedure1<Label>() {
          public void apply(final Label it) {
            Image _image = SharedImages.getImage(SharedImages.ECLIPSE);
            it.setImage(_image);
          }
        };
        DonationPanel.this._sWTExtensions.newLabel(it, _function);
        final Procedure1<Link> _function_1 = new Procedure1<Link>() {
          public void apply(final Link it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Don\'t forget to favorite this plugins on <a href=\"https://marketplace.eclipse.org/content/jeeeyuls-eclipse-themes\">Market Place</a>.");
            it.setText(_builder.toString());
          }
        };
        DonationPanel.this._sWTExtensions.newLink(it, _function_1);
        final Procedure1<Label> _function_2 = new Procedure1<Label>() {
          public void apply(final Label it) {
            Image _image = SharedImages.getImage(SharedImages.GITHUB);
            it.setImage(_image);
          }
        };
        DonationPanel.this._sWTExtensions.newLabel(it, _function_2);
        final Procedure1<Link> _function_3 = new Procedure1<Link>() {
          public void apply(final Link it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("You can fork and star this project on <a href=\"https://github.com/jeeeyul/eclipse-themes\">GitHub</a>.");
            it.setText(_builder.toString());
          }
        };
        DonationPanel.this._sWTExtensions.newLink(it, _function_3);
        final Procedure1<Label> _function_4 = new Procedure1<Label>() {
          public void apply(final Label it) {
            Image _image = SharedImages.getImage(SharedImages.PLEDGIE);
            it.setImage(_image);
          }
        };
        DonationPanel.this._sWTExtensions.newLabel(it, _function_4);
        final Procedure1<Link> _function_5 = new Procedure1<Link>() {
          public void apply(final Link it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("If you like this software, please consider a donation through <a href=\"https://pledgie.com/campaigns/18377\">Pledgie</a>.");
            it.setText(_builder.toString());
          }
        };
        DonationPanel.this._sWTExtensions.newLink(it, _function_5);
        Iterator<? extends Widget> _allContent = DonationPanel.this._sWTExtensions.getAllContent(it);
        Iterator<Link> _filter = Iterators.<Link>filter(_allContent, Link.class);
        final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Link> _function_6 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<Link>() {
          public void apply(final Link it) {
            final Listener _function = new Listener() {
              public void handleEvent(final Event it) {
                Program.launch(it.text);
              }
            };
            DonationPanel.this._sWTExtensions.setOnSelection(it, _function);
          }
        };
        IteratorExtensions.<Link>forEach(_filter, _function_6);
      }
    };
    Composite _newComposite = this._sWTExtensions.newComposite(parent, _function);
    this.control = _newComposite;
  }
  
  public static void main(final String[] args) {
    @Extension
    final SWTExtensions SWTExtensions = net.jeeeyul.swtend.SWTExtensions.INSTANCE;
    final Procedure1<Shell> _function = new Procedure1<Shell>() {
      public void apply(final Shell it) {
        GridLayout _newGridLayout = SWTExtensions.newGridLayout();
        it.setLayout(_newGridLayout);
        final Procedure1<Button> _function = new Procedure1<Button>() {
          public void apply(final Button it) {
            it.setText("a");
          }
        };
        SWTExtensions.newPushButton(it, _function);
        DonationPanel _donationPanel = new DonationPanel(it);
        final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<DonationPanel> _function_1 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<DonationPanel>() {
          public void apply(final DonationPanel it) {
            GridData _FILL_HORIZONTAL = SWTExtensions.FILL_HORIZONTAL();
            it.control.setLayoutData(_FILL_HORIZONTAL);
          }
        };
        ObjectExtensions.<DonationPanel>operator_doubleArrow(_donationPanel, _function_1);
        final Procedure1<Button> _function_2 = new Procedure1<Button>() {
          public void apply(final Button it) {
            it.setText("b");
          }
        };
        SWTExtensions.newPushButton(it, _function_2);
      }
    };
    Shell _newShell = SWTExtensions.newShell(_function);
    SWTExtensions.openAndRunLoop(_newShell);
  }
}
