Template.detail.helpers({
	"renderModel" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		model.epf = this.epf;
		return model;
	},

	canLike : function() {
		var canLike = true;
		canLike = canLike && (Meteor.userId() != null);
		canLike = canLike && (this.likedBy == undefined || (_(this.likedBy).contains(Meteor.userId()) == false));
		return canLike;
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
	},
	
	canInject : function() {
		return typeof __getCurrentEPF == "function";
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

	"click #download-epf" : function(e, t, d) {
		window.open("/epf/" + t.data._id);
	},

	"click #update-button" : function(e, t, d) {
		var text = t.$("#epf-field").val();

		try {
			var epf = EPFParser.parse(text);
			var model = {};
			_(epf).forEach(function(it) {
				model[it.key] = it.value;
			});

			var themeName = t.$("#name-field").val().trim();
			var description = t.$("#description-field").val().trim();

			if (themeName.length == 0) {
				alert("Theme name was not specified.");
				return;
			}

			EPFs.update(this._id, {
				$set : {
					"name" : themeName,
					"description" : description,
					"epf" : epf,
					"date" : new Date()
				}
			});
		} catch (e) {
			alert(e);
			alert("EPF Syntax is not valid!");
		}
	},
	
	"click #inject-button" : function(e, t, d) {
		t.$("#epf-field").val(__getCurrentEPF());
	},
	
	"focus textarea" : function(e, t, d){
		setTimeout(function(){
			$(e.target).select();
		});
	}
});