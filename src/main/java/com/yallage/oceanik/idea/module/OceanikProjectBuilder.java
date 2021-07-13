package com.yallage.oceanik.idea.module;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

public class OceanikProjectBuilder {
    private final OceanikModuleConfig config;
    private final Project project;
    FileTemplateManager manger;

    public OceanikProjectBuilder(OceanikModuleConfig config, Project project) {
        this.config = config;
        this.project = project;
        this.manger = FileTemplateManager.getInstance(project);
    }

    public void build() {
        VirtualFile root = project.getBaseDir();

        try {
            // 创建根目录下的文件
            root.createChildData(this,".gitignore");
            root.createChildData(this,"build.gradle");
            root.createChildData(this,"settings.gradle");
            // 创建目录
            VirtualFile wrapper = VfsUtil.createDirectoryIfMissing(root, "gradle/wrapper");
            wrapper.createChildData(this,"gradle-wrapper.jar");
            wrapper.createChildData(this,"gradle-wrapper.properties");
            VirtualFile java = VfsUtil.createDirectoryIfMissing(root, "src/main/java");
            VirtualFile resources = VfsUtil.createDirectoryIfMissing(root, "src/main/resources");
            resources.createChildData(this,"plugin.yml");
            resources.createChildData(this,"oceanik.yml");
            resources.createChildData(this,"oceanik-loader.yml");
            VirtualFile util = VfsUtil.createDirectoryIfMissing(root, "src/main/java/com/yallage/oceanik/loader/util");
            util.createChildData(this,"OceanikLoader.java");
            util.createChildData(this,"VersionInfo.java");
            VirtualFile main = VfsUtil.createDirectoryIfMissing(root, "src/main/java" + "/"
                    + config.groupId.replaceAll("\\.","/") + "/" + config.artifactId);
            main.createChildData(this,"OceanikMain.java");
            main.createChildData(this,this.captureName(config.artifactId) + ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 首字母大写
    private String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;
    }
}
