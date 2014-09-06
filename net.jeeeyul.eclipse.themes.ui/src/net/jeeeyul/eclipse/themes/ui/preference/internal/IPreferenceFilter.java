package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.lang.reflect.Field;

import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.annotations.PresetCategory;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

/**
 * Filter that accepts key files in {@link JTPConstants}.
 * 
 * @see JTPUtil#listPreferenceKeys(IPreferenceFilter)
 * 
 * @author Jeeeyul
 */
public interface IPreferenceFilter {
	/**
	 * A filter that matches only categories for theme preset.
	 */
	public static final IPreferenceFilter FILTER_PRESET = new IPreferenceFilter() {
		@Override
		public boolean acceptCategory(Class<?> category) {
			return category.getAnnotation(PresetCategory.class) != null;
		}

		@Override
		public boolean acceptKey(Field field) {
			return true;
		}

		@Override
		public IPreferenceFilter chain(IPreferenceFilter other) {
			return new CompoundPreferenceFilter(this, other);
		}
	};

	/**
	 * A filter that matches every category and field.
	 */
	public static final IPreferenceFilter FILTER_ALL = new IPreferenceFilter() {
		@Override
		public boolean acceptCategory(Class<?> category) {
			return true;
		}

		@Override
		public boolean acceptKey(Field field) {
			return true;
		}

		@Override
		public IPreferenceFilter chain(IPreferenceFilter other) {
			return new CompoundPreferenceFilter(this, other);
		}
	};

	/**
	 * 
	 */
	public static final IPreferenceFilter GRADIENT_TYPE_FILTER = new TypeFilter(Gradient.class);

	/**
	 * 
	 */
	public static final IPreferenceFilter HSB_TYPE_FILTER = new TypeFilter(HSB.class);
	
	/**
	 * @param category
	 *            category interface.
	 * @return <code>true</code> if accept category interface.
	 */
	public boolean acceptCategory(Class<?> category);

	/**
	 * 
	 * @param field
	 *            filed that contains key to test.
	 * @return <code>true</code> if accept given field.
	 */
	public boolean acceptKey(Field field);

	/**
	 * @param other
	 * @return chained {@link IPreferenceFilter}
	 */
	public IPreferenceFilter chain(IPreferenceFilter other);
}
