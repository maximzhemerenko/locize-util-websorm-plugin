package com.locizehelper.core

import com.intellij.openapi.application.EdtImmediate
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TranslationUtil(private val project: Project) {
    fun run(command: String, namespace: String) {
        CoroutineScope(Dispatchers.EdtImmediate).launch {
            saveOpenFiles()

            ShellScript(project).run(listOf("yarn", "locize", command, "--namespace", namespace))

            updateOpenFiles(project)

            updateGitChanges(project)
        }
    }
}
