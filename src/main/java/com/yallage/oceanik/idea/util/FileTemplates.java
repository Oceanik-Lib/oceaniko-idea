package com.yallage.oceanik.idea.util;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

public class FileTemplates implements FileTemplateGroupDescriptorFactory {
    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        var group = new FileTemplateGroupDescriptor("Oceanik", Assets.Icons.OCEANIK);
        return group;
    }
}
