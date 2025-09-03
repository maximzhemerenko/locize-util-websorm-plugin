package com.locizehelper.actions

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.Separator
import com.locizehelper.core.getTranslationFileType

class LocizeButtonGroup : ActionGroup() {
    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)

        val isVisible = getTranslationFileType(file) !== null

        e.presentation.isVisible = isVisible
        e.presentation.isEnabled = isVisible
    }

    override fun getChildren(e: AnActionEvent?) = mutableListOf<AnAction>().apply {
        add(LocizeDevAction())
        add(LocizeDevSyncAction())

        add(Separator())

        add(LocizeGetAction())
        add(LocizeGetAllAction())

        add(Separator())

        add(LocizeReleaseGroup())
    }.toTypedArray()
}
