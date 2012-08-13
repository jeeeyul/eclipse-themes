package net.jeeeyul.eclipse.themes.ui;

/**
 * 
 * 
 * 
 * @author Jeeeyul
 * @since 1.4
 */
public class ColorStop {
	public HSB color;
	public float percent;

	public ColorStop() {
		this.color = new HSB();
	}

	public ColorStop(float h, float s, float b, float percent) {
		this.color = new HSB(h, s, b);
		this.percent = percent;
	}

	public ColorStop(HSB color, float percent) {
		super();
		this.color = color;
		this.percent = percent;
	}

	public int percentAsInt() {
		return (int) (this.percent * 100 + .5f);
	}

	public ColorStop getCopy() {
		return new ColorStop(this.color.getCopy(), this.percent);
	}
}