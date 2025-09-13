package com.locizehelper.actions

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Separator
import com.locizehelper.core.getReleases

class LocizeReleaseGroup : ActionGroup("RELEASE", true) {
    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val project = e?.project ?: return emptyArray()

        return mutableListOf<AnAction>().apply {
            getReleases(project).forEach {
                add(Separator("${it.product} [${it.version}]"))

                add(LocizeGetAllAction(it))
                add(LocizeDevSyncAction(it))
                add(LocizePublishAction(it))
            }
        }.toTypedArray()
    }
}
