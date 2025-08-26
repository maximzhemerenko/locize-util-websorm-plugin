package com.locizehelper.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.locizehelper.core.Release
import com.locizehelper.core.TranslationUtil

class LocizeReleaseAction(private val release: Release) : AnAction("${release.product} [${release.version}]") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        TranslationUtil(project).run("get", release = release)
    }
}
