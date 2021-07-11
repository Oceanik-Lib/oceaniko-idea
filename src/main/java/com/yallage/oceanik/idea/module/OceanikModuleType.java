package com.yallage.oceanik.idea.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.yallage.oceanik.idea.util.Assets;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class OceanikModuleType extends JavaModuleType {
    private static final String ID = "OCEANIK_MODULE_TYPE";
    private OceanikModuleConfig config = new OceanikModuleConfig();
    public OceanikModuleType() {
        super(ID);
    }

    public static OceanikModuleType getInstance() {
        return (OceanikModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public OceanikModuleBuilder createModuleBuilder() {
        return new OceanikModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "Oceaniko";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "A Minecraft Oceanik plugin (Oceaniko).";
    }

    @NotNull
    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return Assets.Icons.OCEANIK;
    }
}
