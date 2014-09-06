package net.jeeeyul.eclipse.themes.css.internal.dynamicresource;

import java.util.HashMap;
import java.util.Map;

/**
 * URI parser for {@link JTDynamicResourceLocator}.
 * 
 * @author Jeeeyul Lee
 */
public class JTResourceURI {
	private static final String PREFIX = "jeeeyul://";

	private String command;
	private Map<String, String> parameters;

	/**
	 * Creates a {@link JTResourceURI}
	 */
	public JTResourceURI() {
		parameters = new HashMap<String, String>();
	}

	/**
	 * Creates a {@link JTResourceURI} with plain string uri.
	 * 
	 * @param uri
	 *            uri.
	 */
	public JTResourceURI(String uri) {
		this();

		String substring = uri.substring(PREFIX.length());
		String[] split = substring.split("\\?");

		setCommand(split[0].trim());
		if (split[1] != null) {
			String[] pairs = split[1].split("&");
			for (String each : pairs) {
				String[] keyValue = each.split("=");
				addParameter(keyValue[0].trim(), keyValue[1].trim());
			}
		}
	}

	/**
	 * Adds a parameter
	 * 
	 * @param name
	 *            parameter name.
	 * @param value
	 *            parameter value.
	 */
	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}

	/**
	 * Gets value for parameter
	 * 
	 * @param name
	 *            parameter name.
	 * @return value for given parameter name. What if there is no parameter it
	 *         will return <code>null</code>.
	 */
	public String getParameterValue(String name) {
		return getParameterValue(name, null);
	}

	/**
	 * Gets value for parameter
	 * 
	 * @param name
	 *            parameter name.
	 * @param fallbackValue
	 *            fallback value for non-existing parameter.
	 * @return value for given parameter name. What if there is no parameter, it
	 *         will return given fallback value.
	 */
	public String getParameterValue(String name, String fallbackValue) {
		String result = parameters.get(name);
		return result != null ? result : fallbackValue;
	}

	/**
	 * Gets command from uri. URI is composed with like:
	 * "jeeeyul://[command]?[parameters]"
	 * 
	 * @return command of uri
	 * @see #setCommand(String)
	 * @see #addParameter(String, String)
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Sets command to uri
	 * 
	 * @param command
	 * 
	 *            command to set.
	 * @see #getCommand()
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
