package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public interface JTPConstants {
	public static final String CATEGORY_SEPARATOR = "__";

	public static interface PartStack {
		public static final String PREFIX = "PART_STACK";

		@TypeHint(int.class)
		public static final String SWT_TAB_HEIGHT = PREFIX + CATEGORY_SEPARATOR + "SWT_TAB_HEIGHT";
		
		@TypeHint(HSB.class)
		public static final String COLOR = PREFIX + CATEGORY_SEPARATOR + "COLOR";
		
		@TypeHint(Gradient.class)
		public static final String SWT_UNSELECTED_TABS_COLOR = PREFIX + CATEGORY_SEPARATOR + "SWT_UNSELECTED_TABS_COLOR";
		
		@TypeHint(HSB.class)
		public static final String SWT_SELECTED_TABS_BACKGROUND = PREFIX + CATEGORY_SEPARATOR + "SWT_SELECTED_TABS_BACKGROUND";

		@TypeHint(Gradient.class)
		public static final String JTAB_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_BORDER_COLOR";

		@TypeHint(Gradient.class)
		public static final String JTAB_SELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_SELECTED_BORDER_COLOR";

		@TypeHint(Gradient.class)
		public static final String JTAB_UNSELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_UNSELECTED_BORDER_COLOR";

		@TypeHint(int.class)
		public static final String JTAB_BORDER_RADIUS = PREFIX + CATEGORY_SEPARATOR + "JTAB_BORDER_RADIUS";

		@TypeHint(int.class)
		public static final String JTAB_SPACING = PREFIX + CATEGORY_SEPARATOR + "JTAB_SPACING";

		@TypeHint(HSB.class)
		public static final String JTAB_CLOSE_BUTTON_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_CLOSE_BUTTON_COLOR";

		@TypeHint(HSB.class)
		public static final String JTAB_CLOSE_BUTTON_HOT_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_CLOSE_BUTTON_HOT_COLOR";

		@TypeHint(HSB.class)
		public static final String JTAB_CLOSE_BUTTON_ACTIVE_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_CLOSE_BUTTON_ACTIVE_COLOR";

		@TypeHint(int.class)
		public static final String JTAB_CLOSE_BUTTON_LINE_WIDTH = PREFIX + CATEGORY_SEPARATOR + "JTAB_CLOSE_BUTTON_LINE_WIDTH";

		@TypeHint(Gradient.class)
		public static final String JTAB_UNSELECTED_TABS_BACKGROUND = PREFIX + CATEGORY_SEPARATOR + "JTAB_UNSELECTED_TABS_BACKGROUND";

		@TypeHint(Gradient.class)
		public static final String JTAB_HOVER_TABS_BACKGROUND = PREFIX + CATEGORY_SEPARATOR + "JTAB_HOVER_TABS_BACKGROUND";

		@TypeHint(Gradient.class)
		public static final String JTAB_HOVER_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_HOVER_BORDER_COLOR";

		@TypeHint(HSB.class)
		public static final String JTAB_HOVER_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_HOVER_COLOR";

		@TypeHint(Rectangle.class)
		public static final String JTAB_MARGIN = PREFIX + CATEGORY_SEPARATOR + "JTAB_MARGIN";

		@TypeHint(HSB.class)
		public static final String JTAB_PADDING = PREFIX + CATEGORY_SEPARATOR + "JTAB_PADDING";

		@TypeHint(Rectangle.class)
		public static final String JTAB_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_SHADOW_COLOR";

		@TypeHint(int.class)
		public static final String JTAB_SHADOW_RADIUS = PREFIX + CATEGORY_SEPARATOR + "JTAB_SHADOW_RADIUS";

		@TypeHint(Point.class)
		public static final String JTAB_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "JTAB_SHADOW_POSITION";

		@TypeHint(HSB.class)
		public static final String JTAB_SELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_SELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(HSB.class)
		public static final String JTAB_UNSELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_UNSELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(HSB.class)
		public static final String JTAB_HOVER_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "JTAB_HOVER_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String JTAB_SELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "JTAB_SELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Point.class)
		public static final String JTAB_UNSELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "JTAB_UNSELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Point.class)
		public static final String JTAB_HOVER_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "JTAB_HOVER_TEXT_SHADOW_POSITION";

		@TypeHint(Rectangle.class)
		public static final String JTAB_ITEM_PADDING = PREFIX + CATEGORY_SEPARATOR + "JTAB_ITEM_PADDING";

		@TypeHint(int.class)
		public static final String JTAB_ITEM_HORIZONTAL_SPACING = PREFIX + CATEGORY_SEPARATOR + "JTAB_ITEM_HORIZONTAL_SPACING";
	}

}
