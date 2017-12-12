package ms.konovalov.intellij.hidpi;

import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;

import java.util.Objects;

public class FontProfileManager {

    static void applyProfile(FontProfile profile) {
        UISettings uiSettings = UISettings.getInstance();
        uiSettings.setFontSize(profile.getGlobalFontSize());
        if (profile.getGlobalFontFamily() != null) {
            uiSettings.setFontFace(profile.getGlobalFontFamily());
        }
        uiSettings.setOverrideLafFonts(profile.isOverrideGlobalFont());

        EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getSchemeForCurrentUITheme();
        globalScheme.setEditorFontSize(profile.getEditorFontSize());
        if (profile.getEditorFontFamily() != null) {
            globalScheme.setEditorFontName(profile.getEditorFontFamily());
        }
        globalScheme.setConsoleFontSize(profile.getConsoleFontSize());
        if (profile.getConsoleFontFamily() != null) {
            globalScheme.setConsoleFontName(profile.getConsoleFontFamily());
        }
        applySettings(uiSettings);
    }

    private static void applySettings(UISettings uiSettings) {
        uiSettings.fireUISettingsChanged();
        EditorFactory.getInstance().refreshAllEditors();
        LafManager.getInstance().updateUI();
        ActionToolbarImpl.updateAllToolbarsImmediately();
    }

    @SuppressWarnings("SameParameterValue")
    static FontProfile readCurrentProfile(String name, boolean active) {
        UISettings instance = UISettings.getInstance();
        EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
        return new FontProfile(
                active,
                name,
                instance.getOverrideLafFonts(),
                instance.getFontSize(),
                instance.getFontFace(),
                globalScheme.getEditorFontSize(),
                globalScheme.getEditorFontName(),
                globalScheme.getConsoleFontSize(),
                globalScheme.getConsoleFontName()
        );
    }

    static void deselectAll() {
        FontSizeComponent.getProfiles().forEach(p -> p.setActive(false));
    }

    static boolean changed(FontProfile active, UISettings settings) {
        return !(Objects.equals(settings.getFontSize(), active.getGlobalFontSize())
                && Objects.equals(settings.getFontFace(), active.getGlobalFontFamily())
                && Objects.equals(settings.getOverrideLafFonts(), active.isOverrideGlobalFont()));
    }

    static boolean changed(FontProfile active, EditorColorsScheme settings) {
        return !(Objects.equals(settings.getEditorFontSize(), active.getEditorFontSize())
                && Objects.equals(settings.getEditorFontName(), active.getEditorFontFamily())
                && Objects.equals(settings.getConsoleFontSize(), active.getConsoleFontSize())
                && Objects.equals(settings.getConsoleFontName(), active.getEditorFontFamily()));
    }
}
