package com.yallage.oceanik.idea.module;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.yallage.oceanik.idea.util.CustomConfigTemplateManager;
import com.yallage.oceanik.idea.util.CustomFileTemplateManager;

import java.io.IOException;

public class OceanikProjectBuilder {
    private final OceanikModuleConfig config;
    private final Project project;

    public OceanikProjectBuilder(OceanikModuleConfig config, Project project) {
        this.config = config;
        this.project = project;
    }

    public void build() {
        CustomFileTemplateManager manager = new CustomFileTemplateManager();
        CustomConfigTemplateManager map = new CustomConfigTemplateManager(config);
        VirtualFile root = project.getBaseDir();

        try {
            // 创建根目录下的文件
            root.createChildData(this, ".gitignore").setBinaryContent(manager.getTemplate("gitignore"));
            root.createChildData(this, "build.gradle").setBinaryContent(manager.getTemplate("build.gradle"));
            root.createChildData(this, "settings.gradle").setBinaryContent(manager.getTemplate("settings.gradle",map.getConfigMap()));
            // 资源包
            VirtualFile resources = VfsUtil.createDirectoryIfMissing(root, "src/main/resources");
            resources.createChildData(this, "plugin.yml").setBinaryContent(manager.getTemplate("plugin.yml",map.getConfigMap()));
            resources.createChildData(this, "oceanik.yml").setBinaryContent(manager.getTemplate("oceanik.yml",map.getConfigMap()));
            resources.createChildData(this, "oceanik-loader.yml").setBinaryContent(manager.getTemplate("oceanik-loader.yml"));
            // 加载器
            VirtualFile util = VfsUtil.createDirectoryIfMissing(root, "src/main/java/com/yallage/oceanik/loader/util");
            util.createChildData(this, "OceanikLoader.java").setBinaryContent(manager.getTemplate("OceanikLoader.java"));
            util.createChildData(this, "VersionInfo.java").setBinaryContent(manager.getTemplate("VersionInfo.java"));
            // 主类
            VirtualFile main = VfsUtil.createDirectoryIfMissing(root, "src/main/java" + "/"
                    + config.groupId.replaceAll("\\.", "/") + "/" + config.artifactId);
            main.createChildData(this, "OceanikMain.java")
                    .setBinaryContent(manager.getTemplate("OceanikMain.java",map.getConfigMap()));
            main.createChildData(this, this.captureName(config.artifactId) + ".java")
                    .setBinaryContent(manager.getTemplate("Example.java",map.getConfigMap()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 首字母大写
    private String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
