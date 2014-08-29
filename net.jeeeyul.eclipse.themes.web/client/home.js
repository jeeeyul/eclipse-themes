Template.home.helpers({
	"options" : function() {
		return {
			sort : {
				date : -1
			}
		};
	},

	"rendered" : function() {
		Session.set("page", 0);
		var me = this;
		this.scrollHook = function(e) {
			if ($(window).scrollTop() + $(window).height() == $(document).height()) {
				me.$("#show-more-button").trigger("click");
			}
		}
		$(window).on("scroll", this.scrollHook);
	},

	"destroyed" : function() {
		$(window).off("scroll", this.scrollHook);
		_(this.subscriptions).forEach(function(each) {
			each.stop();
		});
	}
});

Template.home.events({
	"click #show-more-button" : function(e, t) {
		t.subscriptions = t.subscriptions || [];

		t.$("#show-more-button").prop("disabled", true);
		var preCount = EPFs.find().count();
		var newSubscription = Meteor.subscribe("allEPFs", Session.get("page") + 1, function() {
			if (preCount != EPFs.find().count()) {
				Session.set("page", Session.get("page") + 1);
				t.$("#show-more-button").prop("disabled", false);
			}
		});
		t.subscriptions.push(newSubscription);
	}
});