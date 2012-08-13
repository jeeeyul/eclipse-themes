package net.jeeeyul.eclipse.themes.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

public class GradientScale extends Canvas {
	public static void main(String[] args) {
		Display display = Display.getDefault();

		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		GradientScale scale = new GradientScale(shell, SWT.NORMAL);
		GradientItem item1 = new GradientItem(scale, SWT.NORMAL);
		item1.setColor(new HSB(0, 1, 1));

		GradientItem item2 = new GradientItem(scale, SWT.NORMAL);
		item2.setColor(new HSB(130, 1, 1));
		item2.setPercent(0.5f);

		GradientItem item3 = new GradientItem(scale, SWT.NORMAL);
		item3.setColor(new HSB(230, 1, 1));
		item3.setPercent(0.8f);
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private final int marginWidth = 6;
	private final int marginHeight = 2;
	private final int itemAreaHeight = 20;
	private final int itemOverlap = 5;
	private static final int STATE_NONE = 0;
	private static final int STATE_DRAGGING_ITEM = 1;
	private GradientItem selectedItem;
	private GradientItem hotItem;
	ArrayList<GradientItem> items = new ArrayList<GradientItem>();
	private long lastPressTime = -1;
	private final long clickSpeed = 500;
	private KPoint popupPosition;

	private int state = STATE_NONE;
	private final MenuItem addColorStop;
	private final MenuItem removeColorStop;

	private Color hotItemColor;

	public GradientScale(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);

		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onPaint(event);
			}
		});

		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onResize(event);
			}
		});

		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseMove(event);
			}
		});

		addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseDown(event);
			}
		});

		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onMouseUp(event);
			}
		});

		addListener(SWT.FocusIn, new Listener() {
			@Override
			public void handleEvent(Event event) {
				redraw();
			}
		});

		addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event event) {
				redraw();
			}
		});

		addListener(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onKeyDown(event);
			}
		});

		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onDispose(event);
			}
		});

		Menu menu = new Menu(this);
		this.addColorStop = new MenuItem(menu, SWT.PUSH);
		this.addColorStop.setText("Add Color Stop");
		this.addColorStop.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.x = GradientScale.this.popupPosition.x;
				event.y = GradientScale.this.popupPosition.y;
				createNewItem(event);
			}
		});

		this.removeColorStop = new MenuItem(menu, SWT.PUSH);
		this.removeColorStop.setText("Remove Color Stop");
		this.removeColorStop.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				removeSelectedItem();
			}
		});

		setMenu(menu);

		updateMenu();
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(200, 30);
	}

	private void createNewItem(Event event) {
		GradientItem newItem = new GradientItem(this, SWT.NORMAL);

		KRectangle gradientArea = getGradientArea();
		float percent = Math.max(Math.min((event.x - gradientArea.x) / (float) gradientArea.width, 1f), 0f);

		newItem.setPercent(percent);
		redraw();
		setHotItem(newItem);

		List<GradientItem> sortedItems = getSortedItems();
		int index = sortedItems.indexOf(newItem);

		if (index == 0 && sortedItems.size() > index) {
			newItem.setColor(sortedItems.get(index + 1).getColor().getCopy());
		} else if (sortedItems.size() > index + 1) {
			GradientItem prev = sortedItems.get(index - 1);
			GradientItem next = sortedItems.get(index + 1);
			newItem.setColor(prev.getColor().getCopy().mixWith(next.getColor(), .5f));
		} else {
			newItem.setColor(sortedItems.get(index - 1).getColor().getCopy());
		}

		setSelectedItem(newItem);
	}

	protected KRectangle getGradientArea() {
		return new KRectangle(getClientArea()).translate(this.marginWidth, this.marginHeight).expand(-this.marginWidth * 2,
				-this.marginHeight * 2 - this.itemAreaHeight + this.itemOverlap);
	}

	public Color getHotItemColor() {
		if (this.hotItemColor == null) {
			HSB hsb = new HSB(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION).getRGB());
			hsb.mixWith(new HSB(0f, 0f, 1f), 0.5f);
			this.hotItemColor = new Color(getDisplay(), hsb.toRGB());
		}
		return this.hotItemColor;
	}

	protected KRectangle getItemArea() {
		KRectangle gradientArea = getGradientArea();
		KRectangle result = gradientArea.getCopy();

		result.height = this.itemAreaHeight;
		result.y = gradientArea.y + gradientArea.height - this.itemOverlap;
		return result;
	}

	public GradientItem[] getItems() {
		return this.items.toArray(new GradientItem[this.items.size()]);
	}

	public GradientItem getSelectedItem() {
		return this.selectedItem;
	}

	protected List<GradientItem> getSortedItems() {
		ArrayList<GradientItem> result = new ArrayList<GradientItem>();
		result.addAll(this.items);
		Collections.sort(result, new Comparator<GradientItem>() {
			@Override
			public int compare(GradientItem o1, GradientItem o2) {
				if (o1.getPercent() < o2.getPercent()) {
					return -1;
				} else if (o1.getPercent() > o2.getPercent()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		return result;
	}

	protected void notifyModification() {
		Event e = new Event();
		e.widget = this;
		notifyListeners(SWT.Modify, e);
	}

	protected void onDispose(Event event) {
		if (this.hotItemColor != null && !this.hotItemColor.isDisposed()) {
			this.hotItemColor.dispose();
		}
	}

	protected void onKeyDown(Event event) {
		if (event.keyCode == SWT.DEL && this.selectedItem != null && this.items.size() > 1) {
			removeSelectedItem();
		}
	}

	protected void onMouseDown(Event event) {
		if (event.button == 3) {
			this.popupPosition = new KPoint(event);
		}

		boolean doubleClick = System.currentTimeMillis() - this.lastPressTime < this.clickSpeed;
		switch (this.state) {
		case STATE_NONE:
			if (this.hotItem != null) {
				setSelectedItem(this.hotItem);
				if (event.button == 1) {
					this.state = STATE_DRAGGING_ITEM;
				} else {
					this.state = STATE_NONE;
					setHotItem(null);
				}
			}

			if (event.button == 1) {
				if (this.selectedItem != null && this.selectedItem == this.hotItem && doubleClick) {
					showColorPicker();
					this.state = STATE_NONE;
				}

				if (this.hotItem == null && doubleClick) {
					createNewItem(event);
				}
			}

			this.lastPressTime = System.currentTimeMillis();
			break;

		default:
			break;
		}
	}

	protected void onMouseMove(Event event) {
		switch (this.state) {
		case STATE_NONE:
			updateHotItem(event);
			break;

		case STATE_DRAGGING_ITEM:
			KRectangle gradientArea = getGradientArea();
			float newPercent = Math.max(Math.min((event.x - gradientArea.x) / (float) gradientArea.width, 1f), 0f);
			this.selectedItem.setPercent(newPercent);
			redraw();
			break;

		default:
			break;
		}
	}

	protected void onMouseUp(Event event) {
		if (event.button != 1) {
			return;
		}

		switch (this.state) {
		case STATE_DRAGGING_ITEM:
			this.state = STATE_NONE;
			break;

		default:
			break;
		}
	}

	protected void onPaint(Event event) {
		List<GradientItem> sortedItems = getSortedItems();
		if (sortedItems.size() == 0) {
			return;
		}

		KRectangle gradientArea = getGradientArea();

		for (int i = 0; i <= sortedItems.size(); i++) {
			HSB start = i > 0 ? sortedItems.get(i - 1).getColor() : sortedItems.get(i).getColor();
			float startPercent = i > 0 ? sortedItems.get(i - 1).getPercent() : 0f;
			HSB end = i < sortedItems.size() ? sortedItems.get(i).getColor() : sortedItems.get(i - 1).getColor();
			float endPercent = i < sortedItems.size() ? sortedItems.get(i).getPercent() : 1f;

			int x = (int) (gradientArea.width * startPercent + gradientArea.x + .5f);
			int width = (int) (gradientArea.width * endPercent + gradientArea.x + .5f) - x;

			Color foreground = new Color(getDisplay(), start.toRGB());
			Color background = new Color(getDisplay(), end.toRGB());

			event.gc.setForeground(foreground);
			event.gc.setBackground(background);
			event.gc.fillGradientRectangle(x, gradientArea.y, width, gradientArea.height, false);

			foreground.dispose();
			background.dispose();
		}

		event.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER));
		event.gc.drawRectangle(gradientArea.toRectangle());

		for (GradientItem each : sortedItems) {
			int detail = 0;

			if (this.selectedItem == each) {
				detail |= SWT.SELECTED;
			}

			if (this.hotItem == each) {
				detail |= SWT.HOT;
			}
			each.draw(event.gc, detail);
		}

		if (isFocusControl()) {
			if (this.selectedItem != null) {
				Rectangle bounds = this.selectedItem.getBounds().toRectangle();
				event.gc.drawFocus(bounds.x, bounds.y, bounds.width, bounds.height);
			} else {
				Rectangle bounds = getClientArea();
				event.gc.drawFocus(bounds.x, bounds.y, bounds.width, bounds.height);
			}
		}
	}

	protected void onResize(Event event) {
		updateItemBounds();
	}

	private void removeSelectedItem() {
		this.selectedItem.dispose();
		if (this.hotItem == this.selectedItem) {
			setHotItem(null);
		}
		setSelectedItem(null);
	}

	private void setHotItem(GradientItem each) {
		if (this.hotItem == each) {
			return;
		}
		this.hotItem = each;
		redraw();
	}

	public void setSelectedItem(GradientItem selectedItem) {
		if (this.selectedItem == selectedItem) {
			return;
		}
		this.selectedItem = selectedItem;

		updateMenu();
		redraw();
	}

	private void updateMenu() {
		this.removeColorStop.setEnabled(this.items.size() > 1 && this.selectedItem != null);
	}

	private void showColorPicker() {
		ColorPicker colorPicker = new ColorPicker(getShell());
		colorPicker.setContinuosSelectionHandler(new Procedure1<HSB>() {
			@Override
			public void apply(HSB arg0) {
				GradientScale.this.selectedItem.setColor(arg0);
			}
		});
		HSB oldColor = this.selectedItem.getColor();
		colorPicker.setSelection(oldColor);
		if (colorPicker.open() == IDialogConstants.OK_ID) {
			this.selectedItem.setColor(colorPicker.getSelection());
		} else {
			this.selectedItem.setColor(oldColor);
		}
	}

	private void updateHotItem(Event event) {
		for (GradientItem each : getSortedItems()) {
			if (each.getBounds().contains(event.x, event.y)) {
				setHotItem(each);
				return;
			}
		}
		setHotItem(null);
	}

	private void updateItemBounds() {
		for (GradientItem each : getItems()) {
			each.invalidate();
		}
	}
}
