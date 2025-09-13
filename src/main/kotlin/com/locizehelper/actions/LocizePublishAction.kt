package com.locizehelper.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.locizehelper.core.Release
import com.locizehelper.core.TranslationUtil

class LocizePublishAction(private val release: Release) : AnAction("PUBLISH") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        TranslationUtil(project).run("publish", release = release)
    }
}
