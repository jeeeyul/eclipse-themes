Template.donate.events({
	"click a[target=_blank]" : function(e, t, d) {
		var url = $(e.currentTarget).attr("href");
		if (typeof __openURL == "function") {
			__openURL(url);
		} else {
			window.open(url, "_blank");
		}
		e.preventDefault();
	}
});