package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.SharedImages;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractChromePage implements IChromePage {
	private Image pageImage;
	private String title;
	private CTabFolder tabFolder;

	public AbstractChromePage(String title, String imageURI) {
		super();
		this.title = title;
		this.pageImage = SharedImages.getImage(imageURI);
	}

	public Image getPageImage() {
		return pageImage;
	}

	protected CTabFolder getTabFolder() {
		return tabFolder;
	}

	public String getTitle() {
		return title;
	}

	public void setPageImage(Image image) {
		this.pageImage = image;
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
