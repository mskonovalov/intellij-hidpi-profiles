package ms.konovalov.intellij.hidpi

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class FontSizeActionGroup : ActionGroup() {

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return FontProfileManager.profiles.map({ FontSizeAction(it) }).toTypedArray()
    }
}
