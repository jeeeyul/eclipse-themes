PreviewHelper = (function() {
	function Helper() {
	}
	Helper.prototype.getColor = function(value) {
		if (value == null) {
			return "";
		}
		switch (value.type) {
		case "HSB":
			var hsl = HSBtoHSL(value);
			return _.template("hsl(<%=hue%>, <%=saturation*100%>%, <%=lightness*100%>%)", hsl);
		default:
			return "";
		}
	};

	Helper.prototype.getLastColor = function(value) {
		if (value == null) {
			return "";
		}
		switch (value.type) {
		case "gradient":
			var hsl = HSBtoHSL(_(value.colorStops).last());
			return _.template("hsl(<%=hue%>, <%=saturation*100%>%, <%=lightness*100%>%)", hsl);
		default:
			return "";
		}
	};

	Helper.prototype.getBackground = function(value) {
		if (value == null) {
			return "";
		}
		switch (value.type) {
		case "gradient":
			var exp = value.colorStops.map(function(it) {
				var hsl = HSBtoHSL(it);
				hsl.percent = it.percent;
				return _.template("hsl(<%=hue%>, <%=saturation*100%>%, <%=lightness*100%>%) <%=percent%>%", hsl);
			}).join(", ");
			return "linear-gradient(" + exp + ")";
		default:
			return "";
		}
	}

	return new Helper();
})();

