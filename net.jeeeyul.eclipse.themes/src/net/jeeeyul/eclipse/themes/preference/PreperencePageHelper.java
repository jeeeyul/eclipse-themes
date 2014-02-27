package net.jeeeyul.eclipse.themes.preference;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.swtend.ui.ColorStop;
import net.jeeeyul.swtend.ui.Gradient;
import net.jeeeyul.swtend.ui.HSB;

public class PreperencePageHelper {

	public PreperencePageHelper() {
	}

	public List<HSB> asSWTSafeHSBArray(Gradient gradient) {
		ArrayList<HSB> result = new ArrayList<HSB>();

		ColorStop first = gradient.get(0);
		if (first.percent != 0) {
			result.add(first.color);
		}

		for (ColorStop each : gradient) {
			result.add(each.color);
		}

		ColorStop last = gradient.get(gradient.size() - 1);
		if (last.percent != 100) {
			result.add(last.color);
		}

		return result;
	}

	public List<Integer> asSWTSafePercentArray(Gradient gradient) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		ColorStop first = gradient.get(0);
		if (first.percent != 0) {
			result.add(first.percent);
		}

		for (int i = 1; i < gradient.size(); i++) {
			ColorStop each = gradient.get(i);
			result.add(each.percent);
		}

		ColorStop last = gradient.get(gradient.size() - 1);
		if (last.percent != 100) {
			result.add(100);
		}

		return result;
	}
}
