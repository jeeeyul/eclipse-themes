package net.jeeeyul.eclipse.themes.decorator;

import org.eclipse.swt.custom.CTabFolder;

/**
 * 
 * @author Jeeeyul
 * 
 */
public interface ICTabFolderDecorator {
	public abstract void apply(CTabFolder tabFolder);

	public abstract void dispose();
}