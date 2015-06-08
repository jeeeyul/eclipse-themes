package net.jeeeyul.eclipse.themes.test.e4app.views;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TestView {
	
	@PostConstruct
	public void create(Composite parent) {
		Label label = new Label(parent, SWT.NORMAL);
		label.setText("Hello World");
	}
}
