package com.yallage.oceanik.idea.util;

import com.yallage.oceanik.idea.module.OceanikModuleConfig;

import java.util.HashMap;
import java.util.Map;

public class CustomConfigTemplateManager {
    OceanikModuleConfig config;
    private CustomConfigTemplateManager(){}
    public CustomConfigTemplateManager(OceanikModuleConfig config){ this.config = config;}
    public Map<String,String> getConfigMap(){
        Map<String,String> map = new HashMap<>();
        map.put("{groupId}",config.groupId);
        map.put("{artifactId}",config.artifactId);
        map.put("{main}",this.captureName(config.artifactId));
        map.put("{mainClass}",config.mainClass);
        map.put("{version}",config.version);
        map.put("{pluginName}",config.pluginName);
        map.put("{description}",config.description);
        map.put("{website}",config.website);
        map.put("{prefix}",config.prefix);
        map.put("{minecraftVersion}",config.minecraftVersion);
        map.put("{loadBefore}",config.loadBefore.toString());
        map.put("{authors}",config.authors.toString());
        map.put("{dependencies}",config.dependencies.toString());
        map.put("{softDependencies}",config.softDependencies.toString());
        return map;
    }
    // 首字母大写
    private String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
