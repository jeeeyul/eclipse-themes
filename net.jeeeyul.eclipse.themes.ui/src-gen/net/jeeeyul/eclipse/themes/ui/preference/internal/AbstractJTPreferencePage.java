package net.jeeeyul.eclipse.themes.ui.preference.internal;

import net.jeeeyul.eclipse.themes.rendering.JTabSettings;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.PreperencePageHelper;
import net.jeeeyul.swtend.SWTExtensions;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Abstract Preference Page for Theme plugin
 * 
 * @since 2.0
 */
@SuppressWarnings("all")
public abstract class AbstractJTPreferencePage {
  /**
   * name of page
   */
  @Accessors
  private String name;
  
  /**
   * image of page
   */
  @Accessors
  private Image image;
  
  public AbstractJTPreferencePage() {
  }
  
  public AbstractJTPreferencePage(final String name) {
    this.name = name;
  }
  
  public void init(@Extension final PreperencePageHelper helper) {
  }
  
  public AbstractJTPreferencePage[] getChildren() {
    return new AbstractJTPreferencePage[] {};
  }
  
  public abstract Control createContents(final Composite parent, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper);
  
  public abstract void updatePreview(final CTabFolder folder, final JTabSettings renderSettings, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper);
  
  public abstract void load(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper);
  
  public abstract void save(final JThemePreferenceStore store, @Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper);
  
  public abstract void dispose(@Extension final SWTExtensions swtExtensions, @Extension final PreperencePageHelper helper);
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  public void setName(final String name) {
    this.name = name;
  }
  
  @Pure
  public Image getImage() {
    return this.image;
  }
  
  public void setImage(final Image image) {
    this.image = image;
  }
}
