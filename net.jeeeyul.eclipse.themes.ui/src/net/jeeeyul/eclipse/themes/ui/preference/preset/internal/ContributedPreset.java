package net.jeeeyul.eclipse.themes.ui.preference.preset.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;
import net.jeeeyul.eclipse.themes.ui.shared.PresetIconGenerator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

@SuppressWarnings("javadoc")
public class ContributedPreset implements IJTPreset {
	public static final String EXTENSION_POINT = "net.jeeeyul.eclipse.themes.preset";
	public static final String ELEMENT_PRESET = "preset";
	public static final String ATTR_ID = "id";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_EPF = "epf";
	public static final String ATTR_ICON = "icon";
	public static final String ATTR_CATEGORY = "category";

	private IConfigurationElement element;
	private Properties properties;
	private ImageDescriptor descriptor;
	private String categoryId;

	public ContributedPreset(IConfigurationElement element) {
		this.element = element;
	}

	public ImageDescriptor getImageDescriptor() {
		if (descriptor == null) {
			String iconAttr = element.getAttribute(ATTR_ICON);
			if (iconAttr != null && !iconAttr.isEmpty()) {
				String contributor = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(contributor);
				descriptor = ImageDescriptor.createFromURL(bundle.getResource(iconAttr));
			} else {
				descriptor = new PresetIconGenerator().generatedDescriptor(this);
			}
		}
		return descriptor;

	}

	@Override
	public Properties getProperties() {
		if (properties == null) {
			loadProperties();
		}
		return properties;
	}

	public String getCategoryID() {
		if (categoryId == null) {
			categoryId = element.getAttribute(ATTR_CATEGORY);
			if (categoryId == null) {
				categoryId = IJTPresetCategory.DEFAULT_CATEGORY_ID;
			}
		}
		return categoryId;
	}

	protected void loadProperties() {
		properties = new Properties();
		try {
			String path = element.getAttribute(ATTR_EPF);
			String contributor = element.getContributor().getName();
			Bundle bundle = Platform.getBundle(contributor);
			URL resource = bundle.getResource(path);
			properties.load(resource.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return element.getAttribute(ATTR_NAME);
	}

	@Override
	public String getId() {
		return element.getAttribute(ATTR_ID);
	}

}
