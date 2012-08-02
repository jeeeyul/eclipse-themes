package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.SharedImages;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractChromePage implements IChromePage {
	private Image image;
	private String title;
	private CTabFolder tabFolder;

	public AbstractChromePage(String title, String imageURI) {
		super();
		this.title = title;
		this.image = SharedImages.getImage(imageURI);
	}

	public Image getImage() {
		return image;
	}

	protected CTabFolder getTabFolder() {
		return tabFolder;
	}

	public String getTitle() {
		return title;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public void dispose() {
		
	}
}
