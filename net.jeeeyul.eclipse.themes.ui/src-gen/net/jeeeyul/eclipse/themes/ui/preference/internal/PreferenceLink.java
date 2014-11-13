package net.jeeeyul.eclipse.themes.ui.preference.internal;

import org.eclipse.xtend.lib.Data;
import org.eclipse.xtend.lib.Property;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;

@Data
@SuppressWarnings("all")
public class PreferenceLink {
  @Property
  private final String __name;
  
  @Property
  private final String __prefId;
  
  public PreferenceLink(final String name, final String prefId) {
    super();
    this.__name = name;
    this.__prefId = prefId;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.__name== null) ? 0 : this.__name.hashCode());
    result = prime * result + ((this.__prefId== null) ? 0 : this.__prefId.hashCode());
    return result;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PreferenceLink other = (PreferenceLink) obj;
    if (this.__name == null) {
      if (other.__name != null)
        return false;
    } else if (!this.__name.equals(other.__name))
      return false;
    if (this.__prefId == null) {
      if (other.__prefId != null)
        return false;
    } else if (!this.__prefId.equals(other.__prefId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    String result = new ToStringHelper().toString(this);
    return result;
  }
  
  @Pure
  public String getName() {
    return this.__name;
  }
  
  @Pure
  public String getPrefId() {
    return this.__prefId;
  }
  
  @Pure
  public String get_name() {
    return this.__name;
  }
  
  @Pure
  public String get_prefId() {
    return this.__prefId;
  }
}
