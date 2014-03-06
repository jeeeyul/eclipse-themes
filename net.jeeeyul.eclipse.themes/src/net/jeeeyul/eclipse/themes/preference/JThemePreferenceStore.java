package net.jeeeyul.eclipse.themes.preference;

import java.io.IOException;

import net.jeeeyul.eclipse.themes.internal.SerializeUtil;
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
 * @since 2.0.0
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
		return originalStore.contains(resolveName(name));
	}

	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
		originalStore.firePropertyChangeEvent(resolveName(name), oldValue, newValue);
	}

	public boolean getBoolean(String name) {
		return originalStore.getBoolean(resolveName(name));
	}

	public String getContext() {
		return context;
	}

	public JThemePreferenceStore getCopyWithContext(String contenxt) {
		JThemePreferenceStore result = new JThemePreferenceStore(originalStore);

		if (this.context == null) {
			result.setContext(contenxt);
		} else {
			result.setContext(this.context + JTPConstants.CATEGORY_SEPARATOR + contenxt);
		}
		return result;
	}

	public Function1<String, String> getCustomKeyResolver() {
		return customKeyResolver;
	}

	public boolean getDefaultBoolean(String name) {
		return originalStore.getDefaultBoolean(resolveName(name));
	}

	public double getDefaultDouble(String name) {
		return originalStore.getDefaultDouble(resolveName(name));
	}

	public float getDefaultFloat(String name) {
		return originalStore.getDefaultFloat(resolveName(name));
	}

	public Gradient getDefaultGradient(String name) {
		String exp = originalStore.getDefaultString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.deserializeGradient(exp);
	}

	public HSB getDefaultHSB(String name) {
		String exp = originalStore.getDefaultString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeHSB(exp);
	}

	public int getDefaultInt(String name) {
		return originalStore.getDefaultInt(resolveName(name));
	}

	public long getDefaultLong(String name) {
		return originalStore.getDefaultLong(resolveName(name));
	}

	public Point getDefaultPoint(String name) {
		String exp = originalStore.getDefaultString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializePoint(exp);
	}

	public Rectangle getDefaultRectangle(String name) {
		String exp = originalStore.getDefaultString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeRectangle(exp);
	}

	public String getDefaultString(String name) {
		return originalStore.getDefaultString(resolveName(name));
	}

	public double getDouble(String name) {
		return originalStore.getDouble(resolveName(name));
	}

	public float getFloat(String name) {
		return originalStore.getFloat(resolveName(name));
	}

	public Gradient getGradient(String name) {
		String exp = originalStore.getString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.deserializeGradient(exp);
	}

	public HSB getHSB(String name) {
		String exp = originalStore.getString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeHSB(exp);
	}

	public int getInt(String name) {
		return originalStore.getInt(resolveName(name));
	}

	public long getLong(String name) {
		return originalStore.getLong(resolveName(name));
	}

	public IPersistentPreferenceStore getOriginalStore() {
		return originalStore;
	}

	public Point getPoint(String name) {
		String exp = originalStore.getString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializePoint(exp);
	}

	public Rectangle getRectangle(String name) {
		String exp = originalStore.getString(resolveName(name));
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeRectangle(exp);
	}

	public String getString(String name) {
		return originalStore.getString(resolveName(name));
	}

	public boolean isDefault(String name) {
		return originalStore.isDefault(resolveName(name));
	}

	public boolean needsSaving() {
		return originalStore.needsSaving();
	}

	public void putValue(String name, String value) {
		originalStore.putValue(resolveName(name), value);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		originalStore.removePropertyChangeListener(listener);
	}

	private String resolveName(String name) {
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

	public void setCustomKeyResolver(Function1<String, String> customKeyResolver) {
		this.customKeyResolver = customKeyResolver;
	}

	public void setDefault(String name, boolean value) {
		originalStore.setDefault(resolveName(name), value);
	}

	public void setDefault(String name, double value) {
		originalStore.setDefault(resolveName(name), value);
	}

	public void setDefault(String name, float value) {
		originalStore.setDefault(resolveName(name), value);
	}

	public void setDefault(String name, Gradient gradient) {
		originalStore.setDefault(resolveName(name), serializeUtil.serialize(gradient));
	}

	public void setDefault(String name, HSB defaultValue) {
		originalStore.setDefault(resolveName(name), serializeUtil.serialize(defaultValue));
	}

	public void setDefault(String name, int value) {
		originalStore.setDefault(resolveName(name), value);
	}

	public void setDefault(String name, long value) {
		originalStore.setDefault(resolveName(name), value);
	}

	public void setDefault(String name, Rectangle rect) {
		originalStore.setDefault(resolveName(name), serializeUtil.serialize(rect));
	}

	public void setDefault(String name, String defaultObject) {
		originalStore.setDefault(resolveName(name), defaultObject);
	}

	public void setDefaultValue(String name, Point point) {
		originalStore.setDefault(resolveName(name), serializeUtil.serialize(point));
	}

	public void setOriginalStore(PreferenceStore originalStore) {
		this.originalStore = originalStore;
	}

	public void setToDefault(String name) {
		originalStore.setToDefault(resolveName(name));
	}

	public void setValue(String name, boolean value) {
		originalStore.setValue(resolveName(name), value);
	}

	public void setValue(String name, double value) {
		originalStore.setValue(resolveName(name), value);
	}

	public void setValue(String name, float value) {
		originalStore.setValue(resolveName(name), value);
	}

	public void setValue(String name, Gradient gradient) {
		originalStore.setValue(resolveName(name), serializeUtil.serialize(gradient));
	}

	public void setValue(String name, HSB value) {
		originalStore.setValue(resolveName(name), serializeUtil.serialize(value));
	}

	public void setValue(String name, int value) {
		originalStore.setValue(resolveName(name), value);
	}

	public void setValue(String name, long value) {
		originalStore.setValue(resolveName(name), value);
	}

	public void setValue(String name, Point point) {
		originalStore.setValue(resolveName(name), serializeUtil.serialize(point));
	}

	public void setValue(String name, Rectangle rect) {
		originalStore.setValue(resolveName(name), serializeUtil.serialize(rect));
	}

	public void setValue(String name, String value) {
		originalStore.setValue(resolveName(name), value);
	}

	public void withContext(String context, Procedure1<JThemePreferenceStore> work) {
		String oldContext = getContext();
		setContext(context);
		work.apply(this);
		setContext(oldContext);
	}
}
