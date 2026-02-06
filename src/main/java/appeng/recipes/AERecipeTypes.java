package appeng.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.core.AppEng;
import appeng.recipes.game.StorageCellDisassemblyRecipe;
import appeng.recipes.handlers.ChargerRecipe;
import appeng.recipes.handlers.InscriberRecipe;
import appeng.recipes.quartzcutting.QuartzCuttingRecipe;
import appeng.recipes.transform.TransformRecipe;

public final class AERecipeTypes {
    private AERecipeTypes() {
    }

    public static final DeferredRegister<RecipeType<?>> DR = DeferredRegister
            .create(Registries.RECIPE_TYPE, AppEng.MOD_ID);

    public static final RecipeType<TransformRecipe> TRANSFORM = register("transform");
    public static final RecipeType<InscriberRecipe> INSCRIBER = register("inscriber");
    public static final RecipeType<ChargerRecipe> CHARGER = register("charger");
    public static final RecipeType<QuartzCuttingRecipe> QUARTZ_CUTTING = register("quartz_cutting");
    public static final RecipeType<StorageCellDisassemblyRecipe> CELL_DISASSEMBLY = register(
            "storage_cell_disassembly");

    private static <T extends Recipe<?>> RecipeType<T> register(String id) {
        RecipeType<T> type = RecipeType.simple(AppEng.makeId(id));
        DR.register(id, () -> type);
        return type;
    }
}
