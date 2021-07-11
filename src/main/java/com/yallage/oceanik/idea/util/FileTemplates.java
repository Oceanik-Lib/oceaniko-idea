package com.yallage.oceanik.idea.util;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

public class FileTemplates implements FileTemplateGroupDescriptorFactory {
    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        var group = new FileTemplateGroupDescriptor("Oceanik", Assets.Icons.OCEANIK);
        group.addTemplate(new FileTemplateDescriptor("Main Class.java"));
        group.addTemplate(new FileTemplateDescriptor("plugin.yml"));
        group.addTemplate(new FileTemplateDescriptor("oceanik.yml"));
        group.addTemplate(new FileTemplateDescriptor("oceanik-loader.yml"));
        group.addTemplate(new FileTemplateDescriptor("build.gradle"));
        group.addTemplate(new FileTemplateDescriptor("settings.gradle"));
        return group;
    }
}
