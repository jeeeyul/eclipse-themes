package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.preference.annotations.Ignore;
import net.jeeeyul.eclipse.themes.preference.annotations.PresetCategory;
import net.jeeeyul.eclipse.themes.preference.annotations.TypeHint;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * provides keys for {@link IPreferenceStore} that are used in Jeeeyul's eclipse
 * themes. {@link Ignore} annotations means it is not a key. {@link TypeHint}
 * provides types of each key.
 * 
 * @author Jeeeyul
 * @since 2.1
 */
@SuppressWarnings("javadoc")
public interface JTPConstants {
	/**
	 * Used by {@link PartStackPage}. Keys in this interfaces are not used by
	 * theme plugin, actual keys are contained by {@link ActivePartStack} and
	 * {@link InactivePartStack}.
	 * 
	 * @see JThemePreferenceStore
	 * @author Jeeeyul
	 * @since 2.0.0
	 */
	@Ignore
	public static interface PartStack {
		@TypeHint(Gradient.class)
		public static final String HEADER_BACKGROUND_COLOR = "HEADER_BACKGROUND_COLOR";

		@TypeHint(HSB.class)
		public static final String BODY_BACKGROUND_COLOR = "BODY_BACKGROUND_COLOR";

		@TypeHint(Gradient.class)
		public static final String BORDER_COLOR = "BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String BORDER_SHOW = "BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String SELECTED_FILL_COLOR = "SELECTED_FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String SELECTED_BORDER_COLOR = "SELECTED_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String SELECTED_BORDER_SHOW = "SELECTED_BORDER_SHOW";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_COLOR = "SELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_SHADOW_COLOR = "SELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String SELECTED_TEXT_SHADOW_POSITION = "SELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_BORDER_COLOR = "UNSELECTED_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String UNSELECTED_BORDER_SHOW = "UNSELECTED_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_FILL_COLOR = "UNSELECTED_FILL_COLOR";

		@TypeHint(boolean.class)
		public static final String UNSELECTED_FILL = "UNSELECTED_FILL";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_COLOR = "UNSELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_SHADOW_COLOR = "UNSELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String UNSELECTED_TEXT_SHADOW_POSITION = "UNSELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String HOVER_BORDER_COLOR = "HOVER_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String HOVER_BORDER_SHOW = "HOVER_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String HOVER_FILL_COLOR = "HOVER_FILL_COLOR";

		@TypeHint(boolean.class)
		public static final String HOVER_FILL = "HOVER_FILL";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_COLOR = "HOVER_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_SHADOW_COLOR = "HOVER_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String HOVER_TEXT_SHADOW_POSITION = "HOVER_TEXT_SHADOW_POSITION";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_COLOR = "CLOSE_BUTTON_COLOR";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_HOVER_COLOR = "CLOSE_BUTTON_HOVER_COLOR";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_ACTIVE_COLOR = "CLOSE_BUTTON_ACTIVE_COLOR";

		@TypeHint(int.class)
		public static final String CLOSE_BUTTON_LINE_WIDTH = "CLOSE_BUTTON_LINE_WIDTH";

		@TypeHint(HSB.class)
		public static final String CHEVRON_COLOR = "CHEVRON_COLOR";
	}

	@PresetCategory
	public static interface ActivePartStack {
		@Ignore
		public static final String PREFIX = "ACTIVE_PART_STACK";

		@TypeHint(Gradient.class)
		public static final String HEADER_BACKGROUND_COLOR = PREFIX + CATEGORY_SEPARATOR + "HEADER_BACKGROUND_COLOR";

		@TypeHint(HSB.class)
		public static final String BODY_BACKGROUND_COLOR = PREFIX + CATEGORY_SEPARATOR + "BODY_BACKGROUND_COLOR";

		@TypeHint(Gradient.class)
		public static final String BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String SELECTED_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String SELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String SELECTED_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "SELECTED_BORDER_SHOW";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String SELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String UNSELECTED_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_FILL_COLOR";

		@TypeHint(boolean.class)
		public static final String UNSELECTED_FILL = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_FILL";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String UNSELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String HOVER_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String HOVER_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "HOVER_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String HOVER_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_FILL_COLOR";

		@TypeHint(boolean.class)
		public static final String HOVER_FILL = PREFIX + CATEGORY_SEPARATOR + "HOVER_FILL";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String HOVER_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_SHADOW_POSITION";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_COLOR = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_COLOR";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_HOVER_COLOR = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_HOVER_COLOR";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_ACTIVE_COLOR = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_ACTIVE_COLOR";

		@TypeHint(int.class)
		public static final String CLOSE_BUTTON_LINE_WIDTH = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_LINE_WIDTH";

		@TypeHint(HSB.class)
		public static final String CHEVRON_COLOR = PREFIX + CATEGORY_SEPARATOR + "CHEVRON_COLOR";
	}

	@PresetCategory
	public static interface InactivePartStack {
		@Ignore
		public static final String PREFIX = "INACTIVE_PART_STACK";

		@TypeHint(Gradient.class)
		public static final String HEADER_BACKGROUND_COLOR = PREFIX + CATEGORY_SEPARATOR + "HEADER_BACKGROUND_COLOR";

		@TypeHint(HSB.class)
		public static final String BODY_BACKGROUND_COLOR = PREFIX + CATEGORY_SEPARATOR + "BODY_BACKGROUND_COLOR";

