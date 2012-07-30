package net.jeeeyul.eclipse.themes.decorator;

import java.util.HashSet;
import java.util.Set;

import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.UpdateCTabFolderClassesJob;
import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ChromeTabRendering extends HackedTabRendering {
	private ChromeThemeConfig preference = ChromeThemeConfig.getInstance();
	private CTabFolder tabFolder;
	private static Set<ChromeTabRendering> INSTANCES = new HashSet<ChromeTabRendering>();
	private UpdateCTabFolderClassesJob updateTags;

	public static Set<ChromeTabRendering> getInstances() {
		return INSTANCES;
	}

	public ChromeTabRendering(CTabFolder tabFolder) {
		super(tabFolder);
		this.tabFolder = tabFolder;
		updateTags = new UpdateCTabFolderClassesJob(tabFolder);

		INSTANCES.add(this);

		tabFolder.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				INSTANCES.remove(this);
			}
		});

		/*
		 * 10: Window - New Window does not consider Styling
		 * https://github.com/jeeeyul/eclipse-themes/issues/issue/10
		 */
		applyChromeThemePreference();
	}

	@Override
	protected void draw(int part, int state, Rectangle bounds, GC gc) {
		CSSClasses tags = CSSClasses.getStyleClasses(tabFolder);

		boolean hasEmptyClass = tags.contains("empty");
		boolean haveToSetEmpty = tabFolder.getItemCount() == 0;

		if (hasEmptyClass != haveToSetEmpty) {
			updateTags.schedule();
		}

		if (part == PART_BODY && !tags.contains("chrome-tabfolder-preview")) {
			/*
			 * 7: Editor area - Minimize / maximize look brocken
			 * https://github.com/jeeeyul/eclipse-themes/issues/issue/7
			 * 
			 * Calculated tab height of empty tab seens to cause this problems.
			 */
			if (tabFolder.getItemCount() == 0) {
				tabFolder.setTabHeight(23);
			} else if (tags.contains("active")) {
				tabFolder.setTabHeight(-1);
			} else {
				preference.getInactiveDecorator().apply(tabFolder);
				tabFolder.setTabHeight(-1);
			}
		}

		super.draw(part, state, bounds, gc);
	}

	@Override
	protected void dispose() {
		super.dispose();
	}

	public void applyChromeThemePreference() {
		if (tabFolder.isDisposed()) {
			return;
		}

		invalidateTabItems();
	}

	private void invalidateTabItems() {
		int tabHeight = tabFolder.getTabHeight();
		tabFolder.setTabHeight(1);
		tabFolder.setTabHeight(tabHeight);
	}

}
