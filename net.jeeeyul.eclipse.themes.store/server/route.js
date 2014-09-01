Router.map(function() {
	this.route("downloadEPF", {
		where : "server",
		path : "epf/:id",
		action : function() {
			var epf = EPFs.findOne(this.params.id);
			if (epf) {
				this.response.writeHead(200, {
					"Content-Type" : "application/octet-stream",
					"Content-Disposition" : _.template('attachement; filename="<%=name%>"', {
						name : epf.name + ".epf"
					})
				});
				var content = EPFSerializer.serialize(epf.epf);
				this.response.end(content);
			} else {
				this.response.end(404);
			}
		}
	})
});