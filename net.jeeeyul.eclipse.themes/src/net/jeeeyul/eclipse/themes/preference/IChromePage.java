package net.jeeeyul.eclipse.themes.preference;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public interface IChromePage {
	public void create(Composite parent);

	public Image getImage();

	public String getTitle();

	public void load(IPreferenceStore store);

	public void save(IPreferenceStore store);
	
	public void setToDefault(IPreferenceStore store);
}
