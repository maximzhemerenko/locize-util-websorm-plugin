package com.locizehelper.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.locizehelper.core.getTranslationFileType

class LocizeButtonGroup : DefaultActionGroup() {
    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)

        val isVisible = getTranslationFileType(file) !== null

        e.presentation.isVisible = isVisible
        e.presentation.isEnabled = isVisible
    }
}
