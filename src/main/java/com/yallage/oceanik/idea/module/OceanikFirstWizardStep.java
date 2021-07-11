package com.yallage.oceanik.idea.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OceanikFirstWizardStep extends ModuleWizardStep {
    private JPanel panel1;
    private JRadioButton craftBukkitRadioButton;
    private JRadioButton spigotRecommendedRadioButton;
    private JRadioButton paperRadioButton;
    private JLabel infoLabel;

    public OceanikFirstWizardStep() {
        craftBukkitRadioButton.addActionListener(e -> {
            if (craftBukkitRadioButton.isSelected()) infoLabel.setText(
                    "<html>The most basic Minecraft server type.<br/>" +
                            "<pl>" +
                            "<li style='color:green'>Maximum compatibility. You can use your plugin in craftbukkit, spigot and paper.</li>" +
                            "<li style='color:red'>Fewer features. Lots of features in spigot or paper is unusable, and little people use craftbukkit.</li> " +
                            "</pl></html>"
            );
        });
        spigotRecommendedRadioButton.addActionListener(e -> {
            if (spigotRecommendedRadioButton.isSelected()) infoLabel.setText(
                    "<html>Recommend to use spigot.<br/>" +
                            "<pl>" +
                            "<li style='color:green'>Great compatibility. You can use your plugin in spigot and paper.</li>" +
                            "<li style='color:green'>More features than craftbukkit.</li>" +
                            "<li style='color:green'>Most people use spigot and paper, so spigot is suitable for most people.</li> " +
                            "</pl></html>"
            );
        });
        paperRadioButton.addActionListener(e -> {
            if (paperRadioButton.isSelected()) infoLabel.setText(
                    "<html>A more advanced and faster Minecraft server type.<br/>" +
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

    }
}
