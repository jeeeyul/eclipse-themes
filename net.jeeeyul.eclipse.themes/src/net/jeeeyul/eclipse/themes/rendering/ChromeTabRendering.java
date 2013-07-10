package net.jeeeyul.eclipse.themes.rendering;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.UpdateCTabFolderClassesJob;
import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig;
import net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ChromeTabRendering extends HackedCTabRendering {
	private IChromeThemeConfig config = ChromeThemeConfig.getInstance();

	private CTabFolder tabFolder;
	private int lastKnownTabHeight = -1;

	private static Set<ChromeTabRendering> INSTANCES = new HashSet<ChromeTabRendering>();

	public static Set<ChromeTabRendering> getInstances() {
		return INSTANCES;
	}

	private UpdateCTabFolderClassesJob updateTags;

	private boolean showShineyShadow;

	@Inject
	public ChromeTabRendering(CTabFolder tabFolder) {
		super(tabFolder);
		this.tabFolder = tabFolder;
		updateTags = new UpdateCTabFolderClassesJob(tabFolder);

		tabFolder.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateEmptyClassIfNeeded();
			}
		});

		tabFolder.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				INSTANCES.remove(this);
			}
		});

		INSTANCES.add(this);
	}

	public void applyChromeThemePreference() {
	}

	@Override
	protected void dispose() {
		super.dispose();
	}

	@Override
	protected void draw(int part, int state, Rectangle bounds, GC gc) {
		if (parent.isDisposed() || gc.isDisposed()) {
			return;
		}

		updateEmptyClassIfNeeded();

		if (part == PART_BODY && !isPreviewingTab()) {
			/*
			 * 7: Editor area - Minimize / maximize look brocken
			 * https://github.com/jeeeyul/eclipse-themes/issues/issue/7
			 * 
			 * Calculated tab height of empty tab seens to cause this problems.
			 */
			if (tabFolder.getItemCount() == 0) {
				if (lastKnownTabHeight < 0) {
					lastKnownTabHeight = tabFolder.getFont().getFontData()[0].getHeight() + 19;
				}
				tabFolder.setTabHeight(lastKnownTabHeight);
			} else {
				tabFolder.setTabHeight(-1);
				lastKnownTabHeight = tabFolder.getTabHeight();
			}
		}

		super.draw(part, state, bounds, gc);
	}

	protected IChromeThemeConfig getPreference() {
		return config;
	}

	private boolean isPreviewingTab() {
		CSSClasses tags = CSSClasses.getStyleClasses(tabFolder);
		return tags.contains("chrome-tabfolder-preview");
	}

	public boolean isShowShineyShadow() {
		return showShineyShadow;
	}

	public void setShowShineyShadow(boolean showShineyShadow) {
		this.showShineyShadow = showShineyShadow;
	}

	@Override
	protected boolean showUnselectedTabItemShadow() {
		return showShineyShadow;
	}

	private void updateEmptyClassIfNeeded() {
		CSSClasses tags = CSSClasses.getStyleClasses(tabFolder);

		boolean haveToSetEmpty = tabFolder.getItemCount() == 0;

		if (haveToSetEmpty && !tags.contains("empty")) {
			updateTags.schedule(10);
		} else if (!haveToSetEmpty && !tags.contains("nonEmpty")) {
			updateTags.schedule(10);
		}
	}

}
