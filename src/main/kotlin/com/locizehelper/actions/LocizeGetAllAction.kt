package com.locizehelper.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.locizehelper.core.Release
import com.locizehelper.core.TranslationUtil

class LocizeGetAllAction(private val release: Release? = null) : AnAction(if (release == null) "Locize GET ALL" else "GET ALL") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        TranslationUtil(project).run("get", release = release)
    }
}
