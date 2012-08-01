package net.jeeeyul.eclipse.themes.preference.internal;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class FontNameProvider implements IStructuredContentProvider {
	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		ArrayList<String> result = new ArrayList<String>();
		FontData[] fontList = Display.getDefault().getFontList(null, true);
		for (FontData each : fontList) {
			String name = each.getName();
			if (name.startsWith("@") || result.contains(name)) {
				continue;
			}
			result.add(name);
		}
		Collections.sort(result);
		return result.toArray();
	}

}
