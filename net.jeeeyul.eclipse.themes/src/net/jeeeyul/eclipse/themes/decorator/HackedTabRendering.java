package net.jeeeyul.eclipse.themes.decorator;

import java.lang.reflect.Field;

import javax.inject.Inject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;

public class HackedTabRendering extends CTabFolderRenderer {
	protected static Field CTabItem_closeRect;
	protected static Field CTabItem_shortenText;
	protected static Field CTabItem_shortenTextWidth;
	protected static Field CTabItem_closeImageState;

	static {
		try {
			CTabItem_closeRect = CTabItem.class.getDeclaredField("closeRect");
			CTabItem_closeRect.setAccessible(true);

			CTabItem_shortenText = CTabItem.class.getDeclaredField("shortenedText");
			CTabItem_shortenText.setAccessible(true);

			CTabItem_shortenTextWidth = CTabItem.class.getDeclaredField("shortenedTextWidth");
			CTabItem_shortenTextWidth.setAccessible(true);

			CTabItem_closeImageState = CTabItem.class.getDeclaredField("closeImageState");
			CTabItem_closeImageState.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Constants for circle drawing
	final static int LEFT_TOP = 0;
	final static int LEFT_BOTTOM = 1;
	final static int RIGHT_TOP = 2;
	final static int RIGHT_BOTTOM = 3;

	// drop shadow constants
	final static int SIDE_DROP_WIDTH = 3;
	final static int BOTTOM_DROP_WIDTH = 4;

	// keylines
	final static int OUTER_KEYLINE = 1;
	final static int INNER_KEYLINE = 0;
	final static int TOP_KEYLINE = 0;

	// Item Constants
	static final int ITEM_TOP_MARGIN = 2;
	static final int ITEM_BOTTOM_MARGIN = 6;
	static final int ITEM_LEFT_MARGIN = 4;
	static final int ITEM_RIGHT_MARGIN = 4;
	static final int INTERNAL_SPACING = 4;

	static final String E4_SHADOW_IMAGE = "org.eclipse.e4.renderer.shadow_image"; //$NON-NLS-1$
	static final String E4_TOOLBAR_ACTIVE_IMAGE = "org.eclipse.e4.renderer.toolbar_background_active_image"; //$NON-NLS-1$
	static final String E4_TOOLBAR_INACTIVE_IMAGE = "org.eclipse.e4.renderer.toolbar_background_inactive_image"; //$NON-NLS-1$

	int[] shape;

	Image shadowImage, toolbarActiveImage, toolbarInactiveImage;

	int cornerSize = 14;

	boolean shadowEnabled = true;
	Color shadowColor;
	Color outerKeyline, innerKeyline;
	Color[] activeToolbar;
	int[] activePercents;
	Color[] inactiveToolbar;
	int[] inactivePercents;
	boolean active;
	Color selectedTabFillColor;
	Color tabOutlineColor;

	int paddingLeft = 0, paddingRight = 0, paddingTop = 0, paddingBottom = 0;

	static int blend(int v1, int v2, int ratio) {
		int b = (ratio * v1 + (100 - ratio) * v2) / 100;
		return Math.min(255, b);
	}

	static RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}

	static int[] drawCircle(int xC, int yC, int r, int circlePart) {
		int x = 0, y = r, u = 1, v = 2 * r - 1, e = 0;
		int[] points = new int[1024];
		int[] pointsMirror = new int[1024];
		int loop = 0;
		int loopMirror = 0;
		while (x < y) {
			if (circlePart == RIGHT_BOTTOM) {
				points[loop++] = xC + x;
				points[loop++] = yC + y;
			}
			if (circlePart == RIGHT_TOP) {
				points[loop++] = xC + y;
				points[loop++] = yC - x;
			}
			if (circlePart == LEFT_TOP) {
				points[loop++] = xC - x;
				points[loop++] = yC - y;
			}
			if (circlePart == LEFT_BOTTOM) {
				points[loop++] = xC - y;
				points[loop++] = yC + x;
			}
			x++;
			e += u;
			u += 2;
			if (v < 2 * e) {
				y--;
				e -= v;
				v -= 2;
			}
			if (x > y)
				break;
			if (circlePart == RIGHT_BOTTOM) {
				pointsMirror[loopMirror++] = xC + y;
				pointsMirror[loopMirror++] = yC + x;
			}
			if (circlePart == RIGHT_TOP) {
				pointsMirror[loopMirror++] = xC + x;
				pointsMirror[loopMirror++] = yC - y;
			}
			if (circlePart == LEFT_TOP) {
				pointsMirror[loopMirror++] = xC - y;
				pointsMirror[loopMirror++] = yC - x;
			}
			if (circlePart == LEFT_BOTTOM) {
				pointsMirror[loopMirror++] = xC - x;
				pointsMirror[loopMirror++] = yC + y;
			}
			// grow?
			if ((loop + 1) > points.length) {
				int length = points.length * 2;
				int[] newPointTable = new int[length];
				int[] newPointTableMirror = new int[length];
				System.arraycopy(points, 0, newPointTable, 0, points.length);
				points = newPointTable;
				System.arraycopy(pointsMirror, 0, newPointTableMirror, 0, pointsMirror.length);
				pointsMirror = newPointTableMirror;
			}
		}
		int[] finalArray = new int[loop + loopMirror];
		System.arraycopy(points, 0, finalArray, 0, loop);
		for (int i = loopMirror - 1, j = loop; i > 0; i = i - 2, j = j + 2) {
			int tempY = pointsMirror[i];
			int tempX = pointsMirror[i - 1];
			finalArray[j] = tempX;
			finalArray[j + 1] = tempY;
		}
		return finalArray;
	}

	@Inject
	public HackedTabRendering(CTabFolder parent) {
		super(parent);
	}

	protected boolean showUnselectedTabItemShadow() {
		return true;
	}

	protected void _drawUnselected(int index, GC gc, Rectangle bounds, int state) {
		try {
			CTabItem item = parent.getItem(index);
			int x = bounds.x;
			int y = bounds.y;
			int height = bounds.height;
			int width = bounds.width;

			// Do not draw partial items
			if (!item.isShowing())
				return;

			Rectangle clipping = gc.getClipping();
			if (!clipping.intersects(bounds))
				return;

			if ((state & SWT.FOREGROUND) != 0) {
				// draw Image
				Rectangle trim = computeTrim(index, SWT.NONE, 0, 0, 0, 0);
				int xDraw = x - trim.x;
				Image image = item.getImage();
				if (image != null && !image.isDisposed() && parent.getUnselectedImageVisible()) {
					Rectangle imageBounds = image.getBounds();
					// only draw image if it won't overlap with close button
					int maxImageWidth = x + width - xDraw - (trim.width + trim.x);
					if (parent.getUnselectedCloseVisible() && ((parent.getStyle() & SWT.CLOSE) != 0 || item.getShowClose())) {
						maxImageWidth -= ((Rectangle) CTabItem_closeRect.get(item)).width + 4;
					}
					if (imageBounds.width < maxImageWidth) {
						int imageX = xDraw;
						int imageHeight = imageBounds.height;
						int imageY = y + (height - imageHeight) / 2;
						imageY += parent.getTabPosition() == SWT.BOTTOM ? -1 : 1;
						int imageWidth = imageBounds.width * imageHeight / imageBounds.height;
						gc.drawImage(image, imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height, imageX, imageY, imageWidth, imageHeight);
						xDraw += imageWidth + 4;
					}
				}

				// draw Text
				int textWidth = x + width - xDraw - (trim.width + trim.x);
				if (parent.getUnselectedCloseVisible() && ((parent.getStyle() & SWT.CLOSE) != 0 || item.getShowClose())) {
					textWidth -= ((Rectangle) CTabItem_closeRect.get(item)).width + 4;
				}
				if (textWidth > 0) {
					Font gcFont = gc.getFont();
					gc.setFont(item.getFont() == null ? parent.getFont() : item.getFont());
					if (CTabItem_shortenText.get(item) == null || CTabItem_shortenTextWidth.getInt(item) != textWidth) {
						CTabItem_shortenText.set(item, _shortenText(gc, item.getText(), textWidth));
						CTabItem_shortenTextWidth.setInt(item, textWidth);
					}
					Point extent = gc.textExtent((String) CTabItem_shortenText.get(item), SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);
					int textY = y + (height - extent.y) / 2;
					textY += parent.getTabPosition() == SWT.BOTTOM ? -1 : 1;

					if (showUnselectedTabItemShadow()) {
						gc.setAlpha(180);
						gc.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
						gc.drawText((String) CTabItem_shortenText.get(item), xDraw, textY + 1, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);
					}

					gc.setAlpha(255);
					gc.setForeground(parent.getForeground());
					gc.drawText((String) CTabItem_shortenText.get(item), xDraw, textY, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);
					gc.setFont(gcFont);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String _shortenText(GC gc, String text, int width) {
		return parent.getSimple() ? _shortenText(gc, text, width, "...") : _shortenText(gc, text, width, ""); //$NON-NLS-1$
	}

	String _shortenText(GC gc, String text, int width, String ellipses) {
		if (gc.textExtent(text, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC).x <= width)
			return text;
		int ellipseWidth = gc.textExtent(ellipses, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC).x;
		int length = text.length();
		TextLayout layout = new TextLayout(parent.getDisplay());
		layout.setText(text);
		int end = layout.getPreviousOffset(length, SWT.MOVEMENT_CLUSTER);
		while (end > 0) {
			text = text.substring(0, end);
			int l = gc.textExtent(text, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC).x;
			if (l + ellipseWidth <= width) {
				break;
			}
			end = layout.getPreviousOffset(end, SWT.MOVEMENT_CLUSTER);
		}
		layout.dispose();
		return end == 0 ? text.substring(0, 1) : text + ellipses;
	}

	public ImageData blur(Image src, int radius, int sigma) {
		float[] kernel = create1DKernel(radius, sigma);

		ImageData imgPixels = src.getImageData();
		int width = imgPixels.width;
		int height = imgPixels.height;

		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		int offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				RGB rgb = imgPixels.palette.getRGB(imgPixels.getPixel(x, y));
				if (rgb.red == 255 && rgb.green == 255 && rgb.blue == 255) {
					inPixels[offset] = (rgb.red << 16) | (rgb.green << 8) | rgb.blue;
				} else {
					inPixels[offset] = (imgPixels.getAlpha(x, y) << 24) | (rgb.red << 16) | (rgb.green << 8) | rgb.blue;
				}
				offset++;
			}
		}

		convolve(kernel, inPixels, outPixels, width, height, true);
		convolve(kernel, outPixels, inPixels, height, width, true);

		ImageData dst = new ImageData(imgPixels.width, imgPixels.height, 24, new PaletteData(0xff0000, 0xff00, 0xff));

		dst.setPixels(0, 0, inPixels.length, inPixels, 0);
		offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inPixels[offset] == -1) {
					dst.setAlpha(x, y, 0);
				} else {
					int a = (inPixels[offset] >> 24) & 0xff;
					// if (a < 150) a = 0;
					dst.setAlpha(x, y, a);
				}
				offset++;
			}
		}
		return dst;
	}

