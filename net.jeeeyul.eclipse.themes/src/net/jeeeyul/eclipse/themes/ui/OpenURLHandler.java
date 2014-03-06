package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.program.Program;

public class OpenURLHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String url = event.getParameter("url");
		if (url != null) {
			Program.launch(url);
		}
		return null;
	}

}
