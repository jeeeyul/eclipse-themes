EPFSerializer = (function() {
	function convertFloat(num) {
		if (num % 1 != 0) {
			return num;
		} else {
			return num.toFixed(1);
		}
	}

	function serializeValue(value) {
		if (value == null) {
			return "";
		}
		switch (value.type) {
		case "gradient": {
			return _(value.colorStops).map(function(each) {
				return _.template("<%=hue%>, <%=saturation%>, <%=brightness%>, <%=percent%>", {
					"hue" : convertFloat(each.hue),
					"saturation" : convertFloat(each.saturation),
					"brightness" : convertFloat(each.brightness),
					"percent" : each.percent
				});
			}).join("|");
		}
		case "HSB": {
			return _.template("<%=hue%>, <%=saturation%>, <%=brightness%>", {
				"hue" : convertFloat(value.hue),
				"saturation" : convertFloat(value.saturation),
				"brightness" : convertFloat(value.brightness)
			});
		}
		case "Boolean": {
			return "true"
		}
		case "IntArray": {
			return value.values.join(", ");
		}
		case "Integer": {
			return value.value;
		}

		default:
			throw new Error(value.type + " is not handled!");
		}
	}

	function serialize(entries) {
		var result = [];
		_(entries).forEach(function(it) {
			if(it.comments){
				_(it.comments).forEach(function(comment){
					result.push("# " + comment);
				})
			}
			result.push(_.template("<%=key%>=<%=value%>", {
				"key" : it.key,
				"value" : serializeValue(it.value)
			}));
		});
		return result.join("\n");
	}

	return {
		"serialize" : serialize
	};
})();