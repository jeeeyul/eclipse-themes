package net.jeeeyul.eclipse.themes.preference;

import org.eclipse.swt.graphics.RGB;

public class Test {
	public static void main(String[] args) {
		RGB rgb = new RGB(221, 221, 221);
		float[] hsb = rgb.getHSB();
		
		System.out.println(hsb[0] + " " + hsb[1] + " " + hsb[2]);
	}
}
