package com.locizehelper.core

import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.execution.ui.RunContentManager
import com.intellij.openapi.project.Project
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ShellScript(private val project: Project) {
    suspend fun run(command: List<String>) = suspendCoroutine { suspend ->
        val consoleView = TextConsoleBuilderFactory.getInstance()
            .createBuilder(project)
            .console

        val process = ProcessBuilder("/bin/zsh", "-li", "-c", command.joinToString(" "))
            .directory(project.basePath?.let { File(it) })
            .redirectErrorStream(true)
            .start()

        val processHandler = object : OSProcessHandler(process, command.joinToString(" ")) {}
        consoleView.attachToProcess(processHandler)

        val descriptor = RunContentDescriptor(consoleView, processHandler, consoleView.component, "Locize")

        val runContentManager = RunContentManager.getInstance(project)

        processHandler.addProcessListener(object : ProcessListener {
            override fun processTerminated(event: ProcessEvent) {
                if (event.exitCode == 0) {
                    consoleView.print("\nDone\n", ConsoleViewContentType.SYSTEM_OUTPUT)

                    suspend.resume(Unit)
                } else {
                    consoleView.print("\nProcess exited with code ${event.exitCode}\n", ConsoleViewContentType.ERROR_OUTPUT)

                    suspend.resume(Unit)
                }
            }
        })

        processHandler.startNotify()

        runContentManager.showRunContent(DefaultRunExecutor.getRunExecutorInstance(), descriptor)
    }
}
