package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;

public class GradientItem extends Item {
	private HSB color = new HSB();

	private float percent = 0f;
	private final int width = 10;
	private final int arrowHeight = 5;

	private KRectangle bounds;
	private final GradientScale parent;

	public GradientItem(GradientScale parent, int style) {
		super(parent, style);
		this.parent = parent;
		parent.items.add(this);

		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onDispose();
			}
		});

		this.parent.notifyModification();
	}

	private KRectangle computeBounds() {
		KRectangle itemArea = this.parent.getItemArea();

		KRectangle result = itemArea.getCopy();
		result.x = (int) (itemArea.width * this.percent + itemArea.x + 0.5f - this.width / 2);
		result.width = this.width;

		return result;
	}

	protected void draw(GC gc, int detail) {

		if ((detail & SWT.SELECTED) != 0) {
			gc.setBackground(this.parent.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
		} else if ((detail & SWT.HOT) != 0) {
			gc.setBackground(this.parent.getHotItemColor());
		} else {
			gc.setBackground(this.parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		}

		gc.setForeground(this.parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER));

		KRectangle bounds = getBounds();

		int[] polygon = toArrow(bounds);
		gc.fillPolygon(polygon);
		gc.drawPolygon(polygon);
		KRectangle inner = bounds.getCopy().translate(2, 2 + this.arrowHeight).expand(-3, -3 - this.arrowHeight);
		Color color = new Color(this.parent.getDisplay(), this.color.toRGB());
		gc.setBackground(color);
		gc.fillRectangle(inner.toRectangle());

		gc.setForeground(this.parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER));
		gc.drawRectangle(inner.expand(-1, -1).toRectangle());
		color.dispose();
	}

	public KRectangle getBounds() {
		if (this.bounds == null) {
			this.bounds = computeBounds();
		}
		return this.bounds;
	}

	public HSB getColor() {
		return this.color;
	}

	public float getPercent() {
		return this.percent;
	}

	protected void invalidate() {
		this.bounds = null;
	}

	protected void onDispose() {
		if (this.parent == null || this.parent.isDisposed()) {
			return;
		}
		this.parent.items.remove(this);
		this.parent.notifyModification();
	}

	public void redraw() {
		KRectangle bounds = getBounds();
		this.parent.redraw(bounds.x, bounds.y, bounds.width, bounds.height, false);
	}

	public void setColor(HSB hsb) {
		this.color = hsb;
		this.parent.redraw();
		this.parent.notifyModification();
	}

	public void setPercent(float percent) {
		if (this.percent == percent) {
			return;
		}
		invalidate();
		this.percent = percent;
		redraw();
		this.parent.notifyModification();
	}

	private int[] toArrow(KRectangle bounds) {
		int[] polygon = new int[] { bounds.getTop().x, bounds.getTop().y, bounds.x, bounds.y + this.arrowHeight, bounds.x, bounds.y + bounds.height,
				bounds.x + bounds.width, bounds.y + bounds.height, bounds.x + bounds.width, bounds.y + this.arrowHeight, };
		return polygon;
	}

}
