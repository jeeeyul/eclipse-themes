{
	var comments = [];
}

entries=
	(WS* '\n' WS* / comment)*
	first:entry
	trail:(((WS* '\n' WS*)/comment)+ each:entry {return each;})*
	(WS* '\n' WS*)*
	{
		return [first].concat(trail);
	}

entry=
	key:id WS* '=' WS* value:value? WS*
	{
		var result = {
			"key" : key,
			"value" : value
		}
		if(comments.length > 0){
			result["comments"] = comments;
			comments = [];
		}
		return result;
	}

comment=
	'#' comment:[^\n]+ {
		comments.push(comment.join("").trim());
		return comment.join("").trim()
	}

value=
	Gradient/HSB/True/IntArray/Integer

id= 
	id:IDPart+
	{
	  return id.join("")
	}

IDPart= 
	[a-zA-Z_0-9]

WS=
	[ \t]

UnsignedFloat=
	pre:([0-9]+) post:('.' ([0-9]+))
	{
		var exp = pre.join("");
		if(post) exp = exp + "." + post[1].join("");
		return parseFloat(exp);
	}
	
INT= 
	digits:([0-9]+)
	{
		return parseInt(digits.join(""));
	}

Integer=
	value:INT
	{
		return {
			type:"Integer",
			"value" : value
		};
	}

HSB=
	h:UnsignedFloat WS* ',' WS* 
	s:UnsignedFloat WS* ',' WS* 
	b:UnsignedFloat
	{
		return {
			"type" : "HSB",
			"hue" : h,
			"saturation" : s,
			"brightness" : b
		}
	}

ColorStop=
	h:UnsignedFloat WS* ',' WS*
	s:UnsignedFloat WS* ',' WS*
	b:UnsignedFloat WS* ',' WS*
	p:INT
	{
		return {
			"hue" : h,
			"saturation" : s,
			"brightness" : b,
			"percent" : p
		}
	}

	
Gradient=
	first:ColorStop WS* trail:('|' WS* each:ColorStop{return each;})+ WS*
	
	{
		return {
			type : "gradient",
			colorStops : [first].concat(trail)
		}
	}

True=
	('true'
	{
		return {
			type:"Boolean",
			value: true
		}
	})
	/
	(
		'false'
		{return null;
	})

IntArray=
	first:INT
	trail:(WS* ',' WS* each:INT {return each;})+
	{
		return {
			type:"IntArray",
			values: [first].concat(trail)
		};
	}