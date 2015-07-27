package net.jeeeyul.eclipse.themes.ui.hotswap

import net.jeeeyul.eclipse.themes.JTRuntime
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment
import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Display
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Generates CSS content with {@link JThemePreferenceStore} as input.
 * @see #generate()
 * 
 * @since 2.1
 * @author Jeeeyul Lee
 */
class CustomThemeGenerator {
	@Accessors JThemePreferenceStore store

	extension ENVHelper = ENVHelper.INSTANCE

	/**
	 * Creates {@link CustomThemeGenerator} with input.
	 * @param store An input to generate css.
	 */
	new(JThemePreferenceStore store) {
		if(store == null) {
			throw new IllegalArgumentException
		}
		this.store = store
	}

	/**
	 * Generate CSS content.
	 * 
	 * @return generated CSS content.
	 */
	def String generate() '''
		«header» 
		
		«body»
	'''

	def private String header() '''
		.jeeeyul-custom-theme{
			/* This class must appear first. */
		}
	'''

	def private String body() {
		try {
			return doGenerateBody()
		} catch(Exception e) {
			'''
				/*
				«e.class.name»: «e.message»
					«FOR each : e.stackTrace»
						at «each.className».«each.methodName» («each.fileName» : «each.lineNumber»)
					«ENDFOR»
				*/
			'''
		}
	}

