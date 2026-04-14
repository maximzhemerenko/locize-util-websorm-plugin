package com.locizehelper.actions

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Separator
import com.locizehelper.core.getRelease

class LocizeReleaseGroup : ActionGroup("RELEASE", true) {
    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val project = e?.project ?: return emptyArray()

        return mutableListOf<AnAction>().apply {
            getRelease(project).let {
                add(Separator(it.version))

                add(LocizeGetAllAction(it))
                add(LocizeDevSyncAction(it))

                add(Separator())

                add(LocizePublishAction(it))
            }
        }.toTypedArray()
    }
}
