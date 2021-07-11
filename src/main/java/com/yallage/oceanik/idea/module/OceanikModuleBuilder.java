package com.yallage.oceanik.idea.module;

import com.demonwav.mcdev.creator.buildsystem.BuildSystemType;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;

public class OceanikModuleBuilder extends JavaModuleBuilder {
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



    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) throws ConfigurationException {
        Project project = rootModel.getProject();
        super.setupRootModel(rootModel);
        if (getModuleJdk() != null) {
            rootModel.setSdk(getModuleJdk());
        } else {
            rootModel.inheritSdk();
        }
        new OceanikProjectBuilder(config, project).build();
    }
}
