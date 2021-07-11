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

import com.demonwav.mcdev.creator.ValidatedField
import com.demonwav.mcdev.creator.ValidatedFieldType.*
import com.demonwav.mcdev.creator.buildsystem.BuildSystem
import com.demonwav.mcdev.creator.exception.SetupException
import com.demonwav.mcdev.creator.getVersionSelector
import com.demonwav.mcdev.platform.bukkit.data.LoadOrder
import com.demonwav.mcdev.util.toPackageName
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import kotlinx.coroutines.withContext
import org.apache.commons.lang.WordUtils
import javax.swing.*

class BukkitProjectSettingsWizard(private val creator: OceanikModuleConfig) : ModuleWizardStep() {

    @ValidatedField(NON_BLANK)
    private lateinit var pluginNameField: JTextField

    @ValidatedField(NON_BLANK, CLASS_NAME)
    private lateinit var mainClassField: JTextField
    private lateinit var panel: JPanel
    private lateinit var descriptionField: JTextField

    @ValidatedField(LIST)
    private lateinit var authorsField: JTextField
    private lateinit var websiteField: JTextField
    private lateinit var prefixField: JTextField
    private lateinit var loadOrderBox: JComboBox<*>
    private lateinit var loadBeforeField: JTextField

    @ValidatedField(LIST)
    private lateinit var dependField: JTextField
    private lateinit var softDependField: JTextField
    private lateinit var title: JLabel
    private lateinit var minecraftVersionBox: JComboBox<String>
    private lateinit var errorLabel: JLabel

    override fun getComponent(): JComponent {
        return panel
    }

    override fun isStepVisible(): Boolean {
        return true
    }

    private inline fun generateClassName(
        name: String,
        classNameModifier: (String) -> String = { it }
    ): String {
        val packageNameStart = creator.groupId.toPackageName()
        val packageNameEnd = creator.artifactId.toPackageName()
        val className = classNameModifier(name.replace(" ", ""))
        return "$packageNameStart.$packageNameEnd.$className"
    }

    override fun updateStep() {
        val name = WordUtils.capitalize(creator.artifactId.replace('-', ' '))
        pluginNameField.text = name
        mainClassField.text = generateClassName(name)
        CoroutineScope(Dispatchers.Swing).launch {
            try {
                withContext(Dispatchers.IO) { getVersionSelector(creator.platformType) }.set(minecraftVersionBox)
            } catch (e: Exception) {
                errorLabel.isVisible = true
            } finally {
                errorLabel.isVisible = false
            }
        }
    }

    override fun validate(): Boolean {
        try {
            for (field in javaClass.declaredFields) {
                val annotation = field.getAnnotation(ValidatedField::class.java) ?: continue
                field.isAccessible = true
                val textField = field.get(this) as? JTextField ?: continue
                for (validationType in annotation.value) {
                    validationType.validate(textField)
                }
            }
            return minecraftVersionBox.selectedItem != null
        } catch (e: SetupException) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(e.error, MessageType.ERROR, null)
                .setFadeoutTime(4000)
                .createBalloon()
                .show(RelativePoint.getSouthWestOf(e.j), Balloon.Position.below)
            return false
        }
    }

    override fun updateDataModel() {
        creator.pluginName = this.pluginNameField.text
        creator.mainClass = this.mainClassField.text
        creator.description = this.descriptionField.text
        creator.website = this.websiteField.text

        creator.loadOrder = if (this.loadOrderBox.selectedIndex == 0) LoadOrder.POSTWORLD else LoadOrder.STARTUP
        creator.prefix = this.prefixField.text
        creator.minecraftVersion = this.minecraftVersionBox.selectedItem as String

        creator.setLoadBefore(this.loadBeforeField.text)
        creator.setAuthors(this.authorsField.text)
        creator.setDependencies(this.dependField.text)
        creator.setSoftDependencies(this.softDependField.text)
    }
}
