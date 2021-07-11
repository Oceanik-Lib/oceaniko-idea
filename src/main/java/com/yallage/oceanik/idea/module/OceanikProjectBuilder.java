package com.yallage.oceanik.idea.module;

import com.intellij.openapi.project.Project;

public class OceanikProjectBuilder {
    private OceanikModuleConfig config;
    private Project project;
    public OceanikProjectBuilder(OceanikModuleConfig config, Project project) {
        this.config = config;
        this.project = project;
    }
    public void build() {

    }
}
