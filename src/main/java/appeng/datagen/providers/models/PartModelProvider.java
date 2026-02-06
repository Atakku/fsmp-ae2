package appeng.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.core.AppEng;

public class PartModelProvider extends ModelProvider<BlockModelBuilder> {
    public PartModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, AppEng.MOD_ID, "part", BlockModelBuilder::new, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Part Models: " + modid;
    }

    @Override
    protected void registerModels() {
        addBuiltInModel("part/p2p/p2p_tunnel_frequency");
    }

    /**
     * The files need to exist for Fabric's post-processor to pick them up. The content is ignored. For Forge, we still
     * set the loader name, since it'll be used there.
     */
    private void addBuiltInModel(String name) {
        getBuilder(name);
    }
}
