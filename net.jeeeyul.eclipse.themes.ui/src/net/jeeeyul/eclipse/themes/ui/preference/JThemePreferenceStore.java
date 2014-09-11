package net.jeeeyul.eclipse.themes.ui.preference;

import java.io.IOException;

import net.jeeeyul.eclipse.themes.ui.internal.SerializeUtil;
import net.jeeeyul.swtend.sam.Function1;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Enhanced {@link IPreferenceStore}. It supports {@link HSB}, {@link Gradient},
 * {@link Rectangle}, {@link Point} as value.
 * 
 * When it has a {@link #context}, it will be used as prefix for key.
 * 
 * @author Jeeeyul
 * @since 2.1
 */
public class JThemePreferenceStore implements IPreferenceStore, IPersistentPreferenceStore {
	private IPersistentPreferenceStore originalStore;
	private SerializeUtil serializeUtil = new SerializeUtil();
	private String context = null;

	private Function1<String, String> customKeyResolver;

	JThemePreferenceStore() {
	}

	/**
	 * create a {@link JThemePreferenceStore} with original
	 * {@link IPersistentPreferenceStore}.
	 * 
	 * @param originalStore
	 *            store to be wrapped.
	 */
	public JThemePreferenceStore(IPersistentPreferenceStore originalStore) {
		super();
		this.originalStore = originalStore;
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		originalStore.addPropertyChangeListener(listener);
	}

	public boolean contains(String name) {
		return originalStore.contains(resolveKey(name));
	}

	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
		originalStore.firePropertyChangeEvent(resolveKey(name), oldValue, newValue);
	}

	public boolean getBoolean(String name) {
		return originalStore.getBoolean(resolveKey(name));
	}

	/**
	 * Gets context.
	 * 
	 * @return context, can be <code>null</code>.
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Gets copy of current {@link JThemePreferenceStore} with specific child
	 * context.
	 * 
	 * @param contenxt
	 *            context to append.
	 * @return copy of {@link JThemePreferenceStore}.
	 */
	public JThemePreferenceStore getCopyWithContext(String contenxt) {
		JThemePreferenceStore result = new JThemePreferenceStore(originalStore);

		if (this.context == null) {
			result.setContext(contenxt);
		} else {
			result.setContext(this.context + JTPConstants.CATEGORY_SEPARATOR + contenxt);
		}
		return result;
	}

	/**
	 * Gets custom key resolver.
	 * 
	 * @return custom key resolver.
	 * @see #resolveKey(String)
	 */
	public Function1<String, String> getCustomKeyResolver() {
		return customKeyResolver;
	}

	public boolean getDefaultBoolean(String name) {
		return originalStore.getDefaultBoolean(resolveKey(name));
	}

	public double getDefaultDouble(String name) {
		return originalStore.getDefaultDouble(resolveKey(name));
	}

	public float getDefaultFloat(String name) {
		return originalStore.getDefaultFloat(resolveKey(name));
	}

	/**
	 * Gets preference value as {@link Gradient}.
	 * 
	 * @param name
	 *            key name.
	 * @return A {@link Gradient} value for given key name.
	 */
	public Gradient getDefaultGradient(String name) {
		String exp = originalStore.getDefaultString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return new Gradient(HSB.WHITE, HSB.WHITE);
		}
		return serializeUtil.deserializeGradient(exp);
	}

	/**
	 * Returns the default value for the {@link HSB}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the default value of the named preference
	 */
	public HSB getDefaultHSB(String name) {
		String exp = originalStore.getDefaultString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return HSB.WHITE;
		}
		return serializeUtil.desrializeHSB(exp);
	}

	public int getDefaultInt(String name) {
		return originalStore.getDefaultInt(resolveKey(name));
	}

	public long getDefaultLong(String name) {
		return originalStore.getDefaultLong(resolveKey(name));
	}

	/**
	 * Returns the default value for the {@link Point}-valued preference with
	 * the given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the default value of the named preference
	 */
	public Point getDefaultPoint(String name) {
		String exp = originalStore.getDefaultString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return new Point(0, 0);
		}
		return serializeUtil.desrializePoint(exp);
	}

	/**
	 * Returns the default value for the {@link Rectangle}-valued preference
	 * with the given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the default value of the named preference
	 */
	public Rectangle getDefaultRectangle(String name) {
		String exp = originalStore.getDefaultString(resolveKey(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeRectangle(exp);
	}

	public String getDefaultString(String name) {
		return originalStore.getDefaultString(resolveKey(name));
	}

	public double getDouble(String name) {
		return originalStore.getDouble(resolveKey(name));
	}

	public float getFloat(String name) {
		return originalStore.getFloat(resolveKey(name));
	}

	/**
	 * Returns the value for the {@link Gradient}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the value of the named preference
	 */
	public Gradient getGradient(String name) {
		String exp = originalStore.getString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return new Gradient(HSB.WHITE, HSB.WHITE);
		}
		return serializeUtil.deserializeGradient(exp);
	}

	/**
	 * Returns the value for the {@link HSB}-valued preference with the given
	 * name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the value of the named preference
	 */
	public HSB getHSB(String name) {
		String exp = originalStore.getString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return HSB.RED;
		}
		return serializeUtil.desrializeHSB(exp);
	}

	public int getInt(String name) {
		return originalStore.getInt(resolveKey(name));
	}

	public long getLong(String name) {
		return originalStore.getLong(resolveKey(name));
	}

	/**
	 * 
	 * @return original preference store that is wrapped by
	 *         {@link JThemePreferenceStore}.
	 */
	public IPersistentPreferenceStore getOriginalStore() {
		return originalStore;
	}

	/**
	 * Returns the value for the {@link Point}-valued preference with the given
	 * name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the value of the named preference
	 */
	public Point getPoint(String name) {
		String exp = originalStore.getString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return new Point(0, 0);
		}
		return serializeUtil.desrializePoint(exp);
	}

	/**
	 * Returns the value for the {@link Rectangle}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the value of the named preference
	 */
	public Rectangle getRectangle(String name) {
		String exp = originalStore.getString(resolveKey(name));
		if (exp == null || exp.isEmpty()) {
			return new Rectangle(0, 0, 0, 0);
		}
		return serializeUtil.desrializeRectangle(exp);
	}

	public String getString(String name) {
		return originalStore.getString(resolveKey(name));
	}

	public boolean isDefault(String name) {
		return originalStore.isDefault(resolveKey(name));
	}

	public boolean needsSaving() {
		return originalStore.needsSaving();
	}

	public void putValue(String name, String value) {
		originalStore.putValue(resolveKey(name), value);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		originalStore.removePropertyChangeListener(listener);
	}

	/**
	 * Resolves actual key for given key name. What if there is custom resolver
	 * was set through {@link #setCustomKeyResolver(Function1)} It will be used
	 * to resolve key. What if there is no custom resolver, then it will add
	 * {@link #context} as prefix.
	 * 
	 * @param name
	 *            original key.
	 * @return resolved key.
	 */
	private String resolveKey(String name) {
		if (customKeyResolver != null) {
			return customKeyResolver.apply(name);
		}

		if (this.context != null) {
			return context + JTPConstants.CATEGORY_SEPARATOR + name;
		} else {
			return name;
		}
	}

	public void save() {
		try {
			originalStore.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sets a context. context will be used as prefix for each key accesing.
	 * 
	 * @param context
	 *            context to set.
	 * @since 2.0.0
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Sets custom key resolover.
	 * 
	 * @param customKeyResolver
	 * @see #resolveKey(String)
	 */
	public void setCustomKeyResolver(Function1<String, String> customKeyResolver) {
		this.customKeyResolver = customKeyResolver;
	}

	public void setDefault(String name, boolean value) {
		originalStore.setDefault(resolveKey(name), value);
	}

	public void setDefault(String name, double value) {
		originalStore.setDefault(resolveKey(name), value);
	}

	public void setDefault(String name, float value) {
		originalStore.setDefault(resolveKey(name), value);
	}

	/**
	 * Sets the default value for the {@link Gradient}-valued preference with
	 * the given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param gradient
	 *            the new default value for the preference
	 */
	public void setDefault(String name, Gradient gradient) {
		originalStore.setDefault(resolveKey(name), serializeUtil.serialize(gradient));
	}

	/**
	 * Sets the default value for the {@link HSB}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param defaultValue
	 *            the new default value for the preference
	 */
	public void setDefault(String name, HSB defaultValue) {
		originalStore.setDefault(resolveKey(name), serializeUtil.serialize(defaultValue));
	}

	public void setDefault(String name, int value) {
		originalStore.setDefault(resolveKey(name), value);
	}

	public void setDefault(String name, long value) {
		originalStore.setDefault(resolveKey(name), value);
	}

	/**
	 * Sets the default value for the {@link Rectangle}-valued preference with
	 * the given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param rect
	 *            the new default value for the preference
	 */
	public void setDefault(String name, Rectangle rect) {
		originalStore.setDefault(resolveKey(name), serializeUtil.serialize(rect));
	}

	public void setDefault(String name, String defaultObject) {
		originalStore.setDefault(resolveKey(name), defaultObject);
	}

	/**
	 * Sets the default value for the {@link Point}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param point
	 *            the new default value for the preference
	 */
	public void setDefaultValue(String name, Point point) {
		originalStore.setDefault(resolveKey(name), serializeUtil.serialize(point));
	}

	/**
	 * Sets original store to wrap.
	 * 
	 * @param originalStore
	 *            store to wrap.
	 */
	public void setOriginalStore(PreferenceStore originalStore) {
		this.originalStore = originalStore;
	}

	public void setToDefault(String name) {
		originalStore.setToDefault(resolveKey(name));
	}

	public void setValue(String name, boolean value) {
		originalStore.setValue(resolveKey(name), value);
	}

	public void setValue(String name, double value) {
		originalStore.setValue(resolveKey(name), value);
	}

	public void setValue(String name, float value) {
		originalStore.setValue(resolveKey(name), value);
	}

	/**
	 * Sets the value for the {@link Gradient}-valued preference with the given
	 * name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param gradient
	 *            the new default value for the preference
	 */
	public void setValue(String name, Gradient gradient) {
		originalStore.setValue(resolveKey(name), serializeUtil.serialize(gradient));
	}

	/**
	 * Sets the default value for the {@link HSB}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param value
	 *            the new default value for the preference
	 */
	public void setValue(String name, HSB value) {
		originalStore.setValue(resolveKey(name), serializeUtil.serialize(value));
	}

	public void setValue(String name, int value) {
		originalStore.setValue(resolveKey(name), value);
	}

	public void setValue(String name, long value) {
		originalStore.setValue(resolveKey(name), value);
	}

	/**
	 * Sets the default value for the {@link Point}-valued preference with the
	 * given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param point
	 *            the new default value for the preference
	 */
	public void setValue(String name, Point point) {
		originalStore.setValue(resolveKey(name), serializeUtil.serialize(point));
	}

	/**
	 * Sets the default value for the {@link Rectangle}-valued preference with
	 * the given name.
	 *
	 * @param name
	 *            the name of the preference
	 * @param rect
	 *            the new default value for the preference
	 */
	public void setValue(String name, Rectangle rect) {
		originalStore.setValue(resolveKey(name), serializeUtil.serialize(rect));
	}

	public void setValue(String name, String value) {
		originalStore.setValue(resolveKey(name), value);
	}

	/**
	 * Runs work with extended context.
	 * 
	 * @param context
	 *            context to extend.
	 * @param work
	 *            The job to run with extended cotnext.
	 */
	public void withContext(String context, Procedure1<JThemePreferenceStore> work) {
		String oldContext = getContext();
		setContext(context);
		work.apply(this);
		setContext(oldContext);
	}
}
