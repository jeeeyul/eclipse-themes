Template.menu.helpers({
	"menuClass" : function(routeName) {
		if (Router.current().route.name == routeName) {
			return "active";
		} else {
			return "";
		}
	},

	"username" : function() {
		var name = Meteor.user().profile.name;
		if (typeof name != "string" || name == null || name == undefined || name.trim().length == 0) {
			name = "Noname";
		}
		return name;
	}
});

Template.menu.events({
	"click .menu-item" : function(e, t, d) {
		if (t.$("button.navbar-toggle").hasClass("collapsed")) {
			return;
		}
		t.$("button.navbar-toggle").trigger("click");
	}
});
