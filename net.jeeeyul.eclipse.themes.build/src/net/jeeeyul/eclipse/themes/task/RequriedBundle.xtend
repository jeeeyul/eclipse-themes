package net.jeeeyul.eclipse.themes.task

import java.util.Map
import java.util.HashMap

class RequriedBundle {
    String id;
    String version;
    Map<String, String> others = new HashMap();
    
    new(String expression) {
        var parse = expression.trim().split(";").toList();
        id = parse.get(0).trim;
        
        if(parse.size > 1){
            for(i : 1..parse.size-1){
                var each = parse.get(i)
                var set = each.split("=");
                var key = set.get(0).trim;
                var value = set.get(1).trim;
                if(key == "bundle-version"){
                    version = value;
                }else{
                    others.put(key.trim(), value.trim());
                }
            }
        }
    }
    
    def setVersion(String version){
        this.version = version;
    }
    
    def getVersion(){
        version;
    }
    
    def getId(){
        id;
    }
    
    def toFileContent()'''«id»«IF version != null»;bundle-version=«version»«ENDIF»«FOR key : others.keySet BEFORE ';' SEPARATOR ';'»«key»=«others.get(key)»«ENDFOR»'''
    
    def String unquote(String string){
        return string.trim.substring(1, string.length-1);
    }
    
    def static void main(String[] args){
        var rb = new RequriedBundle("test;bundle-version=\"1.2\";x=2;y=3")
        println(rb.toFileContent)
    }
}