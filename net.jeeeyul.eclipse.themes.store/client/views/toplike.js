Template.toplike.helpers({
	"options" : function() {
		return {
			sort : {
				likeCount : -1,
				date : -1
			}
		}
	}
});

Paging.apply(Template.toplike, "allEPFsByRating");