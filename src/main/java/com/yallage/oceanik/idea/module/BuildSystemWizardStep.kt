/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package com.yallage.oceanik.idea.module

import com.demonwav.mcdev.creator.MinecraftProjectCreator
import com.demonwav.mcdev.creator.buildsystem.BuildSystem
import com.demonwav.mcdev.creator.buildsystem.BuildSystemType
import com.demonwav.mcdev.creator.exception.EmptyFieldSetupException
import com.demonwav.mcdev.creator.exception.OtherSetupException
import com.demonwav.mcdev.creator.exception.SetupException
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JTextField

class BuildSystemWizardStep(private val creator: OceanikModuleConfig) : ModuleWizardStep() {

    private lateinit var groupIdField: JTextField
    private lateinit var artifactIdField: JTextField
    private lateinit var versionField: JTextField
    private lateinit var panel: JPanel

    override fun getComponent() = panel

    override fun updateDataModel() {
        creator.artifactId = artifactIdField.text;
        creator.groupId = groupIdField.text;
        creator.version = versionField.text;
    }

    override fun validate(): Boolean {
        try {
            if (groupIdField.text.isEmpty()) {
                throw EmptyFieldSetupException(groupIdField)
            }

            if (artifactIdField.text.isEmpty()) {
                throw EmptyFieldSetupException(artifactIdField)
            }

            if (versionField.text.isBlank()) {
                throw EmptyFieldSetupException(versionField)
            }

            if (!groupIdField.text.matches(NO_WHITESPACE)) {
                throw OtherSetupException("The GroupId field cannot contain any whitespace", groupIdField)
            }

            if (!artifactIdField.text.matches(NO_WHITESPACE)) {
                throw OtherSetupException("The ArtifactId field cannot contain any whitespace", artifactIdField)
            }
        } catch (e: SetupException) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(e.error, MessageType.ERROR, null)
                .setFadeoutTime(2000)
                .createBalloon()
                .show(RelativePoint.getSouthWestOf(e.j), Balloon.Position.below)
            return false
        }

        return true
    }

    companion object {
        val NO_WHITESPACE = Regex("\\S+")
    }
}
