package net.jeeeyul.eclipse.themes.rendering;

import org.eclipse.swt.custom.CTabItem;

public class KTabRendererHelper {
	static interface hack {
		static interface CTabItem {
			static final HackedField<CTabItem, Boolean> showing = new HackedField<CTabItem, Boolean>(CTabItem.class, "showing");
		}
	}

	public boolean isShowing(CTabItem me) {
		return hack.CTabItem.showing.get(me);
	}
}
