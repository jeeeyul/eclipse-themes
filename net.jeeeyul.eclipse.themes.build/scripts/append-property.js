var propertyName = attributes.get("property");
var value = attributes.get("value");
var oldValue = self.getProject().getProperty(propertyName) || "";

var elements = oldValue.split(/\s*,\s*/g);
elements = elements.concat(value).filter(function(it) {
	return it.length > 0;
});

self.getProject().setProperty(propertyName, elements.join(","));