package net.jeeeyul.eclipse.themes.test.e4app;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.splash.AbstractSplashHandler;

public class SplashHandler extends AbstractSplashHandler {

	public SplashHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(Shell splash) {
		super.init(splash);
		
		System.out.println(splash);
	}
}
