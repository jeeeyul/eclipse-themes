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
	}
});

Paging.apply(Template.home, "allEPFs");