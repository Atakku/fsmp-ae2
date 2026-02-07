package appeng.siteexport;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import guideme.Guide;
import guideme.internal.siteexport.SiteExporter;

import appeng.client.guidebook.ConfigValueTagExtension;

public class TLSiteExporter extends SiteExporter {
    public TLSiteExporter(Minecraft client, Path outputFolder, Guide guide) {
        super(client, outputFolder, guide);

        // Ref items used as icons
        referenceItem(Items.FURNACE);
        referenceFluid(Fluids.WATER);
        referenceFluid(Fluids.LAVA);
    }

    protected Map<String, Object> getModData() {
        return Map.of("defaultConfigValues", ConfigValueTagExtension.CONFIG_VALUES.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get())));
    }
}
