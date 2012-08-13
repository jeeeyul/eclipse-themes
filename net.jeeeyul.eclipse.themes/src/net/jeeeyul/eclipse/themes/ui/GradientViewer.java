package net.jeeeyul.eclipse.themes.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

public class GradientViewer {
	private GradientScale control;
	private Gradient input = new Gradient();
	private boolean ignoreModify = false;

	public Gradient getInput() {
		return this.input;
	}

	public void setInput(Gradient input) {
		if (this.input == input) {
			return;
		}
		this.input = input;
		updateView();
	}

	private void updateView() {
		if (this.control == null || this.control.isDisposed()) {
			return;
		}

		this.ignoreModify = true;

		for (GradientItem each : this.control.getItems()) {
			each.dispose();
		}

		for (ColorStop each : this.input) {
			GradientItem item = new GradientItem(this.control, SWT.NORMAL);
			item.setPercent(each.percent);
			item.setColor(each.color);
			System.out.println(each);
		}
		this.ignoreModify = false;
	}

	private Procedure1<Gradient> gradientChangedHandler;

	public GradientViewer() {
		this.input.add(new ColorStop());
	}

	public GradientViewer(Composite parent) {

		this.control = new GradientScale(parent, SWT.NORMAL);

		updateView();

		this.control.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!GradientViewer.this.ignoreModify) {
					updateInput();
				}
			}
		});
	}

	public GradientScale getControl() {
		return this.control;
	}

	public Procedure1<Gradient> getGradientChangedHandler() {
		return this.gradientChangedHandler;
	}

	public void setGradientChangedHandler(Procedure1<Gradient> gradientChangedHandler) {
		this.gradientChangedHandler = gradientChangedHandler;
	}

	protected void updateInput() {
		List<GradientItem> sortedItems = this.control.getSortedItems();
		this.input.clear();

		for (GradientItem each : sortedItems) {
			ColorStop stop = new ColorStop();
			stop.color = each.getColor();
			stop.percent = each.getPercent();
			this.input.add(stop);
		}

		if (this.gradientChangedHandler != null) {
			this.gradientChangedHandler.apply(this.input);
		}
	}
}
