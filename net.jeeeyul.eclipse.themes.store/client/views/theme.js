Template.theme.helpers({
	canLike : function() {
		var canLike = true;
		canLike = canLike && (Meteor.userId() != null);
		canLike = canLike && (this.likedBy == undefined || (_(this.likedBy).contains(Meteor.userId()) == false));
		return canLike;
	},

	"canInstall" : function() {
		return typeof (__install) == "function";
	},
});

Template.theme.events({
	"click #install-button" : function(e, t, d) {
		var content = EPFSerializer.serialize(this.epf);
		__install(content);
	},
	"click #author-link" : function(e, t, d) {
		Router.go("author", {
			"id" : this.authorId
		});

		e.preventDefault();
	},
	"click .preview" : function(e, t, d) {
		Router.go("detail", {
			"id" : t.data._id
		});
	},
	"click #download-epf" : function(e, t, d) {
		window.open("/epf/" + t.data._id);
	},
	"click #like-button" : function(e, t, d) {
		EPFs.update(this._id, {
			$addToSet : {
				"likedBy" : Meteor.userId()
			}
		});
	},
	"click #like-button" : function(e, t, d) {
		EPFs.update(this._id, {
			$addToSet : {
				"likedBy" : Meteor.userId()
			}
		});
	},
});