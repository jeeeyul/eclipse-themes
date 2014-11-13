package net.jeeeyul.eclipse.themes.ui.linux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import net.jeeeyul.eclipse.themes.ui.linux.AskingRestart;
import net.jeeeyul.eclipse.themes.ui.linux.DownloadGTKRC2Page;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.xtend.lib.Property;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class DownloadGTKRC2Wizard extends Wizard {
  @Property
  private boolean _dontDownload;
  
  public DownloadGTKRC2Wizard() {
    this.setNeedsProgressMonitor(true);
    this.setWindowTitle("Linux GTK2 RC Wizard");
  }
  
  public void addPages() {
    DownloadGTKRC2Page _downloadGTKRC2Page = new DownloadGTKRC2Page();
    this.addPage(_downloadGTKRC2Page);
  }
  
  public boolean performFinish() {
    try {
      boolean _xblockexpression = false;
      {
        boolean _isDontDownload = this.isDontDownload();
        if (_isDontDownload) {
          return true;
        }
        IWizardContainer _container = this.getContainer();
        final IRunnableWithProgress _function = new IRunnableWithProgress() {
          public void run(final IProgressMonitor it) throws InvocationTargetException, InterruptedException {
            try {
              it.beginTask("Downloading", IProgressMonitor.UNKNOWN);
              String _property = System.getProperty("user.home");
              File file = new File(_property, ".gtkrc-2.0");
              URL url = new URL("http://eclipse.jeeeyul.net/files/gtkrc-2.0");
              InputStream _openStream = url.openStream();
              ReadableByteChannel rbc = Channels.newChannel(_openStream);
              FileOutputStream fos = new FileOutputStream(file);
              FileChannel _channel = fos.getChannel();
              _channel.transferFrom(rbc, 0, Long.MAX_VALUE);
              it.done();
              AskingRestart _askingRestart = new AskingRestart();
              _askingRestart.schedule();
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        };
        _container.run(true, false, _function);
        _xblockexpression = true;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Pure
  public boolean isDontDownload() {
    return this._dontDownload;
  }
  
  public void setDontDownload(final boolean dontDownload) {
    this._dontDownload = dontDownload;
  }
}
