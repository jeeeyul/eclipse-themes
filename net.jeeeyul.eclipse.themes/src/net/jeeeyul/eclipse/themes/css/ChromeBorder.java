package net.jeeeyul.eclipse.themes.css;

import java.util.HashSet;

import net.jeeeyul.eclipse.themes.ui.KRectangle;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ChromeBorder {
	private static HashSet<ChromeBorder> INSTANCES = new HashSet<ChromeBorder>();

	public static void disposeAll() {
		ChromeBorder[] array = INSTANCES.toArray(new ChromeBorder[INSTANCES.size()]);
		for (ChromeBorder each : array) {
			each.dispose();
		}
	}

	public static ChromeBorder get(Composite control) {
		ChromeBorder border = (ChromeBorder) control.getData(ChromeBorder.class.getName());
		if (border == null) {
			border = new ChromeBorder(control);
		}
		return border;
	}

	private Composite parent;
	private Color topColor;
	private Color bottomColor;
	private Color leftColor;
	private Color rightColor;
	private boolean topVisible = false;
	private boolean leftVisible = false;
	private boolean rightVisible = false;

	private boolean bottomVisible = false;

	private Listener controlHook;

	public ChromeBorder(Composite parent) {
		Assert.isNotNull(parent);
		Assert.isLegal(!parent.isDisposed());
		this.parent = parent;

		controlHook = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ChromeBorder.this.handleEvent(event);
			}
		};

		parent.addListener(SWT.Paint, controlHook);
		parent.addListener(SWT.Dispose, controlHook);
		parent.setData(ChromeBorder.class.getName(), this);
		INSTANCES.add(this);
	}

	public void dispose() {
		if (!parent.isDisposed()) {
			parent.removeListener(SWT.Paint, controlHook);
			parent.removeListener(SWT.Dispose, controlHook);
		}
		INSTANCES.remove(this);
	}

	public Color getBottomColor() {
		return bottomColor;
	}

	public Color getLeftColor() {
		return leftColor;
	}

	public Color getRightColor() {
		return rightColor;
	}

	public Color getTopColor() {
		return topColor;
	}

	protected void handleEvent(Event event) {
		switch (event.type) {
		case SWT.Paint:
			onPaint(event);
			break;

		case SWT.Dispose:
			dispose();
			break;
		}
	}

	public boolean isBottomVisible() {
		return bottomVisible;
	}

	public boolean isLeftVisible() {
		return leftVisible;
	}

	public boolean isRightVisible() {
		return rightVisible;
	}

	public boolean isTopVisible() {
		return topVisible;
	}

	private void onPaint(Event event) {
		KRectangle bounds = new KRectangle(parent.getClientArea());
		if (topVisible && topColor != null) {
			event.gc.setForeground(topColor);
			event.gc.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y);
		}
	}

	public void setBottomColor(Color bottom) {
		this.bottomColor = bottom;
	}

	public void setBottomVisible(boolean bottomVisible) {
		this.bottomVisible = bottomVisible;
	}

	public void setLeftColor(Color left) {
		this.leftColor = left;
	}

	public void setLeftVisible(boolean leftVisible) {
		this.leftVisible = leftVisible;
	}

	public void setRightColor(Color right) {
		this.rightColor = right;
	}

	public void setRightVisible(boolean rightVisible) {
		this.rightVisible = rightVisible;
	}

	public void setTopColor(Color top) {
		this.topColor = top;
		parent.redraw();
	}

	public void setTopVisible(boolean topVisible) {
		this.topVisible = topVisible;
	}

}
