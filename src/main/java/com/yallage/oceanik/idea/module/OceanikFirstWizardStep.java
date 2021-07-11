package com.yallage.oceanik.idea.module;

import com.demonwav.mcdev.platform.PlatformType;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.JdkUtil;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkVersionUtil;
import com.intellij.openapi.roots.ui.configuration.SdkLookupUtil;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OceanikFirstWizardStep extends ModuleWizardStep {
    private JPanel panel1;
    private JRadioButton craftBukkitRadioButton;
    private JRadioButton spigotRecommendedRadioButton;
    private JRadioButton paperRadioButton;
    private JLabel infoLabel;
    private OceanikModuleConfig config;

    public OceanikFirstWizardStep(OceanikModuleConfig config) {
        this.config = config;
        craftBukkitRadioButton.addActionListener(e -> {
            if (craftBukkitRadioButton.isSelected()) {
                infoLabel.setText(
                        "<html><h3>The most basic Minecraft server type.</h3><br/>" +
                                "<pl>" +
                                "<li style='color:green'>Maximum compatibility. You can use your plugin in craftbukkit, spigot and paper.</li>" +
                                "<li style='color:red'>Fewer features. Lots of features in spigot or paper is unusable, and little people use craftbukkit.</li> " +
                                "</pl></html>"
                );
                config.platformType = PlatformType.BUKKIT;
            }
        });
        spigotRecommendedRadioButton.addActionListener(e -> {
            if (spigotRecommendedRadioButton.isSelected()) infoLabel.setText(
                    "<html><h3>Recommend to use spigot.</h3><br/>" +
                            "<pl>" +
                            "<li style='color:green'>Great compatibility. You can use your plugin in spigot and paper.</li>" +
                            "<li style='color:green'>More features than craftbukkit.</li>" +
                            "<li style='color:green'>Most people use spigot and paper, so spigot is suitable for most people.</li> " +
                            "</pl></html>"
            );
        });
        paperRadioButton.addActionListener(e -> {
            if (paperRadioButton.isSelected()) infoLabel.setText(
                    "<html><h3>A more advanced and faster Minecraft server type.</h3><br/>" +
                            "<pl>" +
                            "<li style='color:green'>There are a lot of cool features.</li>" +
                            "<li style='color:red'>You can only run your plugin with paper.</li> " +
                            "<li style='color:red'>About half of the people don't use paper, so it only work for a few people.</li> " +
                            "</pl></html>"
            );
        });
    }

    @Override
    public JComponent getComponent() {
        return panel1;
    }

    @Override
    public void updateDataModel() {
        if (craftBukkitRadioButton.isSelected()) config.platformType = PlatformType.BUKKIT;
        if (spigotRecommendedRadioButton.isSelected()) config.platformType = PlatformType.SPIGOT;
        if (paperRadioButton.isSelected()) config.platformType = PlatformType.PAPER;
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (!(craftBukkitRadioButton.isSelected() || spigotRecommendedRadioButton.isSelected() ||
                paperRadioButton.isSelected())) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder("Please select a platform type first!", MessageType.ERROR, null)
                    .setFadeoutTime(2000)
                    .createBalloon()
                    .show(RelativePoint.getSouthWestOf(spigotRecommendedRadioButton), Balloon.Position.above);
        }
        return craftBukkitRadioButton.isSelected() || spigotRecommendedRadioButton.isSelected() ||
                paperRadioButton.isSelected();
    }
}