	def private String doGenerateBody() '''
		«comment("GTK3")»
		GtkToolbar#swt-toolbar-flat {
			padding: 0px;
		}
		
		
		
		«comment("Window")»
		Shell.MTrimmedWindow {
			margin-top: «windowMargins.y»px;
			margin-right: «windowMargins.width»px;
			margin-bottom: «windowMargins.height»px;
			margin-left: «windowMargins.x»px;
			«IF isLinux»
				background-color: 
					«store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»
					«store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»
					100%;
			«ELSE»
				background-color: «store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»;
			«ENDIF»
		}
		
		.MPartSashContainer {
			jsash-width : «partSpacing»px;
			background-color: «store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»;
		}
		
		
		
		«comment("Main Toolbar")»
		#org-eclipse-ui-main-toolbar {
			background-color:«store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR).toSWTCSSString»;
		}
		
		#org-eclipse-ui-main-toolbar > .Draggable {
			handle-image:
				url(jeeeyul://drag-handle?height=«toolbarHeight»&background-color=«store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR).middlePointColor.toHTMLCode»&embossed=false);
		}
		
		#org-eclipse-ui-main-toolbar > .TrimStack {
			frame-image: none;
			handle-image:
				url(jeeeyul://drag-handle?height=«toolbarHeight»&background-color=«store.getGradient(JTPConstants.Window.TOOLBAR_FILL_COLOR).middlePointColor.toHTMLCode»&embossed=false);
		}
		
		#org-eclipse-ui-main-toolbar #PerspectiveSwitcher {
			background-color: «store.getGradient(JTPConstants.Window.PERSPECTIVE_SWITCHER_FILL_COLOR).toSWTCSSString»;
			handle-image: none;
			eclipse-perspective-keyline-color: «store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_KEY_LINE_COLOR).toHTMLCode»;
		}
		
		#org-eclipse-ui-main-toolbar #PerspectiveSwitcher ToolBar {
			jtool-item-color : «store.getHSB(JTPConstants.Window.PERSPECTIVE_SWITCHER_TEXT_COLOR).toHTMLCode»;
		}
		
		
		
		«comment("Status Bar")»
		#org-eclipse-ui-trim-status{
			background-color:«store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR).toSWTCSSString»;
		}
		
		#org-eclipse-ui-trim-status,
		#org-eclipse-ui-trim-status *
		{
			color : «store.getHSB(JTPConstants.Window.STATUS_BAR_TEXT_COLOR).toHTMLCode»;
		}
		
		#org-eclipse-ui-trim-status ToolBar{
			jtool-item-color : «store.getHSB(JTPConstants.Window.STATUS_BAR_TEXT_COLOR).toHTMLCode»;
		}
		
		#org-eclipse-ui-trim-status .Draggable {
			handle-image:
				url(jeeeyul://drag-handle?height=«toolbarHeight»&background-color=«store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR).middlePointColor.toHTMLCode»&embossed=false);
		}
		
		#org-eclipse-ui-trim-status .TrimStack {
			handle-image:
				url(jeeeyul://drag-handle?height=«toolbarHeight»&background-color=«store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR).middlePointColor.toHTMLCode»&embossed=false);
			frame-image: url(jeeeyul://frame?background-color=«store.getGradient(JTPConstants.Window.STATUS_BAR_FILL_COLOR).middlePointColor.toHTMLCode»);
			frame-cuts: 4px 2px 5px 16px;
		}
		
		
		
		«comment("Trim Stack")»
		Shell.MTrimmedWindow > .MTrimBar > .TrimStack {
			frame-image: url(jeeeyul://frame?background-color=«store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»);
			frame-cuts: 4px 2px 5px 16px;
			handle-image:
				url(jeeeyul://drag-handle?height=«toolbarHeight»&background-color=«store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»&embossed=false);
		}
		
		Shell.MTrimmedWindow > .MTrimBar > .MToolBar.Draggable {
			handle-image:
				url(jeeeyul://drag-handle?height=«toolbarHeight»&background-color=«store.getHSB(JTPConstants.Window.BACKGROUND_COLOR).toHTMLCode»&embossed=false);
		}
		
		
		
		«comment("Inactive Part Stack")»
		.MPartStack {
			swt-tab-renderer: url('«tabRendererClass»');
			swt-mru-visible: «store.getBoolean(JTPConstants.Others.MRU_VISIBLE)»;
			
			/* layout */
			swt-tab-height: «store.getInt(JTPConstants.Layout.TAB_HEIGHT)»px;
			jtab-border-radius: «store.getInt(JTPConstants.Layout.BORDER_RADIUS)»px;
			jtab-spacing: «store.getInt(JTPConstants.Layout.TAB_SPACING)»px;
			jtab-item-padding: 0px «store.getInt(JTPConstants.Layout.TAB_ITEM_PADDING)»px;
			jtab-item-horizontal-spacing: «store.getInt(JTPConstants.Layout.TAB_ITEM_SPACING)»px;
			jtab-padding : «store.getInt(JTPConstants.Layout.CONTENT_PADDING)»px;
			jtab-truncate-tab-items : «store.getBoolean(JTPConstants.Layout.TRUNCATE_TAB_ITEMS)»;
			jtab-use-ellipses : «store.getBoolean(JTPConstants.Layout.USE_ELLIPSES)»;
			jtab-minimum-characters : «store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS)»;
			jtab-close-button-alignment : «VerticalAlignment.fromValue(store.getInt(JTPConstants.Layout.CLOSE_BUTTON_VERTICAL_ALIGNMENT)).CSSValue»;
			
			«IF store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)»
				jtab-margin : 0px 4px 4px 1px; /* top right bottom left */
				jtab-shadow-color: «store.getHSB(JTPConstants.Layout.SHADOW_COLOR).toHTMLCode»;
				jtab-shadow-position: 1px 1px;
				jtab-shadow-radius: 3px;
			«ELSE»
				jtab-margin : 0px 1px 1px 0px;
				jtab-shadow-color: none;
				jtab-shadow-position: 0px 0px;
				jtab-shadow-radius: 0px;
			«ENDIF»
			
			/* tab background */
			jtab-header-background : «store.getGradient(JTPConstants.InactivePartStack.HEADER_BACKGROUND_COLOR).toSWTCSSString»;
			«IF store.getBoolean(JTPConstants.InactivePartStack.BORDER_SHOW)»
				jtab-border-color : «store.getGradient(JTPConstants.InactivePartStack.BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-border-color : none;
			«ENDIF»
			background : «store.getHSB(JTPConstants.InactivePartStack.BODY_BACKGROUND_COLOR).toHTMLCode»;
			
			/* selected tabs */
			jtab-selected-tab-background: «store.getGradient(JTPConstants.InactivePartStack.SELECTED_FILL_COLOR).toSWTCSSString»;
			«IF store.getBoolean(JTPConstants.InactivePartStack.SELECTED_BORDER_SHOW)»
				jtab-selected-border-color: «store.getGradient(JTPConstants.InactivePartStack.SELECTED_BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-selected-border-color: none;
			«ENDIF»
			«IF !store.getPoint(JTPConstants.InactivePartStack.SELECTED_TEXT_SHADOW_POSITION).empty»
				jtab-selected-text-shadow-position: «store.getPoint(JTPConstants.InactivePartStack.SELECTED_TEXT_SHADOW_POSITION).toCSS»;
				jtab-selected-text-shadow-color: «store.getHSB(JTPConstants.InactivePartStack.SELECTED_TEXT_SHADOW_COLOR).toHTMLCode»;
			«ELSE»
				jtab-selected-text-shadow-color: none;
			«ENDIF»
		
			/* unselected tabs */
			«IF store.getBoolean(JTPConstants.InactivePartStack.UNSELECTED_FILL)»
				jtab-unselected-tabs-background: «store.getGradient(JTPConstants.InactivePartStack.UNSELECTED_FILL_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-unselected-tabs-background: none;
			«ENDIF»
			
			«IF store.getBoolean(JTPConstants.InactivePartStack.UNSELECTED_BORDER_SHOW)»
				jtab-unselected-border-color: «store.getGradient(JTPConstants.InactivePartStack.UNSELECTED_BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-unselected-border-color: none;
			«ENDIF»
			«IF !store.getPoint(JTPConstants.InactivePartStack.UNSELECTED_TEXT_SHADOW_POSITION).empty»
				jtab-unselected-text-shadow-position: «store.getPoint(JTPConstants.InactivePartStack.UNSELECTED_TEXT_SHADOW_POSITION).toCSS»;
				jtab-unselected-text-shadow-color: «store.getHSB(JTPConstants.InactivePartStack.UNSELECTED_TEXT_SHADOW_COLOR).toHTMLCode»;
			«ELSE»
				jtab-unselected-text-shadow-color: none;
			«ENDIF»
			
			/* hover tabs */
			jtab-hover-color : «store.getHSB(JTPConstants.InactivePartStack.HOVER_TEXT_COLOR).toHTMLCode»;
			«IF store.getBoolean(JTPConstants.InactivePartStack.HOVER_FILL)»
				jtab-hover-tabs-background: «store.getGradient(JTPConstants.InactivePartStack.HOVER_FILL_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-hover-tabs-background: none;
			«ENDIF»
			
			«IF store.getBoolean(JTPConstants.InactivePartStack.HOVER_BORDER_SHOW)»
				jtab-hover-border-color: «store.getGradient(JTPConstants.InactivePartStack.HOVER_BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-hover-border-color: none;
			«ENDIF»
			«IF !store.getPoint(JTPConstants.InactivePartStack.HOVER_TEXT_SHADOW_POSITION).empty»
				jtab-hover-text-shadow-position: «store.getPoint(JTPConstants.InactivePartStack.HOVER_TEXT_SHADOW_POSITION).toCSS»;
				jtab-hover-text-shadow-color: «store.getHSB(JTPConstants.InactivePartStack.HOVER_TEXT_SHADOW_COLOR).toHTMLCode»;
			«ELSE»
				jtab-hover-text-shadow-color: none;
			«ENDIF»
			
			jtab-close-button-color: «store.getHSB(JTPConstants.InactivePartStack.CLOSE_BUTTON_COLOR).toHTMLCode»;
			jtab-close-button-hot-color: «store.getHSB(JTPConstants.InactivePartStack.CLOSE_BUTTON_HOVER_COLOR).toHTMLCode»;
			jtab-close-button-active-color: «store.getHSB(JTPConstants.InactivePartStack.CLOSE_BUTTON_ACTIVE_COLOR).toHTMLCode»;
			jtab-close-button-line-width: «store.getInt(JTPConstants.InactivePartStack.CLOSE_BUTTON_LINE_WIDTH)»px;
			
			jtab-chevron-color: «store.getHSB(JTPConstants.InactivePartStack.CHEVRON_COLOR).toHTMLCode»;
		}
		
		.MPartStack>CTabItem {
			/* unselected tab text */
			color: «store.getHSB(JTPConstants.InactivePartStack.UNSELECTED_TEXT_COLOR).toHTMLCode»;
		}
		
		.MPartStack>CTabItem:selected {
			/* selected tab text */
			color: «store.getHSB(JTPConstants.InactivePartStack.SELECTED_TEXT_COLOR).toHTMLCode»;
		}
		
		
		«comment("Active Part Stack")»
		.MPartStack.active {
			swt-tab-renderer: url('«tabRendererClass»');
			
			/* tab background */
			jtab-header-background : «store.getGradient(JTPConstants.ActivePartStack.HEADER_BACKGROUND_COLOR).toSWTCSSString»;
			«IF store.getBoolean(JTPConstants.ActivePartStack.BORDER_SHOW)»
				jtab-border-color : «store.getGradient(JTPConstants.ActivePartStack.BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-border-color : none;
			«ENDIF»
			background : «store.getHSB(JTPConstants.ActivePartStack.BODY_BACKGROUND_COLOR).toHTMLCode»;
			
			/* selected tabs */
			jtab-selected-tab-background: «store.getGradient(JTPConstants.ActivePartStack.SELECTED_FILL_COLOR).toSWTCSSString»;
			«IF store.getBoolean(JTPConstants.ActivePartStack.SELECTED_BORDER_SHOW)»
				jtab-selected-border-color: «store.getGradient(JTPConstants.ActivePartStack.SELECTED_BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-selected-border-color: none;
			«ENDIF»
			«IF !store.getPoint(JTPConstants.ActivePartStack.SELECTED_TEXT_SHADOW_POSITION).empty»
				jtab-selected-text-shadow-position: «store.getPoint(JTPConstants.ActivePartStack.SELECTED_TEXT_SHADOW_POSITION).toCSS»;
				jtab-selected-text-shadow-color: «store.getHSB(JTPConstants.ActivePartStack.SELECTED_TEXT_SHADOW_COLOR).toHTMLCode»;
			«ELSE»
				jtab-selected-text-shadow-color: none;
			«ENDIF»
		
			/* unselected tabs */
			«IF store.getBoolean(JTPConstants.ActivePartStack.UNSELECTED_FILL)»
				jtab-unselected-tabs-background: «store.getGradient(JTPConstants.ActivePartStack.UNSELECTED_FILL_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-unselected-tabs-background: none;
			«ENDIF»
			
			«IF store.getBoolean(JTPConstants.ActivePartStack.UNSELECTED_BORDER_SHOW)»
				jtab-unselected-border-color: «store.getGradient(JTPConstants.ActivePartStack.UNSELECTED_BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-unselected-border-color: none;
			«ENDIF»
			«IF !store.getPoint(JTPConstants.ActivePartStack.UNSELECTED_TEXT_SHADOW_POSITION).empty»
				jtab-unselected-text-shadow-position: «store.getPoint(JTPConstants.ActivePartStack.UNSELECTED_TEXT_SHADOW_POSITION).toCSS»;
				jtab-unselected-text-shadow-color: «store.getHSB(JTPConstants.ActivePartStack.UNSELECTED_TEXT_SHADOW_COLOR).toHTMLCode»;
			«ELSE»
				jtab-unselected-text-shadow-color: none;
			«ENDIF»
			
			/* hover tabs */
			jtab-hover-color : «store.getHSB(JTPConstants.ActivePartStack.HOVER_TEXT_COLOR).toHTMLCode»;
			«IF store.getBoolean(JTPConstants.ActivePartStack.HOVER_FILL)»
				jtab-hover-tabs-background: «store.getGradient(JTPConstants.ActivePartStack.HOVER_FILL_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-hover-tabs-background: none;
			«ENDIF»
			
			«IF store.getBoolean(JTPConstants.ActivePartStack.HOVER_BORDER_SHOW)»
				jtab-hover-border-color: «store.getGradient(JTPConstants.ActivePartStack.HOVER_BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-hover-border-color: none;
			«ENDIF»
			«IF !store.getPoint(JTPConstants.ActivePartStack.HOVER_TEXT_SHADOW_POSITION).empty»
				jtab-hover-text-shadow-position: «store.getPoint(JTPConstants.ActivePartStack.HOVER_TEXT_SHADOW_POSITION).toCSS»;
				jtab-hover-text-shadow-color: «store.getHSB(JTPConstants.ActivePartStack.HOVER_TEXT_SHADOW_COLOR).toHTMLCode»;
			«ELSE»
				jtab-hover-text-shadow-color: none;
			«ENDIF»
			
			jtab-close-button-color: «store.getHSB(JTPConstants.ActivePartStack.CLOSE_BUTTON_COLOR).toHTMLCode»;
			jtab-close-button-hot-color: «store.getHSB(JTPConstants.ActivePartStack.CLOSE_BUTTON_HOVER_COLOR).toHTMLCode»;
			jtab-close-button-active-color: «store.getHSB(JTPConstants.ActivePartStack.CLOSE_BUTTON_ACTIVE_COLOR).toHTMLCode»;
			jtab-close-button-line-width: «store.getInt(JTPConstants.ActivePartStack.CLOSE_BUTTON_LINE_WIDTH)»px;
			
			jtab-chevron-color: «store.getHSB(JTPConstants.ActivePartStack.CHEVRON_COLOR).toHTMLCode»;
		}
		
		.MPartStack.active>CTabItem {
			/* unselected tab text */
			color: «store.getHSB(JTPConstants.ActivePartStack.UNSELECTED_TEXT_COLOR).toHTMLCode»;
		}
		
		.MPartStack.active>CTabItem:selected {
			/* selected tab text */
			color: «store.getHSB(JTPConstants.ActivePartStack.SELECTED_TEXT_COLOR).toHTMLCode»;
		}
		
		«comment("Busy Part")»
		.MPart{
			font-style: normal;
		}
		
		.MPart.busy {
			font-style: italic;
		}
		
		
		
		«comment("Editor Part Stack")»
		«IF store.getBoolean(JTPConstants.Layout.TRUNCATE_TAB_ITEMS)»
			.MPartStack.EditorStack{
				jtab-truncate-tab-items : «store.getBoolean(JTPConstants.Layout.TRUNCATE_EDITORS_TAB_ITEMS_ALSO)»;
				jtab-minimum-characters : «store.getInt(JTPConstants.Layout.MINIMUM_CHARACTERS_FOR_EDITORS)»;
			}
		«ENDIF»
		
		
		
		«comment("Empty Editor Stack")»
		#org-eclipse-ui-editorss.MArea .MPartStack.empty{
			jtab-header-background : «store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR).toSWTCSSString»;
			«IF store.getBoolean(JTPConstants.EmptyPartStack.BORDER_SHOW)»
				jtab-border-color : «store.getGradient(JTPConstants.EmptyPartStack.BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-border-color : none;
			«ENDIF»
			background : «store.getGradient(JTPConstants.EmptyPartStack.FILL_COLOR).last.color.toHTMLCode»;
		}
		
		
		
		«comment("Editors Area")»
		CTabFolder#org-eclipse-ui-editorss.MArea{
			swt-tab-renderer: url('«tabRendererClass»');
			swt-tab-height: 10px;
			jtab-header-background : «store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR).toSWTCSSString»;
			«IF store.getBoolean(JTPConstants.EditorsPartStack.BORDER_SHOW)»
				jtab-border-color : «store.getGradient(JTPConstants.EditorsPartStack.BORDER_COLOR).toSWTCSSString»;
			«ELSE»
				jtab-border-color : none;
			«ENDIF»
			jtab-selected-tab-background: «store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR).toSWTCSSString»;
			jtab-selected-border-color: none;
			swt-single : true;
			
			«IF store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)»
				jtab-margin : 0px 4px 4px 1px; /* top right bottom left */
				jtab-shadow-color: «store.getHSB(JTPConstants.Layout.SHADOW_COLOR).toHTMLCode»;
				jtab-shadow-position: 1px 1px;
				jtab-shadow-radius: 3px;
				jtab-padding : «store.getInt(JTPConstants.Layout.CONTENT_PADDING)»px «store.getInt(JTPConstants.Layout.CONTENT_PADDING)»px «store.getInt(JTPConstants.Layout.CONTENT_PADDING) + 2»px «store.getInt(JTPConstants.Layout.CONTENT_PADDING) + 2»px;
			«ELSE»
				jtab-margin : 0px;
				jtab-shadow-color: none;
				jtab-shadow-position: 0px 0px;
				jtab-shadow-radius: 0px;
				jtab-padding : «store.getInt(JTPConstants.Layout.CONTENT_PADDING)»px;
			«ENDIF»
		}
		
		CTabFolder#org-eclipse-ui-editorss.MArea Composite.MPartSashContainer {
			background-color: «store.getGradient(JTPConstants.EditorsPartStack.FILL_COLOR).last.color.toHTMLCode»;
		}
		
		
		
		«comment("Text Editor")»
		.MPart.Editor Canvas {
			background-color: «store.getHSB(JTPConstants.TextEditor.RULER_COLOR).toHTMLCode»;
		}
		
		.MPart.Editor StyledText,
		.MPart.Editor StyledTextWithoutVerticalBar /* PyDev support */{
			jeditor-line-style : «store.getLineStyle(JTPConstants.TextEditor.UNDER_LINE_STYLE)»;
			jeditor-line-color : «store.getHSB(JTPConstants.TextEditor.UNDER_LINE_COLOR).toHTMLCode»;
			jeditor-range-indicator-color: «rangeIndicatorColor.toHTMLCode»;
		}
		
		
		
		«comment("Customized Text Editor Support")»
		.MPart Section StyledText
		{
			background : jtexteditor-background;
			color : jtexteditor-foreground;
		}
		
		
		
		«comment("Forms")»
		.MPart FormHeading {
			jtext-background: «store.getGradient(JTPConstants.Forms.FORM_HEADING_BACKGROUND).toSWTCSSString»;
			color : «store.getHSB(JTPConstants.Forms.FORM_HEADING_TITLE_COLOR).toHTMLCode»;
			jbottom-keyline-1-color: «store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_1_COLOR).toHTMLCode»;
			jbottom-keyline-2-color: «store.getHSB(JTPConstants.Forms.FORM_HEADING_BORDER_2_COLOR).toHTMLCode»;
		}
		
		.MPart Section {
			jtitle-bar-background-color:  «store.getHSB(JTPConstants.Forms.SECTION_HEADER_TINT_COLOR).toHTMLCode»;
			jtitle-bar-border-color:  «store.getHSB(JTPConstants.Forms.SECTION_HEADER_BORDER_COLOR).toHTMLCode»;
			jtitle-bar-text-color :  «store.getHSB(JTPConstants.Forms.SECTION_HEADER_TITLE_COLOR).toHTMLCode»;
			jtitle-bar-active-text-color :  «store.getHSB(JTPConstants.Forms.SECTION_HEADER_ACTIVE_TITLE_COLOR).toHTMLCode»;
		}
		
		.MPart FormText {
			hyperlink-color: «store.getHSB(JTPConstants.Forms.HYPER_LINK_COLOR).toHTMLCode»;
			active-hyperlink-color: «store.getHSB(JTPConstants.Forms.ACTIVE_HYPER_LINK_COLOR).toHTMLCode»;
		}
		
		
		«comment("Others")»
		.DragFeedback {
			background-color: «store.getHSB(JTPConstants.Others.DRAG_FEEDBACK_COLOR).toHTMLCode»;
			jswt-alpha: «store.getInt(JTPConstants.Others.DRAG_FEEDBACK_ALPHA)»;
		}
		
		#org-eclipse-ui-HeapStatus {
			color: #F9FDBA;
		}
		
		«IF isLunaOrAbove»
			«comment("User Color and Font Changes")»
			«generateUserColorAndFontStylings()»
		«ENDIF»
		
		«comment("User Custom CSS")»
		«store.getString(JTPConstants.Others.USER_CSS)»
	'''

