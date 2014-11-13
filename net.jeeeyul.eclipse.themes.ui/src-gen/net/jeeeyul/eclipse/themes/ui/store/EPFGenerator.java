package net.jeeeyul.eclipse.themes.ui.store;

import com.google.common.base.Objects;
import java.util.List;
import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class EPFGenerator {
  public String generate() {
    JThemesCore _default = JThemesCore.getDefault();
    final JThemePreferenceStore store = _default.getPreferenceStore();
    List<String> _listPreferenceKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET);
    final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
      public Boolean apply(final String it) {
        return Boolean.valueOf((!Objects.equal(it, JTPConstants.Others.USER_CSS)));
      }
    };
    final Iterable<String> keys = IterableExtensions.<String>filter(_listPreferenceKeys, _function);
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final String each : keys) {
        {
          boolean _equals = Objects.equal(each, JTPConstants.Layout.TAB_HEIGHT);
          if (_equals) {
            _builder.append(each, "");
            _builder.append("=16");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append(each, "");
            _builder.append("=");
            String _string = store.getString(each);
            String _saveConvert = JTPUtil.saveConvert(_string, false, true);
            _builder.append(_saveConvert, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder.toString();
  }
}
