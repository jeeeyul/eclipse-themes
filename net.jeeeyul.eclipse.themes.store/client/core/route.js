Router.configure({
	layoutTemplate : "master",
	onAfterAction : function() {
		if (typeof __updateURL == "function") {
			__updateURL(location.origin + Router.current().url);
		}
	}
});

Router.map(function() {
	this.route("about", {
		path : "/about",
		action : function() {
			document.title = "About - Eclipse Theme Store";
			this.render();
		}
	});

	this.route("profile", {
		path : "/profile",
		action : function() {
			document.title = "Profile - Eclipse Theme Store";
			this.render();
		}
	});

	this.route("home", {
		path : "/",
		waitOn : function() {
			return Meteor.subscribe("allEPFs");
		},
		action : function() {
			document.title = "Recent - Eclipse Theme Store";
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
				document.title = _.template("<%=name%> by <%=authorName%> - Eclipse Theme Store", this.data());
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
			document.title = "Top Ratings - Eclipse Theme Store";
			if (this.ready()) {
				this.render();
			} else {
				this.render("loading");
			}
		}
	});

	this.route("post", {
		path : "/post",
		action : function() {
			document.title = "Share - Eclipse Theme Store";
			this.render();
		}
	});

	this.route("author", {
		path : "/author/:id",
		waitOn : function() {
			return Meteor.subscribe("allEPFsByAuthor", this.params.id);
		},
		action : function() {
			if (this.ready()) {
				document.title = _.template("Themes by <%=profile.name%>", this.data());
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

	this.route("donate", {
		path : "/donate",
		action : function() {
			document.title = "Help This Project";
			this.render();
		}
	});
});