	private int clamp(int value) {
		if (value > 255)
			return 255;
		if (value < 0)
			return 0;
		return value;
	}

	protected Point computeSize(int part, int state, GC gc, int wHint, int hHint) {
		wHint += paddingLeft + paddingRight;
		hHint += paddingTop + paddingBottom;
		if (0 <= part && part < parent.getItemCount()) {
			gc.setAdvanced(true);
			Point result = super.computeSize(part, state, gc, wHint, hHint);
			gc.setAdvanced(false);
			return result;
		}
		return super.computeSize(part, state, gc, wHint, hHint);
	}

	protected Rectangle computeTrim(int part, int state, int x, int y, int width, int height) {
		GC gc = new GC(parent);
		gc.dispose();
		int borderTop = TOP_KEYLINE + OUTER_KEYLINE;
		int borderBottom = INNER_KEYLINE + OUTER_KEYLINE;
		int marginWidth = parent.marginWidth;
		int marginHeight = parent.marginHeight;
		int sideDropWidth = shadowEnabled ? SIDE_DROP_WIDTH : 0;
		switch (part) {
		case PART_BODY:
			if (state == SWT.FILL) {
				x = -1 - paddingLeft;
				int tabHeight = parent.getTabHeight() + 1;
				y = y - paddingTop - marginHeight - tabHeight - borderTop - (cornerSize / 4);
				width = 2 + paddingLeft + paddingRight;
				height += paddingTop + paddingBottom;
				height += tabHeight + (cornerSize / 4) + borderBottom + borderTop;
			} else {
				x = x - marginWidth - OUTER_KEYLINE - INNER_KEYLINE - sideDropWidth - (cornerSize / 2);
				width = width + 2 * OUTER_KEYLINE + 2 * INNER_KEYLINE + 2 * marginWidth + 2 * sideDropWidth + cornerSize;
				int tabHeight = parent.getTabHeight() + 1; // TODO: Figure out
															// what
															// to do about the
															// +1
				// TODO: Fix
				if (parent.getMinimized()) {
					y = /* parent.onBottom ? y - borderTop : */y - tabHeight - borderTop - 5;
					height = borderTop + borderBottom + tabHeight;
				} else {
					// y = tabFolder.onBottom ? y - marginHeight -
					// highlight_margin
					// - borderTop: y - marginHeight - highlight_header -
					// tabHeight
					// - borderTop;
					y = y - marginHeight - tabHeight - borderTop - (cornerSize / 4);
					height = height + borderBottom + borderTop + 2 * marginHeight + tabHeight + cornerSize / 2 + cornerSize / 4
							+ (shadowEnabled ? BOTTOM_DROP_WIDTH : 0);
				}
			}
			break;
		case PART_HEADER:
			x = x - (INNER_KEYLINE + OUTER_KEYLINE) - sideDropWidth;
			width = width + 2 * (INNER_KEYLINE + OUTER_KEYLINE + sideDropWidth);
			break;
		case PART_BORDER:
			x = x - INNER_KEYLINE - OUTER_KEYLINE - sideDropWidth - (cornerSize / 4);
			width = width + 2 * (INNER_KEYLINE + OUTER_KEYLINE + sideDropWidth) + cornerSize / 2;
			y = y - borderTop;
			height = height + borderTop + borderBottom;
			break;
		default:
			if (0 <= part && part < parent.getItemCount()) {
				x = x - ITEM_LEFT_MARGIN;// - (CORNER_SIZE/2);
				width = width + ITEM_LEFT_MARGIN + ITEM_RIGHT_MARGIN + 1;
				y = y - ITEM_TOP_MARGIN;
				height = height + ITEM_TOP_MARGIN + ITEM_BOTTOM_MARGIN;
			}
			break;
		}
		return new Rectangle(x, y, width, height);
	}

