package net.jeeeyul.eclipse.themes.userpreset

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList
import java.util.List
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import net.jeeeyul.eclipse.themes.ChromeThemeCore
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.jobs.ILock
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.jface.preference.PreferenceStore
import org.w3c.dom.Node

class UserPresetRepository {
	public static val UserPresetRepository INSTANCE = new UserPresetRepository()
	
	extension DomExtensions = new DomExtensions
	
	ILock lock = Job::jobManager.newLock()
	List<UserPreset> userPresets;
	
	def private File getChromeConfigurationFolder(){
		var location = Platform::configurationLocation
		if(location == null){
			return null
		}
		
		var IPath path = new Path(location.URL.toExternalForm).setDevice(null)
		var file = path.append('''«ChromeThemeCore::^default.bundle.symbolicName»''').toFile
		
		if(!file.exists){
			try{
				file.mkdirs()
			}catch(Exception e){
				e.printStackTrace()
			}
		}
		
		return file
	}
	
	def private File getUserPresetFile(){
		new File(chromeConfigurationFolder, "user-presets.xml")
	}
	
	/**
	 * Modifying result will change presets.
	 */
	def getUserPresets(){
		return getUserPresets(false)
	}
	
	def getUserPresets(boolean forceReload){
		lock.acquire()
		
		if(userPresets == null || forceReload){
			load();
		}
		
		lock.release()
		return userPresets
	}
	
	def private load() {
		lock.acquire()
		
		userPresets = new ArrayList<UserPreset>
		
		if(userPresetFile.exists){
			var parser = SAXParserFactory::newInstance.newSAXParser
			var loader = new UserPresetLoader()
			
			try{
				parser.parse(userPresetFile, loader)
				userPresets.addAll(loader.result)
			}catch(Exception e){
				e.printStackTrace()
			}
		}
		
		lock.release()
	}
	
	def save(){
		lock.acquire()
		
		var fos = new FileOutputStream(userPresetFile)
		
		
		var doc = newDocument[]
		var Node root = doc.newElement("presets")[]
		
		for(each  : getUserPresets()){
			root.newElement("preset")[
				newAttribute("name", each.name)
				newCData(each.contents)
			]
		}
		
		var transformer = TransformerFactory::newInstance.newTransformer
		transformer.transform(new DOMSource(doc), new StreamResult(fos))
		
		fos.close
		
		println("User preset file was saved at " + userPresetFile)
		lock.release()
	}

	def void addPreset(String name, PreferenceStore store){
		val baos = new ByteArrayOutputStream()
		store.save(baos, null)
		
		getUserPresets() +=  new UserPreset =>[
			it.name = name
			it.contents = baos.toString("8859_1") 
		]
	}
}