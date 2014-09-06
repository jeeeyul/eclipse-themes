package net.jeeeyul.eclipse.themes.test.e4app;

import org.eclipse.e4.core.di.annotations.Execute;

public class AboutHandler {
	@Execute
	public void execute() {
		System.out.println("about!");
	}
}