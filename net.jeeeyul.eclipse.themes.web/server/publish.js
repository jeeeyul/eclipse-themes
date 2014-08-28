Meteor.publish("EPF", function(id) {
	return [ EPFs.find({
		"_id" : id
	}), Comments.find({
		"epfId" : id
	}) ];
});

Meteor.publish("allEPFs", function() {
	return EPFs.find();
});

Meteor.publish("EPFsByAuthor", function(authorId) {
	return [ EPFs.find({
		"authorId" : authorId
	}, {
		sort : {
			"date" : 1
		}
	}), Meteor.users.find({
		"_id" : authorId
	}) ];
});