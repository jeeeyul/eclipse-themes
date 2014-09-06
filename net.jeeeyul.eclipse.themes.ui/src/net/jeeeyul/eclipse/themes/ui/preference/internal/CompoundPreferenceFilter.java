package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Jeeeyul
 */
public class CompoundPreferenceFilter implements IPreferenceFilter {
	ArrayList<IPreferenceFilter> filters;

	@Override
	public boolean acceptCategory(Class<?> category) {
		for (IPreferenceFilter each : filters) {
			if (!each.acceptCategory(category)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	public CompoundPreferenceFilter() {
		filters = new ArrayList<IPreferenceFilter>();
	}

	@SuppressWarnings("javadoc")
	public CompoundPreferenceFilter(List<IPreferenceFilter> filters) {
		super();
		this.filters = new ArrayList<IPreferenceFilter>(filters);
	}

	/**
	 * @param filters
	 */
	public CompoundPreferenceFilter(IPreferenceFilter... filters) {
		super();
		this.filters = new ArrayList<IPreferenceFilter>(Arrays.asList(filters));
	}

	@Override
	public boolean acceptKey(Field field) {
		for (IPreferenceFilter each : filters) {
			if (!each.acceptKey(field)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public IPreferenceFilter chain(IPreferenceFilter other) {
		List<IPreferenceFilter> list = new ArrayList<IPreferenceFilter>(this.filters);
		list.add(other);
		return new CompoundPreferenceFilter(list);
	}

}
