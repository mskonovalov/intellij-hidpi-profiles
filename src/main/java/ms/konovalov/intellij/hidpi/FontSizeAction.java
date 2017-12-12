package ms.konovalov.intellij.hidpi;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class FontSizeAction extends ToggleAction {

    private static final String GROUP_ID = "ms.konovalov.intellij.hidpi";
    private final FontProfile profile;

    FontSizeAction(FontProfile profile) {
        super(profile.getName());
        this.profile = profile;
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        boolean selected = isSelected(e);
        final Presentation presentation = e.getPresentation();
        presentation.putClientProperty(SELECTED_PROPERTY, selected);
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return profile.isActive();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        if (profile.isActive()) {
            return;
        }
        FontProfileManager.deselectAll();
        FontProfileManager.applyProfile(profile);
        profile.setActive(true);
        ApplicationManager.getApplication().getMessageBus().syncPublisher(EditorColorsManager.TOPIC).globalSchemeChange(
                EditorColorsManager.getInstance().getSchemeForCurrentUITheme()
        );
        showSplashNotification(e.getProject(), GROUP_ID, "HIDPI Profiles", "Profile '" + profile.getName() + "' is applied.", NotificationType.INFORMATION);
    }

    @SuppressWarnings("SameParameterValue")
    private static void showSplashNotification(final Project project, final String groupId, final String title, final String message, final NotificationType notificationType) {
        ApplicationManager.getApplication().runWriteAction(() -> {
            NotificationsConfiguration.getNotificationsConfiguration().register(groupId, NotificationDisplayType.BALLOON, false);
            final Notification notification = new Notification(groupId, title, message,
                    notificationType, null);
            Notifications.Bus.notify(notification, project);
        });
    }
}
