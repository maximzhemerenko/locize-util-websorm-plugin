package com.locizehelper.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.intellij.openapi.project.Project
import java.io.File

@JsonIgnoreProperties(ignoreUnknown = true)
private data class AppProduct(val version: String)

private typealias AppVersion = Map<String, AppProduct>

class Release(val version: String, val product: String)

fun getReleases(project: Project): List<Release> {
    val file = File(project.basePath, "appVersion.json")

    return jacksonObjectMapper()
        .readValue<AppVersion>(file)
        .map { (product, data) -> Release(data.version, product)
    }
}
