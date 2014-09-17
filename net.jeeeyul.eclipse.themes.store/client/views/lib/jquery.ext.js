$.fn.slideFadeOut = function(callback) {
	this.animate({
		opacity : 0,
		height : 0,
		paddingTop : 0,
		paddingBottom : 0,
		marginTop : 0,
		marginBottom : 0
	}, function() {
		if (typeof callback == "function") {
			callback.call(this);
		}
		$(this).remove();
	});
}