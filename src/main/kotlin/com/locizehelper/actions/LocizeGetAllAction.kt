package com.locizehelper.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.locizehelper.core.TranslationUtil

class LocizeGetAllAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        TranslationUtil(project).run("get")
    }
}
