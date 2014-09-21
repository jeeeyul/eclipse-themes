Template.author.helpers({
	"options" : function() {
		return {
			"sort" : {
				date : -1
			}
		};
	},

	"rendered" : function() {
		
	}
});

Template.author.events({});

Paging.apply(Template.author, "allEPFsByAuthor", function() {
	return [ this._id ];
});