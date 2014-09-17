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
		return (typeof __install) != "function" && Session.get("hide-browser-warning") != true;
	}
});

Template.master.events({
	"click .browser-warning .close" : function(e, t, d) {
		t.$(".browser-warning").slideFadeOut(function() {
			Session.set("hide-browser-warning", true);
		});
	}
});