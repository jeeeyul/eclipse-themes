package net.jeeeyul.eclipse.themes.css.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.e4.ui.css.swt.CSSSWTConstants;
import org.eclipse.swt.widgets.Widget;

/**
 * It allows adding class or removing class easily.
 * 
 * @author Jeeeyul Lee
 * @since 2.0.0
 */
@SuppressWarnings("restriction")
public class CSSClasses {

	private List<String> classes = new ArrayList<String>();

	/**
	 * Creates a {@link CSSClasses} with existing class names expression.
	 * 
	 * @param classNames
	 *            empty space separated class names.
	 */
	public CSSClasses(String classNames) {
		if (classNames != null && !classNames.trim().isEmpty())
			for (String name : classNames.split(" "))
				classes.add(name);
	}

	/**
	 * Adds class names.
	 * 
	 * @param classNames
	 *            class names to add.
	 */
	public void add(String... classNames) {
		if (classNames == null) {
			throw new IllegalArgumentException();
		}

		for (String each : classNames) {
			String trimmed = each.trim();
			if (!classes.contains(trimmed)) {
				classes.add(trimmed);
			}
		}
	}

	/**
	 * Test whether it contains given class name.
	 * 
	 * @param className
	 *            class name to test.
	 * @return <code>true</code> if this contains given class name.
	 * 
	 */
	public boolean contains(String className) {
		if (className == null) {
			throw new IllegalArgumentException();
		}
		return classes.contains(className.trim());
	}

	/**
	 * Removes given class names from {@link CSSClasses}.
	 * 
	 * @param classNames
	 *            class names to remove.
	 */
	public void remove(String... classNames) {
		if (classNames == null) {
			throw new IllegalArgumentException();
		}
		for (String each : classNames) {
			String trimmed = each.trim();
			classes.remove(trimmed);
		}
	}

	/**
	 * Retrieves {@link CSSClasses} for given {@link Widget}. After modifying
	 * returned {@link CSSClasses}, call
	 * {@link #setStyleClasses(Widget, CSSClasses)} to apply.
	 * 
	 * @param w
	 *            widget to get {@link CSSClasses}
	 * @return {@link CSSClasses} that represents css classes for give widget.
	 */
	public static CSSClasses getStyleClasses(Widget w) {
		String literal = (String) w.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY);
		return new CSSClasses(literal);
	}

	/**
	 * Sets css classes in {@link CSSClasses} to {@link Widget}.
	 * 
	 * @param w
	 *            Widget to set css classes.
	 * @param newStyleClasses
	 *            {@link CSSClasses} to set.
	 */
	public static void setStyleClasses(Widget w, CSSClasses newStyleClasses) {
		w.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY, newStyleClasses.toString());
	}

	public String toString() {
		if (classes.isEmpty())
			return "";
		Iterator<String> iter = classes.iterator();
		StringBuilder sb = new StringBuilder(iter.next());
		while (iter.hasNext())
			sb.append(" ").append(iter.next());
		return sb.toString();
	}
}