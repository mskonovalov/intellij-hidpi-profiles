package ms.konovalov.intellij.hidpi

import com.intellij.ide.ui.UISettingsListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*
import com.intellij.openapi.editor.colors.EditorColorsListener
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.options.BaseConfigurableWithChangeSupport
import org.jetbrains.annotations.Nls
import java.util.*
import javax.swing.JComponent

@State(
    name = "HIDPIProfiles",
    defaultStateAsResource = true,
    storages = [(Storage(value = "hidpi.xml", roamingType = RoamingType.DISABLED))]
)
class FontSizeComponent : BaseConfigurableWithChangeSupport(), ApplicationComponent, PersistentStateComponent<FontSizeComponent.State> {

    private var form: FontSizeForm? = null
    private var state = State()

    override fun initComponent() {
        if (state.profiles.isEmpty()) {
            val initialProfile = FontProfileManager.readCurrentProfile("Default", true)
            state.profiles.add(initialProfile)
        }
        val active = state.activeProfile
        if (active != null) {
            FontProfileManager.applyProfile(active)
        }

        val messageBus = ApplicationManager.getApplication().messageBus
        messageBus.connect().subscribe(UISettingsListener.TOPIC, UISettingsListener { settingsChange ->
            if (active != null) {
                if (FontProfileManager.changed(active, settingsChange)) {
                    FontProfileManager.deselectAll()
                }
            }
        })
        messageBus.connect().subscribe(EditorColorsManager.TOPIC, EditorColorsListener { _ ->
            if (active != null) {
                if (FontProfileManager.changed(active, EditorColorsManager.getInstance().globalScheme)) {
                    FontProfileManager.deselectAll()
                }
            }
        })
    }

    override fun disposeComponent() {
        state.clear()
    }

    override fun getComponentName(): String {
        return "FontSizeComponent"
    }

    override fun getState(): State {
        return state
    }

    override fun loadState(state: State) {
        this.state = state
    }

    class State {
        var profiles: MutableList<FontProfile> = ArrayList()
        val activeProfile: FontProfile? = profiles.firstOrNull { it.isActive }

        fun clear() {
            this.profiles.clear()
        }
    }

    @Nls
    override fun getDisplayName(): String {
        return "HIDPI Profiles"
    }

    override fun getHelpTopic(): String? {
        return "HIDPI Help"
    }

    override fun createComponent(): JComponent? {
        form = FontSizeForm(this)
        return form!!.panel
    }

    override fun apply() {
        form!!.apply()
    }

    override fun reset() {
        form!!.reset()
    }

    override fun disposeUIResources() {
        //do nothing
    }
}
