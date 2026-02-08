/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.client.gui.widgets;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

import appeng.api.upgrades.IUpgradeableObject;
import appeng.api.upgrades.Upgrades;
import appeng.client.Point;
import appeng.client.gui.ICompositeWidget;
import appeng.client.gui.Rects;
import appeng.client.gui.TLBaseScreen;
import appeng.client.gui.Tooltip;
import appeng.client.gui.style.Blitter;
import appeng.menu.slot.AppEngSlot;

/**
 * A panel that can draw a dynamic number of upgrade slots in a vertical layout.
 */
public final class UpgradesPanel implements ICompositeWidget {

    private static final int SLOT_SIZE = 18;
    private static final int PADDING = 5;

    private static final Blitter BACKGROUND = Blitter.texture("guis/extra_panels.png", 128, 128);

    private final List<Slot> slots;

    // The screen origin in window space (used to layout slots)
    private Point screenOrigin = Point.ZERO;

    // Relative to current screen origin (not window)
    private int x;
    private int y;

    private final Supplier<List<Component>> tooltipSupplier;

    public UpgradesPanel(List<Slot> slots) {
        this(slots, Collections::emptyList);
    }

    public UpgradesPanel(List<Slot> slots, IUpgradeableObject upgradeableObject) {
        this(slots, () -> Upgrades.getTooltipLinesForMachine(upgradeableObject.getUpgrades().getUpgradableItem()));
    }

    public UpgradesPanel(List<Slot> slots, Supplier<List<Component>> tooltipSupplier) {
        this.slots = slots;
        this.tooltipSupplier = tooltipSupplier;
    }

    @Override
    public void setPosition(Point position) {
        x = position.getX();
        y = position.getY();
    }

    /**
     * Changes where the panel is positioned. Coordinates are relative to the current screen's origin.
     */
    @Override
    public void setSize(int width, int height) {
        // Size of upgrades panel cannot be set via JSON
    }

    /**
     * The overall bounding box in screen coordinates.
     */
    @Override
    public Rect2i getBounds() {
        int slotCount = getUpgradeSlotCount();

        int height = 2 * PADDING + SLOT_SIZE;
        int width = 2 * PADDING + slotCount * SLOT_SIZE;
        return new Rect2i(x, y, width, height);
    }

    @Override
    public void populateScreen(Consumer<AbstractWidget> addWidget, Rect2i bounds, TLBaseScreen<?> screen) {
        this.screenOrigin = Point.fromTopLeft(bounds);
    }

    @Override
    public void updateBeforeRender() {
        int slotOriginX = this.x + PADDING - this.getBounds().getWidth();
        int slotOriginY = this.y + PADDING;

        for (Slot slot : slots) {
            if (!slot.isActive()) {
                continue;
            }

            slot.x = slotOriginX + 1;
            slot.y = slotOriginY + 1;
            slotOriginX += SLOT_SIZE;
        }
    }

    @Override
    public void drawBackgroundLayer(GuiGraphics guiGraphics, Rect2i bounds, Point mouse) {
        int slotCount = getUpgradeSlotCount();
        if (slotCount <= 0) {
            return;
        }

        // This is the absolute x,y coord of the first slot within the panel
        int slotOriginX = screenOrigin.getX() + this.x + PADDING - this.getBounds().getWidth();
        int y = screenOrigin.getY() + this.y + PADDING;

        for (int i = 0; i < slotCount; i++) {
            int x = slotOriginX + i * SLOT_SIZE;

            boolean borderLeft = i == 0;
            boolean borderTop = true;
            boolean borderBottom = false;
            boolean borderRight = i >= slotCount - 1;

            drawSlot(guiGraphics, x, y, borderLeft, borderTop, borderRight, borderBottom);
        }
        // Added border to match the rest of the GUI style - RID
        guiGraphics.hLine(slotOriginX, slotOriginX + (SLOT_SIZE * slotCount) - 1, y, 0XFFf2f2f2);
        guiGraphics.hLine(slotOriginX, slotOriginX + (SLOT_SIZE * slotCount) - 1, y + SLOT_SIZE - 1, 0XFFf2f2f2);
        guiGraphics.vLine(slotOriginX, y - 1, y + SLOT_SIZE, 0XFFf2f2f2);
        guiGraphics.vLine(slotOriginX + (SLOT_SIZE * slotCount) - 1, y - 1, y + SLOT_SIZE, 0XFFf2f2f2);
    }

    @Override
    public void addExclusionZones(List<Rect2i> exclusionZones, Rect2i screenBounds) {
        int offsetX = screenBounds.getX() - this.getBounds().getWidth();
        int offsetY = screenBounds.getY();

        int slotCount = getUpgradeSlotCount();

        // Use a bit of a margin around the zone to avoid things looking too cramped
        final int margin = 2;

        // Add a single bounding rectangle for as many columns as are fully populated
        int rightEdge = offsetX + x;
        exclusionZones.add(Rects.expand(new Rect2i(
                rightEdge,
                offsetY + y,
                PADDING * 2 + slotCount * SLOT_SIZE,
                SLOT_SIZE + PADDING * 2), margin));
    }

    @Nullable
    @Override
    public Tooltip getTooltip(int mouseX, int mouseY) {
        if (getUpgradeSlotCount() == 0) {
            return null;
        }

        List<Component> tooltip = this.tooltipSupplier.get();
        if (tooltip.isEmpty()) {
            return null;
        }

        return new Tooltip(tooltip);
    }

    private static void drawSlot(GuiGraphics guiGraphics, int x, int y,
            boolean borderLeft, boolean borderTop, boolean borderRight, boolean borderBottom) {
        int srcX = PADDING;
        int srcY = PADDING;
        int srcWidth = SLOT_SIZE;
        int srcHeight = SLOT_SIZE;

        if (borderLeft) {
            x -= PADDING;
            srcX = 0;
            srcWidth += PADDING;
        }
        if (borderRight) {
            srcWidth += PADDING;
        }
        if (borderTop) {
            y -= PADDING;
            srcY = 0;
            srcHeight += PADDING;
        }
        if (borderBottom) {
            srcHeight += PADDING + 2;
        }

        BACKGROUND.src(srcX, srcY, srcWidth, srcHeight)
                .dest(x, y)
                .blit(guiGraphics);
    }

    /**
     * We need this function since the cell workbench can dynamically change how many upgrade slots are active based on
     * the cell in the workbench.
     */
    private int getUpgradeSlotCount() {
        int count = 0;
        for (Slot slot : slots) {
            if (slot instanceof AppEngSlot && ((AppEngSlot) slot).isSlotEnabled()) {
                count++;
            }
        }
        return count;
    }

}
