package net.jeeeyul.eclipse.themes.css.internal;

import net.jeeeyul.eclipse.themes.internal.Debug;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.osgi.framework.Bundle;

/**
 * #159: Support for EditBox
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/159
 * 
 * @author Jeeeyul
 */
public class EditBoxTracker {
	private static final String KEY_EDITBOX_ENABLEMENT = "ENABLED";
	private static final String PLUGINID_EDITBOX = "pm.eclipse.editbox";

	/**
	 * Singleton instance for {@link EditBoxTracker}.
	 */
	public static final EditBoxTracker INSTANCE = new EditBoxTracker();

	private boolean fIsTracking = false;
	private Boolean fEditBoxInstalled;
	private IPreferenceStore fEditBoxPreferenceStore;
	private Boolean fEditBoxIsActive = null;

	IPropertyChangeListener fEditBoxPreferenceStoreListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equalsIgnoreCase(KEY_EDITBOX_ENABLEMENT)) {
				fEditBoxIsActive = getEditBoxPreferenceStore().getBoolean(KEY_EDITBOX_ENABLEMENT);
				if (editBoxEnablementHandler != null) {
					editBoxEnablementHandler.apply(fEditBoxIsActive);
				}
			}
		}
	};

	Procedure1<Boolean> editBoxEnablementHandler;

	private EditBoxTracker() {
	}

	/**
	 * Starts tracking for EditBox enablement.
	 */
	public synchronized void beginTrack() {
		if (fIsTracking) {
			return;
		}
		if (isEditBoxInstalled()) {
			hookEditBoxPreference();
		} else {
			Debug.println("Editbox is not installed");
		}
		fIsTracking = true;

		Debug.println("Tracking Edit Box Plugin was start");
	}

	private IPreferenceStore getEditBoxPreferenceStore() {
		if (fEditBoxPreferenceStore == null) {
			fEditBoxPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGINID_EDITBOX);
		}
		return fEditBoxPreferenceStore;
	}

	private void hookEditBoxPreference() {
		IPreferenceStore store = getEditBoxPreferenceStore();
		store.addPropertyChangeListener(fEditBoxPreferenceStoreListener);
	}

	/**
	 * 
	 * @return Whether EditBox is active.
	 */
	public boolean isEditBoxActive() {
		if (fEditBoxIsActive == null) {
			if (isEditBoxInstalled() == false) {
				fEditBoxIsActive = false;
			} else {
				fEditBoxIsActive = getEditBoxPreferenceStore().getBoolean(KEY_EDITBOX_ENABLEMENT);
			}
		}

		return fEditBoxIsActive;
	}

	private boolean isEditBoxInstalled() {

		ResolveEditBox: if (fEditBoxInstalled == null) {
			Bundle bundle = Platform.getBundle(PLUGINID_EDITBOX);
			if (bundle == null) {
				fEditBoxInstalled = false;
				break ResolveEditBox;
			}
			switch (bundle.getState()) {
			case Bundle.RESOLVED:
			case Bundle.STARTING:
			case Bundle.ACTIVE:
				fEditBoxInstalled = true;
				break;

			default:
				fEditBoxInstalled = false;
				break;
			}

		}
		return fEditBoxInstalled;
	}

	/**
	 * Sets a handler for EditBox enablemnt changes.
	 * 
	 * @param editBoxEnablementHandler
	 */
	public void setEditBoxEnablementHandler(Procedure1<Boolean> editBoxEnablementHandler) {
		this.editBoxEnablementHandler = editBoxEnablementHandler;
	}

	/**
	 * Stops tracking for EditBox enablement.
	 */
	public synchronized void stopTracking() {
		if (fIsTracking != true) {
			return;
		}
		unhookEditBoxPreference();
		fIsTracking = false;
	}

	private void unhookEditBoxPreference() {
		getEditBoxPreferenceStore().removePropertyChangeListener(fEditBoxPreferenceStoreListener);
	}
}
