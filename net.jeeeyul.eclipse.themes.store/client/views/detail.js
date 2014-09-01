Template.detail.helpers({
	"renderModel" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		model.epf = this.epf;
		return model;
	},

	"likeCount" : function() {
		return _(this.likedBy).size();
	},

	"canInstall" : function() {
		return typeof (__install) == "function";
	},

	"comments" : function() {
		return Comments.find({}, {
			sort : {
				date : -1
			}
		});
	},

	"isOwner" : function() {
		return Meteor.user()._id == this.authorId;
	},

	"hasComment" : function() {
		return Comments.find().count() > 0;
	},

	"epfContent" : function() {
		return EPFSerializer.serialize(this.epf);
	}
});

Template.detail.events({
	"keydown #comment-field" : function(e, t, d) {
		if (e.keyCode == 13) {
			var text = t.$("#comment-field").val();
			Comments.insert({
				"epfId" : this._id,
				"authorId" : Meteor.user()._id,
				"authorName" : Meteor.user().profile.name,
				"date" : new Date(),
				"comment" : text
			});
			$("#comment-field").val("");
		}
	},

	"click #delete-button" : function(e, t, d) {
		if (confirm("Are you sure?")) {
			EPFs.remove(this._id);
			Router.go("home");
		}
	},

	"click #like-button" : function(e, t, d) {
		EPFs.update(this._id, {
			$addToSet : {
				"likedBy" : Meteor.userId()
			}
		});
	},
	
	"click #install-button" : function(e, t, d) {
		var content = EPFSerializer.serialize(this.epf);
		__install(content);
	},
});