/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
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

package appeng.client.render.cablebus;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

import appeng.api.util.TLCableType;
import appeng.api.util.TLColor;
import appeng.core.AppEng;

/**
 * TL can render the core of a cable (the core that connections are made to, in case the cable is not a straight line)
 * in three different ways: - Glass - Covered (also used by the Smart Cable) - Dense
 */
public enum CableCoreType {
    GLASS("part/cable/core/glass"), COVERED("part/cable/core/covered"), DENSE("part/cable/core/dense_smart");

    private static final Map<TLCableType, CableCoreType> cableMapping = generateCableMapping();

    /**
     * Creates the mapping that assigns a cable core type to an TL cable type.
     */
    private static Map<TLCableType, CableCoreType> generateCableMapping() {

        Map<TLCableType, CableCoreType> result = new EnumMap<>(TLCableType.class);

        result.put(TLCableType.GLASS, CableCoreType.GLASS);
        result.put(TLCableType.COVERED, CableCoreType.COVERED);
        result.put(TLCableType.SMART, CableCoreType.COVERED);
        result.put(TLCableType.DENSE_COVERED, CableCoreType.DENSE);
        result.put(TLCableType.DENSE_SMART, CableCoreType.DENSE);

        return ImmutableMap.copyOf(result);
    }

    private final String textureFolder;

    CableCoreType(String textureFolder) {
        this.textureFolder = textureFolder;
    }

    /**
     * @return The type of core that should be rendered when the given cable isn't straight and needs to have a core to
     *         attach connections to. Is null for the NULL cable.
     */
    public static CableCoreType fromCableType(TLCableType cableType) {
        return cableMapping.get(cableType);
    }

    public Material getTexture(TLColor color) {
        return new Material(TextureAtlas.LOCATION_BLOCKS,
                AppEng.makeId(this.textureFolder + "/" + color.name().toLowerCase(Locale.ROOT)));
    }

}
