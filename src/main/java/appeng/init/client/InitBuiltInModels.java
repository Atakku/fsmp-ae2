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

package appeng.init.client;

import java.util.function.Supplier;

import net.minecraft.client.resources.model.UnbakedModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import appeng.client.render.cablebus.CableBusModel;
import appeng.client.render.model.ColorApplicatorModel;
import appeng.client.render.model.DriveModel;
import appeng.client.render.model.GlassModel;
import appeng.client.render.model.MeteoriteCompassModel;
import appeng.core.AppEng;
import appeng.hooks.BuiltInModelHooks;

@OnlyIn(Dist.CLIENT)
public final class InitBuiltInModels {
    private InitBuiltInModels() {
    }

    public static void init() {
        addBuiltInModel("block/cable_bus", CableBusModel::new);
        addBuiltInModel("block/quartz_glass", GlassModel::new);
        addBuiltInModel("item/meteorite_compass", MeteoriteCompassModel::new);
        addBuiltInModel("block/drive", DriveModel::new);
        addBuiltInModel("color_applicator", ColorApplicatorModel::new);
    }

    private static <T extends UnbakedModel> void addBuiltInModel(String id,
            Supplier<T> modelFactory) {
        BuiltInModelHooks.addBuiltInModel(AppEng.makeId(id), modelFactory.get());
    }
}
