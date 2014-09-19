package net.jeeeyul.eclipse.themes.internal;

import java.util.Iterator;

import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.EvictingQueue;

public class FPSCounter {
	EvictingQueue<Long> queue = EvictingQueue.create(1000);
	private Label fpsLabel;
	private Shell shell;
	private Thread countThread;

	private Shell getShell() {
		if (shell == null || shell.isDisposed()) {
			create();
		}
		return shell;
	}

	public void addCount() {
		queue.add(System.currentTimeMillis());
	}

	public long getFPS() {
		Iterator<Long> iter = queue.iterator();
		long offset = System.currentTimeMillis() - 1000;
		long count = 0;
		while (iter.hasNext()) {
			if (iter.next() > offset) {
				count++;
			}
		}
		return count;
	}

	private void create() {
		if (shell != null && !shell.isDisposed()) {
			return;
		}

		Display display = Display.getDefault();
		shell = new Shell(display, SWT.TOP | SWT.TOOL);
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.doit = false;
			}
		});
		shell.setLayout(new GridLayout());

		fpsLabel = new Label(shell, SWT.RIGHT);
		Font font = new Font(display, "sans-serif", 50, SWT.NORMAL);
		SWTExtensions.INSTANCE.chainDispose(fpsLabel, font);
		fpsLabel.setFont(font);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.widthHint = 150;
		fpsLabel.setLayoutData(layoutData);

		shell.pack();

		createAndStartCountThread();
	}

	private void createAndStartCountThread() {
		if (countThread != null) {
			return;
		}

		countThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!shell.isDisposed()) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (fpsLabel != null && !fpsLabel.isDisposed())
								fpsLabel.setText(Long.toString(getFPS(), 10));
						}
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		countThread.start();
	}

	public void open() {
		create();
		shell.open();
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		final FPSCounter counter = new FPSCounter();
		Shell shell = counter.getShell();
		counter.open();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					counter.addCount();
					try {
						Thread.sleep((long) (100));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
