package net.jeeeyul.eclipse.themes.internal;

import java.text.MessageFormat;

import net.jeeeyul.eclipse.themes.JTRuntime;

import org.eclipse.core.runtime.Platform;

/**
 * Debug tool
 * 
 * @author Jeeeyul
 */
public class Debug {
	private static Boolean DEBUG;
	private static Boolean DEBUG_GUI;
	private static Boolean USE_LOCAL_STORE;

	private static FPSCounter fpsCounter;

	public static FPSCounter getFpsCounter() {
		if (fpsCounter == null) {
			fpsCounter = new FPSCounter();
			fpsCounter.open();
		}
		return fpsCounter;
	}

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
			String debugOption = Platform.getDebugOption(MessageFormat.format("{0}/debug", JTRuntime.PLUGIN_ID));
			DEBUG = Boolean.parseBoolean(debugOption);
		}
		return DEBUG;
	}

	/**
	 * 
	 * @return whether running under gui debug mode
	 */
	public static boolean isDebuggingGUI() {
		if (DEBUG_GUI == null) {
			String debugOption = Platform.getDebugOption(MessageFormat.format("{0}/debug/gui", JTRuntime.PLUGIN_ID));
			DEBUG_GUI = Boolean.parseBoolean(debugOption);
		}
		return DEBUG_GUI;
	}

	/**
	 * @return whether use local store server instead of themes.jeeeyul.net
	 */
	public static boolean useLocalStore() {
		if (USE_LOCAL_STORE == null) {
			String debugOption = Platform.getDebugOption("net.jeeeyul.eclipse.themes/debug/useLocalStoreServer");
			USE_LOCAL_STORE = Boolean.parseBoolean(debugOption);
		}
		return USE_LOCAL_STORE;
	}

	public static void countFrame() {
		if (isDebuggingGUI())
			getFpsCounter().addCount();
	}
}
