Template.post.events({
	"click #submit-button" : function(e, t, d) {
		var text = t.$("#epf-field").val();

		try {
			var epf = EPFParser.parse(text);
			var model = {};
			_(epf).forEach(function(it){
				model[it.key] = it.value;
			});
			console.log(model);
			UI.insert(UI.renderWithData(Template.preview, model), document.body);
			var ser = EPFSerializer.serialize(epf);
			console.log(ser);
		} catch (e) {
			alert(e);
		}
	}
});