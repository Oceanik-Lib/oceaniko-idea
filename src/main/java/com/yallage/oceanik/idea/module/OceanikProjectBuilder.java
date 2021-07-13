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
            // 创建目录
            VfsUtil.createDirectoryIfMissing(root, "gradle/wrapper");
            VfsUtil.createDirectoryIfMissing(root, "src/main/java");
            VfsUtil.createDirectoryIfMissing(root, "src/main/resources");
            VfsUtil.createDirectoryIfMissing(root, "src/main/java/com/yallage/oceanik/loader/util");
            VirtualFile packagePath = VfsUtil.createDirectoryIfMissing(root, "src/main/java" + "/"
                    + config.groupId.replaceAll("\\.","/") + "/" + config.artifactId);
            // 向目录追加文件
            this.createFileIfMissing(root.getPath(), ".gitignore");
            this.createFileIfMissing(root.getPath(), "build.gradle");
            this.createFileIfMissing(root.getPath(), "settings.gradle");
            // 资源文件夹的三个文件
            this.createFileIfMissing(root.getPath() + "/src/main/resources", "plugin.yml");
            this.createFileIfMissing(root.getPath() + "/src/main/resources", "oceanik.yml");
            this.createFileIfMissing(root.getPath() + "/src/main/resources", "oceanik-loader.yml");
            // 两个特殊类文件
            this.createFileIfMissing(root.getPath() + "/src/main/java/com/yallage/oceanik/loader/util","OceanikLoader.java");
            this.createFileIfMissing(root.getPath() + "/src/main/java/com/yallage/oceanik/loader/util","VersionInfo.java");
            // gradle 的 wrapper
            this.createFileIfMissing(root.getPath() + "/gradle/wrapper", "gradle-wrapper.jar");
            this.createFileIfMissing(root.getPath() + "/gradle/wrapper", "gradle-wrapper.properties");
            // 插件主类
            this.createFileIfMissing(packagePath.getPath(),"OceanikMain.java");
            this.createFileIfMissing(packagePath.getPath(),this.captureName(config.artifactId) + ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfMissing(String path, String file){
        FileTemplate fileTemplate = manger.getTemplate(file);
        File target = new File(path + "/" + file);
        try {
            target.createNewFile();
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
