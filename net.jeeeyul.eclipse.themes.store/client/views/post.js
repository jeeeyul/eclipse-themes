Template.post.helpers({
	rendered : function() {
		if (typeof __getCurrentEPF == "function") {
			this.$("#epf-field").val(__getCurrentEPF());
		}
	},

	canInject : function() {
		return typeof __getCurrentEPF == "function";
	}
});

Template.post.events({
	"click #submit-button" : function(e, t, d) {
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

			EPFs.insert({
				"authorId" : Meteor.userId(),
				"authorName" : Meteor.user().profile.name,
				"name" : themeName,
				"description" : description,
				"epf" : epf,
				"date" : new Date()
			});

			Router.go("home");

		} catch (e) {
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