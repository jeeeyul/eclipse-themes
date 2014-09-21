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
		
	}
});

Paging.apply(Template.toplike, "allEPFsByRating");