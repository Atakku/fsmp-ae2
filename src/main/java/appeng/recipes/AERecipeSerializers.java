package appeng.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.core.AppEng;
import appeng.recipes.game.AddItemUpgradeRecipeSerializer;
import appeng.recipes.game.RemoveItemUpgradeRecipeSerializer;
import appeng.recipes.game.StorageCellDisassemblyRecipeSerializer;
import appeng.recipes.game.StorageCellUpgradeRecipeSerializer;
import appeng.recipes.transform.TransformRecipeSerializer;

public final class AERecipeSerializers {
    private AERecipeSerializers() {
    }

    public static final DeferredRegister<RecipeSerializer<?>> DR = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, AppEng.MOD_ID);

    static {
        register("transform", TransformRecipeSerializer.INSTANCE);
        register("storage_cell_upgrade", StorageCellUpgradeRecipeSerializer.INSTANCE);
        register("add_item_upgrade", AddItemUpgradeRecipeSerializer.INSTANCE);
        register("remove_item_upgrade", RemoveItemUpgradeRecipeSerializer.INSTANCE);
        register("storage_cell_disassembly", StorageCellDisassemblyRecipeSerializer.INSTANCE);
    }

    private static void register(String id, RecipeSerializer<?> serializer) {
        DR.register(id, () -> serializer);
    }
}
