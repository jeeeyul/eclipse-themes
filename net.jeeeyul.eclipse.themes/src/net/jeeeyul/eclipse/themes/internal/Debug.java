package net.jeeeyul.eclipse.themes.internal;

import java.text.MessageFormat;

import net.jeeeyul.eclipse.themes.JThemesCore;

import org.eclipse.core.runtime.Platform;

/**
 * Debug tool
 * 
 * @author Jeeeyul
 */
public class Debug {
	private static Boolean DEBUG;
	private static Boolean DEBUG_GUI;

	/**
	 * Logs a message
	 * 
	 * @param o
	 *            message to log.
	 */
	public static void println(Object o) {
		String msg = o != null ? o.toString() : "null";
		if (isDebugging()) {
			StackTraceElement element = Thread.currentThread().getStackTrace()[2];
			System.out.println(MessageFormat.format("({0}:{1, number,#}) : {2}", element.getFileName(), element.getLineNumber(), msg));
		}
	}

	/**
	 * @return whether running under debug mode
	 */
	public static boolean isDebugging() {
		if (DEBUG == null) {
			DEBUG = JThemesCore.getDefault().isDebugging();
		}
		return DEBUG;
	}

	/**
	 * 
	 * @return whether running under gui debug mode
	 */
	public static boolean isDebugginGUI() {
		if (DEBUG_GUI == null) {
			String debugOption = Platform.getDebugOption("net.jeeeyul.eclipse.themes/debug/gui");
			DEBUG_GUI = Boolean.parseBoolean(debugOption);
		}
		return DEBUG_GUI;
	}
}
