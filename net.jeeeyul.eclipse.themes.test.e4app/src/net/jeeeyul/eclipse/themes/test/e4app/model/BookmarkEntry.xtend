package net.jeeeyul.eclipse.themes.test.e4app.model

import org.eclipse.xtend.lib.annotations.Accessors
import java.util.List
import java.util.ArrayList

class BookmarkEntry {
	@Accessors String label
	@Accessors String url
	@Accessors(PUBLIC_GETTER) List<BookmarkEntry> children;
	
	new (){
		this.children = new ArrayList
	}
}