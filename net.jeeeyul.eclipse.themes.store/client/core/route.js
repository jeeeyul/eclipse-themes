Router.configure({
	layoutTemplate : "master",
	onAfterAction : function() {
		if (typeof __updateURL == "function") {
			__updateURL(location.origin + Router.current().path);
		}
	}
});

Router.map(function() {
	this.route("about", {
		path : "/about",
		onAfterAction : function(){
			document.title = "About - Eclipse Theme Store";
		}
	});
	
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
		},
		onAfterAction : function(){
			document.title = "Recent - Eclipse Theme Store";
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
		},
		onAfterAction : function(){
			document.title = _.template("<%=name%> by <%=authorName%> - Eclipse Theme Store", this.data());
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
		},
		onAfterAction : function(){
			document.title = "Top Ratings - Eclipse Theme Store";
		}
	});

	this.route("post", {
		path : "/post",
		onAfterAction : function(){
			document.title = "Share - Eclipse Theme Store";
		}
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
		},
		onAfterAction : function(){
			document.title = _.template("Themes by <%=profile.name%>", this.data());
		}
	});

	this.route("search", {
		path : "/search/:keyword?",
		data : function() {
			return EPFs.findOne(this.params.id);
		}
	});
});