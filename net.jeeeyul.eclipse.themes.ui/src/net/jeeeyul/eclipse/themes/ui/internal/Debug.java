package net.jeeeyul.eclipse.themes.ui.internal;

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
	private static Boolean USE_LOCAL_STORE;

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
	 * @return whether use local store server instead of themes.jeeeyul.net
	 */
	public static boolean useLocalStore() {
		if (USE_LOCAL_STORE == null) {
			String debugOption = Platform.getDebugOption("net.jeeeyul.eclipse.themes.ui/debug/useLocalStoreServer");
			USE_LOCAL_STORE = Boolean.parseBoolean(debugOption);
		}
		return USE_LOCAL_STORE;
	}
}
