package com.yallage.oceanik.idea.module;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;

public class OceanikModuleBuilder extends ModuleBuilder {
    private OceanikModuleConfig config = new OceanikModuleConfig();
    @Override
    public ModuleType<?> getModuleType() {
        return OceanikModuleType.getInstance();
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{
                new BuildSystemWizardStep(config),
                new BukkitProjectSettingsWizard(config)
        };
    }

    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new OceanikFirstWizardStep(config);
    }
}
