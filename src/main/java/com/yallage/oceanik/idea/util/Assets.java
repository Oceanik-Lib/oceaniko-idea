package com.yallage.oceanik.idea.util;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class Assets {
    private static Icon loadIcon(String str) {
        return IconLoader.getIcon(str, Assets.class);
    }

    public static class Icons {
        public static final Icon OCEANIK = loadIcon("assets/icons/Oceanik.png");
        public static final Icon OCEANIK_2X = loadIcon("assets/icons/Oceanik@2x.png");
    }
}
