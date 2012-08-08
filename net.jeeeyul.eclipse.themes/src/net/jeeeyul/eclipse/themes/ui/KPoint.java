package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.graphics.Point;

public class KPoint {
	public int x;
	public int y;

	public KPoint() {

	}

	public KPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public KPoint(KPoint original) {
		this.x = original.x;
		this.y = original.y;
	}

	public KPoint getCopy() {
		return new KPoint(this);
	}

	public KPoint getTranslated(int dx, int dy) {
		return getCopy().translate(dx, dy);
	}

	public Point toPoint() {
		return new Point(x, y);
	}

	public KPoint translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		return this;
	}
}