Template.preview.helpers({
	"toolbarStyle" : function() {
		var styles = [];

		styles.push({
			key : "background",
			value : PreviewHelper.getBackground(this["WINDOW__TOOLBAR_FILL_COLOR"])
		});

		styles.push({
			"key" : "border-bottom",
			"value" : PreviewHelper.getColor(this["WINDOW__PERSPECTIVE_SWITCHER_KEY_LINE_COLOR"]) + " solid 1px"
		});

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"perspectiveSwitcherStyle" : function() {
		var styles = [];

		if (this["WINDOW__PERSPECTIVE_SWITCHER_FILL_COLOR"]) {
			styles.push({
				key : "background",
				value : PreviewHelper.getBackground(this["WINDOW__PERSPECTIVE_SWITCHER_FILL_COLOR"])
			});
		}
		if(this["WINDOW__PERSPECTIVE_SWITCHER_KEY_LINE_COLOR"]){
			styles.push({
				key : "border-left",
				value : PreviewHelper.getColor(this["WINDOW__PERSPECTIVE_SWITCHER_KEY_LINE_COLOR"]) + " solid 1px"
			});
		}
		if (this.WINDOW__PERSPECTIVE_SWITCHER_TEXT_COLOR) {
			styles.push({
				"key" : "color",
				"value" : PreviewHelper.getColor(this["WINDOW__PERSPECTIVE_SWITCHER_TEXT_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"statusBarStyle" : function() {
		var styles = [];

		styles.push({
			key : "background",
			value : PreviewHelper.getBackground(this["WINDOW__STATUS_BAR_FILL_COLOR"])
		});
		
		if(this.WINDOW__STATUS_BAR_TEXT_COLOR){
			styles.push({
				key : "color",
				value : PreviewHelper.getColor(this.WINDOW__STATUS_BAR_TEXT_COLOR)
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},
	"windowStyle" : function() {
		var styles = [];

		styles.push({
			key : "background-color",
			value : PreviewHelper.getColor(this["WINDOW__BACKGROUND_COLOR"])
		});

		styles.push({
			key : "padding",
			value : this["WINDOW__MARGINS"].values.map(function(it) {
				return it + "px"
			}).join(" ")
		});
		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"tabFolderStyle" : function() {
		var styles = [];

		if (this["LAYOUT__SHOW_SHADOW"] != null) {
			styles.push({
				key : "box-shadow",
				value : PreviewHelper.getColor(this["LAYOUT__SHADOW_COLOR"]) + " 1px 1px 3px"
			});
		}

		if (this["LAYOUT__BORDER_RADIUS"] != null) {
			styles.push({
				key : "border-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"glueStyle" : function(active) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";

		var styles = [];
		if (this[prefix + "PART_STACK__SELECTED_BORDER_SHOW"] != null) {
			styles.push({
				key : "background",
				value : PreviewHelper.getLastColor(this[prefix + "PART_STACK__SELECTED_FILL_COLOR"])
			});
		}
		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"tabHeaderBorderStyle" : function(active) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";

		var styles = [];
		if (this[prefix + "PART_STACK__BORDER_SHOW"] != null) {
			styles.push({
				key : "background",
				value : PreviewHelper.getBackground(this[prefix + "PART_STACK__BORDER_COLOR"])
			});
			styles.push({
				key : "padding",
				value : "1px 1px 0px 1px"
			});
			if (this["LAYOUT__BORDER_RADIUS"]) {
				styles.push({
					key : "border-top-right-radius",
					value : this["LAYOUT__BORDER_RADIUS"].value + "px"
				});
				styles.push({
					key : "border-top-left-radius",
					value : this["LAYOUT__BORDER_RADIUS"].value + "px"
				});
			}
		}
		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},
	"tabHeaderStyle" : function(active) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";
		var styles = [];
		styles.push({
			key : "background",
			value : PreviewHelper.getBackground(this[prefix + "PART_STACK__HEADER_BACKGROUND_COLOR"])
		});

		if (this["LAYOUT__BORDER_RADIUS"] != null) {
			styles.push({
				key : "border-top-right-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
			styles.push({
				key : "border-top-left-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
		}

		if (this[prefix + "PART_STACK__SELECTED_BORDER_SHOW"] != null) {
			styles.push({
				"key" : "border-bottom",
				"value" : "solid 1px " + PreviewHelper.getLastColor(this[prefix + "PART_STACK__SELECTED_BORDER_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},
	"tabItemBorderStyle" : function(active, selected) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";
		prefix += "PART_STACK__" + selected + "_";
		var styles = [];
		if (this[prefix + "BORDER_SHOW"] != null) {
			styles.push({
				key : "background",
				value : PreviewHelper.getBackground(this[prefix + "BORDER_COLOR"])
			});
			styles.push({
				key : "padding",
				value : "0px 1px 0px 1px"
			});

			if (this["LAYOUT__BORDER_RADIUS"] != null) {
				styles.push({
					key : "border-top-right-radius",
					value : this["LAYOUT__BORDER_RADIUS"].value + "px"
				});
				styles.push({
					key : "border-top-left-radius",
					value : this["LAYOUT__BORDER_RADIUS"].value + "px"
				});
			}
		}
		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},
	"tabItemStyle" : function(active, selected) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";
		prefix += "PART_STACK__" + selected + "_";

		var styles = [];
		if (this[prefix + "FILL"] === undefined || this[prefix + "FILL"] != null) {
			styles.push({
				key : "background",
				value : PreviewHelper.getBackground(this[prefix + "FILL_COLOR"])
			});
		} else {
			styles.push({
				key : "background",
				value : PreviewHelper.getBackground(this[(active ? "ACTIVE_" : "INACTIVE_") + "PART_STACK__HEADER_BACKGROUND_COLOR"])
			});
		}

		if (this["LAYOUT__BORDER_RADIUS"] != null) {
			styles.push({
				key : "border-top-right-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
			styles.push({
				key : "border-top-left-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
		}
		styles.push({
			key : "color",
			value : PreviewHelper.getColor(this[prefix + "TEXT_COLOR"])
		});

		var sx = this[prefix + "TEXT_SHADOW_POSITION"].values[0];
		var sy = this[prefix + "TEXT_SHADOW_POSITION"].values[1];
		if (sx != 0 || sy != 0) {
			styles.push({
				key : "text-shadow",
				value : _.template("<%=color%> <%=x%>px <%=y%>px 0px", {
					"color" : PreviewHelper.getColor(this[prefix + "TEXT_SHADOW_COLOR"]),
					"x" : sx,
					"y" : sy
				})
			});
		}
		if (selected == "SELECTED" && this[prefix + "BORDER_SHOW"] != null) {
			styles.push({
				"key" : "margin-left",
				"value" : "-1px"
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"tabBodyStyle" : function(active) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";
		var styles = [];

		var lastColorStop = _(this[prefix + "PART_STACK__SELECTED_FILL_COLOR"].colorStops).last();
		var bgHSL = HSBtoHSL(lastColorStop);
		styles.push({
			key : "background",
			value : _.template("hsl(<%=hue%>, <%=saturation*100%>%, <%=lightness*100%>%)", bgHSL)
		});

		if (this["LAYOUT__BORDER_RADIUS"] != null) {

			styles.push({
				key : "border-bottom-right-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
			styles.push({
				key : "border-bottom-left-radius",
				value : this["LAYOUT__BORDER_RADIUS"].value + "px"
			});
		}

		if (this["LAYOUT__CONTENT_PADDING"] != null) {
			styles.push({
				key : "padding",
				value : this["LAYOUT__CONTENT_PADDING"].value + "px"
			});
		}

		if (this[prefix + "PART_STACK__BORDER_SHOW"] != null) {
			styles.push({
				key : "border-bottom",
				value : "solid 1px " + PreviewHelper.getLastColor(this[prefix + "PART_STACK__BORDER_COLOR"])
			});
			styles.push({
				key : "border-left",
				value : "solid 1px " + PreviewHelper.getLastColor(this[prefix + "PART_STACK__BORDER_COLOR"])
			});
			styles.push({
				key : "border-right",
				value : "solid 1px " + PreviewHelper.getLastColor(this[prefix + "PART_STACK__BORDER_COLOR"])
			});
		}

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	},

	"contentStyle" : function(active) {
		var prefix = active ? "ACTIVE_" : "INACTIVE_";
		var styles = [];
		styles.push({
			key : "background",
			value : PreviewHelper.getColor(this[prefix + "PART_STACK__BODY_BACKGROUND_COLOR"])
		});

		return styles.map(function(each) {
			return _.template("<%=key%>:<%=value%>", each);
		}).join(";");
	}
});