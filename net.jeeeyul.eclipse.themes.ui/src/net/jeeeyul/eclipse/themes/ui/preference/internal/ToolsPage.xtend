package net.jeeeyul.eclipse.themes.ui.preference.internal

import net.jeeeyul.eclipse.themes.SharedImages
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.ColorPicker
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.jface.preference.PreferenceStore
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite

class ToolsPage extends AbstractJTPreferencePage {
	new() {
		super("Tools")
		image = SharedImages.getImage(SharedImages.CONFIG)
	}

	override createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		parent.newComposite [
			layout = newGridLayout[]
			newGroup[
				text = "Batch Color Tasks"
				layout = newGridLayout
				layoutData = FILL_HORIZONTAL
				newPushButton[
					text = "Re-write hue globally"
					onSelection = [
						rewriteHue(helper)
					]
				]
			]
			newGroup[
				text = "Part Stack Batch Task"
				layout = newGridLayout
				layoutData = FILL_HORIZONTAL
				newPushButton[
					text = "Copy settings from Active to Inactive"
					onSelection = [
						copy(JTPConstants.ActivePartStack.PREFIX, JTPConstants.InactivePartStack.PREFIX, swtExtensions, helper)
					]
				]
				newPushButton[
					text = "Copy settings from Inactive to Active"
					onSelection = [
						copy(JTPConstants.InactivePartStack.PREFIX, JTPConstants.ActivePartStack.PREFIX, swtExtensions, helper)
					]
				]
			]
		]
	}

	def private rewriteHue(extension PreperencePageHelper helper) {
		var picker = new ColorPicker(null);
		picker.selection = new HSB(255, 0, 0)
		if(picker.open() == IDialogConstants.OK_ID) {
			var copy = createWorkingCopy()

			val gradientKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET.chain(IPreferenceFilter.GRADIENT_TYPE_FILTER))
			for (each : gradientKeys) {
				var grad = copy.getGradient(each)
				for (eachStop : grad) {
					eachStop.color.hue = picker.selection.hue;
				}
				copy.setValue(each, grad)
			}

			val hsbKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET.chain(IPreferenceFilter.HSB_TYPE_FILTER))
			for (each : hsbKeys) {
				var hsb = copy.getHSB(each)
				hsb.hue = picker.selection.hue
				copy.setValue(each, hsb)
			}

			loadFromWorkingCopy(copy)
		}
	}

	override updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	override dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
	}

	private def void copy(String from, String to, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper) {
		var stacksPage = allPages.filter(typeof(PartStacksPage)).head
		var sibilings = stacksPage.children
		var fromPage = sibilings.filter(typeof(PartStackPage)).findFirst[it.context == from]
		var toPage = sibilings.filter(typeof(PartStackPage)).findFirst[it.context == to]

		var fakeStore = new JThemePreferenceStore(new PreferenceStore())
		fakeStore.customKeyResolver = [it]

		fromPage.save(fakeStore, swtExtensions, helper)
		toPage.load(fakeStore, swtExtensions, helper)

		requestUpdatePreview()
	}

}
