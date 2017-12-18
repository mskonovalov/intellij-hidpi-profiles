package ms.konovalov.intellij.hidpi

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UISettings
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorColorsScheme

object FontProfileManager {

    fun applyProfile(profile: FontProfile) {
        val uiSettings = UISettings.instance
        uiSettings.fontSize = profile.globalFontSize
        if (profile.globalFontFamily != null) {
            uiSettings.fontFace = profile.globalFontFamily
        }
        uiSettings.overrideLafFonts = profile.isOverrideGlobalFont

        val globalScheme = EditorColorsManager.getInstance().schemeForCurrentUITheme
        globalScheme.editorFontSize = profile.editorFontSize
        if (profile.editorFontFamily != null) {
            globalScheme.editorFontName = profile.editorFontFamily
        }
        globalScheme.consoleFontSize = profile.consoleFontSize
        if (profile.consoleFontFamily != null) {
            globalScheme.consoleFontName = profile.consoleFontFamily
        }
        applySettings(uiSettings)
    }

    private fun applySettings(uiSettings: UISettings) {
        uiSettings.fireUISettingsChanged()
        EditorFactory.getInstance().refreshAllEditors()
        LafManager.getInstance().updateUI()
        ActionToolbarImpl.updateAllToolbarsImmediately()
    }

    fun readCurrentProfile(name: String, active: Boolean): FontProfile {
        val instance = UISettings.instance
        val globalScheme = EditorColorsManager.getInstance().globalScheme
        return FontProfile(
                active,
                name,
                instance.overrideLafFonts,
                instance.fontSize,
                instance.fontFace,
                globalScheme.editorFontSize,
                globalScheme.editorFontName,
                globalScheme.consoleFontSize,
                globalScheme.consoleFontName
        )
    }

    fun deselectAll() {
        profiles.forEach { p -> p.isActive = false }
    }

    fun changed(active: FontProfile, settings: UISettings): Boolean {
        return !(settings.fontSize == active.globalFontSize
                && settings.fontFace == active.globalFontFamily
                && settings.overrideLafFonts == active.isOverrideGlobalFont)
    }

    fun changed(active: FontProfile, settings: EditorColorsScheme): Boolean {
        return !(settings.editorFontSize == active.editorFontSize
                && settings.editorFontName == active.editorFontFamily
                && settings.consoleFontSize == active.consoleFontSize
                && settings.consoleFontName == active.editorFontFamily)
    }

    val profiles = ApplicationManager.getApplication().getComponent(FontSizeComponent::class.java).state.profiles
}
