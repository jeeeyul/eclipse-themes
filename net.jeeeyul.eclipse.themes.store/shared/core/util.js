HSBtoHSL = function(hsb) {
	var l = 0.5 * hsb.brightness * (2.0 - hsb.saturation);
	var s = (hsb.brightness * hsb.saturation) / (1 - Math.abs(2 * l - 1));
	if (isNaN(s)) {
		s = 0;
	}

	return {
		"hue" : hsb.hue,
		"saturation" : s,
		"lightness" : l
	};
}