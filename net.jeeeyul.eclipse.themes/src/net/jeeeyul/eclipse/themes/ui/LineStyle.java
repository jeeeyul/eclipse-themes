package net.jeeeyul.eclipse.themes.ui;

import java.util.Arrays;
import java.util.List;

public enum LineStyle {
	NONE("none"), SOLID("solid"), DASH("dash");

	public static LineStyle[] VALUE_ARRAYS = { NONE, SOLID, DASH };
	public static List<LineStyle> VALUES = Arrays.asList(VALUE_ARRAYS);

	public static LineStyle getByLiteral(String literal) {
		for (LineStyle each : VALUE_ARRAYS) {
			if (each.getLiteral().equalsIgnoreCase(literal)) {
				return each;
			}
		}

		return NONE;
	}

	private String literal;

	private LineStyle(String literal) {
		this.literal = literal;
	}

	public String getLiteral() {
		return literal;
	}
}
