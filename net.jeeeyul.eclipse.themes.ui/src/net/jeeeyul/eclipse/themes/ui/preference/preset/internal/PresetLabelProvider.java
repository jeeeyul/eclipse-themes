package net.jeeeyul.eclipse.themes.ui.preference.preset.internal;

import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.swtend.sam.Function1;
import net.jeeeyul.swtend.ui.ResourceRegistry;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Shared Label Provider for {@link IJTPreset}
 * 
 * @author Jeeeyul
 */
public class PresetLabelProvider extends LabelProvider {
	ResourceRegistry<IJTPreset, Image> imageRegistry;

	/**
	 * 
	 */
	public PresetLabelProvider() {
		imageRegistry = new ResourceRegistry<IJTPreset, Image>(new Function1<IJTPreset, Image>() {
			@Override
			public Image apply(IJTPreset t) {
				return t.getImageDescriptor().createImage();
			}
		});
	}

	@Override
	public Image getImage(Object element) {
		IJTPreset preset = (IJTPreset) element;
		return imageRegistry.get(preset);
	}

	@Override
	public String getText(Object element) {
		IJTPreset preset = (IJTPreset) element;
		return preset.getName();
	}

	@Override
	public void dispose() {
		imageRegistry.dispose();
		super.dispose();
	}
}
