// Update plugin versions and it's dependency plugin version that is part of
// feature to the version of feature.
Array.prototype.find = function(evaluator) {
	var i, each;
	for (i in this) {
		each = this[i];
		if (evaluator(each)) {
			return each;
		}
	}
	return undefined;
};

function Bundle(expression) {
	this.elements = expression.split(/\s*;\s*/g);
	return this;
};

Bundle.prototype.setVersion = function(version) {
	var versionElement = this.elements.find(function(it) {
		return it.match(/^bundle-version\s*=\s*"[^"]*"$/)
	});

	if (versionElement) {
		this.elements.splice(this.elements.indexOf(versionElement), 1);
	}
	this.elements.push('bundle-version="' + version + '"');
};

Bundle.prototype.getID = function() {
	return this.elements[0];
};

Bundle.prototype.serialize = function() {
	return this.elements.join(";");
};

// Imports
var Manifest = java.util.jar.Manifest;
var FileInputStream = java.io.FileInputStream;
var FileOutputStream = java.io.FileOutputStream;

// Input
var version = attributes.get("version");
var pluginPath = attributes.get("plugin");
/** @type Array */
var dependencies = attributes.get("dependencies").split(/\s*,\s*/g);

var manifestFile = pluginPath + "/META-INF/MANIFEST.MF";

var mf = new Manifest(new FileInputStream(manifestFile));
var main = mf.getMainAttributes();

var requiredBundles = (function(expression) {
	var result = [];
	var expList = expression.split(/\s*,\s*/g);
	expList.forEach(function(it) {
		result.push(new Bundle(it));
	});
	return result;
})(main.getValue("Require-Bundle"));

main.putValue("Bundle-Version", version + ".qualifier");
requiredBundles.forEach(function(it) {
	/** @type Bundle */
	var each = it;

	if (dependencies.indexOf(each.getID()) >= 0) {
		each.setVersion(version);
	}
});

main.putValue("Require-Bundle", requiredBundles.map(function(it) {
	return it.serialize();
}).join(","));

mf.write(new FileOutputStream(manifestFile));