		@TypeHint(Gradient.class)
		public static final String BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String SELECTED_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String SELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String SELECTED_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "SELECTED_BORDER_SHOW";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String SELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String SELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "SELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String UNSELECTED_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String UNSELECTED_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_FILL_COLOR";

		@TypeHint(boolean.class)
		public static final String UNSELECTED_FILL = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_FILL";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String UNSELECTED_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String UNSELECTED_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "UNSELECTED_TEXT_SHADOW_POSITION";

		@TypeHint(Gradient.class)
		public static final String HOVER_BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String HOVER_BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "HOVER_BORDER_SHOW";

		@TypeHint(Gradient.class)
		public static final String HOVER_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_FILL_COLOR";

		@TypeHint(boolean.class)
		public static final String HOVER_FILL = PREFIX + CATEGORY_SEPARATOR + "HOVER_FILL";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_COLOR";

		@TypeHint(HSB.class)
		public static final String HOVER_TEXT_SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_SHADOW_COLOR";

		@TypeHint(Point.class)
		public static final String HOVER_TEXT_SHADOW_POSITION = PREFIX + CATEGORY_SEPARATOR + "HOVER_TEXT_SHADOW_POSITION";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_COLOR = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_COLOR";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_HOVER_COLOR = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_HOVER_COLOR";

		@TypeHint(HSB.class)
		public static final String CLOSE_BUTTON_ACTIVE_COLOR = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_ACTIVE_COLOR";

		@TypeHint(int.class)
		public static final String CLOSE_BUTTON_LINE_WIDTH = PREFIX + CATEGORY_SEPARATOR + "CLOSE_BUTTON_LINE_WIDTH";

		@TypeHint(HSB.class)
		public static final String CHEVRON_COLOR = PREFIX + CATEGORY_SEPARATOR + "CHEVRON_COLOR";
	}

	@PresetCategory
	public static interface Layout {
		@Ignore
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

		@TypeHint(boolean.class)
		public static final String SHOW_SHADOW = PREFIX + CATEGORY_SEPARATOR + "SHOW_SHADOW";

		@TypeHint(HSB.class)
		public static final String SHADOW_COLOR = PREFIX + CATEGORY_SEPARATOR + "SHADOW_COLOR";
	}

	@PresetCategory
	public static interface Window {
		@Ignore
		public static final String PREFIX = "WINDOW";

		@TypeHint(Gradient.class)
		public static final String TOOLBAR_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "TOOLBAR_FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String STATUS_BAR_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "STATUS_BAR_FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String PERSPECTIVE_SWITCHER_FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "PERSPECTIVE_SWITCHER_FILL_COLOR";

		@TypeHint(HSB.class)
		public static final String PERSPECTIVE_SWITCHER_KEY_LINE_COLOR = PREFIX + CATEGORY_SEPARATOR + "PERSPECTIVE_SWITCHER_KEY_LINE_COLOR";

		@TypeHint(HSB.class)
		public static final String BACKGROUND_COLOR = PREFIX + CATEGORY_SEPARATOR + "BACKGROUND_COLOR";

		@TypeHint(int.class)
		public static final String SASH_WIDTH = PREFIX + CATEGORY_SEPARATOR + "SASH_WIDTH";

		@TypeHint(Rectangle.class)
		public static final String MARGINS = PREFIX + CATEGORY_SEPARATOR + "MARGINS";
	}

	@PresetCategory
	public static interface EmptyPartStack {
		@Ignore
		public static final String PREFIX = "EMPTY_PART_STACK";

		@TypeHint(Gradient.class)
		public static final String FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "BORDER_SHOW";
	}

	@PresetCategory
	public static interface EditorsPartStack {
		@Ignore
		public static final String PREFIX = "EDITORS_PART_STACK";

		@TypeHint(Gradient.class)
		public static final String FILL_COLOR = PREFIX + CATEGORY_SEPARATOR + "FILL_COLOR";

		@TypeHint(Gradient.class)
		public static final String BORDER_COLOR = PREFIX + CATEGORY_SEPARATOR + "BORDER_COLOR";

		@TypeHint(boolean.class)
		public static final String BORDER_SHOW = PREFIX + CATEGORY_SEPARATOR + "BORDER_SHOW";
	}

	@PresetCategory
	public static interface Others {
		@Ignore
		public static final String PREFIX = "OTHERS";

		@TypeHint(String.class)
		public static final String USER_CSS = PREFIX + CATEGORY_SEPARATOR + "USER_CSS";
	}

	@PresetCategory
	public static interface TextEditor {
		@Ignore
		public static final String PREFIX = "TEXT_EDITOR";

		@TypeHint(int.class)
		public static final String UNDER_LINE_STYLE = PREFIX + CATEGORY_SEPARATOR + "UNDER_LINE_STYLE";

		@TypeHint(HSB.class)
		public static final String UNDER_LINE_COLOR = PREFIX + CATEGORY_SEPARATOR + "UNDER_LINE_COLOR";

		@TypeHint(HSB.class)
		public static final String RULER_COLOR = PREFIX + CATEGORY_SEPARATOR + "RULER_COLOR";
	}

	public static interface Memento {
		@Ignore
		public static final String PREFIX = "MEMENTO";

		@TypeHint(String.class)
		public static final String LAST_CHOOSED_PRESET = PREFIX + CATEGORY_SEPARATOR + "LAST_CHOOSED_PRESET";
	}

	public static final String CATEGORY_SEPARATOR = "__";

}
