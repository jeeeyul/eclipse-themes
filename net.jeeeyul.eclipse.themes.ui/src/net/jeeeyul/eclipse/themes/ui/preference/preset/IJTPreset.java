package net.jeeeyul.eclipse.themes.ui.preference.preset;

import java.util.Properties;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @since 2.1
 * 
 * @author Jeeeyul
 */
public interface IJTPreset {
	/**
	 * 
	 * @return {@link Properties} that contains settings of this
	 *         {@link IJTPreset}.
	 */
	public Properties getProperties();

	/**
	 * 
	 * @return Human readable name for this preset.
	 */
	public String getName();

	/**
	 * 
	 * @return unique id for this {@link IJTPreset}.
	 */
	public String getId();

	/**
	 * 
	 * @return A preview image descriptor for this {@link IJTPreset}. It's size
	 *         should have to be 16 x 16.
	 */
	public ImageDescriptor getImageDescriptor();
}
