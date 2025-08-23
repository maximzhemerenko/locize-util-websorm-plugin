package com.locizehelper.core

import com.intellij.openapi.project.Project
import com.sshtools.jini.INI
import java.io.File
import kotlin.jvm.optionals.getOrNull

object Config {
    var defaultLanguage: String? = null
    var translationsDirectory: String? = null

    fun load(project: Project) {
        defaultLanguage = null
        translationsDirectory = null

        try {
            val ini = INI.fromFile(File(project.basePath, ".translations").toPath())

            defaultLanguage = ini.getOr("defaultLanguage").getOrNull()
            translationsDirectory = ini.getOr("translationsDirectory").getOrNull()
        } catch (_: Throwable) {
        }
    }
}
