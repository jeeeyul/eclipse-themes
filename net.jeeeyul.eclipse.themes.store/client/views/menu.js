Template.menu.helpers({
	"menuClass" : function(routeName){
		if(Router.current().route.name == routeName){
			return "active";
		}else{
			return "";
		}
	}
});

Template.menu.events({
	"click .menu-item" : function(e, t, d){
		if(t.$("button.navbar-toggle").hasClass("collapsed")){
			return;
		}
		t.$("button.navbar-toggle").trigger("click");
	}
});