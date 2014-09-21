Template.home.helpers({
	"options" : function() {
		return {
			sort : {
				date : -1
			}
		};
	},

	"isETSClient" : function() {
		alert(typeof __install);
		return (typeof __install) == "function";
	},

	"rendered" : function() {
		googleAnalytics();
	}
});

Paging.apply(Template.home, "allEPFs");