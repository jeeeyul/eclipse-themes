package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.SharedImages;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public abstract class ChromePage {
	private Image pageImage;
	private String title;
	private ChromeThemePrefererncePage parentPage;
	private CTabFolder tabFolder;

	public ChromePage(String title, String imageURI) {
		super();
		this.title = title;
		this.pageImage = SharedImages.getImage(imageURI);
	}

	public abstract void create(Composite control);

	public void dispose() {

	}

	@SuppressWarnings("unchecked")
	public <T extends ChromePage> T getCompanionPage(Class<T> type) {
		for (ChromePage each : getParentPage().getPages()) {
			if (each.getClass() == type) {
				return (T) each;
			}
		}
		return null;
	}

	public Image getPageImage() {
		return pageImage;
	}

	public ChromeThemePrefererncePage getParentPage() {
		return parentPage;
	}

	protected CTabFolder getTabFolder() {
		return tabFolder;
	}

	public String getTitle() {
		return title;
	}

	public abstract void load(IPreferenceStore preferenceStore);

	public abstract void save(IPreferenceStore preferenceStore);

	public void setPageImage(Image image) {
		this.pageImage = image;
	}

	public void setParentPage(ChromeThemePrefererncePage parentPage) {
		this.parentPage = parentPage;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract void setToDefault(IPreferenceStore preferenceStore);
}
