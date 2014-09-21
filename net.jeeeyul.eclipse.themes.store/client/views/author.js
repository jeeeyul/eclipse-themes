Template.author.helpers({
	"options" : function() {
		return {
			"sort" : {
				date : -1
			}
		};
	},

	"rendered" : function() {
		googleAnalytics();
	}
});

Template.author.events({});

Paging.apply(Template.author, "allEPFsByAuthor", function() {
	return [ this._id ];
});