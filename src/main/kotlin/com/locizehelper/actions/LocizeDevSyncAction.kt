package com.locizehelper.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.locizehelper.core.Release
import com.locizehelper.core.TranslationUtil
import com.locizehelper.core.getNamespace

class LocizeDevSyncAction(private val release: Release? = null) : AnAction(if (release == null) "Locize DEV SYNC" else "DEV SYNC") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val namespace = getNamespace(file)

        val versionDescription = if (release == null) "" else "(${release.product} ${release.version})"

        if (Messages.showYesNoDialog(
                e.project,
                "Do you really want to overwrite the \"$namespace\" namespace $versionDescription with your local version? This will remove any translations that may have been added by other developers.",
                "DEV SYNC",
                Messages.getQuestionIcon()
            ) != Messages.YES) {
            return
        }

        TranslationUtil(project).run("dev", namespace, release = release, sync = true)
    }
}
