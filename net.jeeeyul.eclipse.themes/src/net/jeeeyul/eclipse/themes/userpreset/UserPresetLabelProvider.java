package net.jeeeyul.eclipse.themes.userpreset;

import org.eclipse.jface.viewers.LabelProvider;

public class UserPresetLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof UserPreset) {
			return ((UserPreset) element).getName();
		}
		return super.getText(element);
	}
}
