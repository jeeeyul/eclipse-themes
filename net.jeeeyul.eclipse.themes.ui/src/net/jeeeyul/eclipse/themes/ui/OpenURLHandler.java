package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.program.Program;

/**
 * Command handler for command "net.jeeeyul.eclipse.themes.command.open.url".
 * 
 * @author Jeeeyul
 * @since 2.1
 */
public class OpenURLHandler extends AbstractHandler implements IHandler {
	/**
	 * The command id that is handled by this handler.
	 */
	public static final String COMMAND_ID = "net.jeeeyul.eclipse.themes.command.open.url";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String url = event.getParameter("url");
		if (url != null) {
			Program.launch(url);
		}
		return null;
	}

}
