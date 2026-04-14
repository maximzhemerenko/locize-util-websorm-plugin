package com.locizehelper.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.intellij.openapi.project.Project
import java.io.File

@JsonIgnoreProperties(ignoreUnknown = true)
private data class AppProduct(val version: String)

private typealias AppVersion = AppProduct

class Release(val version: String)

fun getRelease(project: Project): Release {
    val file = File(project.basePath, "appVersion.json")

    val data = jacksonObjectMapper()
        .readValue<AppVersion>(file)

    return Release(data.version)
}
