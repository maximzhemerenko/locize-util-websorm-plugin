package com.locizehelper.actions.publish

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class LocizePublishAction() : AnAction("PUBLISH...") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        LocizePublishDialog(project).show()
    }
}
