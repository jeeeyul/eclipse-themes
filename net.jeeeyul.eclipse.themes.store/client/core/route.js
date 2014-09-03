Router.configure({
	layoutTemplate : "master",
	onAfterAction : function(){
		if(typeof __updateURL == "function"){
			__updateURL(location.origin + Router.current().path);
		}
	}
});

Router.map(function() {
	this.route("home", {
		path : "/",
		waitOn : function() {
			return Meteor.subscribe("allEPFs");
		},
		action : function() {
			if (this.ready()) {
				this.render();
			} else {
				this.render("loading");
			}
		}
	});

	this.route("detail", {
		path : "/detail/:id",
		waitOn : function() {
			return Meteor.subscribe("EPF", this.params.id);
		},
		action : function() {
			if (this.ready()) {
				this.render();
			} else {
				this.render("loading");
			}
		},
		data : function() {
			return EPFs.findOne(this.params.id);
		}
	});

	this.route("toplike", {
		path : "/top",
		waitOn : function() {
			return Meteor.subscribe("allEPFsByRating");
		},
		action : function() {
			if (this.ready()) {
				this.render();
			} else {
				this.render("loading");
			}
		}
	});

	this.route("post", {
		path : "/post"
	});

	this.route("author", {
		path : "/author/:id",
		waitOn : function() {
			return Meteor.subscribe("allEPFsByAuthor", this.params.id);
		},
		action : function() {
			if (this.ready()) {
				this.render();
			} else {
				this.render("loading");
			}
		},
		data : function() {
			return Meteor.users.findOne(this.params.id);
		}
	});

	this.route("search", {
		path : "/search/:keyword?",
		data : function() {
			return EPFs.findOne(this.params.id);
		}
	});
});