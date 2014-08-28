Template.themeList.helpers({
	"themes" : function() {
		return EPFs.find({}, this);
	}
});