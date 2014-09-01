var pageSize = 12;

Meteor.publish("EPF", function(id) {
	return [ EPFs.find({
		"_id" : id
	}), Comments.find({
		"epfId" : id
	}) ];
});

Meteor.publish("allEPFs", function(page) {
	if (page == undefined) {
		page = 0;
	}
	return EPFs.find({}, {
		sort : {
			date : -1
		},
		skip : page * pageSize,
		limit : pageSize
	});
});

Meteor.publish("allEPFsByKeyword", function(keyword, page) {
	if (page == undefined) {
		page = 0;
	}

	return EPFs.find({
		name : {
			$regex : ".*" + keyword + ".*",
			$options : '-i'
		}
	}, {
		sort : {
			date : -1
		},
		skip : page * pageSize,
		limit : pageSize
	});
});

Meteor.publish("allEPFsByRating", function(page) {
	if (page == undefined) {
		page = 0;
	}
	return EPFs.find({}, {
		sort : {
			likeCount : -1,
			date : -1
		},
		skip : page * pageSize,
		limit : pageSize
	});
});

Meteor.publish("allEPFsByAuthor", function(authorId, page) {
	if (page == undefined) {
		page = 0;
	}

	return [ EPFs.find({
		"authorId" : authorId
	}, {
		sort : {
			date : -1
		},
		skip : page * pageSize,
		limit : pageSize
	}), Meteor.users.find({
		_id : authorId
	}) ];
});