package ms.konovalov.intellij.hidpi

import com.intellij.notification.*
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.actionSystem.Toggleable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.project.Project

class FontSizeAction internal constructor(private val profile: FontProfile) : ToggleAction(profile.name) {

    override fun update(e: AnActionEvent) {
        val selected = isSelected(e)
        val presentation = e.presentation
        presentation.putClientProperty(Toggleable.SELECTED_PROPERTY, selected)
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        return profile.isActive
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        if (profile.isActive) {
            return
        }
        FontProfileManager.deselectAll()
        FontProfileManager.applyProfile(profile)
        profile.isActive = true
        ApplicationManager.getApplication().messageBus.syncPublisher(EditorColorsManager.TOPIC).globalSchemeChange(
                EditorColorsManager.getInstance().schemeForCurrentUITheme
        )
        showSplashNotification(e.project, GROUP_ID, "HIDPI Profiles", "Profile '${profile.name}' is applied.", NotificationType.INFORMATION)
    }

    companion object {
        private val GROUP_ID = "ms.konovalov.intellij.hidpi"

        private fun showSplashNotification(project: Project?, groupId: String, title: String, message: String, notificationType: NotificationType) {
            ApplicationManager.getApplication().runWriteAction {
                NotificationsConfiguration.getNotificationsConfiguration().register(groupId, NotificationDisplayType.BALLOON, false)
                Notifications.Bus.notify(Notification(groupId, title, message, notificationType, null), project)
            }
        }
    }
}
