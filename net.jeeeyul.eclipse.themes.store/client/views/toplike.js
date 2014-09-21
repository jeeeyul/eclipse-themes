Template.toplike.helpers({
	"options" : function() {
		return {
			sort : {
				likeCount : -1,
				date : -1
			}
		}
	},

	"rendered" : function() {
		googleAnalytics();
	}
});

Paging.apply(Template.toplike, "allEPFsByRating");