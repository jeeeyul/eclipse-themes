Template.post.helpers({
	rendered : function(){
		if(typeof __getCurrentEPF == "function") {
			this.$("#epf-field").val(__getCurrentEPF());
		}
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

			EPFs.insert({
				"authorId" : Meteor.userId(),
				"authorName" : Meteor.user().profile.name,
				"name" : t.$("#name-field").val(),
				"description" : t.$("#description-field").val(),
				"epf" : epf,
				"date" : new Date()
			});

			Router.go("home");

		} catch (e) {
			alert(e);
		}
	}
});