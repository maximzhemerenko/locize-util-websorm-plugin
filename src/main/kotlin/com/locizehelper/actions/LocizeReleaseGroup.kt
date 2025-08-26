package com.locizehelper.actions

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnActionEvent
import com.locizehelper.core.getReleases

class LocizeReleaseGroup : ActionGroup() {
    override fun getChildren(e: AnActionEvent?): Array<LocizeReleaseAction> {
        val project = e?.project ?: return emptyArray()

        return getReleases(project).map { LocizeReleaseAction(it) }.toTypedArray()
    }
}
