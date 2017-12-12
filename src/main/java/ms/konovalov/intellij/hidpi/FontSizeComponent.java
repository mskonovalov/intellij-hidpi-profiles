package ms.konovalov.intellij.hidpi;

import com.intellij.ide.ui.UISettingsListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.options.BaseConfigurableWithChangeSupport;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@State(name = "HIDPIProfiles", defaultStateAsResource = true, storages = @Storage(value = "hidpi.xml", roamingType = RoamingType.DISABLED))
public class FontSizeComponent extends BaseConfigurableWithChangeSupport implements ApplicationComponent, PersistentStateComponent<FontSizeComponent.State> {

    private FontSizeForm form;
    private State state = new State();

    public void initComponent() {
        if (state.getProfiles().isEmpty()) {
            FontProfile initialProfile = FontProfileManager.INSTANCE.readCurrentProfile("Default", true);
            state.getProfiles().add(initialProfile);
        }
        state.getActiveProfile().ifPresent(FontProfileManager.INSTANCE::applyProfile);

        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        messageBus.connect().subscribe(UISettingsListener.TOPIC, settingsChange ->
                state.getActiveProfile().ifPresent(active -> {
                    if (FontProfileManager.INSTANCE.changed(active, settingsChange)) {
                        FontProfileManager.INSTANCE.deselectAll();
                    }
                }));
        messageBus.connect().subscribe(EditorColorsManager.TOPIC, settingsChange ->
                state.getActiveProfile().ifPresent(active -> {
                    if (FontProfileManager.INSTANCE.changed(active, EditorColorsManager.getInstance().getGlobalScheme())) {
                        FontProfileManager.INSTANCE.deselectAll();
                    }
                }));
    }

    private List<FontProfile> profiles() {
        return state.getProfiles();
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

    @Getter
    @Setter
    static class State {

        private List<FontProfile> profiles = new ArrayList<>();

        void clear() {
            if (profiles != null) {
                profiles.clear();
            }
        }

        Optional<FontProfile> getActiveProfile() {
            return profiles.stream().filter(FontProfile::isActive).findFirst();
        }
    }

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
    public void apply() {
        form.apply();
    }

    @Override
    public void reset() {
        form.reset();
    }

    @Override
    public void disposeUIResources() {
        //do nothing
    }
}
