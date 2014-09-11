package net.jeeeyul.eclipse.themes.ui.hotswap;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.internal.Debug;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.resources.IResourcesRegistry;
import org.eclipse.e4.ui.internal.workbench.swt.CSSRenderingUtils;
import org.eclipse.e4.ui.widgets.ImageBasedFrame;
import org.eclipse.e4.ui.workbench.renderers.swt.TrimmedPartLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class HotSwap {
	/**
	 * 
	 */
	public static final HotSwap INSTANCE = new HotSwap();

	private HashMap<ImageBasedFrame, HashSet<Resource>> trackedFrames = new HashMap<ImageBasedFrame, HashSet<Resource>>();

	private HotSwap() {
	}

	private void bind(ImageBasedFrame owner, Resource... r) {
		HashSet<Resource> hashSet = trackedFrames.get(owner);
		if (hashSet == null) {
			hashSet = new HashSet<Resource>();
			trackedFrames.put(owner, hashSet);
		}

		hashSet.addAll(Arrays.asList(r));
	}

	private void disposeBindedResources(ImageBasedFrame f) {
		HashSet<Resource> hashSet = trackedFrames.get(f);
		if (hashSet != null) {
			Debug.println(MessageFormat.format("Image Frame {0} disposed {1} resources", f.hashCode(), hashSet.size()));
			SWTExtensions.INSTANCE.safeDispose(hashSet);
			hashSet.clear();
		}
	}

	/**
	 * @param cssEngine
	 */
	public void fixDragHandles(CSSEngine cssEngine) {
		for (Shell each : Display.getDefault().getShells()) {
			Iterator<? extends Widget> iter = SWTExtensions.INSTANCE.getAllContent(each);
			while (iter.hasNext()) {
				Widget w = iter.next();
				if (w instanceof ImageBasedFrame) {
					fixHandle(cssEngine, w);
				}
			}
		}
	}

	private Image rotateImage(Display display, Image image, Integer[] frameInts) {
		// Swap the widths / heights of the 'cuts'
		if (frameInts != null) {
			int tmp;
			tmp = frameInts[0];
			frameInts[0] = frameInts[2];
			frameInts[2] = tmp;
			tmp = frameInts[1];
			frameInts[1] = frameInts[3];
			frameInts[3] = tmp;
		}

		// rotate 90 degrees,,,
		Rectangle bounds = image.getBounds();
		ImageData imageData = new ImageData(bounds.height, bounds.width, 32, new PaletteData(0xFF0000, 0x00FF00, 0x0000FF));
		Image rotatedImage = new Image(display, imageData);
		GC gc = new GC(rotatedImage);
		RGB rgb = new RGB(0x7d, 0, 0);
		Color offRed = new Color(display, rgb);
		gc.setBackground(offRed);
		gc.fillRectangle(0, 0, bounds.height, bounds.width);
		Transform t = new Transform(display);
		int w = image.getBounds().height;
		int offset = 0; // (w+1) % 2;
		t.translate(w - offset, 0);
		t.rotate(90);
		gc.setTransform(t);
		gc.drawImage(image, 0, 0);
		gc.dispose();
		t.dispose();
		offRed.dispose();
		ImageData alphaData = rotatedImage.getImageData();
		rotatedImage.dispose();
		int transparentPix = alphaData.palette.getPixel(rgb);
		for (int i = 0; i < alphaData.width; i++) {
			for (int j = 0; j < alphaData.height; j++) {
				if (alphaData.getPixel(i, j) != transparentPix) {
					alphaData.setAlpha(i, j, 255);
				}
			}
		}
		rotatedImage = new Image(display, alphaData);

		// Return the new one
		return rotatedImage;
	}

	private void fixHandle(CSSEngine cssEngine, Widget w) {
		final ImageBasedFrame f = (ImageBasedFrame) w;

		if (!trackedFrames.containsKey(f)) {
			f.addListener(SWT.Dispose, new Listener() {
				@Override
				public void handleEvent(Event event) {
					disposeBindedResources(f);
					trackedFrames.remove(f);
				}
			});
		}

		Element element = cssEngine.getElement(f);
		CSSStyleDeclaration style = cssEngine.getViewCSS().getComputedStyle(element, "");
		if (style != null) {
			try {
				Control firstChild = f.getChildren()[0];
				if (!(firstChild instanceof ToolBar)) {
					return;
				}
				ToolBar toolBar = (ToolBar) firstChild;
				boolean vertical = (toolBar.getStyle() & SWT.VERTICAL) != 0;

				CSSRenderingUtils util = new CSSRenderingUtils();
				Integer[] frameInts = new Integer[4];
				Image frameImage = util.createImage(f, null, "frame-image", frameInts);
				if (frameImage != null && vertical) {
					frameImage = rotateImage(f.getDisplay(), frameImage, frameInts);
					bind(f, frameImage);
				}

				Image handleImage = util.createImage(f, null, "handle-image", null);
				if (handleImage != null && vertical) {
					handleImage = rotateImage(f.getDisplay(), handleImage, null);
					bind(f, handleImage);
				}
				if (frameImage != null) {
					f.setImages(frameImage, frameInts, handleImage);
				} else if (handleImage != null) {
					f.setImages(null, null, handleImage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Rectangle windowMargins() {
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		Rectangle margins = store.getRectangle(JTPConstants.Window.MARGINS);
		if (store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)) {
			margins.x = Math.max(margins.x - 1, 0);
			margins.width = Math.max(margins.width - 4, 0);
			margins.height = Math.max(margins.height - 4, 0);
		} else {
			margins.width = Math.max(margins.width - 1, 0);
			margins.height = Math.max(margins.height - 1, 0);
		}
		return margins;
	}

	/**
	 * 
	 */
	public void fixWindowMargins() {
		for (IWorkbenchWindow each : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			TrimmedPartLayout layout = (TrimmedPartLayout) each.getShell().getLayout();
			Rectangle margins = windowMargins();
			layout.gutterTop = margins.y;
			layout.gutterBottom = margins.height;
			layout.gutterLeft = margins.x;
			layout.gutterRight = margins.width;
		}
	}

	/**
	 * @param cssEngine
	 */
	public void releaseHandleImages(CSSEngine cssEngine) {
		IResourcesRegistry resourcesRegistry = cssEngine.getResourcesRegistry();
		new ResourceRegistryHack().disposeDynamicImages(resourcesRegistry);
	}

}
