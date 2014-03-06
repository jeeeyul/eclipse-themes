package net.jeeeyul.eclipse.themes.preference.preset;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ContributedPreset implements IJTPreset {
	public static final String EXTENSION_POINT = "net.jeeeyul.eclipse.themes.preset";
	public static final String ELEMENT_PRESET = "preset";
	public static final String ATTR_ID = "id";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_EPF = "epf";

	private IConfigurationElement element;
	private Properties properties;

	public ContributedPreset(IConfigurationElement element) {
		this.element = element;
	}

	@Override
	public Properties getProperties() {
		if (properties == null) {
			loadProperties();
		}
		return properties;
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
