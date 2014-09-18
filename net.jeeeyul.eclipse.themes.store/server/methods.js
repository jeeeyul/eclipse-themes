Meteor.methods({
	"updateProfile" : function(profile) {
		check(profile, Object);

		if (!this.userId) {
			throw new Meteor.Error(403, "Not logged in");
		}

		if (profile.name != null && profile.name != undefined) {
			check(profile.name, String);
			if (profile.name.length == 0) {
				throw new Meteor.Error(403, "Name can't be empty");
			}
		}

		var updates = {};
		var k;
		for (k in profile) {
			updates["profile." + k] = profile[k];
		}

		Meteor.users.update(this.userId, {
			$set : updates
		});

		if (profile.name) {
			EPFs.update({
				"authorId" : this.userId
			}, {
				$set : {
					"authorName" : profile.name
				}
			}, {
				multi : true
			});
		}
	}
});