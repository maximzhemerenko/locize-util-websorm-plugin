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

private fun getAllRemoteBranches(project: Project): List<String> {
    val process = ProcessBuilder("git", "--no-pager", "branch", "-a")
        .directory(project.basePath?.let { File(it) })
        .redirectErrorStream(true)
        .start()

    process.waitFor()

    return process.inputStream.bufferedReader().readLines()
}

private fun extractProductVersion(branch: String, regexPattern: String): String? {
    val regex = Regex(regexPattern)
    val match = regex.find(branch)
    return match?.groupValues?.get(1)
}

private fun extractReleaseFromBranch(branch: String): Release? {
    // slotoking
    var version = extractProductVersion(branch, """\/release\/release-(\d+\.\d+\.\d+)""")
    if (version != null) {
        return Release(version, "slotoking")
    }

    // 777
    version = extractProductVersion(branch, """\/release\/777\/release-(\d+\.\d+\.\d+)""")
    if (version != null) {
        return Release(version, "777")
    }

    // vegas
    version = extractProductVersion(branch, """\/release\/vegas\/release-(\d+\.\d+\.\d+)""")
    if (version != null) {
        return Release(version, "vegas")
    }

    return null
}

fun getAllReleases(project: Project) = mutableListOf<Release>().apply {
    getAllRemoteBranches(project).forEach {
        add(extractReleaseFromBranch(it) ?: return@forEach)
    }
}
