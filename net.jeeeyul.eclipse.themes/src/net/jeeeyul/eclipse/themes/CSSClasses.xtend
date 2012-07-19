package net.jeeeyul.eclipse.themes

import java.util.ArrayList
import java.util.List

class CSSClasses {
	List<String> classes = new ArrayList<String>();
	
	new(String source){
		if(source != null && !source.trim.empty){
			classes += source.split(" ");	
		}
	}
	
	def boolean add(String className){
		if(classes.contains(className)){
			return false;
		}else{
			classes += className;
		}
	}
	
	def boolean contains(String className){
		return classes.contains(className)
	}
	
	def boolean operator_add(String className){
		add(className)
	}
	
	def boolean remove(String className){
		classes.remove(className);
	}
	
	override String toString() {
		classes.join(" ");
	}
}