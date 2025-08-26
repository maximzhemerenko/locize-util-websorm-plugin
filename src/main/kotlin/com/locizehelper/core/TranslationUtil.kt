package com.locizehelper.core

import com.intellij.openapi.application.EdtImmediate
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TranslationUtil(private val project: Project) {
    fun run(command: String, namespace: String? = null, sync: Boolean = false) {
        CoroutineScope(Dispatchers.EdtImmediate).launch {
            saveOpenFiles()

            val args = mutableListOf("yarn", "locize", command).apply {
                if (namespace != null) addAll(listOf("--namespace", namespace))

                if (sync) add("--sync")
            }

            ShellScript(project).run(args)

            updateOpenFiles(project)

            updateGitChanges(project)
        }
    }
}
