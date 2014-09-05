Handlebars.registerHelper('session', function(key) {
	return Session.get(key);
});