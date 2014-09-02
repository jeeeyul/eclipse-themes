package net.jeeeyul.eclipse.themes.rendering;

/**
 * Vertical Alignment Enum
 * 
 * @author Jeeeyul
 */
public enum VerticalAlignment {
	/**
	 * Align to Middle
	 */
	MIDDLE("Middle", "middle", 0),

	/**
	 * Align to Text Baseline
	 */
	BASE_LINE("Baseline", "baseline", 1);

	private final String name;
	private final int value;
	private final String cssValue;

	private VerticalAlignment(String name, String cssValue, int value) {
		this.name = name;
		this.cssValue = cssValue;
		this.value = value;
	}

	/**
	 * 
	 * @return Human readable name, label.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return Serialize value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 
	 * @return CSS Notation
	 */
	public String getCSSValue() {
		return cssValue;
	}

	private static VerticalAlignment[] VALUES = new VerticalAlignment[] { MIDDLE, BASE_LINE };

	/**
	 * 
	 * @param value
	 * @return {@link VerticalAlignment} that matches given value.
	 */
	public static VerticalAlignment fromValue(int value) {
		for (VerticalAlignment each : VALUES) {
			if (each.getValue() == value) {
				return each;
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 
	 * @param css
	 * @return {@link VerticalAlignment} that matches given css value.
	 */
	public static VerticalAlignment fromCSSValue(String css) {
		for (VerticalAlignment each : VALUES) {
			if (each.getCSSValue().equalsIgnoreCase(css)) {
				return each;
			}
		}
		throw new IllegalArgumentException();
	}

}
