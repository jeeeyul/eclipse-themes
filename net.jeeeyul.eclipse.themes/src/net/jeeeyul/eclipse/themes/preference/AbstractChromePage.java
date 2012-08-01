package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.SharedImages;

import org.eclipse.swt.graphics.Image;

public abstract class AbstractChromePage implements IChromePage {
	private Image image;
	private String title;

	public AbstractChromePage(String title, String imageURI) {
		super();
		this.title = title;
		this.image = SharedImages.getImage(imageURI);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
