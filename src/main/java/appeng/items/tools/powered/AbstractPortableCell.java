package appeng.items.tools.powered;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import appeng.api.implementations.menuobjects.IMenuItem;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.core.localization.PlayerMessages;
import appeng.items.contents.PortableCellMenuHost;
import appeng.menu.MenuOpener;
import appeng.menu.locator.ItemMenuHostLocator;
import appeng.menu.locator.MenuLocators;
import appeng.recipes.game.StorageCellDisassemblyRecipe;
import appeng.util.InteractionUtil;

public abstract class AbstractPortableCell extends ContainerItem
        implements ICellWorkbenchItem, IMenuItem {

    private final MenuType<?> menuType;
    private final int defaultColor;

    public AbstractPortableCell(MenuType<?> menuType, Properties props, int defaultColor) {
        super(props);
        this.menuType = menuType;
        this.defaultColor = defaultColor;
    }

    /**
     * Gets the recipe ID for crafting this particular cell.
     */
    public abstract ResourceLocation getRecipeId();

    /**
     * Open a Portable Cell from a slot in the player inventory, i.e. activated via hotkey.
     *
     * @return True if the menu was opened.
     */
    public boolean openFromInventory(Player player, ItemMenuHostLocator locator) {
        return openFromInventory(player, locator, false);
    }

    protected boolean openFromInventory(Player player, ItemMenuHostLocator locator, boolean returningFromSubmenu) {
        var is = locator.locateItem(player);
        if (is.getItem() == this) {
            return MenuOpener.open(this.menuType, player, locator,
                    returningFromSubmenu);
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public PortableCellMenuHost<?> getMenuHost(Player player, ItemMenuHostLocator locator,
            @Nullable BlockHitResult hitResult) {
        return new PortableCellMenuHost<>(this, player, locator,
                (p, sm) -> openFromInventory(p, locator, true));
    }

    // Override to change the default color
    public int getColor(ItemStack stack) {
        return DyedItemColor.getOrDefault(stack, defaultColor);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return context.isSecondaryUseActive()
                && this.disassembleDrive(stack, context.getLevel(), context.getPlayer())
                        ? InteractionResult.sidedSuccess(context.getLevel().isClientSide())
                        : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!InteractionUtil.isInAlternateUseMode(player)
                || !disassembleDrive(player.getItemInHand(hand), level, player)) {
            if (!level.isClientSide()) {
                MenuOpener.open(this.menuType, player, MenuLocators.forHand(player, hand));
            }
        }
        return new InteractionResultHolder<>(InteractionResult.sidedSuccess(level.isClientSide()),
                player.getItemInHand(hand));
    }

    private boolean disassembleDrive(ItemStack stack, Level level, Player player) {
        var playerInventory = player.getInventory();
        var disassemblyItems = StorageCellDisassemblyRecipe.getDisassemblyResult(level, stack.getItem());
        if (disassemblyItems.isEmpty() || playerInventory.getSelected() != stack || stack.getCount() != 1) {
            return false;
        }

        if (level.isClientSide()) {
            return true; // Further checks cannot be done on the client
        }

        var inv = StorageCells.getCellInventory(stack, null);
        if (inv != null && !inv.getAvailableStacks().isEmpty()) {
            player.displayClientMessage(PlayerMessages.OnlyEmptyCellsCanBeDisassembled.text(), true);
            return true; // Prevents the UI from opening and overlaying the error message
        }

        playerInventory.setItem(playerInventory.selected, ItemStack.EMPTY);

        for (var recipeStack : disassemblyItems) {
            playerInventory.placeItemBackInInventory(recipeStack.copy());
        }

        // Drop upgrades
        getUpgrades(stack).forEach(playerInventory::placeItemBackInInventory);

        return true;
    }

    public static int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1 && stack.getItem() instanceof AbstractPortableCell) {
            // Determine LED color
            var cellInv = StorageCells.getCellInventory(stack, null);
            var cellStatus = cellInv != null ? cellInv.getStatus() : CellState.EMPTY;
            return cellStatus.getStateColor();
        } else if (tintIndex == 2 && stack.getItem() instanceof AbstractPortableCell portableCell) {
            return portableCell.getColor(stack);
        } else {
            // White
            return 0xFFFFFF;
        }
    }
}
