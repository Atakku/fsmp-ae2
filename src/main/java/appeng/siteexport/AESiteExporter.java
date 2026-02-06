package appeng.siteexport;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluids;

import guideme.Guide;
import guideme.internal.siteexport.SiteExporter;

import appeng.client.guidebook.ConfigValueTagExtension;
import appeng.core.definitions.AEBlocks;
import appeng.recipes.transform.TransformRecipe;

public class AESiteExporter extends SiteExporter {
    public AESiteExporter(Minecraft client, Path outputFolder, Guide guide) {
        super(client, outputFolder, guide);

        // Ref items used as icons
        referenceItem(Items.FURNACE);
        referenceFluid(Fluids.WATER);
        referenceFluid(Fluids.LAVA);
    }

    protected Map<String, Object> getModData() {

        // TODO public List<P2PTypeInfo> p2pTunnelTypes = new ArrayList<>();

        // TODO public Map<String, Map<DyeColor, String>> coloredVersions = new HashMap<>();

        return Map.of("defaultConfigValues", ConfigValueTagExtension.CONFIG_VALUES.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get())));
    }

    @Nullable
    protected Map<String, Object> getCustomRecipeFields(ResourceLocation id, Recipe<?> recipe) {
        return switch (recipe) {
            case TransformRecipe transformRecipe -> addRecipe(transformRecipe);
            case null, default -> null;
        };
    }

    private Map<String, Object> addRecipe(TransformRecipe recipe) {

        Map<String, Object> circumstanceJson = new HashMap<>();
        var circumstance = recipe.circumstance;
        if (circumstance.isFluid()) {
            circumstanceJson.put("type", "fluid");

            // Special-case water since a lot of mods add their fluids to the tag
            if (recipe.circumstance.isFluidTag(FluidTags.WATER)) {
                circumstanceJson.put("fluids", List.of(Fluids.WATER));
            } else {
                circumstanceJson.put("fluids", circumstance.getFluidsForRendering());
            }
        } else {
            throw new IllegalStateException("Unknown circumstance: " + circumstance.toJson());
        }

        return Map.of(
                "resultItem", recipe.getResultItem(null),
                "ingredients", recipe.getIngredients(),
                "circumstance", circumstanceJson);
    }
}
