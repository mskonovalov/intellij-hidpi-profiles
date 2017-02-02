package ms.konovalov.intellij.hidpi;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FontSizeActionGroup extends ActionGroup {

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        return FontSizeComponent.getProfiles().stream().map(FontSizeAction::new).toArray(AnAction[]::new);
    }
}
