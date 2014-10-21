Template.master.helpers({
	"menuClass" : function(routeName) {
		if (Router.current().route.name == routeName) {
			return "active";
		} else {
			return "";
		}
	},

	"isETSClient" : function() {
		return (typeof __install) == "function";
	},

	"showBrowserWarning" : function() {
		return (typeof __install) != "function";
	}
});

Template.master.events({
	"click .alert .close" : function(e, t, d) {
		t.$(e.currentTarget).parent(".alert:first").slideFadeOut();
	},
	
	"click #tip-link" : function(e, t, d) {
		var url = $(e.currentTarget).attr("href");
		if (typeof __openURL == "function") {
			__openURL(url);
		} else {
			return;
		}
		e.preventDefault();
	}
});