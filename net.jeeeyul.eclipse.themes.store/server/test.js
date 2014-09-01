Meteor.startup(function() {
	var fs = Npm.require("fs");
	var path = Npm.require("path");
	console.log(process.env.PWD);
});