	def private comment(String comment) '''
		/**************************************************
		 * «comment»
		 **************************************************/
	'''

	def private toCSS(Point point) '''«point.x»px «point.y»px'''

	def private boolean isEmpty(Point point) {
		return point.x == 0 && point.y == 0
	}

	def private getLineStyle(JThemePreferenceStore store, String key) {
		switch (store.getInt(key)) {
			case SWT.LINE_SOLID: "solid"
			case SWT.LINE_DASH: "dashed"
			case SWT.LINE_DOT: "dotted"
			default: "none"
		}
	}

	def private toolbarHeight() {
		return SWTExtensions.INSTANCE.minimumToolBarHeight
	}

	def private windowMargins() {
		var margins = store.getRectangle(JTPConstants.Window.MARGINS)
		if(store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)) {
			margins.x = Math.max(margins.x - 1, 0)
			margins.width = Math.max(margins.width - 4, 0)
			margins.height = Math.max(margins.height - 4, 0)
		} else {
			margins.width = Math.max(margins.width - 1, 0)
			margins.height = Math.max(margins.height - 1, 0)
		}
		return margins
	}

	def private tabRendererClass() {
		return '''bundleclass://«JTRuntime.PLUGIN_ID»/«JeeeyulsTabRenderer.canonicalName»'''
	}

	def private partSpacing() {
		var offset = store.getInt(JTPConstants.Window.SASH_WIDTH)
		if(store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)) {
			offset = offset - 4
		}
		return Math.max(offset, 0)
	}

	def private HSB getRangeIndicatorColor() {
		var selectionColor = new HSB(Display.^default.getSystemColor(SWT.COLOR_LIST_SELECTION).RGB)
		return store.getHSB(JTPConstants.TextEditor.RULER_COLOR).getMixedWith(selectionColor, 0.5f)
	}
	
	def private generateUserColorAndFontStylings() {
		var sb = new StringBuilder();
		var colorAndFontGenerator = new ColorAndFontCSSGenerator;
		colorAndFontGenerator.run(sb);
		return sb.toString;
	}
}
