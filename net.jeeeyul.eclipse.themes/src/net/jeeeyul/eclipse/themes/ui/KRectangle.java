package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;

public class KRectangle {
	public int x;
	public int y;
	public int width;
	public int height;

	public KRectangle() {
	}

	public KRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public KRectangle(Rectangle bounds) {
		this(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public KRectangle(Event event) {
		this(event.x, event.y, event.width, event.height);
	}

	public boolean contains(int x, int y, int width, int height) {
		return this.x <= x && this.y <= y && x + width <= this.x + this.width && y + height < this.y + this.height;
	}

	public boolean contains(KRectangle rect) {
		return contains(rect.x, rect.y, rect.width, rect.height);
	}

	public boolean contains(Rectangle rect) {
		return contains(rect.x, rect.y, rect.width, rect.height);
	}

	public KRectangle expand(int width, int height) {
		this.width += width;
		this.height += height;
		return this;
	}

	public KRectangle getCopy() {
		return new KRectangle(x, y, width, height);
	}

	public KRectangle getExpand(int width, int height) {
		return getCopy().expand(width, height);
	}

	public KRectangle getTranslated(int x, int y) {
		return getCopy().translate(x, y);
	}

	public KRectangle getUnion(int x, int y, int width, int height) {
		return getCopy().union(x, y, width, height);
	}

	public Rectangle toRectangle() {
		return new Rectangle(x, y, width, height);
	}

	@Override
	public String toString() {
		return "KRectangle [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	public KRectangle translate(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public KRectangle union(int x, int y, int width, int height) {
		int newX = Math.min(this.x, x);
		int newY = Math.min(this.y, y);
		int newWidth = Math.max(this.x + this.width, x + width) - newX;
		int newHeight = Math.max(this.y + this.height, y + height) - newY;

		this.x = newX;
		this.y = newY;
		this.width = newWidth;
		this.height = newHeight;

		return this;
	}

	public KRectangle union(Rectangle bounds) {
		return union(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public KRectangle setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public KRectangle setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public KPoint getRight() {
		return new KPoint(x + width, y + height / 2);
	}

	public boolean contains(int x, int y) {
		return this.x <= x && this.y <= y && x <= this.x + this.width && y <= this.y + this.height;
	}

	public boolean contains(KPoint point) {
		return contains(point.x, point.y);
	}

	public KPoint getBottom() {
		return new KPoint(this.x + this.width / 2, this.y + this.height);
	}

	public KPoint getTopLeft() {
		return new KPoint(this.x, this.y);
	}

	public KPoint getTop() {
		return new KPoint(this.x + this.width / 2, this.y);
	}

	public KPoint getTopRight() {
		return new KPoint(this.x + this.width, this.y);
	}

	public KPoint getBottomLeft() {
		return new KPoint(this.x, this.y + this.height);
	}

	public KPoint getBottomRight() {
		return new KPoint(this.x + width, this.y + this.height);
	}

}
