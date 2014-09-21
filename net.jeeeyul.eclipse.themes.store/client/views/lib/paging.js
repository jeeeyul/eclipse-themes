var pageSize = 12;

Paging = {
	apply : function(template, topic, topicArgs) {
		var rendered = function() {
			Session.set("page", 0);
			var me = this;
			this.scrollHook = function(e) {
				if (me.reachEnd || me.fetching) {
					return;
				}

				if ($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
					$(me.firstNode).trigger("load-more");
				}
			}
			$(window).on("scroll", this.scrollHook);
			this.$(".loading").hide();
		}

		if (typeof template.rendered == "function") {
			var old = template.rendered;
			template.rendered = function() {
				var args = Array.prototype.splice.call(arguments, 0);
				old.apply(this, args);
				rendered.call(this);
			};

		} else {
			template.rendered = rendered;
		}

		var destroyed = function() {
			$(window).off("scroll", this.scrollHook);
			_(this.subscriptions).forEach(function(each) {
				each.stop();
			});
		};

		if (typeof template.destroyed == "function") {
			var old = template.destroyed;
			template.rendered = function() {
				var args = Array.prototype.splice.call(arguments, 0);
				old.apply(this, args);
				destroyed.call(this);
			};

		} else {
			template.destroyed = destroyed;
		}

		template.events({
			"load-more" : function(e, t) {
				t.subscriptions = t.subscriptions || [];
				t.fetching = true;

				var preCount = EPFs.find().count();
				t.$(".loading").show();

				if (topicArgs === undefined || topicArgs === null) {
					topicArgs = [];
				}

				if (typeof topicArgs == "function") {
					topicArgs = topicArgs.call(t.data);
				}

				var handler = function() {
					var newCount = EPFs.find().count();
					if (preCount != newCount) {
						Session.set("page", Session.get("page") + 1);
					}

					if (newCount - preCount < pageSize) {
						t.reachEnd = true;
					}
					t.$(".loading").hide();
					t.fetching = false;
				}

				var subArgs = [ topic ];
				subArgs = subArgs.concat(topicArgs);
				subArgs.push(Session.get("page") + 1);
				subArgs.push(handler);

				var newSubscription = Meteor.subscribe.apply(Meteor, subArgs);

				t.subscriptions.push(newSubscription);
			}
		});
	}
};