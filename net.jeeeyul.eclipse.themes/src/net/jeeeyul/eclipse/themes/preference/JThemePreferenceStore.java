package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.internal.SerializeUtil;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class JThemePreferenceStore implements IPreferenceStore {
	private IPreferenceStore originalStore;
	private SerializeUtil serializeUtil = new SerializeUtil();

	public JThemePreferenceStore(IPreferenceStore originalStore) {
		super();
		this.originalStore = originalStore;
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		originalStore.addPropertyChangeListener(listener);
	}

	public boolean contains(String name) {
		return originalStore.contains(name);
	}

	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
		originalStore.firePropertyChangeEvent(name, oldValue, newValue);
	}

	public boolean getBoolean(String name) {
		return originalStore.getBoolean(name);
	}

	public boolean getDefaultBoolean(String name) {
		return originalStore.getDefaultBoolean(name);
	}

	public double getDefaultDouble(String name) {
		return originalStore.getDefaultDouble(name);
	}

	public float getDefaultFloat(String name) {
		return originalStore.getDefaultFloat(name);
	}

	public Gradient getDefaultGradient(String name) {
		String exp = originalStore.getDefaultString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.deserializeGradient(exp);
	}

	public HSB getDefaultHSB(String name) {
		String exp = originalStore.getDefaultString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeHSB(exp);
	}

	public int getDefaultInt(String name) {
		return originalStore.getDefaultInt(name);
	}

	public long getDefaultLong(String name) {
		return originalStore.getDefaultLong(name);
	}

	public Point getDefaultPoint(String name) {
		String exp = originalStore.getDefaultString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializePoint(exp);
	}

	public Rectangle getDefaultRectangle(String name) {
		String exp = originalStore.getDefaultString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeRectangle(exp);
	}

	public String getDefaultString(String name) {
		return originalStore.getDefaultString(name);
	}

	public double getDouble(String name) {
		return originalStore.getDouble(name);
	}

	public float getFloat(String name) {
		return originalStore.getFloat(name);
	}

	public Gradient getGradient(String name) {
		String exp = originalStore.getString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.deserializeGradient(exp);
	}

	public HSB getHSB(String name) {
		String exp = originalStore.getString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeHSB(exp);
	}

	public int getInt(String name) {
		return originalStore.getInt(name);
	}

	public long getLong(String name) {
		return originalStore.getLong(name);
	}

	public Point getPoint(String name) {
		String exp = originalStore.getString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializePoint(exp);
	}

	public Rectangle getRectangle(String name) {
		String exp = originalStore.getString(name);
		if (exp == null) {
			return null;
		}
		return serializeUtil.desrializeRectangle(exp);
	}

	public String getString(String name) {
		return originalStore.getString(name);
	}

	public boolean isDefault(String name) {
		return originalStore.isDefault(name);
	}

	public boolean needsSaving() {
		return originalStore.needsSaving();
	}

	public void putValue(String name, String value) {
		originalStore.putValue(name, value);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		originalStore.removePropertyChangeListener(listener);
	}

	public void setDefault(String name, boolean value) {
		originalStore.setDefault(name, value);
	}

	public void setDefault(String name, double value) {
		originalStore.setDefault(name, value);
	}

	public void setDefault(String name, float value) {
		originalStore.setDefault(name, value);
	}

	public void setDefault(String name, Gradient gradient) {
		originalStore.setDefault(name, serializeUtil.serialize(gradient));
	}

	public void setDefault(String name, HSB defaultValue) {
		originalStore.setDefault(name, serializeUtil.serialize(defaultValue));
	}

	public void setDefault(String name, int value) {
		originalStore.setDefault(name, value);
	}

	public void setDefault(String name, long value) {
		originalStore.setDefault(name, value);
	}

	public void setDefault(String name, Rectangle rect) {
		originalStore.setDefault(name, serializeUtil.serialize(rect));
	}

	public void setDefault(String name, String defaultObject) {
		originalStore.setDefault(name, defaultObject);
	}

	public void setDefaultValue(String name, Point point) {
		originalStore.setDefault(name, serializeUtil.serialize(point));
	}

	public void setToDefault(String name) {
		originalStore.setToDefault(name);
	}

	public void setValue(String name, boolean value) {
		originalStore.setValue(name, value);
	}

	public void setValue(String name, double value) {
		originalStore.setValue(name, value);
	}

	public void setValue(String name, float value) {
		originalStore.setValue(name, value);
	}

	public void setValue(String name, Gradient gradient) {
		originalStore.setValue(name, serializeUtil.serialize(gradient));
	}

	public void setValue(String name, HSB value) {
		originalStore.setValue(name, serializeUtil.serialize(value));
	}

	public void setValue(String name, int value) {
		originalStore.setValue(name, value);
	}

	public void setValue(String name, long value) {
		originalStore.setValue(name, value);
	}

	public void setValue(String name, Point point) {
		originalStore.setValue(name, serializeUtil.serialize(point));
	}

	public void setValue(String name, Rectangle rect) {
		originalStore.setValue(name, serializeUtil.serialize(rect));
	}

	public void setValue(String name, String value) {
		originalStore.setValue(name, value);
	}
}
