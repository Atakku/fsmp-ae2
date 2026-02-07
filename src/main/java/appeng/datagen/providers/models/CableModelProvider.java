package appeng.datagen.providers.models;

import static appeng.core.AppEng.makeId;

import java.util.Locale;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.api.util.TLColor;
import appeng.core.AppEng;
import appeng.core.definitions.ColoredItemDefinition;
import appeng.core.definitions.TLParts;

public class CableModelProvider extends TL2BlockStateProvider {
    public CableModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, AppEng.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        buildCableItems(TLParts.GLASS_CABLE, "item/glass_cable_base", "part/cable/glass/");
        buildCableItems(TLParts.COVERED_CABLE, "item/covered_cable_base", "part/cable/covered/");
        buildCableItems(TLParts.COVERED_DENSE_CABLE, "item/covered_dense_cable_base", "part/cable/dense_covered/");
        buildCableItems(TLParts.SMART_CABLE, "item/smart_cable_base", "part/cable/smart/");
        buildCableItems(TLParts.SMART_DENSE_CABLE, "item/smart_dense_cable_base", "part/cable/dense_smart/");

    }

    private void buildCableItems(ColoredItemDefinition cable, String baseModel, String textureBase) {
        for (TLColor color : TLColor.values()) {
            itemModels().withExistingParent(
                    cable.id(color).getPath(),
                    makeId(baseModel)).texture("base", makeId(textureBase + color.name().toLowerCase(Locale.ROOT)));
        }
    }
}
