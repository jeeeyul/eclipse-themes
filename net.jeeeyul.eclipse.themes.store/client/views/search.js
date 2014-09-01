Template.search.helpers({
	"options" : function() {
		return {
			"sort" : {
				date : -1
			}
		};
	},

	"rendered" : function() {
		Session.set("page", 0);
		var me = this;
		this.scrollHook = function(e) {
			if ($(window).scrollTop() + $(window).height() == $(document)
					.height()) {
				me.$("#show-more-button").trigger("click");
			}
		}
		$(window).on("scroll", this.scrollHook);
		
		if(Router.current().params.keyword){
			this.$("#search-field").val(Router.current().params.keyword);
			this.$(".search").trigger("search");
		}
	},

	"destroyed" : function() {
		$(window).off("scroll", this.scrollHook);
		_(this.subscriptions).forEach(function(each) {
			each.stop();
		});
	}
});

Template.search.events({
	"keydown #search-field" : function(e, t, d) {
		if (e.keyCode == 13) {
			t.$(".search").trigger("search");
		}
	},
	"search" : function(e, t, d){
		var keyword = t.$("#search-field").val().trim();
		_(t.subscriptions).forEach(function(each) {
			each.stop();
		});
		t.subscriptions = [];
		Session.set("page", 0);
		var newSubscription = Meteor.subscribe("allEPFsByKeyword", keyword);
		t.subscriptions.push(newSubscription);
		t.$("#show-more-button").prop("disabled", false);
	},

	"click #show-more-button" : function(e, t) {
		t.subscriptions = t.subscriptions || [];

		t.$("#show-more-button").prop("disabled", true);
		var preCount = EPFs.find().count();
		var newSubscription = Meteor.subscribe("allEPFsByKeyword", t.$(
				"#search-field").val().trim(), Session.get("page") + 1,
				function() {
					if (preCount != EPFs.find().count()) {
						Session.set("page", Session.get("page") + 1);
						t.$("#show-more-button").prop("disabled", false);
					}
				});
		t.subscriptions.push(newSubscription);
	},
	
	"focus input" : function(e, t, d){
		setTimeout(function(){
			$(e.target).select();
		});
	}
});