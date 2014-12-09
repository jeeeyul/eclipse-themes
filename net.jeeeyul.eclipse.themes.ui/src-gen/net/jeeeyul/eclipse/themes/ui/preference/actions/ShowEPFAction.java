package net.jeeeyul.eclipse.themes.ui.preference.actions;

import java.util.List;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.actions.AbstractPreferenceAction;
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

/**
 * Displays EPF content that will be generated with currently editing in preference page.
 */
@SuppressWarnings("all")
public class ShowEPFAction extends AbstractPreferenceAction {
  public ShowEPFAction(final JTPreferencePage page) {
    super(page);
    this.setText("Show EPF");
    ImageDescriptor _imageDescriptor = SharedImages.getImageDescriptor(SharedImages.PROPS);
    this.setImageDescriptor(_imageDescriptor);
  }
  
  public void run() {
    @Extension
    final SWTExtensions SWTExtension = SWTExtensions.INSTANCE;
    PreferenceStore _preferenceStore = new PreferenceStore();
    JThemePreferenceStore tempStore = new JThemePreferenceStore(_preferenceStore);
    JTPreferencePage _page = this.getPage();
    _page.saveTo(tempStore);
    final List<String> presetKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET);
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final String key : presetKeys) {
        _builder.append(key, "");
        _builder.append("=");
        String _string = tempStore.getString(key);
        String _saveConvert = JTPUtil.saveConvert(_string, false, true);
        _builder.append(_saveConvert, "");
        _builder.newLineIfNotEmpty();
      }
    }
    final String epf = _builder.toString();
    JTPreferencePage _page_1 = this.getPage();
    Shell _shell = _page_1.getShell();
    int _or = SWTExtension.operator_or(SWT.APPLICATION_MODAL, SWT.SHELL_TRIM);
    final Procedure1<Shell> _function = new Procedure1<Shell>() {
      public void apply(final Shell it) {
        it.setText("EPF");
        Image _image = SharedImages.getImage(SharedImages.JTHEME);
        it.setImage(_image);
        Color _COLOR_WHITE = SWTExtension.COLOR_WHITE();
        it.setBackground(_COLOR_WHITE);
        final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
          public void apply(final GridLayout it) {
            it.marginWidth = 5;
            it.marginHeight = 5;
          }
        };
        GridLayout _newGridLayout = SWTExtension.newGridLayout(_function);
        it.setLayout(_newGridLayout);
        int _or = SWTExtension.operator_or(SWT.READ_ONLY, SWT.MULTI);
        int _or_1 = SWTExtension.operator_or(_or, SWT.H_SCROLL);
        int _or_2 = SWTExtension.operator_or(_or_1, SWT.V_SCROLL);
        StyledText _styledText = new StyledText(it, _or_2);
        final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<StyledText> _function_1 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<StyledText>() {
          public void apply(final StyledText it) {
            it.setText(epf);
            Color _COLOR_WHITE = SWTExtension.COLOR_WHITE();
            it.setBackground(_COLOR_WHITE);
            Color _COLOR_BLACK = SWTExtension.COLOR_BLACK();
            it.setForeground(_COLOR_BLACK);
            final Procedure1<GridData> _function = new Procedure1<GridData>() {
              public void apply(final GridData it) {
                it.widthHint = 640;
                it.heightHint = 480;
              }
            };
            GridData _FILL_BOTH = SWTExtension.FILL_BOTH(_function);
            it.setLayoutData(_FILL_BOTH);
          }
        };
        ObjectExtensions.<StyledText>operator_doubleArrow(_styledText, _function_1);
        it.pack();
      }
    };
    Shell shell = SWTExtension.newShell(_shell, _or, _function);
    Rectangle shellBounds = shell.getBounds();
    JTPreferencePage _page_2 = this.getPage();
    Shell _shell_1 = _page_2.getShell();
    Monitor _monitor = _shell_1.getMonitor();
    Rectangle _bounds = _monitor.getBounds();
    Point _center = SWTExtension.getCenter(_bounds);
    Rectangle _relocateCenterWith = SWTExtension.relocateCenterWith(shellBounds, _center);
    shell.setBounds(_relocateCenterWith);
    shell.open();
  }
}
