Router.configure({
	layoutTemplate : "master"
});

Router.map(function() {
	this.route("home", {
		path : "/",
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
	
	this.route("toplike", {
		path : "/top",
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
	
	this.route("post", {
		path : "/post"
	});

	this.route("author", {
		path : "/author/:id",
		waitOn : function() {
			return Meteor.subscribe("EPFsByAuthor", this.params.id);
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
});