Template.theme.helpers({
	"renderModel" : function() {
		var model = {};
		_(this.epf).forEach(function(it) {
			model[it.key] = it.value;
		});

		model.epf = this.epf;
		return model;
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
	},
	"click .preview" : function(e, t, d) {
		Router.go("detail", {
			"id" : t.data._id
		});
	}
});