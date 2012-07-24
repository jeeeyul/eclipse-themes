package net.jeeeyul.eclipse.themes.decorator;

import org.eclipse.swt.custom.CTabFolder;

/**
 * 
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
public interface ICTabFolderDecorator {
	public abstract void apply(CTabFolder tabFolder);

	public abstract void dispose();
}