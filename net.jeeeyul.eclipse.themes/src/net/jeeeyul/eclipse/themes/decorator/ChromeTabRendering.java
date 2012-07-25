package net.jeeeyul.eclipse.themes.decorator;

import java.util.HashSet;
import java.util.Set;

import net.jeeeyul.eclipse.themes.CSSClasses;
import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig;

import org.eclipse.e4.ui.workbench.renderers.swt.CTabRendering;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

@SuppressWarnings("restriction")
public class ChromeTabRendering extends CTabRendering {
	private ChromeThemeConfig preference = ChromeThemeConfig.getInstance();
	private CTabFolder tabFolder;
	private static Set<ChromeTabRendering> INSTANCES = new HashSet<ChromeTabRendering>();

	public static Set<ChromeTabRendering> getInstances() {
		return INSTANCES;
	}

	public ChromeTabRendering(CTabFolder tabFolder) {
		super(tabFolder);
		this.tabFolder = tabFolder;
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

		if (part == PART_BODY && !tags.contains("chrome-tabfolder-preview")) {
			if (tabFolder.getItemCount() == 0) {
				preference.getEmptyDecorator().apply(tabFolder);

				/*
				 * 7: Editor area - Minimize / maximize look brocken
				 * https://github.com/jeeeyul/eclipse-themes/issues/issue/7
				 * 
				 * Calculated tab height of empty tab seens to cause this
				 * problems.
				 */
				tabFolder.setTabHeight(23);
			} else if (tags.contains("active")) {
				preference.getActiveDecorator().apply(tabFolder);
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

		super.setShadowVisible(preference.usePartShadow());
		invalidateTabItems();
	}

	private void invalidateTabItems() {
		int tabHeight = tabFolder.getTabHeight();
		tabFolder.setTabHeight(1);
		tabFolder.setTabHeight(tabHeight);
	}
}
