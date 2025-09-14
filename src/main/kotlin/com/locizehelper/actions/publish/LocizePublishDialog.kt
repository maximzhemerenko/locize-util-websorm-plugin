package com.locizehelper.actions.publish

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.locizehelper.core.getAllReleases
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import com.intellij.ui.components.JBScrollPane
import com.locizehelper.core.Release
import com.locizehelper.core.TranslationUtil
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.Action
import javax.swing.JScrollPane

val versionComparator = Comparator<Release> { a, b ->
    val partsA = a.version.split('.').map { it.toInt() }
    val partsB = b.version.split('.').map { it.toInt() }

    for (i in 0 until maxOf(partsA.size, partsB.size)) {
        val partA = partsA.getOrElse(i) { 0 }
        val partB = partsB.getOrElse(i) { 0 }
        if (partA != partB) return@Comparator partB - partA
    }

    return@Comparator 0
}

class PublishButton(private val project: Project, private val release: Release): JButton() {
    var state: String? = null
        set(value) {
            field = value
            text = getTitle()
        }

    init {
        text = getTitle()

        addActionListener { publish() }
    }

    fun getTitle() = "<html>${release.product} ${release.version}${if (state != null) "<br>$state" else ""}</html>"

    fun publish() {
        state = "published"

        TranslationUtil(project).run("publish", release = release)
    }
}

class LocizePublishDialog(private val project: Project) : DialogWrapper(true) {
    init {
        init()
        title = "PUBLISH..."
        isModal = false
    }

    override fun createActions(): Array<Action> {
        // Только кнопка OK
        return arrayOf(okAction)
    }

    override fun createCenterPanel(): JComponent {
        val releases = getAllReleases(project)

        val panel = JPanel(GridBagLayout())

        val constraints = GridBagConstraints().apply {
            anchor = GridBagConstraints.NORTHWEST
        }

        fun addProductVersions(product: String) {
            panel.add(JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)

                releases.filter { it.product === product }.sortedWith(versionComparator).forEach {
                    add(PublishButton(project, it))
                }
            }, constraints)
        }

        addProductVersions("slotoking")
        addProductVersions("777")
        addProductVersions("vegas")

        return JBScrollPane(panel).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        }
    }
}