package net.jeeeyul.eclipse.themes.preference.preset;

import java.util.Properties;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @since 2.1
 * 
 * @author Jeeeyul
 */
public interface IJTPreset {
	public Properties getProperties();

	public String getName();

	public String getId();
	
	public ImageDescriptor getImageDescriptor();
}
