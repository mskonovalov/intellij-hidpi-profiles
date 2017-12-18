package ms.konovalov.intellij.hidpi

import com.intellij.openapi.ui.Messages

import javax.swing.*
import java.awt.*

class FontSizeForm internal constructor(private val component: FontSizeComponent) {

    internal var panel: JPanel? = null
    private var profilesList: JList<FontProfile>? = null
    private var addCurrentButton: JButton? = null
    private var deleteButton: JButton? = null
    private val profiles = FontProfileManager.profiles

    init {
        profilesList!!.model = setModel()
        profilesList!!.selectedIndex = 0
        profilesList!!.cellRenderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                val label = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) as JLabel
                label.text = (value as FontProfile).name
                return label
            }
        }

        deleteButton!!.addActionListener { _ ->
            val idx = profilesList!!.selectedIndex
            if (idx != -1) {
                (profilesList!!.model as DefaultListModel).remove(idx)
                setModified(true)
                if (profilesList!!.model.size == 0) {
                    return@addActionListener
                }
                if (idx > profilesList!!.model.size - 1) {
                    profilesList!!.setSelectedIndex(idx - 1)
                } else {
                    profilesList!!.setSelectedIndex(idx)
                }
            }
        }

        addCurrentButton!!.addActionListener { _ ->
            val profileName = Messages.showInputDialog("Provide name for new Profile", "HDPI Profile ", null) ?: return@addActionListener
            val activeProfile = FontProfileManager.readCurrentProfile(profileName, false)
            (profilesList!!.model as DefaultListModel<FontProfile>).addElement(activeProfile)
            profilesList!!.selectedIndex = profilesList!!.model.size - 1
            setModified(true)
        }
    }

    private fun setModel(): DefaultListModel<FontProfile> {
        val model = DefaultListModel<FontProfile>()
        profiles.forEach { fp -> model.addElement(fp) }
        return model
    }

    internal fun reset() {
        profilesList!!.model = setModel()
        setModified(false)
    }

    internal fun apply() {
        profiles.clear()
        val model = profilesList!!.model
        (0 until model.size).mapTo(profiles) { model.getElementAt(it) }
        setModified(false)
    }

    private fun setModified(modified: Boolean) {
        component.isModified = modified
    }
}
