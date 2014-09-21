Accounts.onCreateUser(function(options, user) {
	if (user.services) {
		var service = _.keys(user.services)[0];
		var email = user.services[service].email;

		if (service == 'github') {
			if (!user.profile)
				user.profile = {};
			if (!user.profile.name)
				user.profile.name = user.services[service].username;
		}

		if (!email)
			return user;

		// see if any existing user has this email address, otherwise create new
		var existingUser = Meteor.users.findOne({
			'emails.address' : email
		});
		if (!existingUser)
			return user;

		// precaution, these will exist from accounts-password if used
		if (!existingUser.services)
			existingUser.services = {
				resume : {
					loginTokens : []
				}
			};
		if (!existingUser.services.resume)
			existingUser.services.resume = {
				loginTokens : []
			};

		// copy accross new service info
		existingUser.services[service] = user.services[service];
		existingUser.services.resume.loginTokens.push(user.services.resume.loginTokens[0]);

		// even worse hackery
		Meteor.users.remove({
			_id : existingUser._id
		}); // remove existing record
		return existingUser; // record is re-inserted
	}
});