package net.jeeeyul.eclipse.themes.task

import java.io.ByteArrayInputStream
import java.util.ArrayList
import java.util.Collection
import org.eclipse.core.resources.IFile
import org.eclipse.osgi.util.ManifestElement

class Manifest {
    IFile file
    OrderedMap header = new OrderedMap();
    
    new(IFile file) {
        this.file = file;
        ManifestElement::parseBundleManifest(file.contents, header);   
    }
    
    def getBundleId(){
        header.get("Bundle-SymbolicName").split(";").head();
    }
    
    def setBundleVersion(String verString){
        header.put("Bundle-Version", verString);
    }
    
    def void updateDependencies(Collection<String> bundleIdList, String version){
        var dependencies = header.get("Require-Bundle");
        if(dependencies == null){
            return;
        }
        
        var depList = dependencies.split(",").toList;
        val reqBundleList = new ArrayList<RequriedBundle>();
        depList.forEach[
            reqBundleList += new RequriedBundle(it)
        ];
        
        reqBundleList.filter[bundleIdList.contains(it.id)].forEach[
            it.version = version;
        ]
         
        var newValue = reqBundleList.join(",\r\n ")[it.toFileContent()]
        header.put("Require-Bundle", newValue);
    }
    
    def toFileContent()'''
        «FOR key : header.keyList»
        «key»: «header.get(key).lineFormat»
        «ENDFOR»
    '''
    
    def lineFormat(String s)'''«FOR each : s.split(",") SEPARATOR ',\r\n '»«each.replaceAll("(\\r\\n)+", "").trim»«ENDFOR»''' 
        
    def void save(){
        file.setContents(new ByteArrayInputStream(toFileContent.toString.getBytes("UTF-8")), true, true, null);
    }
}