	private void convolve(float[] kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha) {
		int kernelWidth = kernel.length;
		int kernelMid = kernelWidth / 2;
		for (int y = 0; y < height; y++) {
			int index = y;
			int currentLine = y * width;
			for (int x = 0; x < width; x++) {
				// do point
				float a = 0, r = 0, g = 0, b = 0;
				for (int k = -kernelMid; k <= kernelMid; k++) {
					float val = kernel[k + kernelMid];
					int xcoord = x + k;
					if (xcoord < 0)
						xcoord = 0;
					if (xcoord >= width)
						xcoord = width - 1;
					int pixel = inPixels[currentLine + xcoord];
					// float alp = ((pixel >> 24) & 0xff);
					a += val * ((pixel >> 24) & 0xff);
					r += val * (((pixel >> 16) & 0xff));
					g += val * (((pixel >> 8) & 0xff));
					b += val * (((pixel) & 0xff));
				}
				int ia = alpha ? clamp((int) (a + 0.5)) : 0xff;
				int ir = clamp((int) (r + 0.5));
				int ig = clamp((int) (g + 0.5));
				int ib = clamp((int) (b + 0.5));
				outPixels[index] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
				index += height;
			}
		}

	}

	private float[] create1DKernel(int radius, int sigma) {
		// guideline: 3*sigma should be the radius
		int size = radius * 2 + 1;
		float[] kernel = new float[size];
		int radiusSquare = radius * radius;
		float sigmaSquare = 2 * sigma * sigma;
		float piSigma = 2 * (float) Math.PI * sigma;
		float sqrtSigmaPi2 = (float) Math.sqrt(piSigma);
		int start = size / 2;
		int index = 0;
		float total = 0;
		for (int i = -start; i <= start; i++) {
			float d = i * i;
			if (d > radiusSquare) {
				kernel[index] = 0;
			} else {
				kernel[index] = (float) Math.exp(-(d) / sigmaSquare) / sqrtSigmaPi2;
			}
			total += kernel[index];
			index++;
		}
		for (int i = 0; i < size; i++) {
			kernel[i] /= total;
		}
		return kernel;
	}

