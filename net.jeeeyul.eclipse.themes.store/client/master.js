
Template.master.helpers({
	"menuClass" : function(routeName){
		if(Router.current().route.name == routeName){
			return "active";
		}else{
			return "";
		}
	},
	
	"isETSClient" : function() {
		return (typeof __install) == "function";
	}
});