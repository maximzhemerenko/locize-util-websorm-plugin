package com.locizehelper.core

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.InvokeAfterUpdateMode
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager

fun saveOpenFiles() {
    ApplicationManager.getApplication().invokeAndWait {
        ApplicationManager.getApplication().runWriteAction {
            FileDocumentManager.getInstance().saveAllDocuments()
        }
    }
}

fun updateOpenFiles(project: Project) {
    val fileEditorManager = FileEditorManager.getInstance(project)
    val openFiles = fileEditorManager.openFiles

    for (virtualFile in openFiles) {
        virtualFile.refresh(false, false)
    }
}

fun updateGitChanges(project: Project) {
    VcsDirtyScopeManager.getInstance(project).markEverythingDirty()

    ChangeListManager.getInstance(project).invokeAfterUpdate(
        {},
        InvokeAfterUpdateMode.SILENT,
        "",
        null,
    )
}
