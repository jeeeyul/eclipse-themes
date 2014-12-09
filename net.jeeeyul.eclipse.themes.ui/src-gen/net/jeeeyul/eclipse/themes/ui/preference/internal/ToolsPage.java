package net.jeeeyul.eclipse.themes.ui.preference.internal;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.List;
import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.AbstractJTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PartStackPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PartStacksPage;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.ColorPicker;
import net.jeeeyul.swtend.ui.ColorStop;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ToolsPage extends AbstractJTPreferencePage {
  public ToolsPage() {
    super("Tools");
    Image _image = SharedImages.getImage(SharedImages.CONFIG);
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
            it.setText("Batch Color Tasks");
            GridLayout _newGridLayout = swtExtensions.newGridLayout();
            it.setLayout(_newGridLayout);
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<Button> _function = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Re-write hue globally");
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    ToolsPage.this.rewriteHue(helper);
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            swtExtensions.newPushButton(it, _function);
          }
        };
        swtExtensions.newGroup(it, _function_1);
        final Procedure1<Group> _function_2 = new Procedure1<Group>() {
          public void apply(final Group it) {
            it.setText("Part Stack Batch Task");
            GridLayout _newGridLayout = swtExtensions.newGridLayout();
            it.setLayout(_newGridLayout);
            GridData _FILL_HORIZONTAL = swtExtensions.FILL_HORIZONTAL();
            it.setLayoutData(_FILL_HORIZONTAL);
            final Procedure1<Button> _function = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Copy settings from Active to Inactive");
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    ToolsPage.this.copy(JTPConstants.ActivePartStack.PREFIX, JTPConstants.InactivePartStack.PREFIX, swtExtensions, helper);
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            swtExtensions.newPushButton(it, _function);
            final Procedure1<Button> _function_1 = new Procedure1<Button>() {
              public void apply(final Button it) {
                it.setText("Copy settings from Inactive to Active");
                final Listener _function = new Listener() {
                  public void handleEvent(final Event it) {
                    ToolsPage.this.copy(JTPConstants.InactivePartStack.PREFIX, JTPConstants.ActivePartStack.PREFIX, swtExtensions, helper);
                  }
                };
                swtExtensions.setOnSelection(it, _function);
              }
            };
            swtExtensions.newPushButton(it, _function_1);
          }
        };
        swtExtensions.newGroup(it, _function_2);
      }
    };
    return swtExtensions.newComposite(parent, _function);
  }
  
  private void rewriteHue(@Extension final PreperencePageHelper helper) {
    ColorPicker picker = new ColorPicker(null);
    HSB _hSB = new HSB(255, 0, 0);
    picker.setSelection(_hSB);
    int _open = picker.open();
    boolean _equals = (_open == IDialogConstants.OK_ID);
    if (_equals) {
      JThemePreferenceStore copy = helper.createWorkingCopy();
      IPreferenceFilter _chain = IPreferenceFilter.FILTER_PRESET.chain(IPreferenceFilter.GRADIENT_TYPE_FILTER);
      final List<String> gradientKeys = JTPUtil.listPreferenceKeys(_chain);
      for (final String each : gradientKeys) {
        {
          Gradient grad = copy.getGradient(each);
          for (final ColorStop eachStop : grad) {
            HSB _selection = picker.getSelection();
            eachStop.color.hue = _selection.hue;
          }
          copy.setValue(each, grad);
        }
      }
      IPreferenceFilter _chain_1 = IPreferenceFilter.FILTER_PRESET.chain(IPreferenceFilter.HSB_TYPE_FILTER);
      final List<String> hsbKeys = JTPUtil.listPreferenceKeys(_chain_1);
      for (final String each_1 : hsbKeys) {
        {
          HSB hsb = copy.getHSB(each_1);
          HSB _selection = picker.getSelection();
          hsb.hue = _selection.hue;
          copy.setValue(each_1, hsb);
        }
      }
      helper.loadFromWorkingCopy(copy);
    }
  }
  
  public void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
  
  public void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
  
  public void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
  
  public void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
  }
  
  private void copy(final String from, final String to, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper) {
    AbstractJTPreferencePage[] _allPages = helper.getAllPages();
    Iterable<PartStacksPage> _filter = Iterables.<PartStacksPage>filter(((Iterable<?>)Conversions.doWrapArray(_allPages)), PartStacksPage.class);
    PartStacksPage stacksPage = IterableExtensions.<PartStacksPage>head(_filter);
    AbstractJTPreferencePage[] sibilings = stacksPage.getChildren();
    final AbstractJTPreferencePage[] _converted_sibilings = (AbstractJTPreferencePage[])sibilings;
    Iterable<PartStackPage> _filter_1 = Iterables.<PartStackPage>filter(((Iterable<?>)Conversions.doWrapArray(_converted_sibilings)), PartStackPage.class);
    final Function1<PartStackPage, Boolean> _function = new Function1<PartStackPage, Boolean>() {
      public Boolean apply(final PartStackPage it) {
        String _context = it.getContext();
        return Boolean.valueOf(Objects.equal(_context, from));
      }
    };
    PartStackPage fromPage = IterableExtensions.<PartStackPage>findFirst(_filter_1, _function);
    final AbstractJTPreferencePage[] _converted_sibilings_1 = (AbstractJTPreferencePage[])sibilings;
    Iterable<PartStackPage> _filter_2 = Iterables.<PartStackPage>filter(((Iterable<?>)Conversions.doWrapArray(_converted_sibilings_1)), PartStackPage.class);
    final Function1<PartStackPage, Boolean> _function_1 = new Function1<PartStackPage, Boolean>() {
      public Boolean apply(final PartStackPage it) {
        String _context = it.getContext();
        return Boolean.valueOf(Objects.equal(_context, to));
      }
    };
    PartStackPage toPage = IterableExtensions.<PartStackPage>findFirst(_filter_2, _function_1);
    PreferenceStore _preferenceStore = new PreferenceStore();
    JThemePreferenceStore fakeStore = new JThemePreferenceStore(_preferenceStore);
    final net.jeeeyul.swtend.sam.Function1<String, String> _function_2 = new net.jeeeyul.swtend.sam.Function1<String, String>() {
      public String apply(final String it) {
        return it;
      }
    };
    fakeStore.setCustomKeyResolver(_function_2);
    fromPage.save(fakeStore, swtExtensions, helper);
    toPage.load(fakeStore, swtExtensions, helper);
    helper.requestUpdatePreview();
  }
}
