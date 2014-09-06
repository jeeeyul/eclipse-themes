package net.jeeeyul.eclipse.themes.ui.store;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 * @author Jeeeyul
 */
public class OpenStoreClientHandler extends AbstractHandler {
	/**
	 * Open Store Command ID.
	 */
	public static final String COMMAND_ID = "net.jeeeyul.eclipse.themes.command.open.store";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage ap = window.getActivePage();
		try {
			ap.openEditor(new StoreEditorInput(), StoreClient.EDITOR_ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}
