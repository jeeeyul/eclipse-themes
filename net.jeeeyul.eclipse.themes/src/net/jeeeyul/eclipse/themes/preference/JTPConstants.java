package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.Point;

public class JTPConstants {
	public static class ActivePartStack {
		public static final String PREFIX = "ACTIVE_PART_STACK";

		@TypeHint(HSB.class)
		public static final String BACKGROUND_COLOR = PREFIX + CATEGORY_SEPARATOR + "BACKGROUND_COLOR";

		@TypeHint(Gradient.class)
		public static final String BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "BORDER_COLOR";

		@TypeHint(Boolean.class)
		public static final String BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String SELECTED_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String SELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_BORDER_COLOR";

		@TypeHint(Boolean.class)
		public static final String SELECTED_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "SELECTED_BORDER_SHOW";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String SELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_BORDER_COLOR";

		@TypeHint(Boolean.class)
		public static final String UNSELECTED_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_FILL_COLOR";

		@TypeHint(Boolean.class)
		public static final String UNSELECTED_FILL = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_FILL";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String UNSELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String HOVER_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_BORDER_COLOR";

		@TypeHint(Boolean.class)
		public static final String HOVER_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "HOVER_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String HOVER_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_FILL_COLOR";

		@TypeHint(Boolean.class)
		public static final String HOVER_FILL = PREFIX + CATEGORY_SEPARATOR + "HOVER_FILL";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String HOVER_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_SHADOW_POSITION";
	}

	public static class Layout{
		public static final String PREFIX = "LAYOUT";
		
		@TypeHint(int.class)
		public static final String BORDER_RADIUS = PREFIX + CATEGORY_SEPARATOR + "BORDER_RADIUS";

		@TypeHint(int.class)
		public static final String CONTENT_PADDING = PREFIX + CATEGORY_SEPARATOR + "CONTENT_PADDING";
		
		@TypeHint(int.class)
		public static final String TAB_HEIGHT = PREFIX + CATEGORY_SEPARATOR + "TAB_HEIGHT";
		
		@TypeHint(int.class)
		public static final String TAB_ITEM_SPACING = PREFIX + CATEGORY_SEPARATOR + "TAB_ITEM_SPACING";
		
		@TypeHint(int.class)
		public static final String TAB_ITEM_PADDING = PREFIX + CATEGORY_SEPARATOR + "TAB_ITEM_PADDING";
		
		@TypeHint(int.class)
		public static final String TAB_SPACING = PREFIX + CATEGORY_SEPARATOR + "TAB_SPACING";
		
		@TypeHint(Boolean.class)
		public static final String SHOW_SHADOW = PREFIX + CATEGORY_SEPARATOR + "SHOW_SHADOW";
		
		@TypeHint(HSB.class)
		public static final String SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "SHADOW_COLOR";
	}
	
	public static final String CATEGORY_SEPARATOR = "__";

}
