package net.jeeeyul.eclipse.themes.ui.hotswap;

import java.util.Map;

import net.jeeeyul.eclipse.themes.util.HackedField;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.e4.ui.css.core.resources.AbstractResourcesRegistry;
import org.eclipse.e4.ui.css.core.resources.IResourcesRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class ResourceRegistryHack {

	@SuppressWarnings({ "rawtypes" })
	private HackedField<AbstractResourcesRegistry, Map> allResourceMapField = new HackedField<AbstractResourcesRegistry, Map>(AbstractResourcesRegistry.class,
			"allResourcesMap");

	/**
	 * Disposes and unregisters dynamically generated images from Jeeeyul's
	 * Eclipse Themes
	 * 
	 * @param registry
	 */
	@SuppressWarnings("rawtypes")
	public void disposeDynamicImages(IResourcesRegistry registry) {
		try {
			Map map = allResourceMapField.get(registry);
			if (map == null) {
				return;
			}

			@SuppressWarnings("unchecked")
			Map<Object, Object> imageMap = (Map<Object, Object>) map.get(Image.class);
			if (imageMap != null) {
				for (Object each : imageMap.keySet().toArray(new String[imageMap.size()])) {
					if (each instanceof String) {
						String key = (String) each;
						if (key.startsWith("jeeeyul:")) {
							Object resource = imageMap.get(key);
							if (resource instanceof Image) {
								SWTExtensions.INSTANCE.safeDispose((Image) resource);
								imageMap.remove(key);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
