package net.jeeeyul.eclipse.themes.userpreset

import java.io.File
import java.util.List
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import net.jeeeyul.eclipse.themes.ChromeThemeCore
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Platform
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.CDATASection
import org.w3c.dom.Attr

class UserPreset {
	def IPath getPresetLocation(){
		var location = Platform::configurationLocation
		if(location == null){
			return null
		}
		
		var IPath path = new Path(location.URL.toExternalForm)
		return path.append(ChromeThemeCore::^default.bundle.symbolicName)
	}
	
	def List<File> listPresets(){
		presetLocation.toFile.listFiles()
	}
	
	def static void main(String[] args) {
		new UserPreset().run()
	}
	
	def run() { 
		var factory = DocumentBuilderFactory::newInstance
		var builder = factory.newDocumentBuilder
		val doc = builder.newDocument()
		doc.setStrictErrorChecking(false)
				
		doc.newElement("root")[
			newElement("test")[
				newAttribute("name", "지랄한다")
				newAttribute("test", "sucks")
				newCData("아오 씨발 미치겠네")
			]
		] 
		
		var transformer = TransformerFactory::newInstance.newTransformer
		transformer.transform(new DOMSource(doc), new StreamResult(System::out))
	}
	
	def Element newElement(Node parent, String name, (Node)=>void initializer){
		var node = parent.document.createElement(name)
		parent.appendChild(node)
		initializer.apply(node)
		return node
	}
	
	def Attr newAttribute(Node parent, String name, String value){
		var node = parent.document.createAttribute(name)
		node.setValue(value)
		parent.attributes.setNamedItem(node)
		return node
	}
	
	def CDATASection newCData(Node parent, String data){
		var node = parent.document.createCDATASection(data)
		parent.appendChild(node)
		return node
	}
	
	def Document getDocument(Node node){
		var Node finger = node;
		
		while(finger != null){
			if(finger instanceof Document){
				return finger as Document;
			}	
			finger = finger.parentNode
		}
		
		return null
	}
	
	def Node operator_add(Node parent, Node child){
		parent.appendChild(child)
		return parent
	}
	
}