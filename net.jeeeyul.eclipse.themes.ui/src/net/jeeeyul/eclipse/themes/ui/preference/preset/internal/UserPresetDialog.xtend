package net.jeeeyul.eclipse.themes.ui.preference.preset.internal

import net.jeeeyul.eclipse.themes.JThemesCore
import net.jeeeyul.eclipse.themes.ui.shared.CollectionContentProvider
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.dialogs.Dialog
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil
import org.eclipse.swt.custom.CLabel
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.ISharedImages
import org.eclipse.jface.viewers.IStructuredSelection
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Dialog for #157
 */
class UserPresetDialog extends Dialog {
	extension SWTExtensions = SWTExtensions.INSTANCE

	Text presetNameField
	TableViewer presetViewer
	@Accessors String selectedPresetName

	Button newPresetButton
	Button existingPresetButton
	CLabel validationLabel

	new(Shell parentShell) {
		super(parentShell)
		shellStyle = SWT.RESIZE || SWT.CLOSE || SWT.TITLE
	}

	override create() {
		super.create()
		shell.text = "Save as preset"
	}

	override protected createDialogArea(Composite parent) {
		schedule[
			if(shell.isDisposed) {
				return;
			}
			updateUIStates()
		]

		super.createDialogArea(parent) as Composite => [
			newPresetButton = newRadioButton[
				text = "Save as a new preset"
				selection = true
				onSelection = [updateUIStates]
			]
			presetNameField = newTextField[
				layoutData = FILL_HORIZONTAL[
					horizontalIndent = 16
				]
				message = "Enter a new preset name"
				onModified = [
					validate
					selectedPresetName = presetNameField.text.trim
				]
			]
			existingPresetButton = newRadioButton[
				text = "Save as an existing preset"
				onSelection = [updateUIStates]
			]
			presetViewer = new TableViewer(it, SWT.FULL_SELECTION || SWT.BORDER) => [
				it.control.layoutData = FILL_BOTH[
					horizontalIndent = 16
				]
				labelProvider = new PresetLabelProvider
				contentProvider = new CollectionContentProvider
				input = JThemesCore.^default.presetManager.userCategory.presets
				addSelectionChangedListener[
					var selected = (presetViewer.selection as IStructuredSelection).firstElement as IJTPreset
					selectedPresetName = selected?.name
					validate
				]
			]
			validationLabel = newCLabel[
				layoutData = FILL_HORIZONTAL
			]
		]
	}

	override protected getDialogBoundsSettings() {
		var settings = JThemesCore.^default.dialogSettings.getSection(UserPresetDialog.canonicalName)
		if(settings == null) {
			settings = JThemesCore.^default.dialogSettings.addNewSection(UserPresetDialog.canonicalName)
		}
		return settings
	}

	def private updateUIStates() {
		presetNameField.enabled = newPresetButton.selection
		presetViewer.control.enabled = existingPresetButton.selection
		validate()
	}

	def private validate() {
		var String error = null;
		if(newPresetButton.selection) {
			var nameValidator = JTPUtil.getPresetNameValidator
			var name = presetNameField.text.trim
			error = nameValidator.isValid(name)
		} else {
			var selected = (presetViewer.selection as IStructuredSelection).firstElement as IJTPreset
			if(selected == null) {
				error = "Choose a preset to overwrite."
			}
		}

		var okButton = getButton(IDialogConstants.OK_ID)
		if(error == null) {
			validationLabel.text = ""
			validationLabel.image = null
		} else {
			validationLabel.text = error
			validationLabel.image = PlatformUI.workbench.sharedImages.getImage(ISharedImages.IMG_OBJS_WARN_TSK)
		}
		okButton.enabled = (error == null)
	}

	override protected createButtonBar(Composite parent) {
		parent => [
			newHorizontalSeparator[]
		]
		super.createButtonBar(parent)
	}

	def static void main(String[] args) {
		new UserPresetDialog(null).open
	}

}
