package com.locizehelper.core

import com.intellij.openapi.vfs.VirtualFile
import com.locizehelper.core.Config.defaultLanguage
import com.locizehelper.core.Config.translationsDirectory

enum class TranslationFileType {
    Dev,
    Other,
}

fun getTranslationFileType(file: VirtualFile?): TranslationFileType? = when {
    file?.extension != "json" || !file.path.contains("/$translationsDirectory/") -> null
    file.path.contains("/${defaultLanguage}/") -> TranslationFileType.Dev
    else -> TranslationFileType.Other
}

fun getNamespace(file: VirtualFile) = file.nameWithoutExtension
