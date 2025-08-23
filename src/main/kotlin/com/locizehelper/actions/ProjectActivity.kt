package com.locizehelper.actions

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.locizehelper.core.Config

class ProjectActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        Config.load(project)
    }
}
