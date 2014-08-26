package net.jeeeyul.eclipse.themes.internal;

import java.text.MessageFormat;

import net.jeeeyul.eclipse.themes.JThemesCore;

/**
 * Debug tool
 * 
 * @author Jeeeyul
 */
public class Debug {
	/**
	 * Logs a message
	 * 
	 * @param o
	 *            message to log.
	 */
	public static void println(Object o) {
		String msg = o != null ? o.toString() : "null";
		if (JThemesCore.getDefault().isDebugging()) {
			StackTraceElement element = Thread.currentThread().getStackTrace()[2];
			System.out.println(MessageFormat.format("({0}:{1, number,#}) : {2}", element.getFileName(), element.getLineNumber(), msg));
		}
	}
}
