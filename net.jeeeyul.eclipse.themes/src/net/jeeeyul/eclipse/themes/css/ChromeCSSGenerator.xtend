package net.jeeeyul.eclipse.themes.css

import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig
import org.eclipse.swt.graphics.RGB
import net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig

class ChromeCSSGenerator {
	IChromeThemeConfig config = ChromeThemeConfig::instance

	def setConfig(IChromeThemeConfig config){
		this.config = config
	}

	def generate()'''
		/*
		 *  Chrome Theme generate css dynamically, So do not inspect this file, See "ChromeCSSGenerator.xtend" instead
		 */
		.jeeeyul-chrome-theme{
			/*
			 * This selector rule is exist for detect Chrome Theme to find rewrite target. 
			 * See "RewriteChormeCSS.java"
			 */	
		}
		
		.Editor StyledText{
			chrome-line-visible: «config.editorLineVisible»;
			chrome-line-dash: «config.editorLineDashed»;
			chrome-line-color: «config.editorLineColor.toHtmlColor»;
		}
		
		.MTrimmedWindow.topLevel {
			margin-top: «config.sashWidth + if(config.usePartShadow) 3 else 0»px;
			margin-bottom: 2px;
			margin-left: 2px;
			margin-right: 2px;
		}
		
		.MPartStack {
			font-size: «config.partFontData.height as int»;
			font-family: '«config.partFontData.name»';
			swt-simple: true;
			swt-tab-renderer:
				url('bundleclass://net.jeeeyul.eclipse.themes/net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering');
		
			padding: «config.partStackPadding»px «config.partStackPadding+5»px «config.partStackPadding+7»px «config.partStackPadding+5»px; /* top left bottom right */
			swt-tab-outline: «config.inactiveOulineColor.toHtmlColor»;
			swt-outer-keyline-color: «config.inactiveOulineColor.toHtmlColor»;
			swt-unselected-tabs-color: «config.inactivePartGradientStart.toHtmlColor» «config.inactivePartGradientEnd.toHtmlColor» «config.inactiveSelectedTabEndColor.toHtmlColor» 99% 100%;
			swt-shadow-visible: «config.usePartShadow»;
			
			swt-selected-tab-fill: «config.inactiveSelectedTabEndColor.toHtmlColor»;
			chrome-selected-tab-fill-highlight: «config.inactiveSelectedTabStartColor.toHtmlColor»;
			
			chrome-selected-tab-color: «config.inactiveSelectedTitleColor.toHtmlColor»;
			chrome-unselected-tab-color: «config.inactiveUnselectedTitleColor.toHtmlColor»;
			
			swt-shadow-color: «config.partShadowColor.toHtmlColor»;
			
			chrome-shiney-shadow: «config.useInactivePartTitleShadow»;
			swt-mru-visible: «config.mruVisible»;
		}
		
		.MPartStack.active {
			swt-inner-keyline-color: #FFFFFF;
			swt-tab-outline: «config.activeOulineColor.toHtmlColor»;
			swt-outer-keyline-color: «config.activeOulineColor.toHtmlColor»;
			swt-unselected-tabs-color: «config.activePartGradientStart.toHtmlColor» «config.activePartGradientEnd.toHtmlColor» «config.activeSelectedTabEndColor.toHtmlColor» 99% 100%;
			
			swt-selected-tab-fill: «config.activeSelectedTabEndColor.toHtmlColor»;
			chrome-selected-tab-fill-highlight: «config.activeSelectedTabStartColor.toHtmlColor»;
			
			chrome-selected-tab-color: «config.activeSelectedTitleColor.toHtmlColor»;
			chrome-unselected-tab-color: «config.activeUnselectedTitleColor.toHtmlColor»;
			chrome-shiney-shadow: «config.useActivePartTitleShadow»;
		}
		
		.MPartStack.empty {
			swt-unselected-tabs-color: «config.emptyPartBackgroundColor.toHtmlColor» «config.emptyPartBackgroundColor.toHtmlColor» «config.emptyPartBackgroundColor.toHtmlColor» 99% 100%;
			swt-tab-outline: «config.emptyPartOutloneColor.toHtmlColor»;
			swt-outer-keyline-color: «config.emptyPartOutloneColor.toHtmlColor»;
		}
		
		.MToolControl.TrimStack {
			frame-image: url("./winXPTSFrame.png");
			handle-image: url("./winXPHandle.png");
			frame-cuts: 5px 1px 5px 16px;
		}
		
		.MTrimmedWindow {
		  	margin-top: 2px;
			margin-bottom: 2px;
			«IF config.usePartShadow»
				margin-left: 0px;
				margin-right: 0px;
			«ELSE»
				margin-left: 2px;
				margin-right: 2px;
			«ENDIF»
			background-color: «config.windowBackgroundColor.toHtmlColor»;
		}
		
		.MTrimBar {
			background-color: «config.windowBackgroundColor.toHtmlColor»;
		}
		
		.MTrimBar#org-eclipse-ui-main-toolbar {
			background-color: «config.toolbarGradientStart.toHtmlColor» «config.toolbarGradientEnd.toHtmlColor»;
		}
		
		.MToolControl.TrimStack {
			frame-image: url("./jeeeyul-TSFrame.png");
			handle-image: url("./jeeeyul-Handle.png");
		}
		
		#PerspectiveSwitcher {
			eclipse-perspective-keyline-color: «config.perspectiveOutlineColor.toHtmlColor»;
			background-color: «config.getPerspectiveStartColor.toHtmlColor» «config.perspectiveEndColor.toHtmlColor» 100%;
		}
		
		CTabFolder.MArea .MPartStack,CTabFolder.MArea .MPartStack.active {
			swt-shadow-visible: false;
		}
		
		CTabFolder Canvas {
			background-color: #F8F8F8;
		}
		
		#org-eclipse-ui-editorss {
			swt-tab-renderer:
				url('bundleclass://org.eclipse.e4.ui.workbench.renderers.swt/org.eclipse.e4.ui.workbench.renderers.swt.CTabRendering');
			swt-unselected-tabs-color: #F0F0F0 #F0F0F0 #F0F0F0 100% 100%;
			swt-outer-keyline-color: #B4B4B4;
			swt-inner-keyline-color: #F0F0F0;
			swt-tab-outline: #F0F0F0;
			color: #F0F0F0;
			swt-tab-height: 8px;
			padding: 0px 5px 7px;
		}
	'''

	def private String toHtmlColor(RGB rgb){
		return String::format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue)
	}
}