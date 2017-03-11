package ms.konovalov.intellij.hidpi;

import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.UISettings;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class FontSizeAction extends ToggleAction {

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
        unselectAll();
        applyProfile(profile);
        profile.setActive(true);
        notify(e.getProject(), "HIDPI Profiles", "HIDPI Profiles", "Profile '" + profile.getName() + "' is applied.", NotificationType.INFORMATION);
    }

    private void unselectAll() {
        FontSizeComponent.getProfiles().forEach(p -> p.setActive(false));
    }

    private void applyProfile(FontProfile profile) {
        UISettings uiSettings = UISettings.getInstance();
        uiSettings.setFontSize(profile.getGlobalFontSize());
        uiSettings.setOverrideLafFonts(profile.isOverrideGlobalFont());
        EditorColorsManager.getInstance().getGlobalScheme().setEditorFontSize(profile.getEditorFontSize());
        EditorColorsManager.getInstance().getGlobalScheme().setConsoleFontSize(profile.getConsoleFontSize());

        applySettings(uiSettings);
    }

    private void applySettings(UISettings uiSettings) {
        uiSettings.fireUISettingsChanged();
        EditorFactory.getInstance().refreshAllEditors();
        LafManager.getInstance().updateUI();
        ActionToolbarImpl.updateAllToolbarsImmediately();
    }

    @SuppressWarnings("SameParameterValue")
    private static void notify(final Project project, final String groupId, final String title, final String message, final NotificationType notificationType) {
        ApplicationManager.getApplication().runWriteAction(() -> {
            NotificationsConfiguration.getNotificationsConfiguration().register(groupId, NotificationDisplayType.BALLOON, false);
            final Notification notification = new Notification(groupId, title, message,
                    notificationType, null);
            Notifications.Bus.notify(notification, project);
        });
    }
}