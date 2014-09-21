Template.profile.helpers({
	"rendered" : function() {
		
	}
});

Template.profile.events({
	"click #update-button" : function(e, t, d) {
		t.$("#update-button").attr("disabled", true);
		Meteor.call("updateProfile", {
			"name" : t.$("input#name-field").val().trim(),
			"homepage" : t.$("input#homepage-field").val().trim()
		}, function(err, res) {
			if (err) {
				alert(err);
			} else {
				alert("Updated!");
			}
			t.$("#update-button").attr("disabled", false);
		});
	},

	"focus input" : function(e, t, d) {
		var me = $(e.currentTarget);
		setTimeout(me.select.bind(me));
	},

	"click #logout-button" : function(e, t, d) {
		Meteor.logout();
	}
});