	void createShadow(final Display display, boolean recreate) {
		Object obj = display.getData(E4_SHADOW_IMAGE);
		if (obj != null && !recreate) {
			shadowImage = (Image) obj;
		} else {
			ImageData data = new ImageData(60, 60, 32, new PaletteData(0xFF0000, 0xFF00, 0xFF));
			Image tmpImage = shadowImage = new Image(display, data);
			GC gc = new GC(tmpImage);
			if (shadowColor == null)
				shadowColor = gc.getDevice().getSystemColor(SWT.COLOR_GRAY);
			gc.setBackground(shadowColor);
			drawTabBody(gc, new Rectangle(0, 0, 60, 60), SWT.None);
			ImageData blured = blur(tmpImage, 5, 25);
			shadowImage = new Image(display, blured);
			display.setData(E4_SHADOW_IMAGE, shadowImage);
			tmpImage.dispose();
			display.disposeExec(new Runnable() {
				public void run() {
					Object obj = display.getData(E4_SHADOW_IMAGE);
					if (obj != null) {
						Image tmp = (Image) obj;
						tmp.dispose();
						display.setData(E4_SHADOW_IMAGE, null);
					}
				}
			});
		}
	}

	protected void dispose() {
		super.dispose();
	}

	protected void draw(int part, int state, Rectangle bounds, GC gc) {
		switch (part) {
		case PART_BODY:
			this.drawTabBody(gc, bounds, state);
			return;
		case PART_HEADER:
			this.drawTabHeader(gc, bounds, state);
			return;
		default:
			if (0 <= part && part < parent.getItemCount()) {
				if (bounds.width == 0 || bounds.height == 0)
					return;
				gc.setAdvanced(true);
				if ((state & SWT.SELECTED) != 0) {
					drawSelectedTab(part, gc, bounds, state);
					state &= ~SWT.BACKGROUND;
					super.draw(part, state, bounds, gc);
				} else {
					drawUnselectedTab(part, gc, bounds, state);
					if ((state & SWT.HOT) == 0 && !active) {
						gc.setAlpha(0x7f);
						state &= ~SWT.BACKGROUND;
						_drawUnselected(part, gc, bounds, state);
					} else {
						state &= ~SWT.BACKGROUND;
						_drawUnselected(part, gc, bounds, state);
					}
				}
				gc.setAdvanced(false);
				return;
			}
		}
		super.draw(part, state, bounds, gc);
	}

