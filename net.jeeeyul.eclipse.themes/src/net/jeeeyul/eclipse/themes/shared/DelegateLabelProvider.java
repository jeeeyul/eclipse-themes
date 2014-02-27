package net.jeeeyul.eclipse.themes.shared;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

public final class DelegateLabelProvider<T> extends LabelProvider {
	private Function1<T, String> textGetter;
	private Function1<T, Image> imageGetter;

	@SuppressWarnings("unchecked")
	@Override
	public Image getImage(Object element) {
		if (imageGetter != null) {
			return imageGetter.apply((T) element);
		} else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getText(Object element) {
		if (textGetter != null) {
			return textGetter.apply((T) element);
		} else {
			return null;
		}
	}

	public void setImageGetter(Function1<T, Image> imageGetter) {
		this.imageGetter = imageGetter;
	}

	public void setTextGetter(Function1<T, String> textGetter) {
		this.textGetter = textGetter;
	}

}
