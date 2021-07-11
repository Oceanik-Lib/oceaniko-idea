package com.yallage.oceanik.idea.module;

import com.demonwav.mcdev.platform.PlatformType;
import com.demonwav.mcdev.platform.bukkit.data.LoadOrder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class OceanikModuleConfig {
    private final Pattern commaRegex = Pattern.compile("\\s*,\\s*");
    public PlatformType platformType;
    public String groupId;
    public String artifactId;
    public String version;
    public String pluginName;
    public String mainClass;
    public String description;
    public String website;
    public LoadOrder loadOrder;
    public String prefix;
    public String minecraftVersion;
    public List<String> loadBefore;
    public List<String> authors;
    public List<String> dependencies;
    public List<String> softDependencies;
    public void setLoadBefore(String string) {
        loadBefore = commaSplit(string);
    }
    public void setAuthors(String string) {
        authors = commaSplit(string);
    }
    public void setDependencies(String string) {
        dependencies = commaSplit(string);
    }
    public void setSoftDependencies(String string) {
        softDependencies = commaSplit(string);
    }
    protected List<String> commaSplit(String string) {
        if (!string.isBlank()) {
            return Arrays.asList(commaRegex.split(string.trim().replaceAll("[\\[\\]]", "")));
        } else {
            return Collections.emptyList();
        }
    }
}