	void drawSelectedTab(int itemIndex, GC gc, Rectangle bounds, int state) {
		if (parent.getSingle() && parent.getItem(itemIndex).isShowing())
			return;

		int width = bounds.width;
		int[] points = new int[1024];
		int index = 0;
		int radius = cornerSize / 2;
		int circX = bounds.x + radius;
		int circY = bounds.y - 1 + radius;
		int selectionX1, selectionY1, selectionX2, selectionY2;
		if (itemIndex == 0 && bounds.x == -computeTrim(CTabFolderRenderer.PART_HEADER, SWT.NONE, 0, 0, 0, 0).x) {
			circX -= 1;
			points[index++] = circX - radius;
			points[index++] = bounds.y + bounds.height;

			points[index++] = selectionX1 = circX - radius;
			points[index++] = selectionY1 = bounds.y + bounds.height;
		} else {
			if (active) {
				points[index++] = shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE;
				points[index++] = bounds.y + bounds.height;
			}
			points[index++] = selectionX1 = bounds.x;
			points[index++] = selectionY1 = bounds.y + bounds.height;
		}
		int[] ltt = drawCircle(circX, circY, radius, LEFT_TOP);
		int startX = ltt[6];
		for (int i = 0; i < ltt.length / 2; i += 2) {
			int tmp = ltt[i];
			ltt[i] = ltt[ltt.length - i - 2];
			ltt[ltt.length - i - 2] = tmp;
			tmp = ltt[i + 1];
			ltt[i + 1] = ltt[ltt.length - i - 1];
			ltt[ltt.length - i - 1] = tmp;
		}
		System.arraycopy(ltt, 0, points, index, ltt.length);
		index += ltt.length;

		int[] rt = drawCircle(circX + width - (radius * 2), circY, radius, RIGHT_TOP);
		int endX = rt[rt.length - 4];
		for (int i = 0; i < rt.length / 2; i += 2) {
			int tmp = rt[i];
			rt[i] = rt[rt.length - i - 2];
			rt[rt.length - i - 2] = tmp;
			tmp = rt[i + 1];
			rt[i + 1] = rt[rt.length - i - 1];
			rt[rt.length - i - 1] = tmp;
		}
		System.arraycopy(rt, 0, points, index, rt.length);
		index += rt.length;

		points[index++] = selectionX2 = bounds.width + circX - radius;
		points[index++] = selectionY2 = bounds.y + bounds.height;

		if (active) {
			points[index++] = parent.getSize().x - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);
			points[index++] = bounds.y + bounds.height;
		}
		gc.setClipping(0, bounds.y, parent.getSize().x - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE), bounds.y + bounds.height);// bounds.height
																																							// +
																																							// 4);
		if (selectedTabFillColor == null)
			selectedTabFillColor = gc.getDevice().getSystemColor(SWT.COLOR_WHITE);
		gc.setBackground(selectedTabFillColor);
		gc.setForeground(selectedTabFillColor);
		Color gradientTop = null;
		Pattern backgroundPattern = null;
		if (!active) {
			RGB blendColor = gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW).getRGB();
			RGB topGradient = blend(blendColor, parent.getParent().getBackground().getRGB(), 40);
			gradientTop = new Color(gc.getDevice(), topGradient);
			backgroundPattern = new Pattern(gc.getDevice(), 0, 0, 0, bounds.height + 1, gradientTop, gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
			gc.setBackgroundPattern(backgroundPattern);
		}
		int[] tmpPoints = new int[index];
		System.arraycopy(points, 0, tmpPoints, 0, index);
		gc.fillPolygon(tmpPoints);
		gc.drawLine(selectionX1, selectionY1, selectionX2, selectionY2);
		if (tabOutlineColor == null)
			tabOutlineColor = gc.getDevice().getSystemColor(SWT.COLOR_BLACK);
		gc.setForeground(tabOutlineColor);
		Color gradientLineTop = null;
		Pattern foregroundPattern = null;
		if (!active) {
			RGB blendColor = gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW).getRGB();
			RGB topGradient = blend(blendColor, tabOutlineColor.getRGB(), 40);
			gradientLineTop = new Color(gc.getDevice(), topGradient);
			foregroundPattern = new Pattern(gc.getDevice(), 0, 0, 0, bounds.height + 1, gradientLineTop, gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
			gc.setForegroundPattern(foregroundPattern);
		}
		gc.drawPolyline(tmpPoints);
		Rectangle rect = null;
		gc.setClipping(rect);

		if (active) {
			if (outerKeyline == null)
				outerKeyline = gc.getDevice().getSystemColor(SWT.COLOR_RED);
			gc.setForeground(outerKeyline);
			gc.drawPolyline(shape);
		} else {
			gc.drawLine(startX, 0, endX, 0);
			if (gradientTop != null)
				gradientTop.dispose();
			if (backgroundPattern != null)
				backgroundPattern.dispose();
			if (gradientLineTop != null)
				gradientLineTop.dispose();
			if (foregroundPattern != null)
				foregroundPattern.dispose();

		}
	}

	void drawShadow(final Display display, Rectangle bounds, GC gc) {
		if (shadowImage == null) {
			createShadow(display, true);
		}
		int x = bounds.x;
		int y = bounds.y;
		int SIZE = shadowImage.getBounds().width / 3;

		int height = Math.max(bounds.height, SIZE * 2);
		int width = Math.max(bounds.width, SIZE * 2);
		// top left
		gc.drawImage(shadowImage, 0, 0, SIZE, SIZE, 2, 10, SIZE, 20);
		int fillHeight = height - SIZE * 2;
		int fillWidth = width + 5 - SIZE * 2;

		int xFill = 0;
		for (int i = SIZE; i < fillHeight; i += SIZE) {
			xFill = i;
			gc.drawImage(shadowImage, 0, SIZE, SIZE, SIZE, 2, i, SIZE, SIZE);
		}

		// Pad the rest of the shadow
		gc.drawImage(shadowImage, 0, SIZE, SIZE, fillHeight - xFill, 2, xFill + SIZE, SIZE, fillHeight - xFill);

		// bl
		gc.drawImage(shadowImage, 0, 40, 20, 20, 2, y + height - SIZE, 20, 20);

		int yFill = 0;
		for (int i = SIZE; i <= fillWidth; i += SIZE) {
			yFill = i;
			gc.drawImage(shadowImage, SIZE, SIZE * 2, SIZE, SIZE, i, y + height - SIZE, SIZE, SIZE);
		}
		// Pad the rest of the shadow
		gc.drawImage(shadowImage, SIZE, SIZE * 2, fillWidth - yFill, SIZE, yFill + SIZE, y + height - SIZE, fillWidth - yFill, SIZE);

		// br
		gc.drawImage(shadowImage, SIZE * 2, SIZE * 2, SIZE, SIZE, x + width - SIZE - 1, y + height - SIZE, SIZE, SIZE);

		// tr
		gc.drawImage(shadowImage, (SIZE * 2), 0, SIZE, SIZE, x + width - SIZE - 1, 10, SIZE, SIZE);

		xFill = 0;
		for (int i = SIZE; i < fillHeight; i += SIZE) {
			xFill = i;
			gc.drawImage(shadowImage, SIZE * 2, SIZE, SIZE, SIZE, x + width - SIZE - 1, i, SIZE, SIZE);
		}

		// Pad the rest of the shadow
		gc.drawImage(shadowImage, SIZE * 2, SIZE, SIZE, fillHeight - xFill, x + width - SIZE - 1, xFill + SIZE, SIZE, fillHeight - xFill);
	}

	void drawTabBody(GC gc, Rectangle bounds, int state) {
		int[] points = new int[1024];
		int index = 0;
		int radius = cornerSize / 2;
		int marginWidth = parent.marginWidth;
		int marginHeight = parent.marginHeight;
		int delta = INNER_KEYLINE + OUTER_KEYLINE + 2 * (shadowEnabled ? SIDE_DROP_WIDTH : 0) + 2 * marginWidth;
		int width = bounds.width - delta;
		int height = Math.max(parent.getTabHeight() + INNER_KEYLINE + OUTER_KEYLINE + (shadowEnabled ? BOTTOM_DROP_WIDTH : 0), bounds.height - INNER_KEYLINE
				- OUTER_KEYLINE - 2 * marginHeight - (shadowEnabled ? BOTTOM_DROP_WIDTH : 0));

		int circX = bounds.x + delta / 2 + radius;
		int circY = bounds.y + radius;

		// Body
		index = 0;
		int[] ltt = drawCircle(circX, circY, radius, LEFT_TOP);
		System.arraycopy(ltt, 0, points, index, ltt.length);
		index += ltt.length;

		int[] lbb = drawCircle(circX, circY + height - (radius * 2), radius, LEFT_BOTTOM);
		System.arraycopy(lbb, 0, points, index, lbb.length);
		index += lbb.length;

		int[] rb = drawCircle(circX + width - (radius * 2), circY + height - (radius * 2), radius, RIGHT_BOTTOM);
		System.arraycopy(rb, 0, points, index, rb.length);
		index += rb.length;

		int[] rt = drawCircle(circX + width - (radius * 2), circY, radius, RIGHT_TOP);
		System.arraycopy(rt, 0, points, index, rt.length);
		index += rt.length;
		points[index++] = circX;
		points[index++] = circY - radius;

		int[] tempPoints = new int[index];
		System.arraycopy(points, 0, tempPoints, 0, index);
		gc.fillPolygon(tempPoints);

		// Fill in parent background for non-rectangular shape
		Region r = new Region();
		r.add(bounds);
		r.subtract(tempPoints);
		gc.setBackground(parent.getParent().getBackground());
		Display display = parent.getDisplay();
		Region clipping = new Region();
		gc.getClipping(clipping);
		r.intersect(clipping);
		gc.setClipping(r);
		Rectangle mappedBounds = display.map(parent, parent.getParent(), bounds);
		parent.getParent().drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height, mappedBounds.x, mappedBounds.y);

		// Shadow
		if (shadowEnabled)
			drawShadow(display, bounds, gc);

		gc.setClipping(clipping);
		clipping.dispose();
		r.dispose();

		// Remember for use in header drawing
		shape = tempPoints;
	}

	void drawTabHeader(GC gc, Rectangle bounds, int state) {
		// gc.setClipping(bounds.x, bounds.y, bounds.width,
		// parent.getTabHeight() + 1);

		int[] points = new int[1024];
		int index = 0;
		int radius = cornerSize / 2;
		int marginWidth = parent.marginWidth;
		int marginHeight = parent.marginHeight;
		int delta = INNER_KEYLINE + OUTER_KEYLINE + 2 * (shadowEnabled ? SIDE_DROP_WIDTH : 0) + 2 * marginWidth;
		int width = bounds.width - delta;
		int height = bounds.height - INNER_KEYLINE - OUTER_KEYLINE - 2 * marginHeight - (shadowEnabled ? BOTTOM_DROP_WIDTH : 0);
		int circX = bounds.x + delta / 2 + radius;
		int circY = bounds.y + radius;

		// Fill in background
		Region clipping = new Region();
		gc.getClipping(clipping);
		Region region = new Region();
		region.add(shape);
		region.intersect(clipping);
		gc.setClipping(region);

		int header = 3; // TODO: this needs to be added to computeTrim for
						// HEADER
		Rectangle trim = computeTrim(PART_HEADER, state, 0, 0, 0, 0);
		trim.width = bounds.width - trim.width;
		trim.height = (parent.getTabHeight() + 1 + header) - trim.height;
		trim.x = -trim.x;
		trim.y = -trim.y;

		draw(PART_BACKGROUND, SWT.NONE, trim, gc);

		gc.setClipping(clipping);
		clipping.dispose();
		region.dispose();

		int[] ltt = drawCircle(circX + 1, circY + 1, radius, LEFT_TOP);
		System.arraycopy(ltt, 0, points, index, ltt.length);
		index += ltt.length;

		int[] lbb = drawCircle(circX + 1, circY + height - (radius * 2) - 2, radius, LEFT_BOTTOM);
		System.arraycopy(lbb, 0, points, index, lbb.length);
		index += lbb.length;

		int[] rb = drawCircle(circX + width - (radius * 2) - 2, circY + height - (radius * 2) - 2, radius, RIGHT_BOTTOM);
		System.arraycopy(rb, 0, points, index, rb.length);
		index += rb.length;

		int[] rt = drawCircle(circX + width - (radius * 2) - 2, circY + 1, radius, RIGHT_TOP);
		System.arraycopy(rt, 0, points, index, rt.length);
		index += rt.length;
		points[index++] = points[0];
		points[index++] = points[1];

		int[] tempPoints = new int[index];
		System.arraycopy(points, 0, tempPoints, 0, index);

		if (outerKeyline == null)
			outerKeyline = gc.getDevice().getSystemColor(SWT.COLOR_BLACK);
		gc.setForeground(outerKeyline);
		gc.drawPolyline(shape);
	}

	void drawUnselectedTab(int itemIndex, GC gc, Rectangle bounds, int state) {
		if ((state & SWT.HOT) != 0) {
			int width = bounds.width;
			int[] points = new int[1024];
			int[] inactive = new int[8];
			int index = 0, inactive_index = 0;
			int radius = cornerSize / 2;
			int circX = bounds.x + radius;
			int circY = bounds.y - 1 + radius;

			int leftIndex = circX;
			if (itemIndex == 0) {
				if (parent.getSelectionIndex() != 0)
					leftIndex -= 1;
				points[index++] = leftIndex - radius;
				points[index++] = bounds.y + bounds.height;
			} else {
				points[index++] = bounds.x;
				points[index++] = bounds.y + bounds.height;
			}

			if (!active) {
				System.arraycopy(points, 0, inactive, 0, index);
				inactive_index += 2;
			}

			int[] ltt = drawCircle(leftIndex, circY, radius, LEFT_TOP);
			for (int i = 0; i < ltt.length / 2; i += 2) {
				int tmp = ltt[i];
				ltt[i] = ltt[ltt.length - i - 2];
				ltt[ltt.length - i - 2] = tmp;
				tmp = ltt[i + 1];
				ltt[i + 1] = ltt[ltt.length - i - 1];
				ltt[ltt.length - i - 1] = tmp;
			}
			System.arraycopy(ltt, 0, points, index, ltt.length);
			index += ltt.length;

			if (!active) {
				System.arraycopy(ltt, 0, inactive, inactive_index, 2);
				inactive_index += 2;
			}

			int rightIndex = circX - 1;
			int[] rt = drawCircle(rightIndex + width - (radius * 2), circY, radius, RIGHT_TOP);
			for (int i = 0; i < rt.length / 2; i += 2) {
				int tmp = rt[i];
				rt[i] = rt[rt.length - i - 2];
				rt[rt.length - i - 2] = tmp;
				tmp = rt[i + 1];
				rt[i + 1] = rt[rt.length - i - 1];
				rt[rt.length - i - 1] = tmp;
			}
			System.arraycopy(rt, 0, points, index, rt.length);
			index += rt.length;
			if (!active) {
				System.arraycopy(rt, rt.length - 4, inactive, inactive_index, 2);
				inactive[inactive_index] -= 1;
				inactive_index += 2;
			}

			points[index++] = bounds.width + rightIndex - radius;
			points[index++] = bounds.y + bounds.height;

			if (!active) {
				System.arraycopy(points, index - 2, inactive, inactive_index, 2);
				inactive[inactive_index] -= 1;
				inactive_index += 2;
			}

			gc.setClipping(points[0], bounds.y, parent.getSize().x - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE), bounds.y
					+ bounds.height);// bounds.height
			// + 4);
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
			int[] tmpPoints = new int[index];
			System.arraycopy(points, 0, tmpPoints, 0, index);
			gc.fillPolygon(tmpPoints);
			Color tempBorder = new Color(gc.getDevice(), 182, 188, 204);
			gc.setForeground(tempBorder);
			tempBorder.dispose();
			if (active) {
				gc.drawPolyline(tmpPoints);
			} else {
				gc.drawLine(inactive[0], inactive[1], inactive[2], inactive[3]);
				gc.drawLine(inactive[4], inactive[5], inactive[6], inactive[7]);
			}

			Rectangle rect = null;
			gc.setClipping(rect);

			if (outerKeyline == null)
				outerKeyline = gc.getDevice().getSystemColor(SWT.COLOR_BLACK);
			// gc.setForeground(outerKeyline);
			// gc.drawPolyline(shape);
		}
	}

	public Rectangle getPadding() {
		return new Rectangle(paddingTop, paddingRight, paddingBottom, paddingLeft);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActiveToolbarGradient(Color[] color, int[] percents) {
		activeToolbar = color;
		activePercents = percents;
	}

	public void setCornerRadius(int radius) {
		cornerSize = radius;
		parent.redraw();
	}

	public void setInactiveToolbarGradient(Color[] color, int[] percents) {
		inactiveToolbar = color;
		inactivePercents = percents;
	}

	public void setInnerKeyline(Color color) {
		this.innerKeyline = color;
		parent.redraw();
	}

	public void setOuterKeyline(Color color) {
		this.outerKeyline = color;
		// TODO: HACK! Should be set based on pseudo-state.
		setActive(!(color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255));
		parent.redraw();
	}

	public void setPadding(int paddingLeft, int paddingRight, int paddingTop, int paddingBottom) {
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		parent.redraw();
	}

	public void setSelectedTabFill(Color color) {
		this.selectedTabFillColor = color;
		parent.redraw();
	}

	public void setShadowColor(Color color) {
		this.shadowColor = color;
		createShadow(parent.getDisplay(), true);
		parent.redraw();
	}

	public void setShadowVisible(boolean visible) {
		this.shadowEnabled = visible;
		parent.redraw();
	}

	public void setTabOutline(Color color) {
		this.tabOutlineColor = color;
		parent.redraw();
	}
}
