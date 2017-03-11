package ms.konovalov.intellij.hidpi;

import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.options.BaseConfigurableWithChangeSupport;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@State(name = "HIDPIProfiles", defaultStateAsResource = true, storages = @Storage(value = "hidpi.xml", roamingType = RoamingType.DISABLED))
public class FontSizeComponent extends BaseConfigurableWithChangeSupport implements ApplicationComponent, PersistentStateComponent<FontSizeComponent.State> {

    private State state = new State();

    public void initComponent() {
        if (state.getProfiles().isEmpty()) {
            FontProfile initialProfile = getCurrentProfile("Default", true);
            state.getProfiles().add(initialProfile);
        }
    }

    private List<FontProfile> profiles() {
        return state.getProfiles();
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    static FontProfile getCurrentProfile(String name, boolean active) {
        UISettings instance = UISettings.getInstance();
        return new FontProfile(
                active,
                name,
                instance.getOverrideLafFonts(),
                instance.getFontSize(),
                EditorColorsManager.getInstance().getGlobalScheme().getEditorFontSize(),
                EditorColorsManager.getInstance().getGlobalScheme().getConsoleFontSize()
        );
    }

    @Override
    public void disposeComponent() {
        if (state != null) {
            state.clear();
        }
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "FontSizeComponent";
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    static List<FontProfile> getProfiles() {
        return ApplicationManager.getApplication().getComponent(FontSizeComponent.class).profiles();
    }

    @Data
    static class State {

        private List<FontProfile> profiles = new ArrayList<>();

        void clear() {
            if (profiles != null)
                profiles.clear();
            profiles = null;
        }

    }

    private FontSizeForm form;

    @Nls
    @Override
    public String getDisplayName() {
        return "HIDPI Profiles";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "HIDPI Help";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        form = new FontSizeForm(this);
        return form.getPanel();
    }

    @Override
    public void apply() throws ConfigurationException {
        form.apply();
    }

    @Override
    public void reset() {
        form.reset();
    }

    @Override
    public void disposeUIResources() {
    }
}
