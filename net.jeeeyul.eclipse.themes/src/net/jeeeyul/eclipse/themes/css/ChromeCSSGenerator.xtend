package net.jeeeyul.eclipse.themes.css

import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig
import org.eclipse.swt.graphics.RGB
import net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig

class ChromeCSSGenerator {
	IChromeThemeConfig config = ChromeThemeConfig::instance

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
			swt-mru-visible: false;
			swt-tab-renderer:
				url('bundleclass://net.jeeeyul.eclipse.themes/net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering');
		
			padding: 0px 9px 10px;
			swt-tab-outline: #B6BCCC;
			swt-outer-keyline-color: #B6BCCC;
			swt-inner-keyline-color: black;
			swt-unselected-tabs-color: #eee #ddd white 99% 100%;
			swt-shadow-visible: «config.usePartShadow»;
			
			selected-tab-color: #000;
			unselected-tab-color: #333;
			
			shadow-color: #aaa;
			
		}
		
		.MPartStack.active {
			swt-inner-keyline-color: #FFFFFF;
			swt-tab-outline: «config.activeOulineColor.toHtmlColor»;
			swt-outer-keyline-color: «config.activeOulineColor.toHtmlColor»;
			swt-unselected-tabs-color: «config.activePartGradientStart.toHtmlColor» «config.activePartGradientEnd.toHtmlColor» white 99% 100%;
		}
		
		.MPartStack.empty {
			swt-unselected-tabs-color: #ddd #ddd #ddd 99% 100%;
		}
		
		.MToolControl.TrimStack {
			frame-image: url("./winXPTSFrame.png");
			handle-image: url("./winXPHandle.png");
			frame-cuts: 5px 1px 5px 16px;
		}
		
		.MTrimmedWindow {
			background-color: #eee;
		}
		
		.MTrimBar {
			background-color: #eee;
		}
		
		.MTrimBar#org-eclipse-ui-main-toolbar {
			background-color: #eee #ddd;
		}
		
		.MToolControl.TrimStack {
			frame-image: url("./jeeeyul-TSFrame.png");
			handle-image: url("./jeeeyul-Handle.png");
		}
		
		#PerspectiveSwitcher {
			eclipse-perspective-keyline-color: #AAB0BF #AAB0BF;
			background-color: #F5F7FC #eee 100%;
		}
		
		CTabFolder.MArea .MPartStack,CTabFolder.MArea .MPartStack.active {
			
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