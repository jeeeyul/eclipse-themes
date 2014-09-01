Meteor.startup(function() {
	WebApp.connectHandlers.use(function(req, res, next) {
		res.setHeader('X-UA-Compatible', 'IE=Edge,chrome=1');
		return next();
	})
});