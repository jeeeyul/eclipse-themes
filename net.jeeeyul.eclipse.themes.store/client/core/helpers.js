Handlebars.registerHelper('session', function(key) {
	return Session.get(key);
});

Handlebars.registerHelper('currentURL', function(key) {
	return location.href;
});
