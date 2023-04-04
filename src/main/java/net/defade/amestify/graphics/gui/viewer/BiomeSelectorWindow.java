package net.defade.amestify.graphics.gui.viewer;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import net.defade.amestify.utils.NamespaceID;
import net.defade.amestify.world.biome.Biome;

import java.util.Comparator;

public class BiomeSelectorWindow {
    private Biome selectedBiome = Biome.PLAINS;

    public void render() {
        ImGui.begin("Biome Picker");

        renderBiomeList();

        ImGui.end();
    }

    private void renderBiomeList() {
        boolean shouldEndList = ImGui.beginListBox("##Biomes", ImGui.getContentRegionAvailX(), ImGui.getContentRegionAvailY());

        ImGui.pushStyleColor(ImGuiCol.Text, 255, 204, 0, 255); // Yellow
        for (String createdBiome : getCreatedBiomes()) {
            if (ImGui.selectable(createdBiome, createdBiome.equals(selectedBiome.name().asString()))) {
                selectedBiome = Biome.getByName(NamespaceID.from(createdBiome));
            }
        }
        ImGui.popStyleColor();

        ImGui.separator();

        for (String vanillaBiome : getVanillaBiomes()) {
            if (ImGui.selectable(vanillaBiome, selectedBiome.name().asString().equals(vanillaBiome))) {
                selectedBiome = Biome.getByName(NamespaceID.from(vanillaBiome));
            }
        }

        if(shouldEndList) ImGui.endListBox();
    }

    private static String[] getVanillaBiomes() {
        return Biome.unmodifiableCollection().stream()
                .filter(biome -> biome.name().asString().contains("minecraft:"))
                .sorted(Comparator.comparing(biome -> biome.name().asString()))
                .map(biome -> biome.name().asString())
                .toArray(String[]::new);
    }

    private static String[] getCreatedBiomes() {
        return Biome.unmodifiableCollection().stream()
                .filter(biome -> !biome.name().asString().contains("minecraft:"))
                .sorted(Comparator.comparing(biome -> biome.name().asString()))
                .map(biome -> biome.name().asString())
                .toArray(String[]::new);
    }
}