var sharedHelpers = {
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
		if (Meteor.user() == null) {
			return false;
		}
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
	},

	"formHeadingStyle" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		var styles = [];
		if (model["FORMS__FORM_HEADING_BACKGROUND"]) {
			styles.push({
				key : "background",
				value : PreviewHelper.getBackground(model["FORMS__FORM_HEADING_BACKGROUND"])
			});
		}

		if (model["FORMS__FORM_HEADING_BORDER_1_COLOR"]) {
			styles.push({
				key : "border-bottom",
				value : PreviewHelper.getColor(model["FORMS__FORM_HEADING_BORDER_1_COLOR"]) + " solid 1px"
			});
		}

		if (model["FORMS__FORM_HEADING_TITLE_COLOR"]) {
			styles.push({
				key : "color",
				value : PreviewHelper.getColor(model["FORMS__FORM_HEADING_TITLE_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"formHeadingBorderStyle" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		var styles = [];
		styles.push({
			key : "height",
			value : "1px"
		});

		if (model["FORMS__FORM_HEADING_BORDER_2_COLOR"]) {
			styles.push({
				key : "background-color",
				value : PreviewHelper.getColor(model["FORMS__FORM_HEADING_BORDER_2_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"sectionHeaderStyle" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		var styles = [];
		if (model["FORMS__SECTION_HEADER_TINT_COLOR"]) {
			styles.push({
				key : "background",
				value : _.template("linear-gradient(<%=color%> 0%, white 100%)", {
					color : PreviewHelper.getColor(model["FORMS__SECTION_HEADER_TINT_COLOR"])
				})
			});
		}
		if (model["FORMS__FORM_HEADING_TITLE_COLOR"]) {
			styles.push({
				key : "color",
				value : PreviewHelper.getColor(model["FORMS__FORM_HEADING_TITLE_COLOR"])
			});
		}

		if (model["FORMS__SECTION_HEADER_BORDER_COLOR"]) {
			styles.push({
				key : "border-color",
				value : PreviewHelper.getColor(model["FORMS__SECTION_HEADER_BORDER_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"hyperlinkStyle" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		var styles = [];
		if (model["FORMS__HYPER_LINK_COLOR"]) {
			styles.push({
				key : "color",
				value : PreviewHelper.getColor(model["FORMS__HYPER_LINK_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},
};

Template.detail.helpers(_.extend({}, sharedHelpers, {
	rendered : function() {
		this.$(".section-header").each(function() {
			$(this).append($("<div class='spacer'></div>"));
		});
	}
}));
Template.detailActivePart.helpers(sharedHelpers);
Template.detailInactivePart.helpers(sharedHelpers);

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
			alert("Updated!");
		} catch (e) {
			alert(e);
			alert("EPF Syntax is not valid!");
		}
	},

	"click #inject-button" : function(e, t, d) {
		t.$("#epf-field").val(__getCurrentEPF());
	},

	"focus textarea" : function(e, t, d) {
		setTimeout(function() {
			$(e.target).select();
		});
	}
});
