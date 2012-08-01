package net.jeeeyul.eclipse.themes.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jeeeyul 2012. 4. 16. ¿ÀÈÄ 3:22:31
 * @since M1.10
 */
public class OrderedMap extends HashMap<String, String> {
	private static final long serialVersionUID = -6093087470256600968L;
	private ArrayList<String> keyOrder = new ArrayList<String>();

	@Override
	public String put(String key, String value) {
		if (!this.keyOrder.contains(key) && value != null) {
			this.keyOrder.add(key);
		}
		return super.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	@Override
	public String remove(Object key) {
		this.keyOrder.remove(key);
		return super.remove(key);
	}

	public List<String> getKeyList() {
		return this.keyOrder;
	}
}
